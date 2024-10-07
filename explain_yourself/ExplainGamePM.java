package explain_yourself;

import com.sun.net.httpserver.HttpExchange;

import library.webgame.PlayerManager;
import library.webgame.api.APIRequest;

import static explain_yourself.ExplainGameConfigs.*;

import java.io.IOException;

public class ExplainGamePM extends PlayerManager {

    private final ExplainGameData gameData;

    public ExplainGamePM(int minPlayers, int maxPlayers, ExplainGameData gameData){
       super(minPlayers,maxPlayers);
       this.gameData = gameData;
    }

    @Override
    public void sendScreen(HttpExchange exchange, int playerId) throws IOException {
        int gamePhase = game.gameStateManager.getPhase();
        int playerPhase = game.playerManager.getPlayerPhase(playerId);

        if ( playerPhase == gamePhase ) {
            APIRequest.sendResponse(exchange, 208, "Already Sent");
            return;
        }
        
        switch (gamePhase) {
            case PROMPT_PHASE:
                if ( playerPhase == WAIT_PHASE ) { APIRequest.sendResponse( exchange, 200, WAIT_SCREEN); } 
                else {
                    APIRequest.sendResponse( exchange, 200, RESPONSE_FORM );
                    game.playerManager.setPlayerPhase( playerId, PROMPT_PHASE );
                }
                break;

            case CARD_INTRO_PHASE:
                APIRequest.sendResponse(exchange, 200, WAIT_SCREEN);
                game.playerManager.setPlayerPhase(playerId, CARD_INTRO_PHASE);
                break;

            case VOTE_PHASE:
                int[] responders = gameData.getPromptResponders(gameData.getCardIndex());
                if ( playerPhase == WAIT_PHASE ) { APIRequest.sendResponse( exchange, 200, WAIT_SCREEN); }
                else if (playerId == responders[0] || playerId == responders[1]) {
                    APIRequest.sendResponse( exchange, 200, WAIT_SCREEN );
                    game.playerManager.setPlayerPhase( playerId, WAIT_PHASE );
                } else {
                    APIRequest.sendResponse( exchange, 200, CARD_CHOOSER );
                    game.playerManager.setPlayerPhase( playerId, VOTE_PHASE );
                }
                break;

            case VOTE_RESULTS_PHASE:
                APIRequest.sendResponse(exchange, 200, WAIT_SCREEN);
                game.playerManager.setPlayerPhase(playerId, VOTE_RESULTS_PHASE);
                break;

            case GAME_OVER:
                APIRequest.sendResponse( exchange, 200, GAME_OVER_SCREEN );
                game.playerManager.setPlayerPhase( playerId, GAME_OVER );
                break;

            default:
                APIRequest.sendResponse( exchange, 418, "I AM A TEA POT :D" );
                break;
        }
    }
}
