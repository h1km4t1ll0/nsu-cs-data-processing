package Task5;

class Printer implements Runnable {
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("I'm a printer " + i);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println("INTERRUPTED");
                return;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(new Printer());
        thread.start();

        Thread.sleep(2000);
        thread.interrupt();
    }
}