import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public interface MultiThreadCountable {
    void createMultiThreadCount(List<String> userSourcePaths, ExecutorService executor,
                                Map<String, Long> pathsAndFilesCount);
}
