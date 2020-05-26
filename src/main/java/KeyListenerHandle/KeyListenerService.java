package KeyListenerHandle;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.concurrent.ExecutorService;

public class KeyListenerService {
    private final ExecutorService executor;

    public KeyListenerService(ExecutorService executor) {
        this.executor = executor;
    }

    public void createListener() {
        try {

            GlobalScreen.registerNativeHook();

        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new GlobalKeyListener(executor));
    }
}
