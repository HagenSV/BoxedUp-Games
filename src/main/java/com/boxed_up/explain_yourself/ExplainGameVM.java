package com.boxed_up.explain_yourself;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.boxed_up.explain_yourself.screen.*;
import com.boxed_up.library.graphics.DefaultLabel;
import com.boxed_up.library.graphics.Window;
import com.boxed_up.library.webgame.ServerViewManager;
import com.boxed_up.library.webgame.WebGame;
import static com.boxed_up.explain_yourself.ExplainGameConfigs.*;

public class ExplainGameVM extends ServerViewManager {

    public static final Color BACKGROUND_COLOR = new Color(0xd6f5f5);
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final Font FONT = new Font("Cooper Black",Font.PLAIN,1);

    private final Window window;
    private final JPanel panel;

    private final ExplainGameData gameData;
    
    private BasicScreen currentScreen;
    private BasicScreen menuScreen;
    private BasicScreen scoresScreen;
    private BasicScreen cardScreen;
    private BasicScreen promptScreen;

    public ExplainGameVM(Window w, ExplainGameData gameData){
        this.window = w;
        this.gameData = gameData;

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setFocusable(true);
        window.setScene(panel);
        panel.requestFocus();

    }


    @Override
    public void init(){
        menuScreen = new MenuScreen(window,game,gameData);
        promptScreen = new PromptScreen(window,game,gameData);
        cardScreen = new CardScreen(window,game,gameData);
        scoresScreen = new ScoresScreen(window,game,gameData);

        if (DEBUG_MODE){
            panel.addKeyListener(new DebugKeys(game));
        }

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
        panel.validate();
    }

    public static class BasicScreen extends JPanel {

        public final Window window;
        public final WebGame game;
        public final ExplainGameData gameData;
        public final JLabel title;

        public BasicScreen(Window w, WebGame game, ExplainGameData gameData){
            this.window = w;
            this.game = game;
            this.gameData = gameData;
            setBackground(BACKGROUND_COLOR);
            setLayout(null);
            title = new DefaultLabel("Explain Yourself!",JLabel.LEFT);
            title.setFont(FONT.deriveFont(Font.BOLD).deriveFont(50f));
            title.setBounds(20,20,480,50);
            add(title);
        }
    }
}
