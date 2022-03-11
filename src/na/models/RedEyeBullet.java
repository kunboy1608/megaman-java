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
public class RedEyeBullet extends Bullet {

    private Animation _forwarBulletAni, _backBulletAni;

    public RedEyeBullet(float x, float y, GameWorld gameWorld) {
        super(x, y, 30, 30, 1.0f, 10, gameWorld);
        _forwarBulletAni = CacheDataLoader.getInstance().getAnimation("redeyebullet");
        _backBulletAni = CacheDataLoader.getInstance().getAnimation("redeyebullet");
        _backBulletAni.flipAllImage();
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (getSpeedX() > 0) {
            _forwarBulletAni.update(System.nanoTime());
            _forwarBulletAni.draw(
                    (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                    (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                    g2
            );
        } else {
            _backBulletAni.update(System.nanoTime());
            _backBulletAni.draw(
                    (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                    (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                    g2
            );
        }
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void attack() {
    }
}
