package explain_yourself.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.*;

import explain_yourself.*;
import library.graphics.*;
import static explain_yourself.ExplainGameConfigs.*;

public class ScreenManager extends JPanel {

    public static final Color BACKGROUND_COLOR = new Color(0xd6f5f5);
    public static final Color TEXT_COLOR = Color.BLACK;
    public static final Font FONT = new Font("Cooper Black",Font.PLAIN,1);

    public Window window;

    private boolean initialized;
    
    private ExplainGame game;
    private int lastPhase;

    private BasicScreen currentScreen;
    private BasicScreen menuScreen;
    private BasicScreen scoresScreen;
    private BasicScreen cardScreen;
    private BasicScreen promptScreen;

    public ScreenManager(Window w) throws IOException {
        this.window = w;
        window.setScene(this);

        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setFocusable(true);

        game = new ExplainGame();
        lastPhase = game.gameStateManager.getPhase();

        addKeyListener(new DebugKeys(game));

        menuScreen = new MenuScreen(this);
        promptScreen = new PromptScreen(this);
        cardScreen = new CardScreen(this);
        scoresScreen = new ScoresScreen(this);

        setScreen(menuScreen);

        initialized = true;
    }

    public Window getWindow(){
        return window;
    }

    public ExplainGame getGame(){
        return game;
    }

    private void setScreen(BasicScreen s){
        if (currentScreen != null){
            remove(currentScreen);
        }
        currentScreen = s;
        currentScreen.setSize(getSize());
        add(currentScreen,BorderLayout.CENTER);
    }

    protected void update() {

        if (game.gameStateManager.getPhase() != lastPhase){
            lastPhase = game.gameStateManager.getPhase();
            switch (game.gameStateManager.getPhase()) {
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

        if (!initialized){
            requestFocus();
            return;
        }
    }

    public static class BasicScreen extends JPanel {

        ScreenManager screenManager;
        ExplainGame game;
        JLabel title;

        public BasicScreen(ScreenManager sm){
            this.screenManager = sm;
            this.game = sm.getGame();
            setBackground(BACKGROUND_COLOR);
            setLayout(null);
            title = new JLabel("Explain Yourself!",JLabel.LEFT);
            title.setFont(FONT.deriveFont(Font.BOLD).deriveFont(50f));
            title.setBounds(20,20,480,50);
            add(title);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            screenManager.update();
        }

    }
}