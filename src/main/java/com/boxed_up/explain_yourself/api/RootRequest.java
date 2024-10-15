package com.boxed_up.explain_yourself.api;

import java.io.IOException;
import java.net.URL;

import com.boxed_up.library.FileSystem;
import com.boxed_up.library.webgame.WebGame;
import com.boxed_up.library.webgame.api.APIRequest;
import com.sun.net.httpserver.HttpExchange;


public class RootRequest extends APIRequest {

    public static final String PATH = "/";

    public RootRequest(WebGame game) {
        super(PATH, game);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (validate(exchange) != -1){
            send303Redirect(exchange, GameRequest.PATH);
        }

        URL toSend = FileSystem.getFile("common/index.html");

        if (toSend == null){
            send404NotFound(exchange);
            return;
        }

        sendResponse(exchange, 200, toSend);
    }
    
}
