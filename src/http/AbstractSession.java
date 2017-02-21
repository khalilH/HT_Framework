package http;

import http.interfaces.SessionInterface;

import java.util.Date;

/**
 * Created by ladislas on 21/02/2017.
 */
public abstract class AbstractSession implements SessionInterface{
    protected long lastAccess;

    public AbstractSession() {
        Date d = new Date();
        lastAccess = d.getTime();
    }

    /**
     * Permet de verifier si un objet session a expire
     * @return duree de vie de la session depasse 1h
     */
    @Override
    public final boolean isAlive() {
        long oneHourInMillis = 60 * 60 * 1000;
        Date d = new Date();
        return d.getTime() < lastAccess + oneHourInMillis;
    }

    /**
     * Permet de mettre a jour la derniere date d'acces de la session
     */
    @Override
    public final void updateLastAccess() {
        Date d = new Date();
        lastAccess = d.getTime();
    }
}
