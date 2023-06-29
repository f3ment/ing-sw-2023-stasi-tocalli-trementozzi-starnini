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

/**
 * This class is the stub of the server.
 * It implements the server interface and it is used by the client to communicate with the server.
 * This class together with the ServerSkeleton class implements the middleware between the client and the server in the socket implementation
 * so that the model and the view are not aware of the communication protocol.
 */
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

    /**
     * This method is used by the client to register to the server.
     * It creates the socket and the input and output streams.
     * @param client the client that wants to register to the server
     * @throws RemoteException if the client cannot be registered
     */
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

    @Override
    public void update(Client client, Message message) throws RemoteException {
        try {
            synchronized (outputlock){
                oos.writeObject(message);
                oos.reset();
                oos.flush();
            }
        } catch (IOException e) {
            throw new RemoteException("Cannot send Message : " + e.getMessage());
        }
    }

    /**
     * This method receives a message from the server and notifies the client.
     * @param client the client that wants to receive a message
     * @throws RemoteException if the message cannot be received
     */
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
        if(message.getEvent() == Event.CLIENT_CLOSE){
            try {
                close();
            } catch (RemoteException e) {
                System.err.println("Cannot close socket : " + e.getMessage());
            }
        }else {
            client.update(message);
        }
    }

    /**
     * This method closes the socket.
     * @throws RemoteException if the socket cannot be closed
     */
    public void close() throws RemoteException {
        try {
            socket.close();
        }catch(IOException e){
            throw new RemoteException(e.getMessage());
        }
    }
}
