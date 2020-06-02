package MultiThreadHandle;

import java.nio.file.Path;
import java.util.Map;

public interface CountStatistics {

    void fileCountStatistics();

    Map<Path, Long> getStatisticsResult();

}
