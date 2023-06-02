package model.goals;
// 1
import model.Bookshelf;
import model.ScoringToken;

public class CheckCross extends CommonGoal{

    public CheckCross(int romanNumber, int numberPlayers){
        super(romanNumber, numberPlayers);
    }

    @Override
    public String toString() {
        String str = new String("Five tiles of the same type forming an X");
        return str;
    }

    public String getSource(){
        return "10.jpg";
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
                                System.out.println("Hai completato l'obiettivo: 5 tessere dello stesso tipo formano una X");
                                return getStack().pop();  // Esiste una <X> dello stesso tipo
                            }
                        }catch (Exception e){
                            continue;}
                    } else continue;
                }
            }
            return null; // Non esiste una <X> dello stesso tipo}
        } catch (Exception e) {
            return null;
        }
    }
}