package apps.todoList.setvlet.services;

import apps.todoList.setvlet.model.User;
import exception.TemplateVariableNotFoundException;
import http.TemplateLib;
import org.json.JSONException;
import org.json.JSONObject;
import server.HttpServer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ladislas on 07/03/2017.
 */
public class UserServices {

    public static JSONObject login(String username, String password){
        JSONObject res = new JSONObject();
        int id = BDD.login(username,password);
        try {
            if (id != -1) {
                res.put("id", BDD.login(username, password));
                if (!BDD.memos.containsKey(id))
                    BDD.memos.put(id, new ArrayList<String>());
            }
            else
                res.put("error", "user inexistant ou mdp errone");
            return res;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject signup(String username, String password, String prenom, String nom, int age){
        JSONObject res = new JSONObject();
        boolean signup = BDD.signup(username, password, prenom, nom, age);
        try {
            if (signup)
                res.put("success", "ok");
            else
                res.put("error", "nom d'utilisateur non disponible");
            return res;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static  JSONObject getMemos(String userId) throws JSONException {
        JSONObject res = new JSONObject();
        List<String> memos = BDD.getMemos(Integer.parseInt(userId));
        if (memos != null)
            res.put("memos", memos);
        else
            res.put("error", "la liste de memo n'existe pas");
        return res;
    }

    public static JSONObject addMemo(int userId, String memo) throws JSONException {
        JSONObject res = new JSONObject();
        BDD.addMemo(userId, memo);
        res.put("success", "memo ajoute");
        return res;
    }

    public static JSONObject removeMemo(String userId, int memoId) throws JSONException {
        JSONObject res = new JSONObject();
        String toz = BDD.removeMemo(Integer.parseInt(userId), memoId);
        if (toz != null)
            res.put("success", "memo supprime");
        else
            res.put("error", "memo n'existe pas");
        return res;
    }

    public static String getUserInfoTemplate(String username) {
        Map<String, Object> map = new HashMap<>();

        String res = "";
        User user = BDD.getUser(username);
        if (user != null) {
            map.put("user", user);
            Path templatePath = Paths.get(User.templatePath);
            Charset charset = StandardCharsets.UTF_8;
            try {
                String content = new String(Files.readAllBytes(templatePath), charset);
                res = TemplateLib.replaceAllObject(content, map);
            } catch (TemplateVariableNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

}
