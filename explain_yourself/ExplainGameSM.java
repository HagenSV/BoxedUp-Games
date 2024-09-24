package explain_yourself;

import static explain_yourself.ExplainGameConfigs.*;

import library.Timer;
import library.webgame.GameStateManager;

public class ExplainGameSM extends GameStateManager {

    public Timer voteTimer;
    public Timer promptTimer;

    public ExplainGameSM(){
        voteTimer = new Timer(){
            @Override
            public void onExpire() {
                if (getPhase() == VOTE_PHASE){
                    setPhase(VOTE_RESULTS_PHASE);
                }
            }
        };

        promptTimer = new Timer(){
            @Override
            public void onExpire() {
                if (getPhase() == PROMPT_PHASE){
                    setPhase(CARD_INTRO_PHASE);
                }
            }
        };
    }

    @Override
    public void setPhase(int phase) {
        super.setPhase(phase);
        game.playerManager.setAllPlayerPhases(0);

        if (phase == PROMPT_PHASE){
            promptTimer.setTime(PROMPT_TIME);
        }
        else if (phase == VOTE_PHASE){
            voteTimer.setTime(VOTE_TIME);
        }
    }

    @Override
    public boolean canJoin() {
        return getPhase() == JOIN_PHASE;
    }

}
