package model;

public class ScoringToken extends Token {
    private final int roman_number;
    public ScoringToken(int score,int retro_number) {
        super(score);
        this.roman_number = retro_number;
    }

    public int getNumber() {
        return roman_number;
    }
}
