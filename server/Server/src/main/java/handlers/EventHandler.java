package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import DataAccessObjects.AuthorizationDataAccess;
import DataAccessObjects.EventsDataAccess;
import model.Event;
import request.EventRequest;
import request.EventsRequest;
import response.EventResponse;
import response.EventsResponse;
import response.Response;
import services.EventService;

import static json.JsonConverter.convertObjectToJson;
import static server.Server.writeString;

/**
 * Created by jakeg on 3/1/2018.
 */

public class EventHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        boolean success = false;
        try{
            if(httpExchange.getRequestMethod().toLowerCase().equals("get")){
                Headers requestHeaders = httpExchange.getRequestHeaders();
                // Check to see if an "Authorization" header is present
                if (requestHeaders.containsKey("Authorization")) {

                    // Extract the auth token from the "Authorization" header
                    System.out.println("Requesting authorization.");
                    String authToken = requestHeaders.getFirst("Authorization");

                    //Find the authToken in the database
                    AuthorizationDataAccess authTokenDao = new AuthorizationDataAccess();
                    String user = authTokenDao.getUser(authToken);

                    //The user will not be null of the AuthToken was found
                    if(user != null) {

                        //Get the URI and determine if this is a single event or all event request
                        String exchangeUri = httpExchange.getRequestURI().toString();
                        System.out.println("URI: " + exchangeUri);
                        String[] uriArray = exchangeUri.split("/");

                        //The array will equal 1 if it is an entire event request
                        if(uriArray.length > 2) {
                            //Is this the correct user?
                            Event event = new EventsDataAccess().convertDatabaseToJava(uriArray[2]);
                            System.out.println("GetDescedant: '" + event.getDescendant()+ "'");
                            System.out.println("String user: '" + user + "'");
                            if(user.equals(event.getDescendant())) {

                                //Request is for a single event. Run the event service and return a response
                                System.out.println("Generating event request...");
                                EventResponse response = new EventService().getEvent(new EventRequest(uriArray[2], authToken));

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
                        else{
                            //Request is for all user's events. Run the event service and return a response
                            System.out.println("Generating events request...");
                            EventsResponse response = new EventService().getAllEvents(new EventsRequest(authToken));

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
                }
            }

            if(!success){
                Response response = new Response("Invalid authorization token.");

                //Convert the response into a json string
                String responseData = convertObjectToJson(new Response("Invalid authorization token."));

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
        catch(IOException e){
            //There was an internal server error
            httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);

            //
            httpExchange.getResponseBody().close();

            e.printStackTrace();
        }
    }
}
