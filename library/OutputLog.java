package library;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class OutputLog {
    
    private static File log_dir = new File("logs");
    private File log;

    private static OutputLog instance;

    private OutputLog(){

        log_dir.mkdir();

        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        String fileName = String.format("output-%d-%d-%d", year,month,day);

        log = new File(log_dir,fileName+".log");
        int add = 0;

        while (log.exists()){
            add += 1;
            log = new File(log_dir,fileName+"-"+add+".log");
        }


        try {
            log.createNewFile();
        } catch (IOException e){
            System.out.println("Filed to create log file");
            System.exit(0);
        }
    }

    public static OutputLog getInstance(){
        if (instance == null){
            instance = new OutputLog();
        }
        return instance;
    }


    public void log(String message){
        try (FileWriter writer = new FileWriter(log, true)) {

            Calendar c = Calendar.getInstance();

            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            int second = c.get(Calendar.SECOND);

            writer.append("[");
            writer.append(hour < 10 ? "0"+hour : ""+hour);
            writer.append(minute < 10 ? ":0"+minute : ":"+minute);
            writer.append(second < 10 ? ":0"+second : ":"+second);

            writer.append("] ");
            writer.append(message);
            writer.append("\n");

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
