package io.github.cputnama11y.antipothicspawners.impl.util;

import io.github.cputnama11y.antipothicspawners.impl.AntipothicSpawners;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.IdentityHashMap;
import java.util.Optional;

public class OptionalFabricAttachmentDebugData {
    @SuppressWarnings("unchecked")
    public static Optional<DebugInfo> info(Player player) {
        try {
            var lookup = ((DebuggableAttachableEntity) player).antipothicSpawners$getPrivilagedLookup();
            AntipothicSpawners.LOGGER.info("Fetching Debug Info");
            var attachments = Arrays.stream(Entity.class.getDeclaredFields())
                    .filter(it -> it.getName().contains("dataAttachments") && it.getType().equals(IdentityHashMap.class))
                    .map(it -> getUnchecked(() -> (IdentityHashMap<Object, Object>) lookup.unreflectGetter(it).invokeExact(player)))
                    .reduce(new IdentityHashMap<>(), (m1, m2) -> Util.make(new IdentityHashMap<>(), m -> {
                        m.putAll(m1);
                        m.putAll(m2);
                    }));
            var info = new DebugInfo(attachments);
            AntipothicSpawners.LOGGER.info("Found Debug Info: {}", info);
            return Optional.of(info);
        } catch (Throwable t) {
            AntipothicSpawners.LOGGER.info("Failed to get DebugInfo: ", t);
            return Optional.empty();
        }
    }


    public record DebugInfo(IdentityHashMap<Object, Object> potentiallyFoundAttachments) {
    }

    public interface DebuggableAttachableEntity {
        MethodHandles.Lookup antipothicSpawners$getPrivilagedLookup();
    }

    interface ThrowySupplier<T> {
        T get() throws Throwable;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> RuntimeException rethrowUnchecked(Throwable t) throws T {
        throw (T) t;
    }

    private static <T> T getUnchecked(ThrowySupplier<T> getter) {
        try {
            return getter.get();
        } catch (Throwable t) {
            throw rethrowUnchecked(t);
        }
    }
}
