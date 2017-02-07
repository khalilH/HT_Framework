package tests;


import http.Request;
import http.RequestAnalyser;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public class Main {

    public static void main(String [] args){
        String str = "GET /hello.txt HTTP/1.1\n" +
                "User-Agent: curl/7.16.3 libcurl/7.16.3 OpenSSL/0.9.7l zlib/1.2.3\n" +
                "Host: www.example.com\n" +
                "Accept-Language: en, mi\n";
        Request request = RequestAnalyser.analyse(str);
    }

}
