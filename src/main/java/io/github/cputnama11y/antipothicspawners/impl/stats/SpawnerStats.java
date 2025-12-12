package io.github.cputnama11y.antipothicspawners.impl.stats;

import io.github.cputnama11y.antipothicspawners.impl.AntipothicAttachments;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;

import java.util.function.Consumer;

import static io.github.cputnama11y.antipothicspawners.impl.AntipothicSpawners.id;

public class SpawnerStats {

    public static final Registry<SpawnerStat<?>> REGISTRY =
            FabricRegistryBuilder.<SpawnerStat<?>>createSimple(
                            ResourceKey.createRegistryKey(
                                    id("spawner_stat")
                            )
                    )
                    .attribute(RegistryAttribute.SYNCED)
                    .buildAndRegister();

    public static final SpawnerStat<Integer> MIN_DELAY = register("min_delay", new VanillaStat(s -> s.spawner.minSpawnDelay, (s, v) -> s.spawner.minSpawnDelay = v));

    public static final SpawnerStat<Integer> MAX_DELAY = register("max_delay", new VanillaStat(s -> s.spawner.maxSpawnDelay, (s, v) -> s.spawner.maxSpawnDelay = v));

    public static final SpawnerStat<Integer> SPAWN_COUNT = register("spawn_count", new VanillaStat(s -> s.spawner.spawnCount, (s, v) -> s.spawner.spawnCount = v));

    public static final SpawnerStat<Integer> MAX_NEARBY_ENTITIES = register("max_nearby_entities", new VanillaStat(s -> s.spawner.maxNearbyEntities, (s, v) -> s.spawner.maxNearbyEntities = v));

    public static final SpawnerStat<Integer> REQ_PLAYER_RANGE = register("req_player_range", new VanillaStat(s -> s.spawner.requiredPlayerRange, (s, v) -> s.spawner.requiredPlayerRange = v));

    public static final SpawnerStat<Integer> SPAWN_RANGE = register("spawn_range", new VanillaStat(s -> s.spawner.spawnRange, (s, v) -> s.spawner.spawnRange = v));
    // TODO: Wire
    public static final SpawnerStat<Float> INITIAL_HEALTH = register("initial_health", new PercentageStat(1F, AntipothicAttachments.INITIAL_HEALTH));

    public static final SpawnerStat<Boolean> IGNORE_PLAYERS = register("ignore_players", new BooleanStat(false, AntipothicAttachments.IGNORE_PLAYERS));
    // TODO: Wire
    public static final SpawnerStat<Boolean> IGNORE_CONDITIONS = register("ignore_conditions", new BooleanStat(false, AntipothicAttachments.IGNORE_CONDITIONS));

    public static final SpawnerStat<Boolean> REDSTONE_CONTROL = register("redstone_control", new BooleanStat(false, AntipothicAttachments.REDSTONE_CONTROL));
    // TODO: Wire
    public static final SpawnerStat<Boolean> IGNORE_LIGHT = register("ignore_light", new BooleanStat(false, AntipothicAttachments.IGNORE_LIGHT));
    // TODO: Wire
    public static final SpawnerStat<Boolean> NO_AI = register("no_ai", new BooleanStat(false, AntipothicAttachments.NO_AI));
    // TODO: Wire
    public static final SpawnerStat<Boolean> SILENT = register("silent", new BooleanStat(false, AntipothicAttachments.SILENT));
    // TODO: Wire
    public static final SpawnerStat<Boolean> YOUTHFUL = register("youthful", new BooleanStat(false, AntipothicAttachments.YOUTHFUL));
    // TODO: Wire
    public static final SpawnerStat<Boolean> BURNING = register("burning", new BooleanStat(false, AntipothicAttachments.BURNING));
    // TODO: Wire
    public static final SpawnerStat<Integer> ECHOING = register("echoing", new LevelStat(0, AntipothicAttachments.ECHOING));

    public static void init() {
    }

    public static void generateTooltip(SpawnerBlockEntity tile, Consumer<Component> list) {
        for (SpawnerStat<?> stat : REGISTRY) {
            Component comp = stat.getTooltip(tile);
            if (!comp.getString().isEmpty()) {
                list.accept(comp);
            }
        }
    }

    private static <T extends SpawnerStat<?>> T register(String id, T t) {
        Registry.register(REGISTRY, id(id), t);
        return t;
    }

}
