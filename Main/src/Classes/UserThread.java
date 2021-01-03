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
            System.out.println("TESTING DONE HERE");
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            String response = reader.readLine();
            System.out.println(response);
            if (response.equals("1")) {
                String pollID = reader.readLine();
                System.out.println("pollID: "+pollID);
                String question = reader.readLine();
                System.out.println("question: "+question);
                ArrayList<String> answers = new ArrayList<>();
                int numberOfAnswers = reader.read();
                System.out.println(numberOfAnswers);
                reader.readLine();
                for (int i = 0; i < numberOfAnswers; i++) {
                    response = reader.readLine();
                    System.out.println("response: "+response);
                    answers.add(response);
                }

                System.out.println("Testing: Create poll starts");
                createPoll(pollID, question, answers);
            } else if (response.equals("2")) {

            } else if (response.equals("3")) {

            }
        } catch (IOException ex) {
            //server.removeUser(tempUsername, this);
            System.out.println("A user has Disconnected.");
        }
    }

    public void createPoll(String pollID, String question, ArrayList<String> answers) {
        File users = new File("Polls/" + pollID + ".txt");
        if (users.exists()) {

        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(users, true))) {
            bw.write(question);
            bw.newLine();
            for (String answer : answers) {
                bw.write(answer);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}