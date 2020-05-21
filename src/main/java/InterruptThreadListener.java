import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class InterruptThreadListener implements Runnable {

    private final ExecutorService executor;

    public InterruptThreadListener(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void run() {
        String s;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Waiting for entering \"q\"");

            // Scanner waiting for entering that's why thread can't stop
            s = scanner.nextLine();

        } while (!s.equals("q"));
        executor.shutdownNow();
    }
}