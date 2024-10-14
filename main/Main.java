package main;

import library.OutputLog;
public class Main {

    public static final int THREAD_POOL_SIZE = 10;
    public static void main(String[] args) {
        OutputLog.getInstance().log("Program Started");
        MenuManager.getInstance();
    }
}