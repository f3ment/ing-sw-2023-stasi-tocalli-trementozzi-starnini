package distributed.socket.middleware;

import distributed.Client;
import distributed.Server;
import model.Message;
import model.views.GameView;
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
    public void update(Client client, Message message) throws RemoteException {
        try{
            oos.writeObject(message);
            oos.reset();
            oos.flush();
        }catch (IOException e){
            throw new RemoteException("Cannot send UserName : " + e.getMessage());
        }


    }

    public void receive(Client client) throws RemoteException{
        Message message;
        try{
            message = (Message) ios.readObject();
        }catch (IOException e ){
            throw new RemoteException("Cannot receive Message : " + e.getMessage());
        }catch (ClassNotFoundException e){
            throw new RemoteException("Cannot cast Message " + e.getMessage());
        }
        client.update(message);
    }

    public void close() throws RemoteException {
        try {
            socket.close();
        }catch(IOException e){
            throw new RemoteException("Cannote close socket " + e.getMessage());
        }
    }
}
