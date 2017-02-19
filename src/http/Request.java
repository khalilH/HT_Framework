package http;

import http.interfaces.RequestInterface;

import java.util.HashMap;
import java.util.Map;

public class Request implements RequestInterface{
    private Url url;
    private Method method;
    private Map<String, String> headers;
    private Map<String, String> cookies;
    private String body;

    public Request() {
        headers = new HashMap<>();
        cookies = new HashMap<>();
    }

    public Request(Url url, Method method, Map<String, String> headers, Map<String, String> cookies) {
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
    public Map<String, String> getCookies() {
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
        return cookies.get(key);
    }

    @Override
    public String getParameter(String param) {
        return getUrl().getParameter(param);
    }

}
