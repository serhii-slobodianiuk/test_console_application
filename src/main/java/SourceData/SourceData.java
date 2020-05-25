package SourceData;

public class SourceData {

    private String path;
    private Long countValue;

    public SourceData(String path, Long countValue) {
        this.path = path;
        this.countValue = countValue;
    }

    public String getPath() {
        return path;
    }

    public Long getCountValue() {
        return countValue;
    }
}