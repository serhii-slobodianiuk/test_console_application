package statistics;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class CountStatisticsImpl implements CountStatistics {

    private final ExecutorService executor;
    private List<Path> paths;
    private Map<Path, DataRecord> statistics;

    public CountStatisticsImpl(ExecutorService executor, List<Path> paths) {
        this.executor = executor;
        this.paths = paths;
        this.statistics = new ConcurrentHashMap<>(paths.size());
    }

    @Override
    public Map<Path, DataRecord> getStatistics() {
        return statistics;
    }

    @Override
    public void computeStatisticsService() {

        CompletionService<DataRecord> cs = new ExecutorCompletionService<>(executor);

        for (Path path : paths) {
            cs.submit(new FileCount(path));
        }
        for (int i = 0; i < paths.size(); i++) {

            Future<DataRecord> result = null;
            DataRecord fileCountResult;
            Path path;
            Long fileCountValue;

            try {
                result = cs.take();
                fileCountResult = result.get();
                path = fileCountResult.getPath();
                fileCountValue = fileCountResult.getCountValue();
                statistics.put(path, new DataRecord(path, fileCountValue));

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