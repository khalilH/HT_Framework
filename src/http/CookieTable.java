package http;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ladislas on 21/02/2017.
 */
public class CookieTable {

    public static Map<String, List<Cookie>> cookieMap;
    //    in List<Cookie>, the first element is always the private cookie (userHash, userHashValue)
    public static String UNIQUE_ID = "uniqueId";
    public static String USER_HASH = "userHash";

    static {
        System.out.println("CREATION DE LA TABLE DE COOKIES");
        cookieMap = new HashMap<>();
    }

    public static List<Cookie> getUserCookies(String userHash) {
        return cookieMap.get(userHash);
    }

    public static void addCookiesToUser(String userUniqueId, List<Cookie> cookies){
        cookieMap.put(userUniqueId, cookies);
    }

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

}
