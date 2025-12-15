package io.github.cputnama11y.antipothicspawners.impl.component;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.NullMarked;

import java.util.function.UnaryOperator;

import static io.github.cputnama11y.antipothicspawners.impl.AntipothicSpawners.id;

@NullMarked
public interface AntipothicComponents {
    DataComponentType<SpawnerStatsComponent> SPAWNER_STATS = register("spawner_stats", it -> it.persistent(SpawnerStatsComponent.CODEC));

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> unaryOperator) {
        return Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                ResourceKey.create(
                        Registries.DATA_COMPONENT_TYPE,
                        id(name)
                ),
                unaryOperator
                        .apply(DataComponentType.builder())
                        .build()
        );
    }

    static void init() {

    }
}
