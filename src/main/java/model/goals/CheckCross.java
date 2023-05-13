package model.goals;
// 1
import model.Bookshelf;
import model.ScoringToken;

public class CheckCross extends CommonGoal{

    public CheckCross(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers/*, "Five tiles of the same type forming an X"*/);
    }

    // null = false
    @Override
    public ScoringToken validate(Bookshelf bookshelf) {
        try {
            for (int i = 0; i < bookshelf.getHeight(); i++) {
                for (int j = 0; j < bookshelf.getLength(); j++) {
                    if (i < bookshelf.getHeight() - 2 && j < bookshelf.getLength() - 2) {
                        try {
                            if (bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i, j + 2).getType()) &&
                                    bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + 1, j + 1).getType()) &&
                                    bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + 2, j).getType()) &&
                                    bookshelf.getItem(i, j).getType().equals(bookshelf.getItem(i + 2, j + 2).getType())) {
                                return getStack().pop();  // Esiste una <X> dello stesso tipo
                            }
                        }catch (Exception e){
                            continue;
                        }

                    } else continue;
                }
            }
            return null; // Non esiste una <X> dello stesso tipo}
        } catch (Exception e) {
            return null;
        }
    }
}
