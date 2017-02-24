package http;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public class Cookie {

    private String key;
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

    public String toString() {
        return Headers.SET_COOKIE+":"+" "+getKey()+"="+getValue();

    }
}
