package statistics;

import java.nio.file.Path;
import java.util.Map;

public interface CountStatistics {

    void statisticsCounter();

    Map<Path, StatisticData> getStatisticsResult();
}