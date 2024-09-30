package Task8;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static Double count(int counter, int threadIndex) {
        if (threadIndex % 2 == 0) {
            return 1.0 / (2 * counter + 1);
        } else {
            return -1.0 / (2 * counter + 1);
        }
    }

    public static void main(final String[] args) {
        if (args.length < 1) {
            System.out.println("No arguments");
            return;
        }

        List<Thread> threads = new ArrayList<>();
        int threadsQuantity = Integer.parseInt(args[0]);
        Map<Integer, Double> partialSums = new LinkedHashMap<>(threadsQuantity);
        AtomicInteger atomicInt = new AtomicInteger(0);

        Runtime.getRuntime().addShutdownHook(
            new Thread(
                () -> {
                    threads.forEach(Thread::interrupt);
                    while (threads.stream().anyMatch(Thread::isAlive)){}
                    System.out.println(partialSums.values().stream().mapToDouble(Double::doubleValue).sum() * 4);
                }
            )
        );

        for (int i = 0; i < threadsQuantity; i++) {
            final int threadIndex = i;
            threads.add(
                new Thread(
                    () -> {
                        double sum = 0.0;
                        int counter = threadIndex;
                        int operationsCount = 0;
                        while (!Thread.currentThread().isInterrupted()) {
                            sum += count(counter, threadIndex);
                            counter += threadsQuantity;
                            operationsCount ++;

                            if (operationsCount > atomicInt.get()) {
                                atomicInt.lazySet(operationsCount);
                            }
                        }

                        int operationsDelta = atomicInt.get() - operationsCount;
                        if (operationsDelta > 0) {
                            for (int k = operationsCount; k < atomicInt.get(); k += threadsQuantity) {
                                sum += count(k, threadIndex);
                            }
                        }

                        partialSums.put(threadIndex, sum);
                    }
                )
            );
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}
