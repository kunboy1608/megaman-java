/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package na.models;

/**
 *
 * @author hoangdp
 */
public abstract class GameModel {
    private float _posX;
    private float _posY;
    private GameWorld _gameWorld;

    public GameModel(float _posX, float _posY, GameWorld _gameWorld) {
        this._posX = _posX;
        this._posY = _posY;
        this._gameWorld = _gameWorld;
    }
    
    public abstract void update();
    

    public float getPosX() {
        return _posX;
    }

    public void setPosX(float _posX) {
        this._posX = _posX;
    }

    public float getPosY() {
        return _posY;
    }

    public void setPosY(float _posY) {
        this._posY = _posY;
    }

    public GameWorld getGameWorld() {
        return _gameWorld;
    }

    public void setGameWorld(GameWorld _gameWorld) {
        this._gameWorld = _gameWorld;
    }
    
    
    
}
