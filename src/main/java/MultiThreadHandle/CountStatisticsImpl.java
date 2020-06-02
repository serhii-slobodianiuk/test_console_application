package MultiThreadHandle;

import SourceData.SourceData;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


public class CountStatisticsImpl implements CountStatistics {

    private final ExecutorService executor;
    private List<Path> userSourcePaths;
    private Map<Path, Long> statisticsResult;

    public CountStatisticsImpl(ExecutorService executor, List<Path> userSourcePaths) {
        this.executor = executor;
        this.userSourcePaths = userSourcePaths;
        this.statisticsResult = new ConcurrentHashMap<>(userSourcePaths.size());
    }

    @Override
    public Map<Path, Long> getStatisticsResult() {
        return statisticsResult;
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
                statisticsResult.put(path, fileCountValue);

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
