package Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Map;

public final class Report {

    private final Map<Path, Long> statisticsResult;

    private Report(Map<Path, Long> statisticsResult) {
        this.statisticsResult = statisticsResult;
    }

    public static Report create(Map<Path, Long> statisticsResult) {
        return new Report(statisticsResult);
    }

    public void saveCsv(String destination) {
        for (Path path : statisticsResult.keySet()) {
            Long fileCount = statisticsResult.get(path);
            try (FileWriter fw = new FileWriter(destination, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.print(path);
                out.print(";");
                out.println(fileCount);
//                out.println(";");
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void print() {
        int rowIndex = 0;
        System.out.printf("%-10s| %-11s| %s\n", "Row Index", "File count", "SourceRead");
        System.out.println("-------------------------------");
        for (Path path : statisticsResult.keySet()) {
            Long fileCount = statisticsResult.get(path);
            rowIndex++;
            System.out.printf("%-10d| %-11d| %s\n", rowIndex, fileCount, path);
        }
    }
}
