package library;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

public abstract class WebGame implements HttpHandler {
    private final int MIN_PLAYERS;
    private final int MAX_PLAYERS;
    public final String GAME_ID;

    private boolean gameOver;
    private Server gameServer;

    private List<String> playerList;

    public WebGame( int minPlayers, int maxPlayers ) throws IOException {
        GAME_ID = ""+(int)(Math.random()*1_000_000);
        MIN_PLAYERS = minPlayers;
        MAX_PLAYERS = maxPlayers;
        gameOver = false;
        playerList = new ArrayList<String>( MAX_PLAYERS );
        gameServer = new Server(this);
        gameServer.createContext("/players", new PlayerHandler());
    }

    /**
     * Attempts to add a player to the game
     * @param name the name of the player to add
     * @return the updated name of the player, or null if the player could not be added
     */
    public String addPlayer( String name ) {
        if ( playerList.size() == MAX_PLAYERS ) {
            return null;
        }

        if ( playerList.contains( name ) ) {
            for ( int i = 1; i < MAX_PLAYERS; i++ ) {
                if ( !playerList.contains( name+" "+i ) ) {
                    playerList.add( name+" "+i );
                    return name+" "+i;
                }
            }
        }

        playerList.add( name );

        return name;
    }

    public abstract void start();

    public boolean canStart(){ return getPlayerCount() >= MIN_PLAYERS; }

    public boolean isOver(){ return gameOver; }

    public void endGame(){
        gameOver = true;
        gameServer.close();
    }

    public int getPlayerCount() {
        return playerList.size();
    }

    public List<String> getPlayers() {
        return new ArrayList<String>(playerList);
    }

    public static void sendString( HttpExchange exchange, int rCode, String s ) throws IOException {
        exchange.sendResponseHeaders( rCode, s.length() );

        OutputStream out = exchange.getResponseBody();

        out.write( s.getBytes() );
        out.close();
    }

    public static void sendFile( HttpExchange exchange, int rCode, File f ) throws IOException {
        exchange.sendResponseHeaders( rCode, f.length() );

        OutputStream out = exchange.getResponseBody();

        Files.copy( f.toPath(), out );
        out.close();   
    }

    private class PlayerHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            sendString(exchange, 200, playerList.toString().replaceAll("\\[|\\]", "") );
        }

    }
}
