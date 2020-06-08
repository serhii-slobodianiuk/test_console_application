import statistics.StatisticData;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public final class Report {

    private final Map<?, ?> statistics;

    private Report(Map<?, ?> statistics) {
        this.statistics = statistics;
    }

    public static Report create(Map<?, ?> statistics) {
        return new Report(statistics);
    }

    public void saveCsv(String destination) {
        for (Object path : statistics.keySet()) {
            StatisticData result = (StatisticData) statistics.get(path);
            try (FileWriter fw = new FileWriter(destination, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.print(path);
                out.print(";");
                out.println(result.getCountValue());
//                out.println(";");
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void print() {
        int rowIndex = 0;
        System.out.printf("%-10s| %-11s| %s\n", "Row Index", "File count", "FileUtils");
        System.out.println("-------------------------------");
        for (Object path : statistics.keySet()) {
            StatisticData result = (StatisticData) statistics.get(path);
            rowIndex++;
            System.out.printf("%-10d| %-11d| %s\n", rowIndex, result.getCountValue(), path);
        }
    }
}
