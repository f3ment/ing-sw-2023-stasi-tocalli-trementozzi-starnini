package utils;

import utils.Observable;

public class Turn extends Observable<Turn.Event>{

    public enum Event {
        PLAYER_DRAW, PLAYER_INSERT, PLAYER_FINISH
    }

    private Event playerEvent;


    public Event getPlayerEvent(){
        return playerEvent;
    }

    public void setPlayerEvent(Event playerEvent){
        this.playerEvent = playerEvent;
        setChangedAndNotifyObservers(playerEvent);
    }

    public void clear() {
        playerEvent = null;
    }

    private void setChangedAndNotifyObservers(Event arg){
        setChanged();
        //notifyObservers(arg);
    }


}
