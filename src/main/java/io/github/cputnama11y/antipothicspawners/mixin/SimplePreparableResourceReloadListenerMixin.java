package io.github.cputnama11y.antipothicspawners.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.cputnama11y.antipothicspawners.impl.mixinsupport.StateHolder;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Mixin(SimplePreparableReloadListener.class)
public class SimplePreparableResourceReloadListenerMixin<T> {
    @Definition(id = "supplyAsync", method = "Ljava/util/concurrent/CompletableFuture;supplyAsync(Ljava/util/function/Supplier;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;")
    @Expression("supplyAsync(@(?), ?)")
    @ModifyExpressionValue(
            method = "reload",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private Supplier<T> prepareWithState(Supplier<T> supplier, PreparableReloadListener.SharedState sharedState) {
        return () -> StateHolder.getWithState(supplier, sharedState);
    }

    @Definition(id = "thenAcceptAsync", method = "Ljava/util/concurrent/CompletableFuture;thenAcceptAsync(Ljava/util/function/Consumer;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;")
    @Expression("?.thenAcceptAsync(@(?), ?)")
    @ModifyExpressionValue(
            method = "reload",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private Consumer<T> applyWithState(Consumer<T> original, PreparableReloadListener.SharedState sharedState) {
        return value -> StateHolder.runWithState(() -> original.accept(value), sharedState);
    }
}
