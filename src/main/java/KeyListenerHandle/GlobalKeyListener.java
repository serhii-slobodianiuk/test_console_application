package KeyListenerHandle;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.concurrent.ExecutorService;

public class GlobalKeyListener implements NativeKeyListener {

    private final ExecutorService executor;

    GlobalKeyListener(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent event) {
        // nothing to do
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {

        if (event.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                executor.shutdownNow();
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent event) {
        // nothing to do
    }
}