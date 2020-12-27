package Classes;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * UserThread.java
 *
 * This thread handles connection for each connected client, so the server can
 * handle multiple clients at the same time.
 *
 */

@SuppressWarnings({ "InfiniteLoopStatement", "ResultOfMethodCallIgnored" })
public class UserThread extends Thread {
    private final Socket socket;
    private final PollServer server;
    private PrintWriter writer;

    public UserThread(Socket socket, PollServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        String tempUsername = "";
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            while (true) {
                String response = reader.readLine();

                if (response != null) {

                }
            }
        } catch (IOException ex) {
            //server.removeUser(tempUsername, this);
            System.out.println("A user has Disconnected.");
        }
    }
}
