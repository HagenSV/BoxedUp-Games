package library;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

        String fileName = String.format("log-%d-%d-%d", year,month,day);

        log = new File(fileName);
        int add = 0;

        while (log.exists()){
            add += 1;
            log = new File(fileName+"-"+add);
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
        try (PrintWriter writer = new PrintWriter(log)) {

            Calendar c = Calendar.getInstance();

            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            int second = c.get(Calendar.SECOND);

            writer.println(String.format("[%d:%d:%d] %s",hour,minute,second,message));

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
