import java.io.File;

public class FileCount {
    private String path;

    public FileCount(String path) {
        this.path = path;
    }

    public String getPath() {
        return String.valueOf(validateSourceFile(path));
    }

    public Long getCountFiles() {
        return doCount(path);
    }

    private File validateSourceFile(String path) {
        File file = new File(path);
        if (!file.exists() || !file.canRead()) {
            System.err.println("Source file doesn't exist or is unreadable");
        }
        return file;
    }

    private long doCount(String path) {
        long count = 0;
        File f = new File(path);
        File[] files = f.listFiles();

        while (!Thread.currentThread().isInterrupted()) {
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        count += doCount(file.getAbsolutePath());
                    } else {
                        count++;
                    }
                }
            }
            return count;
        }
        return count;

    }
}
