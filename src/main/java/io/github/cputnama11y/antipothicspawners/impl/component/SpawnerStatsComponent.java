package io.github.cputnama11y.antipothicspawners.impl.component;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import io.github.cputnama11y.antipothicspawners.impl.stats.SpawnerStat;
import io.github.cputnama11y.antipothicspawners.impl.stats.SpawnerStats;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NullMarked;

import java.util.function.Function;

@SuppressWarnings("unchecked")
@NullMarked
public record SpawnerStatsComponent(ImmutableMap<SpawnerStat<Object>, Object> stats) {
    public static final Codec<SpawnerStatsComponent> CODEC = Codec.dispatchedMap(
            Codec.lazyInitialized(() -> (Codec<SpawnerStat<@NotNull Object>>) (Object) SpawnerStats.REGISTRY.byNameCodec()),
            SpawnerStat::getValueCodec
    ).xmap(
            ImmutableMap::copyOf,
            Function.identity()
    ).xmap(
            SpawnerStatsComponent::new,
            SpawnerStatsComponent::stats
    );
}
