import distributed.Server;
import distributed.ServerImpl;
import distributed.socket.middleware.ClientSkeleton;
import view.Color;

import java.io.IOException;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServer {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private static Server server;


    protected AppServer() {
    }


    public static void main(String[] args) throws RemoteException {
        DatagramSocket datagramSocket;
        try {
            datagramSocket = new DatagramSocket();
            datagramSocket.connect(InetAddress.getByName("8.8.8.8"),10002);
            String currentIp = datagramSocket.getLocalAddress().getHostAddress();
            System.setProperty("java.rmi.server.hostname",currentIp);
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }

        server = new ServerImpl();

        Thread rmiThread = new Thread(() -> {
            try {
                startRMI();
            } catch (RemoteException e) {
                System.err.println( Color.RED_BOLD +  "Cannot start RMI. This protocol will be disabled." + Color.RESET);
            }
        });

        rmiThread.start();

        Thread socketThread = new Thread(() -> {
            try {
                startSocket();
            } catch (RemoteException e) {
                System.out.print(Color.RED);
                System.err.println("Cannot start socket. This protocol will be disabled.");
                System.out.print(Color.RESET);
            }
        });

        socketThread.start();

        try {
            rmiThread.join();
            socketThread.join();
        } catch (InterruptedException e) {
            System.err.println("No connection protocol available. Exiting...");
        }
    }

    public static void startSocket() throws RemoteException {

        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(() -> {
                    try {
                        ClientSkeleton clientSkeleton = new ClientSkeleton(socket);
                        server.register(clientSkeleton);
                        while (true) {
                            clientSkeleton.receive(server);
                        }
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from client. Closing this connection...");
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            System.err.println("Cannot close socket");
                        }
                    }
                });
            }
        } catch (IOException e) {
            throw new RemoteException("Cannot start socket server", e);
        }
    }

    private static void startRMI() throws RemoteException{
        Registry registry = LocateRegistry.createRegistry(1099);
        try {
            registry.bind("server", server);
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
