import java.io.File;
import java.util.concurrent.Callable;

public class FileCount implements Callable<Long>, Countable {
    private final String path;

    FileCount(String path) {
        this.path = path;
    }

    @Override
    public Long count(String path) {

        long count = 0;
        while (!Thread.currentThread().isInterrupted()) {
            File f = new File(path);
            File[] files = f.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        count += count(file.getAbsolutePath());
                    } else {
                        count++;
                    }
                }
            }
            return count;
        }
        System.out.println(" Current thread is interrupted");
        return count;
    }

    @Override
    public Long call() {
        return count(path);
    }
}
