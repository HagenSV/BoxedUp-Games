package com.boxed_up.explain_yourself.api;

import java.io.IOException;

import com.boxed_up.explain_yourself.ExplainGameData;
import com.boxed_up.library.OutputLog;
import com.boxed_up.library.webgame.WebGame;
import com.boxed_up.library.webgame.api.APIRequest;
import com.sun.net.httpserver.HttpExchange;


import static com.boxed_up.explain_yourself.ExplainGameConfigs.*;


public class CardDataRequest extends APIRequest {

    public static final String PATH = "/card-data";
    private final ExplainGameData gameData;

    public CardDataRequest(WebGame game, ExplainGameData gameData) {
        super(PATH, game);
        this.gameData = gameData;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (validate(exchange) == -1 || game.gameStateManager.getPhase() != VOTE_PHASE ) {
            send401AccessDenied(exchange);
            return;
        }
            
        String prompt = gameData.getPrompt( gameData.getCardIndex() );
        String[] responses = gameData.getPromptResponses( gameData.getCardIndex(), "No Response" );

        String data = String.format( "%s\n%s\n%s", prompt, responses[0], responses[1] );

        OutputLog.getInstance().log(data);

        sendResponse( exchange, 200, data );
    }
    
}
