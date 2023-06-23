package distributed.socket.middleware;

import distributed.Client;
import distributed.Server;
import model.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientSkeleton implements Client {

    private final ObjectOutputStream oos;
    private final ObjectInputStream ios;

    private final Object inputlock = new Object();
    private final Object outputlock = new Object();

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
