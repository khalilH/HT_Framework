package http.interfaces;

/**
 * interface representant un objet reponse permettant l'acces et la modification
 * du corps de la reponse et son content-Type par l'utilisateur du framework
 */
public interface ApplicationResponseInterface {


    void setBody(Object body);
    Object getBody();

    void setContentType(String contentType);
    String getContentType();
}
