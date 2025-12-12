package io.github.cputnama11y.antipothicspawners.impl.stats;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Base class for implementation of vanilla stats, as they are all shorts backed by individual fields.
 */
class VanillaStat implements SpawnerStat<Integer> {

    private static final Codec<Integer> SHORT_INT = Codec.intRange(Short.MIN_VALUE, Short.MAX_VALUE);

    protected final Function<SpawnerBlockEntity, Integer> getter;
    protected final BiConsumer<SpawnerBlockEntity, Integer> setter;

    VanillaStat(Function<SpawnerBlockEntity, Integer> getter, BiConsumer<SpawnerBlockEntity, Integer> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    public Codec<Integer> getValueCodec() {
        return SHORT_INT;
    }

    @Override
    public Integer getValue(SpawnerBlockEntity spawner) {
        return this.getter.apply(spawner);
    }

    @Override
    public void setValue(SpawnerBlockEntity spawner, Integer value) {
        this.setter.accept(spawner, value);
    }

    @Override
    public Component getTooltip(SpawnerBlockEntity spawner) {
        return SpawnerStat.createTooltip(this, this.getValue(spawner).toString());
    }

    @Override
    public boolean applyModifier(SpawnerBlockEntity spawner, Integer value, Optional<Integer> min, Optional<Integer> max) {
        Integer old = this.getValue(spawner);
        this.setValue(spawner, this.clamp(old + value, min, max));
        return !old.equals(this.getValue(spawner));
    }

    @Override
    public String toString() {
        return "SpawnerStat{%s}".formatted(SpawnerStats.REGISTRY.getKey(this));
    }

    private Integer clamp(Integer value, Optional<Integer> min, Optional<Integer> max) {
        if (min.isPresent()) value = Math.max(value, min.get());
        if (max.isPresent()) value = Math.min(value, max.get());
        return value;
    }

}
