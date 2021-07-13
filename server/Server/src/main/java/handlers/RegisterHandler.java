package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import request.RegisterRequest;
import response.RegisterResponse;
import services.RegisterService;

import static json.JsonConverter.convertJsonToObject;
import static json.JsonConverter.convertObjectToJson;

import static server.Server.readString;
import static server.Server.writeString;

/**
 * Created by jakeg on 3/1/2018.
 */

public class  RegisterHandler implements HttpHandler{
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        boolean success = false;
        try{
            if(httpExchange.getRequestMethod().toLowerCase().equals("post")){
                System.out.println("Running register service...");

                //Write the InputStream request body to a String
                InputStream iStream = httpExchange.getRequestBody();
                String requestBody = readString(iStream);

                //Convert the requestBody string, the json string, into a loginRequest object
                RegisterRequest request = (RegisterRequest)convertJsonToObject(requestBody,
                        new RegisterRequest("username", "password"));

                //Send the request to the loginService and store the response
                RegisterService service = new RegisterService();
                RegisterResponse response = service.register(request);

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
                //The user sent a bad request
                httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);

                httpExchange.getResponseBody().close();
            }
        }
        catch(IOException e){
            //There was an internal server error
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            //
            httpExchange.getResponseBody().close();

            e.printStackTrace();
        }
    }
}
