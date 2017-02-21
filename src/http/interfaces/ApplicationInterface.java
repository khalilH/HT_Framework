package http.interfaces;

import exception.MethodNotAllowedException;
import http.Method;

/**
 * Interface devant etre implemente par les utilisateurs souhaitant ecrire des services
 */
public interface ApplicationInterface {

    default Object doGet(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.GET.toString());
    };
    default Object doPost(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.POST.toString());
    };
    default Object doPut(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.PUT.toString());
    };
    default Object doDelete(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.DELETE.toString());
    };


}
