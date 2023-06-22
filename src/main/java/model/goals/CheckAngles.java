package model.goals;
// 1
import model.Bookshelf;
import model.ScoringToken;

public class CheckAngles extends CommonGoal{

    public  CheckAngles(int romanNumber, int numberPlayers){
        super(romanNumber,numberPlayers);
    }

    @Override
    public String toString() {
        return "Four tiles of the same type in the four corners of the bookshelf.";
    }
    public String getSource(){
        return "8.jpg";
    }
    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        try{
            if(bookshelf.getItem(0,0).getType().equals(bookshelf.getItem(0, bookshelf.getLength()-1).getType())  &&
                    bookshelf.getItem(0,0).getType().equals(bookshelf.getItem(bookshelf.getHeight()-1, bookshelf.getLength()-1).getType())  &&
                    bookshelf.getItem(0,0).getType().equals(bookshelf.getItem(bookshelf.getHeight()-1, 0).getType())){
                return getStack().pop();
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }

    }
}
