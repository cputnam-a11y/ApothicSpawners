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
        provider.accept(
                id("initial_health"),
                new SpawnerModifier(
                        Ingredient.of(Items.POINTED_DRIPSTONE),
                        List.of(
                                new StatModifier<>(SpawnerStats.INITIAL_HEALTH, -0.05f, Optional.empty(), Optional.of(1.0f), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("inverse/initial_health"),
                new SpawnerModifier(
                        Ingredient.of(Items.POINTED_DRIPSTONE),
                        Optional.of(Ingredient.of(Items.QUARTZ)),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.INITIAL_HEALTH, 0.05f, Optional.of(0.20f), Optional.empty(), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("vanilla/max_delay"),
                new SpawnerModifier(
                        Ingredient.of(Items.CLOCK),
                        List.of(
                                new StatModifier<>(SpawnerStats.MAX_DELAY, -20, Optional.of(20), Optional.empty(), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("inverse/vanilla/max_delay"),
                new SpawnerModifier(
                        Ingredient.of(Items.CLOCK),
                        Optional.of(Ingredient.of(Items.QUARTZ)),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.MAX_DELAY, 20, Optional.empty(), Optional.of(1600), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("vanilla/min_delay"),
                new SpawnerModifier(
                        Ingredient.of(Items.SUGAR),
                        List.of(
                                new StatModifier<>(SpawnerStats.MIN_DELAY, -10, Optional.of(20), Optional.empty(), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("inverse/vanilla/min_delay"),
                new SpawnerModifier(
                        Ingredient.of(Items.SUGAR),
                        Optional.of(Ingredient.of(Items.QUARTZ)),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.MIN_DELAY, 10, Optional.empty(), Optional.of(1600), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("inverse/vanilla/max_nearby"),
                new SpawnerModifier(
                        Ingredient.of(Items.GHAST_TEAR),
                        Optional.of(Ingredient.of(Items.QUARTZ)),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.MAX_NEARBY_ENTITIES, -2, Optional.of(1), Optional.empty(), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("vanilla/max_nearby"),
                new SpawnerModifier(
                        Ingredient.of(Items.GHAST_TEAR),
                        List.of(
                                new StatModifier<>(SpawnerStats.MAX_NEARBY_ENTITIES, 2, Optional.empty(), Optional.of(32), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("inverse/vanilla/player_range"),
                new SpawnerModifier(
                        Ingredient.of(Items.PRISMARINE_CRYSTALS),
                        Optional.of(Ingredient.of(Items.QUARTZ)),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.REQ_PLAYER_RANGE, -4, Optional.of(1), Optional.empty(), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("vanilla/player_range"),
                new SpawnerModifier(
                        Ingredient.of(Items.PRISMARINE_CRYSTALS),
                        List.of(
                                new StatModifier<>(SpawnerStats.REQ_PLAYER_RANGE, 4, Optional.empty(), Optional.of(48), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("vanilla/spawn_count"),
                new SpawnerModifier(
                        Ingredient.of(Items.FERMENTED_SPIDER_EYE),
                        List.of(
                                new StatModifier<>(SpawnerStats.SPAWN_COUNT, 2, Optional.empty(), Optional.of(16), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("inverse/vanilla/spawn_count"),
                new SpawnerModifier(
                        Ingredient.of(Items.FERMENTED_SPIDER_EYE),
                        Optional.of(Ingredient.of(Items.QUARTZ)),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.SPAWN_COUNT, -2, Optional.of(1), Optional.empty(), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("vanilla/spawn_range"),
                new SpawnerModifier(
                        Ingredient.of(Items.PISTON),
                        List.of(
                                new StatModifier<>(SpawnerStats.SPAWN_RANGE, 2, Optional.empty(), Optional.of(32), StatModifier.Mode.ADD)
                        )
                )
        );
        provider.accept(
                id("inverse/vanilla/spawn_range"),
                new SpawnerModifier(
                        Ingredient.of(Items.PISTON),
                        Optional.of(Ingredient.of(Items.QUARTZ)),
                        false,
                        List.of(
                                new StatModifier<>(SpawnerStats.SPAWN_RANGE, -2, Optional.of(1), Optional.empty(), StatModifier.Mode.ADD)
                        )
                )
        );
    }

    @Override
    public String getName() {
        return "Spawner Modifiers";
    }
}
