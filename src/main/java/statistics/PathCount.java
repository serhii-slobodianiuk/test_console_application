package statistics;

import java.nio.file.Path;

public class PathCount {

    private final Path path;
    private Long countValue;

    PathCount(Path path, Long countValue) {
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