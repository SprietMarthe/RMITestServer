package Server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    private void startServer() {
        try {
            // create on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // create a new service named CounterService
            registry.rebind("Chatservice", new CounterImpl());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("system is ready");
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}