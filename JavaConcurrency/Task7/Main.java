package Task7;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            System.out.println("No arguments");
            return;
        }

        int threadsQuantity = Integer.parseInt(args[0]);
        ExecutorService executor = Executors.newFixedThreadPool(threadsQuantity);
        double[] partialSums = new double[threadsQuantity];

        for (int i = 0; i < threadsQuantity; i++) {
            final int threadIndex = i;
            executor.submit(() -> {
                double sum = 0.0;
                for (int j = threadIndex; j < 10000000; j += threadsQuantity) {
                    if (threadIndex % 2 == 0) {
                        sum += (1.0 / (2 * j + 1));
                    } else {
                        sum += (-1.0 / (2 * j + 1));
                    }
                }
                partialSums[threadIndex] = sum;
            });
        }

        executor.shutdown();
        var terminated = executor.awaitTermination(60, TimeUnit.SECONDS);
        if (!terminated) {
            System.out.println("Error! Some threads aren't terminated!");
            return;
        }

        System.out.println(Arrays.stream(partialSums).sum() * 4);
    }
}
