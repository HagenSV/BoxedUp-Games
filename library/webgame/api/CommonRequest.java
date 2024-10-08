package library.webgame.api;

import java.io.File;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import library.webgame.WebGame;

public class CommonRequest extends APIRequest {

    public static final String PATH = "/common/";
    public static final File DIRECTORY = new File("assets/common");

    public CommonRequest(WebGame game) {
        super(PATH, game);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath().substring(PATH.length());

        if (path.isEmpty()){ path = "index.html"; }

        File toSend = new File(DIRECTORY,path);

        if (!toSend.exists()){
            send404NotFound(exchange);
            return;
        }

        sendResponse(exchange, 200, toSend);
    }
    
}
