/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package na.models;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Hashtable;
import na.effects.Animation;
import na.handle.CacheDataLoader;

/**
 *
 * @author hoangdp
 */
public class FinalBoss extends Human {

    private Animation _idleForward, _idleBack;
    private Animation _shootingForward, _shootingBack;
    private Animation _slideForward, _slideBack;

    private long _startTimeForAttacked;

    private Hashtable<String, Long> _timeAttack = new Hashtable<String, Long>();
    private String[] _attackType = new String[4];
    private int _attackIndex = 0;
    private long _lastAttackTime;

    public FinalBoss(float x, float y, GameWorld gameWorld) {
        super(x, y, 110, 150, 0.1f, 200, gameWorld);

        _idleBack = CacheDataLoader.getInstance().getAnimation("boss_idle");
        _idleForward = CacheDataLoader.getInstance().getAnimation("boss_idle");
        _idleForward.flipAllImage();

        _shootingBack = CacheDataLoader.getInstance().getAnimation("boss_shooting");
        _shootingForward = CacheDataLoader.getInstance().getAnimation("boss_shooting");
        _shootingForward.flipAllImage();

        _slideBack = CacheDataLoader.getInstance().getAnimation("boss_slide");
        _slideForward = CacheDataLoader.getInstance().getAnimation("boss_slide");
        _slideForward.flipAllImage();

        setTimeForNoBeHurt(500 * 1000000);
        setDamge(10);

        _attackType[0] = "NONE";
        _attackType[1] = "shooting";
        _attackType[2] = "NONE";
        _attackType[3] = "slide";

        _timeAttack.put("NONE", 2000L);
        _timeAttack.put("shooting", 500L);
        _timeAttack.put("slide", 5000L);

    }

    @Override
    public void update() {
        super.update();
        if (getGameWorld().getMegaman().getPosX() > getPosX()) {
            setDirection(RIGHT_DIR);
        } else {
            setDirection(LEFT_DIR);
        }

        if (_startTimeForAttacked == 0) {
            _startTimeForAttacked = System.currentTimeMillis();
        }
        attack();
        if (_attackType[_attackIndex].equals("slide")) {
            if (getGameWorld().getPhysicalMap().haveCollisionWithRightWall(getBoundForCollisionWithMap()) != null) {
                setSpeedX(-5);
            } else if (getGameWorld().getPhysicalMap().haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null) {
                setSpeedX(5);
            }
            //Co thay doi so voi code mau            

        }
        setPosX(getPosX() + getSpeedX());
    }

    @Override
    public void draw(Graphics2D g2) {
        if (getState() == NOBEHURT && ((System.nanoTime() / 1000000000) % 2 != 1)) {
            return;
        }
        switch (_attackType[_attackIndex]) {
            case "NONE":
                if (getDirection() == RIGHT_DIR) {
                    _idleForward.update(System.nanoTime());
                    _idleForward.draw(
                            (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                            g2
                    );
                } else {
                    _idleBack.update(System.nanoTime());
                    _idleBack.draw(
                            (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                            g2
                    );
                }
                break;
            case "shooting":
                if (getDirection() == RIGHT_DIR) {
                    _shootingForward.update(System.nanoTime());
                    _shootingForward.draw(
                            (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                            g2
                    );
                } else {
                    _shootingBack.update(System.nanoTime());
                    _shootingBack.draw(
                            (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                            g2
                    );
                }
                break;
            case "slide":
                if (getSpeedX() > 0) {
                    _slideForward.update(System.nanoTime());
                    _slideForward.draw(
                            (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            (int) (getPosY() - getGameWorld().getCamera().getPosY() + 50),
                            g2
                    );
                } else {
                    _slideBack.update(System.nanoTime());
                    _slideBack.draw(
                            (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            (int) (getPosY() - getGameWorld().getCamera().getPosY() + 50),
                            g2
                    );
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void attack() {
        if (System.currentTimeMillis() - _lastAttackTime > _timeAttack.get(_attackType[_attackIndex])) {
            _lastAttackTime = System.currentTimeMillis();

            _attackIndex = (_attackIndex + 1) % _attackType.length;
        }
        if (_attackType[_attackIndex].equals("slide")) {
            if (getPosX() < getGameWorld().getMegaman().getPosX()) {
                setSpeedX(5);
            } else {
                setSpeedX(-5);
            }
        } else if (_attackType[_attackIndex].equals("shooting")) {
            Bullet bullet = new RocketBullet(getPosX(), getPosY() - 50, getGameWorld());

            if (getDirection() == LEFT_DIR) {
                bullet.setSpeedX(-4);
            } else {
                bullet.setSpeedX(4);
            }

            bullet.setTeamType(getTeamType());
            getGameWorld().getBulletManager().addModel(bullet);

        } else {
            setSpeedX(0);
        }

    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle r = getBoundForCollisionWithMap();
        if (_attackType[_attackIndex].endsWith("slide")) {
            r.height -= 50;
        }
        return r;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void jump() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void dick() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void standUp() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void stopRun() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
