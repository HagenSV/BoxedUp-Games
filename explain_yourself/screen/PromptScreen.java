package explain_yourself.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JLabel;

import explain_yourself.ExplainGameVM;
import static explain_yourself.ExplainGameVM.*;

public class PromptScreen extends BasicScreen {
    JLabel label1;
    JLabel label2;

    private boolean initialized;

    public PromptScreen(ExplainGameVM explainGameVM){
        super(explainGameVM);

        label1 = new JLabel("Time Remaining: "+game.gameData.promptTimer.getTimeRemaining());
        label1.setFont(FONT.deriveFont(35f));
        label1.setSize(400,40);
        label1.setLocation(20,25+title.getHeight());
        add(label1);

        label2 = new JLabel("Answer the prompts on your device");
        label2.setFont(FONT.deriveFont(35f));
        label2.setSize(700,40);
        label2.setLocation(20,25+title.getHeight()+label1.getHeight());
        add(label2);

        initialized = false;
    }

    public void init(){
        //label1.getOffsetX().interpolate(20+label1.getWidth()/2,1500);
        //label2.getOffsetX().interpolate(20+label2.getWidth()/2,1500);
        initialized = true;
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!initialized){
            init();
        }

        label1.setText("Time Remaining: "+game.gameData.promptTimer.getTimeRemaining());

        g.setFont(FONT.deriveFont(35f));
        g.drawString("Submissions:",20,300);
        g.setFont(FONT.deriveFont(20f));
        List<String> players = game.playerManager.getPlayers();
        for (int i = 0; i < game.playerManager.getPlayerCount(); i++){
            String pName = players.get(i);
            int submissions = game.gameData.getResponseCount(i);
            //Gray out the player's name if they submitted both responses
            g.setColor(submissions == 2 ? Color.GRAY : Color.BLACK);
            g.drawString(pName+"  "+submissions+"/2", 40,330+25*i);
        }
    }
}
