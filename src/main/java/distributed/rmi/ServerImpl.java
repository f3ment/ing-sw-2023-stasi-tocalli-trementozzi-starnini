package distributed.rmi;

import controller.GameController;
import controller.GamesManagerController;
import distributed.Client;
import distributed.Server;
import model.Game;
import model.GameView;
import utils.Event;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerImpl extends UnicastRemoteObject implements Server {

    private GameController controller;
    private Game model;

    private GamesManagerController gamesManagerController;

    public ServerImpl() throws RemoteException {
        super();
    }

    public ServerImpl(int port) throws RemoteException {
        super(port);
    }

    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }


    //ci permette di acquisire un nuovo client
    // damiani fa un 1to1 client server e model, cioè ad ogni client è associato un nuovo model e un nuovo controller
    // la mia idea è di usare la lobby prima del model, il client si collega ad un server e con la funzione
    // register si collega alla lobby
    // todo metodo da rifare
    @Override

    public void register(Client client) throws RemoteException{
        ArrayList<String> names = new ArrayList<>();
        names.add("Michi");
        names.add("giovanni");
        try {
            this.model = new Game(names);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.model.addObserver((o,arg, columnNumber,coords,UserName) -> {
            try {
                client.update(new GameView(model), (Event) arg);
            } catch (RemoteException e) {
                System.err.println("Error while updating the client : " + e.getMessage() + ". Skipping the update...");

            }
        });
        this.controller = new GameController(model);
    }

    @Override
    public void update(Client client, Event event, Integer columnNumber, ArrayList coords, String UserName) throws RemoteException{
        if(UserName == null){
            this.controller.update(client,event,columnNumber, coords , UserName);
        }else{
            this.gamesManagerController.update(client,event,columnNumber,null,UserName);
        }

    }






}
