import distributed.Server;
import distributed.ClientImpl;
import distributed.socket.middleware.ServerStub;
import view.Color;

import java.net.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;
import java.util.Scanner;

public class AppClient {

    //Default network address
    private static int Port = 1234;
    private static String Ip = "localhost";
    public static void main(String[] args) throws RemoteException, NotBoundException {
        if(chooseNetworkArchitecture()==1){
            //Client selected Rmi architecture
            //todo non chiedere porta
            chooseNetworkAddress();
            try {
                startRmiClient();
            } catch (Exception e) {
                System.out.println("failed to start rmi connection");
            }
        }else{
            //client selected Socket architecture
            chooseNetworkAddress();
            startSocketClient();
        }
    }
    
    
    public static int chooseNetworkArchitecture(){
        int choice;
        System.out.println("Welcome to MyShelfie!\nPlease choose what architecture do you want to connect with:\n" +
                "To use "+Color.YELLOW+"RMI "+Color.RESET+"press 1\n" +
                "To use "+Color.YELLOW+"SOCKET " +Color.RESET+ "press 2");
        while(true){
            System.out.print("> ");
            Scanner read = new Scanner(System.in);
            try{
                choice = read.nextInt();
                if(choice != 1 && choice != 2){
                    System.out.println("invalid input, please choose '1' or '2'");
                }else{
                    break;
                }
            }catch (Exception e){
                System.out.println("invalid input, please choose '1' or '2'");
            }

        }
        return choice;
    }

    private static void startRmiClient() throws RemoteException, NotBoundException, SocketException {
        DatagramSocket datagramSocket = new DatagramSocket();
        try{
            datagramSocket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            String currentIp = datagramSocket.getLocalAddress().getHostAddress();
            System.setProperty("java.rmi.server.hostname",currentIp);
        }catch (UnknownHostException e){
            System.out.println("Cannot connect to google dns");
        }
        Registry registry = LocateRegistry.getRegistry(Ip);
        Server server = (Server) registry.lookup("server");
        ClientImpl client = new ClientImpl(server);
            client.run();
    }

    private static void startSocketClient() throws RemoteException {
        ServerStub serverStub = new ServerStub(Ip, Port);
        ClientImpl client = new ClientImpl(serverStub);
        new Thread(() -> {
            while(true){
                try {
                    serverStub.receive(client);
                } catch (RemoteException e) {
                    System.err.println("Error while receiving message from server.Closing connection...");
                    try {
                        serverStub.close();
                    } catch (RemoteException ex) {
                        System.err.println("Cannot close connection with server. Halting...");
                    }
                    System.exit(1);
                }
            }
        }).start();
            client.run();
    }



    private static void chooseNetworkAddress(){

        System.out.println("Now enter the" + Color.BLUE_BRIGHT + " Port Number " +Color.RESET + "and the " +Color.BLUE_BRIGHT+ "IP address "+Color.RESET +"of the server you want to join");
        System.out.println("PORT NUMBER (Press Enter to use default): ");
        while(true){
            System.out.print("> ");
            Scanner PortNumber = new Scanner(System.in);
            String StringPort = PortNumber.nextLine();
            if(!(Objects.equals(StringPort, ""))){
                if(isNumeric(StringPort)){
                    Port = Integer.parseInt(StringPort);
                    break;
                }else{
                    System.out.println(Color.RED + "Not numeric input! please try again..." + Color.RESET);
                }
            }else {
                break;
            }
        }

        System.out.println("IP ADDRESS (Press Enter to use default): ");
        while (true){
            System.out.print(">");
            Scanner IpAddress = new Scanner(System.in);
            String ip = IpAddress.nextLine();

            if (!isNumeric(ip) && !ip.equals("localhost") && !ip.equals("")) {
                System.err.println(Color.RED + "Not numeric input! please try again..." + Color.RESET);
            } else{
                if (!ip.equals("")) {
                    Ip = ip;
                    break;
                } else {
                    break;
                }
            }
        }
    }

    private static boolean isNumeric(String str){
        return str != null && str.matches("[0-9.]+");
    }
}
