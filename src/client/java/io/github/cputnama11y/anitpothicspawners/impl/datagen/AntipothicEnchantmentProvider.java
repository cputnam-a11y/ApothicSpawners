package io.github.cputnama11y.anitpothicspawners.impl.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

import java.util.concurrent.CompletableFuture;

import static io.github.cputnama11y.antipothicspawners.impl.AntipothicSpawners.id;

public class AntipothicEnchantmentProvider extends FabricDynamicRegistryProvider {
    public AntipothicEnchantmentProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.add(
                registries.lookupOrThrow(Registries.ENCHANTMENT)
                        .getOrThrow(
                                ResourceKey.create(
                                        Registries.ENCHANTMENT,
                                        id("capturing")
                                )
                        )
        );
    }

    @Override
    public String getName() {
        return "EnchantmentProvider";
    }
}
