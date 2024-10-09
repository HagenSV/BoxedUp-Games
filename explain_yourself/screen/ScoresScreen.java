package explain_yourself.screen;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import explain_yourself.ExplainGame;
import explain_yourself.ExplainGameData;
import explain_yourself.ExplainGameVM.BasicScreen;

import static explain_yourself.ExplainGameVM.*;
import library.DynamicValue;
import library.graphics.BlankButton;
import library.graphics.Window;
import library.webgame.WebGame;
import main.MenuManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class ScoresScreen extends BasicScreen {

    private static final int REVEAL_SCORE_DELAY = 1500;

    private boolean initialized;

    private String[] players;
    private int[] scores;

    private JButton menuBtn;
    private JButton newGameBtn;

    private DynamicValue displayScores;

    public ScoresScreen(Window w, WebGame game, ExplainGameData gameData) {
        super(w, game, gameData);

        initialized = false;

        menuBtn = new BlankButton("Main Menu");
        menuBtn.setFont(FONT.deriveFont(20f));
        menuBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        menuBtn.setSize(200,50);
        menuBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuBtn.setLocation(getWidth()*3/4-menuBtn.getWidth()/2,getHeight()-menuBtn.getHeight()-10);
        menuBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                window.setScene(MenuManager.getInstance());
            }
            
        });
        add(menuBtn);


        newGameBtn = new BlankButton("New Game");
        newGameBtn.setFont(FONT.deriveFont(20f));
        newGameBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        newGameBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newGameBtn.setSize(200,50);
        newGameBtn.setLocation(getWidth()*1/4-newGameBtn.getWidth()/2,getHeight()-newGameBtn.getHeight()-10);
        newGameBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //new ExplainGame(w);
            }
            
        });
        add(newGameBtn);

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

        menuBtn.setLocation(getWidth()*3/4-menuBtn.getWidth()/2,getHeight()-menuBtn.getHeight()-10);
        newGameBtn.setLocation(getWidth()*1/4-newGameBtn.getWidth()/2,getHeight()-newGameBtn.getHeight()-10);


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
