package statistics;

import java.nio.file.Path;

class StatisticData {

    private final Path path;
    private Long countValue;

    StatisticData(Path path, Long countValue) {
        this.path = path;
        this.countValue = countValue;
    }

    Path getPath() {
        return path;
    }

    Long getCountValue() {
        return countValue;
    }
}