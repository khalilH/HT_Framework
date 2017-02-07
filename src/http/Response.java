package http;

import http.interfaces.ResponseInterface;

import java.util.Map;

public class Response implements ResponseInterface{

    private int statusCode;
    private Map<String, String> headers;
    private Map<String, String> cookies;

    @Override
    public void addHeader(String fieldName, String value) {
        headers.put(fieldName, value);
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void setCookie(String key, String value) {
        cookies.put(key, value);
    }
}
