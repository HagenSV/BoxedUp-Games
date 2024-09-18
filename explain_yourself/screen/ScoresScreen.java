package explain_yourself.screen;

import static explain_yourself.screen.ScreenManager.*;

import java.awt.Graphics;

import explain_yourself.screen.ScreenManager.BasicScreen;
import library.DynamicValue;

public class ScoresScreen extends BasicScreen {

    private static final int REVEAL_SCORE_DELAY = 1500;

    private boolean initialized;

    private String[] players;
    private int[] scores;

    private DynamicValue displayScores;

    public ScoresScreen(ScreenManager sm){
        super(sm);

        initialized = false;
    }


    private void init(){
        initialized = true;

        //Get scores
        players = new String[game.getPlayerCount()];
        game.getPlayers().toArray(players);
        scores = game.getPlayerManager().calcScores();

        //Sort scores highest to lowest
        //this algorithm is O(n^2) but that doesnt matter
        //list size will always be < 20 elements
        for (int i = 0; i < scores.length; i++){

            //Find max
            int max = scores[i];
            int maxIdx = i;
            for (int j = i+1; j < scores.length; j++){
                if (scores[j] > max){
                    max = scores[j];
                    maxIdx = j;
                }
            }

            //Swap
            int scoreTmp = scores[i];
            String nameTmp = players[i];

            scores[i] = scores[maxIdx];
            players[i] = players[maxIdx];

            scores[maxIdx] = scoreTmp;
            players[maxIdx] = nameTmp;
        }

        displayScores = new DynamicValue(game.getPlayerCount());
        displayScores.interpolate(0, game.getPlayerCount()*REVEAL_SCORE_DELAY);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if (!initialized){
            init();
        }

        g.setFont(FONT.deriveFont(35f));
        g.drawString("Scores:",20,300);
        g.setFont(FONT.deriveFont(20f));
        //Display scores
        for (int i = game.getPlayerCount()-1; i >= displayScores.getValue(); i--){
            String pName = players[i];
            g.drawString(pName+": "+scores[i], 40,330+25*i);
        }
    }
}
