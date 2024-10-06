package library.webgame.api;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import library.webgame.WebGame;

public class NameRequest extends APIRequest {

    public static final String PATH = "/name";

    public NameRequest(WebGame game) {
        super(PATH, game);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        super.handle(exchange);

        if (validate(exchange) == -1){
            send401AccessDenied(exchange);
            return;
        }

        sendResponse(exchange, 200, getPlayerName(exchange));
    }
    
}
