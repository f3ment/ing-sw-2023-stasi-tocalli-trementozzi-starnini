package model.views;

import model.*;
import model.board.Board;

import java.io.Serializable;
import java.util.*;
public class GameView implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Game model;

    public GameView(Game model) {
        if (model == null) {
            throw new IllegalArgumentException();
        }
        this.model = model;
    }
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

    public ArrayListView getPlayerList(){
        ArrayList<PlayerView> playerViews = new ArrayList<>();
        model.getListPlayer().forEach(e ->playerViews.add(new PlayerView(e)));
        return new ArrayListView(playerViews);
    }

    public Map<String, Integer> getMapPlayerScore(){
        List<Player> playerList = model.getListPlayer();
        //ArrayListView returnPlayerList = new ArrayListView((ArrayList) playerList);
        Map<String, Integer> returnMap = new HashMap<>();
        for(int i=0; i<getNumPlayer(); i++){
            returnMap.put(playerList.get(i).getUsername(), playerList.get(i).getScore());
        }
        return returnMap;
    }
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

    public Map<String,PlayerView> getPlayerByUsername(){
        Map<String,PlayerView> mapPlayerByUsername = new HashMap<>();
        for (Player player : model.getListPlayer()) {
            mapPlayerByUsername.put(player.getUsername(), new PlayerView(player));
        }
        return mapPlayerByUsername;
    }


    public ItemTiles[][] getParticularBookshelf(int x){
        return (ItemTiles[][])getListBookshelf().get(x);
    }

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

    public ArrayListView getFirstCommonGoal(){
        Stack<ScoringToken> stack=model.getFirstCommonGoal().getStack();
        ArrayList<ScoringToken> viewStack= new ArrayList<>();
        for (ScoringToken scoringToken : stack) {
            viewStack.add(new ScoringToken(scoringToken.getScore(), scoringToken.getNumber()));
        }
        ArrayListView viewStack2= new ArrayListView(viewStack);
        return viewStack2;
    }

    public String getFirstCommonGoalDescription(){
        return model.getFirstCommonGoal().toString();
    }

    public String getSecondCommonGoalDescription(){
        return model.getSecondCommonGoal().toString();
    }
    public String getFirstCommonGoalSource(){
        return model.getFirstCommonGoal().getSource();
    }

    public String getSecondCommonGoalSource(){
        return model.getSecondCommonGoal().getSource();
    }

    public ScoringToken getScoringTokenByNumber(int number, int stackPosition){
        switch (number){
            case 1:
                return getScoringToken1(stackPosition);
            case 2:
                return getScoringToken2(stackPosition);
        }
        return null;
    }
    public ScoringToken getScoringToken1(int x){
        return (ScoringToken) getFirstCommonGoal().get(x);
    }

    public ScoringToken getScoringToken2(int x){
        return (ScoringToken) getSecondCommonGoal().get(x);
    }

    public ArrayListView getSecondCommonGoal(){
        Stack<ScoringToken> stack=model.getSecondCommonGoal().getStack();
        ArrayList<ScoringToken> viewStack= new ArrayList<ScoringToken>();
        for(int i=0;i<stack.size();i++){
            viewStack.add(new ScoringToken(stack.get(i).getScore(),stack.get(i).getNumber()));
        }
        return new ArrayListView(viewStack);
    }

    public int getHeightBookshelf(){
        return model.getCurrentBookshelf().getHeight();
    }

    public int getLenghtBookshelf(){
        return model.getCurrentBookshelf().getLength();
    }

    public int getHeightBoard(){
        return model.getBoard().getMaxHeight();
    }

    public int getLenghtBoard(){
        return model.getBoard().getMaxLength();
    }

    public PlayerView getCurrentPlayer(){
        return new PlayerView(model.getCurrentPosition().getPlayer());
    }


    public boolean getStatusByNickname(String nickname){
        return model.getPlayerByNickname().get(nickname).getStatus();
    }


    public ItemTiles getHand(int x){
        return (ItemTiles)getPickedCards().get(x);
    }

    public boolean getEndGame(){
        return model.getEndGame();
    }

    public String getFirstPlayer(){
        return model.getFirstPlayer();
    }

    public String getWinner(){
        return model.getWinner();
    }

    public int getMaxDrawable(){
        return model.getCurrentBookshelf().getMaxDrowable();
    }

    public int getScore(){
        return this.model.getCurrentPosition().getPlayer().getScore();
    }

    public int getNumPlayer(){
        return this.model.getListBookshelf().size();
    }

    public Map<String, Map<String, String>> getPersonalGoalByUsername(String username){
        for(Player player : model.getListPlayer()){
            if(player.getUsername().equals(username)){
                return player.getCurrentPosition().getCurrentPGoal().getWindows();
            }
        }
        //todo exception no player with that username
        throw new NullPointerException("No player with this username found.");
    }

    public int getPersonalGoalIdByUsername(String username){
        for(Player player : model.getListPlayer()){
            if(player.getUsername().equals(username)){
                return player.getCurrentPosition().getCurrentPGoal().getId();
            }
        }
        //todo exception no player with that username
        throw new NullPointerException("No player with this username found.");
    }


    public boolean myBookshelfIsFull() {
        return model.getCurrentPosition().getPlayer().getCurrentPosition().getBookshelf().isFull();
    }

    public String GetShelfCompletedBy(){
        return model.getShelfCompletedBy();
    }

    public String getPlayerNameByRanking(int position) {
        return model.getPlayerNameByRanking(position);
    }
}

