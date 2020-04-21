import java.util.concurrent.Callable;

public class CallableTask implements Callable<FileCount> {
    private final String path;

    public CallableTask(String path) {
        this.path = path;
    }

    @Override
    public FileCount call() {
        return new FileCount(path);
    }
}
