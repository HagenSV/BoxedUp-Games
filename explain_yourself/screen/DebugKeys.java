package explain_yourself.screen;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import explain_yourself.ExplainGame;

import static explain_yourself.GameConfigs.*;

public class DebugKeys extends KeyAdapter {

    private ExplainGame game;

    public DebugKeys(ExplainGame game){
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            
            case KeyEvent.VK_1:
                game.setPhase(JOIN_PHASE);
                break;
        
            case KeyEvent.VK_2:
                game.start();
                break;

            case KeyEvent.VK_3:
                game.setPhase(CARD_INTRO_PHASE);
                break;

            case KeyEvent.VK_4:
                game.setPhase(VOTE_PHASE);
                break;

            case KeyEvent.VK_5:
                game.setPhase(VOTE_RESULTS_PHASE);
                break;

            case KeyEvent.VK_6:
                game.setPhase(GAME_OVER);
                break;


            case KeyEvent.VK_J:
                game.addPlayer("Bot");
                break;

            default:
                break;
        }
    }
}
