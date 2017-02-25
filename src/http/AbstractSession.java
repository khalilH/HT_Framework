package http;

import http.interfaces.SessionInterface;

import java.util.Date;

/**
 * Classe abstraite de l'objet session
 */
public abstract class AbstractSession implements SessionInterface{
    /**
     * timestamp du dernier acces a l'objet
     */
    protected long lastAccess;

    public AbstractSession() {
        Date d = new Date();
        lastAccess = d.getTime();
    }

    /**
     * Permet de verifier si un objet session a expire
     * @return true si duree de vie de la session depasse 1h
     */
    @Override
    public final boolean isAlive() {
        long oneHourInMillis = 60 * 60 * 1000;
        Date d = new Date();
        return d.getTime() < lastAccess + oneHourInMillis;
    }

    /**
     * Permet de mettre a jour la date de dernier acces a l'objet session
     */
    @Override
    public final void updateLastAccess() {
        Date d = new Date();
        lastAccess = d.getTime();
    }
}
