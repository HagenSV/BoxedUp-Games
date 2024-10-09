package explain_yourself.screen;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import explain_yourself.ExplainGame;
import library.webgame.WebGame;

import static explain_yourself.ExplainGameConfigs.*;

public class DebugKeys extends KeyAdapter {

    private WebGame game;

    public DebugKeys(WebGame game){
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            
            case KeyEvent.VK_1:
                game.gameStateManager.setPhase(JOIN_PHASE);
                break;
        
            case KeyEvent.VK_2:
                game.gameStateManager.start();
                break;

            case KeyEvent.VK_3:
                game.gameStateManager.setPhase(CARD_INTRO_PHASE);
                break;

            case KeyEvent.VK_4:
                game.gameStateManager.setPhase(VOTE_PHASE);
                break;

            case KeyEvent.VK_5:
                game.gameStateManager.setPhase(VOTE_RESULTS_PHASE);
                break;

            case KeyEvent.VK_6:
                game.gameStateManager.setPhase(GAME_OVER);
                break;


            case KeyEvent.VK_J:
                game.playerManager.addPlayer("Bot");
                break;

            default:
                break;
        }
    }
}
