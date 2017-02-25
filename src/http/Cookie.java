package http;

/**
 * Classe representant un objet Cookie
 */
public class Cookie {

    /**
     * Cle du cookie
     */
    private String key;

    /**
     * Valeur du cookie
     */
    private String value;

    public Cookie(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return Retoune la chaine de caractere representant un cookie envoye par
     * le serveur a un client
     */
    public String toString() {
        return Headers.SET_COOKIE+":"+" "+getKey()+"="+getValue();

    }
}
