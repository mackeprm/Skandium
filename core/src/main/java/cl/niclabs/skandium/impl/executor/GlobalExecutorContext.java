package cl.niclabs.skandium.impl.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GlobalExecutorContext {
    private static GlobalExecutorContext context = new GlobalExecutorContext();

    private final ExecutorService executorService;

    public GlobalExecutorContext() {
        executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }


    public static ExecutorService skandiumExecutor() {
        return getContext().executorService;
    }

    //TODO instance creation has to be synchronized. therefore checking it here is not sufficient
    private static GlobalExecutorContext getContext() {
        if (context == null) {
            throw new IllegalStateException("trying to use uninitialized Skandium Instance. Please use one of the initialize methods");
        }
        return context;
    }
}
