package io.github.cputnama11y.antipothicspawners.impl.handler;

import com.mojang.datafixers.util.Pair;
import io.github.cputnama11y.antipothicspawners.impl.enchantment.AntipothicEnchantmentEffectComponents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.Optionull;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public class CapturingHandler implements LootTableEvents.ModifyDrops {
    @Override
    public void modifyLootTableDrops(Holder<LootTable> entry, LootContext context, List<ItemStack> drops) {
        if (
                !context.hasParameter(LootContextParams.ATTACKING_ENTITY)
                        || !context.hasParameter(LootContextParams.THIS_ENTITY)
        ) return;
        var attacker = context.getParameter(LootContextParams.ATTACKING_ENTITY);
        var killed = context.getParameter(LootContextParams.THIS_ENTITY);
        var weapon = attacker.getWeaponItem();
        if (weapon == null) return;
        int enchantmentLevel = Optionull.mapOrDefault(EnchantmentHelper.getHighestLevel(weapon, AntipothicEnchantmentEffectComponents.CAPTURING), Pair::getSecond, 0);
        if (killed.level().random.nextFloat() >= enchantmentLevel * 0.1) return;
        Item eggItem = SpawnEggItem.byId(killed.getType());
        if (eggItem == null) return;
        drops.add(new ItemStack(eggItem));

    }
}
