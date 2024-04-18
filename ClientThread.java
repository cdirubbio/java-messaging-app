import java.net.*;
import java.util.*;
import java.io.*;

public class ClientThread extends Thread {
    public Socket sock;
    public GWackChannel server;
    public boolean valid;
    public PrintWriter pWriter = null;
    public BufferedReader bReader = null;

    public ClientThread(Socket socket, GWackChannel serv) {
        this.sock = socket;
        this.server = serv;

       
    }
    // TODO: fucking everything
    public void run() {
        try {
            pWriter = new PrintWriter(sock.getOutputStream());
            bReader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (Exception e) {
        }
        // collects messages from users
        // send to all users
        Queue<String> currentQueue = this.server.getOutputQueue();
        pWriter.println(currentQueue.remove());
    }
}
