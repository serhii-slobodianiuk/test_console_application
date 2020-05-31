import KeyListenerHandle.GlobalKeyListener;
import MultiThreadHandle.FileCounterService;
import SourceData.Arguments;
import SourceData.SourceRead;
import Util.ConsoleLogger;
import Util.CsvReport;
import Util.Printer;

import java.io.File;
import java.nio.file.Path;
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

        List<Path> paths = SourceRead.readFrom(arguments, sourceFileName);

        GlobalKeyListener.listenToEsc(Main::shutDownNowExecutor);

        FileCounterService countable = new FileCounterService(executor, paths);
        countable.fileCountStatistics();

        Map<Path, Long> pathsAndFilesCount = countable.getPathsAndFilesCount();

        Printer.printDirectory(pathsAndFilesCount);

        CsvReport.save(destFileName, pathsAndFilesCount);

        executor.shutdown();
        System.exit(0);
    }

    private static void shutDownNowExecutor() {
        executor.shutdownNow();
    }
}