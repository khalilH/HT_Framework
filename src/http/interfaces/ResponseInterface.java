package http.interfaces;

import http.Cookie;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public interface ResponseInterface {
    void addHeader(String fieldName, String value);
    void setStatusCode(int statusCode);
    void addCookie(Cookie cookie);
    int getStatusCode();
    Object getBody();
    void setBody(Object body);

    @Override
    String toString();
}
