package explain_yourself.api;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import explain_yourself.ExplainGameData;
import library.OutputLog;
import library.webgame.WebGame;
import library.webgame.api.APIRequest;

import static explain_yourself.ExplainGameConfigs.*;

public class SubmitRequest extends APIRequest {

    public static final String PATH = "/submit";

    private final ExplainGameData gameData;

    public SubmitRequest(WebGame game, ExplainGameData gameData) {
        super(PATH, game);
        this.gameData = gameData;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String data = getBody(exchange);
        int playerId = validate(exchange);

        if (playerId == -1){
            send401AccessDenied(exchange);
            return;
        }

        if (data == null){
            OutputLog.getInstance().log(getInfo(exchange)+" Error: Missing body");
            send400BadRequest(exchange);
            return;
        }

        OutputLog.getInstance().log(getInfo(exchange)+" body["+data+",len="+data.length()+"]");

        String response = data.substring(9,data.length());

        //Validate input server-side because you should never trust users
        response = response.replaceAll("[^\\w\\d!@#$%^&*()\\-=_+\\[\\]\\{}|\\\\\"':;?/.,<> ]","");

        sendResponse(exchange, (gameData.getResponseCount(playerId) == 0) ? 200 : 208, "Recieved");
        gameData.storeResponse(playerId, gameData.getResponseCount(playerId), response);

        if (gameData.getResponseCount(playerId) == 2){
            game.playerManager.setPlayerPhase(playerId, WAIT_PHASE);

            //Continue to next phase if all other players have submitted both responses
            boolean advancePhase = true;
            for ( int i = 0; i < game.playerManager.getPlayerCount(); i++ ){
                if (gameData.getResponseCount(i) < 2){
                    advancePhase = false;
                    break;
                }
            }
            if (advancePhase){ 
                game.gameStateManager.setPhase(CARD_INTRO_PHASE);
            }
        }
    }
    
}
