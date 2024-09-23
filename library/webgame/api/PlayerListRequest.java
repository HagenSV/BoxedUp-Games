package library.webgame.api;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import library.webgame.WebGame;

public class PlayerListRequest extends APIRequest {
    public static final String PATH = "/players/";

    public PlayerListRequest(WebGame game) {
        super(PATH, game);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        sendResponse(exchange, 200, game.playerManager.getPlayers().toString().replaceAll("\\[|\\]", "") );

    }

}
