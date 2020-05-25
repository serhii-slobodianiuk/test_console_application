package SourceData;/*
 * A directory to validate an input data from specified by the user.
 * */

import java.io.File;

import static com.google.common.base.Preconditions.checkArgument;

public class SourceValidator {

    public static void checkNumberOfArgument(String[] args) {
        checkArgument(args.length == 2,
                "Wrong number of arguments");
    }

    public static boolean validateSourceFile(File file) {
        if (!file.exists() || !file.canRead()) {
            System.err.println(file + "...... file doesn't exist or unreadable");
            return false;
        }
        return true;
    }

    public static void validateDestFile(File file) {
        if (!file.exists() || !file.canRead()) {
            System.err.println("Destination isn't file or is unreadable");
        }
    }
}
