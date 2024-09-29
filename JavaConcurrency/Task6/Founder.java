package Task6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public final class Founder {
    private final List<Runnable> workers;

    public Founder(final Company company) {
        this.workers = new ArrayList<>(company.getDepartmentsCount());
        CyclicBarrier BARRIER = new CyclicBarrier(company.getDepartmentsCount(), company::showCollaborativeResult);

        for (int i = 0; i < company.getDepartmentsCount(); i++) {
            Department department = company.getFreeDepartment(i);
            this.workers.add(new Thread(() -> {
                System.out.println("Starting calculations for department #" + department.getIdentifier());
                department.performCalculations();
                try {
                    BARRIER.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            }));
        }
    }

    public void start() {
        for (final Runnable worker : workers) {
            new Thread(worker).start();
        }
    }
}
