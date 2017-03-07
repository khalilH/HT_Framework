package apps.chatApp.setvlet.services;

import apps.chatApp.setvlet.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ladislas on 07/03/2017.
 */
public class BDD {

    private static int cpt = 0;

    // Contient les utilisateurs inscrits à l'application (username -> User(username, password, id))
    public static Map<String, User> users = new HashMap<String, User>();
    // Recense les messages d'un utilisateur (id1 -> (id2 -> fileName))
    public static Map<String, Map<String, String>> messages = new HashMap<>();

    /* methodes pour users */
    public static void signup(String login, String password){
        users.put(login, new User(login, password, ++cpt));
    }

    public static int login(String login, String password){
        return (users.get(login) != null && users.get(login).getPassword().equals(password)) ? users.get(login).getId() : -1;
    }

}
