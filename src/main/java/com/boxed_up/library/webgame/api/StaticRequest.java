package com.boxed_up.library.webgame.api;

import java.io.IOException;
import java.net.URL;

import com.boxed_up.library.FileSystem;
import com.boxed_up.library.webgame.WebGame;
import com.sun.net.httpserver.HttpExchange;

public class StaticRequest extends APIRequest {

    public static final String PATH = "/static/";

    private final String DIRECTORY;

    public StaticRequest(WebGame game, String dirPath) {
        super(PATH, game);
        DIRECTORY = dirPath;

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath().substring(PATH.length());

        if (path.isEmpty()){ path = "index.html"; }


        URL toSend = FileSystem.getFile(DIRECTORY, path);

        if (toSend == null){
            send404NotFound(exchange);
            return;
        }

        sendResponse(exchange, 200, toSend);
    }
    
}
