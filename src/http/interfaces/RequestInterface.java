package http.interfaces;

import http.Method;
import http.Url;

import java.util.Map;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public interface RequestInterface {
    Url getUrl();
    Method getMethod();
    Map<String, String> getHeaders();
    Map<String, String> getCookies();
    String getHeader(String header);
    String getBody();
    String getCookie(String key);
    String getParameter(String param);


//    public String getCookie(String key);
//    public Object getHeader(String fieldName);
}
