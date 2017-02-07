package http.interfaces;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public interface ResponseInterface {
    public void addHeader(String fieldName, String value);
    public void setStatusCode(int statusCode);
    public void setCookie(String key, String value);
}
