package com.boxed_up.library;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
    
    Clip clip;
    
    public Sound(File src) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // create AudioInputStream object 
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(src.getAbsoluteFile()); 
          
        // create clip reference 
        clip = AudioSystem.getClip(); 
          
        // open audioInputStream to the clip 
        clip.open(inputStream);
    }


    public void play(){
        clip.start();
    }


    public long getDuration(){
        return 1 + (clip.getMicrosecondLength() / 1000);
    }

}
