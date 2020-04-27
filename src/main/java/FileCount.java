import java.io.File;

public class FileCount {
    private String path;

    public FileCount(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public Long getCountFiles() {
        return doCount(path);
    }

    private Long doCount(String path) {

        long count = 0;

        File f = new File(path);
        File[] files = f.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    count += doCount(file.getAbsolutePath());
                } else {
                    count++;
                }
            }
        }
        return count;
    }
}
