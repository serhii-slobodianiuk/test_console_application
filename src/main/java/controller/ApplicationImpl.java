package controller;

import keyboard.GlobalKeyListener;
import output.ConsoleLogger;
import output.Report;
import source.Arguments;
import source.FileUtils;
import statistics.StatisticsAuditor;
import statistics.StatisticsAuditorImpl;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ApplicationImpl implements Application {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private String[] args;

    ApplicationImpl(String[] args) {
        this.args = args;
    }

    @Override
    public void runApp() {

        var arguments = new Arguments(args);
        arguments.checkNumberOfArgument();
        var sourceFileName = String.valueOf(arguments.sourceFile());
        var destFileName = String.valueOf(arguments.destinationFile());
        arguments.ensureParentDirExists(new File(destFileName));

        var paths = FileUtils.create(arguments).read(sourceFileName);

        ConsoleLogger.disableLog();
        GlobalKeyListener.escListener(executor::shutdownNow);

        StatisticsAuditor auditor = new StatisticsAuditorImpl(executor, paths);
        auditor.startStatisticsCompute();

        var statistics = auditor.getStatistics();

        Report.create(statistics).print();
        Report.create(statistics).saveCsv(destFileName);

        executor.shutdown();
        GlobalKeyListener.close();
    }
}