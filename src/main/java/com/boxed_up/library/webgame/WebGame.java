package com.boxed_up.library.webgame;

import com.boxed_up.library.Server;

import java.io.IOException;

import java.net.InetAddress;

public class WebGame {

    public final String GAME_ID;

    public final PlayerManager playerManager;
    public final GameStateManager gameStateManager;
    public final ServerViewManager serverViewManager;
    public final Server gameServer;

    private boolean gameOver;

    public WebGame(PlayerManager pm, GameStateManager gsm, ServerViewManager svm) throws IOException {
        GAME_ID = ""+(int)(Math.random()*1_000_000);

        if (InetAddress.getLocalHost().equals(InetAddress.getLoopbackAddress())){
            throw new IOException("Not connected to network");
        }

        playerManager = pm;
        gameStateManager = gsm;
        serverViewManager = svm;

        playerManager.game = this;
        gameStateManager.game = this;
        serverViewManager.game = this;

        playerManager.init();
        gameStateManager.init();
        serverViewManager.init();

        gameOver = false;
        gameServer = new Server();
    }

    public void start(){
        gameStateManager.start();
    }

    public boolean canStart(){ return playerManager.hasMin(); }

    public boolean isOver(){ return gameOver; }

    public void endGame(){
        gameStateManager.endGame();
        gameOver = true;
        gameServer.close();
    }
}