package io.github.cputnama11y.anitpothicspawners.impl.datagen;

import io.github.cputnama11y.antipothicspawners.impl.enchantment.AntipothicEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.util.Util;

import java.util.concurrent.CompletableFuture;

public class AntipothicSpawnersLangProvider extends FabricLanguageProvider {
    public AntipothicSpawnersLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(Util.makeDescriptionId("enchantment", AntipothicEnchantments.CAPTURING.identifier()), "Capturing");
        translationBuilder.add("misc.antipothicspawners.concat", "%s %s");
        translationBuilder.add("misc.antipothicspawners.value_concat", "%s: %s");
        translationBuilder.add("misc.antipothicspawners.min_value", "Min Value: %s");
        translationBuilder.add("misc.antipothicspawners.max_value", "Max Value: %s");
        translationBuilder.add("misc.antipothicspawners.mainhand", "Held in Main Hand");
        translationBuilder.add("misc.antipothicspawners.offhand", "Held in Off Hand");
        translationBuilder.add("misc.antipothicspawners.not_consumed", "This item is not consumed");
        translationBuilder.add("misc.antipothicspawners.rclick_spawner", "Right-Click the Spawner");
        translationBuilder.add("misc.antipothicspawners.entity", "Entity");
        translationBuilder.add("misc.antipothicspawners.shift_stats", "Hold Shift for Stats");
        translationBuilder.add("misc.antipothicspawners.ctrl_stats", "Hold Ctrl for Stats");
        translationBuilder.add("misc.antipothicspawners.banned", "Cannot be used on a Spawner");
        translationBuilder.add("misc.antipothicspawners.on", "%s: On");
        translationBuilder.add("misc.antipothicspawners.off", "%s: Off");
        translationBuilder.add("stat.antipothicspawners.min_delay", "Min Spawn Delay");
        translationBuilder.add("stat.antipothicspawners.max_delay", "Max Spawn Delay");
        translationBuilder.add("stat.antipothicspawners.spawn_count", "Spawn Count");
        translationBuilder.add("stat.antipothicspawners.max_nearby_entities", "Max Entities");
        translationBuilder.add("stat.antipothicspawners.req_player_range", "Activation Range");
        translationBuilder.add("stat.antipothicspawners.spawn_range", "Spawn Range");
        translationBuilder.add("stat.antipothicspawners.ignore_players", "Ignores Players");
        translationBuilder.add("stat.antipothicspawners.ignore_conditions", "Ignores Conditions");
        translationBuilder.add("stat.antipothicspawners.redstone_control", "Redstone Control");
        translationBuilder.add("stat.antipothicspawners.ignore_light", "Ignores Light");
        translationBuilder.add("stat.antipothicspawners.no_ai", "No AI");
        translationBuilder.add("stat.antipothicspawners.silent", "Silent");
        translationBuilder.add("stat.antipothicspawners.youthful", "Youthful");
        translationBuilder.add("stat.antipothicspawners.initial_health", "Initial Health");
        translationBuilder.add("stat.antipothicspawners.burning", "Burning");
        translationBuilder.add("stat.antipothicspawners.echoing", "Echoing");

    }
}
