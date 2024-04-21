// Your GWack server (GWackChannel) should host a single chatroom or 
//channel at a specified port, provided as a command line argument. 
// The remaining requirements of the server are as follows:

// The server should be able to handle multiple connected clients
// All incoming messages should be distributed to all connected clients
// Clients should be able to connect and disconnect gracefully
// The current client list should be distributed and updated when clients leave
// The protocol for client interaction (as described above) should be observed

import java.net.*;
import java.util.*;

public class GWackChannel {

    private ArrayList<ClientThread> connectedClients;
    private Queue<String> outputQueue = new PriorityQueue<String>();

    private int port;

    private ServerSocket serverSock;

    public GWackChannel(int port) {
        this.port = port;
        connectedClients = new ArrayList<ClientThread>();
        try {
            serverSock = new ServerSocket(this.port);
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public synchronized ArrayList<ClientThread> getConnectedClients() {
        return this.connectedClients;
    }

    // START_CLIENT_LIST\nJane\nJohn\nRavi\nEND_CLIENT_LIST
    public synchronized String getClientList() {
        String cList = "START_CLIENT_LIST";
        for (int i = 0; i < connectedClients.size(); i++) {
            cList += "\n" + connectedClients.get(i).clientName;
        }
        cList += "\nEND_CLIENT_LIST";
        return cList;
    }

    public synchronized void addClient(ClientThread cT) {
        this.connectedClients.add(cT);
    }

    // this works, maybe
    public synchronized void removeClients() {
        for (int i = 0; i < connectedClients.size(); i++) {
            if (connectedClients.get(i).valid == false) {
                try {
                    connectedClients.remove(i);
                } catch (Exception e) {
                    System.err.println(e);
                }
              
            }
        }
    }

    public synchronized ServerSocket getServerSocket() {
        return this.serverSock;
    }

    public synchronized Queue<String> getOutputQueue() {
        return this.outputQueue;
    }

    public void serve(int arg) {
        while (arg != 0) {
            try {
                arg--;
                // accept incoming connection
                Socket clientSock = serverSock.accept();               
                ClientThread temp = new ClientThread(clientSock, this);
                addClient(temp);
                temp.start();
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            } 
        }
    }

    public synchronized void sendOutNextMessage() {
        String nextMessageToSend = getOutputQueue().poll();
        if (nextMessageToSend != null) {
            broadcast(nextMessageToSend);
        }
    }
    
    public synchronized void broadcast(String msg) {
        for (int i = 0; i < getConnectedClients().size(); i++) {
            getConnectedClients().get(i).pWriter.println(msg);
            getConnectedClients().get(i).pWriter.flush();
            // System.out.println(msg);
        }
    }
    public static void main(String[] args) {
        int port = Integer.valueOf(args[0]);
        GWackChannel server = new GWackChannel(port);
        server.serve(-1);
    }
}

// EXPECTED FROM CLIENT
//////////////////////////////////////////////////////////////////
// SECRET
// 3c3c4ac618656ae32b7f3431e75f7b26b1a14a87
// NAME
// username
//////////////////////////////////////////////////////////////////