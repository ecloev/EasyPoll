package Classes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class Poll {
    private String name;
    private int maxResponses;
    private final String id;
    private final ArrayList<String> questions;
    private final ArrayList<String[]> answerChoices;
    private final String pollPassword;
    private final LocalDateTime creationDate;

    public Poll (String name, int maxResponses, String pollPassword) {
        this.name = name;
        this.maxResponses = maxResponses;
        this.id = UUID.randomUUID().toString();
        this.pollPassword = pollPassword;
        this.creationDate = LocalDateTime.now();
        questions = new ArrayList<>();
        answerChoices = new ArrayList<>();
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

    public ArrayList<String> getQuestions() { return this.questions; }

    public ArrayList<String[]> getAnswerChoices() { return this.answerChoices; }

    public String getPollPassword() { return this.pollPassword; }

    public LocalDateTime getCreationDate() { return creationDate; }
}
