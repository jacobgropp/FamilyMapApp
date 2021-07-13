package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import model.Event;
import model.Person;
import model.User;
import request.LoadRequest;
import response.Response;
import services.LoadService;

import static json.JsonConverter.convertJsonToObject;
import static json.JsonConverter.convertObjectToJson;
import static server.Server.readString;
import static server.Server.writeString;

/**
 * Created by jakeg on 3/1/2018.
 */

public class LoadHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        boolean success = false;

        if(httpExchange.getRequestMethod().toLowerCase().equals("post")) {

            //Write the InputStream request body to a String
            InputStream iStream = httpExchange.getRequestBody();
            String requestBody = readString(iStream);

            //Convert the requestBody string, the json string, into a loginRequest object
            LoadRequest request = (LoadRequest)convertJsonToObject(requestBody,
                    new LoadRequest(new ArrayList<User>(), new ArrayList<Person>(), new ArrayList<Event>()));

            //Send the request to the loginService and store the response
            LoadService service = new LoadService();
            Response response = service.load(request);

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

            success = true;
        }
        if(!success){
            //Convert the response into a json string
            String responseData = convertObjectToJson(new Response("Incorrect input"));

            //Send the initial HTTP_ok response
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

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
