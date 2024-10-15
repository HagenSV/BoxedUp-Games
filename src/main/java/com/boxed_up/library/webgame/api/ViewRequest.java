package com.boxed_up.library.webgame.api;


import java.io.IOException;

import com.boxed_up.library.webgame.WebGame;
import com.sun.net.httpserver.HttpExchange;

public class ViewRequest extends APIRequest {

    public static final String PATH = "/game-status";

    public ViewRequest(WebGame game){
        super(PATH,game);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        int playerId = validate(exchange);

        if (playerId == -1){
            APIRequest.sendResponse(exchange, 403, "Forbidden");
            return;
        }

        game.playerManager.sendScreen(exchange, playerId);
    }
    
}
