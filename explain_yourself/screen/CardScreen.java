package explain_yourself.screen;

import static explain_yourself.ExplainGameConfigs.CARD_INTRO_PHASE;
import static explain_yourself.ExplainGameConfigs.VOTE_PHASE;
import static explain_yourself.ExplainGameConfigs.VOTE_RESULTS_PHASE;

import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JTextPane;

import explain_yourself.ExplainGameData;
import explain_yourself.ExplainGameVM.BasicScreen;
import library.DynamicValue;
import library.graphics.DefaultLabel;
import library.graphics.Window;
import library.webgame.WebGame;

import static explain_yourself.ExplainGameVM.*;

public class CardScreen extends BasicScreen {
    
    private boolean initialized;
    private int introAnimationStep = -1;
    private int resultsAnimationStep = -1;

    private JLabel label1;
    private JLabel label2;

    private JLabel promptLabel;
    private DynamicValue promptOffset;

    private JLabel name1;
    private JTextPane card1;
    private DynamicValue card1Offset;

    private JLabel name2;
    private JTextPane card2;
    private DynamicValue card2Offset;

    private JLabel votes1Lbl;
    private JLabel votes2Lbl;
    private JLabel winnerLbl;

    private DynamicValue animationTimer;
    private DynamicValue revealCard1;
    private DynamicValue revealCard2;

    public CardScreen(Window w, WebGame game, ExplainGameData gameData) {
        super(w, game, gameData);
        
        label1 = new DefaultLabel("Time Remaining: "+gameData.voteTimer.getTimeRemaining());
        label1.setFont(FONT.deriveFont(35f));
        label1.setSize(500,40);
        label1.setLocation(20,title.getY()+title.getHeight()+5);
        add(label1);

        label2 = new JLabel("Vote for your favorite response",JLabel.CENTER);
        label2.setFont(FONT.deriveFont(35f));
        label2.setSize(700,40);
        label2.setLocation(getWidth()/2-label2.getWidth()/2,getHeight()-label2.getHeight()-50);
        add(label2);

        promptLabel = new DefaultLabel("",JLabel.CENTER);
        promptLabel.setFont(FONT.deriveFont(30f));
        promptLabel.setSize(getWidth(),40);
        promptLabel.setLocation(0, label1.getY()+label1.getHeight()+70);
        add(promptLabel);

        card1 = new Card();
        card1.setLocation(getWidth()/2-card1.getWidth()-50,promptLabel.getY()+promptLabel.getHeight()+50);
        add(card1);

        name1 = new DefaultLabel("",JLabel.CENTER);
        name1.setFont(FONT.deriveFont(30f));
        name1.setSize(400,30);
        name1.setLocation(card1.getX(),card1.getY()-name1.getHeight());
        add(name1);

        card2 = new Card();
        card2.setLocation(getWidth()/2+50,promptLabel.getY()+promptLabel.getHeight()+50);
        add(card2);

        name2 = new DefaultLabel("",JLabel.CENTER);
        name2.setFont(FONT.deriveFont(30f));
        name2.setSize(400,30);
        name2.setLocation(card2.getX(),card2.getY()-name2.getHeight());
        add(name2);

        votes1Lbl = new DefaultLabel("99");
        votes1Lbl.setFont(FONT.deriveFont(30f));
        votes1Lbl.setSize(50,30);
        add(votes1Lbl);

        votes2Lbl = new DefaultLabel("99");
        votes2Lbl.setFont(FONT.deriveFont(30f));
        votes2Lbl.setSize(50,30);
        add(votes2Lbl);

        winnerLbl = new DefaultLabel("Winner!");
        winnerLbl.setFont(FONT.deriveFont(20f));
        winnerLbl.setSize(100,30);
        add(winnerLbl);


        animationTimer = new DynamicValue(0);
        revealCard1 = new DynamicValue(0);
        revealCard2 = new DynamicValue(0);
        promptOffset = new DynamicValue(0);
        card1Offset = new DynamicValue(-card1.getWidth()-20);
        card2Offset = new DynamicValue(20);

        resetIntro();

        initialized = true;
    }

    public void introCards(){
        if (animationTimer.isInterpolating()){ return; }

        if (introAnimationStep == -1){

            int promptId = gameData.getCardIndex();
            if (promptId == -1 || promptId >= game.playerManager.getPlayerCount()){ return; }

            String[] responses = gameData.getPromptResponses(promptId, "No Response");
            int[] responders = gameData.getPromptResponders(promptId);

            promptLabel.setText( gameData.getPrompt(promptId) );
            card1.setText(responses[0]);
            name1.setText(game.playerManager.getPlayers().get(responders[0]));
            card2.setText(responses[1]);
            name2.setText(game.playerManager.getPlayers().get(responders[1]));

            introAnimationStep = 0;
            animationTimer.interpolate(0, 1000);
            promptOffset.setValue(promptLabel.getHeight()/2+card1.getHeight()/2+50);
            card1.setVisible(false);
            card2.setVisible(false);
            name1.setVisible(false);
            name2.setVisible(false);
            label1.setVisible(false);
            label2.setVisible(false);
            votes1Lbl.setVisible(false);
            votes2Lbl.setVisible(false);
            winnerLbl.setVisible(false);
        }

        else if (introAnimationStep == 0){
            introAnimationStep++;
            animationTimer.interpolate(0, 1000);
            promptOffset.interpolate(0, 1000);
        }

        else if (introAnimationStep == 1){
            introAnimationStep++;
            animationTimer.interpolate(0,1000);
            card1.setVisible(true);
        }

        else if (introAnimationStep == 2){
            introAnimationStep++;
            animationTimer.interpolate(0, 1000);
        }

        else if (introAnimationStep == 3){
            introAnimationStep++;
            animationTimer.interpolate(0, 1000);
            card2.setVisible(true);
        }

        else if (introAnimationStep == 4){
            introAnimationStep++;
            animationTimer.interpolate(0, 1000);
            label2.setVisible(true);
        }

        else if (introAnimationStep == 5){
            introAnimationStep++;
            animationTimer.interpolate(0, 1000);
        }

        else if (introAnimationStep == 6){
            label1.setVisible(true);
            resetIntro();
            game.gameStateManager.setPhase(VOTE_PHASE);
        }
        
    }

    public void resetIntro(){
        introAnimationStep = -1;
        animationTimer.setValue(1);
        revealCard1.setValue(1);
        revealCard2.setValue(1);
    }

    public void showResults(){
        if (animationTimer.isInterpolating()){ return; }

        int promptId = gameData.getCardIndex();

        int votes1 = gameData.getVotes(promptId,0);
        int votes2 = gameData.getVotes(promptId,1);

        if (resultsAnimationStep == -1){
            resultsAnimationStep++;
            animationTimer.interpolate(0, 1000);

            name1.setVisible(true);
            name2.setVisible(true);

            label1.setVisible(false);
            label2.setVisible(false);

            votes1Lbl.setVisible(true);
            votes2Lbl.setVisible(true);
            
            votes1Lbl.setText(""+votes1);
            votes2Lbl.setText(""+votes2);
        }

        else if (resultsAnimationStep == 0){
            resultsAnimationStep++;
            animationTimer.interpolate(0, 2000);
            winnerLbl.setVisible(votes1 != votes2);
            winnerLbl.setLocation(
                votes1 > votes2 ? card1.getX()-winnerLbl.getWidth() : card2.getX()+card2.getWidth(),
                votes1Lbl.getY()+votes1Lbl.getHeight()
            );
        }
        else if (resultsAnimationStep == 1){
            resultsAnimationStep = -1;
            game.gameStateManager.setPhase(CARD_INTRO_PHASE);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!initialized){ return; }

        promptLabel.setBounds(
            0,
            label1.getY()+label1.getHeight()+70+promptOffset.getValue(),
            getWidth(),
            promptLabel.getHeight()
        );

        label2.setLocation(getWidth()/2-label2.getWidth()/2,getHeight()-label2.getHeight()-50);
        card1.setLocation(getWidth()/2+card1Offset.getValue(),promptLabel.getY()+promptLabel.getHeight()+50);
        card2.setLocation(getWidth()/2+card2Offset.getValue(),promptLabel.getY()+promptLabel.getHeight()+50);
        name1.setLocation(card1.getX(),card1.getY()-name1.getHeight());
        name2.setLocation(card2.getX(),card2.getY()-name2.getHeight());

        votes1Lbl.setLocation(card1.getX()-votes1Lbl.getWidth()-20,card1.getY()+card1.getHeight()/2-votes1Lbl.getWidth()/2);
        votes2Lbl.setLocation(card2.getX()+card2.getWidth()+20,card2.getY()+card2.getHeight()/2-votes2Lbl.getWidth()/2);

        if (game.gameStateManager.getPhase() == CARD_INTRO_PHASE){
            introCards();
        }

        else if (game.gameStateManager.getPhase() == VOTE_PHASE){
            if (introAnimationStep != -1){ resetIntro(); }
            label1.setText("Time Remaining: "+gameData.voteTimer.getTimeRemaining());
        }

        else if (game.gameStateManager.getPhase() == VOTE_RESULTS_PHASE){
            showResults();
        }
    }
}
