package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import response.Response;
import services.ClearService;

import static json.JsonConverter.convertObjectToJson;
import static server.Server.writeString;

/**
 * Created by jakeg on 3/1/2018.
 */

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if(httpExchange.getRequestMethod().toLowerCase().equals("post")) {

            System.out.println("Clearing database...");

            //Run the clear service and receive a response
            Response response = new ClearService().clear();

            //Convert the response into a json string
            String responseData = convertObjectToJson(response);

            //Send the initial HTTP_ok response
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

            //Grab the response body of the httpExchange
            OutputStream responseBody = httpExchange.getResponseBody();

            // Write the JSON string to the output stream.
            writeString(responseData, responseBody);

            //Close the responseBody signifying it is complete
            responseBody.close();

            httpExchange.getResponseBody().close();
        }
    }
}
