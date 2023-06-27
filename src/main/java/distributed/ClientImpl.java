package distributed;

import javafx.application.Application;
import model.Message;
import utils.Event;
import view.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class implements client.
 * It links the user's view (either textual or graphical)
 * to the communication protocol.
 */
public class ClientImpl extends UnicastRemoteObject implements Client, Runnable {

    private final View view;
    private final int choice;
    private final Server sr;
    private final Client cl=this;

    private Timer timerPong;

    /**
     * This method is used to create a new client and initialize it with the
     * server to which it is connected and the view it uses to communicate
     * with the user (either textual or graphical).
     * @param server the server to which the client is connected
     * @throws RemoteException if the server is unreachable
     */
    public ClientImpl(Server server) throws RemoteException {
        super();
        choice = chooseGraphicSettings();
        if(choice ==1)
            view = new TextualUI();
        else{
            view = new GraphicalUI();
        }
        sr=server;
        try {
            initialize(server);
        } catch (RemoteException e){
            System.err.println(Color.RED_BOLD + "Server unreachable!" );
            System.err.println(Color.RED + "Client is unable to establish a connection to the server, either because the server is offline or there is an issue with the network connection.\n\n");
            e.printStackTrace();
        }
    }

    /**
     * This method is used to ask the user to choose which User Interface he wants to use.
     * The user can choose between a textual or a graphical interface.
     * The field choice is set to 1 if the user chooses the textual interface, 2 otherwise.
     * @return the choice of the user, which is represented by an integer value that can be either 1 or 2
     */
    private static int chooseGraphicSettings() {
        int graphicSettings;
        System.out.println("Please choose whether playing from COMMAND-LINE or from the GRAPHICAL APP:\n" +
                "To play with "+Color.YELLOW+"CLI "+Color.RESET+"press 1\n" +
                "To play with "+Color.YELLOW+"GUI " +Color.RESET+ "press 2");
        do {
            System.out.print("> ");
            Scanner read = new Scanner(System.in);
            try {
                graphicSettings = read.nextInt();
                if (graphicSettings != 1 && graphicSettings != 2) {
                    System.out.println("invalid input, please choose '1' or '2'");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("invalid input, please choose '1' or '2'");
            }
        } while (true);
        return graphicSettings;
    }

    /** This method is used to initialize the client with the server to which it is connected.
     * @param server the server to which the client is connected
     * @throws RemoteException if the server is unreachable
     */
    private void initialize(Server server) throws RemoteException{
        server.register(this);
        if(choice == 1){
            view.addObserver((o, message)-> {
                try {
                    server.update(this, message);
                } catch (RemoteException e) {
                    System.err.println("Error while updating server : " + e.getMessage() + ". Skipping the update...");
                }
            });
        }else {

            HelloApplication.setClientServer( server, this);
        }
    }

    /**
     * This method is used to send a message to the user interface.
     * @param message message to be sent to the user
     * @throws RemoteException if the server is unreachable
     */
    @Override
    public void update(Message message) throws RemoteException {
        //if message.finale--> clientimpl.close();
        if(message.getEvent().equals(Event.CLIENT_CLOSE)){
            System.out.println("Client is closing...");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.close();
        }else if(message.getEvent().equals(Event.PING)){
            timerPong.cancel();
            timerPong = new Timer();
            timerPong.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.err.println(Color.RED_BOLD + "Server unreachable!" + Color.RESET);
                    view.close();
                }
            },12000);
        }else {
            new Thread(() -> view.update(message)).start();
        }
    }

    /**
     * This method is used to start the client interface thread
     * and the thread that periodically sends a ping message to the server.
     */
    @Override
    public void run() throws RuntimeException {
        if(choice == 1)
            new Thread(view::run).start();
        else {
            new Thread(() -> {
                view.run();
                HelloApplication.setGui((GraphicalUI) view);
                Application.launch(HelloApplication.class);
            }).start();
        }

        new Thread(){
            @Override
            public void run(){
                while (true) {
                  try{
                      Thread.sleep(200);
                  }catch (InterruptedException e){
                      throw new RuntimeException();
                  }
                   try{
                       sr.update(cl,new Message(Event.PING));
                   }catch (RemoteException e){
                       Thread.interrupted();
                       System.err.println(Color.RED_BOLD + "Error server side! Terminal is closing..." + Color.RESET);
                       try {
                           Thread.sleep(5000);
                       } catch (InterruptedException ex) {
                           throw new RuntimeException(ex);

                       }
                       view.close();
                       //TODO close per cli e gui
                   }
                }
            }
        }.start();


        timerPong=new Timer();
        timerPong.schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println(Color.RED_BOLD + "Server unreachable!" + Color.RESET);
                view.close();
            }
        },12000);
    }

    public void close(){
        view.close();
    }
}
