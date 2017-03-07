package apps.chatApp.setvlet.model;

import http.AbstractSession;

/**
 * Created by ladislas on 07/03/2017.
 */
public class Session extends AbstractSession {

    private int id;

    public Session() {
        super();
    }

    public Session(int id) {
        this();
        this.id = id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

}
