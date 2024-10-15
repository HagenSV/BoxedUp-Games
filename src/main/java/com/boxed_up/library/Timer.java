package com.boxed_up.library;
public abstract class Timer implements Runnable {
    
    private Thread thread;
    private int secondsRemaining;

    /**
     * Creates a timer that will call onExpire() when the time runs out
     * @param time time in miliseconds
     */
    public Timer(){}

    public abstract void onExpire();

    public void setTime(int seconds){
        if (this.thread != null) { thread.interrupt(); }
        secondsRemaining = seconds;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run(){

        try {
            while (secondsRemaining > 0){
                Thread.sleep(1000);
                secondsRemaining -= 1;
            }
            onExpire();
        } catch (Exception e){
        
        }
    }

    public int getTimeRemaining(){
        return secondsRemaining;
    }
}
