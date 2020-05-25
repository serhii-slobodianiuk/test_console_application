import SourceData.SourceReader;
import SourceData.SourceValidator;
import Util.FormatConverter;
import Util.Printer;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {

    private static final ExecutorService executor = Executors.newCachedThreadPool();

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

        InterruptThreadListener listener = new InterruptThreadListener(executor);
        executor.submit(listener);

        ParallelFileCounterService countable = new ParallelFileCounterService(executor, userSourcePaths);
        countable.createMultiThread();

        Map<String, Long> pathsAndFilesCount = countable.getPathsAndFilesCount();
        FormatConverter.createCSV(destFileName, pathsAndFilesCount);
        executor.shutdown();

    }
}