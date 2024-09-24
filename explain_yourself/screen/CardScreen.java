package explain_yourself.screen;

import static explain_yourself.ExplainGameConfigs.CARD_INTRO_PHASE;
import static explain_yourself.ExplainGameConfigs.VOTE_PHASE;
import static explain_yourself.ExplainGameConfigs.VOTE_RESULTS_PHASE;

import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JTextPane;

import explain_yourself.ExplainGameData;
import explain_yourself.ExplainGameVM;
import static explain_yourself.ExplainGameVM.*;
import library.DynamicValue;
import library.webgame.PlayerManager;

public class CardScreen extends BasicScreen {
    
    private boolean initialized;
    private int introAnimationStep = -1;
    private int resultsAnimationStep = -1;

    private JLabel label1;
    private JLabel label2;

    private JLabel promptLabel;
    private DynamicValue promptOffset;

    private JTextPane card1;
    private DynamicValue card1Offset;

    private JTextPane card2;
    private DynamicValue card2Offset;

    private JLabel votes1Lbl;
    private JLabel votes2Lbl;
    private JLabel winnerLbl;

    private DynamicValue animationTimer;
    private DynamicValue revealCard1;
    private DynamicValue revealCard2;

    public CardScreen(ExplainGameVM explainGameVM) {
        super(explainGameVM);
        
        label1 = new JLabel("Time Remaining: "+game.voteTimer.getTimeRemaining());
        label1.setFont(FONT.deriveFont(35f));
        label1.setSize(500,40);
        label1.setLocation(20,title.getY()+title.getHeight()+5);
        add(label1);

        label2 = new JLabel("Vote for your favorite response",JLabel.CENTER);
        label2.setFont(FONT.deriveFont(35f));
        label2.setSize(700,40);
        label2.setLocation(getWidth()/2-label2.getWidth()/2,getHeight()-label2.getHeight()-50);
        add(label2);

        promptLabel = new JLabel("",JLabel.CENTER);
        promptLabel.setFont(FONT.deriveFont(30f));
        promptLabel.setSize(getWidth(),40);
        promptLabel.setLocation(0, label1.getY()+label1.getHeight()+70);
        add(promptLabel);

        card1 = new Card();
        card1.setLocation(getWidth()/2-card1.getWidth()-50,promptLabel.getY()+promptLabel.getHeight()+50);

        card2 = new Card();
        card2.setLocation(getWidth()/2+50,promptLabel.getY()+promptLabel.getHeight()+50);
        
        add(card2);
        add(card1);

        votes1Lbl = new JLabel("99");
        votes1Lbl.setFont(FONT.deriveFont(30f));
        votes1Lbl.setSize(30,20);
        add(votes1Lbl);

        votes2Lbl = new JLabel("99");
        votes2Lbl.setFont(FONT.deriveFont(30f));
        votes2Lbl.setSize(30,30);
        add(votes2Lbl);

        winnerLbl = new JLabel("Winner!");
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
            introAnimationStep = 0;
            animationTimer.interpolate(0, 1000);
            promptOffset.setValue(promptLabel.getHeight()/2+card1.getHeight()/2+50);
            card1.setVisible(false);
            card2.setVisible(false);
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

        ExplainGameData data = game.gameData;
        int promptId = data.getCardIndex();

        int votes1 = data.getVotes(promptId,0);
        int votes2 = data.getVotes(promptId,1);

        if (resultsAnimationStep == -1){
            resultsAnimationStep++;
            animationTimer.interpolate(0, 1000);
            label1.setVisible(false);
            label2.setVisible(false);

            votes1Lbl.setVisible(true);
            votes2Lbl.setVisible(true);
            
            votes1Lbl.setText(""+votes1);
            votes2Lbl.setText(""+votes2);
        }

        else if (resultsAnimationStep == 0){
            resultsAnimationStep++;
            animationTimer.interpolate(0, 1000);
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

        votes1Lbl.setLocation(card1.getX()-votes1Lbl.getWidth()-20,card1.getY()+card1.getHeight()/2-votes1Lbl.getWidth()/2);
        votes2Lbl.setLocation(card2.getX()+card2.getWidth()+20,card2.getY()+card2.getHeight()/2-votes2Lbl.getWidth()/2);


        int cardIndex = game.gameData.getCardIndex();
        if (cardIndex == -1 || cardIndex >= game.playerManager.getPlayerCount()){ return; }

        ExplainGameData data = game.gameData;
        String[] responses = data.getPromptResponses(cardIndex, "No Response");

        promptLabel.setText( data.getPrompt(cardIndex) );
        card1.setText(responses[0]);
        card2.setText(responses[1]);

        if (game.gameStateManager.getPhase() == CARD_INTRO_PHASE){
            introCards();
        }

        else if (game.gameStateManager.getPhase() == VOTE_PHASE){
            if (introAnimationStep != -1){ resetIntro(); }
            label1.setText("Time Remaining: "+game.voteTimer.getTimeRemaining());
        }

        else if (game.gameStateManager.getPhase() == VOTE_RESULTS_PHASE){
            showResults();
        }
    }
}
