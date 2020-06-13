package statistics;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import static java.util.Map.copyOf;

public class StatisticsAuditorImpl implements StatisticsAuditor {

    private final ExecutorService executor;
    private List<Path> paths;
    private Map<Path, PathCount> statistics;

    public StatisticsAuditorImpl(ExecutorService executor, List<Path> paths) {
        this.executor = executor;
        this.paths = paths;
        this.statistics = new ConcurrentHashMap<>(paths.size());
    }

    @Override
    public void startStatisticsCompute() {

        CompletionService<PathCount> cs = new ExecutorCompletionService<>(executor);

        for (Path path : paths) {
            cs.submit(new DirTreeFileCounter(path));
        }
        for (int i = 0; i < paths.size(); i++) {

            Future<PathCount> result = null;
            PathCount pathCount;

            try {
                result = cs.take();
                pathCount = result.get();
                saveStatisticsCompute(pathCount);
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
    public void saveStatisticsCompute(PathCount fileCountResult){
        Path path;
        Long fileCountValue;
        path = fileCountResult.getPath();
        fileCountValue = fileCountResult.getCountValue();
        statistics.put(path, new PathCount(path, fileCountValue));
    }

    @Override
    public Map<Path, PathCount> getStatistics() {
        return copyOf(statistics);
    }
}