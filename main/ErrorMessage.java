package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import library.graphics.BlankButton;
import library.graphics.CenteredTextPane;

public class ErrorMessage extends CenteredTextPane {

    private JButton exitBtn;
    
    public ErrorMessage(String message){
        setBackground(new Color(1, 1, 1, 0.5f));
        setForeground(Color.BLACK);

        setFocusable(false);
        setEditable(false);

        setText(message);
        setSize(500, 300);
        setFont(MenuManager.FONT.deriveFont(40f));
        setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

        exitBtn = new BlankButton("X");
        exitBtn.setBounds(getWidth()-21,1,20,20);
        exitBtn.setBackground(new Color(1,0,0,0.5f));
        exitBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Container parent = getParent();
                if (parent == null){ return; }
                parent.remove(ErrorMessage.this);
            }
            
        });
        add(exitBtn);
    }
}
