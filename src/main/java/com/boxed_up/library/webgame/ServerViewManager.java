package com.boxed_up.library.webgame;

public abstract class ServerViewManager {

    protected WebGame game;

    public ServerViewManager(){}

    public void init(){}

    /**
     * Called whenever the game phase changes
     * @param screenId the id of the new phase
     */
    public abstract void renderScreen(int screenId);
}
