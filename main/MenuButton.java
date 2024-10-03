package main;
import javax.swing.JButton;

import library.graphics.BlankButton;

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
