package apps.todoList.setvlet.services;

import apps.todoList.setvlet.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ladislas on 07/03/2017.
 */
public class BDD {

    private static int cpt = 0;

    // Contient les utilisateurs inscrits à l'application (username -> User(username, password, id))
    public static Map<String, User> users = new HashMap<String, User>();
    // Recense les notes d'un utilisateur (id1 -> liste de notes)
    public static Map<Integer, List<String>> memos = new HashMap<>();

    /* methodes pour users */
    public static boolean signup(String username, String password, String prenom, String nom, int age){
        if (users.containsKey(username)) {
            return false;
        } else {
            users.put(username, new User(username, password, prenom, nom, age, ++cpt));
            return true;
        }
    }

    public static int login(String login, String password){
        return (users.get(login) != null && users.get(login).getPassword().equals(password)) ? users.get(login).getId() : -1;
    }

    public static List<String> getMemos(int userID) {
        return memos.get(userID);
    }

    public static boolean addMemo(int userid, String memo) {
        if (memos.containsKey(userid))
            return memos.get(userid).add(memo);
        else
            return false;
    }

    public static String removeMemo(int userid, int memoId) {
        return memos.get(userid).remove(memoId);
    }

    public static User getUser(String username) {
        return users.get(username);
    }

}
