package io.github.cputnama11y.antipothicspawners.mixin;

import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;
import java.util.stream.Collectors;

@Mixin(BlockEntityType.class)
public class BlockEntityTypeMixin {
    @Shadow
    @Final
    private static Set<BlockEntityType<?>> OP_ONLY_CUSTOM_DATA;

    static {
        OP_ONLY_CUSTOM_DATA = OP_ONLY_CUSTOM_DATA.stream()
                .filter(it -> it != BlockEntityType.MOB_SPAWNER)
                .collect(Collectors.toUnmodifiableSet());
    }
}
