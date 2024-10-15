package com.boxed_up.explain_yourself.api;


import java.io.IOException;

import com.boxed_up.explain_yourself.ExplainGameData;
import com.boxed_up.library.webgame.WebGame;
import com.boxed_up.library.webgame.api.APIRequest;
import com.sun.net.httpserver.HttpExchange;


import static com.boxed_up.explain_yourself.ExplainGameConfigs.PROMPT_PHASE;

public class PromptRequest extends APIRequest {

    private static final String PATH = "/prompt";
    private final ExplainGameData gameData;

    public PromptRequest(WebGame game, ExplainGameData gameData) {
        super(PATH, game);
        this.gameData = gameData;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        int playerId = validate(exchange);

        if (playerId == -1 || game.gameStateManager.getPhase() != PROMPT_PHASE ){
            send401AccessDenied(exchange);
            return;
        }

        sendResponse( exchange, 200, gameData.getPrompt( playerId, gameData.getResponseCount(playerId) ) );
    }
    
}
