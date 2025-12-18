package io.github.cputnama11y.antipothicspawners.mixin;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.cputnama11y.antipothicspawners.impl.AntipothicSpawners;
import io.github.cputnama11y.antipothicspawners.impl.attachment.AntipothicAttachments;
import io.github.cputnama11y.antipothicspawners.impl.component.AntipothicComponents;
import io.github.cputnama11y.antipothicspawners.impl.component.SpawnerStatsComponent;
import io.github.cputnama11y.antipothicspawners.impl.modifier.SpawnerModifier;
import io.github.cputnama11y.antipothicspawners.impl.stats.CustomStat;
import io.github.cputnama11y.antipothicspawners.impl.stats.SpawnerStat;
import io.github.cputnama11y.antipothicspawners.impl.stats.SpawnerStats;
import io.github.cputnama11y.antipothicspawners.impl.util.OptionalFabricAttachmentDebugData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("UnstableApiUsage")
@Mixin(SpawnerBlock.class)
public abstract class SpawnerBlockMixin extends BlockMixin {
    @Override
    @SuppressWarnings("deprecation")
    protected List<ItemStack> addSpawnerToDrops(BlockState blockState, LootParams.Builder builder, Operation<List<ItemStack>> original) {
        ItemStack tool = builder.getParameter(LootContextParams.TOOL);
        Optional<Holder.Reference<@NotNull Enchantment>> silkTouch = builder.getLevel().holderLookup(Registries.ENCHANTMENT).get(Enchantments.SILK_TOUCH);

        if (silkTouch.isEmpty() || EnchantmentHelper.getItemEnchantmentLevel(silkTouch.get(), tool) < 1)
            return super.addSpawnerToDrops(blockState, builder, original);

        ItemStack spawnerStack = new ItemStack(this);
        BlockEntity blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);

        if (!(blockEntity instanceof SpawnerBlockEntity spawner))
            return super.addSpawnerToDrops(blockState, builder, original);

        try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(spawner.problemPath(), AntipothicSpawners.LOGGER)) {
            TagValueOutput tagValueOutput = TagValueOutput.createWithContext(scopedCollector, builder.getLevel().registryAccess());
            spawner.saveCustomOnly(tagValueOutput);
            spawner.removeComponentsFromTag(tagValueOutput);
            BlockItem.setBlockEntityData(spawnerStack, spawner.getType(), tagValueOutput);
            var statsBuilder = ImmutableMap.<SpawnerStat<@NotNull Object>, Object>builder();
            for (var stat : SpawnerStats.REGISTRY) {
                if (!(stat instanceof CustomStat<?>)) continue;
                statsBuilder.put((SpawnerStat<@NotNull Object>) stat, stat.getValue(spawner));
            }
            spawnerStack.applyComponents(spawner.collectComponents());
            spawnerStack.set(AntipothicComponents.SPAWNER_STATS, new SpawnerStatsComponent(statsBuilder.build()));
        }

        return List.of(spawnerStack);
    }

    @Override
    protected InteractionResult handleUseItemOnForSpawners(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult, Operation<InteractionResult> original) {
        if (!(level.getBlockEntity(blockPos) instanceof SpawnerBlockEntity spawner))
            return super.handleUseItemOnForSpawners(itemStack, blockState, level, blockPos, player, hand, blockHitResult, original);
        ItemStack otherHandStack = player.getItemInHand(
                hand == InteractionHand.MAIN_HAND
                ? InteractionHand.OFF_HAND
                : InteractionHand.MAIN_HAND
        );
        var modifiers = player.getAttached(AntipothicAttachments.MODIFIERS);
        if (modifiers == null) {
            AntipothicSpawners.LOGGER.info("Failed to find modifiers");
            AntipothicSpawners.LOGGER.info("Debug Info: {}", OptionalFabricAttachmentDebugData.info(player));
            return super.handleUseItemOnForSpawners(itemStack, blockState, level, blockPos, player, hand, blockHitResult, original);
        }
        SpawnerModifier match = SpawnerModifier.findMatch(modifiers, spawner, itemStack, otherHandStack);
        if (match != null && match.apply(spawner)) {
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS;
            }

            if (!player.isCreative()) {
                itemStack.shrink(1);
                if (match.consumesOffhand()) {
                    otherHandStack.shrink(1);
                }
            }

            level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL);
            return InteractionResult.SUCCESS;
        }

        return super.handleUseItemOnForSpawners(itemStack, blockState, level, blockPos, player, hand, blockHitResult, original);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected ItemStack addSpawnerDataToCloneItemStack(LevelReader level, BlockPos pos, BlockState blockState, boolean bl, Operation<ItemStack> original) {
        ItemStack spawnerStack = super.addSpawnerDataToCloneItemStack(level, pos, blockState, bl, original);
        BlockEntity te = level.getBlockEntity(pos);
        if (!spawnerStack.is(Items.SPAWNER) || !(te instanceof SpawnerBlockEntity spawner)) return spawnerStack;
        try (ProblemReporter.ScopedCollector scopedCollector = new ProblemReporter.ScopedCollector(spawner.problemPath(), AntipothicSpawners.LOGGER)) {
            TagValueOutput tagValueOutput = TagValueOutput.createWithContext(scopedCollector, level.registryAccess());
            spawner.saveWithoutMetadata(tagValueOutput);
            spawner.removeComponentsFromTag(tagValueOutput);
            BlockItem.setBlockEntityData(spawnerStack, spawner.getType(), tagValueOutput);
            var statsBuilder = ImmutableMap.<SpawnerStat<@NotNull Object>, Object>builder();
            for (var stat : SpawnerStats.REGISTRY) {
                if (!(stat instanceof CustomStat<?>)) continue;
                statsBuilder.put((SpawnerStat<@NotNull Object>) stat, stat.getValue(spawner));
            }
            spawnerStack.applyComponents(spawner.collectComponents());
            spawnerStack.set(AntipothicComponents.SPAWNER_STATS, new SpawnerStatsComponent(statsBuilder.build()));
        }
        return spawnerStack;
    }
}
