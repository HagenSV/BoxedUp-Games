package com.boxed_up.explain_yourself.screen;

import com.boxed_up.library.graphics.CenteredTextPane;

import java.awt.Color;

import javax.swing.BorderFactory;

import static com.boxed_up.explain_yourself.ExplainGameVM.BACKGROUND_COLOR;

public class Card extends CenteredTextPane {
    public Card() {

        setEditable(false);
        setFocusable(false);
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setFont(getFont().deriveFont(18f));
        setSize(400,225);
    }
}