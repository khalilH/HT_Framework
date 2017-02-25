package http.interfaces;

import exception.MethodNotAllowedException;
import http.Method;

/**
 * Interface devant etre implemente par les utilisateurs souhaitant ecrire des services
 */
public interface ApplicationInterface {

    //Retourner directement la reponse/template d'erreur ??

    default ApplicationResponseInterface doGet(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.GET.toString());
    };
    default ApplicationResponseInterface doPost(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.POST.toString());
    };
    default ApplicationResponseInterface doPut(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.PUT.toString());
    };
    default ApplicationResponseInterface doDelete(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.DELETE.toString());
    };


}
