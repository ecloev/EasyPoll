package Classes;

import java.io.Serializable;

public class Poll implements Serializable {
    private String name;
    private int maxResponses;
    private long id;

    public Poll (String name, int maxResponses, long id) {
        this.name = name;
        this.maxResponses = maxResponses;
        this.id = id;
    }

    public int getMaxResponses() {
        return this.maxResponses;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setMaxResponses(int maxResponses) {
        this.maxResponses = maxResponses;
    }

    public void setName(String name) {
        this.name = name;
    }
}
