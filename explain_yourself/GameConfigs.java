package explain_yourself;

import java.io.File;

public class GameConfigs {
    public static final boolean DEBUG_MODE = true;

    public static final int PROMPT_TIME =   ( int )( 3*60 );
    public static final int VOTE_TIME =     ( int )( 10 );

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 12;
    
    public static final File DIRECTORY = new File("game_files/explain_yourself/client");
    public static final File JOIN_GAME_FORM = new File(DIRECTORY,"index.html");
    public static final File GAME_PAGE = new File(DIRECTORY,"pages/join_game.html");
    public static final File GAME_CLOSED = new File(DIRECTORY,"pages/game_closed.html");
    public static final File WAIT_SCREEN = new File(DIRECTORY,"pages/wait_screen.html");
    public static final File RESPONSE_FORM = new File(DIRECTORY, "pages/form.html");
    public static final File CARD_CHOOSER = new File(DIRECTORY,"pages/cards.html");
    public static final File GAME_OVER_SCREEN = new File(DIRECTORY,"pages/game_over.html");

    public static final int JOIN_PHASE = 0;
    public static final int PROMPT_PHASE = 1;
    public static final int WAIT_PHASE = 2;
    public static final int CARD_INTRO_PHASE = 3;
    public static final int VOTE_PHASE = 4;
    public static final int VOTE_RESULTS_PHASE = 5;
    public static final int GAME_OVER = 6;
}
