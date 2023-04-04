package utils;

public class Turn {
    private Event playerEvent;

    public Event getPlayerEvent(){
        return playerEvent;
    }

    public void setPlayerEvent(Event playerEvent){
        this.playerEvent = playerEvent;
        setChangedAndNotifyObserver(playerEvent);
    }

    public void clear() {
        playerEvent = null;
    }

    private void setChangedAndNotifyObserver(Event arg){
        setChanged();
        notifyObserver(arg);
    }


}
