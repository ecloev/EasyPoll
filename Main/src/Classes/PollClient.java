package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class PollClient extends JComponent implements Runnable {

    private final String hostname;
    private final int port;
    private static boolean isUserValid = true;

    //Login Frame
    private JFrame loginFrame;
    private JButton newPollButton;
    private JButton existingPollButton;

    //New Poll Frame
    private JFrame newPollFrame;
    private JTextField pollID;
    private JButton confirm;


    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newPollButton) {
                loginFrame.dispose();
            }
            if (e.getSource() == existingPollButton) {
                String pollID = JOptionPane.showInputDialog(null, "Enter the poll's ID: ",
                        "EasyPoll", JOptionPane.INFORMATION_MESSAGE);
                loginFrame.dispose();
            }
        }
    };
    public PollClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }
        @Override
    public void run() {
            // ***** Login Frame *****//
            loginFrame = new JFrame("Start Up - EasyPoll");
            Container loginContent = loginFrame.getContentPane();
            loginContent.setLayout(new BorderLayout());

            newPollButton = new JButton("Create New Poll");
            existingPollButton = new JButton("Find Existing Poll");

            loginFrame.setSize(350, 150); // Size of the window
            loginFrame.setLocationRelativeTo(null); // opens in middle of screen
            loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program stops when window is closed
            loginFrame.setVisible(true); // you can see it now

            JPanel newPollPanel = new JPanel(); // creates a new poll section
            JPanel existingPollPanel = new JPanel(); // creates an existing poll section

            newPollButton = new JButton("Create New Poll");
            newPollButton.setPreferredSize(new Dimension(140, 100));
            newPollPanel.add(newPollButton, BorderLayout.EAST); // adds Button to new poll panel

            existingPollButton = new JButton("Find Existing Poll");
            existingPollButton.setPreferredSize(new Dimension(140, 100));
            existingPollPanel.add(existingPollButton); // adds Button to new poll panel

            loginFrame.add(newPollPanel, BorderLayout.WEST); // puts username panel on top
            loginFrame.add(existingPollPanel, BorderLayout.EAST); // puts password panel in middle

            newPollButton.addActionListener(actionListener);
            existingPollButton.addActionListener(actionListener);
            
            //NEW POLL FRAME

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
