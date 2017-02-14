package http;

import http.interfaces.ResponseInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
        this.statusCode = StatusCode.OK;
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

    @Override
    public String toHTML() {
        String response = "HTTP/1.1 "+getStatusCode()+" "+StatusCode.getDescription(getStatusCode())+System.lineSeparator();
        addHeader(Headers.CONTENT_TYPE, Headers.TEXT_HTML);
        addHeader(Headers.CONTENT_LENGTH, getBody().length()+"");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            response += entry.getKey() + ": "+entry.getValue()+System.lineSeparator();
        }
        response += System.lineSeparator();
        response += getBody()+System.lineSeparator();
        return response;
    }

    @Override
    public String toJson() {
        String response = "HTTP/1.1 "+getStatusCode()+" "+StatusCode.getDescription(getStatusCode())+System.lineSeparator();
        addHeader(Headers.CONTENT_TYPE, Headers.APPLICATION_JSON);
        JSONObject json = new JSONObject();
        try {
            json.put("content", getBody().toString());
            addHeader(Headers.CONTENT_LENGTH, json.toString().length()+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            response += entry.getKey() + ": "+entry.getValue()+System.lineSeparator();
        }
        response += System.lineSeparator();
        response += json.toString()+System.lineSeparator();
        return response;
    }

    @Override
    public String toString() {
        String response = "HTTP/1.1 "+getStatusCode()+" "+StatusCode.getDescription(getStatusCode())+System.lineSeparator();
        addHeader(Headers.CONTENT_TYPE, Headers.TEXT_PLAIN);
        addHeader(Headers.CONTENT_LENGTH, getBody().length()+"");
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            response += entry.getKey() + ": "+entry.getValue()+System.lineSeparator();
        }
        response += System.lineSeparator();
        response += getBody()+System.lineSeparator();
        return response;
    }



}
