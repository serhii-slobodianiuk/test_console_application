package Util;

import java.nio.file.Path;
import java.util.Map;

public class Printer {

    public static void printDirectory(Map<Path, Long> pathsAndFilesCount) {
        int rowIndex = 0;
        System.out.printf("%-10s| %-11s| %s\n", "Row Index", "File count", "SourceRead");
        System.out.println("-------------------------------");
        for (Path path : pathsAndFilesCount.keySet()) {
            Long fileCount = pathsAndFilesCount.get(path);
            rowIndex++;
            System.out.printf("%-10d| %-11d| %s\n", rowIndex, fileCount, path);
        }
    }
}
