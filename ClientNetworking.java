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

    public synchronized String getName() {
        return this.name;
    }

    public synchronized Socket getSocket() {
        return this.socket;
    }

    public synchronized void disconnect() {

        try {
            writeMsg("LOGOUT");
            pw.close();
            this.gui.f.setTitle("GWACK -- Slack Simulator (disconnected)");
            this.gui.messagesTextArea.setText("");
            this.socket.close();

        } catch (Exception e) {
        }
    }

    public synchronized Socket connect(int port) {
        Socket sock = null;
        try {
            sock = new Socket(this.host, port);
            if (sock.isConnected()) {
                socket = sock;
                pw = new PrintWriter(this.socket.getOutputStream());
                sendInitialSecret();
                if (gui != null) {
                    this.gui.f.setTitle("GWACK -- Slack Simulator (connected)");
                }
                netThread = new NetworkThread(sock);
                netThread.cN = this;
                netThread.start();
            }

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
            this.gui.showErrorMessage();
        }
        return sock;
    }

    public synchronized void writeMsg(String msg) {
        try {
            pw.println(msg);
            pw.flush();
        } catch (Exception e) {
            System.err.print(e);
            e.printStackTrace();

        }
    }

    public synchronized PrintWriter getOut() {
        return this.pw;
    }

    public void sendInitialSecret() {
        try {
            pw.println("SECRET" + "\n" + secretKey + "\n" + "NAME" + "\n" + this.getName());
            pw.flush();
        } catch (Exception e) {
            System.err.print("IOException -- sendsecret");
            System.err.print(e);
        }
    }

    private class NetworkThread extends Thread {
        public BufferedReader in;
        private Socket socket;
        public ClientNetworking cN;
       

        public NetworkThread(Socket socket) {
            this.socket = socket;
        }


        public void run() {
            try {
                if (!this.socket.isConnected()) {
                    this.socket.close();
                    return;
                }
                in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                while (true) {
                    if (this.socket.isConnected() == false) {
                        socket.close();
                        return;
                    }
                    // Thread.sleep(100000);
                    if (!in.ready()) {
                        continue;
                    }
                    String reply = in.readLine();// read a line from ther server
                    // if we switch control flow to updating client list
                    if (reply.contains("START_CLIENT_LIST")) {
                        String clientList = "";
                        while (true) {
                            reply = in.readLine();
                            if (reply.contains("END")) {
                                break;
                            }
                            clientList += reply +"\n";
                        }
                        if (cN.gui != null) {
                            cN.gui.membersTextArea.setText(clientList);
                        }
                    } else {
                        System.out.println(reply);
                        if (this.cN.gui != null) {
                            cN.gui.appendNewMessage(reply);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.print(e);
                e.printStackTrace();
            }
        }
    }
}

