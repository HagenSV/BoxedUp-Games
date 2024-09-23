package explain_yourself;

import com.sun.net.httpserver.HttpExchange;

import library.webgame.GameStateManager;
import library.webgame.PlayerManager;
import library.webgame.api.APIRequest;

import static explain_yourself.GameConfigs.*;

import java.io.IOException;

public class PlayerViewManager extends library.webgame.PlayerViewManager {

    public PlayerViewManager(GameStateManager gsm, PlayerManager pm) {
        super(gsm, pm);
    }

    @Override
    public void sendScreen(HttpExchange exchange, int playerId) {
        int gamePhase = gameStateManager.getPhase();
        int playerPhase = playerManager.getPlayerPhase(playerId);

        try {

            if ( playerPhase == gamePhase ) {
                APIRequest.sendResponse(exchange, 208, "Already Sent");
                return;
            }
            
            switch (gamePhase) {
                case PROMPT_PHASE:
                    if ( playerPhase == WAIT_PHASE ) { APIRequest.sendResponse( exchange, 200, WAIT_SCREEN); } 
                    else {
                        APIRequest.sendResponse( exchange, 200, RESPONSE_FORM );
                        playerManager.setPlayerPhase( playerId, PROMPT_PHASE );
                    }
                    break;

                case CARD_INTRO_PHASE:
                    APIRequest.sendResponse(exchange, 200, WAIT_SCREEN);
                    playerManager.setPlayerPhase(playerId, CARD_INTRO_PHASE);
                    break;

                case VOTE_PHASE:
                    if ( playerPhase == WAIT_PHASE ) { APIRequest.sendResponse( exchange, 200, WAIT_SCREEN); }
                    else {
                        APIRequest.sendResponse( exchange, 200, CARD_CHOOSER );
                        playerManager.setPlayerPhase( playerId, VOTE_PHASE );
                    }
                    break;

                case VOTE_RESULTS_PHASE:
                    APIRequest.sendResponse(exchange, 200, WAIT_SCREEN);
                    playerManager.setPlayerPhase(playerId, VOTE_RESULTS_PHASE);
                    break;

                case GAME_OVER:
                    APIRequest.sendResponse( exchange, 200, GAME_OVER_SCREEN );
                    playerManager.setPlayerPhase( playerId, GAME_OVER );
                    break;

                default:
                    APIRequest.sendResponse( exchange, 418, "I AM A TEA POT :D" );
                    break;
            }
        } catch (IOException e){
            System.err.println("Failed to send response");
        }
    }
}
