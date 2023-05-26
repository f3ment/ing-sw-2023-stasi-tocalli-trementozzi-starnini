package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
//import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import utils.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Message;
import utils.Observable;


import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;

public class ScenesController extends Observable<Event> implements Initializable {

    @FXML
    private Button startButton;

    @FXML
    private GridPane boardGrid;

    @FXML
    private TextField nickname;


    @FXML
    private ChoiceBox<Integer> nPlayers = new ChoiceBox<>();

    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;
    @FXML
    private Label player4;

    private String username;
    private Object matchSize;



    /**
     * This method will change the welcome page to the login page
     * @param actionEvent the event that triggers the method , in this case the "play" button click
     */
    @FXML
    public void AskLobbyInfo(ActionEvent actionEvent) throws IOException, InterruptedException {
        new Thread(()->{
            setChanged();
            notifyObservers(new Message(Event.LOGIN,4,"toky"));
        }).start();
    }


    @FXML
    /**
     * This method retrieve the nickname from the textfield
     * @param actionEvent the event that triggers the method , in this case the "nickname" textfield
     */
    public void handleNickname(ActionEvent actionEvent) {
        username = nickname.getText();
    }


    public String getUsername() {
        return username;
    }

    @Override
    /**
     * This method initialize the choice-box with the number of players
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> options = FXCollections.observableArrayList(
                2,
                3,
                4
        );
        nPlayers.setItems(options);
    }

    @FXML
    /**
     * this method collect the player's info and change the scene to lobby
     * @param actionEvent the event that triggers the method , in this case the "join" button click
     */
    public void sendPlayerInfo(ActionEvent actionEvent) throws IOException {
        if (nickname.getText().isEmpty() || nPlayers.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input required");
            alert.setHeaderText(null);
            alert.setContentText("Please enter the required values.");
            alert.showAndWait();
            return;
        }
            matchSize = nPlayers.getValue();
            username = nickname.getText();
            new Thread(()->{
                setChanged();
                notifyObservers(new Message(Event.LOGIN, (int)matchSize, username));
            }).start();
        }


    /**
     * This method displays the current players' nicknames in the lobby
     * @param nicknames the list of the players' nicknames
     */
    public  void addPlayerNameToLobby(ArrayList<String> nicknames){
        if(player1.getText().equals("WAITING PLAYER...")){
            player1.setText(nicknames.get(0));
        }
        if(player2.getText().equals("WAITING PLAYER...") && nicknames.size()>1){
            player2.setText(nicknames.get(1));
        }
        if(player3.getText().equals("WAITING PLAYER...") && nicknames.size()>2){
            player3.setText(nicknames.get(2));
        }
        if(player4.getText().equals("WAITING PLAYER...") && nicknames.size()>3){
            player4.setText(nicknames.get(3));
        }
        //todo aggiungere dinamicamente altri giocatori in lobby
    }


    public void startGame() {
        new Thread(()->{
            setChanged();
            notifyObservers(new Message(Event.NEW_TURN));
        }).start();
    }

    public void setGridImage(String s, int i, int j) {
        Image image = new Image(getClass().getResourceAsStream(s));
        ImageView tile = new ImageView(image);
        tile.setFitHeight(90);
        tile.setFitWidth(90);
        tile.setPreserveRatio(true);
        boardGrid.add(tile, j, i);
    }

}

