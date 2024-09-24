package explain_yourself;
import java.io.IOException;

import explain_yourself.api.CardDataRequest;
import explain_yourself.api.JoinRequest;
import explain_yourself.api.PromptRequest;
import explain_yourself.api.SubmitRequest;

import static explain_yourself.ExplainGameConfigs.*;

import library.graphics.Window;
import library.webgame.GameStateManager;
import library.webgame.PlayerManager;
import library.webgame.PlayerViewManager;
import library.webgame.ServerViewManager;
import library.webgame.WebGame;
import library.webgame.api.NameRequest;
import library.webgame.api.PlayerListRequest;
import library.webgame.api.RootRequest;
import library.webgame.api.ViewRequest;

public class ExplainGame {

    public final PlayerManager playerManager;
    public final GameStateManager gameStateManager;
    public final PlayerViewManager playerViewManager;
    public final ServerViewManager serverViewManager;
    public final ExplainGameData gameData;

    public final WebGame game;

    public ExplainGame(Window w) throws IOException {
        gameData = new ExplainGameData(this);

        playerManager = new PlayerManager(MIN_PLAYERS, MAX_PLAYERS);
        gameStateManager = new ExplainGameSM(gameData);
        playerViewManager = new ExplainGamePVM();
        serverViewManager = new ExplainGameVM(w,this);

        game = new WebGame(playerManager,gameStateManager,playerViewManager,serverViewManager);

        new RootRequest(game, DIRECTORY);
        new PlayerListRequest(game);
        new ViewRequest(game);
        new NameRequest(game);
        new JoinRequest(game);
        new PromptRequest(game, gameData);
        new SubmitRequest(game, gameData);
        new CardDataRequest(game, gameData);
    }
}