package http;

import http.Method;
import http.Request;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public class RequestAnalyser {

    public static Request analyse(String request){
        Request res;
        Method method = null;
        Url url = new Url();
        HashMap<String, String> headers = new HashMap<>();
        Scanner scanner = new Scanner(request);
        String line;
        String[] tab;

        // Getting the METHOD
        if(scanner.hasNextLine()) {
            line = scanner.nextLine();
            tab = line.split(" ");
            if (tab != null) {
                method = Method.valueOf(tab[0]);
                url.parseUrl(tab[1]);
            }
        }

        // Getting the headers
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            //TODO A adapter pcq j'ai mis un retour chariot entre les headers et le body (on peut l'enlever aussi) 
            tab = line.split(": ");
            headers.put(tab[0], tab[1]);
        }

        // Getting the URL
        url.setHost(headers.get(Headers.HOST));

        // Getting the cookies ??

        // Getting the body

        res = new Request(url, method, headers, null);

        return res;
    }


}
