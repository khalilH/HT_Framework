package http.interfaces;

import http.Cookie;
import http.Method;
import http.Url;

import java.util.List;
import java.util.Map;

/**
 * interface representant un objet request HTTP
 */
public interface RequestInterface {

    // GETTERS
    String getBody();
    String getCookie(String key);
    List<Cookie> getCookies();
    String getHeader(String header);
    Map<String, String> getHeaders();
    String getIp();
    Method getMethod();
    String getParameter(String param);
    String getUniqueId();
    Url getUrl();

    // SETTERS
    void setBody(String body);
    void setCookies(List<Cookie> cookies);
    void setIp(String ip);
    void setUniqueId(String uniqueId);


}
