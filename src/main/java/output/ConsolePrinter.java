package output;

import statistics.PathCount;

import java.nio.file.Path;
import java.util.Map;

public class ConsolePrinter implements Reporter {

    private final Map<Path, PathCount> statistics;

    public ConsolePrinter(Map<Path, PathCount> statistics) {
        this.statistics = statistics;
    }

    @Override
    public void report() {
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