package io.github.cputnama11y.antipothicspawners.impl.stats;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

import java.util.Optional;

public class LevelStat extends CustomStat<Integer> {

    public LevelStat(Integer defaultValue, AttachmentType<Integer> type) {
        super(defaultValue, type);
    }

    @Override
    public Codec<Integer> getValueCodec() {
        return Codec.INT;
    }

    @Override
    public boolean applyModifier(SpawnerBlockEntity spawner, Integer value, Optional<Integer> min, Optional<Integer> max) {
        Integer old = this.getValue(spawner);
        this.setValue(spawner, this.clamp(old + value, min, max));
        return !old.equals(this.getValue(spawner));
    }

    private Integer clamp(Integer value, Optional<Integer> min, Optional<Integer> max) {
        if (min.isPresent()) value = Math.max(value, min.get());
        if (max.isPresent()) value = Math.min(value, max.get());
        return value;
    }

    @Override
    public Component getTooltipImpl(SpawnerBlockEntity spawner) {
        return SpawnerStat.createTooltip(this, this.formatValue(this.getValue(spawner)));
    }

    @Override
    public String formatValue(Integer value) {
        if (value < 0) {
            return Component.literal("-").append(Component.translatable("enchantment.level." + Math.abs(value))).getString();
        }
        return Component.translatable("enchantment.level." + Math.abs(value)).getString();
    }

}
