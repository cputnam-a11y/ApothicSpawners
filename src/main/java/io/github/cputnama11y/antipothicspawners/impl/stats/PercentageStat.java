package io.github.cputnama11y.antipothicspawners.impl.stats;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class PercentageStat extends CustomStat<Float> {

    public PercentageStat(Float defaultValue, AttachmentType<Float> type) {
        super(defaultValue, type);
    }

    @Override
    public Codec<Float> getValueCodec() {
        return Codec.floatRange(-1, 1);
    }

    @Override
    public Component getTooltipImpl(SpawnerBlockEntity spawner) {
        return SpawnerStat.createTooltip(this, this.formatValue(this.getValue(spawner)));
    }

    @Override
    public boolean applyModifier(SpawnerBlockEntity spawner, Float value, Optional<Float> min, Optional<Float> max) {
        Float old = this.getValue(spawner);
        this.setValue(spawner, this.clamp(old + value, min, max));
        return !old.equals(this.getValue(spawner));
    }

    @Override
    public String formatValue(Float value) {
        return ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(value * 100) + "%";
    }

    private Float clamp(Float value, Optional<Float> min, Optional<Float> max) {
        if (min.isPresent()) value = Math.max(value, min.get());
        if (max.isPresent()) value = Math.min(value, max.get());
        return value;
    }

}
