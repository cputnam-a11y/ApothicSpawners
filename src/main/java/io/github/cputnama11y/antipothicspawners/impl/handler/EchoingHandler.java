package io.github.cputnama11y.antipothicspawners.impl.handler;

import io.github.cputnama11y.antipothicspawners.impl.AntipothicAttachments;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Holder;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class EchoingHandler implements LootTableEvents.ModifyDrops {
    ThreadLocal<@Nullable Unit> REC_GUARD = ThreadLocal.withInitial(() -> null);

    @Override
    public void modifyLootTableDrops(Holder<LootTable> entry, LootContext context, List<ItemStack> drops) {
        var entity = context.getOptionalParameter(LootContextParams.THIS_ENTITY);
        if (REC_GUARD.get() != null || !(entity instanceof LivingEntity)) return;
        try {
            REC_GUARD.set(Unit.INSTANCE);
            var echoing = entity.getAttached(AntipothicAttachments.ECHOING);
            if (echoing != null && echoing > 0) {
                for (int i = 0; i < echoing; i++) {
                    entry.value().getRandomItems(context, drops::add);
                }
            }
        } finally {
            REC_GUARD.remove();
        }
    }
}
