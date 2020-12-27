import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class PollClient extends JComponent implements Runnable {

    private final String hostname;
    private final int port;
    private static boolean isUserValid = true;

    public PollClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }
        @Override
    public void run() {

    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            JOptionPane.showMessageDialog(null, "Connected to the Chat Server", "Messenger Connection",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Server is Not Online", "Messenger Connection",
                    JOptionPane.WARNING_MESSAGE);
            isUserValid = false;
        }
    }

    public static void main(String[] args) {
        PollClient client = new PollClient("localhost", 2020);
        client.execute();
        if (isUserValid) {
            SwingUtilities.invokeLater(client);
        }
    }
}
