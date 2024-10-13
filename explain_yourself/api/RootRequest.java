package explain_yourself.api;

import java.io.IOException;
import java.net.URL;

import com.sun.net.httpserver.HttpExchange;

import library.FileSystem;
import library.webgame.WebGame;
import library.webgame.api.APIRequest;

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

        URL toSend = FileSystem.getFile("assets/common/index.html");

        if (toSend == null){
            send404NotFound(exchange);
            return;
        }

        sendResponse(exchange, 200, toSend);
    }
    
}
