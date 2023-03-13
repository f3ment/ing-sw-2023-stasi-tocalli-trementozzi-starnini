package model.entities;

public class ScoringToken extends Token{
    int retro_number;
    public ScoringToken(int score,int retro_number) {
        super(score);
        this.retro_number = retro_number;
    }

    public int getRetro_number() {
        return retro_number;
    }
}
