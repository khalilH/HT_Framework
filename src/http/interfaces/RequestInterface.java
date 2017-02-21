package http.interfaces;

import http.Cookie;
import http.Method;
import http.Url;

import java.util.List;
import java.util.Map;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public interface RequestInterface {
    Url getUrl();
    Method getMethod();
    Map<String, String> getHeaders();
    List<Cookie> getCookies();
    String getHeader(String header);
    String getBody();
    void setBody(String body);
    String getCookie(String key);
    String getParameter(String param);
    void setIp(String ip);
    void setUniqueId(String uniqueId);
    String getUniqueId();
    String getIp();


//    public String getCookie(String key);
//    public Object getHeader(String fieldName);
}
