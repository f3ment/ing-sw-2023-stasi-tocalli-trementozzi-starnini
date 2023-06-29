package view;

import distributed.ClientImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import distributed.Server;

import java.io.IOException;
import java.rmi.RemoteException;

import static java.lang.Math.max;

/**
 * This class is the starting point for the GUI Thread.
 * It loads the first scene and sets its scene controller, adding also all the observers of the View.
 * It also changes different scenes during the game.
 */
public class HelloApplication extends Application {

    private static Server Server;
    private static ScenesController controller;
    private static ClientImpl Client;

    private static GraphicalUI gui;

    private static Stage CurrentStage;

    /**
     * This method is the starting point for the GUI Thread that sets the first scene and its controller.
     * @param stage the stage to be set
     * @throws IOException if the FXML file is not found
     */
    @Override
    public void start(Stage stage) throws IOException {
        CurrentStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        Scale scale = new Scale();
        scale.xProperty().bind(stage.widthProperty().divide(1020));
        scale.yProperty().bind(stage.heightProperty().divide(675));
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        scale.setPivotX(0);
        scale.setPivotY(0);
        stage.setMaxHeight(2160);
        stage.setMaxWidth(4096);
        root.getTransforms().add(scale);
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
        CurrentStage.setOnCloseRequest(event -> {
            // Terminate the program
            System.exit(0);
        });
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Publishermaterial/Icon50x50px.png")));
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * This method is used to change the scenes during the game by passing a fxml source as a parameter.
     * @param source the name of the FXML file to be loaded
     * @throws IOException if the FXML file is not found
     */
    public static void setScene(String source) throws IOException {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(source + ".fxml"));
        Parent root = loader.load();
        Scale scale = new Scale();
        scale.xProperty().bind(CurrentStage.widthProperty().divide(1920));
        scale.yProperty().bind(CurrentStage.heightProperty().divide(1080));
        scale.setPivotX(0);
        scale.setPivotY(0);
        root.getTransforms().add(scale);
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
        CurrentStage.setMinWidth(800);
        CurrentStage.setMinHeight(600);
        CurrentStage.setMaxHeight(2160);
        CurrentStage.setMaxWidth(4096);
        CurrentStage.setScene(scene);
        CurrentStage.setFullScreen(true);
        CurrentStage.setOnCloseRequest(event -> {
            // Terminate the program
            System.exit(0);
        });
        CurrentStage.setTitle("MyShelfie-" + source);
        CurrentStage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream("/Images/Publishermaterial/Icon50x50px.png")));
        CurrentStage.show();
    }

    /**
     * @param gui it saves a reference to the {@code GraphicalUI}
     *
     */
    public static void  setGui(GraphicalUI gui){
        HelloApplication.gui = gui;
    }

    /**
     * @param server It saves a reference to the {@code Server} inorder to update it from the scene controller
     * @param client It saves a reference to the {@code Client} inorder to update the server from the right client
     */
    public static void setClientServer(Server server, ClientImpl client){
        Server = server;
        Client = client;
    }


}