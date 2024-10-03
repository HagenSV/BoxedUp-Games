package explain_yourself.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import explain_yourself.ExplainGameVM;
import static explain_yourself.ExplainGameVM.*;
import library.DynamicValue;
import library.Server;
import library.graphics.BlankButton;
import library.graphics.DefaultLabel;
import main.MenuManager;

public class MenuScreen extends BasicScreen {
    private JLabel label1;
    private DynamicValue label1Offset;
    private JLabel label2;
    private DynamicValue label2Offset;
    private JLabel qrCode;
    private DynamicValue qrCodeOffset;

    private JButton startButton;
    private DynamicValue buttonOffset;

    private JButton exitButton;

    private boolean initialized;
    private boolean transition;

    public MenuScreen(ExplainGameVM explainGameVM){
        super(explainGameVM);
        this.transition = false;

        label1Offset = new DynamicValue(0);
        label2Offset = new DynamicValue(0);
        qrCodeOffset = new DynamicValue(0);
        buttonOffset = new DynamicValue(0);

        String ipAddress = "XXX.XXX.XXX.XXX";
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e){}

        label1 = new DefaultLabel("Go to "+ipAddress);
        label1.setFont(FONT.deriveFont(35f));
        label1.setSize(400,40);
        label1.setLocation(20,5+title.getY()+title.getHeight());
        add(label1);

        label2 = new DefaultLabel("or scan code to join");
        label2.setFont(FONT.deriveFont(35f));
        label2.setSize(400,40);
        label2.setLocation(20,label1.getY()+label1.getHeight());
        add(label2);
        
        try {
            Server.generateQR("margin=0&size=300&light=d6f5f5");
            qrCode = new JLabel(new ImageIcon("qrcode.png"));
            qrCode.setSize(300, 300);
            qrCode.setLocation(getWidth()-qrCode.getWidth()-30,30);
            add(qrCode);
        } catch (Exception e){
            qrCode = new DefaultLabel("Failed to generate QR Code");
            qrCode.setFont(FONT.deriveFont(30f));
            qrCode.setSize(300,30);
            qrCode.setLocation(getWidth()-qrCode.getWidth()-30,30);
            add(qrCode);
        }

        //Button initialization
        startButton = new JButton("Start");
        startButton.setFont(FONT.deriveFont(18f));
        startButton.setSize(200, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.playerManager.hasMin()){
                    transition();
                } else {
                    //TODO make label
                    System.out.println("Not enough players");
                }
            }
        });
        add(startButton);

        exitButton = new BlankButton("Back to Menu");
        exitButton.setFont(FONT.deriveFont(18f));
        exitButton.setSize(150,20);
        exitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                game.game.endGame();
                screenManager.window.setScene(MenuManager.getInstance());
            }
        });
        add(exitButton);

        resetPositions();
        initialized = true;
    }

    public void resetPositions(){
        transition = false;
        label1Offset.setValue(20);
        label2Offset.setValue(20);
        qrCodeOffset.setValue(-qrCode.getWidth()-30);
        buttonOffset.setValue(-startButton.getHeight()-30);
    }

    public void transition(){
        transition = true;
        label1Offset.interpolate(-label1.getWidth(), 2000);
        label2Offset.interpolate(-label2.getWidth(), 2000);
        qrCodeOffset.interpolate(0, 2000);
        buttonOffset.interpolate(0,2000);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (!initialized){ return; }

        if (transition && !label1Offset.isInterpolating() ) {
            game.gameStateManager.start();
            resetPositions();
        }

        g.setColor(Color.black);

        g.setFont(FONT.deriveFont(35f));
        g.drawString("Players:",20,300);
        g.setFont(FONT.deriveFont(20f));
        List<String> players = game.playerManager.getPlayers();
        for (int i = 0; i < players.size(); i++){
            String pName = players.get(i);
            g.drawString(pName, 40,330+25*i);
        }

        label1.setLocation(label1Offset.getValue(),label1.getY());
        label2.setLocation(label2Offset.getValue(),label2.getY());

        qrCode.setLocation(
            getWidth()+qrCodeOffset.getValue(),
            30
        );

        startButton.setLocation(
            getWidth()/2 - startButton.getWidth()/2,
            getHeight() + buttonOffset.getValue()
        );

        exitButton.setLocation(
            getWidth() - exitButton.getWidth()-20,
            getHeight() - exitButton.getHeight()-20
        );
    }
}
