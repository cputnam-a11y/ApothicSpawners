package io.github.cputnama11y.anitpothicspawners.impl.datagen;

import io.github.cputnama11y.antipothicspawners.impl.enchantment.AntipothicEnchantments;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class AntipothicSpawnersDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        var pack = fabricDataGenerator.createPack();
        pack.addProvider(AntipothicEnchantmentProvider::new);
        pack.addProvider(AntipothicEnchantmentTagProvider::new);
        pack.addProvider(AntipothicSpawnerModifierProvider::new);
        pack.addProvider(AntipothicSpawnersLangProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.ENCHANTMENT, AntipothicEnchantments::bootstrap);
    }
}
