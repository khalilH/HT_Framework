package http.interfaces;

import exception.MethodNotAllowedException;
import http.Method;

/**
 * Interface devant etre implementee par les classes services
 * d'un utilisateur du framework
 */
public interface ApplicationInterface {

    //Retourner directement la reponse/template d'erreur ??

    /**
     * Methode doGet du service
     * @param request la requete HTTP
     * @param session une session
     * @return un objet ApplicationResponseInterface contenant le corps et le content-Type de la reponse
     * @throws MethodNotAllowedException si l'utilisateur n'implemente pas la methode
     */
    default ApplicationResponseInterface doGet(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.GET.toString());
    };

    /**
     * Methode doPost du service
     * @param request la requete HTTP
     * @param session une session
     * @return un objet ApplicationResponseInterface contenant le corps et le content-Type de la reponse
     * @throws MethodNotAllowedException si l'utilisateur n'implemente pas la methode
     */
    default ApplicationResponseInterface doPost(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.POST.toString());
    };

    /**
     * Methode doPut du service
     * @param request la requete HTTP
     * @param session une session
     * @return un objet ApplicationResponseInterface contenant le corps et le content-Type de la reponse
     * @throws MethodNotAllowedException si l'utilisateur n'implemente pas la methode
     */
    default ApplicationResponseInterface doPut(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.PUT.toString());
    };

    /**
     * Methode doDelete du service
     * @param request la requete HTTP
     * @param session une session
     * @return un objet ApplicationResponseInterface contenant le corps et le content-Type de la reponse
     * @throws MethodNotAllowedException si l'utilisateur n'implemente pas la methode
     */
    default ApplicationResponseInterface doDelete(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.DELETE.toString());
    };


    /**
     * Methode doUpdate du service
     * @param request la requete HTTP
     * @param session une session
     * @return un objet ApplicationResponseInterface contenant le corps et le content-Type de la reponse
     * @throws MethodNotAllowedException si l'utilisateur n'implemente pas la methode
     */
    default ApplicationResponseInterface doUpdate(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.UPDATE.toString());
    };

    /**
     * Methode doOptions du service
     * @param request la requete HTTP
     * @param session une session
     * @return un objet ApplicationResponseInterface contenant le corps et le content-Type de la reponse
     * @throws MethodNotAllowedException si l'utilisateur n'implemente pas la methode
     */
    default ApplicationResponseInterface doOptions(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.OPTIONS.toString());
    };

    /**
     * Methode doSearch du service
     * @param request la requete HTTP
     * @param session une session
     * @return un objet ApplicationResponseInterface contenant le corps et le content-Type de la reponse
     * @throws MethodNotAllowedException si l'utilisateur n'implemente pas la methode
     */
    default ApplicationResponseInterface doSearch(RequestInterface request, SessionInterface session) throws MethodNotAllowedException {
        throw new MethodNotAllowedException(Method.SEARCH.toString());
    };




}
