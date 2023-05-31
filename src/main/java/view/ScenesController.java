package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
//import javafx.event.Event;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.Chat;
import model.ChatMessage;
import model.Type;
import model.views.ChatView;
import model.views.GameView;
import utils.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Message;
import utils.Observable;


import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScenesController extends Observable<Event> implements Initializable {
    @FXML
    private TextField chatInputText;
    @FXML
    private ListView chatArea;
    @FXML
    private Pane chatView;
    @FXML
    private ImageView gameView;
    @FXML
    private Label scoreLabel1;
    @FXML
    private Label score1;
    @FXML
    private Label name1;
    @FXML
    private ImageView shelf1;
    @FXML
    private ImageView shelf3;
    @FXML
    private ImageView shelf4;
    @FXML
    private Label name2;
    @FXML
    private Label name3;
    @FXML
    private Label name4;
    @FXML
    private Label scoreLabel4;
    @FXML
    private Label scoreLabel3;
    @FXML
    private Label scoreLabel2;
    @FXML
    private Label score2;
    @FXML
    private Label score3;
    @FXML
    private Label score4;
    @FXML
    private ImageView chair2;
    @FXML
    private ImageView chair3;
    @FXML
    private ImageView chair4;
    @FXML
    private ImageView chair1;
    @FXML
    private Button startButton;
    @FXML
    private Button retryButton;

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
    @FXML
    private Pane common1;
    @FXML
    private Pane common2;
    @FXML
    private Pane stack2;
    @FXML
    private Pane stack1;
    @FXML
    private Pane personalGoal;
    @FXML
    private Label dialogText;
    @FXML
    private GridPane shelfGrid;
    @FXML
    private Pane column1;
    @FXML
    private Pane column2;
    @FXML
    private Pane column3;
    @FXML
    private Pane column4;
    @FXML
    private Pane column5;
    @FXML
    private Tab showGame;
    @FXML
    private Tab chatButton;
    private String username;
    private Object matchSize;
    private String playerName3;
    private String playerName4;
    private String playerName2;
    private boolean myTurn;
    ObservableList<ChatMessage> chatMessages = FXCollections.observableArrayList();

    private int nDraws = 0;

    private ArrayList<ArrayList<Integer>> drawen = new ArrayList<>(0);
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private GridPane hand;

    ArrayList<Integer> tileOrder = new ArrayList<>();
    private ArrayList<ImageView> playerHand = new ArrayList<>(0);
    private boolean goodDraw;

    /**
     * This method will change the welcome page to the login page
     * @param actionEvent the event that triggers the method , in this case the "play" button click
     */
    @FXML
    public void AskLobbyInfo(ActionEvent actionEvent) throws IOException, InterruptedException {
        new Thread(()->{
            setChanged();
            notifyObservers(new Message(Event.GAME_INIT));
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

    @FXML
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
        //todo modificare lobby page
    }


    public void startGame(String username) {
        this.username = username;
        new Thread(()->{
            setChanged();
            notifyObservers(new Message(Event.NEW_TURN));
        }).start();
    }


    @FXML
    public void setGridImage(String s, int i, int j) {
        Image image = new Image(getClass().getResourceAsStream(s));
        ImageView tile = new ImageView(image);
        tile.setOnMouseClicked(event ->tileSelected(i,j,tile));
        tile.setOnMouseEntered(event -> hoverOnTiles(tile));
        tile.setOnMouseExited(event -> tile.setOpacity(1));
        tile.setFitHeight(90);
        tile.setFitWidth(90);
        tile.setPreserveRatio(true);
        boardGrid.add(tile, j, i);
    }

    private void hoverOnTiles(ImageView tile) {
        if(myTurn)
            tile.setOpacity(0.5);
    }

    @FXML
    public void showShelves(GameView model, String myName) {
        ArrayList<String> players = new ArrayList<>(model.getMapPlayerScore().keySet());
        int Nplayers = players.size();
        name1.setText(myName);
        players.remove(myName);
        playerName2 = players.get(0).toString();
        players.remove(playerName2);
        name2.setText(playerName2);
        if(Nplayers >= 3){
            shelf3.setVisible(true);
            playerName3 = players.get(0).toString();
            players.remove(playerName3);
            name3.setText(playerName3);
            name3.setVisible(true);
            scoreLabel3.setVisible(true);
            score3.setVisible(true);
            if(Nplayers == 4){
                shelf4.setVisible(true);
                playerName4 = players.get(0).toString();
                players.remove(playerName4);
                name4.setText(playerName4);
                name4.setVisible(true);
                scoreLabel4.setVisible(true);
                score4.setVisible(true);
            }
        }
    }


    @FXML
    public void setChair(GameView model,String myName) {
        String first = model.getFirstPlayer();
        if(first.equals(myName)){
            chair1.setVisible(true);
        }else if(first.equals(playerName2)){
            chair2.setVisible(true);
        }else if(first.equals(playerName3)) {
            chair3.setVisible(true);
        }else if(first.equals(playerName4)){
            chair4.setVisible(true);
        }
    }

    @FXML
    public void setCommongoals(GameView model) {
        ImageView cg1 = new ImageView(new Image(getClass().getResourceAsStream("/Images/commongoalcards/" + model.getFirstCommonGoalScource())));
        cg1.setFitHeight(150);
        cg1.setFitWidth(266);
        //cg1.setRotate(12.5);
        ImageView cg2 = new ImageView(new Image(getClass().getResourceAsStream("/Images/commongoalcards/" + model.getSecondCommonGoalScource())));
        cg2.setFitHeight(150);
        cg2.setFitWidth(266);
        //cg2.setRotate(12.5);
        common1.getChildren().add(cg1);
        common2.getChildren().add(cg2);
        ImageView s1 = new ImageView(new Image(getClass().getResourceAsStream("/Images/scoringtokens/scoring_8.jpg")));
        s1.setFitWidth(80);
        s1.setFitHeight(78);
        s1.setVisible(true);
        ImageView s2 = new ImageView(new Image(getClass().getResourceAsStream("/Images/scoringtokens/scoring_8.jpg")));
        s2.setFitWidth(80);
        s2.setFitHeight(78);
        s2.setVisible(true);
        stack1.getChildren().add(s1);
        stack1.setVisible(true);
        stack1.toFront();
        stack2.getChildren().add(s2);
        stack2.setVisible(true);
        stack2.toFront();
    }


    @FXML
    public void setPersonalGoal(GameView model) {
        ImageView pGoal = new ImageView(new Image(getClass().getResourceAsStream("/Images/personalgoalcards/Personal_Goals" + model.getPersonalGoalIndex() + ".png")));
        pGoal.setFitHeight(283);
        pGoal.setFitWidth(204);
        personalGoal.getChildren().add(pGoal);
    }

    @FXML
    public void setMyTurn(boolean b , String currentPlayer) {
        if(!b){
            dialogText.setText(currentPlayer + " is playing,\nWait for your turn...");
        }
        this.myTurn = b;
    }


    @FXML
    private void tileSelected(int i, int j, ImageView tile) {
        if(myTurn){
            if(drawen.size()<nDraws){
                ArrayList<Integer> coords = new ArrayList<>(2);
                coords.add(0,i);
                coords.add(1,j);
                drawen.add(coords);
                tile.setVisible(false);
                int position = drawen.size();
                ImageView tileCopy = new ImageView(tile.getImage());
                tileCopy.setOnMouseClicked(event ->tileOrderSelection(tileCopy,position));
                tileCopy.setFitHeight(90);
                tileCopy.setFitWidth(90);
                hand.add(tileCopy,0,3-drawen.size());
                playerHand.add(tile);
                if(drawen.size()==nDraws) {
                    new Thread(()->{
                        setChanged();
                        notifyObservers(new Message(Event.PLAYER_DRAW_POSITIVE,drawen));
                    }).start();
                }
            }
        }else{
            dialogText.setText("it's not your turn!");
        }
    }

    private void tileOrderSelection(ImageView tileCopy, int position) {
        if(myTurn){
            if(goodDraw){
                tileOrder.add(position-1);
                hand.getChildren().remove(tileCopy);
                if(tileOrder.size() == drawen.size()){
                    goodDraw = false;
                    insertInShelf();
                    dialogText.setText("Now click on the column where you \nwant to insert the tiles");
                    drawen.clear();
                }
            }
        }else{
            dialogText.setText("it's not your turn!");
        }
    }

    @FXML
    public void draw1(ActionEvent actionEvent) {
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        nDraws = 1;
        dialogText.setText("Select 1 tile from the living room");
    }

    @FXML
    public void draw2(ActionEvent actionEvent) {
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        nDraws = 2;
        dialogText.setText("Select 2 tiles from the living room");
    }

    @FXML
    public void draw3(ActionEvent actionEvent) {
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        nDraws = 3;
        dialogText.setText("Select 3 tiles from the living room");
    }

    @FXML
    public void letDraw() {
        button1.setVisible(true);
        button2.setVisible(true);
        button3.setVisible(true);
        dialogText.setText("Choose how many tiles \nyou want to draw ");
    }

    @FXML
    public void badDraw() {
        dialogText.setText("You can only draw adjacent tiles\non same column or row and they\nmust have at least one free side!");
        nDraws = 0;
        drawen.clear();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for(ImageView tile : playerHand){
            tile.setVisible(true);
            hand.getChildren().clear();
        }
        playerHand.clear();
        retryButton.setVisible(true);
    }

    public void retry(ActionEvent actionEvent) {
        dialogText.setStyle("-fx-text-fill: #000000;");
        retryButton.setVisible(false);
        letDraw();
    }

    public void goodDraw() {
        goodDraw = true;
        dialogText.setText("You have drawn " + nDraws + " tiles!\nnow click them by the order\nyou want to place them in your shelf");
        nDraws=0;
        //drawen.clear();
    }

    private void insertInShelf() {
        column1.setOnMouseEntered(event -> column1.setOpacity(0.25));
        column1.setOnMouseClicked(event -> setColumn(1));
        column1.setOnMouseExited(event -> column1.setOpacity(0));
        column2.setOnMouseEntered(event -> column2.setOpacity(0.25));
        column2.setOnMouseExited(event -> column2.setOpacity(0));
        column2.setOnMouseClicked(event -> setColumn(2));
        column3.setOnMouseEntered(event -> column3.setOpacity(0.25));
        column3.setOnMouseExited(event -> column3.setOpacity(0));
        column3.setOnMouseClicked(event -> setColumn(3));
        column4.setOnMouseEntered(event -> column4.setOpacity(0.25));
        column4.setOnMouseExited(event -> column4.setOpacity(0));
        column4.setOnMouseClicked(event -> setColumn(4));
        column5.setOnMouseEntered(event -> column5.setOpacity(0.25));
        column5.setOnMouseExited(event -> column5.setOpacity(0));
        column5.setOnMouseClicked(event -> setColumn(5));
    }

    private void setColumn(int i) {
        column1.setOnMouseClicked(null);
        column2.setOnMouseClicked(null);
        column3.setOnMouseClicked(null);
        column4.setOnMouseClicked(null);
        column5.setOnMouseClicked(null);
        column1.setOnMouseEntered(null);
        column2.setOnMouseEntered(null);
        column3.setOnMouseEntered(null);
        column4.setOnMouseEntered(null);
        column5.setOnMouseEntered(null);
        new Thread(()->{
            setChanged();
            notifyObservers(new Message(Event.PLAYER_INSERT_POSITIVE,i-1,tileOrder));
            tileOrder.clear();
        }).start();
    }

    public void insertPositive(GameView model) {
        dialogText.setText("Tiles inserted correctly!");
        updateShelf(model);
        hand.getChildren().clear();
        playerHand.clear();
    }

    public void insertNegative(GameView model) {
        dialogText.setText("You can't insert tiles in that column!");
        insertInShelf();
    }

    private void updateShelf(GameView model) {
        for(int i=0; i< model.getHeightBookshelf(); i++){
            for(int j=0; j< model.getLenghtBookshelf(); j++){
                if(model.getCurrentBookshelf()[i][j]!=null){
                    ImageView tile = new ImageView(new Image(getClass().getResourceAsStream(pickTileImage(model.getCurrentBookshelf()[i][j].getType(),model.getCurrentBookshelf()[i][j].getId()))));
                    tile.setFitHeight(63);
                    tile.setFitWidth(63);
                    shelfGrid.add(tile,j,i);
                }
            }
        }
    }

    public String pickTileImage(Type type, int id) {
        int ID = id+1;
        switch (type){
            case CATS:
                return "/Images/itemtiles/Gatti1." + ID + ".png";
            case TROPHIES:
                return "/Images/itemtiles/Trofei1." + ID + ".png";
            case PLANTS:
                return "/Images/itemtiles/Piante1." + ID + ".png";
            case BOOKS:
                return "/Images/itemtiles/Libri1." + ID + ".png";
            case FRAMES:
                return "/Images/itemtiles/Cornici1." + ID + ".png";
            case GAMES:
                return "/Images/itemtiles/Giochi1." + ID + ".png";
        }
        return null;
    }

    public void clickChatButton(){

        chatInputText.setOnAction(event -> {
            if(!chatInputText.getText().equals("")){
                String message = chatInputText.getText();
                new Thread(()->{
                    setChanged();
                    notifyObservers(new Message(Event.SEND_MESSAGE, new ChatMessage(message, username, null)));
                }).start();
                chatInputText.clear();
            }
        });

        chatArea.setItems(chatMessages);

        chatArea.setCellFactory(param -> new ListCell<ChatMessage>(){
            @Override
            protected void updateItem(ChatMessage item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText((item.getSender().equals(username)? "You" : item.getSender()) + (item.getReceiver()!=null && item.getReceiver().equals(username)? " to You" : "") + " > " + item.getMessage());
                }
            }
        });
    }

    public void updateChat(ChatMessage message){
        chatMessages.add(message);
    }

}



