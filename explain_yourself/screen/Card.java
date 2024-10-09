package explain_yourself.screen;

import static explain_yourself.ExplainGameVM.*;

import java.awt.Color;

import javax.swing.BorderFactory;

import library.graphics.CenteredTextPane;

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