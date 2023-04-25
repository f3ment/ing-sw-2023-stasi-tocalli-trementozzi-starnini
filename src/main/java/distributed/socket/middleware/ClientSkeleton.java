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

public class ClientSkeleton implements Client {

    private final ObjectOutputStream oos;
    private final ObjectInputStream ios;

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

    @Override
    public void update(GameView o, Event arg) throws RemoteException {
        try{
            oos.writeObject(o);
            oos.reset();
        }catch(IOException e){
            throw new RemoteException("Cannot send event : " +e.getMessage());
        }
        try{
            oos.writeObject(arg);
            oos.reset();
        }catch(IOException e){
            throw new RemoteException("Cannot send event : " +e.getMessage());
        }
    }

    public void receive(Server server) throws RemoteException{
        Event e;
        try {
            e =(Event) ios.readObject();
        } catch (IOException ex) {
            throw new RuntimeException("Cannot receive event : " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Cannot deserialize event : " + ex.getMessage());
        }
        Integer cn;
        try {
            cn =(Integer) ios.readObject();
        } catch (IOException ex) {
            throw new RuntimeException("Cannot receive event : " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Cannot deserialize event : " + ex.getMessage());
        }

        ArrayList coords;
        try {
            coords =(ArrayList) ios.readObject();
        } catch (IOException ex) {
            throw new RuntimeException("Cannot receive event : " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Cannot deserialize event : " + ex.getMessage());
        }
        String UserName;
        try {
            UserName =(String) ios.readObject();
        } catch (IOException ex) {
            throw new RuntimeException("Cannot receive event : " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Cannot deserialize event : " + ex.getMessage());
        }
        server.update(this, e, cn, coords , UserName);

    }
}
