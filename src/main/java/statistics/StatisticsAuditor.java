package statistics;

import java.nio.file.Path;
import java.util.Map;

public interface StatisticsAuditor {

    void startStatisticsCompute();

    void saveStatisticsCompute(PathCount result);

    Map<Path, PathCount> getStatistics();
}