package io.github.cputnama11y.antipothicspawners.impl.stats;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
public class BooleanStat extends CustomStat<Boolean> {

    public BooleanStat(Boolean defaultValue, AttachmentType<Boolean> type) {
        super(defaultValue, type);
    }

    @Override
    public Codec<Boolean> getValueCodec() {
        return Codec.BOOL;
    }

    @Override
    public Component getTooltipImpl(SpawnerBlockEntity spawner) {
        return this.name().withStyle(ChatFormatting.DARK_GREEN);
    }

    @Override
    public boolean applyModifier(SpawnerBlockEntity spawner, Boolean value, Optional<Boolean> min, Optional<Boolean> max) {
        boolean old = this.getValue(spawner);
        this.setValue(spawner, value);
        return old != this.getValue(spawner);
    }

}
