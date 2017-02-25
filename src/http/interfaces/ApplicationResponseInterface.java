package http.interfaces;

/**
 * Created by Khalil on 25/02/2017.
 */
public interface ApplicationResponseInterface {

    void setBody(Object body);
    Object getBody();

    void setContentType(String contentType);
    String getContentType();
}
