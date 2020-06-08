import keyboard.GlobalKeyListener;
import source.Arguments;
import source.FileUtils;
import statistics.CountStatistics;
import statistics.CountStatisticsImpl;
import statistics.StatisticData;

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

        CountStatistics countable = new CountStatisticsImpl(executor, paths);
        countable.statisticsCounter();

        Map<Path, StatisticData> statistics = countable.getStatisticsResult();

        Report.create(statistics).print();
        Report.create(statistics).saveCsv(destFileName);

        executor.shutdown();
        GlobalKeyListener.close();
    }
}