package io.github.cputnama11y.antipothicspawners.mixin;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockMixin extends BlockBehaviorMixin implements ItemLike {

}
