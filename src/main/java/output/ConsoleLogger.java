package output;

import org.jnativehook.GlobalScreen;

import java.util.logging.Logger;

import static java.util.logging.Level.OFF;

public final class ConsoleLogger {

    public static void disableLog() {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(OFF);
        logger.setUseParentHandlers(false);
    }
}
