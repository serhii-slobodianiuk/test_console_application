package MultiThreadHandle;

import SourceData.SourceData;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class FileCount implements Callable<SourceData>, Countable {
    private final Path path;

    FileCount(Path path) {
        this.path = path;
    }

    public Long count(Path path) {

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
            }
            return countResult;
        } else {
            return countResult;
        }
    }

    public SourceData call() {
        return new SourceData(path, count(path));
    }
}
