package explain_yourself;

import java.net.URL;

import library.FileSystem;

public class ExplainGameConfigs {
    
    public static final boolean DEBUG_MODE = false;

    public static final int PROMPT_TIME =   ( int )( 3*60 );
    public static final int VOTE_TIME =     ( int )( 30 );

    public static final int MIN_PLAYERS = 5;
    public static final int MAX_PLAYERS = 12;

    public static final String DIR_STRING = "assets/explain_yourself";
    public static final String CLIENT_DIR_STRING = DIR_STRING + "/client";
    
    public static final URL DIRECTORY = FileSystem.getFile(DIR_STRING);
    public static final URL CLIENT_DIRECTORY = FileSystem.getFile(CLIENT_DIR_STRING);

    public static final URL GAME_PAGE = FileSystem.getFile(CLIENT_DIR_STRING,"join_game.html");
    public static final URL GAME_CLOSED = FileSystem.getFile(CLIENT_DIR_STRING,"game_closed.html");
    public static final URL WAIT_SCREEN = FileSystem.getFile(CLIENT_DIR_STRING,"wait_screen.html");
    public static final URL RESPONSE_FORM = FileSystem.getFile(CLIENT_DIR_STRING,"form.html");
    public static final URL CARD_CHOOSER = FileSystem.getFile(CLIENT_DIR_STRING,"cards.html");
    public static final URL GAME_OVER_SCREEN = FileSystem.getFile(CLIENT_DIR_STRING,"game_over.html");

    public static final int JOIN_PHASE = 0;
    public static final int PROMPT_PHASE = 1;
    public static final int WAIT_PHASE = 2;
    public static final int CARD_INTRO_PHASE = 3;
    public static final int VOTE_PHASE = 4;
    public static final int VOTE_RESULTS_PHASE = 5;
    public static final int GAME_OVER = 6;

}
