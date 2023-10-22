import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int port = 8000;
        try {
            Thread server = new Thread(new Server(port), "Server");
            server.start();
        } catch (IOException e) {
            System.out.println("Unable to open connection on port [" + port + "]");
        }

        System.out.println("Good Morning");
    }
}