package model.views;

import model.*;
import model.board.Board;

import java.io.Serializable;
import java.util.*;

/**
 * This class is the immutable representation of the Game class.
 */
public class GameView implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Game model;

    /**
     * Creates a new GameView object from a Game object.
     * @param model the Game object to be represented
     */
    public GameView(Game model) {
        if (model == null) {
            throw new IllegalArgumentException();
        }
        this.model = model;
    }

    /**
     * @return an immutable representation of the Game's board
     */
    public BoxView[][] getBoard() {
        Board board=model.getBoard();
        BoxView[][] viewBoard= new BoxView[board.getMaxHeight()][board.getMaxLength()];
        for(int i=0;i< board.getMaxHeight();i++){
            for(int j=0;j< board.getMaxLength();j++){
                try{
                    viewBoard[i][j]= new BoxView(board.getBox(i, j).getValid(), board.getBox(i,j).getItemContained());
                }catch (Exception e){
                    viewBoard[i][j]=null;
                }
            }
        }
        return viewBoard;
    }

    /**
     * @return an immutable representation of the Game's list of players
     */
    public ArrayListView getPlayerList(){
        ArrayList<PlayerView> playerViews = new ArrayList<>();
        model.getListPlayer().forEach(e ->playerViews.add(new PlayerView(e)));
        return new ArrayListView(playerViews);
    }

    /**
     * @return a map of the players' usernames and their scores
     */
    public Map<String, Integer> getMapPlayerScore(){
        List<Player> playerList = model.getListPlayer();
        //ArrayListView returnPlayerList = new ArrayListView((ArrayList) playerList);
        Map<String, Integer> returnMap = new HashMap<>();
        for(int i=0; i<getNumPlayer(); i++){
            returnMap.put(playerList.get(i).getUsername(), playerList.get(i).getScore());
        }
        return returnMap;
    }

    /**
     * @return an immutable representation of the Game's list of Bookshelves
     */
    public ArrayListView getListBookshelf(){
        List<Bookshelf> bookshelfList= model.getListBookshelf();
        List<ItemTiles[][]> viewBookshelfList= new ArrayList<>();
        for(int i=0;i<bookshelfList.size();i++){
            viewBookshelfList.add(new ItemTiles[bookshelfList.get(i).getHeight()][bookshelfList.get(i).getLength()]);
            for(int k=0;k< bookshelfList.get(i).getHeight();k++){
                for(int j=0;j<bookshelfList.get(i).getLength();j++){
                    try{
                        viewBookshelfList.get(i)[k][j]= new ItemTiles(bookshelfList.get(i).getItem(k,j).getType(), bookshelfList.get(i).getItem(k,j).getId());
                    }catch (Exception e){
                        viewBookshelfList.get(i)[k][j]=null;
                    }
                }
            }
        }
        ArrayListView viewBookshelfList2= new ArrayListView((ArrayList) viewBookshelfList);
        return viewBookshelfList2;
    }

    /**
     * @return A map of current game usernames and their corresponding player instances
     */
    public Map<String,PlayerView> getPlayerByUsername(){
        Map<String,PlayerView> mapPlayerByUsername = new HashMap<>();
        for (Player player : model.getListPlayer()) {
            mapPlayerByUsername.put(player.getUsername(), new PlayerView(player));
        }
        return mapPlayerByUsername;
    }


    /**
     * This method retuurns the bookshelf at index x in the list of bookshelves
     * @param x the index of the Bookshelf to be returned
     * @return an immutable representation of the Bookshelf at index x
     */
    public ItemTiles[][] getParticularBookshelf(int x){
        return (ItemTiles[][])getListBookshelf().get(x);
    }

    /**
     * @return the bookshelf of the current player who is playing
     */
    public ItemTiles[][] getCurrentBookshelf(){
        Bookshelf bookshelf=model.getCurrentBookshelf();
        ItemTiles[][] viewCurrentBookshelf= new ItemTiles[bookshelf.getHeight()][bookshelf.getLength()];
        for(int i=0;i< bookshelf.getHeight();i++){
            for(int j=0;j< bookshelf.getLength();j++){
                try{
                    viewCurrentBookshelf[i][j]= new ItemTiles(bookshelf.getItem(i,j).getType(), bookshelf.getItem(i,j).getId());
                }catch (Exception e){
                    viewCurrentBookshelf[i][j]=null;
                }
            }
        }
        return viewCurrentBookshelf;
    }

    /**
     * @return false if the end game token is on the board, true it has been already assigned to the player who completed the board
     */
    public boolean getEndGameToken(){
        return model.getEndGameToken();
    }

    public ArrayListView getPickedCards(){
        ArrayList<ItemTiles> hand= model.getPickedCards();
        ArrayList<ItemTiles> viewHand= new ArrayList<ItemTiles>();
        for (ItemTiles itemTiles : hand) {
            viewHand.add(itemTiles);
        }
        ArrayListView viewHandreturn = new ArrayListView(viewHand);
        return viewHandreturn;
    }

    /**
     * @return The first common goal of the game
     */
    public ArrayListView getFirstCommonGoal(){
        Stack<ScoringToken> stack=model.getFirstCommonGoal().getStack();
        ArrayList<ScoringToken> viewStack= new ArrayList<>();
        for (ScoringToken scoringToken : stack) {
            viewStack.add(new ScoringToken(scoringToken.getScore(), scoringToken.getNumber()));
        }
        ArrayListView viewStack2= new ArrayListView(viewStack);
        return viewStack2;
    }

    /**
     * @return The second common goal of the game
     */
    public ArrayListView getSecondCommonGoal(){
        Stack<ScoringToken> stack=model.getSecondCommonGoal().getStack();
        ArrayList<ScoringToken> viewStack= new ArrayList<ScoringToken>();
        for(int i=0;i<stack.size();i++){
            viewStack.add(new ScoringToken(stack.get(i).getScore(),stack.get(i).getNumber()));
        }
        return new ArrayListView(viewStack);
    }

    /**
     * @return the first common goal literal description
     */
    public String getFirstCommonGoalDescription(){
        return model.getFirstCommonGoal().toString();
    }

    /**
     * @return the second common goal literal description
     */
    public String getSecondCommonGoalDescription(){
        return model.getSecondCommonGoal().toString();
    }

    /**
     * @return the first common goal image resource
     */
    public String getFirstCommonGoalSource(){
        return model.getFirstCommonGoal().getSource();
    }

    /**
     * @return the second common goal image resource
     */
    public String getSecondCommonGoalSource(){
        return model.getSecondCommonGoal().getSource();
    }

    /**
     * @param number the number of the scoring token to be returned
     * @param stackPosition the position of the scoring token in the stack
     * @return the scoring token with the given number and position
     */
    public ScoringToken getScoringTokenByNumber(int number, int stackPosition){
        switch (number){
            case 1:
                return getScoringToken1(stackPosition);
            case 2:
                return getScoringToken2(stackPosition);
        }
        return null;
    }

    /**
     * @param x the position of the scoring token in the stack
     * @return the scoring token at position x in the first common goal stack
     */
    public ScoringToken getScoringToken1(int x){
        return (ScoringToken) getFirstCommonGoal().get(x);
    }

    /**
     * @param x the position of the scoring token in the stack
     * @return the scoring token at position x in the second common goal stack
     */
    public ScoringToken getScoringToken2(int x){
        return (ScoringToken) getSecondCommonGoal().get(x);
    }

    /**
     * @return the height of the bookshelf
     */
    public int getHeightBookshelf(){
        return model.getCurrentBookshelf().getHeight();
    }

    /**
     * @return the length of the bookshelf
     */
    public int getLenghtBookshelf(){
        return model.getCurrentBookshelf().getLength();
    }

    /**
     * @return the height of the board
     */
    public int getHeightBoard(){
        return model.getBoard().getMaxHeight();
    }

    /**
     * @return the length of the board
     */
    public int getLenghtBoard(){
        return model.getBoard().getMaxLength();
    }

    /**
     * @return the player who is playing
     */
    public PlayerView getCurrentPlayer(){
        return new PlayerView(model.getCurrentPosition().getPlayer());
    }


    /**
     * retrieve the status of the player with the given nickname
     * @param nickname the nickname of the player to be returned
     * @return true if the player with the given nickname is online and false otherwise
     */
    public boolean getStatusByNickname(String nickname){
        return model.getPlayerByNickname().get(nickname).getStatus();
    }


    /**
     * this method returns the player hand's card at position x
     * @param x the position in the player cards hand
     * @return the card at position x in the player cards hand
     */
    public ItemTiles getHand(int x){
        return (ItemTiles)getPickedCards().get(x);
    }

    /**
     * @return true if a player has already completed his shelf and false otherwise
     */
    public boolean getEndGame(){
        return model.getEndGame();
    }

    /**
     * @return the player who started at first position
     */
    public String getFirstPlayer(){
        return model.getFirstPlayer();
    }

    /**
     * @return the last player of the turn
     */
    public String getLastPlayer(){
        return model.getLastPlayer();
    }

    /**
     * @return the winner of the game (who has the highest score)
     */
    public String getWinner(){
        return model.getWinner();
    }

    /**
     * this method returns the maximum number of drawable tiles based on the current player bookshelf
     * based on the shelf column with the lowest number of tiles
     * @return the maximum number of drawable tiles
     */
    public int getMaxDrawable(){
        return model.getCurrentBookshelf().getMaxDrowable();
    }

    /**
     * @return the score of the current player
     */
    public int getScore(){
        return this.model.getCurrentPosition().getPlayer().getScore();
    }

    /**
     * @return the number of players in the game
     */
    public int getNumPlayer(){
        return this.model.getListBookshelf().size();
    }

    /**
     * this methodreturn the personal goal of the player with the given username
     * @param username the username of the player
     * @return the personal goal of the player with the given username
     */
    public Map<String, Map<String, String>> getPersonalGoalByUsername(String username){
        for(Player player : model.getListPlayer()){
            if(player.getUsername().equals(username)){
                return player.getCurrentPosition().getCurrentPGoal().getWindows();
            }
        }
        throw new NullPointerException("No player with this username found.");
    }

    /**
     * this method return the id of the personal goal of the player with the given username
     * @param username the username of the player
     * @return the id of the personal goal of the player with the given username
     */
    public int getPersonalGoalIdByUsername(String username){
        for(Player player : model.getListPlayer()){
            if(player.getUsername().equals(username)){
                return player.getCurrentPosition().getCurrentPGoal().getId();
            }
        }
        throw new NullPointerException("No player with this username found.");
    }


    /**
     * @return true if the player has already completed his shelf and false otherwise
     */
    public boolean myBookshelfIsFull() {
        return model.getCurrentPosition().getPlayer().getCurrentPosition().getBookshelf().isFull();
    }

    /**
     * @return the name of the player who has completed his shelf first
     */
    public String GetShelfCompletedBy(){
        return model.getShelfCompletedBy();
    }

    /**
     * This method returns the player at the given position in the final ranking
     * @param position the position of the player in the ranking
     * @return the name of the player at the given position in the ranking
     */
    public String getPlayerNameByRanking(int position) {
        return model.getPlayerNameByRanking(position);
    }

}

