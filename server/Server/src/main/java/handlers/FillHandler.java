package handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import request.FillRequest;
import response.Response;
import services.FillService;

import static json.JsonConverter.convertObjectToJson;
import static server.Server.readString;
import static server.Server.writeString;

/**
 * Created by jakeg on 3/1/2018.
 */

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        boolean success = false;

        if(httpExchange.getRequestMethod().toLowerCase().equals("post")) {

            //Write the InputStream request body to a String
            InputStream iStream = httpExchange.getRequestBody();
            String requestBody = readString(iStream);

            //Get the URI and determine if this is a default fill or a specific generation
            String exchangeUri = httpExchange.getRequestURI().toString();
            System.out.println("URI: " + exchangeUri);
            String[] uriArray = exchangeUri.split("/");
            System.out.println(uriArray[0] +" "+ uriArray[1]+ " "+uriArray[2] + " " + uriArray[3]);

            System.out.println(uriArray.length);
            if(uriArray.length > 3) {
                //URI contains more than 2 arguments
                System.out.println("Running fill service...");

                //convert the generations argument into an int
                int generations = Integer.parseInt(uriArray[3]);

                //Run the service
                Response response = new FillService().fill(new FillRequest(uriArray[2], generations));

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
            else{
                //URI contains only two arguments. Run default FillRequest
                //Convert the requestBody string, the json string, into a default FillRequest object
                System.out.println("Running default fill service...");
                FillRequest request = new FillRequest(uriArray[2]);

                //Run the service
                Response response = new FillService().fill(request);

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
        }

        if(!success){
            //Convert the response into a json string
            String responseData = convertObjectToJson(new Response("Incorrect input"));

            //The user sent a bad request
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
