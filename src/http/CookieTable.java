package http;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe contenant la table des Cookies
 */
public class CookieTable {

    /**
     * Liste d'association (cle, valeur) contenant les listes de cookies des utilisateurs
     * La cle est l'identifiant unique d'un utilisateur (qui est le cookie envoye au client)
     * La valeur est la liste des cookies associee a un utilisateur
     * Dans la liste de cookie, le premier cookie est toujour le cookie privee : cookie contenant
     * la valeur hachee de l'IP et du User Agent d'un utilisateur
     * Ce coookie prive n'est jamais envoye a un client
     */
    public static Map<String, List<Cookie>> cookieMap;

    public static String UNIQUE_ID = "uniqueId";
    public static String USER_HASH = "userHash";

    static {
        cookieMap = new HashMap<>();
    }

    /**
     * Permet d'obtenir la liste de cookies d'un utilisateur
     * @param userUniqueId l'identifiant unique d'un utlisateur
     * @return une liste de cookies
     */
    public static List<Cookie> getUserCookies(String userUniqueId) {
        return cookieMap.get(userUniqueId);
    }

    /**
     * Permet d'ajouter la liste de cookies d'un utilisateur a la Table des cookies
     * @param userUniqueId l'identifiant unique d'un utilsateur
     * @param cookies la liste de cookies de l'utilisateur
     */
    public static void addCookiesToUser(String userUniqueId, List<Cookie> cookies){
        cookieMap.put(userUniqueId, cookies);
    }

    /**
     * permet de hacher une chaine de caractere avec l'algorithme SHA-256
     * @param base la chaine de caractere a hacher
     * @return le hachage de la chaine de caracteres par SHA-256
     */
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
