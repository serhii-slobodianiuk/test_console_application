import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ParallelFileCounterService implements MultiThreadCountable {

    @Override
    public void createMultiThreadCount(List<String> userSourcePaths,
                                       ExecutorService executor,
                                       Map<String, Long> pathsAndFilesCount) {

        CompletionService<FileCount> cs = new ExecutorCompletionService<>(executor);

        List<CallableTask> taskList = new ArrayList<>();

        for (String path : userSourcePaths) {
            CallableTask task = new CallableTask(path);
            taskList.add(task);
        }

        for (CallableTask r : taskList) {
            cs.submit(r);
            Future<FileCount> future = null;

            try {
                future = cs.take();
                FileCount fileCountResult;
                fileCountResult = future.get(100, TimeUnit.MILLISECONDS);
                pathsAndFilesCount.put(fileCountResult.getPath(), fileCountResult.getCountFiles());
            } catch (InterruptedException e) {
                if (future != null) {
                    future.cancel(true);
                }
                System.out.println("Current thread was interrupted/cancelled");
            } catch (ExecutionException e) {
                System.err.println("Internal exception: " + e.getMessage());
            } catch (TimeoutException e) {
                future.cancel(true);
                System.out.println("Counting has timed out and cancelled");
            }
        }
    }
}
