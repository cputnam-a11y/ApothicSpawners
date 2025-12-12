package io.github.cputnama11y.antipothicspawners.impl.modifier;

import com.google.common.collect.ImmutableList;
import io.github.cputnama11y.antipothicspawners.impl.AntipothicAttachments;
import io.github.cputnama11y.antipothicspawners.impl.util.LessSimpleJsonResourceReloadListener;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.github.cputnama11y.antipothicspawners.impl.AntipothicSpawners.id;

@SuppressWarnings("UnstableApiUsage")
public class ModifierLoader extends LessSimpleJsonResourceReloadListener<SpawnerModifier> {
    private static final List<SpawnerModifier> MODIFIERS = new ArrayList<>();
    private static @Nullable MinecraftServer SERVER;

    public ModifierLoader(HolderLookup.Provider provider) {
        super(provider, SpawnerModifier.CODEC.codec(), ResourceKey.createRegistryKey(id("spawner_modifier")));
    }

    @Override
    protected Map<Identifier, SpawnerModifier> prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller, SharedState state) {
        return super.prepare(resourceManager, profilerFiller, state);
    }

    @Override
    protected void apply(Map<Identifier, SpawnerModifier> object, ResourceManager resourceManager, ProfilerFiller profilerFiller, SharedState sharedState) {
        MODIFIERS.clear();
        MODIFIERS.addAll(object.values());

        if (SERVER != null) {
            var immutable = ImmutableList.copyOf(MODIFIERS);
            SERVER.getAllLevels().forEach(it -> it.setAttached(AntipothicAttachments.MODIFIERS, immutable));
        }

    }

    static {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            SERVER = server;
            var immutable = ImmutableList.copyOf(MODIFIERS);
            SERVER.getAllLevels().forEach(it -> it.setAttached(AntipothicAttachments.MODIFIERS, immutable));
        });
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> SERVER = null);
    }
}
