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
public class BulletFire extends Bullet {

    private Animation _forwardBulletAni, _backBulletAni;

    public BulletFire(float x, float y, GameWorld gameWorld) {
        super(x, y, 60, 30, 1.0f, 10, gameWorld);
        _forwardBulletAni = CacheDataLoader.getInstance().getAnimation("bluefire");
        _backBulletAni = CacheDataLoader.getInstance().getAnimation("bluefire");
        _backBulletAni.flipAllImage();
    }

    @Override
    public void update() {
        // Vien dan da no xong thi moi cho bay
        if (_forwardBulletAni.isIgnoreFrame(0) || _backBulletAni.isIgnoreFrame(0)) {
            setPosX(getPosX() + getSpeedX());
        }

    }

    @Override
    public void draw(Graphics2D g2) {
        if (getSpeedX() > 0) {
            // Vien dan no mat hinh 3 thi khong cho lap lai may khung hinh dau tien nay
            if (!_forwardBulletAni.isIgnoreFrame(0) && _forwardBulletAni.isIgnoreFrame(3)) {
                _forwardBulletAni.setIgnoreFrames(1);
                _forwardBulletAni.setIgnoreFrames(2);
                _forwardBulletAni.setIgnoreFrames(3);
            }
            _forwardBulletAni.update(System.nanoTime());
            _forwardBulletAni.draw(
                    (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                    (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                    g2
            );
        } else {
            if (!_backBulletAni.isIgnoreFrame(0) && _backBulletAni.isIgnoreFrame(3)) {
                _backBulletAni.setIgnoreFrames(1);
                _backBulletAni.setIgnoreFrames(2);
                _backBulletAni.setIgnoreFrames(3);
            }
            _backBulletAni.update(System.nanoTime());
            _backBulletAni.draw(
                    (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                    (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                    g2
            );
        }
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void attack() {
    }

}
