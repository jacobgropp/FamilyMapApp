package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import request.LoginRequest;
import response.LoginResponse;
import services.LoginService;

import static json.JsonConverter.convertJsonToObject;
import static json.JsonConverter.convertObjectToJson;

import static server.Server.readString;
import static server.Server.writeString;

/**
 * Created by jakeg on 3/1/2018.
 */

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        boolean success = false;
        try{
            if(httpExchange.getRequestMethod().toLowerCase().equals("post")){
                //Write the InputStream request body to a String
                InputStream iStream = httpExchange.getRequestBody();
                String requestBody = readString(iStream);

                System.out.println(requestBody);

                //Convert the requestBody string, the json string, into a loginRequest object
                LoginRequest request = (LoginRequest)convertJsonToObject(requestBody,
                        new LoginRequest("username", "password"));

                //Send the request to the loginService and store the response
                LoginService service = new LoginService();
                LoginResponse response = service.login(request);

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
