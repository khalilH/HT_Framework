package http.interfaces;

import http.Method;
import http.Url;

import java.util.Map;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public interface RequestInterface {
    public Url getUrl();
    public Method getMethod();
    public Map<String, String> getHeaders();
    public Map<String, String> getCookies();
    public String getCookie(String key);


//    public String getCookie(String key);
//    public Object getHeader(String fieldName);
}
