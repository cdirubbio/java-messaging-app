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

    private Socket socket;

    public ClientNetworking(String name, String host, int port, GWackClientGUI gui) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.gui = gui;
        netThread = new NetworkThread();
    }

    public String getName() {
        return this.name;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public void disconnect() {
        try {
            this.socket.close();
            this.gui.f.setTitle("GWACK -- Slack Simulator (disconnected)");
        } catch (Exception e) {
        }

    }

    public Socket connect(int port) {
        Socket sock = null;
        try {
            sock = new Socket(this.host, port);
            // send server the 4 required messages
            writeMsg("SECRET" + "\n");
            writeMsg(secretKey + "\n");
            writeMsg("NAME" + "\n");
            writeMsg(this.name + "\n");
            this.gui.f.setTitle("GWACK -- Slack Simulator (connected)");
            netThread.start();
        } catch (Exception e) {
            System.err.println("Cannot Connect");
            // NEED ERROR MODAL HERE this.gui.
        }
        return sock;
    }

    public void writeMsg(String msg) {
        try {
            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            pw.println(msg);
            pw.close();

        } catch (Exception e) {
            System.err.print("IOException -- writemsg");
            System.exit(1);
        }
    }

    // This class should store some kind of Thread object (which you should
    // declare inside of this class as a private class that extends Thread)
    // which has a run method that collects messages passed down from the server
    // and updates the GUI accordingly.
    private class NetworkThread extends Thread {
        // needs to be able to collect all messages from server
        // and update GUI
        public BufferedReader in;

        public void run() {
            try {
                while (socket.isConnected()) {
                    in = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));

                    String reply = in.readLine();// read a line from ther server
                    // if we switch control flow to updating client list
                    if (reply == "START_CLIENT_LIST") {

                    }

                    // if its a mesage
                    gui.appendNewMessage(reply);

                    


                }
            } catch (Exception e) {
                System.err.print("IOExceptionhmhmhm");
            }
        }
    }
}

// a class named GWackClientGUI that represents the GUI and contains the main
// method to display the GUI. You’ll also need to provide all methods required
// by the unit tests.
// a class named ClientNetworking that is used by the GUI to set up a connection
// to the server and manages messages to and from the server.