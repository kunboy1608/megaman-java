/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.handle;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import na.effects.Animation;
import na.effects.FrameImage;

/**
 *
 * @author hoangdp
 */
public class CacheDataLoader {

    private final String _frameFile = "src/na/media/images/frame.txt";
    private final String _animationFile = "src/na/media/images/animation.txt";
    private final String _physMapFile = "src/na/media/images/phys_map.txt";
    private final String _backGroundMapFile = "src/na/media/images/background_map.txt";
    private final String _SoundFile = "scr/na/media/images/sounds.txt";

    private Hashtable<String, FrameImage> _frameImages;
    private Hashtable<String, Animation> _animations;
    private Hashtable<String, AudioInputStream> _sounds;

    private int[][] _phys_map;
    private int[][] _background_map;

    private static CacheDataLoader _instance;

    private CacheDataLoader() {
    }

    public static synchronized CacheDataLoader getInstance() {
        if (_instance == null) {
            _instance = new CacheDataLoader();
        }
        return _instance;
    }

    public void loadData() {
        loadFrame();
        loadAnimation();
        loadPhysicalMap();
        loadBackgroundMap();
    }

    public void loadPhysicalMap() {
        try {
            FileReader fr = new FileReader(_physMapFile);
            BufferedReader br = new BufferedReader(fr);

            String line = null;

            line = br.readLine();
            int numberOfRows = Integer.parseInt(line);
            line = br.readLine();
            int numberOfColumns = Integer.parseInt(line);

            this._phys_map = new int[numberOfRows][numberOfColumns];

            for (int i = 0; i < numberOfRows; i++) {
                line = br.readLine();
                String[] str = line.split(" ");

                for (int j = 0; j < numberOfColumns; j++) {
                    this._phys_map[i][j] = Integer.parseInt(str[j]);

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFrame() {
        try {
            _frameImages = new Hashtable<>();
            FileReader fr = new FileReader(_frameFile);

            BufferedReader br = new BufferedReader(fr);

            String line = null;

            if (br.readLine() == null) {
                System.out.println("No data");
                throw new IOException();
            } else {
                // Dua con tro ve dau file
                fr = new FileReader(_frameFile);
                br = new BufferedReader(fr);

                // Bo qua cach dong trong o dau file
                while ((line = br.readLine()).equals(""));

                int n = Integer.parseInt(line);

                for (int i = 0; i < n; i++) {
                    FrameImage frame = new FrameImage();

                    while ((line = br.readLine()).equals(""));
                    frame.setName(line);

                    // Doc path 
                    while ((line = br.readLine()).equals(""));
                    String[] str = line.split(" ");
                    String path = str[1];

                    // Doc x
                    while ((line = br.readLine()).equals(""));
                    str = line.split(" ");
                    int x = Integer.parseInt(str[1]);

                    // Doc y
                    while ((line = br.readLine()).equals(""));
                    str = line.split(" ");
                    int y = Integer.parseInt(str[1]);

                    // Doc w
                    while ((line = br.readLine()).equals(""));
                    str = line.split(" ");
                    int w = Integer.parseInt(str[1]);

                    // Doc h
                    while ((line = br.readLine()).equals(""));
                    str = line.split(" ");
                    int h = Integer.parseInt(str[1]);

                    BufferedImage imageData = ImageIO.read(new File(path));
                    BufferedImage image = imageData.getSubimage(x, y, w, h);
                    frame.setImage(image);

                    _instance._frameImages.put(frame.getName(), frame);

                }
            }
            br.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadAnimation() {
        try {

            _animations = new Hashtable<>();
            FileReader fr = new FileReader(_animationFile);
            BufferedReader br = new BufferedReader(fr);

            String line = null;

            if (br.readLine() == null) {
                System.out.println("No data");
                throw new IOException();
            } else {
                // Dua con tro ve dau file
                fr = new FileReader(_animationFile);
                br = new BufferedReader(fr);

                // Bo qua cach dong trong o dau file
                while ((line = br.readLine()).equals(""));

                //Lay so luong animation
                int n = Integer.parseInt(line);

                for (int i = 0; i < n; i++) {
                    Animation ani = new Animation();

                    while ((line = br.readLine()).equals(""));
                    ani.setName(line);

                    // Doc path 
                    while ((line = br.readLine()).equals(""));
                    String[] str = line.split(" ");
                    for (int j = 0; j < str.length; j += 2) {
                        ani.add(getFrameImage(str[j]), Double.parseDouble(str[j + 1]));
                    }
                    _animations.put(ani.getName(), ani);
                }
            }
            br.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void loadBackgroundMap() {
        FileReader fr;
        try {
            fr = new FileReader(_backGroundMapFile);
            BufferedReader br = new BufferedReader(fr);

            String line = null;

            line = br.readLine();
            int numberOfRows = Integer.parseInt(line);
            line = br.readLine();
            int numberOfColumns = Integer.parseInt(line);

            _background_map = new int[numberOfRows][numberOfColumns];

            for (int i = 0; i < numberOfRows; i++) {
                line = br.readLine();
                String[] str = line.split(" ");
                for (int j = 0; j < numberOfColumns; j++) {
                    _background_map[i][j] = Integer.parseInt(str[j]);
                }
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CacheDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CacheDataLoader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public FrameImage getFrameImage(String name) {
        FrameImage frame = new FrameImage(_instance._frameImages.get(name));
        return frame;
    }

    public Animation getAnimation(String name) {
        Animation ani = new Animation(_instance._animations.get(name));
        return ani;
    }

    public int[][] getPhys_map() {
        return _phys_map;
    }

    public int[][] getBackgroundMap() {
        return _background_map;
    }

}
