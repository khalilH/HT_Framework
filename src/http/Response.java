package http;

import http.interfaces.ResponseInterface;

import java.util.Map;

public class Response implements ResponseInterface{

    private int statusCode;
    private Map<String, String> headers;
    private Map<String, String> cookies;
    private String body;

    @Override
    public void addHeader(String fieldName, String value) {
        headers.put(fieldName, value);
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public int getStatusCode(){ return this.statusCode; }

    @Override
    public void setCookie(String key, String value) {
        cookies.put(key, value);
    }

    public void setHeaders(Map<String, String> headers) { this.headers = headers; }

    public void setCookies(Map<String, String> cookies) { this.cookies = cookies; }

    public void setBody(String body) { this.body = body; }

    @Override
    public String getBody(){ return this.body; }
}
