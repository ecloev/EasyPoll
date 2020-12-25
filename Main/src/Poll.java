import java.io.Serializable;
import java.math.BigInteger;

public class Poll implements Serializable {
    private String name;
    private int maxResponses;
    private long id;

    public Poll (String name, int maxResponses, long id) {
        this.name = name;
        this.maxResponses = maxResponses;
        this.id = id;
    }
}
