package source;

import java.io.File;

import static com.google.common.base.Preconditions.checkArgument;

public class Arguments {

    private String[] args;

    public Arguments(String[] args) {
        this.args = args;
    }

    public void checkNumberOfArgument() {
        checkArgument(args.length == 2,
                "Wrong number of arguments",
                new IllegalArgumentException());
    }

    public File validateFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println(file + "...... file doesn't exist");
            throw new IllegalArgumentException();

        } else if (!file.canRead()) {
            System.err.println(file + "...... file is unreadable");
            throw new IllegalArgumentException();
        }
        return file;
    }

    public File sourceFile() {
        return validateFile(args[0]);
    }

    public File destinationFile() {
        return validateFile(args[1]);
    }

    public void ensureParentDirExists(File destinationFile) {
        destinationFile.getParentFile().mkdirs();
    }
}
