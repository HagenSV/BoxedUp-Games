package explain_yourself.api;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import explain_yourself.ExplainGameData;
import library.webgame.WebGame;
import library.webgame.api.APIRequest;

import static explain_yourself.ExplainGameConfigs.*;
public class CardData extends APIRequest {

    public static final String PATH = "/card-data/";
    private final ExplainGameData gameData;

    public CardData(WebGame game, ExplainGameData gameData) {
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

        System.out.println(data);

        sendResponse( exchange, 200, data );
    }
    
}
