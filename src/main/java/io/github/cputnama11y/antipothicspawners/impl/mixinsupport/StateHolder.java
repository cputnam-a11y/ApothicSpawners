package io.github.cputnama11y.antipothicspawners.impl.mixinsupport;

import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.Optional;
import java.util.function.Supplier;

public class StateHolder {
    private static final ThreadLocal<Optional<PreparableReloadListener.SharedState>> STATE = ThreadLocal.withInitial(Optional::empty);

    public static Optional<PreparableReloadListener.SharedState> currentState() {
        return STATE.get();
    }

    public static <T> T getWithState(Supplier<T> action, PreparableReloadListener.SharedState state) {
        var current = STATE.get();
        try {
            STATE.set(Optional.of(state));
            return action.get();
        } finally {
            if (current.isPresent()) STATE.set(current);
            else STATE.remove();
        }
    }

    public static void runWithState(Runnable action, PreparableReloadListener.SharedState state) {
        var current = STATE.get();
        try {
            STATE.set(Optional.of(state));
            action.run();
        } finally {
            if (current.isPresent()) STATE.set(current);
            else STATE.remove();
        }
    }
}
