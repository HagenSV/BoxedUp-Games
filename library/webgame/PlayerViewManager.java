package library.webgame;

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

    public abstract void sendScreen(int playerId);
}
