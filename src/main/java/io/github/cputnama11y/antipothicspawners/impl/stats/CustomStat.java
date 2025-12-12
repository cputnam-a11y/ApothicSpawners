package io.github.cputnama11y.antipothicspawners.impl.stats;

import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

@SuppressWarnings("UnstableApiUsage")
public abstract class CustomStat<T> implements SpawnerStat<T> {

    private final T defaultValue;
    private final AttachmentType<T> type;

    public CustomStat(T defaultValue, AttachmentType<T> type) {
        this.defaultValue = defaultValue;
        this.type = type;
    }

    @Override
    public T getValue(SpawnerBlockEntity spawner) {
        return spawner.getAttachedOrElse(type, this.defaultValue);
    }

    @Override
    public void setValue(SpawnerBlockEntity spawner, T value) {
        spawner.setAttached(type, value);
    }

    @Override
    public final Component getTooltip(SpawnerBlockEntity spawner) {
        return this.getValue(spawner) == this.defaultValue
               ? CommonComponents.EMPTY
               : this.getTooltipImpl(spawner);
    }

    @Override
    public String toString() {
        return "SpawnerStat{%s}".formatted(SpawnerStats.REGISTRY.getKey(this));
    }

    public abstract Component getTooltipImpl(SpawnerBlockEntity spawner);

}
