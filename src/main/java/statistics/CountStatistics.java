package statistics;

import java.nio.file.Path;
import java.util.Map;

public interface CountStatistics {

    void countStatistics();

    Map<Path, StatisticData> getStatisticsResult();
}