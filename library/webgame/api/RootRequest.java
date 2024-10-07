package library.webgame.api;

import java.io.File;
import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import library.webgame.WebGame;

public class RootRequest extends APIRequest {

    public static final String PATH = "/";

    private final File DIRECTORY;

    public RootRequest(WebGame game, File dir) {
        super(PATH, game);
        if (!dir.isDirectory()){ throw new IllegalArgumentException("dir must be a directory!"); }
        DIRECTORY = dir;

    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath().substring(1);

        if (path.isEmpty()){ path = "index.html"; }

        File toSend = new File(DIRECTORY,path);

        if (!toSend.exists()){
            send404NotFound(exchange);
            return;
        }

        sendResponse(exchange, 200, toSend);
    }
    
}
