package io.github.cputnama11y.antipothicspawners.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.cputnama11y.antipothicspawners.impl.attachment.AntipothicAttachments;
import io.github.cputnama11y.antipothicspawners.impl.stats.SpawnerStats;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
import java.util.Optional;


@Mixin(targets = "net.minecraft.world.level.block.entity.SpawnerBlockEntity$1")
public class SpawnerBlockEntity$1Mixin extends BaseSpawnerMixin {
    @Unique
    private SpawnerBlockEntity spawner;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void storeSpawner(SpawnerBlockEntity spawner, CallbackInfo ci) {
        this.spawner = spawner;
    }

    @Override
    protected boolean checkRedstoneControl(BaseSpawner instance, Level level, BlockPos blockPos, Operation<Boolean> original) {
        return super.checkRedstoneControl(instance, level, blockPos, original) && (!SpawnerStats.REDSTONE_CONTROL.getValue(spawner) || (spawner.getLevel() != null && spawner.getLevel().hasNeighborSignal(spawner.getBlockPos())));
    }

    @Override
    protected boolean checkPlayerOverride(boolean original, Level level, BlockPos blockPos) {
        return super.checkPlayerOverride(original, level, blockPos) || SpawnerStats.IGNORE_PLAYERS.getValue(spawner);
    }

    @Override
    protected void handleBurningModifier(ServerLevel serverLevel, BlockPos blockPos, CallbackInfo ci, Entity entity) {
        if (SpawnerStats.BURNING.getValue(spawner)) {
            entity.getSelfAndPassengers().forEach(it -> it.setRemainingFireTicks(Integer.MAX_VALUE));
        }
    }

    @Override
    protected void handleInitialHealthModifier(ServerLevel serverLevel, BlockPos blockPos, CallbackInfo ci, Entity entity) {
        float healthMultiplier = SpawnerStats.INITIAL_HEALTH.getValue(spawner);
        if (healthMultiplier != 1) {
            entity.getSelfAndPassengers().forEach(it -> {
                if (it instanceof LivingEntity living) {
                    living.setHealth(living.getHealth() * healthMultiplier);
                }
            });
        }
    }

    @Override
    protected void handleSilentModifier(ServerLevel serverLevel, BlockPos blockPos, CallbackInfo ci, Entity entity) {
        if (SpawnerStats.SILENT.getValue(this.spawner)) {
            entity.getSelfAndPassengers().forEach(it -> it.setSilent(true));
        }
    }

    @Override
    protected void handleYouthfulModifier(ServerLevel serverLevel, BlockPos blockPos, CallbackInfo ci, Entity entity) {
        if (SpawnerStats.YOUTHFUL.getValue(this.spawner)) {
            entity.getSelfAndPassengers().forEach(it -> {
                if (it instanceof Mob mob) {
                    mob.setBaby(true);
                }
            });
        }
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    protected void handleEchoingModifier(ServerLevel serverLevel, BlockPos blockPos, CallbackInfo ci, Entity entity) {
        int echoing = SpawnerStats.ECHOING.getValue(this.spawner);
        if (echoing > 0) {
            entity.getSelfAndPassengers().forEach(it -> it.setAttached(AntipothicAttachments.ECHOING, echoing));
        }
    }

    @Override
    protected Optional<SpawnData.CustomSpawnRules> getApplicableCustomRules(Optional<SpawnData.CustomSpawnRules> original) {
        return super.getApplicableCustomRules(original).filter(it -> Objects.requireNonNullElse(SpawnerStats.IGNORE_CONDITIONS.getValue(this.spawner), false));
    }

    @Override
    protected boolean cancelPlacementCheckIfIgnoreConditions(boolean original) {
        return super.cancelPlacementCheckIfIgnoreConditions(original) || Objects.requireNonNullElse(SpawnerStats.IGNORE_CONDITIONS.getValue(this.spawner), false);
    }
}
