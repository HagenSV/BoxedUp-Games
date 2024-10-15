package com.boxed_up;

import com.boxed_up.library.OutputLog;

public class Main {
    public static void main(String[] args) {
        OutputLog.getInstance().log("Program Started");
        MenuManager.getInstance();
    }
}