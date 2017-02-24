package http;

import http.interfaces.RequestInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request implements RequestInterface{
    private Url url;
    private Method method;
    private Map<String, String> headers;
    private List<Cookie> cookies;
    private String body;
    private String ip;
    private String uniqueId;

    public Request() {
        headers = new HashMap<>();
    }

    public Request(Url url, Method method, Map<String, String> headers, List<Cookie> cookies) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.body = null;
        this.cookies = cookies;
    }

    @Override
    public Url getUrl() {
        return url;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Map getHeaders() {
        return headers;
    }

    @Override
    public List<Cookie> getCookies() {
        return cookies;
    }

    @Override
    public String getHeader(String header) {
        return headers.get(header);
    }

    @Override
    public String getBody() {
        return body;
    }

    @Override
    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String getCookie(String key) {
        String cookie = null;
        for (int i = 0; i < cookies.size() ; i++) {
            Cookie c = cookies.get(i);
            if (c.getKey().equals(key)); {
                return c.getValue();
            }
        }
        return null;
    }

    @Override
    public String getParameter(String param) {
        return getUrl().getParameter(param);
    }

    @Override
    public void setIp(String ip){ this.ip = ip; }

    @Override
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getIp(){ return ip; }

    @Override
    public void setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
    }


}
