import java.net.*;
import java.io.*;
import java.util.*;

class Client {
    private static final Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8000);
            System.out.println("Socket connected...");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Remove below line to turn off random colors.
            System.out.print("\033[" + (30 + (int)(Math.random() * ((37 - 30) + 1))) + "m");

            Thread reader = new Thread(() -> {
                try {
                    String line = null;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnecting...");
                }
            });
            reader.start();

            while (true) {
                String message = s.nextLine();
                if (message.equals("exit")) {
                    break;
                }
                out.println(message);
                System.out.print("\033[A");
            }

            out.close();
            in.close();
            socket.close();
        } catch (UnknownHostException unk) {
            System.out.println(unk);
        } catch (IOException e) {
            System.out.println(e);
        }

        System.out.print("\033[0m");
    }
}