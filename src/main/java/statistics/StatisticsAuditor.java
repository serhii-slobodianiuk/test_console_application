package statistics;

import java.nio.file.Path;
import java.util.Map;

public interface StatisticsAuditor {

    void conspectusStatistics();

    Map<Path, PathCountRecord> getStatisticsInference();
}