package io.github.cputnama11y.antipothicspawners.mixin;

import io.github.cputnama11y.antipothicspawners.impl.util.OptionalFabricAttachmentDebugData;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.lang.invoke.MethodHandles;

@Mixin(Entity.class)
public class EntityMixin implements OptionalFabricAttachmentDebugData.DebuggableAttachableEntity {
    @Unique
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    @Override
    public MethodHandles.@NotNull Lookup antipothicSpawners$getPrivilagedLookup() {
        return LOOKUP;
    }
}
