package apps.authenticationApp;

import http.AbstractSession;
import http.interfaces.SessionInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ladislas on 21/02/2017.
 */

public class Session extends AbstractSession {

    private List<String> notes;

    public Session() {
        super();
        notes = new ArrayList<>();
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
}
