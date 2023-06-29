package model;

/**
 * This class extends Token class and represents a scoring token which is a scoring object assigned to the player when
 * achieving some goals during the game It is defined by a score between 2,4,6,8 except for the special ending token with
 * the score of 1 and by a roman number that indicates which goal has been achieved between the 2 available during a game
 */
public class ScoringToken extends Token {
    private final int roman_number;

    /**
     * Constructor of the scoring token
     * The roman number can be 1 for the first common goal or 2 for the second
     * @param score the score of the token
     * @param retro_number the roman number of the token
     */
    public ScoringToken(int score,int retro_number) {
        super(score);
        this.roman_number = retro_number;
    }

    /**
     * @return the roman number of the token
     */
    public int getNumber() {
        return roman_number;
    }
}
