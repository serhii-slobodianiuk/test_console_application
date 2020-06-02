package SourceData;

import java.nio.file.Path;

public class SourceData {

    private Path path;
    private Long countValue;

    public SourceData(Path path, Long countValue) {
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