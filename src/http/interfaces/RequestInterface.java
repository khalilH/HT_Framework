package http.interfaces;

import http.Cookie;
import http.Method;

import java.util.List;
import java.util.Map;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public interface RequestInterface {
    public String getUrl();
    public Method getMethod();
    public Map<String, String> getHeaders();
    public Map<String, String> getCookies();
    public String getCookie(String key);


//    public String getCookie(String key);
//    public Object getHeader(String fieldName);
}
