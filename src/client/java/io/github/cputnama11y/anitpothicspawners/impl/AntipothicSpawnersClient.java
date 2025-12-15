package io.github.cputnama11y.anitpothicspawners.impl;

import io.github.cputnama11y.antipothicspawners.impl.AntipothicSpawners;
import io.github.cputnama11y.antipothicspawners.impl.stats.SpawnerStats;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AntipothicSpawnersClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, tooltipType, lines) -> {
            if (!stack.is(Items.SPAWNER) || !stack.has(DataComponents.BLOCK_ENTITY_DATA) || context.registries() == null)
                return;

            if (!Minecraft.getInstance().hasShiftDown()) {
                lines.add(AntipothicSpawners.lang("misc", "shift_stats").withStyle(ChatFormatting.GRAY));
                return;
            }
            TypedEntityData<@NotNull BlockEntityType<?>> data = stack.get(DataComponents.BLOCK_ENTITY_DATA);
            if (data == null) return;
            SpawnerBlockEntity spawner = new SpawnerBlockEntity(BlockPos.ZERO, Blocks.SPAWNER.defaultBlockState());
            data.loadInto(spawner, Objects.requireNonNull(context.registries()));
            SpawnerStats.generateTooltip(spawner, lines::add);
        });
    }
}