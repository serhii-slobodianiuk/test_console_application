package keyboard;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import static org.jnativehook.GlobalScreen.addNativeKeyListener;

public final class GlobalKeyListener implements NativeKeyListener {

    private Runnable eventHandler;

    private GlobalKeyListener(Runnable eventHandler) {
        this.eventHandler = eventHandler;
    }

    public static void escListener(Runnable eventHandler) {

        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            throw new IllegalStateException();
        }
        addNativeKeyListener(new GlobalKeyListener(eventHandler));
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
        if (event.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            whenPressed(eventHandler);
            closeHook();
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent event) {
        // nothing to do
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent event) {
        // nothing to do
    }

    private void whenPressed(Runnable eventHandler) {
        this.eventHandler = eventHandler;
        this.eventHandler.run();
    }

    public static void closeHook() {
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            throw new IllegalStateException();
        }
    }
}