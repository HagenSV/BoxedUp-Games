package library;

public abstract class OutputManager {

    private PlayerManager playerManager;

    public OutputManager(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    /**
     * Gets the player manager tied to this output manager
     * @return
     */
    protected PlayerManager getPlayerManager(){
        return playerManager;
    }

    /**
     * Called whenever the game phase changes
     * @param screenId the id of the new phase
     */
    public abstract void renderScreen(int screenId);
}
