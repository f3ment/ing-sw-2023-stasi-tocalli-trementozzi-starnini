package distributed.socket.middleware;

import distributed.Client;
import distributed.Server;
import model.Message;
import utils.Event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Arrays;

public class ServerStub implements Server {


    private final int port;
    private final String ip;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ios;

    private final Object inputlock = new Object();
    private final Object outputlock = new Object();

    public ServerStub(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void register(Client client) throws RemoteException {
        try {
            this.socket = new Socket(ip, port);
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
        if(message.getEvent().equals(Event.FINISH_MATCH)){
            try {
                Thread.sleep(10000);
            }catch (InterruptedException e){
                System.err.println("Interrupted Exception");
            }
            close();
        } else {
            try {
                synchronized (outputlock){
                    oos.writeObject(message);
                    oos.reset();
                    oos.flush();
                }
            } catch (IOException e) {
                throw new RemoteException("Cannot send Message : " + e.getMessage() );
            }
        }

    }

    public void receive(Client client) throws RemoteException{
        Message message;
            try {
                synchronized (inputlock){
                    message = (Message) ios.readObject();
                }
            } catch (IOException e) {
                throw new RemoteException("Cannot receive Message : " + e.getMessage() + Arrays.toString(e.getStackTrace()));
            } catch (ClassNotFoundException e) {
                throw new RemoteException("Cannot cast Message " + e.getMessage());
            }
        client.update(message);

    }

    public void close() throws RemoteException {
        try {
            socket.close();
        }catch(IOException e){
            throw new RemoteException("Cannot close socket " + e.getMessage());
        }
    }
}
