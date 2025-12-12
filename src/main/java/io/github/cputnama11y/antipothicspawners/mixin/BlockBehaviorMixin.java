package io.github.cputnama11y.antipothicspawners.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(BlockBehaviour.class)
public class BlockBehaviorMixin {
    @WrapMethod(
            method = "getDrops"
    )
    protected List<ItemStack> addSpawnerToDrops(BlockState blockState, LootParams.Builder builder, Operation<List<ItemStack>> original) {
        return original.call(blockState, builder);
    }

    @WrapMethod(
            method = "useItemOn"
    )
    protected InteractionResult handleUseItemOnForSpawners(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult, Operation<InteractionResult> original) {
        return original.call(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
    }

    @WrapMethod(
            method = "getCloneItemStack"
    )
    protected ItemStack addSpawnerDataToCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState, boolean bl, Operation<ItemStack> original) {
        return original.call(levelReader, blockPos, blockState, bl);
    }
}
