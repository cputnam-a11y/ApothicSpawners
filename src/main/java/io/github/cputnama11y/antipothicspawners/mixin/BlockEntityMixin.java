package io.github.cputnama11y.antipothicspawners.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockEntity.class)
public class BlockEntityMixin {
    @WrapMethod(
            method = "applyImplicitComponents"
    )
    protected void applySpawnerStatsFromComponent(DataComponentGetter dataComponentGetter, Operation<Void> original) {
        original.call(dataComponentGetter);
    }
}
