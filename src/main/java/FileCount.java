import java.io.File;
import java.util.concurrent.Callable;

public class FileCount implements Callable<SourceData>, Countable {
    private final String path;

    FileCount(String path) {
        this.path = path;
    }

    @Override
    public Long count(String path) {

        long countResult = 0;

        if (!Thread.currentThread().isInterrupted()) {
            File f = new File(path);
            File[] files = f.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        countResult += count(file.getAbsolutePath());
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

    @Override
    public SourceData call() {
        return new SourceData(path, count(path));
    }
}
