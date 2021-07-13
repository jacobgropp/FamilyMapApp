package handlers;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Paths;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by jakeg on 3/1/2018.
 */

public class FileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        OutputStream os = exchange.getResponseBody();
        try {
            byte[] file;
            String uri = exchange.getRequestURI().toString();

            //Load the home page
            if (uri.equals("/")) {
                System.out.println("Home page loading...");
                file = Files.readAllBytes(Paths.get("Server/web/index.html"));
            }
            //Load all other pages
            else{
                file = Files.readAllBytes(Paths.get("Server/web" + uri));
            }
            exchange.sendResponseHeaders(HTTP_OK, 0);
            os.write(file);
            os.close();
        }
        catch(Exception e){
            e.printStackTrace();
            os.close();
        }
    }
}
