package com.boxed_up.library.graphics;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * JLabel with black text by default, rather than the usual off-black default
 */
public class DefaultLabel extends JLabel {
    public DefaultLabel(){
        setForeground(Color.BLACK);
    }

    public DefaultLabel(String text){
        super(text);
        setForeground(Color.BLACK);
    }

    public DefaultLabel(String text, int align){
        super(text,align);
        setForeground(Color.BLACK);
    }
}
