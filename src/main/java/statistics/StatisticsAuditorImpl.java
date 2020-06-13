package statistics;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static java.util.Map.copyOf;

public class StatisticsAuditorImpl implements StatisticsAuditor {

    private final ExecutorService executor;
    private List<Path> paths;
    private Map<Path, PathCountRecord> statistics;

    public StatisticsAuditorImpl(ExecutorService executor, List<Path> paths) {
        this.executor = executor;
        this.paths = paths;
        this.statistics = new ConcurrentHashMap<>(paths.size());
    }

    @Override
    public void startStatisticsCompute() {

        CompletionService<PathCountRecord> cs = new ExecutorCompletionService<>(executor);

        for (Path path : paths) {
            cs.submit(new DirTreeFileCounter(path));
        }
        for (int i = 0; i < paths.size(); i++) {

            Future<PathCountRecord> result = null;
            PathCountRecord fileCountResult;

            try {
                result = cs.take();
                fileCountResult = result.get();
                saveStatisticsCompute(fileCountResult);
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

    @Override
    public void saveStatisticsCompute(PathCountRecord fileCountResult){
        Path path;
        Long fileCountValue;
        path = fileCountResult.getPath();
        fileCountValue = fileCountResult.getCountValue();
        statistics.put(path, new PathCountRecord(path, fileCountValue));
    }

    @Override
    public Map<Path, PathCountRecord> getStatistics() {
        return copyOf(statistics);
    }
}