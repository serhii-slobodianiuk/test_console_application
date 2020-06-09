package statistics;

import java.nio.file.Path;

public class DataRecord {

    private final Path path;
    private Long countValue;

    DataRecord(Path path, Long countValue) {
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