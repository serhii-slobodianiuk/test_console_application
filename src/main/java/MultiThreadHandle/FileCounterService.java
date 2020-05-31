package MultiThreadHandle;

import SourceData.SourceData;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


public class FileCounterService implements MultiThreadable {

    private final ExecutorService executor;
    private List<Path> userSourcePaths;
    private Map<Path, Long> pathsAndFilesCount;

    public FileCounterService(ExecutorService executor, List<Path> userSourcePaths) {
        this.executor = executor;
        this.userSourcePaths = userSourcePaths;
        this.pathsAndFilesCount = new ConcurrentHashMap<>(userSourcePaths.size());
    }

    public Map<Path, Long> getPathsAndFilesCount() {
        return pathsAndFilesCount;
    }

    @Override
    public void fileCountStatistics() {

        CompletionService<SourceData> cs = new ExecutorCompletionService<>(executor);

        for (Path path : userSourcePaths) {
            cs.submit(new FileCount(path));
        }

        for (int i = 0; i < userSourcePaths.size(); i++) {

            Future<SourceData> result = null;
            SourceData fileCountResult;
            Path path;
            Long fileCountValue;

            try {
                result = cs.take();
                fileCountResult = result.get();
                path = fileCountResult.getPath();
                fileCountValue = fileCountResult.getCountValue();
                pathsAndFilesCount.put(path, fileCountValue);

            } catch (InterruptedException e) {
                if (result != null) {
                    result.cancel(true);
                    System.out.println("\n" + "Current thread was interrupted/cancelled");
                }
            } catch (ExecutionException e) {
                System.err.println("Internal exception: " + e.getMessage());
            }
        }

    }
}
