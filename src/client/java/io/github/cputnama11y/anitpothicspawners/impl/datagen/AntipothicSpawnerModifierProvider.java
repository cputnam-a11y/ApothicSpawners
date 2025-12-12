package io.github.cputnama11y.anitpothicspawners.impl.datagen;

import io.github.cputnama11y.antipothicspawners.impl.modifier.SpawnerModifier;
import io.github.cputnama11y.antipothicspawners.impl.modifier.StatModifier;
import io.github.cputnama11y.antipothicspawners.impl.stats.SpawnerStats;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import static io.github.cputnama11y.antipothicspawners.impl.AntipothicSpawners.id;

public class AntipothicSpawnerModifierProvider extends FabricCodecDataProvider<SpawnerModifier> {
    protected AntipothicSpawnerModifierProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, PackOutput.Target.DATA_PACK, id("spawner_modifier").toString().replace(":", "/"), SpawnerModifier.CODEC.codec());
    }

    @Override
    protected void configure(BiConsumer<Identifier, SpawnerModifier> provider, HolderLookup.Provider lookup) {
        final var itemLookup = lookup.lookupOrThrow(Registries.ITEM);
        provider.accept(
                id("burning"),
                new SpawnerModifier(
                        Ingredient.of(Items.CAMPFIRE),
                        Optional.empty(),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.BURNING, true)
                        )
                )
        );
        provider.accept(
                id("inverse/burning"),
                new SpawnerModifier(
                        Ingredient.of(Items.CAMPFIRE),
                        Optional.of(Ingredient.of(Items.QUARTZ)),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.BURNING, false)
                        )
                )
        );

        provider.accept(
                id("redstone_control"),
                new SpawnerModifier(
                        Ingredient.of(Items.COMPARATOR),
                        Optional.empty(),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.REDSTONE_CONTROL, true)
                        )
                )
        );
        provider.accept(
                id("inverse/redstone_control"),
                new SpawnerModifier(
                        Ingredient.of(Items.COMPARATOR),
                        Optional.of(Ingredient.of(Items.QUARTZ)),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.BURNING, false)
                        )
                )
        );

        provider.accept(
                id("silent"),
                new SpawnerModifier(
                        Ingredient.of(itemLookup.getOrThrow(ItemTags.WOOL)),
                        Optional.empty(),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.SILENT, true)
                        )
                )
        );
        provider.accept(
                id("inverse/silent"),
                new SpawnerModifier(
                        Ingredient.of(itemLookup.getOrThrow(ItemTags.WOOL)),
                        Optional.of(Ingredient.of(Items.QUARTZ)),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.SILENT, false)
                        )
                )
        );

        provider.accept(
                id("ignore_players"),
                new SpawnerModifier(
                        Ingredient.of(Items.NETHER_STAR),
                        Optional.empty(),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.IGNORE_PLAYERS, true)
                        )
                )
        );
        provider.accept(
                id("inverse/ignore_players"),
                new SpawnerModifier(
                        Ingredient.of(Items.NETHER_STAR),
                        Optional.of(Ingredient.of(Items.QUARTZ)),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.IGNORE_PLAYERS, false)
                        )
                )
        );

        provider.accept(
                id("youthful"),
                new SpawnerModifier(
                        Ingredient.of(Items.TURTLE_EGG),
                        Optional.empty(),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.YOUTHFUL, true)
                        )
                )
        );
        provider.accept(
                id("inverse/youthful"),
                new SpawnerModifier(
                        Ingredient.of(Items.TURTLE_EGG),
                        Optional.of(Ingredient.of(Items.QUARTZ)),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.YOUTHFUL, false)
                        )
                )
        );

        provider.accept(
                id("echoing"),
                new SpawnerModifier(
                        Ingredient.of(Items.ECHO_SHARD),
                        Optional.empty(),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.ECHOING, 1, Optional.empty(), Optional.of(5), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("inverse/echoing"),
                new SpawnerModifier(
                        Ingredient.of(Items.ECHO_SHARD),
                        Optional.of(Ingredient.of(Items.QUARTZ)),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.ECHOING, -1, Optional.of(0), Optional.empty(), StatModifier.Mode.ADD)
                        )
                )
        );
    }

    @Override
    public String getName() {
        return "";
    }
}
