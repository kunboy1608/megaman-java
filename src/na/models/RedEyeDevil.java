/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package na.models;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.sound.sampled.AudioInputStream;
import na.effects.Animation;
import na.handle.CacheDataLoader;
import na.handle.SoundsHandle;

/**
 *
 * @author hoangdp
 */
public class RedEyeDevil extends ParticularModel {

    private Animation _forwardAni, _backAni;

    private long _startTimeToShoot;

    private AudioInputStream _shooting;

    public RedEyeDevil(float x, float y, GameWorld gameWorld) {
        super(x, y, 127, 89, 0, 100, gameWorld);
        _backAni = CacheDataLoader.getInstance().getAnimation("redeye");
        _forwardAni = CacheDataLoader.getInstance().getAnimation("redeye");
        _forwardAni.flipAllImage();
        _startTimeToShoot = 0;
        setDamge(10);
        setTimeForNoBeHurt(3000000000L);
        //_shooting

    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        return getBoundForCollisionWithMap();
    }

    @Override
    public void draw(Graphics2D g2) {
        if (getState() == NOBEHURT && ((System.nanoTime() / 10000000) % 2 != 1)) {
            return;
        }
        if (getDirection() == LEFT_DIR) {
            _backAni.draw((int) (getPosX() - getGameWorld().getCamera().getPosX()),
                    (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                    g2
            );
        }
    }

    @Override
    public void attack() {
        SoundsHandle.getInstance().playSound("redeyeshooting");

        Bullet bullet = new RedEyeBullet(getPosX(), getPosY(), getGameWorld());
        if (getDirection() == LEFT_DIR) {
            bullet.setSpeedX(-8);
        } else {
            bullet.setSpeedX(8);
        }
        bullet.setTeamType(getTeamType());
        getGameWorld().getBulletManager().addModel(bullet);
    }

    @Override
    public void update() {
        super.update();
        if (System.nanoTime() - _startTimeToShoot > 1000 * 1000000) {
            attack();
            _startTimeToShoot = System.nanoTime();
        }
    }

}
