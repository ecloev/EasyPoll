package Classes;

import Exceptions.PollNotFoundException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


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
                int numberOfAnswers = reader.read();
                reader.readLine();
                for (int i = 0; i < numberOfAnswers; i++) {
                    response = reader.readLine();
                    answers.add(response);
                }
                createPoll(pollID, question, answers);
            } else if (response.equals("2")) {
                String pollID = reader.readLine();
                try {
                    flushPollData(pollID);
                } catch (PollNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (response.equals("3")) {

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
            bw.write("" + answers.size());
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

    private void flushPollData(String pollID) throws PollNotFoundException {
        File users = new File("Polls/" + pollID + ".txt");
        if (!users.exists()) {
            throw new PollNotFoundException("Poll with that ID does not exist!");
        }
        try (BufferedReader br = new BufferedReader(new FileReader(users))) {
            String question = br.readLine();
            writer.write(question);
            writer.println();
            String numOfAnswers = br.readLine();
            try {
                int num = Integer.parseInt(numOfAnswers);
                System.out.println(num);
                writer.write(numOfAnswers);
                writer.println();
                for (int i = 0; i < num; i++) {
                    String answer = br.readLine();
                    writer.write(answer);
                    writer.println();
                }
                writer.flush();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            System.out.println(numOfAnswers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
