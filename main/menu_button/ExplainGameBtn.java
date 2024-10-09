package main.menu_button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import explain_yourself.ExplainGame;
import library.graphics.Window;
import main.MenuManager;
import main.PopUpMessage;

public class ExplainGameBtn extends MenuButton {

    private final Window window;

    public ExplainGameBtn(Window w) {
        super("Explain Yourself");
        this.window = w;
        addActionListener(new ClickListener());
    }


    class ClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new ExplainGame(window);
            } catch (IOException e1) {
                window.setScene(MenuManager.getInstance());

                PopUpMessage errorMsg = new PopUpMessage(e1.getMessage());
                errorMsg.setLocation((getWidth()-errorMsg.getWidth())/2, (getHeight()-errorMsg.getHeight())/2);
                add(errorMsg);


                e1.printStackTrace();
            }
        }
    }
}
