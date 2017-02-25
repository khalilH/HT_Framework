package http.interfaces;

/**
 * Interface representant un objet etat d'une session
 */
public interface SessionInterface {

    /**
     * Permet de verifier si un objet session a expire
     * @return true si l'objet n'a pas expire
     */
    public boolean isAlive();

    /**
     * Permet de mettre a jour la date de dernier acces a l'objet session
     */
    public void updateLastAccess();
}
