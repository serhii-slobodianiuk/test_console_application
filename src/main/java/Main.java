import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


public class Main {

    private static ExecutorService executor = Executors.newCachedThreadPool();

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

        SourceReader sourceReader = new SourceReader();
        List<String> userSourcePaths = sourceReader.getPathsFromSourceFile(sourceFileName);
        Map<String, Long> pathsAndFilesCount = new HashMap<>();

        List<CallableTask> taskList = new ArrayList<>();
        for (String s : userSourcePaths) {
            CallableTask task = new CallableTask(s);
            taskList.add(task);
        }

        List<Future<FileCount>> resultList = null;
        try {
            resultList = executor.invokeAll(taskList);
        } catch (InterruptedException e) {
//            e.printStackTrace();
        }

        if (resultList != null) {
            for (Future<FileCount> r : resultList) {

                try {
                    FileCount result;
                    result = r.get(100, TimeUnit.MILLISECONDS);
                    pathsAndFilesCount.put(result.getPath(), result.getCountFiles());
                } catch (InterruptedException e) {
                    r.cancel(true);
                    System.out.println("Current thread was interrupted/cancelled");
                } catch (ExecutionException e) {
                    System.err.println("Internal exception: " + e.getMessage());
                } catch (TimeoutException e) {
                    r.cancel(true);
                    System.out.println("Counting has timed out and cancelled");
                }
            }
        }

//        for (String path : userSourcePaths) {
//            SourceValidator.validateSourceFile(new File(path));
//            if (!executor.isShutdown()) {
//                Future<?> futureResult = executor.submit(() -> {
//                    pathsAndFilesCount.put(path, sourceReader.doCount(path));
//                    return null;
//                });
//
//                try {
//                    futureResult.get(1500, TimeUnit.MILLISECONDS);
//                } catch (InterruptedException e) {
//                    System.out.println("\n" + "Current thread was interrupted/cancelled");
//                    futureResult.cancel(true);
//                    System.out.println("Thread has been cancelled");
//                } catch (ExecutionException e) {
//                    System.err.println("Internal exception: " + e.getMessage());
//                } catch (TimeoutException e) {
//                    futureResult.cancel(true);
//                    System.out.println(path);
//                    System.out.println("Counting has timed out and cancelled");
//                    System.out.println();
//                }
//            }
//        }


        FormatConverter.createCSV(destFileName, pathsAndFilesCount);
        printDirectory(pathsAndFilesCount);
        executor.shutdown();
    }

    private static void printDirectory(Map<String, Long> pathsAndFilesCount) {
        int rowIndex = 0;

        System.out.printf("%-10s| %-11s| %s\n", "Row Index", "File Count", "Paths");
        System.out.println("-------------------------------");
        for (String path : pathsAndFilesCount.keySet()) {
            Long fileCount = pathsAndFilesCount.get(path);
            rowIndex++;
            System.out.printf("%-10d| %-11d| %s\n", rowIndex++, fileCount, path);
        }
    }
}