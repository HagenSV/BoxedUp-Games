package library.graphics;

import javax.swing.JButton;

public class BlankButton extends JButton {
    public BlankButton(){
        setFocusPainted(false);
        setBackground(null);
        setBorder(null);
    }

    public BlankButton(String text){
        this();
        setText(text);
    }
}
