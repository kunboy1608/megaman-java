/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.handle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import na.models.GameWorld;
import na.models.Megaman;
import na.views.GamePanel;

/**
 *
 * @author hoangdp
 */
public class InputManager implements KeyListener {

//    private static final inputManager _instance = new inputManager();
//
//    public static inputManager getInstance() {
//        return _instance;
//    }
    GameWorld _game;

    private InputManager() {
    }

    public InputManager(GameWorld game) {
        _game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                _game.getMegaman().setDirection(Megaman.DIR_LEFT);
                _game.getMegaman().setSpeedX(-5f);
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                _game.getMegaman().setDirection(Megaman.DIR_RIGHT);
                _game.getMegaman().setSpeedX(5f);

                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                // Xuong duoi
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                // Len tren
                _game.getMegaman().setSpeedY(-3f);

                break;
            case KeyEvent.VK_SPACE:
                // Nhay
                _game.getMegaman().setSpeedY(-3f);

                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                _game.getMegaman().setSpeedX(0f);
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                _game.getMegaman().setSpeedX(0f);

                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                // Xuong duoi

                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                // Len tren
                _game.getMegaman().setSpeedY(0f);
                break;
            case KeyEvent.VK_SPACE:
                // Nhay
                _game.getMegaman().setSpeedY(0f);
                break;
        }
    }
}
