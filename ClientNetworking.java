import java.net.*;
import java.io.*;

// IP: 135.181.248.23

// ports: 
// 8082
// 8181
// 8283

public class ClientNetworking {
    public String secretKey = "3c3c4ac618656ae32b7f3431e75f7b26b1a14a87";
    private String name;
    private String host;
    private int port;
    private GWackClientGUI gui;

    private NetworkThread netThread;
    public PrintWriter pw;

    private Socket socket;

    public ClientNetworking(String name, String host, int port, GWackClientGUI gui) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.gui = gui;

        connect(this.port);
    }

    public String getName() {
        return this.name;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void disconnect() {

        try {
            writeMsg("LOGOUT");
            pw.close();
            this.gui.f.setTitle("GWACK -- Slack Simulator (disconnected)");
            this.gui.messagesTextArea.setText("");
            this.socket.close();

        } catch (Exception e) {
        }
    }

    public Socket connect(int port) {
        Socket sock = null;
        try {
            sock = new Socket(this.host, port);
            // send server the 4 required messages
            if (sock.isConnected()) {
                System.out.println("CONNECTED");
                socket = sock;
                pw = new PrintWriter(this.socket.getOutputStream());
                sendInitialSecret();
                if (gui != null) {
                    this.gui.f.setTitle("GWACK -- Slack Simulator (connected)");
                }
                netThread = new NetworkThread(sock);

                netThread.start();
            }

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
            // NEED ERROR MODAL HERE this.gui.
        }
        return sock;
    }

    public void writeMsg(String msg) {
        try {
            pw.println(msg);
            pw.flush();
        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();
            System.exit(1);

        }
    }

    public PrintWriter getOut() {
        return this.pw;
    }

    public void sendInitialSecret() {
        try {
            pw.println("SECRET" + "\n" + secretKey + "\n" + "NAME" + "\n" + this.getName());
            // pw.flush();
        } catch (Exception e) {
            System.err.print("IOException -- sendsecret");
            System.err.print(e);
        }
    }

    private class NetworkThread extends Thread {
        public BufferedReader in;
        private Socket socket;

        public NetworkThread(Socket socket) {
            this.socket = socket;
        }

        // This is fine, sometimes
        public void run() {
            try {
                if (this.socket.isConnected()) {
                    in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                }
                while (this.socket.isConnected()) {
                    // Thread.sleep(100000);
                    String reply = in.readLine();// read a line from ther server
                    // if we switch control flow to updating client list
                    
                    if (reply.contains("START_CLIENT_LIST")) {
                        String clientList = "";
                        while (!(clientList.contains("END_CLIENT"))) {
                            reply = in.readLine();
                            System.out.println(reply);
                            clientList += reply + "\n";
                        }
                        clientList = clientList.substring(0, clientList.length() - 16);
                        gui.membersTextArea.setText(clientList);
                        continue;
                    } else {
                        System.out.println(reply);
                        gui.appendNewMessage(reply);
                    }
                }
            } catch (Exception e) {
                System.err.print(e);
            }
        }
    }
}

// a class named GWackClientGUI that represents the GUI and contains the main
// method to display the GUI. You’ll also need to provide all methods required
// by the unit tests.
// a class named ClientNetworking that is used by the GUI to set up a connection
// to the server and manages messages to and from the server.