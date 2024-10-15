package com.boxed_up.explain_yourself.api;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;

import com.boxed_up.library.OutputLog;
import com.boxed_up.library.webgame.WebGame;
import com.boxed_up.library.webgame.api.APIRequest;
import com.sun.net.httpserver.HttpExchange;

import static com.boxed_up.explain_yourself.ExplainGameConfigs.GAME_CLOSED;
import static com.boxed_up.explain_yourself.ExplainGameConfigs.JOIN_PHASE;

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
        String inputName = nameBody.group(1);

        try {
            String result = java.net.URLDecoder.decode(inputName, StandardCharsets.UTF_8);
            String playerName = game.playerManager.addPlayer( result );
            exchange.getResponseHeaders().add( "Set-Cookie", "name="+playerName+"; HttpOnly" );
            exchange.getResponseHeaders().add( "Set-Cookie", "session="+game.GAME_ID+"; HttpOnly" );
            send303Redirect(exchange, GameRequest.PATH);

        } catch (UnsupportedEncodingException e) {
            OutputLog.getInstance().log("Failed to decode name string");
        }
    }
    
}
