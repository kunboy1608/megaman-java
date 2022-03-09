/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package na.models;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import na.effects.Animation;
import na.handle.CacheDataLoader;

/**
 *
 * @author hoangdp
 */
public class RocketBullet extends Bullet {

    private Animation _forwardBulleUpAni, _forwardBulletDownAni, _forwardBulletAni;
    private Animation _backBulleUpAni, _backBulletDownAni, _backBulletAni;

    private long _startTimeForChangeSpeedY;

    public RocketBullet(float x, float y, GameWorld gameWorld) {
        super(x, y, 30, 30, 1.0f, 10, gameWorld);

        _backBulleUpAni = CacheDataLoader.getInstance().getAnimation("rocketUp");
        _forwardBulleUpAni = CacheDataLoader.getInstance().getAnimation("rocketUp");
        _forwardBulleUpAni.flipAllImage();

        _backBulletDownAni = CacheDataLoader.getInstance().getAnimation("rocketDown");
        _forwardBulletDownAni = CacheDataLoader.getInstance().getAnimation("rocketDown");
        _forwardBulletDownAni.flipAllImage();

        _backBulletAni = CacheDataLoader.getInstance().getAnimation("rocket");
        _forwardBulletAni = CacheDataLoader.getInstance().getAnimation("rocket");
        _forwardBulletAni.flipAllImage();

    }

    @Override
    public void draw(Graphics2D g2) {
        if (getSpeedX() > 0) {
            if (getSpeedY() > 0) {
                _forwardBulletDownAni.draw(
                        (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                        g2
                );
            } else if (getSpeedY() < 0) {
                _forwardBulleUpAni.draw(
                        (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                        g2
                );
            } else {
                _forwardBulletAni.draw(
                        (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                        g2
                );
            }
        } else {
            if (getSpeedY() > 0) {
                _backBulletDownAni.draw(
                        (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                        g2
                );
            } else if (getSpeedY() < 0) {
                _backBulleUpAni.draw(
                        (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                        g2
                );
            } else {
                _backBulletAni.draw(
                        (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                        g2
                );
            }
        }
    }

    public void changeSpeedY() {        
        if (System.currentTimeMillis() % 3 == 0) {
            setSpeedY(getSpeedX());
        } else if (System.currentTimeMillis() % 3 == 1) {
            setSpeedY(-getSpeedX());
        } else {
            setSpeedY(0);
        }
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void attack() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update() {
        super.update();
        if (System.nanoTime() - _startTimeForChangeSpeedY > 500 * 1000000) {
            _startTimeForChangeSpeedY = System.nanoTime();
            changeSpeedY();
        }
    }

}
