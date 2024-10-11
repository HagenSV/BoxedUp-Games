package explain_yourself;

import java.io.IOException;

import explain_yourself.api.*;

import static explain_yourself.ExplainGameConfigs.*;

import library.graphics.Window;
import library.webgame.GameStateManager;
import library.webgame.PlayerManager;
import library.webgame.ServerViewManager;
import library.webgame.WebGame;
import library.webgame.api.*;

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
        new StaticRequest(game,CLIENT_DIRECTORY);
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