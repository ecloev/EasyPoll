package Classes;

import java.util.ArrayList;
import java.util.UUID;

public class Poll {
    private String name;
    private int maxResponses;
    private final String id;
    private ArrayList<String> questions;
    private ArrayList<String[]> answerChoices;

    public Poll (String name, int maxResponses) {
        this.name = name;
        this.maxResponses = maxResponses;
        this.id = UUID.randomUUID().toString();
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
}
