package cl.niclabs.skandium.impl.skandium;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Skandium {
    private final ExecutorService executorService;

    public Skandium() {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public Skandium(int numberOfThreads) {
        executorService = Executors.newFixedThreadPool(numberOfThreads);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void shutdown() {
        this.executorService.shutdown();
    }
}
