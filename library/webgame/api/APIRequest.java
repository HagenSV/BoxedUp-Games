package library.webgame.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.sun.net.httpserver.HttpExchange;

import library.webgame.WebGame;

public class APIRequest {
    
    private static final Pattern namePattern = Pattern.compile( "name=([^;]*)" );
    private static final Pattern sessionPattern = Pattern.compile( "session=(\\w*);?" );

    
    public static int validate(WebGame game, HttpExchange exchange){
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
}
