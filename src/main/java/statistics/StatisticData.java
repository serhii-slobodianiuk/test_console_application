package statistics;

import java.nio.file.Path;

public class StatisticData {

    private final Path path;
    private Long countValue;

    StatisticData(Path path, Long countValue) {
        this.path = path;
        this.countValue = countValue;
    }

    public Path getPath() {
        return path;
    }

    public Long getCountValue() {
        return countValue;
    }
}