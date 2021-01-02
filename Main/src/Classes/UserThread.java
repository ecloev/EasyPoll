package Classes;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * UserThread.java
 * <p>
 * This thread handles connection for each connected client, so the server can
 * handle multiple clients at the same time.
 */

@SuppressWarnings({"InfiniteLoopStatement", "ResultOfMethodCallIgnored"})
public class UserThread extends Thread {
    private final Socket socket;
    private final PollServer server;
    private PrintWriter writer;

    public UserThread(Socket socket, PollServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            String response = reader.readLine();
            if (response.equals("1")) {
                String pollID = reader.readLine();
                String question = reader.readLine();
                ArrayList<String> answers = new ArrayList<>();
                while(true) {
                    response = reader.readLine();
                    if (response != null) {
                        answers.add(reader.readLine());
                    } else {
                        break;
                    }
                }
                createPoll(pollID, question, answers);
            } else if ( response.equals("2")) {

            }
            while (true) {


                if (response != null) {

                }
            }
        } catch (IOException ex) {
            //server.removeUser(tempUsername, this);
            System.out.println("A user has Disconnected.");
        }
    }

    public void createPoll(String pollID, String question, ArrayList<String> answers) {
        File users = new File("Polls/" + pollID + ".txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(users, true))) {
            bw.write(question);
            bw.newLine();
            for (int i = 0; i < answers.size(); i++) {
                bw.write(answers.get(i));
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
