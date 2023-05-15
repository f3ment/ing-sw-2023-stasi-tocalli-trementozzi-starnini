package model;

import utils.Event;
import utils.Observable;

import java.io.Serializable;
import java.util.*;

public class Chat extends Observable<Event> implements Serializable {

    private static final long serialVersionUID = 1L;
    private LinkedHashMap<Integer, LinkedHashMap<String, String>> chat;

    private int id;

    private LinkedHashMap<String, String> last;
    private ArrayList<Integer> lastTen;
    private ArrayList<String> online;

    public Chat() {
        this.chat = new LinkedHashMap<Integer, LinkedHashMap<String, String>>();
        lastTen = new ArrayList<Integer>();
        online = new ArrayList<String>();
        id = 0;
    }

    public void setChangedAndNotifyObservers(Event e) {
        setChanged();
        notifyObservers(new Message(e));
    }

    public synchronized void sendMessage(String userName, String message) {
        LinkedHashMap<String, String> msg = new LinkedHashMap<>();
        msg.put(userName, message);
        this.last = msg;
        this.chat.put(id,msg);
        if(this.lastTen.size() == 10){
            lastTen.remove(0);
        }
        this.lastTen.add(id);
        id++;
    }

    public ArrayList<LinkedHashMap<String, String>> getLastTen() throws NullPointerException {
        ArrayList<LinkedHashMap<String, String>> list = new ArrayList<>(this.lastTen.size());
        if(getLast().size() == 0){
            throw new NullPointerException("No message has been sent");
        }else{
            for(Integer i : this.lastTen){
                LinkedHashMap<String, String> res = new LinkedHashMap<>();
                HashMap<String, String> el = this.chat.get(i);
                for(String usr : el.keySet()){
                    res.put(usr, el.get(usr));
                }
                list.add(res);
            }
            return list;
        }
    }

    public LinkedHashMap<String, String> getLast() throws NullPointerException{
        if(this.last == null){
            throw new NullPointerException("No message has been sent");
        }else{
            return this.last;
        }
    }
    public ArrayList<String> getActive() {
        return this.online;
    }
    public void addActive(String userName) {
        if(!this.online.contains(userName)){
            this.online.add(userName);
        }
    }
    public void removeActive(String userName){
        this.online.remove(userName);
    }
}
