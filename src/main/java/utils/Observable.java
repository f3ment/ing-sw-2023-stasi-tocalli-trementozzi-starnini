package utils;

import model.Message;

import java.util.Vector;


/**
 * This class represents an observable object, or "data"
 * in the model-view paradigm. It can be subclassed to represent an object that the application
 * wants to have observed. An observable object can have one or more observers. An observer may be any object that
 * implements interface {@link Observer}. After an observable instance changes, an application calling the
 * {@code Observable}'s {@code notifyObservers} method causes all of its observers to be notified of the change by a
 * call to their {@code update} method.
 *
 * @param <Event> Enum of events that can be observed
 */
public abstract class Observable<Event extends Enum<Event>> {
    private boolean changed = false;
    private Vector<Observer<? extends Observable<Event>, Event>> obs;

    /** Construct an Observable with zero Observers. */

    public Observable() {
        obs = new Vector<>();
    }

    /**
     * Adds an observer to the set of observers for this object, provided
     * that it is not the same as some observer already in the set.
     * The order in which notifications will be delivered to multiple
     * observers is not specified. See the class comment.
     *
     * @param   o   an observer to be added.
     * @throws NullPointerException   if the parameter o is null.
     */
    public synchronized void addObserver(Observer<? extends Observable<Event>, Event> o) {
        if (o == null)
            throw new NullPointerException();
        if (!obs.contains(o)) {
            obs.addElement(o);
        }
    }

    /**
     * Deletes an observer from the set of observers of this object.
     * Passing {@code null} to this method will have no effect.
     * @param   o   the observer to be deleted.
     */
    public synchronized void deleteObserver(Observer<? extends Observable<Event>, Event> o) {
        obs.removeElement(o);
    }


    public void notifyObservers() {
        notifyObservers(null);
    }


    /**
     * If this object has changed, as indicated by the {@code hasChanged} method, then notify all of its observers
     *
     * @param message Message to be sent to observers
     */
    public void notifyObservers(Message message) {
        /*
         * a temporary array buffer, used as a snapshot of the state of
         * current Observers.
         */
        Object[] arrLocal;

        synchronized (this) {
            /* We don't want the Observer doing callbacks into
             * arbitrary code while holding its own Monitor.
             * The code where we extract each Observable from
             * the Vector and store the state of the Observer
             * needs synchronization, but notifying observers
             * does not (should not).  The worst result of any
             * potential race-condition here is that:
             * 1) a newly-added Observer will miss a
             *   notification in progress
             * 2) a recently unregistered Observer will be
             *   wrongly notified when it doesn't care
             */
            if (!changed)
                return;
            arrLocal = obs.toArray();
            clearChanged();
        }

        for (int i = arrLocal.length-1; i>=0; i--) {
            ((Observer<Observable<Event>, Event>) arrLocal[i]).update(this, message);
        }
    }

    /**
     * Clears the observer list so that this object no longer has any observers.
     */
    public synchronized void deleteObservers() {
        obs.removeAllElements();
    }

    /**
     * Marks this {@code Observable} object as having been changed; the
     * {@code hasChanged} method will now return {@code true}.
     */
    public synchronized void setChanged() {
        changed = true;
    }

    /**
     * Indicates that this object has no longer changed, or that it has already notified all of its observers of its
     */
    protected synchronized void clearChanged() {
        changed = false;
    }


    /**
     * @return {@code true} if and only if this object has changed.
     */
    public synchronized boolean hasChanged() {
        return changed;
    }

    /**
     * Returns the number of observers of this {@code Observable} object.
     *
     * @return  the number of observers of this object.
     */
    public synchronized int countObservers() {
        return obs.size();
    }

}
