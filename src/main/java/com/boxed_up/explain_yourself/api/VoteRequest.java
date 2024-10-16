package com.boxed_up.explain_yourself.api;

import java.io.IOException;

import com.boxed_up.explain_yourself.ExplainGameData;
import com.boxed_up.library.OutputLog;
import com.boxed_up.library.webgame.WebGame;
import com.boxed_up.library.webgame.api.APIRequest;
import com.sun.net.httpserver.HttpExchange;

public class VoteRequest extends APIRequest {

    public static final String PATH = "/select-card";
    private final ExplainGameData gameData;


    public VoteRequest(WebGame game, ExplainGameData gameData) {
        super(PATH, game);
        this.gameData = gameData;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String body = getBody(exchange);
        int playerId = validate(exchange);

        if (playerId == -1){
            send401AccessDenied(exchange);
            return;
        }

        if (body == null){
            send400BadRequest(exchange);
            return;
        }

        String answer = body.split("=")[1];
        if (answer.equals("1")){
            gameData.vote(playerId,gameData.getCardIndex(),0);
        } else if (answer.equals("2")){
            gameData.vote(playerId,gameData.getCardIndex(),1);
        }

        OutputLog.getInstance().log(getInfo(exchange)+" body["+body+",len="+body.length()+"]");
        OutputLog.getInstance().log(
            String.format("Votes: [%d,%d]",
                gameData.getVotes(gameData.getCardIndex(), 0),
                gameData.getVotes(gameData.getCardIndex(), 1)
            )
        );

        sendResponse( exchange, 200, "Success" );

    }
}