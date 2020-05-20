public class SourceData {

    private String path;
    private Long countValue;

    SourceData(String path, Long countValue) {
        this.path = path;
        this.countValue = countValue;
    }

    String getPath() {
        return path;
    }

    Long getCountValue() {
        return countValue;
    }
}