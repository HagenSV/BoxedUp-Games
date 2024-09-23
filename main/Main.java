package main;
import explain_yourself.ExplainGame;
import library.webgame.WebGame;

import java.net.InetAddress;
import java.util.Scanner;

public class Main {

    public static final int THREAD_POOL_SIZE = 10;
    public static void main(String[] args) {
        //consoleGame();
        //launchApp();
        MenuManager.getInstance();

    }

    /**
     * @deprecated
     * Due to changes with the ExplainGame class, this method will no longer work
     */
    @Deprecated
    public static void consoleGame(){

        Scanner s = new Scanner(System.in);

        try {

            WebGame game = new ExplainGame();
            
            System.out.println("Server hosted on "+InetAddress.getLocalHost().getHostAddress());

            System.out.print("Begin? ");
            s.nextLine();
            while (!game.canStart()){
                System.out.println("Not enough players.");
                System.out.print("Begin? ");
                s.nextLine();
            }
            
            System.out.println("Starting game...");
            game.start();
            while (!game.isOver()){}
            System.out.println("Thanks for playing!");

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed to initialize server");
        }

        s.close();
    }
}