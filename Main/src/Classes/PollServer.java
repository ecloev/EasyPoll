package Classes;

import java.io.*;
import java.net.*;
import java.util.*;

public class PollServer {
    private final int port;
    private final ArrayList<UserThread> userThreads;

    public PollServer(int port) {
        this.port = port;
        this.userThreads = new ArrayList<>();
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Chat Server is listening on port " + port);
            boolean serverCrash = false;
            do {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("A User Has Connected.");

                    UserThread newUser = new UserThread(socket, this);
                    userThreads.add(newUser);
                    newUser.start();
                } catch (IOException | IllegalThreadStateException e) {
                    System.out.println("Server Has Crashed!");
                    serverCrash = true;
                }
            } while (!serverCrash);
        } catch (IOException e) {
            System.out.println("Server Has Crashed!");
        }
    }

    public static void main(String[] args) {
        PollServer server = new PollServer(2020);
        server.execute();
    }
}
