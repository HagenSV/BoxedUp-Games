package library.webgame;

import library.Server;
import library.webgame.api.PlayerListRequest;
import library.webgame.api.ViewRequest;

import java.io.IOException;


public abstract class WebGame {

    public final String GAME_ID;

    public final PlayerManager playerManager;
    public final GameStateManager gameStateManager;
    public final PlayerViewManager playerViewManager;
    public final ServerViewManager serverViewManager;
    public final Server gameServer;

    private boolean gameOver;

    public WebGame(PlayerManager pm, GameStateManager gsm, PlayerViewManager pvm, ServerViewManager svm) throws IOException {
        GAME_ID = ""+(int)(Math.random()*1_000_000);

        playerManager = pm;
        gameStateManager = gsm;
        playerViewManager = pvm;
        serverViewManager = svm;

        playerManager.game = this;
        gameStateManager.game = this;
        playerViewManager.game = this;
        serverViewManager.game = this;

        gameOver = false;
        gameServer = new Server();

        new ViewRequest(this);
        new PlayerListRequest(this);
    }

    public abstract void start();

    public boolean canStart(){ return playerManager.hasMin(); }

    public boolean isOver(){ return gameOver; }

    public void endGame(){
        gameOver = true;
        gameServer.close();
    }
}