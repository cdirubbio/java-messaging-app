import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class GWackClientGUI extends JFrame {
    public JFrame f;

    public JTextField clientName;
    public JTextField ipaddressField;
    public JTextField portField;
    public JTextArea membersTextArea;
    public JTextArea messagesTextArea;
    public JTextArea composeTextArea;

    public ClientNetworking cN;

    public String getHost() {
        return this.ipaddressField.getText();
    }

    public String getGUIName() {
        return this.clientName.getText();
    }

    public int getPort() {
        if (portField.getText() == "") {
            return 0;
        }
        return Integer.valueOf((String) portField.getText());
    }

    public JTextArea getMembersTextArea() {
        return this.membersTextArea;
    }
    public JTextArea getDisplayTextArea() {
        return this.messagesTextArea;
    }

    public GWackClientGUI() {
        f = new JFrame("GWACK -- Slack Simulator (disconnected)");
        f.setSize(800, 355);
        f.setLocation(220, 400);
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
        clientName = new JTextField(10);
        JLabel ipAddressLabel = new JLabel("IP Address");
        ipaddressField = new JTextField(10);
        JLabel portLabel = new JLabel("Port");
        portField = new JTextField(10);
        JButton connOrDisconnButton = new JButton("Connect");

        // Bottom! ! !! ! ! ! !
        JLabel membersOnlineLabel = new JLabel("Members Online");
        membersTextArea = new JTextArea(5, 13);
        membersTextArea.setEditable(false);
        JPanel membersBox = new JPanel();
        membersBox.setLayout(new BoxLayout(membersBox, BoxLayout.Y_AXIS));
        membersBox.add(membersOnlineLabel);
        membersBox.add(membersTextArea);

        JLabel messagesLabel = new JLabel("Messages");
        messagesTextArea = new JTextArea(10, 25);
        messagesTextArea.setEditable(false);
        JPanel messagesBox = new JPanel();
        messagesBox.setLayout(new BoxLayout(messagesBox, BoxLayout.Y_AXIS));
        messagesBox.add(messagesLabel);
        messagesBox.add(messagesTextArea);

        JLabel composeLabel = new JLabel("Compose");
        composeTextArea = new JTextArea(4, 25);
        JPanel composeBox = new JPanel(new BorderLayout()); // Use BorderLayout
        composeBox.add(composeLabel, BorderLayout.NORTH); // Add label to the top
        composeBox.add(composeTextArea, BorderLayout.CENTER); // Add text area to the center

        JPanel bottomSendButtonPanel = new JPanel(new BorderLayout(0, 0));
        JButton sendButton = new JButton("Send");
        bottomSendButtonPanel.add(sendButton, BorderLayout.EAST);
        fullBorderPanel.add(bottomSendButtonPanel, BorderLayout.SOUTH);

        bottomRightStackPanel.add(messagesBox, BorderLayout.NORTH);
        bottomRightStackPanel.add(composeBox, BorderLayout.SOUTH);

        topRowPanel.add(nameLabel);
        topRowPanel.add(clientName);
        topRowPanel.add(ipAddressLabel);
        topRowPanel.add(ipaddressField);
        topRowPanel.add(portLabel);
        topRowPanel.add(portField);
        topRowPanel.add(connOrDisconnButton);

        bottomBorderPanel.add(membersBox, BorderLayout.WEST);
        bottomBorderPanel.add(bottomRightStackPanel, BorderLayout.CENTER);

        f.add(fullBorderPanel);

        f.setVisible(true);
        // Action Listeners
        connOrDisconnButton.addActionListener((e) -> {
            if (connOrDisconnButton.getText().equals("Connect")) {
                cN = new ClientNetworking(this.getGUIName(), this.getHost(), this.getPort(), this);
                cN.connect(getPort());
                connOrDisconnButton.setText("Disconnect");
            } else if (connOrDisconnButton.getText().equals("Disconnect") && cN.getSocket().isConnected()) {
                try {
                    cN.getSocket().close();
                    connOrDisconnButton.setText("Connect");
                } catch (Exception err) {
                    System.err.println("Couldnt close socket");
                }
            }
        });

    }

    public void updateClientList(BufferedReader in) {
        try {
            String clientList = "";
            String next = in.readLine();
            while (next != "END_CLIENT_LIST") {
                clientList += next + "\n";
            }
            membersTextArea.setText(clientList);
        } catch (Exception err) {}
    }

    // UPDATE MESSAGES
    public void appendNewMessage(String message) {
        String currentMessages = messagesTextArea.getText();
        currentMessages += "\n" + message;
        messagesTextArea.setText(currentMessages);
    }

    public static void main(String[] args) {
        GWackClientGUI clientGUI = new GWackClientGUI();
        // ClientNetworking cN = new ClientNetworking(clientGUI.getGUIName(),
        // clientGUI.getHost(), clientGUI.getPort(), clientGUI);
        // You will use the Print writer code to do the following things:
        // Gotrta send the secret,
        // And send the messages
    }

}