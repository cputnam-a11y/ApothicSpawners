package io.github.cputnama11y.antipothicspawners.impl.util;

import com.mojang.serialization.Codec;
import io.github.cputnama11y.antipothicspawners.impl.mixinsupport.StateHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public abstract class LessSimpleJsonResourceReloadListener<T> extends SimpleJsonResourceReloadListener<T> {

    protected LessSimpleJsonResourceReloadListener(HolderLookup.Provider provider, Codec<T> codec, ResourceKey<? extends Registry<T>> resourceKey) {
        super(provider, codec, resourceKey);
    }

    @Override
    protected final Map<Identifier, T> prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        return this.prepare(resourceManager, profilerFiller, StateHolder.currentState().orElseThrow());
    }


    @Override
    protected final void apply(Map<Identifier, T> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        this.apply(object, resourceManager, profilerFiller, StateHolder.currentState().orElseThrow());
    }

    protected abstract void apply(Map<Identifier, T> object, ResourceManager resourceManager, ProfilerFiller profilerFiller, SharedState state);

    protected Map<Identifier, T> prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller, SharedState state) {
        return super.prepare(resourceManager, profilerFiller);
    }
}
