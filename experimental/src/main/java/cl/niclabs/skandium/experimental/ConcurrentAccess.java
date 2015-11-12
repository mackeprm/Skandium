package cl.niclabs.skandium.experimental;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.function.IntSupplier;

public class ConcurrentAccess {

    public static void main(String[] args) throws InterruptedException {

        final Foo counter = new Counter();

        final IntSupplier increaseCounter = counter::getAsInt;

        final Executor x1 = new Executor(increaseCounter);
        final Executor x2 = new Executor(increaseCounter);

        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.submit(x1);
        pool.submit(x2);

        pool.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Counter:" + counter.getCounter());

    }


    private interface Foo {
        int getAsInt();

        int getCounter();
    }

    private static class Executor implements Runnable {
        private final IntSupplier f;

        public Executor(final IntSupplier f) {
            this.f = f;
        }

        public void run() {
            for (int i = 0; i < 10_000; i++) {
                f.getAsInt();
            }
        }
    }

    private static class Counter implements Foo {
        private int counter = 0;

        public synchronized int getAsInt() {
            counter++;
            return counter;
        }

        public int getCounter() {
            return counter;
        }
    }


}
