package Task2;

class Printer implements Runnable {
    public void run() {
        for (int i = 0; i < 10; i++)
            System.out.println("I'm a printer " + i);
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var thread = new Thread(new Printer());
        thread.start();
        thread.join();

        for (int i = 0; i < 10; i++) {
            System.out.println("I'm not a printer " + i);
        }
    }
}