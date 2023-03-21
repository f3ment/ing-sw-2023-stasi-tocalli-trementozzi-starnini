package model;

public class ScoringToken extends EndGameToken {
    int roman_number;
    public ScoringToken(int score,int retro_number) {
        super(score);
        this.roman_number = roman_number;
    }

    public int getNumber() {
        return roman_number;
    }
}
