import java.net.*;
import java.util.*;
import java.io.*;

public class ClientThread extends Thread {
    public String SECRET = "3c3c4ac618656ae32b7f3431e75f7b26b1a14a87";
    public Socket sock;
    public GWackChannel server;
    public boolean valid;
    public PrintWriter pWriter = null;
    public BufferedReader bReader = null;
    public String clientName;

    public ClientThread(Socket socket, GWackChannel serv) {
        this.sock = socket;
        this.server = serv;

        try {
            pWriter = new PrintWriter(sock.getOutputStream());
            bReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

    }

    // TODO: fucking everything
    public void run() {
        try {
            String in = bReader.readLine();

            if (in == "SECRET") {
                in = bReader.readLine(); // secret key
                in = bReader.readLine(); // NAME
                clientName = bReader.readLine();
            }
            // String in = bReader.readLine();
            this.server.getOutputQueue().add(in);
            broadcast("[" + clientName + "] " + in);

            // String in = bReader.readLine();
            // this.server.getOutputQueue().add(in);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

    }

    public void broadcast(String msg) {
        for (int i = 0; i < server.getConnectedClients().size(); i++) {
            pWriter.println(msg);
        }
    }
}
