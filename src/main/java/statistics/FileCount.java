package statistics;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class FileCount implements Callable<DataRecord> {
    private final Path path;

    FileCount(Path path) {
        this.path = path;
    }

    private Long count(Path path) {
        long countResult = 0;
        if (!Thread.currentThread().isInterrupted()) {
            File f = new File(String.valueOf(path));
            File[] files = f.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        countResult += count(Paths.get(file.getAbsolutePath()));
                    } else {
                        countResult++;
                    }
                }
                return countResult;
            }
        }
        return countResult;
    }

    public DataRecord call() {
        return new DataRecord(path, count(path));
    }
}