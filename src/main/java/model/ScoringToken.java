package model;

public class ScoringToken extends Token {
    private int roman_number;
    private int score;
    private TablePosition position;
    public ScoringToken(int score,int retro_number) {
        super(score);
        this.roman_number = roman_number;
    }

    public int getNumber() {
        return roman_number;
    }
}
