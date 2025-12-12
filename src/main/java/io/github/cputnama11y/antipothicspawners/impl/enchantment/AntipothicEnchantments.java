package io.github.cputnama11y.antipothicspawners.impl.enchantment;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;

import static io.github.cputnama11y.antipothicspawners.impl.AntipothicSpawners.id;
import static net.minecraft.world.item.enchantment.Enchantment.enchantment;

public class AntipothicEnchantments {
    public static final ResourceKey<Enchantment> CAPTURING = ResourceKey.create(
            Registries.ENCHANTMENT,
            id("capturing")
    );

    public static void bootstrap(BootstrapContext<Enchantment> bootstrapContext) {
        register(
                bootstrapContext,
                CAPTURING,
                enchantment(
                        Enchantment.definition(
                                bootstrapContext.lookup(Registries.ITEM)
                                        .getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                                bootstrapContext.lookup(Registries.ITEM).getOrThrow(ItemTags.SWORDS),
                                2,
                                3,
                                Enchantment.dynamicCost(15, 10),
                                Enchantment.dynamicCost(200, 0),
                                1,
                                EquipmentSlotGroup.MAINHAND
                        )
                ).withEffect(
                        AntipothicEnchantmentEffectComponents.CAPTURING
                )
        );
    }

    private static void register(BootstrapContext<Enchantment> bootstrapContext, ResourceKey<Enchantment> resourceKey, Enchantment.Builder builder) {
        bootstrapContext.register(resourceKey, builder.build(resourceKey.identifier()));
    }
}
