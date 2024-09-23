package main;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import library.graphics.Window;

public class MenuManager extends JPanel {
    private static MenuManager instance;

    public static final Font FONT = new Font("Georgia",Font.PLAIN,1);
    public static final Color BACKGROUND_COLOR = new Color(0xffecb3);
    
    private int selectedIdx = -1;
    private JButton selectedBtn;
    private JButton buttons[];

    private JLabel title;
    private JLabel title2;

    private Window window;

    private MenuManager(){

        setBackground(BACKGROUND_COLOR);
        setLayout(null);
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_UP){
                    selectedIdx--;
                    selectedIdx = Math.max(selectedIdx,0);
                    selectButton(buttons[selectedIdx]);
                }
                else if (key == KeyEvent.VK_DOWN){
                    selectedIdx++;
                    selectedIdx %= buttons.length;
                    selectButton(buttons[selectedIdx]);
                }
                else if (key == KeyEvent.VK_ENTER){
                    if (selectedBtn != null){
                        for (ActionListener a : selectedBtn.getActionListeners()){
                            a.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,null));
                        }
                    }
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                selectedIdx = -1;
                deselectButton();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                selectedIdx = -1;
                deselectButton();
            }
        });

        window = new Window("BoxedUp Games!");
        window.setScene(this);
        window.setVisible(true);

        buttons = new JButton[7];

        title = new JLabel("BoxedUp");
        title.setFont(FONT.deriveFont(Font.BOLD).deriveFont(45f));
        title.setBounds(0,0,250,50);
        add(title);
        title2 = new JLabel("Games");
        title2.setFont(FONT.deriveFont(30f));
        title2.setBounds(0,0,100,30);
        add(title2);

        JButton explainButton = new MenuButton("Explain Yourself!",0);
        explainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new explain_yourself.screen.ScreenManager(window);
                } catch (IOException e1) {
                    //TODO Show popup: failed to start server
                    e1.printStackTrace();
                }
            }
        });
        add(explainButton);

        JButton gamebtn2 = new MenuButton("Game_2",1);
        add(gamebtn2);

        JButton gamebtn3 = new MenuButton("Game_3",2);
        add(gamebtn3);

        JButton gamebtn4 = new MenuButton("Game_4",3);
        add(gamebtn4);

        JButton gamebtn5 = new MenuButton("Game_5",4);
        add(gamebtn5);

        JButton gamebtn6 = new MenuButton("Game_6",5);
        add(gamebtn6);

        buttons = new JButton[]{ explainButton, gamebtn2, gamebtn3, gamebtn4, gamebtn5, gamebtn6 };

        for (JButton btn : buttons){
            btn.addMouseListener(new HoverListener());
        }

    }

    public static MenuManager getInstance(){
        if (instance == null){
            instance = new MenuManager();
        }
        return instance;
    }

    private void selectButton(JButton btn){
        deselectButton();
        selectedBtn = btn;
        selectedBtn.setFont(FONT.deriveFont((float)selectedBtn.getFont().getSize()+3));
        //Formatting changes
    }

    private void deselectButton(){
        if (selectedBtn == null){
            return;
        }
        //Formatting changes
        selectedBtn.setFont(FONT.deriveFont((float)selectedBtn.getFont().getSize()-3));
        selectedBtn = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        title.setLocation(getWidth()-title.getWidth()-50, 20);
        title2.setLocation(getWidth()-title.getWidth()/2-20, 20+title.getHeight());
    }


    private class HoverListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            Component comp = e.getComponent();
            if (comp instanceof JButton){
                selectButton((JButton)comp);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            deselectButton();
        }
    }
}
