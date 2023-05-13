package model.views;

import model.Bookshelf;
import model.Game;
import model.ItemTiles;
import model.ScoringToken;
import model.board.Board;

import java.io.Serializable;
import java.util.*;
//todo serialize
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

    public ArrayListView getListBookshelf(){
        List<Bookshelf> bookshelfList= model.getListBookshelf();
        List<ItemTiles[][]> viewBookshelfList= new ArrayList<ItemTiles[][]>();
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

    public ArrayListView getPickedCards(){
        ArrayList<ItemTiles> hand= model.getPickedCards();
        ArrayList<ItemTiles> viewHand= new ArrayList<ItemTiles>();
        for(int i=0;i<hand.size();i++){
            viewHand.add(hand.get(i));
        }
        ArrayListView viewHandreturn = new ArrayListView(viewHand);
        return viewHandreturn;
    }

    public ArrayListView getFirstCommonGoal(){
        Stack<ScoringToken> stack=model.getFirstCommonGoal().getStack();
        ArrayList<ScoringToken> viewStack= new ArrayList<ScoringToken>();
        for (ScoringToken scoringToken : stack) {
            viewStack.add(new ScoringToken(scoringToken.getScore(), scoringToken.getNumber()));
        }
        ArrayListView viewStack2= new ArrayListView(viewStack);
        return viewStack2;
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
        ArrayListView viewStack2= new ArrayListView(viewStack);
        return viewStack2;
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
        PlayerView res = new PlayerView(model.getCurrentPosition().getPlayer().getUsername(),model.getCurrentPosition().getPlayer().getStatus(),model.getCurrentPosition().getPlayer().getScore(),model.getCurrentPosition().getPlayer().getPickedCards(),model.getCurrentPosition().getPlayer().getCurrentPosition(),model.getCurrentPosition().getPlayer().getToken());
        return res;
    }



    public ItemTiles getHand(int x){
        return (ItemTiles)getPickedCards().get(x);
    }

    public boolean getEndGame(){
        return model.getEndGame();
    }

    public String getFirstPlayer(){
        return new String(model.getFirstPlayer());
    }

    public String getWinner(){
        return new String(model.getWinner());
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


}

