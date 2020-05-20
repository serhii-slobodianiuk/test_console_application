import java.util.Scanner;
import java.util.concurrent.ExecutorService;

public class InterruptThreadListener implements Runnable {

    private final ExecutorService executor;

    public InterruptThreadListener(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void run() {
        scannerListener();
    }

    public void scannerListener() {
        // TODO: 19.05.2020 create Listener for stopping executor
        String s;
        do{
            Scanner scanner = new Scanner(System.in);
            s = scanner.nextLine();
        }while (!s.equals("q"));
        executor.shutdownNow();

    }
}