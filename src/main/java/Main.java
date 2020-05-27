import KeyListenerHandle.KeyListenerService;
import MultiThreadHandle.ParallelFileCounterService;
import SourceData.Arguments;
import SourceData.Path;
import Util.ConsoleLogger;
import Util.CsvReport;
import Util.Printer;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {

        Arguments arguments = new Arguments(args);

        arguments.checkNumberOfArgument();
        String sourceFileName = String.valueOf(arguments.sourceFile());
        String destFileName = String.valueOf(arguments.destinationFile());
        arguments.ensureParentDirExists(new File(destFileName));

        ConsoleLogger.disableLog();

        List<String> userSourcePaths = Path.readFrom(arguments, sourceFileName);

        KeyListenerService keyListener = new KeyListenerService(executor);
        keyListener.listenToESC();

        ParallelFileCounterService countable = new ParallelFileCounterService(executor, userSourcePaths);
        countable.createMultiThreading();

        Map<String, Long> pathsAndFilesCount = countable.getPathsAndFilesCount();

        Printer.printDirectory(pathsAndFilesCount);

        CsvReport.saveTo(destFileName, pathsAndFilesCount);

        executor.shutdown();
        System.exit(0);
    }
}