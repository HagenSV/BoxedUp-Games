package com.boxed_up.menu_button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import com.boxed_up.explain_yourself.ExplainGame;
import com.boxed_up.MenuManager;
import com.boxed_up.PopUpMessage;
import com.boxed_up.library.OutputLog;
import com.boxed_up.library.graphics.Window;

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
                MenuManager panel = MenuManager.getInstance();

                window.setScene(panel);

                PopUpMessage errorMsg = new PopUpMessage(e1.getMessage());
                errorMsg.setLocation((panel.getWidth()-errorMsg.getWidth())/2, (panel.getHeight()-errorMsg.getHeight())/2);
                panel.add(errorMsg);

                OutputLog.getInstance().log(e1.getMessage());
            }
        }
    }
}
