package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * PollClient.java
 * <p>
 * This class handles the GUI and facilitates communication between the client and server.
 */

public class PollClient extends JComponent implements Runnable {

    private final String hostname;
    private final int port;
    private static boolean isUserValid = true;
    private Poll userPoll;
    private BufferedReader reader;
    private PrintWriter writer;

    //Login Frame
    private JFrame loginFrame;
    private JButton newPollButton;
    private JButton existingPollButton;

    //New Poll Frame
    private JFrame newPollFrame;
    private JLabel pollID;
    private JLabel answersPrompt;
    private JTextField question;
    private JTextField response;
    private int responseCount = 1;
    private JButton addResponse;
    private JButton confirm;


    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == newPollButton) {
                try {
                    String pollName = JOptionPane.showInputDialog(null, "Enter Poll Name: ",
                            "EasyPoll", JOptionPane.INFORMATION_MESSAGE);
                    userPoll = new Poll(pollName, 10, "coolguy");
                    pollID.setText(userPoll.getId());
                } catch (IOException error) {
                    error.printStackTrace();
                }

                loginFrame.dispose();
                newPollFrame.setVisible(true);
            }
            if (e.getSource() == existingPollButton) {
                String[] optionsCase2 = {"Pollee", "Poller"};
                String polleeOrPoller = (String) JOptionPane.showInputDialog(null,
                        "Are you the pollee or the poller?",
                        "EasyPoll", JOptionPane.PLAIN_MESSAGE,
                        null, optionsCase2, null);

                if (polleeOrPoller.equals("Pollee")) {
                    String pollID = JOptionPane.showInputDialog(null,
                            "Enter the Poll's ID", "EasyPoll", JOptionPane.PLAIN_MESSAGE);
                    writer.write("2");
                    writer.println();
                    writer.write(pollID);
                    writer.println();
                    writer.flush();

                    try {
                        String question = reader.readLine();
                        System.out.println("Question: " + question);
                        int numOfAnswers = reader.read();
                        System.out.println(numOfAnswers);
                        ArrayList<String> answers = new ArrayList();
                        for (int i = 0; i < numOfAnswers; i++) {
                            String response = reader.readLine();
                            answers.add(response);
                            System.out.println("Response: " + response);
                        }
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                } else if (polleeOrPoller.equals("Poller")) {
                    String pollID = JOptionPane.showInputDialog(null,
                            "Enter the Poll's ID", "EasyPoll", JOptionPane.PLAIN_MESSAGE);
                    String pollPassword = JOptionPane.showInputDialog(null,
                            "Enter the Poll's password", "EasyPoll", JOptionPane.PLAIN_MESSAGE);
                    writer.write("3");
                    writer.println();
                    writer.write(pollID);
                    writer.println();
                    writer.write(pollPassword);
                }

                loginFrame.dispose();
            }

            if (e.getSource() == addResponse) {
                if (response.getText().equals("") || response.getText().equals("*Added*")) {
                    JOptionPane.showMessageDialog(null, "Response Box is Empty", "EasyPoll",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    userPoll.addAnswerChoice(response.getText());
                    responseCount++;
                    response.setText("*Added*");
                    answersPrompt.setText("Response #" + responseCount + ": ");
                }
            }
            if (e.getSource() == confirm) {
                userPoll.setQuestion(question.getText());
                writer.write("1");
                writer.println();
                writer.write(userPoll.getId());
                writer.println();
                writer.write(userPoll.getQuestion());
                writer.println();
                ArrayList<String> answers = userPoll.getAnswerChoices();
                writer.write(answers.size());
                writer.println();
                for (int i = 0; i < answers.size(); i++) {
                    writer.write(answers.get(i));
                    writer.println();
                }
                writer.flush();
                newPollFrame.dispose();
                String message = "New Poll created with the following info:\n"
                        + "Poll ID: " + userPoll.getId() + "\nQuestion: " + userPoll.getQuestion() + "\n";
                for (int i = 0; i < userPoll.getAnswerChoices().size(); i++) {
                    message += (i + 1) + ".) " + userPoll.getAnswerChoices().get(i) + "\n";
                }
                JOptionPane.showMessageDialog(null, message, "EasyPoll",
                        JOptionPane.INFORMATION_MESSAGE);
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
        newPollFrame = new JFrame("EasyPoll");
        Container pollContent = newPollFrame.getContentPane();
        pollContent.setLayout(new BorderLayout());

        newPollFrame.setSize(350, 180); // Size of the window
        newPollFrame.setLocationRelativeTo(null); // opens in middle of screen
        newPollFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // program stops when window is closed
        newPollFrame.setVisible(false); // you can see it now

        pollID = new JLabel("");
        pollID.setBounds(100, 0, 150, 50);
        JLabel pollIDText = new JLabel("Poll ID: ", SwingConstants.RIGHT);
        pollIDText.setBounds(0, 0, 150, 50);

        JPanel idPanel = new JPanel();
        idPanel.add(pollIDText, BorderLayout.EAST);
        idPanel.add(pollID, BorderLayout.WEST);
        newPollFrame.add(idPanel, BorderLayout.NORTH);

        JPanel questionPanel = new JPanel();
        question = new JTextField("", 30);
        response = new JTextField("", 20);
        JLabel prompt = new JLabel("Write question here: ");
        answersPrompt = new JLabel("Response #" + responseCount + ": ");
        addResponse = new JButton("add");
        confirm = new JButton("Confirm Poll");
        questionPanel.add(prompt, BorderLayout.WEST);
        questionPanel.add(question, BorderLayout.EAST);
        questionPanel.add(answersPrompt, BorderLayout.SOUTH);
        questionPanel.add(response, BorderLayout.SOUTH);
        questionPanel.add(addResponse, BorderLayout.SOUTH);
        questionPanel.add(confirm, BorderLayout.SOUTH);
        newPollFrame.add(questionPanel, BorderLayout.CENTER);


        addResponse.addActionListener(actionListener);
        confirm.addActionListener(actionListener);


    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            JOptionPane.showMessageDialog(null, "Connected to the Chat Server", "Messenger Connection",
                    JOptionPane.INFORMATION_MESSAGE);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());

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
