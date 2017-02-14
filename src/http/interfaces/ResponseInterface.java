package http.interfaces;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public interface ResponseInterface {
    void addHeader(String fieldName, String value);
    void setStatusCode(int statusCode);
    void setCookie(String key, String value);
    int getStatusCode();
    Object getBody();
    void setBody(Object body);

    @Override
    String toString();
}
