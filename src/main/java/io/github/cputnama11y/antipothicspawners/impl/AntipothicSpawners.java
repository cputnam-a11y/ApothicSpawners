package io.github.cputnama11y.antipothicspawners.impl;

import io.github.cputnama11y.antipothicspawners.impl.component.AntipothicComponents;
import io.github.cputnama11y.antipothicspawners.impl.enchantment.AntipothicEnchantmentEffectComponents;
import io.github.cputnama11y.antipothicspawners.impl.handler.CapturingHandler;
import io.github.cputnama11y.antipothicspawners.impl.handler.EchoingHandler;
import io.github.cputnama11y.antipothicspawners.impl.modifier.ModifierLoader;
import io.github.cputnama11y.antipothicspawners.impl.stats.SpawnerStats;
import io.github.cputnama11y.antipothicspawners.impl.util.StatefulPreparableReloadListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AntipothicSpawners implements ModInitializer {
    public static final String MOD_ID = "antipothicspawners";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        AntipothicEnchantmentEffectComponents.init();
        LootTableEvents.MODIFY_DROPS.register(new CapturingHandler());
        LootTableEvents.MODIFY_DROPS.register(new EchoingHandler());
        SpawnerStats.init();
        AntipothicComponents.init();
        ResourceLoader.get(PackType.SERVER_DATA).registerReloader(
                id("modifier_loader"),
                new StatefulPreparableReloadListener(
                        ModifierLoader.class.getSimpleName(),
                        ModifierLoader::new
                )
        );
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(
                entries -> entries.accept(Items.SPAWNER)
        );
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static MutableComponent lang(String type, String path, Object... args) {
        return Component.translatable(type + "." + MOD_ID + "." + path, args);
    }
}