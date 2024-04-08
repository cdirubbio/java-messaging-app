import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GWackClientGUI extends JFrame {
    public static String secretKey = "3c3c4ac618656ae32b7f3431e75f7b26b1a14a87";

    public GWackClientGUI() {
        JFrame f = new JFrame("GWACK -- Slack Simulator (disconnected)");
        f.setSize(800, 355);
        f.setLocation(220,400);
        f.setLocationRelativeTo(null);
        f.setResizable(false);

        JPanel fullBorderPanel = new JPanel(new BorderLayout());
        JPanel topRowPanel = new JPanel(new FlowLayout());

        JPanel bottomBorderPanel = new JPanel(new BorderLayout(10, 10));
        fullBorderPanel.add(topRowPanel, BorderLayout.NORTH);
        fullBorderPanel.add(bottomBorderPanel, BorderLayout.CENTER);
        JPanel bottomRightStackPanel = new JPanel(new BorderLayout(0, 50));


        // Top row
        JLabel nameLabel = new JLabel("Name");
        JTextField nameTextField = new JTextField(10);

        JLabel ipAddressLabel = new JLabel("IP Address");
        JTextField ipAddressTextField = new JTextField(10);

        JLabel portLabel = new JLabel("Port");
        JTextField portTextField = new JTextField(10);

        JButton connOrDisconnButton = new JButton("Connect");
        connOrDisconnButton.addActionListener((e) -> {
            if (connOrDisconnButton.getText().equals("Connect")) {
                connOrDisconnButton.setText("Disconnect");
            }else {
                connOrDisconnButton.setText("Connect");
            }
        });


        // Bottom! ! !! ! ! ! !
        JLabel membersOnlineLabel = new JLabel("Members Online");
        JTextArea membersOnlineTextArea = new JTextArea(5, 13);
        membersOnlineTextArea.setEditable(false);
        JPanel membersBox = new JPanel();
        membersBox.setLayout(new BoxLayout(membersBox, BoxLayout.Y_AXIS));
        membersBox.add(membersOnlineLabel);
        membersBox.add(membersOnlineTextArea);

        JLabel messagesLabel = new JLabel("Messages");
        JTextArea messagesTextArea = new JTextArea(10, 25);
        messagesTextArea.setEditable(false);
        JPanel messagesBox = new JPanel();
        messagesBox.setLayout(new BoxLayout(messagesBox, BoxLayout.Y_AXIS));
        messagesBox.add(messagesLabel);
        messagesBox.add(messagesTextArea);

        JLabel composeLabel = new JLabel("Compose");
        JTextArea composeTextArea = new JTextArea(4, 25);
        JPanel composeBox = new JPanel();
        composeBox.setLayout(new BoxLayout(composeBox, BoxLayout.Y_AXIS));
        composeBox.add(composeLabel);
        composeBox.add(composeTextArea);

        JPanel bottomSendButtonPanel = new JPanel(new BorderLayout(0, 0));
        JButton sendButton = new JButton("Send");
        bottomSendButtonPanel.add(sendButton, BorderLayout.EAST);
        fullBorderPanel.add(bottomSendButtonPanel, BorderLayout.SOUTH);


        bottomRightStackPanel.add(messagesBox, BorderLayout.NORTH);
        bottomRightStackPanel.add(composeBox, BorderLayout.SOUTH);

        topRowPanel.add(nameLabel);
        topRowPanel.add(nameTextField);
        topRowPanel.add(ipAddressLabel);
        topRowPanel.add(ipAddressTextField);
        topRowPanel.add(portLabel);
        topRowPanel.add(portTextField);
        topRowPanel.add(connOrDisconnButton);

        bottomBorderPanel.add(membersBox, BorderLayout.WEST);
        bottomBorderPanel.add(bottomRightStackPanel, BorderLayout.CENTER);

        f.add(fullBorderPanel);

        f.setVisible(true);
    }
    public static void main(String[] args) {
        GWackClientGUI clientGUI = new GWackClientGUI();
        // You will use the Print writer code to do the following things:
        // Gotrta send the secret,
        // And send the messages
    }

}