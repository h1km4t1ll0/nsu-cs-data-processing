package Task1;

class Printer implements Runnable {
    public void run() {
        for (int i = 0; i < 10; i++)
            System.out.println("I'm a printer " + i);
    }
}

public class Main {
    public static void main(String[] args) {
        new Thread(new Printer()).start();

        for (int i = 0; i < 10; i++) {
            System.out.println("I'm not a printer " + i);
        }
    }
}