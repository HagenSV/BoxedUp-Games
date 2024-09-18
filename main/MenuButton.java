package main;
import javax.swing.JButton;

import library.graphics.BlankButton;

import java.awt.Color;

public class MenuButton extends BlankButton {
    public MenuButton(String label,int num){
        setText(label);
        setHorizontalAlignment(JButton.LEFT);
        setBounds(30,200+50*num,200,40);
        setForeground(Color.BLACK);
        setFont(MenuManager.FONT.deriveFont(20f));
    }
}
