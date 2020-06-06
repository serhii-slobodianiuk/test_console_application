import keyboard.GlobalKeyListener;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import source.Arguments;
import source.FileUtils;
import statistics.CountStatistics;
import statistics.CountStatisticsImpl;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static Report.create;

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
        countable.fileCountStatistics();

        Map<Path, Long> statistics = countable.getStatistics();

        create(statistics).print();
        create(statistics).saveCsv(destFileName);

        executor.shutdown();

        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            throw new IllegalStateException();
        }
    }
}