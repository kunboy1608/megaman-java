/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author hoangdp
 */
public class Megaman {

    private float _posX;
    private float _posY;

    private float _width;
    private float _height;
    private float _mass;

    private float _speedX;
    private float _speedY;

    private int _direction;

    public static int DIR_LEFT;
    public static int DIR_RIGHT;

    private GameWorld _gameWorld;

    public Megaman(float _posX, float _posY, float _width, float _height, float _mass, GameWorld gameWorld) {
        this._posX = _posX;
        this._posY = _posY;
        this._width = _width;
        this._height = _height;
        this._mass = _mass;
        this._gameWorld = gameWorld;
    }

    public Rectangle getBoundForCollisionWithMap() {
        Rectangle bound = new Rectangle();
        bound.x = (int) (getPosX() - (getWidth() / 2));
        bound.x = (int) (getPosY() - (getHeight() / 2));
        bound.width = (int) getWidth();
        bound.height = (int) getHeight();
        return bound;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.red);
        g2.fillRect(
                (int) (_posX - _width / 2),
                (int) (_posY - _height / 2),
                (int) _width,
                (int) _height
        );
        g2.setColor(Color.BLACK);
        g2.fillRect(
                (int) _posX,
                (int) _posY,
                (int) 2,
                (int) 2
        );
    }

    public void update() {
        setPosX(getPosX() + _speedX);

        Rectangle futureRect = this.getBoundForCollisionWithMap();
        futureRect.x += getSpeedX();
        futureRect.y += getSpeedY() + getMass();

        Rectangle rectLand = _gameWorld.getPhysicalMap().haveCollisionWithLand(futureRect);

        if (rectLand != null) {
//            setPosY(rectLand.y - getHeight() / 2);
        } else {
            setPosY(getPosY() + _speedY + _mass);
        }
    }

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

    public float getWidth() {
        return _width;
    }

    public void setWidth(float _width) {
        this._width = _width;
    }

    public float getHeight() {
        return _height;
    }

    public void setHeight(float _height) {
        this._height = _height;
    }

    public float getMass() {
        return _mass;
    }

    public void setMass(float _mass) {
        this._mass = _mass;
    }

    public float getSpeedX() {
        return _speedX;
    }

    public void setSpeedX(float _speedX) {
        this._speedX = _speedX;
    }

    public float getSpeedY() {
        return _speedY;
    }

    public void setSpeedY(float _speedY) {
        this._speedY = _speedY;
    }

    public int getDirection() {
        return _direction;
    }

    public void setDirection(int _direction) {
        this._direction = _direction;
    }

}
