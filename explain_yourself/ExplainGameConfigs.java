package explain_yourself;

import java.io.File;
import java.net.URISyntaxException;

import library.OutputLog;

public class ExplainGameConfigs {
    
    public static final boolean DEBUG_MODE = false;

    public static final int PROMPT_TIME =   ( int )( 3*60 );
    public static final int VOTE_TIME =     ( int )( 30 );

    public static final int MIN_PLAYERS = 5;
    public static final int MAX_PLAYERS = 12;
    
    public static final File DIRECTORY = getFile("assets\\common\\explain_yourself");

    public static final File GAME_PAGE = new File(DIRECTORY,"client\\join_game.html");
    public static final File GAME_CLOSED = new File(DIRECTORY,"client\\game_closed.html");
    public static final File WAIT_SCREEN = new File(DIRECTORY,"client\\wait_screen.html");
    public static final File RESPONSE_FORM = new File(DIRECTORY, "client\\form.html");
    public static final File CARD_CHOOSER = new File(DIRECTORY,"client\\cards.html");
    public static final File GAME_OVER_SCREEN = new File(DIRECTORY,"client\\game_over.html");

    public static final int JOIN_PHASE = 0;
    public static final int PROMPT_PHASE = 1;
    public static final int WAIT_PHASE = 2;
    public static final int CARD_INTRO_PHASE = 3;
    public static final int VOTE_PHASE = 4;
    public static final int VOTE_RESULTS_PHASE = 5;
    public static final int GAME_OVER = 6;

    private static File getFile(String path){
        try {
            File dir = new File(Thread.currentThread().getContextClassLoader().getResource(path).toURI());
            return dir;
        } catch (URISyntaxException e){
            OutputLog.getInstance().log(e.getMessage());
            return null;
        }
    }
}
