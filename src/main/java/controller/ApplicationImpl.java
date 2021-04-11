package controller;

import keyboard.GlobalKeyListener;
import output.CSVConvertor;
import output.ConsoleLogger;
import output.ConsolePrinter;
import output.Reporter;
import source.ArgumentsHandler;
import source.FileUtils;
import statistics.PathCount;
import statistics.StatisticsAuditor;
import statistics.StatisticsAuditorImpl;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ApplicationImpl implements Application {
    private final ExecutorService executor = Executors.newCachedThreadPool();

    private String[] args;

    public ApplicationImpl(String[] args) {
        this.args = args;
    }

    @Override
    public void runApp() {

        ArgumentsHandler arguments = new ArgumentsHandler(args);
        arguments.checkNumberOfArgument();
        String sourceFileName = String.valueOf(arguments.sourceFile());
        String destFileName = String.valueOf(arguments.destinationFile());
        arguments.ensureParentDirExists(new File(destFileName));

        List<Path> paths = FileUtils.create(arguments).read(sourceFileName);

        ConsoleLogger.disableLog();

        GlobalKeyListener.escListener(executor::shutdownNow);

        StatisticsAuditor auditor = new StatisticsAuditorImpl(executor, paths);
        auditor.startStatisticsCompute();

        Map<Path, PathCount> statistics = auditor.getStatistics();

        Reporter printToConsole = new ConsolePrinter(statistics);
        printToConsole.report();

        Reporter csvConvertor = new CSVConvertor(destFileName, statistics);
        csvConvertor.report();

        executor.shutdown();
        GlobalKeyListener.close();
    }
}