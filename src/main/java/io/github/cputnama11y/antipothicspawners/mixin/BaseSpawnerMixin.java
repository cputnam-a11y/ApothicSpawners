package io.github.cputnama11y.antipothicspawners.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(BaseSpawner.class)
public class BaseSpawnerMixin {
    @WrapOperation(
            method = {"serverTick", "clientTick"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/BaseSpawner;isNearPlayer(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Z"
            )
    )
    protected boolean checkRedstoneControl(BaseSpawner instance, Level level, BlockPos blockPos, Operation<Boolean> original) {
        return original.call(instance, level, blockPos);
    }

    @ModifyReturnValue(
            method = "isNearPlayer",
            at = @At("RETURN")
    )
    protected boolean checkPlayerOverride(boolean original, Level level, BlockPos blockPos) {
        return original;
    }

    @Inject(
            method = "serverTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;snapTo(DDDFF)V"
            )
    )
    protected void handleBurningModifier(ServerLevel serverLevel, BlockPos blockPos, CallbackInfo ci, @Local Entity entity) {

    }

    @Inject(
            method = "serverTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;snapTo(DDDFF)V"
            )
    )
    protected void handleInitialHealthModifier(ServerLevel serverLevel, BlockPos blockPos, CallbackInfo ci, @Local Entity entity) {

    }

    @Inject(
            method = "serverTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;snapTo(DDDFF)V"
            )
    )
    protected void handleSilentModifier(ServerLevel serverLevel, BlockPos blockPos, CallbackInfo ci, @Local Entity entity) {

    }

    @Inject(
            method = "serverTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;snapTo(DDDFF)V"
            )
    )
    protected void handleYouthfulModifier(ServerLevel serverLevel, BlockPos blockPos, CallbackInfo ci, @Local Entity entity) {

    }

    @Inject(
            method = "serverTick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;snapTo(DDDFF)V"
            )
    )
    protected void handleEchoingModifier(ServerLevel serverLevel, BlockPos blockPos, CallbackInfo ci, @Local Entity entity) {

    }
}
