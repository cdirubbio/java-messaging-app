public class ClientNetworking {
    
    String status;
    private String name;
    private String host;
    private int port;
    private GWackClientGUI gui;

    public ClientNetworking(String name, String host, int port, GWackClientGUI gui) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.gui = gui;

        status = "connected";
    }
    public String getName() {
        return this.name;
    }

    private class networkThread extends Thread {

    }


}
