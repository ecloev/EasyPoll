package Classes;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Poll.java
 *
 * Abstraction for a poll object
 *
 */

public class Poll {
    private String name;
    private int maxResponses;
    private final String id;
    private String question;
    private final ArrayList<String> answerChoices;
    private final String pollPassword;
    private final LocalDateTime creationDate;
    public File f;

    public Poll (String name, int maxResponses, String pollPassword) throws IOException {
        this.name = name;
        this.maxResponses = maxResponses;
        this.id = UUID.randomUUID().toString();
        this.pollPassword = pollPassword;
        this.creationDate = LocalDateTime.now();
        question = "";
        answerChoices = new ArrayList<>();
        f = new File("PollData");

        try (PrintWriter pw = new PrintWriter(new FileWriter(f, true))) {
            pw.println(name + "," + Integer.toString(maxResponses) + "," + id + "," + pollPassword);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getMaxResponses() {
        return this.maxResponses;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setMaxResponses(int maxResponses) {
        this.maxResponses = maxResponses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() { return this.question; }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getAnswerChoices() { return this.answerChoices; }

    public void addAnswerChoice(String ans) {
        answerChoices.add(ans);
    }

    public String getPollPassword() { return this.pollPassword; }

    public LocalDateTime getCreationDate() { return creationDate; }
}