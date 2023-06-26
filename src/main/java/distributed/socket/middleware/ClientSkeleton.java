package distributed.socket.middleware;

import distributed.Client;
import distributed.Server;
import model.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * ClientSkeleton is a class that implements the Client interface. It is used to communicate with the client.
 * This class is used to manage the communication between the server and the client so that the model and the view
 * are not aware of the communication protocol.
 */
public class ClientSkeleton implements Client {

    private final ObjectOutputStream oos;
    private final ObjectInputStream ios;

    private final Object inputlock = new Object();
    private final Object outputlock = new Object();

    /**
     * Constructor
     * @param socket Socket to communicate with the client
     */
    public ClientSkeleton(Socket socket){
        try {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Cannot create output stream : "+ e.getMessage());
        }
        try {
            this.ios = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Cannot create input stream: " + e.getMessage() );
        }

    }

    /**
     * Method used to receive a message from the client and send it to the server
     * @param server Server to communicate with
     * @throws RemoteException if the server cannot be reached
     */
    public void receive(Server server) throws RemoteException{
        Message message;
        try {
            synchronized (inputlock){
                message = (Message) ios.readObject();
            }
        } catch (IOException ex) {
            throw new RuntimeException("Cannot receive event : " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Cannot deserialize event : " + ex.getMessage());
        }
        server.update(this, message);
    }

    /**
     * Method used to send a message to the client by serializing it and sending it through the socket output stream
     * @param message Message to send to the client
     * @throws RemoteException if the client cannot be reached
     */
    @Override
    public void update(Message message) throws RemoteException {
        try{
            synchronized (outputlock){
                oos.writeObject(message);
                oos.reset();
                oos.flush();
            }
        }catch(IOException e){
            throw new RemoteException("Cannot send event : " +e.getMessage());
        }
    }
}
