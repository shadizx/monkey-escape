package com.monkeyescape.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

/**
 * Represents a sound that plays music of sound effect
 * @author Henry Ruckman-Utting
 * @version 10/31/2022
 */
public class Sound {
    Clip clip;
    URL soundURL[] = new URL[4];

    /**
     * Creates the sound and gets the resources
     */
    public Sound(){
        //Gets the sounds from the resources folder
        soundURL[0] = getClass().getResource("/sound/BackgroundMusic.wav");
        soundURL[1] = getClass().getResource("/sound/KeySoundEffect.wav");
        soundURL[2] = getClass().getResource("/sound/BananaSoundEffect.wav");
        soundURL[3] = getClass().getResource("/sound/LionPitSoundEffect.wav");
    }

    /**
     * Sets the file to the players and lowers the volume
     *
     * # TODO add @param for i
     */
    public void setFile(int i){
        try{
            //Get the file
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

            //Lower the volume
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.
        }
        catch(Exception e){

        }
    }

    /**
     * Plays the sound
     */
    public void play(){
        clip.start();
    }

    /**
     * Sets the sound to loop
     */
    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Stops playing the sound
     */
    public void stop(){
        clip.stop();
    }
}
