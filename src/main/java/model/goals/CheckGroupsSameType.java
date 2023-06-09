package model.goals;
// 2
import model.Bookshelf;
import model.ScoringToken;

public class CheckGroupsSameType extends CommonGoal{
        private int repetitions;
        private int groupLength; //true -> 4 , false -> 2

    public CheckGroupsSameType(int romanNumber, int numberPlayers, int groupLength, int repetitions){
        super(romanNumber, numberPlayers);
        this.groupLength = groupLength;
        this.repetitions = repetitions;
    }
    public String getSource(){
        if(groupLength==4)
            return "3.jpg";
        else if(groupLength==2)
            return "4.jpg";
        return null;
    }
    @Override
    public String toString() {
        String str = null;
        if(groupLength==4){
             str = new String("Four groups each containing at least 4 tiles of the same type. The tiles of one group can be different from those of another group.");
        }else if (groupLength == 2){
            str = new String("Six groups each containing at least 2 tiles of the same type. The tiles of one group can be different from those of another group.");
        }
        return str;
    }

    @Override
    public ScoringToken validate(Bookshelf bookshelf)  {
        if (bookshelf == null) return null;
        boolean[][] batrix = new boolean[bookshelf.getHeight()][bookshelf.getLength()];
        int rep = 0;
        boolean flag = false;
        for (int i = 0; i < bookshelf.getHeight(); i++) {
            for (int j = 0; j < bookshelf.getLength(); j++) {
                flag = false;
                try{
                    if (j <= bookshelf.getLength() - groupLength) {
                        for (int k = 1; k < groupLength; k++) {
                            if (batrix[i][j] || batrix[i][j + k]) {
                                flag = true;
                                break;
                            } else {
                                if (!bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i, j + k).getType())) {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if (!flag) {
                            rep++;
                            for (int k = 0; k < groupLength; k++) {
                                batrix[i][j + k] = true;
                            }
                        } else {
                            flag = false;
                        }
                    }
                }catch (Exception e){
                    continue;
                }
                if (rep == repetitions) {
                    return getStack().pop();
                }
            }
        }


        for (int i = 0; i < bookshelf.getHeight(); i++) {
            for (int j = 0; j < bookshelf.getLength(); j++) {
                flag = false;
                try {

                    if (i <= bookshelf.getHeight() - groupLength) {
                        for (int k = 1; k < groupLength; k++) {
                            if (batrix[i][j] || batrix[i + k][j]) {
                                flag = true;
                                break;
                            } else {
                                if (!bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + k, j).getType())) {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if (!flag) {
                            rep++;
                            for (int k = 0; k < groupLength; k++) {
                                batrix[i + k][j] = true;
                            }
                        } else {
                            flag = false;
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
                if (rep == repetitions) {
                    return getStack().pop();
                }
            }
        }
        return null;
    }
}
