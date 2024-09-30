package Task3;

class Printer implements Runnable {
    private final String[] messages;
    private final int number;

    Printer(String[] messages, int number) {
        this.messages = messages;
        this.number = number;
    }

    public void run() {
        for (String message : this.messages)
            System.out.println("Thread #" + this.number + " " + message);
    }
}

public class Main {
    public static void main(String[] args) {
        var thread1 = new Thread(new Printer(new String[]{"one", "two", "three", "four"}, 1));
        var thread2 = new Thread(new Printer(new String[]{"two", "two", "three", "four"}, 2));
        var thread3 = new Thread(new Printer(new String[]{"three", "two", "three", "four"}, 3));
        var thread4 = new Thread(new Printer(new String[]{"four", "two", "three", "four"}, 4));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}