package explain_yourself.api;

import java.io.IOException;
import java.util.regex.Matcher;

import com.sun.net.httpserver.HttpExchange;

import library.OutputLog;
import library.webgame.WebGame;
import library.webgame.api.APIRequest;

import static explain_yourself.ExplainGameConfigs.*;

public class JoinRequest extends APIRequest {

    public static final String PATH = "/join";

    public JoinRequest(WebGame game) {
        super(PATH, game);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String body = getBody(exchange);
        Matcher nameBody = namePattern.matcher(body);

        //Player is already authenticated
        if ( validate(exchange) != -1 ) {
            game.playerManager.setPlayerPhase(getPlayerId(exchange), JOIN_PHASE);
            send303Redirect(exchange, GameRequest.PATH);
            return;
        }

        if ( !nameBody.find() ) {
            OutputLog.getInstance().log(getInfo(exchange)+" Bad Request: "+body);
            send400BadRequest(exchange);
            return;
        }

        if (!game.gameStateManager.canJoin()) {
            sendResponse(exchange, 200, GAME_CLOSED);
            return;
        }

        //Only add player to game if their session id does not exist and does not equal the current session id
        OutputLog.getInstance().log(getInfo(exchange)+" "+body);
        String playerName = game.playerManager.addPlayer( nameBody.group( 1 ) );
        exchange.getResponseHeaders().add( "Set-Cookie", "name="+playerName+"; HttpOnly" );
        exchange.getResponseHeaders().add( "Set-Cookie", "session="+game.GAME_ID+"; HttpOnly" );
        send303Redirect(exchange, GameRequest.PATH);
    }
    
}
