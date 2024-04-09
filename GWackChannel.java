// Your GWack server (GWackChannel) should host a single chatroom or 
//channel at a specified port, provided as a command line argument. 
// The remaining requirements of the server are as follows:

        // The server should be able to handle multiple connected clients
        // All incoming messages should be distributed to all connected clients
        // Clients should be able to connect and disconnect gracefully
        // The current client list should be distributed and updated when clients leave
        // The protocol for client interaction (as described above) should be observed
        
public class GWackChannel {
    
    private ClientNetworking[] clientList;
    private int port;

    public GWackChannel(int port) {
        this.port = port;
    }


public static void main(String[] args) {
    int port = Integer.valueOf(args[0]);
}
}



//    EXPECTED FROM CLIENT
//////////////////////////////////////////////////////////////////
// SECRET
// 3c3c4ac618656ae32b7f3431e75f7b26b1a14a87
// NAME
// username
//////////////////////////////////////////////////////////////////