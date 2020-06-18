package output;

import statistics.PathCount;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Map;

public final class Report {

    private final Map<Path, PathCount> statistics;

    private Report(Map<Path, PathCount> statistics) {
        this.statistics = statistics;
    }

    public static Report create(Map<Path, PathCount> statistics) {
        return new Report(statistics);
    }

    public void saveCsv(String destination) {
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

    public void print() {
        int rowIndex = 0;
        System.out.printf("\n" + "%-10s| %-11s| %s\n", "Row Index", "File count", "Path");
        System.out.println("-------------------------------");
        for (Path path : statistics.keySet()) {
            PathCount result = statistics.get(path);
            rowIndex++;
            System.out.printf("%-10d| %-11d| %s\n", rowIndex, result.getCountValue(), path);
        }
    }
}