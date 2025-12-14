package io.github.cputnama11y.anitpothicspawners.impl.datagen;

import io.github.cputnama11y.antipothicspawners.impl.enchantment.AntipothicEnchantments;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class AntipothicSpawnersLangProvider extends FabricLanguageProvider {
    public AntipothicSpawnersLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(AntipothicEnchantments.CAPTURING.identifier(), "Capturing");
    }
}
