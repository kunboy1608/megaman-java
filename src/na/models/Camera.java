/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package na.models;

import na.views.GameFrame;

/**
 *
 * @author hoangdp
 */
public class Camera extends GameModel {

    private float _widthView;
    private float _heightView;

    private boolean _isLocked;

    public Camera(float x, float y, float withView, float heightView, GameWorld gameWorld) {
        super(x, y, gameWorld);
        _widthView = withView;
        _heightView = heightView;
        _isLocked = false;
    }

    public void lock() {
        _isLocked = true;
    }

    public void unlock() {
        _isLocked = false;
    }

    @Override
    public void update() {
        if (!_isLocked) {
            Megaman main = getGameWorld().getMegaman();
            if (main.getPosX() - getPosX() > GameFrame.SCREEN_WIDTH * 0.6) {
                setPosX(main.getPosX() - GameFrame.SCREEN_WIDTH * 0.6f);
            } else if (main.getPosX() - getPosX() < GameFrame.SCREEN_WIDTH * 0.4f) {
                setPosX(main.getPosX() - GameFrame.SCREEN_WIDTH * 0.4f);
            }

            if (main.getPosY() - getPosY() > GameFrame.SCREEN_HEIGHT * 0.7f) {
                setPosY(main.getPosY() - GameFrame.SCREEN_HEIGHT * 0.7f);
            } else if (main.getPosY() - getPosY() < GameFrame.SCREEN_HEIGHT * 0.3f) {
                setPosY(main.getPosY() - GameFrame.SCREEN_HEIGHT * 0.3f);
            }
        }
    }

    public float getWidthView() {
        return _widthView;
    }

    public void setWidthView(float _widthView) {
        this._widthView = _widthView;
    }

    public float getHeightView() {
        return _heightView;
    }

    public void setHeightView(float _heightView) {
        this._heightView = _heightView;
    }

}
