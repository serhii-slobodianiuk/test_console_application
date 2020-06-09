package statistics;

import java.nio.file.Path;

public class PathCountRecord {

    private final Path path;
    private Long countValue;

    PathCountRecord(Path path, Long countValue) {
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