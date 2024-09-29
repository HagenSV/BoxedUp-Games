package library.webgame;

public abstract class ServerViewManager {

    protected WebGame game;

    public ServerViewManager(){}

    /**
     * Called whenever the game phase changes
     * @param screenId the id of the new phase
     */
    public abstract void renderScreen(int screenId);
}
