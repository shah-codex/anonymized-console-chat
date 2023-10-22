import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.file.*;

public class Server implements Runnable {
    private final String DIR = "." + File.separatorChar + "groups" + File.separatorChar;

    private final ServerSocket server;
    private final HashMap<String, ArrayList<Server.ServerRequestHandler>> clients;
    private final HashMap<String, File> files;

    public Server(int port) throws IOException {
        this.server = new ServerSocket(port);
        this.clients = new HashMap<>();
        this.files = new HashMap<>();
        System.out.println("Server started on port [" + port + "]...");
    }

    @Override
    public void run() {
        try {
            while (true) {
                Socket request = server.accept();
                ServerRequestHandler requestHandler = new ServerRequestHandler(request);
                String group = requestHandler.getGroup();
                requestHandler.setUser();

                clients.computeIfAbsent(group, (K) -> new ArrayList<ServerRequestHandler>());
                clients.get(group).add(requestHandler);

                String fileName = DIR + group + ".chat";
                File file = new File(fileName);
                file.createNewFile();

                files.computeIfAbsent(group, (K) -> file);

                String history = readMessageFromFile(group);
                requestHandler.sendMessage(history);

                requestHandler.start();
            }
        } catch (IOException e) {
            System.out.println("Invalid request");
        }
    }

    private String readMessageFromFile(String group) throws IOException {
        File file = files.get(group);

        byte[] history = Files.readAllBytes(file.toPath());

        return new String(history);
    }

    private void writeMessageToFile(String message, String group) throws IOException {
        File file = files.get(group);
        message = message + "\n";

        Files.write(file.toPath(), message.getBytes(), StandardOpenOption.APPEND);
    }

    private void broadcast(String message, String group) throws IOException {
        writeMessageToFile(message, group);

        for (ServerRequestHandler client: clients.get(group)) {
            if (client != null) {
                client.sendMessage(message);
            }
        }
    }

    private class ServerRequestHandler extends Thread {
        private final Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        private String group;

        public ServerRequestHandler(Socket socket) throws IOException {
            this.socket = socket;
            this.in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()
            ));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        }

        public String getGroup() throws IOException {
            sendMessage("Enter group name: ");
            String line = null;
            while ((line = in.readLine()) != null) {
                group = line;
                return line;
            }

            return null;
        }

        public void setUser() throws IOException {
            sendMessage("Enter user name: ");

            String line = null;
            while ((line = in.readLine()) != null) {
                this.setName(line);
                break;
            }
        }

        @Override
        public void run() {
            try {
                System.out.println("Client [" + this.getName() + "] connected with group [" + group + "]");

                String line = null;
                StringBuffer sb = new StringBuffer();
                while ((line = in.readLine()) != null && socket.isConnected()) {
                    sb.append(line);
                    if (!line.endsWith("\\")) {
                        String message = "[" + System.currentTimeMillis() + "] ["  + this.getName() + "] : "  + sb.toString();
                        System.out.println(message);
                        broadcast(message, group);
                        sb.delete(0, sb.length());
                    } else {
                        sb.deleteCharAt(sb.length() - 1);
                        sb.append("\n");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error occured");
            } finally {
                System.out.println("Client Disconnected");
                disconnect();
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        private void disconnect() {
            try {
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("Unable to stop the connection");
            }
        }
    }
}