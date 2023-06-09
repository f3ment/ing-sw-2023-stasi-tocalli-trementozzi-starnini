package model.goals;
// 1
import model.Bookshelf;
import model.ScoringToken;

public class CheckSquare extends CommonGoal{

    public CheckSquare(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);
    }

    @Override
    public String toString() {
        String str = new String("Two groups each containing 4 tiles of the same type in a 2x2 square. The tiles of one square can be different from those of the other square.");
        return str;
    }

    public String getSource(){
        return "1.jpg";
    }

    /*
        * * * * * * * *
        *  DEPRECATED *
        * * * * * * * *

        @Override
        public ScoringToken validate(Bookshelf bookshelf) throws Exception{
            boolean[][] batrix = new boolean[bookshelf.getHeight()][bookshelf.getLength()];
            int rep = 0;

            for(int i=0; i< bookshelf.getHeight(); i++) {
                for (int j = 0; j < bookshelf.getLength(); j++) {
                    if(bookshelf.getItem(i,j) == null) throw new Exception();
                    //check for square
                    if( bookshelf.getItem(i,j).getType().equals(
                            bookshelf.getItem(i,j+1).getType()) &&
                        bookshelf.getItem(i,j).getType().equals(
                                bookshelf.getItem(i+1,j).getType()) &&
                        bookshelf.getItem(i,j).getType().equals(
                                bookshelf.getItem(i+1, j+1).getType())){
                        if(!batrix[i][j] && !batrix[i][j+1] && !batrix[i+1][j] && !batrix[i+1][j+1]){
                            batrix[i][j] = true;
                            batrix[i][j+1] = true;
                            batrix[i+1][j] = true;
                            batrix[i+1][j+1] = true;
                            rep++;
                            if(rep == 2){
                                return this.stack.pop();
                            }
                        }

                    }
                }
            }
            return null;
        }*/
    @Override
    public ScoringToken validate(Bookshelf bookshelf){
        if (bookshelf == null) return null;
        boolean[][] batrix = new boolean[bookshelf.getHeight()][bookshelf.getLength()];
        int found=0;

        //MARK ON batrix[][] ALL SQUARE
        // NB: SQUARE ARE 4 CARDS OF THE SAME TYPE THAT CREATE A SQUARE IN THE BOOKSHELF
        for(int i=0; i< bookshelf.getHeight()-1; i++) {
            for (int j = 0; j < bookshelf.getLength()-1; j++) {
                if(batrix[i][j]) {continue;}
                try{
                    if (bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i, j + 1).getType()) &&
                            bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + 1, j).getType()) &&
                            bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + 1, j + 1).getType())) {
                        if (!(batrix[i][j] || batrix[i][j + 1] || batrix[i + 1][j] || batrix[i + 1][j + 1])) {
                            batrix[i][j] = true;
                            batrix[i][j + 1] = true;
                            batrix[i + 1][j] = true;
                            batrix[i + 1][j + 1] = true;
                            /* ENGLISH VERSION (REMOVE ITALIAN VERSION TOO) */
                            found++;
                            if (found == 2){
                                return getStack().pop();
                            }

                        }
                    }
                }catch(Exception e){
                    continue;
                }

            }
        }
        // <ITALIAN VERSION>
        //CHECK THAT THERE ARE AT LEAST TWO SQUARE OF THE SAME CARD'S T{YPE
/*        for(int i=0; i< bookshelf.getHeight(); i++){
            for(int j=0; j<bookshelf.getLength(); j++){
                int count=0;
                if(!batrix[i][j]) {continue;}
                else{
                    for(int k=i+1; k< bookshelf.getHeight(); k++){
                        for(int h=j+1; h< bookshelf.getLength(); h++){
                            if(batrix[k][h] &&
                                    (bookshelf.getItem(i,j).getType().equals(bookshelf.getItem(k,h).getType()))){
                                count++;
                            }
                        }
                    }
                    //Check if at least two square of the same type found
                    if(count>=8){
                        return stack.pop();
                    }
                }
            }
        }*/
        // </ITALIAN VERSION>
        return null;
    }

}

