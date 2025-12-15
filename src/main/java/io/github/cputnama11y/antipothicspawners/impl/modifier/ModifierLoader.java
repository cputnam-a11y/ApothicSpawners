package io.github.cputnama11y.antipothicspawners.impl.modifier;

import com.google.common.collect.ImmutableList;
import io.github.cputnama11y.antipothicspawners.impl.attachment.AntipothicAttachments;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.v1.DataResourceLoader;
import net.fabricmc.fabric.api.resource.v1.DataResourceStore;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

import static io.github.cputnama11y.antipothicspawners.impl.AntipothicSpawners.id;

@SuppressWarnings("UnstableApiUsage")
public class ModifierLoader extends SimpleJsonResourceReloadListener<SpawnerModifier> {
    private final SharedState state;
    private static final DataResourceStore.Key<ImmutableList<SpawnerModifier>> MODIFIERS_STORE_KEY = new DataResourceStore.Key<>();

    public ModifierLoader(PreparableReloadListener.SharedState state) {
        super(state.get(ResourceLoader.RELOADER_REGISTRY_LOOKUP_KEY), SpawnerModifier.CODEC.codec(), ResourceKey.createRegistryKey(id("spawner_modifier")));
        this.state = state;
    }

    @Override
    protected void apply(Map<Identifier, SpawnerModifier> object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        var mutableResourceStore = state.get(DataResourceLoader.DATA_RESOURCE_STORE_KEY);
        mutableResourceStore.put(MODIFIERS_STORE_KEY, ImmutableList.copyOf(object.values()));
    }

    static {
        ServerLifecycleEvents.SYNC_DATA_PACK_CONTENTS.register((player, joined) -> {
            player.setAttached(AntipothicAttachments.MODIFIERS, player.level().getServer().getOrThrow(MODIFIERS_STORE_KEY));
        });
    }
}
