package com.trapped.utilities;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sounds {

    public static Clip getMusic(String location_soundFile) {
        File file = new File("resources/sounds/"+location_soundFile);
        Clip clip = null;

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20);
            Thread.sleep(0);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }

        return clip;
    }

    public static void changeSoundVolume(Clip clip, int milliseconds, float volume){
        try {
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
            clip.start();
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void changeSoundVolumeLoop(Clip clip, int milliseconds, float volume){

        try {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(volume);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfFileExists(String location_soundFile){
        File tempFile = new File("resources/sounds/"+location_soundFile);
        return tempFile.exists();
    }
}
