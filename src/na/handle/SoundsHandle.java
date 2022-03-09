/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package na.handle;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author hoangdp
 */
public class SoundsHandle {

    private static final SoundsHandle _instance = new SoundsHandle();

    private SoundsHandle() {
    }

    public void playSound(String name) {
        new Thread() {
            Clip clip;

            public void run() {
                try {
                    AudioInputStream ais = CacheDataLoader.getInstance().getSounds(name);
                    AudioFormat format = ais.getFormat();

                    DataLine.Info info = new DataLine.Info(Clip.class, format);
                    clip = (Clip) AudioSystem.getLine(info);
                    clip.open(ais);
                    clip.start();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(SoundsHandle.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(clip.toString());
                } catch (IOException ex) {
                    Logger.getLogger(SoundsHandle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

    public void playBackgroundSounds() {
        new Thread() {
            public void run() {
                try {
                    AudioInputStream ais = CacheDataLoader.getInstance().getSounds("bgmusic");

                    AudioFormat format = ais.getFormat();

                    DataLine.Info info = new DataLine.Info(Clip.class, format);
                    Clip clip = (Clip) AudioSystem.getLine(info);
                    clip.open(ais);
                    clip.start();

                } catch (LineUnavailableException ex) {
                    Logger.getLogger(SoundsHandle.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SoundsHandle.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

    public static SoundsHandle getInstance() {
        return _instance;
    }
}
