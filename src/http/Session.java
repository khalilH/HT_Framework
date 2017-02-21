package http;

import http.interfaces.SessionInterface;

/**
 * Created by ladislas on 21/02/2017.
 */
public class Session implements SessionInterface {
    private Object toz;

    public Session(Object toz) {
        this.toz = toz;
    }

}
