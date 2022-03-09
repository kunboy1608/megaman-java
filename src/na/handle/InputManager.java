/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.handle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import na.models.GameWorld;
import static na.models.GameWorld.INTRO_GAME;
import na.models.Human;

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
                _game.getMegaman().setDirection(Human.LEFT_DIR);
                _game.getMegaman().run();
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                _game.getMegaman().setDirection(Human.RIGHT_DIR);
                _game.getMegaman().run();

                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                _game.getMegaman().dick();
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                // Len tren
                _game.getMegaman().jump();

                break;
            case KeyEvent.VK_SPACE:
                // Nhay
                _game.getMegaman().attack();

                break;

            case KeyEvent.VK_ENTER:
                if (_game.state == GameWorld.PAUSE_GAME
                        && _game.previousState == GameWorld.PAUSE_GAME) {
                    _game.switchState(GameWorld.TUTORIAL);
                } else if (_game.state == GameWorld.TUTORIAL && _game.storyTutorial >= 1) {
                    if (_game.storyTutorial <= 3) {
                        _game.storyTutorial++;
                        _game.currentSize = 1;
                        _game.textTutorial = _game.texts1[_game.storyTutorial - 1];
                    } else {
                        _game.switchState(GameWorld.PLAY_GAME);
                    }

                    // for meeting boss tutorial
                    if (_game.tutorialState == GameWorld.MEET_FINAL_BOSS) {
                        _game.switchState(GameWorld.PLAY_GAME);
                    }
                } else {

                    _game.switchState(GameWorld.PLAY_GAME);
                }
                break;
            case KeyEvent.VK_ESCAPE:
                _game.switchState(GameWorld.PAUSE_GAME);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                _game.getMegaman().stopRun();
                break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                _game.getMegaman().stopRun();

                break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                _game.getMegaman().standUp();

                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                // Len tren

                break;
            case KeyEvent.VK_SPACE:
                // Nhay               
                break;
        }
    }
}
