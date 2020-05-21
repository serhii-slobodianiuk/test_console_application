import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class InterruptThreadListenerService implements MultiThreadable {

    private final ExecutorService executor;

    InterruptThreadListenerService(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void createMultiThread() {
        Future result = executor.submit(new InterruptThreadListener(executor));
        try {
            result.get();
        } catch (InterruptedException e) {
            result.cancel(true);
            System.out.println("\n" + "Current thread was interrupted/cancelled");
        } catch (ExecutionException e) {
            System.err.println("Internal exception in.....: " + e.getMessage());
        }
    }
}
