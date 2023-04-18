package distributed.socket.middleware;

import distributed.Client;
import distributed.Server;
import model.GameView;
import utils.Event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ServerStub implements Server {


    private final int port;
    private final String ip;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ios;

    public ServerStub(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void register(Client client) throws RemoteException {
        try {
            this.socket = new Socket(ip, port);
            //ordine importante, fare diversamente crea deadlock
            try{
                this.oos = new ObjectOutputStream(socket.getOutputStream());
            }catch(IOException e){
                throw new RemoteException("Cannot create output stream : " + e.getMessage());
            }
            try{
                this.ios = new ObjectInputStream(socket.getInputStream());
            }catch (IOException e){
                throw new RemoteException("Cannot create input stream : " + e.getMessage());
            }
        } catch (IOException e) {
            throw new RemoteException("Error while connecting to server : " + e.getMessage());
        }
    }


    //client che manda gli oggetti
    @Override
    public void update(Client client, Event event, Integer columnNumber, ArrayList coords) throws RemoteException {
        try{
            oos.writeObject(event);
            oos.writeObject(columnNumber);
            oos.writeObject(coords);
        }catch (IOException e){
            throw new RemoteException("Cannot send event : " + e.getMessage());
        }
    }

    public void receive(Client client) throws RemoteException{
        GameView o;
        try{
            o = (GameView) ios.readObject();
        }catch (IOException e ){
            throw new RemoteException("Cannot receive event : " + e.getMessage());
        }catch (ClassNotFoundException e){
            throw new RemoteException("Cannot cast event : " + e.getMessage());
        }

        Event arg;
        try{
            arg = (Event) ios.readObject();
        }catch (IOException e ){
            throw new RemoteException("Cannot receive event : " + e.getMessage());
        }catch (ClassNotFoundException e){
            throw new RemoteException("Cannot cast event : " + e.getMessage());
        }

        client.update(o,arg);
    }

    public void close() throws RemoteException {
        try {
            socket.close();
        }catch(IOException e){
            throw new RemoteException("Cannote close socket " + e.getMessage());
        }
    }
}
