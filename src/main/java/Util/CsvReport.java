package Util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Map;

public class CsvReport {

    public static void save(String destFile, Map<Path, Long> pathsAndFilesCount) {
        for (Path path : pathsAndFilesCount.keySet()) {
            Long fileCount = pathsAndFilesCount.get(path);
            try (FileWriter fw = new FileWriter(destFile, true);
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
}