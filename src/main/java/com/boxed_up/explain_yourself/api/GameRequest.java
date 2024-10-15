package com.boxed_up.explain_yourself.api;

import static com.boxed_up.explain_yourself.ExplainGameConfigs.GAME_PAGE;

import java.io.IOException;

import com.boxed_up.library.webgame.WebGame;
import com.boxed_up.library.webgame.api.APIRequest;
import com.sun.net.httpserver.HttpExchange;


public class GameRequest extends APIRequest {

    public static final String PATH = "/game1";

    public GameRequest(WebGame game){
        super(PATH, game);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        
        // If player is not logged in, send them to the login page
        if (validate(exchange) == -1){
            send303Redirect(exchange, "/");
            return;
        }

        sendResponse(exchange, 200, GAME_PAGE);
    }
    
}
