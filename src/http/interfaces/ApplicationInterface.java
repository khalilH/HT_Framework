package http.interfaces;

/**
 * Created by Khalil on 14/02/2017.
 */
public interface ApplicationInterface {

    Object doGet(RequestInterface request);
    Object doPost(RequestInterface resquest);
    Object doPut(RequestInterface request);
    Object doDelete(RequestInterface request);

}
