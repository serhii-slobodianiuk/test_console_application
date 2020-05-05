/*
 * A directory to validate an input data from specified by the user.
 * */

import java.io.File;

import static com.google.common.base.Preconditions.checkArgument;

public class SourceValidator {

    static void checkNumberOfArgument(String[] args) {
        checkArgument(args.length == 2,
                "Wrong number of arguments");
    }

    static void validateSourceFile(File file) {
        if (!file.exists() || !file.canRead()) {
            System.err.println("Source file doesn't exist or is unreadable");
        }
    }

    static void validateDestFile(File file) {
        if (!file.exists() || !file.canRead()) {
            System.err.println("Destination isn't file or is unreadable");
        }
    }
}
