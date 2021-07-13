package server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;

import DataAccessObjects.Database.DatabaseConnection;
import handlers.ClearHandler;
import handlers.EventHandler;
import handlers.FileHandler;
import handlers.FillHandler;
import handlers.LoadHandler;
import handlers.LoginHandler;
import handlers.PersonHandler;
import handlers.RegisterHandler;

import static json.Json.load;

/**
 * Created by jakeg on 3/1/2018.
 */

public class Server {

    //Maximum number of waiting incoming connections to queue.
    private static final int MAX_WAITING_CONNECTIONS = 10;

    //Implements Http network protocol
    private HttpServer server;

    public static void main(String[] args) {
        //Creates the database tables
        createDatabase();

        //Loads included Json files
        loadJsonFiles();

        String portNumber = args[0];
        new Server().run(portNumber);
    }

    /**
     * Creates and runs the server
     *
     * @param portNumber
     */
    private void run(String portNumber){
        System.out.println("Intializing Http server");
        try{
            server = HttpServer.create(new InetSocketAddress(Integer.parseInt(portNumber)), MAX_WAITING_CONNECTIONS);
        }
        catch(IOException e){
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        //Create all the contexts.
        System.out.println("Creating contexts");

        //A register request
        server.createContext("/user/register", new RegisterHandler());

        //A login request
        server.createContext("/user/login", new LoginHandler());

        //A clear request
        server.createContext("/clear", new ClearHandler());

        //A fill request
        server.createContext("/fill", new FillHandler());

        //A load request
        server.createContext("/load", new LoadHandler());

        //A person request
        server.createContext("/person", new PersonHandler());

        //An event request
        server.createContext("/event", new EventHandler());

        //The default file handler
        server.createContext("/", new FileHandler());

        System.out.println("Server started");

        server.start();
    }

    /**
     * Creates the User, Person, Event, and AuthToken tables in the database
     */
    public static void createDatabase(){
        DatabaseConnection familyMapDB = new DatabaseConnection();
        familyMapDB.openDatabase();
        familyMapDB.createTables();
        familyMapDB.closeDatabase();
    }

    /**
     * Loads the json files in the project
     */
    public static void loadJsonFiles(){
        load();
    }

    /**
     * Writes a String object to an OutputStream object
     * @param str
     * @param os
     * @throws IOException
     */
    public static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    /**
     * Reads the InputStream and converts it to a String object
     * @param is
     * @return sb.toString()
     * @throws IOException
     */
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
