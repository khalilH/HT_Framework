package apps.chatApp.setvlet.services;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ladislas on 07/03/2017.
 */
public class UserServices {

    public static JSONObject login(String username, String password){
        JSONObject res = new JSONObject();
        try {
            res.put("repsonse", BDD.login(username, password));
            return res;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject signup(String username, String password){
        JSONObject res = new JSONObject();
        try {
            BDD.signup(username, password);
            res.put("repsonse", "ok");
            return res;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
