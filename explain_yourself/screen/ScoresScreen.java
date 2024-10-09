package explain_yourself.screen;

import java.awt.Color;
import java.awt.Graphics;

import explain_yourself.ExplainGameData;
import explain_yourself.ExplainGameVM.BasicScreen;

import static explain_yourself.ExplainGameVM.*;
import library.DynamicValue;
import library.graphics.Window;
import library.webgame.WebGame;

public class ScoresScreen extends BasicScreen {

    private static final int REVEAL_SCORE_DELAY = 1500;

    private boolean initialized;

    private String[] players;
    private int[] scores;

    private DynamicValue displayScores;

    public ScoresScreen(Window w, WebGame game, ExplainGameData gameData) {
        super(w, game, gameData);

        initialized = false;
    }


    private void init(){
        initialized = true;

        //Get scores
        players = new String[game.playerManager.getPlayerCount()];
        game.playerManager.getPlayers().toArray(players);
        scores = gameData.calcScores();

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

        displayScores = new DynamicValue(game.playerManager.getPlayerCount());
        displayScores.interpolate(0, game.playerManager.getPlayerCount()*REVEAL_SCORE_DELAY);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if (!initialized){
            init();
        }

        g.setFont(FONT.deriveFont(35f));
        g.setColor(Color.black);
        g.drawString("Scores:",20,300);
        g.setFont(FONT.deriveFont(20f));
        //Display scores
        for (int i = game.playerManager.getPlayerCount()-1; i >= displayScores.getValue(); i--){
            String pName = players[i];
            g.drawString(pName+": "+scores[i], 40,330+25*i);
        }
    }
}
