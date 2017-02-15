package tests;


import java.io.IOException;

/**
 * Created by patrick.tran on 07/02/2017.
 */
public class Main {

    public static void main(String [] args){
        try {
            EchoServer echoServer = new EchoServer(Integer.parseInt(args[0]));
            echoServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
