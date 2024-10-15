package com.boxed_up.menu_button;
import javax.swing.JButton;

import com.boxed_up.MenuManager;
import com.boxed_up.library.graphics.BlankButton;

import java.awt.Color;

public class MenuButton extends BlankButton {

    private static int buttonId = 0;

    public MenuButton(String label){
        setText(label);
        setHorizontalAlignment(JButton.LEFT);
        setBounds(30,200+50*buttonId,200,40);
        setForeground(Color.BLACK);
        setFont(MenuManager.FONT.deriveFont(20f));

        buttonId++;
    }
}
