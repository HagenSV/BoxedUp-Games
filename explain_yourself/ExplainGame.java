package explain_yourself;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static explain_yourself.ExplainGameConfigs.*;

import com.sun.net.httpserver.HttpExchange;

import library.Timer;
import library.webgame.GameStateManager;
import library.webgame.PlayerManager;
import library.webgame.PlayerViewManager;
import library.webgame.ServerViewManager;
import library.webgame.WebGame;
import library.webgame.api.APIRequest;

public class ExplainGame extends WebGame {

    public Timer voteTimer;
    public Timer promptTimer;

    private int cardIndex = -1;

    public ExplainGame() throws IOException {
        super(
            new PlayerManager(MIN_PLAYERS, MAX_PLAYERS),
            new GameStateManager(),
            new explain_yourself.PlayerViewManager(),
            new ServerViewManager() {
                
            };
        );

        voteTimer = new Timer(){
            @Override
            public void onExpire() {
                if (gameStateManager.getPhase() == VOTE_PHASE){
                    setPhase(VOTE_RESULTS_PHASE);
                }
            }
        };

        promptTimer = new Timer(){
            @Override
            public void onExpire() {
                if (gameStateManager.getPhase() == PROMPT_PHASE){
                    setPhase(CARD_INTRO_PHASE);
                }
            }
        };
    }
    
    public int getCardIndex(){
        return cardIndex;
    }

    public void setPhase(int phase){
        gamePhase = phase;

        if (gamePhase == CARD_INTRO_PHASE){
            cardIndex++;
            if (cardIndex == playerManager.getPlayerCount()){
                endGame();
            }
        }

        if (gamePhase == VOTE_PHASE){
            playerManager.setAllPlayerPhases(JOIN_PHASE);
            playerManager.clearAllVotes();
            voteTimer.setTime(VOTE_TIME);
        }

        if (gamePhase == PROMPT_PHASE){
            promptTimer.setTime(PROMPT_TIME);
        }
    }

    @Override
    public void start(){
        cardIndex = -1;
        setPhase(PROMPT_PHASE);
    }

    @Override
    public void endGame(){
        setPhase(GAME_OVER);
        super.endGame();
    }

    private void sendPrompt( HttpExchange exchange, String pName ) throws IOException {
        int playerId = getPlayers().indexOf( pName ); 
        sendString( exchange, 200, playerManager.getPrompt( playerId, playerManager.getResponseCount(playerId) ) );
    }

    private void saveResponse( HttpExchange exchange, int playerId, String data ) throws IOException {
        String response = data.split("=")[1];

        for (char c : response.toCharArray()){
            System.out.println(c+" "+(int)c);
        }

        //Replace BROKEN IOS CHARACTERS, I HATE YOU APPLE
        response = response.replace((char)8217,'\'');
        response = response.replace((char)8216,'\'');

        sendString(exchange, (playerManager.getResponseCount(playerId) == 0) ? 200 : 208, "Recieved");
        playerManager.storeResponse(playerId, playerManager.getResponseCount(playerId), response);

        if (playerManager.getResponseCount(playerId) == 2){
            playerManager.setPhase(playerId, WAIT_PHASE);
        }

        //Continue to next phase if all players have submitted their responses
        boolean advancePhase = true;
        for ( int i = 0; i < getPlayerCount(); i++ ){
            if (playerManager.getResponseCount(i) < 2){
                advancePhase = false;
                break;
            }
        }
        if (advancePhase){ 
            setPhase(CARD_INTRO_PHASE);
        }
    }

    private void post( HttpExchange exchange ) throws IOException {
        
        //Get request path
        String path = exchange.getRequestURI().toString();

        //Get request body
        InputStream in = exchange.getRequestBody();
        byte[] inBytes = new byte[ in.available() ];
        in.read( inBytes );
        String body = new String( inBytes );

        //Get cookies sent with the request
        String cookies = exchange.getRequestHeaders().getFirst("Cookie");
        cookies = cookies == null ? "" : cookies;

        //Matchers
        Matcher nameCookie = namePattern.matcher( cookies );
        Matcher sessionCookie = sessionPattern.matcher( cookies );

        //Player info
        String sessionId = sessionCookie.find() ? sessionCookie.group(1) : "";
        String playerName = nameCookie.find() ? nameCookie.group(1) : "";
        int playerId = getPlayers().indexOf(playerName);


        //Default path
        if ( path.matches( "/" ) ) {

            System.out.println(body);

            Matcher nameBody = namePattern.matcher( body );
            if ( !nameBody.find() ) {
                sendString( exchange, 400, "Bad Request" );
                return;
            }

            //Only add player to game if their session id does not exist and does not equal the current session id
            if ( !sessionId.equals( GAME_ID ) && gamePhase == JOIN_PHASE ) {
                playerName = addPlayer( nameBody.group( 1 ) );
                exchange.getResponseHeaders().add("Set-Cookie", "name="+playerName);
                exchange.getResponseHeaders().add("Set-Cookie", "session="+GAME_ID+"; HttpOnly");
            } else { 
                playerManager.setPhase(playerId, JOIN_PHASE);
            }

            sendFile(exchange, 200, GAME_PAGE);
        
        } 
        
        //All following paths require the user to be authenticated
        else if ( !authenticate(playerId, sessionId) ) { sendString( exchange, 401, "Access Denied"); }
        else if ( path.matches( "/game-status/?" ) ) { sendPage( exchange, playerName ); } 
        else if ( path.matches( "/prompt/?" ) ) { sendPrompt( exchange, playerName ); }
        else if ( path.matches( "/submit/?" ) ){ saveResponse( exchange, playerId, body ); }
        
        else if ( path.matches( "/select-card/?" ) ) {

            String answer = body.split("=")[1];
            if (answer.equals("1")){
                playerManager.vote(playerId,cardIndex,0);
            } else if (answer.equals("2")){
                playerManager.vote(playerId,cardIndex,1);
            }

            sendString( exchange, 200, "Success" );

        }
        
        else if ( path.matches( "/card-data/?" ) ) {

            if ( gamePhase != VOTE_PHASE ) {
                sendString( exchange, 401, "Access Denied" );
                return;
            }
                
            String prompt = playerManager.getPrompt( cardIndex );
            String[] responses = playerManager.getPromptResponses( cardIndex, "No Response" );

            String data = String.format( "%s\n%s\n%s", prompt, responses[0], responses[1] );

            System.out.println(data);

            sendString( exchange, 200, data );

        }

        //Unimplemented route
        else { sendString( exchange, 404, "Not found" ); }
    }

    private boolean authenticate(int playerId, String gameId) { return playerId != -1 && gameId.equals( GAME_ID ); }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if ( method.equals( "GET" ) ){ get( exchange ); }
        else if ( method.equals( "POST" ) ){ post( exchange ); }
        else { sendString( exchange, 501, "Error 501 - Not Implemented" ); }
    }
    
}