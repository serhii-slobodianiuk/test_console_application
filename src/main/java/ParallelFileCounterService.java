import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ParallelFileCounterService implements MultiThreadCountable {

    private final Executor executor;
    private List<String> userSourcePaths;
    private Map<String, Long> pathsAndFilesCount;

    ParallelFileCounterService(ExecutorService executor,
                               List<String> userSourcePaths,
                               Map<String, Long> pathsAndFilesCount) {
        this.executor = executor;
        this.userSourcePaths = userSourcePaths;
        this.pathsAndFilesCount = pathsAndFilesCount;
    }

    @Override
    public void createMultiThreadCount() {

        CompletionService<Long> cs = new ExecutorCompletionService<>(executor);
        Future<Long> futureCountResult = null;

        for (final String path : userSourcePaths) {
            cs.submit(new FileCount(path));

            try {
                futureCountResult = cs.take();
                Long fileCountValue;
                fileCountValue = futureCountResult.get();
                pathsAndFilesCount.put(path, fileCountValue);

            } catch (ExecutionException e) {
                System.err.println("Internal exception: " + e.getMessage());
            } catch (InterruptedException e) {
                futureCountResult.cancel(true);
                System.out.println("\n" + "Current thread was interrupted/cancelled");
            }
        }
    }
}
