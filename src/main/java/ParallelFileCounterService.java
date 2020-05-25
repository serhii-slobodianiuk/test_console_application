import SourceData.SourceData;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


public class ParallelFileCounterService implements MultiThreadable {

    private final ExecutorService executor;
    private List<String> userSourcePaths;
    private Map<String, Long> pathsAndFilesCount;

    ParallelFileCounterService(ExecutorService executor, List<String> userSourcePaths) {
        this.executor = executor;
        this.userSourcePaths = userSourcePaths;
        this.pathsAndFilesCount = new ConcurrentHashMap<>(userSourcePaths.size());
    }

    public Map<String, Long> getPathsAndFilesCount() {
        return pathsAndFilesCount;
    }

    @Override
    public void createMultiThread() {

        CompletionService<SourceData> cs = new ExecutorCompletionService<>(executor);

        for (String path : userSourcePaths) {
            cs.submit(new FileCount(path));
        }

        for (int i = 0; i < userSourcePaths.size(); i++) {

            Future<SourceData> result = null;
            SourceData fileCountResult;
            String path;
            Long fileCountValue;

            try {
                result = cs.take();
                fileCountResult = result.get();
                path = fileCountResult.getPath();
                fileCountValue = fileCountResult.getCountValue();
                pathsAndFilesCount.put(path, fileCountValue);

            } catch (InterruptedException e) {
                result.cancel(true);
                System.out.println("\n" + "Current thread was interrupted/cancelled");
            } catch (ExecutionException e) {
                System.err.println("Internal exception: " + e.getMessage());
            }
        }

    }
}
