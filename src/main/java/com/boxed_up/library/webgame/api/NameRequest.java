package com.boxed_up.library.webgame.api;

import java.io.IOException;

import com.boxed_up.library.webgame.WebGame;
import com.sun.net.httpserver.HttpExchange;

public class NameRequest extends APIRequest {

    public static final String PATH = "/name";

    public NameRequest(WebGame game) {
        super(PATH, game);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (validate(exchange) == -1){
            send401AccessDenied(exchange);
            return;
        }

        sendResponse(exchange, 200, getPlayerName(exchange));
    }
    
}
