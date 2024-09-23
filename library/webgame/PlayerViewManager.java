package library.webgame;

import com.sun.net.httpserver.HttpExchange;
public abstract class PlayerViewManager {
    
    private GameStateManager gameStateManager;
    private PlayerManager playerManager;

    public PlayerViewManager(GameStateManager gsm, PlayerManager pm){
        this.gameStateManager = gsm;
        this.playerManager = pm;
    }

    public GameStateManager getGameStateManager(){
        return gameStateManager;
    }

    public PlayerManager getPlayerManager(){
        return playerManager;
    }

    public abstract void sendScreen(HttpExchange exchange, int playerId);
}
