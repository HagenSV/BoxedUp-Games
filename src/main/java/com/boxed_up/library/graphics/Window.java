package com.boxed_up.library.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame {

    Thread drawThread;

    JPanel currentScene;

    public Window(String title){
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(900,600));
        drawThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (drawThread != null){
                    repaint();
                }
            }
        });

        drawThread.start();
    }

    public void setScene(JPanel panel){
        if (currentScene != null){
            getContentPane().remove(currentScene);
        }
        currentScene = panel;
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.requestFocus();
        validate();
    }
}
