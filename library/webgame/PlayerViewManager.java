package library.webgame;

import com.sun.net.httpserver.HttpExchange;
public abstract class PlayerViewManager {
    
    protected WebGame game;

    public PlayerViewManager(){}

    public abstract void sendScreen(HttpExchange exchange, int playerId);
}
