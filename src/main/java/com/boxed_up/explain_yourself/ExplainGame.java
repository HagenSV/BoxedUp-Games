package com.boxed_up.explain_yourself;

import java.io.IOException;

import com.boxed_up.explain_yourself.api.*;
import com.boxed_up.library.graphics.Window;
import com.boxed_up.library.webgame.GameStateManager;
import com.boxed_up.library.webgame.PlayerManager;
import com.boxed_up.library.webgame.ServerViewManager;
import com.boxed_up.library.webgame.WebGame;
import com.boxed_up.library.webgame.api.*;

import static com.boxed_up.explain_yourself.ExplainGameConfigs.CLIENT_DIR_STRING;

public class ExplainGame {

    public final PlayerManager playerManager;
    public final GameStateManager gameStateManager;
    public final ServerViewManager serverViewManager;
    public final ExplainGameData gameData;

    public final WebGame game;

    public ExplainGame(Window w) throws IOException {
        gameData = new ExplainGameData(this);

        playerManager = new ExplainGamePM(gameData);
        gameStateManager = new ExplainGameSM(gameData);
        serverViewManager = new ExplainGameVM(w,gameData);

        game = new WebGame(playerManager,gameStateManager,serverViewManager);

        new RootRequest(game);
        new CommonRequest(game);
        new StaticRequest(game,CLIENT_DIR_STRING);
        new PlayerListRequest(game);
        new ViewRequest(game);
        new NameRequest(game);
        new JoinRequest(game);
        new PromptRequest(game, gameData);
        new SubmitRequest(game, gameData);
        new CardDataRequest(game, gameData);
        new VoteRequest(game, gameData);
        new GameRequest(game);
    }
}