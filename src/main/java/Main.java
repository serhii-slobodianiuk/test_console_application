import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

//    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {

        //check number of arguments
        SourceValidator.checkNumberOfArgument(args);

        // validate source file
        final String sourceFileName = args[0];
        SourceValidator.validateSourceFile(new File(sourceFileName));

        // validate dest file
        final String destFileName = args[1];
        final File destFile = new File(destFileName);
        SourceValidator.validateDestFile(destFile);

        //ensure parent dirs are created
        destFile.getParentFile().mkdirs();

        List<String> userSourcePaths = SourceReader.getPathsFromSourceFile(sourceFileName);
        Map<String, Long> pathsAndFilesCount = new ConcurrentHashMap<>(userSourcePaths.size());

        ExecutorService executor = Executors.newCachedThreadPool();

        MultiThreadCountable countable =
                new ParallelFileCounterService(executor, userSourcePaths, pathsAndFilesCount);
        countable.createMultiThreadCount();

        FormatConverter.createCSV(destFileName, pathsAndFilesCount);
        printDirectory(pathsAndFilesCount);
        executor.shutdown();
    }

    private static void printDirectory(Map<String, Long> pathsAndFilesCount) {
        int rowIndex = 0;

        System.out.printf("%-10s| %-11s| %s\n", "Row Index", "File count", "Paths");
        System.out.println("-------------------------------");
        for (String path : pathsAndFilesCount.keySet()) {
            Long fileCount = pathsAndFilesCount.get(path);
            rowIndex++;
            System.out.printf("%-10d| %-11d| %s\n", rowIndex, fileCount, path);
        }
    }

}