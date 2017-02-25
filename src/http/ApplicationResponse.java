package http;

import http.interfaces.ApplicationResponseInterface;

/**
 * Created by Khalil on 25/02/2017.
 */
public class ApplicationResponse implements ApplicationResponseInterface {

    private Object body;
    private String contentType;


    public ApplicationResponse() {
        this.contentType = Headers.TEXT_HTML;
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
