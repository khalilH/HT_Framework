package http;

import http.interfaces.ApplicationResponseInterface;

/**
 * Classe representant un objet reponse permettant l'acces et la modification
 * di corps de la reponse et son content-Type
 */
public class ApplicationResponse implements ApplicationResponseInterface {

    /**
     * Le corps de la reponse
     */
    private Object body;

    /**
     * Le content-type de la reponse
     */
    private String contentType;

    /**
     * Constructeur par defaut initialisant le content-type a text/html
     */
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
