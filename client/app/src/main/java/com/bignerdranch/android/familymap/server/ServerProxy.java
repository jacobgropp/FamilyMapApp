package com.bignerdranch.android.familymap.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.TreeSet;

import com.bignerdranch.android.familymap.model.Event;
import com.bignerdranch.android.familymap.model.Person;
import request.EventsRequest;
import request.LoginRequest;
import request.PersonRequest;
import request.PersonsRequest;
import request.RegisterRequest;
import response.EventsResponse;
import response.LoginResponse;
import response.PersonResponse;
import response.PersonsResponse;
import response.RegisterResponse;

import static json.JsonConverter.convertJsonToObject;

/**
 * Created by jakeg on 3/23/2018.
 */

public class ServerProxy {

    private String serverHost;
    private int serverPort;

    public ServerProxy(String serverHost, int serverPort){
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    private HttpURLConnection post(URL url){
        try {
            //Begin constructing the HTTP request by opening a connection
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            //API request in a POST request
            http.setRequestMethod("POST");

            //There will be an HTTP request body
            http.setDoOutput(true);

            //Return a response in JSON
            http.addRequestProperty("Accept", "application/json");

            return http;
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private HttpURLConnection get(URL url, String authToken) {
        try {
            //Begin constructing the HTTP request by opening a connection
            HttpURLConnection http = (HttpURLConnection) url.openConnection();

            //API Request is a GET request
            http.setRequestMethod("GET");

            //There will not be an HTTP request body
            http.setDoOutput(false);

            http.addRequestProperty("Authorization", authToken);

            //Return a response in JSON
            http.addRequestProperty("Accept", "application/json");

            return http;
        }
        catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public RegisterResponse registerUser(RegisterRequest request) {
        //Make a register API operation
        if(testForTimeouts()) {
            try {
                URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/register");

                HttpURLConnection http = post(url);

                String reqData =
                        "{" +
                                "\"userName\":\"" + request.getUsername() + "\"," +
                                "\"password\":\"" + request.getPassword() + "\"," +
                                "\"email\":\"" + request.getEmail() + "\"," +
                                "\"firstName\":\"" + request.getFirstName() + "\"," +
                                "\"lastName\":\"" + request.getLastName() + "\"," +
                                "\"gender\":\"" + request.getGender() + "\"" +
                                "}";

                OutputStream reqBody = http.getOutputStream();

                writeString(reqData, reqBody);

                reqBody.close();

                //Connect to the com.bignerdranch.android.familymap.server and send the HTTP request
                http.connect();

                if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    System.out.println("Registration is a success.");

                    //Read the response body from the successful connection
                    InputStream iStream = http.getInputStream();
                    String requestBody = readString(iStream);

                    //Convert the JSON from the response body into a RegisterResponse
                    RegisterResponse response = (RegisterResponse) convertJsonToObject(requestBody,
                            new RegisterResponse("authToken", "username", "password"));

                    //response.setMessage(requestBody);
                    return response;

                } else {
                    System.out.println("ERROR: " + http.getResponseMessage());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public LoginResponse loginUser(LoginRequest request) {
        //Make a register API operation
        try{
            URL url = new URL("http://" + serverHost + ":" + serverPort + "/user/login");

            HttpURLConnection http = post(url);

            String reqData =
                    "{" +
                        "\"userName\":\"" + request.getUsername() + "\"," +
                        "\"password\":\"" + request.getPassword() + "\"" +
                    "}";
            //System.out.println(http.getPermission());
            OutputStream reqBody = http.getOutputStream();
            //System.out.println(reqBody);

            writeString(reqData, reqBody);

            reqBody.close();

            //Connect to the com.bignerdranch.android.familymap.server and send the HTTP request
            http.connect();

            //Check if connection made was a success
            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Login is a success.");

                //Read the response body from the successful connection
                InputStream iStream = http.getInputStream();
                String requestBody = readString(iStream);

                //Convert JSON from the response body into a LoginResponse object
                LoginResponse response = (LoginResponse)convertJsonToObject(requestBody,
                        new LoginResponse("authToken", "username", "personID"));

                System.out.println("Response after JSON conversion: " +response.toString());

                response.setMessage(requestBody);
                return response;

            }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public PersonResponse findPerson(PersonRequest request) {
        //Test IP address and Host
        if(testForTimeouts()) {
            try {
                //Make a register API operation
                URL url = new URL("http://" + serverHost + ":" + serverPort + "/person/" + request.getPersonID());

                //Begin constructing the HTTP request by opening a connection
                HttpURLConnection http = get(url, request.getAuthToken());

                //Connect to the com.bignerdranch.android.familymap.server and send the HTTP request
                http.connect();

                //Check if connection made was a success
                if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    //Read the response body from the successful connection
                    InputStream iStream = http.getInputStream();
                    String requestBody = readString(iStream);

                    //Convert JSON from the response body into a LoginResponse object
                    PersonResponse response = (PersonResponse) convertJsonToObject(requestBody,
                            new PersonResponse("authToken", "username"));

                    response.setMessage(requestBody);
                    return response;

                } else {
                    System.out.println("ERROR: " + http.getResponseMessage());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PersonResponse response = new PersonResponse(null, null);
        response.setMessage("ERROR: Bad Request");
        return response;
    }

    public PersonsResponse findPersons(PersonsRequest request){
        if(testForTimeouts()) {
            try {
                //Make a register API operation
                URL url = new URL("http://" + serverHost + ":" + serverPort + "/person/");

                //Begin constructing the HTTP request by opening a connection
                HttpURLConnection http = get(url, request.getAuthToken());

                //Connect to the com.bignerdranch.android.familymap.server and send the HTTP request
                http.connect();

                //Check if connection made was a success
                if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    System.out.println("Persons retrieval is a success.");

                    //Read the response body from the successful connection
                    InputStream iStream = http.getInputStream();
                    String requestBody = readString(iStream);

                    //Convert JSON from the response body into a LoginResponse object
                    PersonsResponse response = (PersonsResponse) convertJsonToObject(requestBody,
                            new PersonsResponse(new TreeSet<Person>()));

                    System.out.println("Response after JSON conversion: " + response.toString());

                    response.setMessage(requestBody);
                    return response;

                } else {
                    System.out.println("ERROR: " + http.getResponseMessage());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PersonsResponse response = new PersonsResponse(null);
        response.setMessage("ERROR: Bad Request");
        return response;
    }

    public EventsResponse findEvents(EventsRequest request){
        if(testForTimeouts()) {
            try {
                //Make a register API operation
                URL url = new URL("http://" + serverHost + ":" + serverPort + "/event/");

                //Begin constructing the HTTP request by opening a connection
                HttpURLConnection http = get(url, request.getAuthToken());

                //Connect to the com.bignerdranch.android.familymap.server and send the HTTP request
                http.connect();

                //Check if connection made was a success
                if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    System.out.println("Events retrieval is a success.");

                    //Read the response body from the successful connection
                    InputStream iStream = http.getInputStream();
                    String requestBody = readString(iStream);

                    //Convert JSON from the response body into a LoginResponse object
                    EventsResponse response = (EventsResponse) convertJsonToObject(requestBody,
                            new EventsResponse(new TreeSet<Event>()));

                    System.out.println("Response after JSON conversion: " + response.toString());

                    response.setMessage(requestBody);
                    return response;

                } else {
                    System.out.println("ERROR: " + http.getResponseMessage());
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        EventsResponse response = new EventsResponse(null);
        response.setMessage("ERROR: Bad Request");
        return response;
    }

    private boolean testForTimeouts(){
        boolean exists = false;
        try{
            SocketAddress address = new InetSocketAddress(serverHost, serverPort);

            Socket sock = new Socket();

            int timeoutMS = 10000;
            sock.connect(address, timeoutMS);
            exists = true;
            return exists;
        }
        catch(IOException e){
            e.printStackTrace();
            return exists;
        }
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    public static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}
