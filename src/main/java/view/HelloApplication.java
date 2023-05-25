package view;

import distributed.ClientImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import distributed.Server;
import java.io.IOException;
import java.rmi.RemoteException;

public class HelloApplication extends Application {

    private static Server Server;
    private static ScenesController controller;
    private static ClientImpl Client;

    private static GraphicalUI gui;

    private static Stage CurrentStage;

    @Override
    public void start(Stage stage) throws IOException {
        CurrentStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        gui.setGuiController(controller);
        controller.addObserver((o, message)-> {
            try {
                Server.update(Client, message);
            } catch (RemoteException e) {
                System.err.println("Error while updating server : " + e.getMessage() + ". Skipping the update...");
            }
        });
        stage.setTitle("MyShelfie");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void setScene(String source) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(source + ".fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        gui.setGuiController(controller);
        controller.addObserver((o, message)-> {
            try {
                Server.update(Client, message);
            } catch (RemoteException e) {
                System.err.println("Error while updating server : " + e.getMessage() + ". Skipping the update...");
            }
        });
        Scene scene = new Scene(root);
        CurrentStage.setScene(scene);
        CurrentStage.setTitle("MyShelfie-" + source);
        CurrentStage.show();
    }
    public static void  setGui(GraphicalUI gui){
        HelloApplication.gui = gui;
    }

    public static void setClientServer(Server server, ClientImpl client){
        Server = server;
        Client = client;
    }


}