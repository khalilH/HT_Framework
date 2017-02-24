package http.interfaces;

import http.Cookie;
import http.Method;
import http.Url;

import java.util.List;
import java.util.Map;

/**
 *
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
    void setCookies(List<Cookie> cookies);


}
