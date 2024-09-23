package library;

public abstract class OutputManager {

    private PlayerManager playerManager;

    public OutputManager(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    protected PlayerManager getPlayerManager(){
        return playerManager;
    }

    /**
     * Called whenever the game phase changes
     * @param screenId the id of the new phase
     */
    public abstract void renderScreen(int screenId);
}
