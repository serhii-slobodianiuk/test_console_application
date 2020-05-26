package Util;

import org.jnativehook.GlobalScreen;

import java.util.logging.Logger;

import static java.util.logging.Level.OFF;

public class ConsoleLogger {

    public static void consoleLogger(){
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(OFF);
        logger.setUseParentHandlers(false);
    }
}
