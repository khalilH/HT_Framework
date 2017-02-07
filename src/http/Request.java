package http;

import http.interfaces.RequestInterface;

import java.util.HashMap;
import java.util.Map;

public class Request implements RequestInterface{
    private String url;
    private Method method;
    private Map<String, String> headers;
    private Map<String, String> cookies;

    public Request() {
        headers = new HashMap<>();
        cookies = new HashMap<>();
    }

    public Request(String url, Method method, Map<String, String> headers, Map<String, String> cookies) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.cookies = cookies;

    }

    @Override
    public String getUrl() {
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
    public String getCookie(String key) {
        return cookies.get(key);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

}
