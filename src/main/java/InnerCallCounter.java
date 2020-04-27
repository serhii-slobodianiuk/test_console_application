import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class InnerCallCounter implements MultiThreadCountable {

    @Override
    public void createMultiThreadCount(List<String> userSourcePaths,
                                       ExecutorService executor,
                                       Map<String, Long> pathsAndFilesCount) {

        for (String path : userSourcePaths) {
            Future<?> futureResult = executor.submit(() -> {
                pathsAndFilesCount.put(path, SourceReader.doCount(path));
                return null;
            });

            try {
                futureResult.get(100, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                System.out.println("\n" + "Current thread was interrupted/cancelled");
                futureResult.cancel(true);
                System.out.println("Thread has been cancelled");
            } catch (ExecutionException e) {
                System.err.println("Internal exception: " + e.getMessage());
            } catch (TimeoutException e) {
                futureResult.cancel(true);
                System.out.println("Counting has timed out and cancelled");
            }
        }
    }
}

