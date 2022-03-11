/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import na.handle.InputManager;
import na.models.GameWorld;

/**
 *
 * @author hoangdp
 */
public class GamePanel extends JPanel implements Runnable {

    private final int _fps = 60;
    private BufferedImage _image;

    private BufferedImage _bufferedImage;
    private Graphics2D _graphicsBuffered;

    private GameWorld _gameWorld;

    public GamePanel() {
        _gameWorld = new GameWorld();
        setFocusable(true);
        addKeyListener(new InputManager(_gameWorld));
        startGame();
    }

    public void startGame() {
        new Thread(this).start();
    }

    public void update() {
        _gameWorld.update();
    }

    public void render() {
        if (_bufferedImage == null) {
            _bufferedImage = new BufferedImage(
                    GameFrame.SCREEN_WIDTH,
                    GameFrame.SCREEN_HEIGHT,
                    BufferedImage.TYPE_INT_ARGB
            );
        }
        if (_graphicsBuffered == null) {
            _graphicsBuffered = (Graphics2D) _bufferedImage.getGraphics();
        }

        synchronized (_graphicsBuffered) {
            _gameWorld.render(_graphicsBuffered);
        }
    }

    @Override
    public void paint(Graphics g) {
        synchronized (_graphicsBuffered) {
            g.drawImage(_bufferedImage, 0, 0, this);
        }
    }

    @Override
    public void run() {
        long beginTime;
        while (true) {
            beginTime = System.currentTimeMillis();
            // Ve lai hoac render    
            update();
            render();
            repaint();

            try {
                Thread.sleep(beginTime + (1000 / _fps) - System.currentTimeMillis());
//                Thread.sleep(0);
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);

                // Bat truong hop thoi gian Update voi Render > thoi gian cho phep
            } catch (java.lang.IllegalArgumentException e) {

            }
        }
    }

    public GameWorld getGameWorld() {
        return _gameWorld;
    }

}
