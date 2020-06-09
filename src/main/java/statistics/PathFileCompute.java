package statistics;

import java.nio.file.Path;

public class PathFileCompute {

    private final Path path;
    private Long countValue;

    PathFileCompute(Path path, Long countValue) {
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