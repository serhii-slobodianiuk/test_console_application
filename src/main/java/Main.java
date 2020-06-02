import keyboard.GlobalKeyListener;
import statistics.CountStatistics;
import statistics.CountStatisticsImpl;
import source.Arguments;
import source.FileUtils;
import util.ConsoleLogger;
import util.Report;

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

        List<Path> paths = FileUtils.create(arguments).readFrom(sourceFileName);

        GlobalKeyListener.escListener(executor::shutdownNow);

        CountStatistics countable = new CountStatisticsImpl(executor, paths);
        countable.fileCountStatistics();

        Map<Path, Long> statistics = countable.getStatistics();

        Report.create(statistics).print();
        Report.create(statistics).saveCsv(destFileName);

        executor.shutdown();
        System.exit(0);

    }
}