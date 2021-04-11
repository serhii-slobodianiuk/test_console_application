package output;

import statistics.PathCount;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Map;

public class CSVConvertor implements Reporter {

    private final String destination;
    private final Map<Path, PathCount> statistics;

    public CSVConvertor(String destination1, Map<Path, PathCount> statistics) {
        this.destination = destination1;
        this.statistics = statistics;
    }

    @Override
    public void report() {
        for (Path path : statistics.keySet()) {
            PathCount result = statistics.get(path);
            try (FileWriter fw = new FileWriter(destination, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.print(path);
                out.print(";");
                out.println(result.getCountValue());
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
