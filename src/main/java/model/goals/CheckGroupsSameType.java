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

    @Override
    public ScoringToken validate(Bookshelf bookshelf)  {
        boolean[][] batrix = new boolean[bookshelf.getHeight()][bookshelf.getLength()];
        int rep = 0;
        boolean flag = false;
        try{
            for (int i = 0; i < bookshelf.getHeight(); i++) {
                for (int j = 0; j < bookshelf.getLength(); j++) {

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
                    if (rep == repetitions) {
                        return getStack().pop();
                    }
                }
            }
            return null;
        }catch(Exception e){
            return null;
        }
    }
}
