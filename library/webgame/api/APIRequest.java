package library.webgame.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import library.webgame.WebGame;

public abstract class APIRequest implements HttpHandler {
    
    private static final Pattern namePattern = Pattern.compile( "name=([^;]*)" );
    private static final Pattern sessionPattern = Pattern.compile( "session=(\\w*);?" );
    
    protected final WebGame game;

    public APIRequest(String path, WebGame game){
        this.game = game;

        game.gameServer.createContext(path,this);
    }
    
    public int validate(HttpExchange exchange){
        //Get cookies sent with the request
        String cookies = exchange.getRequestHeaders().getFirst("Cookie");
        cookies = cookies == null ? "" : cookies;

        //Matchers
        Matcher nameCookie = namePattern.matcher( cookies );
        Matcher sessionCookie = sessionPattern.matcher( cookies );

        //Player info
        String sessionId = sessionCookie.find() ? sessionCookie.group(1) : "";
        String playerName = nameCookie.find() ? nameCookie.group(1) : "";
        int playerId = game.playerManager.getPlayers().indexOf(playerName);

        return sessionId.equals(game.GAME_ID) ? playerId : -1;
    }

    public static String getBody(HttpExchange exchange){
        try {
            //Get request body
            InputStream in = exchange.getRequestBody();
            byte[] inBytes = new byte[ in.available() ];
            in.read( inBytes );
            
            return new String( inBytes );
        } catch (IOException e){
            return null;
        }
    }

    public static void sendResponse( HttpExchange exchange, int rCode, String s ) throws IOException {
        exchange.sendResponseHeaders( rCode, s.length() );

        OutputStream out = exchange.getResponseBody();

        out.write( s.getBytes() );
        out.close();
    }

    public static void sendResponse( HttpExchange exchange, int rCode, File f ) throws IOException {
        exchange.sendResponseHeaders( rCode, f.length() );

        OutputStream out = exchange.getResponseBody();

        Files.copy( f.toPath(), out );
        out.close();   
    }


    public static void send400BadRequest(HttpExchange exchange) throws IOException {
        sendResponse(exchange, 400, "BadRequest");
    }

    public static void send401AccessDenied(HttpExchange exchange) throws IOException {
        sendResponse(exchange, 401, "Access Denied");
    }

    public static void send404NotFound( HttpExchange exchange) throws IOException {
        sendResponse(exchange, 404, "Not Found");
    }
}
