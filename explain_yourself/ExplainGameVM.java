package explain_yourself;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import explain_yourself.screen.CardScreen;
import explain_yourself.screen.DebugKeys;
import explain_yourself.screen.MenuScreen;
import explain_yourself.screen.PromptScreen;
import explain_yourself.screen.ScoresScreen;
import static explain_yourself.ExplainGameConfigs.*;
import library.graphics.Window;
import library.webgame.ServerViewManager;

public class ExplainGameVM extends ServerViewManager {

    public static final Color BACKGROUND_COLOR = new Color(0xd6f5f5);
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final Font FONT = new Font("Cooper Black",Font.PLAIN,1);

    public final Window window;
    private final JPanel panel;
    
    private ExplainGame game;

    private BasicScreen currentScreen;
    private BasicScreen menuScreen;
    private BasicScreen scoresScreen;
    private BasicScreen cardScreen;
    private BasicScreen promptScreen;

    

    public ExplainGameVM(Window w, ExplainGame game){
        this.window = w;

        panel = new JPanel();

        panel.setLayout(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setFocusable(true);
        panel.addKeyListener(new DebugKeys(game));
        window.setScene(panel);
        panel.requestFocus();

        this.game = game;

        menuScreen = new MenuScreen(this);
        promptScreen = new PromptScreen(this);
        cardScreen = new CardScreen(this);
        scoresScreen = new ScoresScreen(this);

        setScreen(menuScreen);
    }

    @Override
    public void renderScreen(int screenId) {
        switch (screenId) {
            case JOIN_PHASE:
                setScreen(menuScreen);
                break;

            case PROMPT_PHASE:
                setScreen(promptScreen);
                break;

            case CARD_INTRO_PHASE:
            case VOTE_PHASE:
            case VOTE_RESULTS_PHASE:
                setScreen(cardScreen);
                break;

            case GAME_OVER:
                setScreen(scoresScreen);
                break;
        
            default:
                break;
        }
    }

    private void setScreen(BasicScreen s){
        if (currentScreen != null){
            panel.remove(currentScreen);
        }
        currentScreen = s;
        panel.add(currentScreen,BorderLayout.CENTER);
    }

    public static class BasicScreen extends JPanel {

        public final ExplainGameVM screenManager;
        public final ExplainGame game;
        public final JLabel title;

        public BasicScreen(ExplainGameVM sm){
            this.screenManager = sm;
            this.game = sm.game;
            setBackground(BACKGROUND_COLOR);
            setLayout(null);
            title = new JLabel("Explain Yourself!",JLabel.LEFT);
            title.setFont(FONT.deriveFont(Font.BOLD).deriveFont(50f));
            title.setBounds(20,20,480,50);
            add(title);
        }
    }
}
