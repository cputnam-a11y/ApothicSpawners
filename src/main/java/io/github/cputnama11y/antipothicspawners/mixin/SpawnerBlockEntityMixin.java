package io.github.cputnama11y.antipothicspawners.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.cputnama11y.antipothicspawners.impl.component.AntipothicComponents;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SpawnerBlockEntity.class)
public class SpawnerBlockEntityMixin extends BlockEntityMixin {
    @Override
    protected void applySpawnerStatsFromComponent(DataComponentGetter dataComponentGetter, Operation<Void> original) {
        super.applySpawnerStatsFromComponent(dataComponentGetter, original);
        var stats = dataComponentGetter.get(AntipothicComponents.SPAWNER_STATS);
        if (stats == null) return;
        var be = (SpawnerBlockEntity) (Object) this;
        stats.stats().forEach((stat, value) -> stat.setValue(be, value));
    }
}
