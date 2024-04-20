import java.net.*;
import java.util.*;
import java.io.*;

public class ClientThread extends Thread {
    public String SECRET = "3c3c4ac618656ae32b7f3431e75f7b26b1a14a87";
    public Socket sock;
    public GWackChannel server;
    public boolean valid = true;
    public PrintWriter pWriter = null;
    public BufferedReader bReader = null;
    public String clientName;

    public ClientThread(Socket socket, GWackChannel serv) {
        this.sock = socket;
        this.server = serv;

    }

    // TODO: fucking everything
    public void run() {
        try {
            pWriter = new PrintWriter(sock.getOutputStream());
            bReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

            while (true) {

                String nextMessageToSend = this.server.getOutputQueue().poll();
                if (nextMessageToSend != null) {
                    server.broadcast(nextMessageToSend);
                }
                // String in = bReader.readLine();
                String in = bReader.readLine();
                if (in == null) {
                    continue;
                }
                if (in.contains("SECRET")) {
                    in = bReader.readLine(); // secret key
                    in = bReader.readLine(); // NAME
                    clientName = bReader.readLine(); // actual client name
                    this.server.getOutputQueue().add(server.getClientList());
                } else if (in.contains("LOGOUT")) {
                    this.valid = false;
                    System.out.println("IN LOGOUT THREAD - " + clientName);
                    server.removeClients();
                    pWriter.close();
                    bReader.close();
                    this.join();
                    break;
                } else {
                    this.server.getOutputQueue().add("[" + clientName + "] " + in);
                }
            }

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }

    }
}
