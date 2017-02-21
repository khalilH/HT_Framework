package http.interfaces;

import exception.MethodNotAllowedException;
import http.Method;

/**
 * Created by Khalil on 14/02/2017.
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
