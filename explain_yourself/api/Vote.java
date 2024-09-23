package explain_yourself.api;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import library.webgame.WebGame;
import library.webgame.api.APIRequest;

public class Vote extends APIRequest {

    public static final String PATH = "/select-card/";

    public Vote(WebGame game) {
        super(PATH, game);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        int playerId = validate(exchange);

        String answer = body.split("=")[1];
        if (answer.equals("1")){
            game.playerManager.vote(playerId,cardIndex,0);
        } else if (answer.equals("2")){
            game.playerManager.vote(playerId,cardIndex,1);
        }

        sendResponse( exchange, 200, "Success" );

    }
}