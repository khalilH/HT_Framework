package http.interfaces;

/**
 * Created by ladislas on 21/02/2017.
 */
public interface SessionInterface {
    public boolean isAlive();
    public void updateLastAccess();
}
