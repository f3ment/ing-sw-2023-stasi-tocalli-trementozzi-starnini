package view;

import com.google.gson.JsonArray;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
//import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import model.*;
import model.views.GameView;
import utils.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utils.Observable;


import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

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
    private DialogPane description1;
    @FXML
    private DialogPane description2;

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
    private Label winner;
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
    private ImageView boardImage;
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

    private boolean endGame = false;

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
    @FXML
    private ImageView wallpaper;

    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private GridPane shelfGrid3;
    @FXML
    private GridPane shelfGrid4;
    @FXML
    private GridPane shelfGrid2;
    @FXML
    private ImageView endToken;
    private int maxDraw;
    @FXML
    private Label secondPlace;
    @FXML
    private Label thirdPlace;
    @FXML
    private Label fourthPlace;
    @FXML
    private Label firstPlace;
    @FXML
    private Circle status2;
    @FXML
    private Circle status3;
    @FXML
    private Circle status4;
    private boolean isHovered1;
    private boolean isHovered2;


    /**
     * This method will change the welcome page to the login page
     */
    public void AskLobbyInfo(ActionEvent actionEvent) throws IOException, InterruptedException {
        new Thread(()->{
            setChanged();
            notifyObservers(new Message(Event.GAME_INIT));
        }).start();
    }

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


    /**
     * This method initialize the choice-box with the number of players
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> options = FXCollections.observableArrayList(
                2,
                3,
                4
        );
        nPlayers.setItems(options);
        nPlayers.setValue(2);
    }

    /**
     * this method collect the player's info and change the scene to lobby
     * @param actionEvent the event that triggers the method , in this case the "join" button click
     */
    public void sendPlayerInfo(ActionEvent actionEvent) throws IOException {
        if (nickname.getText().isEmpty() || nPlayers.getValue() == null) {
            showAlert("Please enter the required values.");
            return;
        }
        if(nickname.getText().length() > 15){
            showAlert("Please enter a nickname with less than 15 characters.");
            showAlert("Please enter a nickname with less than 15 characters.");
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
     * This method starts the game by sending a NEW_TURN event to the server
     * @param username the username of the last player that joined the game
     */
    public void startGame(String username, boolean myTurn) {
        this.username = username;
        if (myTurn) {
            new Thread(()->{
                setChanged();
                notifyObservers(new Message(Event.NEW_TURN));
            }).start();
        }
    }


    /**
     * this method adds itemTiles images to the board
     * @param s source of the image
     * @param i row
     * @param j column
     */
    public void setGridImage(String s, int i, int j) {
        Image image = new Image(getClass().getResourceAsStream(s));
        ImageView tile = new ImageView(image);
        tile.setOnMouseClicked(event ->tileSelected(i,j,tile));
        tile.setOnMouseEntered(event -> hoverOnTiles(tile));
        tile.setOnMouseExited(event -> tile.setOpacity(1));
        tile.setFitHeight(83);
        tile.setFitWidth(83);
        tile.setPreserveRatio(true);
        boardGrid.add(tile, j, i);
    }

    /**
     * when a tile is hovered, this method will set its opacity to 0.5
     * @param tile hovered
     */
    private void hoverOnTiles(ImageView tile) {
        if(myTurn)
            tile.setOpacity(0.5);
    }

    /**
     * this method shows the shelves and their content every turn
     * @param model the gameView
     * @param myName the player's name
     */
    public void showShelves(GameView model, String myName) {
        username = myName;
        ArrayList<String> players = new ArrayList<>(model.getMapPlayerScore().keySet());
        int Nplayers = players.size();
        name1.setText(myName);
        if(myTurn){
            updateShelf(model);
        }else {
            updateOtherShelves(model,shelfGrid,myName);
        }
        players.remove(myName);
        playerName2 = players.get(0).toString();
        if(model.getStatusByNickname(playerName2)){
            status2.fillProperty().setValue(Paint.valueOf("GREEN"));
        }else {
            status2.fillProperty().setValue(Paint.valueOf("RED"));
        }
        players.remove(playerName2);
        name2.setText(playerName2);
        updateOtherShelves(model,shelfGrid2,playerName2);
        if(Nplayers >= 3){
            shelf3.setOpacity(1);
            shelfGrid3.setVisible(true);
            playerName3 = players.get(0).toString();
            if(!model.getStatusByNickname(playerName3)){
                status3.fillProperty().setValue(Paint.valueOf("RED"));
                status3.setOpacity(0.75);
            }else {
                status3.fillProperty().setValue(Paint.valueOf("GREEN"));
                status3.setOpacity(0.75);
            }
            players.remove(playerName3);
            name3.setText(playerName3);
            name3.setOpacity(1);
            scoreLabel3.setVisible(true);
            score3.setVisible(true);
            updateOtherShelves(model,shelfGrid3,playerName3);
            if(Nplayers == 4){
                shelf4.setOpacity(1);
                shelfGrid4.setVisible(true);
                playerName4 = players.get(0).toString();
                if(!model.getStatusByNickname(playerName4)){
                    status4.fillProperty().setValue(Paint.valueOf("RED"));
                    status4.setOpacity(0.75);
                }else {
                    status4.fillProperty().setValue(Paint.valueOf("GREEN"));
                    status4.setOpacity(0.75);
                }
                players.remove(playerName4);
                name4.setText(playerName4);
                name4.setOpacity(1);
                scoreLabel4.setVisible(true);
                score4.setVisible(true);
                updateOtherShelves(model,shelfGrid4,playerName4);
            }
        }
    }

    /**
     * this method update the shelf of the player
     * @param model the gameView
     */
    private void updateShelf(GameView model) {
        cleanShelf(shelfGrid);
        for(int i=0; i< model.getHeightBookshelf(); i++){
            for(int j=0; j< model.getLenghtBookshelf(); j++){
                if(model.getCurrentBookshelf()[i][j]!=null){
                    ImageView tile = new ImageView(new Image(getClass().getResourceAsStream(pickTileImage(model.getCurrentBookshelf()[i][j].getType(),model.getCurrentBookshelf()[i][j].getId()))));
                    tile.setFitHeight(63);
                    tile.setFitWidth(66);
                    shelfGrid.add(tile,j,i);
                }
            }
        }
    }

    /**
     * this method clean the shelf and remove references to Imageviews
     * @param shelfGrid the grid of the shelf
     */
    private void cleanShelf(GridPane shelfGrid) {
        for (Node node : shelfGrid.getChildren()) {
            if (node instanceof ImageView) {
                ((ImageView) node).setImage(null);}
        }
        shelfGrid.getChildren().clear();
    }

    /**
     * this method update the shelves of the other players
     * @param model the gameView
     * @param shelfGrid the grid of the shelf
     * @param playerName the player's name
     */
    public void updateOtherShelves(GameView model, GridPane shelfGrid, String playerName) {
        cleanShelf(shelfGrid);
        for(int i=0; i< model.getHeightBookshelf(); i++){
            for(int j=0; j< model.getLenghtBookshelf(); j++){
                if((model.getPlayerByUsername().get(playerName)).getBookshelf()[i][j]!=null){
                    ImageView tile = new ImageView(new Image(getClass().getResourceAsStream(pickTileImage(( model.getPlayerByUsername().get(playerName)).getBookshelf()[i][j].getType(), (model.getPlayerByUsername().get(playerName)).getBookshelf()[i][j].getId()))));
                    int height;
                    int width;
                    if(playerName.equals(username)){
                        height = 63;
                        width = 66;
                    }else{
                        height = 24;
                        width = 30;
                    }
                    tile.setFitHeight(height);
                    tile.setFitWidth(width);
                    shelfGrid.add(tile,j,i);
                }
            }
        }
    }

    /**
     * this method place and show the first player armchair
     * @param model the gameView
     * @param myName the player's name
     */
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

    /**
     * this method displays the two extracted common goals that are valid for the current match
     * @param model the gameView
     */

    public void setCommongoals(GameView model) {
        ImageView cg1 = new ImageView(new Image(getClass().getResourceAsStream("/Images/commongoalcards/" + model.getFirstCommonGoalSource())));
        cg1.setFitHeight(150);
        cg1.setFitWidth(266);
        ImageView cg2 = new ImageView(new Image(getClass().getResourceAsStream("/Images/commongoalcards/" + model.getSecondCommonGoalSource())));
        cg2.setFitHeight(150);
        cg2.setFitWidth(266);
        common1.getChildren().add(cg1);
        common2.getChildren().add(cg2);

        description1.setContentText(model.getFirstCommonGoalDescription());
        description2.setContentText(model.getSecondCommonGoalDescription());
        common1.setOnMouseEntered(event -> {
            isHovered1 = true;
            description1.setVisible(true);
            });
        common1.setOnMouseExited(event -> {
            isHovered1 = false;
            description1.setVisible(false);
        });
        common2.setOnMouseEntered(event -> {
            isHovered2 = true;
            description2.setVisible(true);
        });
        common2.setOnMouseExited(event -> {
            isHovered2 = false;
            description2.setVisible(false);
        });

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
        description1.toFront();
        description2.toFront();
    }

    /**
     * this method displays the personal goal of the player
     * @param model the gameView
     * @param username the player's name
     */
    public void setPersonalGoal(GameView model, String username) {
        ImageView pGoal = new ImageView(new Image(getClass().getResourceAsStream("/Images/personalgoalcards/Personal_Goals" + model.getPersonalGoalIdByUsername(username) + ".png")));
        pGoal.setFitHeight(283);
        pGoal.setFitWidth(204);
        personalGoal.getChildren().add(pGoal);
    }

    /**
     * this method sets player turn:
     * if it is not the player's turn, the player cannot interact with the interface and is graphically notified
     * @param b true if it's my turn, false otherwise
     * @param currentPlayer the current player
     */
    public void setMyTurn(boolean b , String currentPlayer) {
        if(!b){
            dialogText.setText(currentPlayer + " is playing,\nWait for your turn...");
        }
        this.myTurn = b;
    }


    /**
     * this method removes the selected tiles from the board,
     * and it adds the selected tiles to the hand in order to send them
     * to the server when hand is full
     *
     * @param i the row of the tile
     * @param j the column of the tile
     * @param tile the tile to be selected
     */
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

    /**
     * this method orders the tiles in the hand as the player wants
     * @param tileCopy the tile's image to be ordered
     * @param position the position of the tile in the hand
     */
    private void tileOrderSelection(ImageView tileCopy, int position) {
        if(myTurn){
            if(goodDraw){
                tileCopy.setOnMouseClicked(null);
                tileOrder.add(position-1);
                tileCopy.setFitWidth(75);
                tileCopy.setFitHeight(75);
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

    /**
     * method invoked when the player chooses to draw one tile
     * @param actionEvent the event that triggers the method
     */
    public void draw1(ActionEvent actionEvent) {
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        nDraws = 1;
        dialogText.setText("Select 1 tile from the living room");
    }

    /**
     * method invoked when the player chooses to draw two tiles
     * @param actionEvent the event that triggers the method
     */
    public void draw2(ActionEvent actionEvent) {
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        nDraws = 2;
        dialogText.setText("Select 2 tiles from the living room");
    }

    /**
     * method invoked when the player chooses to draw three tiles
     * @param actionEvent the event that triggers the method
     */
    public void draw3(ActionEvent actionEvent) {
        button1.setVisible(false);
        button2.setVisible(false);
        button3.setVisible(false);
        nDraws = 3;
        dialogText.setText("Select 3 tiles from the living room");
    }

    /**
     * this method shows the buttons to choose how many tiles to draw
     * @param max the maximum number of tiles that can be drawn
     */
    public void letDraw(int max) {
        maxDraw = max;
        switch (max){
            case 1:
                button1.setVisible(true);
                break;
            case 2:
                button1.setVisible(true);
                button2.setVisible(true);
                break;
            case 3:
                button1.setVisible(true);
                button2.setVisible(true);
                button3.setVisible(true);
                break;
        }
        dialogText.setText("Choose how many tiles \nyou want to draw ");
    }

    /**
     * this method is invoked when tiles drawn are not valid,
     * it notifies the player, and it clears the hand making the player choose again
     */
    public void badDraw() {
        boardGrid.setEffect(new MotionBlur(10,10));
        boardImage.setEffect(new MotionBlur(10,10));
        wallpaper.setEffect(new MotionBlur(10,10));
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
        }
        for(Node node : hand.getChildren()){
            if(node instanceof ImageView){
                ((ImageView) node).setImage(null);
            }
        }
        hand.getChildren().clear();
        playerHand.clear();
        retryButton.setVisible(true);
    }

    /**
     * this method is invoked when the player has to retry drawing tiles
     * @param actionEvent the event that triggers the method
     */
    public void retry(ActionEvent actionEvent) {
        boardGrid.setEffect(null);
        boardImage.setEffect(new DropShadow());
        wallpaper.setEffect(null);
        retryButton.setVisible(false);
        letDraw(maxDraw);
    }

    /**
     * this method is invoked when the player has drawn tiles correctly
     */
    public void goodDraw() {
        goodDraw = true;
        dialogText.setText("You have drawn " + nDraws + " tiles!\nnow click them by the order\nyou want to place them in your shelf");
        nDraws=0;
    }

    /**
     * this method is invoked when the player has to choose the column where to place the tiles
     * when the player clicks on a column, the tiles are placed in the shelf accordingly
     */
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

    /**
     * this method sends the chosen column to the model
     * @param i the column where the tiles have to be placed
     */
    private void setColumn(int i) {
        for(Node tile : hand.getChildren()){
            if(tile instanceof ImageView)
                tile.setVisible(false);
        }
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
        }).start();
    }

    /**
     * this method is invoked when the player has inserted correctly the tiles in the shelf
     * it notifies the player, and it clears the hand, eventually it notifies the server and the turn ends
     * @param model the model of the game
     */
    public void insertPositive(GameView model) {
        dialogText.setText("Tiles inserted correctly!");
        updateShelf(model);
        for(Node node : hand.getChildren()){
            if(node instanceof ImageView){
                ((ImageView) node).setImage(null);
            }
        }
        hand.getChildren().clear();
        tileOrder.clear();
        playerHand.clear();
        if(model.myBookshelfIsFull() && !endGame) {
            setEndGameToken(model);
        }
        new Thread(()->{
            setChanged();
            notifyObservers(new Message(Event.PLAYER_FINISH));
        }).start();
    }

    /**
     * this method is invoked when the player has inserted incorrectly the tiles in the shelf
     * @param model the model of the game
     */
    public void insertNegative(GameView model) {
        dialogText.setText("You can't insert tiles in that column!\nPlease choose another one");
        for(Node node : hand.getChildren()){
            if(node instanceof ImageView){
                node.setVisible(true);
            }
        }
        insertInShelf();
    }

    /**
     * this method returns the path of the image of the tile
     *
     * @param type the type of the tile
     * @param id the id of the tile
     * @return the path of the image of the tile
     */
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

    /**
     * this method is invoked when the player enters the chat and sends
     * a message to a private chat or to all players
     * it sends the information to the model that handles the chat
     */
    public void clickChatButton(){

        inizializeChatBox();

        chatInputText.setOnAction(event -> {
            if(!chatInputText.getText().equals("")){
                String message = chatInputText.getText();
                new Thread(()->{
                    setChanged();
                    notifyObservers(new Message(Event.SEND_MESSAGE, new ChatMessage(message, username, choiceBox.getValue().equals("All")? null : choiceBox.getValue().substring(19))));
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
                    setText((item.getSender().equals(username)? "You" : item.getSender()) + (item.getReceiver()!=null?( item.getReceiver().equals(username)? " to You" : (item.getSender().equals(username)? " to " + item.getReceiver() : "")) : "") + " > " + item.getMessage());
                }
            }
        });
    }

    /**
     * this method initializes the choice box of the chat
     */
    private void inizializeChatBox() {
        if(choiceBox.getItems().isEmpty()){
            choiceBox.getItems().add("All");
            if (playerName2 != null) {
                choiceBox.getItems().add("Private message to " + playerName2);
            }
            if (playerName3 != null) {
                choiceBox.getItems().add("Private message to " + playerName3);
            }
            if (playerName4 != null) {
                choiceBox.getItems().add("Private message to " + playerName4);
            }
            choiceBox.setValue("All");
        }
    }

    /**
     * this method updates the chat with a new message
     * @param message the message to be added to the chat
     */
    public void updateChat(ChatMessage message){
        chatMessages.add(message);
        chatArea.scrollTo(chatArea.getItems().size()-1);
    }

    /**
     * this method updates the players' scores every turn
     * @param model the model of the game
     * @param username the username of the player
     */
    public void updateScores(GameView model,String username) {
        score1.setText(String.valueOf(model.getMapPlayerScore().get(username)));
        score2.setText(String.valueOf(model.getMapPlayerScore().get(name2.getText())));
        if(model.getMapPlayerScore().size()>2){
            score3.setText(String.valueOf(model.getMapPlayerScore().get(name3.getText())));
            if(model.getMapPlayerScore().size()>3){
                score4.setText(String.valueOf(model.getMapPlayerScore().get(name4.getText())));
            }
        }
    }

    /**
     * this method updates the stack of the common goals every turn
     * @param model the model of the game
     */
    public void updateStack(GameView model) {
        int sizeStack1 = model.getFirstCommonGoal().size();
        int sizeStack2 = model.getSecondCommonGoal().size();
        getNewStackScore(sizeStack1,model,1,stack1);
        getNewStackScore(sizeStack2,model,2,stack2);
    }

    /**
     * this method get the current stack size of a common
     * goal and sets the image of the relative scoring token
     *
     * @param sizeStack the size of the stack
     * @param model the model of the game
     * @param commonNumber the number of the common goal
     * @param stack the stack of the common goal
     */
    private void getNewStackScore(int sizeStack,GameView model,int commonNumber,Pane stack){
        if(sizeStack == 0) {
            stack.setVisible(false);
        }else{
            switch (model.getScoringTokenByNumber(commonNumber,sizeStack - 1).getScore()) {
                case 2:
                    setStackToken(2,stack);
                    break;
                case 4:
                    setStackToken(4,stack);
                    break;
                case 6:
                    setStackToken(6,stack);
                    break;
                case 8:
                    setStackToken(8,stack);
                    break;
            }
        }
    }

    /**
     * this method sets the image of the scoring token
     * @param i the number of the scoring token
     * @param stack the stack of the common goal
     */
    private void setStackToken ( int i,Pane stack){
        stack.getChildren().clear();
        ImageView s1 = new ImageView(new Image(getClass().getResourceAsStream("/Images/scoringtokens/scoring_" + i + ".jpg")));
        s1.setFitWidth(80);
        s1.setFitHeight(78);
        s1.setVisible(true);
        stack.getChildren().add(s1);
        stack.setVisible(true);
        stack.toFront();
    }

    /**
     * this method clears the board when needed
     */
    public void cleanBoard() {
        for(ImageView i : boardGrid.getChildren().stream().filter(node -> node instanceof ImageView).map(node -> (ImageView) node).collect(Collectors.toList()))
            i.setImage(null);
        boardGrid.getChildren().clear();
    }

    @FXML
    /**
     * this method sets the end game beside the player who first completes his shelf
     * @param model the model of the game
     */
    public void setEndGameToken(GameView model) {
        chair1.setVisible(true);
        chair1.setImage(endToken.getImage());
        chair1.setRotate(-13);
        endToken.setImage(null);
        endToken.setVisible(false);
    }

    /**
     * this method checks if the end game token has been assigned
     * @param model the model of the game
     */
    public void checkEndTokenAssigned(GameView model) {
        this.endGame = model.getEndGameToken();
        if(endGame){
            dialogText.setText(model.GetShelfCompletedBy().toUpperCase() + " has completed his shelf!");
            if(model.GetShelfCompletedBy().equals(playerName2)){
                chair2.setVisible(true);
                chair2.setImage(endToken.getImage());
                chair2.setRotate(-13);
            }else if(model.GetShelfCompletedBy().equals(playerName3)){
                chair3.setVisible(true);
                chair3.setImage(endToken.getImage());
                chair3.setRotate(-13);
            }else if(model.GetShelfCompletedBy().equals(playerName4)){
                chair4.setVisible(true);
                chair4.setImage(endToken.getImage());
                chair4.setRotate(-13);
            }
            endToken.setImage(null);
            endToken.setVisible(false);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * this method sets the final scene and displays the winner
     * it also shows the entire ranking of the players
     * @param model the model of the game
     */
    public void showWinner(GameView model) {
        int onlinePlayer = 0;
        for (String p : model.getMapPlayerScore().keySet()) {
            if (model.getStatusByNickname(p)) {
                onlinePlayer++;
            }
        }
        winner.setText(model.getPlayerNameByRanking(0) + " WON!");
        firstPlace.setText("1st: " + model.getPlayerNameByRanking(0) + " " + model.getMapPlayerScore().get(model.getPlayerNameByRanking(0)));
        if (onlinePlayer >= 2) {
            secondPlace.setText("2nd: " + model.getPlayerNameByRanking(1) + " " + model.getMapPlayerScore().get(model.getPlayerNameByRanking(1)));
            secondPlace.setVisible(true);
            if (onlinePlayer >= 3) {
                thirdPlace.setText("3rd: " + model.getPlayerNameByRanking(2) + " " + model.getMapPlayerScore().get(model.getPlayerNameByRanking(2)));
                thirdPlace.setVisible(true);
                if (onlinePlayer == 4) {
                    fourthPlace.setText("4th: " + model.getPlayerNameByRanking(3) + " " + model.getMapPlayerScore().get(model.getPlayerNameByRanking(3)));
                    fourthPlace.setVisible(true);
                }
            }
        }
    }


    /**
     * this method sends to the server the request to delete the match
     */
    public void deleteMatch(){
        new Thread(){
            @Override
            public void run(){
                setChanged();
                notifyObservers(new Message(Event.DELETE_MATCH));
            }
        }.start();
    }

    /**
     * this method shows an alert when the player tries to log into a lobby with an unavailable nickname
     * or when the player tries to reconnect to his lobby with a different lobby size
     * @param errorMessage the error message to be displayed in the alert
     */
    public void showAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Please retry");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage.substring(7,errorMessage.length()-4));
        alert.showAndWait();
    }
}



