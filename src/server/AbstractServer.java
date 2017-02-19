package server;

import exception.MapperFileException;
import http.RequestAnalyser;
import http.StatusCode;
import http.interfaces.RequestInterface;
import http.interfaces.ResponseInterface;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractServer implements ServerInterface{

    protected int port;
    protected ServerSocket serverSocket;
    protected List<Socket> clientSockets;

    public AbstractServer(int port) {
        StatusCode.init();
        this.port = port;
        clientSockets = new ArrayList<>();
    }

    public abstract ResponseInterface handleRequest(RequestInterface request);

    public void start() throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Listening on "+port+"...");
        while (true) {
            clientSockets.add(serverSocket.accept());
            System.out.println("Connection opened");
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket clientSocket = clientSockets.remove(0);
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        String inputLine, httpRequestText = "";
                        // hasBody permet de savoir si on a un body, isReadingBodu permet de savoir si on est en train de lire le body
                        boolean hasBody = false, isReadingBody = false;
                        // longueur du body
                        int contentLength = 0;
                        inputLine = in.readLine();
                        // tant que la ligne est non vide OU vide mais qu'il y a un body, on continue
                        while (!inputLine.equals("") || (inputLine.equals("") && hasBody == true)) {
                            // pour savoir s'il y a un body en récupérant aussi sa taille
                            if(inputLine.contains("Content-Length:")) {
                                hasBody = true;
                                contentLength = Integer.parseInt(inputLine.split(": ")[1]);
                            }
                            // si la ligne est vide c'est qu'on atteint la séparation entre headers et body donc on entre dans la phase de lecture du body
                            // on passe donc le traitement de la ligne courante ("") avec continue
                            if(inputLine.equals("")) {
                                hasBody = false;
                                isReadingBody = true;
                            }
                            // si on ne lit pas le body, on lit la ligne de manière habituelle (pas d'action suplémentaire nécessaire)
                            // sinon on lit le body char par char car il ne contient pas de retour chariot à la fin
                            // RAPPEL : le content type d'un POST avec body est SOUVENT "application/x-www-form-urlencoded" et le body est en format "parameter=value&also=another"
                            //          http://stackoverflow.com/questions/14551194/how-are-parameters-sent-in-an-http-post-request

                            // LADI : Dans la plupart des cas c'est ca, mais le body peut etre sous n'importe quel Content-Type.
                            // sur mozilla quand j'envoie du text brut c'est du text/plain
                            // et pour cheapmeal halitran il me semble qu'on mettait parfois application/json dans nos requetes ajax

                            if(isReadingBody){
                                char[] params = new char[contentLength];
                                isReadingBody = false;
                                in.read(params, 0, contentLength);
//                                System.out.println(new String(params));
                                inputLine = new String(params);
                                httpRequestText += System.lineSeparator() + inputLine + System.lineSeparator();
                                break;
                            }else{
                                httpRequestText += inputLine + System.lineSeparator();
                                inputLine = in.readLine();
                            }
                        }
                        System.out.println("=============");
                        System.out.println(httpRequestText);
                        RequestInterface request = RequestAnalyser.analyse(httpRequestText);
                        ResponseInterface response = handleRequest(request);
                        out.println(response.toString());
                        out.close();
                        in.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            t.start();
        }
    }

    public void shutdown() throws IOException {
        serverSocket.close();
    }




}
