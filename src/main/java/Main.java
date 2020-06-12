import keyboard.GlobalKeyListener;
import source.Arguments;
import source.FileUtils;
import statistics.StatisticsAuditor;
import statistics.StatisticsAuditorImpl;
import statistics.PathCountRecord;

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

        List<Path> paths = FileUtils.create(arguments).read(sourceFileName);

        GlobalKeyListener.escListener(executor::shutdownNow);

        StatisticsAuditor countable = new StatisticsAuditorImpl(executor, paths);
        countable.computeStatisticsService();


        Map<Path, PathCountRecord> statistics = countable.getStatistics();

        Report.create(statistics).print();
        Report.create(statistics).saveCsv(destFileName);

        executor.shutdown();
        GlobalKeyListener.close();
    }
}