package model.board;

import model.Box;
import model.EndGameToken;
import model.ItemTiles;

public class FourBoard {
    private Box[][] board;
    private EndGameToken token;


    public FourBoard(){
        board = new Box[9][9];
        for(int i =0; i<9; i++){
            for (int j =0 ; j < 9; j++){
                if (    (i == 0 && ( j<=2 || j>=5 )) ||
                        (j == 0 && ( i<=2 || i>=5 )) ||
                        (i == 8 && ( j<=2 || j>=5 )) ||
                        (j == 8 && ( i<=2 || i>=5 )) ||
                        (i == 1 && ( j <= 2 || j>=6 )) ||
                        (j == 1 && ( i <= 2 || i>=6 )) ||
                        (i == 7 && ( j <= 2 || j>=6 )) ||
                        (j == 7 && ( i <= 2 || i>=6 ))
                ) {
                    board[i][j] = new Box(false, null);
                }else{
                    board[i][j] = new Box(true, null);
                }
            }
        }
        //new Box(true, null);
        token = new EndGameToken(1);
    }

    public Box getBox(int i, int j) throws IndexOutOfBoundsException{
        if(!board[i][j].getValid()){
            //todo gestione eccezione
            throw new IndexOutOfBoundsException();
        }else{
            return board[i][j];
        }
    }

    public ItemTiles draw(int i, int j) {
        ItemTiles res = getBox(i, j).getItemContained();
        getBox(i,j).setContent(null);
        return res;
    }

    public void setToken(EndGameToken token) {
        this.token = token;
    }
    public void setBox(ItemTiles content){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(getBox(i,j)!=null){  //todo gestire eccezzione casella non valida
                    if(getBox(i,j).getItemContained()==null){
                        getBox(i,j).setContent(content);
                        break;
                    }
                }
            }
        }
    }
}
