package library.webgame;

import com.sun.net.httpserver.HttpExchange;
public abstract class PlayerViewManager {
    
    protected final GameStateManager gameStateManager;
    protected final PlayerManager playerManager;

    public PlayerViewManager(GameStateManager gsm, PlayerManager pm){
        this.gameStateManager = gsm;
        this.playerManager = pm;
    }

    public abstract void sendScreen(HttpExchange exchange, int playerId);
}
