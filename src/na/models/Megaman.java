/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.models;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import na.effects.Animation;
import na.handle.CacheDataLoader;

/**
 *
 * @author hoangdp
 */
public class Megaman extends Human {

    public static final int RUN_SPEED = 5;

    private Animation _runForwardAni, _runBackAni, _runShootingForwardAni, _runShootingBackAni;
    private Animation _idleForwardAni, _idleBackAni, _idleShootingForwardAni, _idleShootingBackAni;
    private Animation _dickForwardAni, _dickBackAni;
    private Animation _flyForwardAni, _flyBackAni, _flyShootingForwardAni, _flyShootingBackAni;
    private Animation _landingForwardAni, _landingBackAni;
    private Animation _beHurtForwardAni, _beHurtBackAni;

    private Animation _climbWallForwardAni, _climbWallBackAni;

    private long _lastShootingTime;
    private boolean _isShooting = false;

    private AudioInputStream _hurtingSound;
    private AudioInputStream _shootingSound;

    public Megaman(float x, float y, GameWorld gameWorld) {
        super(x, y, 70, 90, 0.1f, 100, gameWorld);
        setTeamType(LEAGUE_TEAM);
        setTimeForNoBeHurt(2000 * 1000000);
        setDirection(RIGHT_DIR);

        _runForwardAni = CacheDataLoader.getInstance().getAnimation("run");
        _runBackAni = CacheDataLoader.getInstance().getAnimation("run");
        _runBackAni.flipAllImage();

        _runShootingForwardAni = CacheDataLoader.getInstance().getAnimation("runshoot");
        _runShootingBackAni = CacheDataLoader.getInstance().getAnimation("runshoot");
        _runShootingBackAni.flipAllImage();

        _idleForwardAni = CacheDataLoader.getInstance().getAnimation("idle");
        _idleBackAni = CacheDataLoader.getInstance().getAnimation("idle");
        _idleBackAni.flipAllImage();

        _idleShootingForwardAni = CacheDataLoader.getInstance().getAnimation("idleshoot");
        _idleShootingBackAni = CacheDataLoader.getInstance().getAnimation("idleshoot");
        _idleShootingBackAni.flipAllImage();

        _dickForwardAni = CacheDataLoader.getInstance().getAnimation("dick");
        _dickBackAni = CacheDataLoader.getInstance().getAnimation("dick");
        _dickBackAni.flipAllImage();

        _flyForwardAni = CacheDataLoader.getInstance().getAnimation("flyingup");
        _flyForwardAni.setIsRepeated(false);
        _flyBackAni = CacheDataLoader.getInstance().getAnimation("flyingup");
        _flyBackAni.setIsRepeated(false);
        _flyBackAni.flipAllImage();

        _flyShootingForwardAni = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
        _flyShootingBackAni = CacheDataLoader.getInstance().getAnimation("flyingupshoot");
        _flyShootingBackAni.flipAllImage();

        _landingForwardAni = CacheDataLoader.getInstance().getAnimation("landing");
        _landingBackAni = CacheDataLoader.getInstance().getAnimation("landing");
        _landingBackAni.flipAllImage();

        _climbWallForwardAni = CacheDataLoader.getInstance().getAnimation("clim_wall");
        _climbWallBackAni = CacheDataLoader.getInstance().getAnimation("clim_wall");
        _climbWallBackAni.flipAllImage();

        _beHurtForwardAni = CacheDataLoader.getInstance().getAnimation("hurting");
        _beHurtBackAni = CacheDataLoader.getInstance().getAnimation("hurting");
        _beHurtBackAni.flipAllImage();

    }

    @Override
    public void update() {
        super.update();

        if (_isShooting) {
            if (System.nanoTime() - _lastShootingTime > 500 * 1000000) {
                _isShooting = false;
            }
        }
        if (getIsLanding()) {
            // Tai sao lai chi can update 1 caci backanimation??
            _landingBackAni.update(System.nanoTime());
            if (_landingBackAni.isLastFrame()) {
                setIsLanding(false);
                _landingBackAni.reset();
                _runForwardAni.reset();
                _runBackAni.reset();
            }
        }
    }

    @Override
    public void run() {
        if (getDirection() == LEFT_DIR) {
            setSpeedX(-RUN_SPEED);
        } else {
            setSpeedX(RUN_SPEED);
        }
    }

    @Override
    public void jump() {
        if (!getIsJumping()) {
            setIsJumping(true);
            setSpeedY(-RUN_SPEED);
            _flyBackAni.reset();
            _flyForwardAni.reset();
        } // For climb wall
        else {
            Rectangle rectRightWall = getBoundForCollisionWithMap();
            rectRightWall.x += 1;
            Rectangle rectLeftWall = getBoundForCollisionWithMap();
            rectLeftWall.x -= 1;

            if (getGameWorld().getPhysicalMap().haveCollisionWithRightWall(rectRightWall) != null
                    && getSpeedX() > 0) {
                setSpeedY(-5.0f);
                _flyBackAni.reset();
                _flyForwardAni.reset();
            } else if (getGameWorld().getPhysicalMap().haveCollisionWithLeftWall(rectLeftWall) != null
                    && getSpeedX() < 0) {
                setSpeedY(-5.0f);
                _flyBackAni.reset();
                _flyForwardAni.reset();
            }
        }
    }

    @Override
    public void dick() {
        if (!getIsJumping()) {
            setIsDicking(true);
        }
    }

    @Override
    public void standUp() {
        setIsDicking(false);
        _idleBackAni.reset();
        _idleForwardAni.reset();
        _dickBackAni.reset();
        _dickForwardAni.reset();
    }

    @Override
    public void stopRun() {
        setSpeedX(0);
        _runBackAni.reset();
        _runForwardAni.reset();
        _runForwardAni.unIgnoreFrames(0);
        _runBackAni.unIgnoreFrames(0);

    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();
        rect.x = (int) getPosX() - 22;
        rect.y = (int) getPosY() - 20;
        rect.width = 44;

        if (getIsDicking()) {
            rect.height = 65;
        } else {
            rect.height = 80;
        }
        return rect;

    }

    @Override
    public void draw(Graphics2D g2) {
        switch (getState()) {
            case ALIVE:
            case NOBEHURT:
                // Cai tg lam cho con nhan vat nhap nhay
                if (getState() == NOBEHURT && ((System.nanoTime() / 10000000) % 2 != 1)) {
                    System.out.println("flash");
                } else {
                    if (getIsLanding()) {
                        if (getDirection() == RIGHT_DIR) {
                            _landingForwardAni.setCurrentFrame(_landingBackAni.getCurrentFrame());
                            _landingForwardAni.draw(
                                    (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                    (int) (getPosY() - getGameWorld().getCamera().getPosY() + (getBoundForCollisionWithMap().height / 2 - _landingForwardAni.getCurrentImage().getHeight())),
                                    g2
                            );

                        } else {
                            _landingBackAni.draw(
                                    (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                    (int) (getPosY() - getGameWorld().getCamera().getPosY() + (getBoundForCollisionWithMap().height / 2 - _landingBackAni.getCurrentImage().getHeight())),
                                    g2
                            );
                        }
                    } else if (getIsJumping()) {
                        if (getDirection() == RIGHT_DIR) {
                            _flyForwardAni.update(System.nanoTime());
                            if (_isShooting) {
                                _flyShootingForwardAni.setCurrentFrame(_flyShootingForwardAni.getCurrentFrame());
                                _flyShootingForwardAni.draw(
                                        (int) (getPosX() - getGameWorld().getCamera().getPosX() + 10),
                                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                                        g2
                                );
                            } else {
                                _flyForwardAni.draw(
                                        (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                                        g2
                                );
                            }
                        } else {
                            _flyBackAni.update(System.nanoTime());
                            if (_isShooting) {
                                _flyShootingBackAni.setCurrentFrame(_flyShootingBackAni.getCurrentFrame());
                                _flyShootingBackAni.draw(
                                        (int) (getPosX() - getGameWorld().getCamera().getPosX() - 10),
                                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                                        g2
                                );

                            } else {
                                _flyBackAni.draw(
                                        (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                                        g2
                                );
                            }
                        }
                    } else if (getIsDicking()) {
                        if (getDirection() == RIGHT_DIR) {
                            _dickForwardAni.update(System.nanoTime());
                            _dickForwardAni.draw(
                                    (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                    (int) (getPosY() - getGameWorld().getCamera().getPosY() + (getBoundForCollisionWithMap().height / 2 - _dickForwardAni.getCurrentImage().getHeight() / 2)),
                                    g2
                            );
                        } else {
                            _dickBackAni.update(System.nanoTime());
                            _dickBackAni.draw(
                                    (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                    (int) (getPosY() - getGameWorld().getCamera().getPosY() + (getBoundForCollisionWithMap().height / 2 - _dickBackAni.getCurrentImage().getHeight() / 2)),
                                    g2
                            );
                        }
                    } else {
                        if (getSpeedX() > 0) {
                            _runForwardAni.update(System.nanoTime());
                            if (_isShooting) {
                                _runShootingForwardAni.setCurrentFrame(_runForwardAni.getCurrentFrame());
                                _runShootingForwardAni.draw(
                                        (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                                        g2
                                );
                            } else {
                                _runForwardAni.draw(
                                        (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                                        g2
                                );
                            }
                        } else if (getSpeedX() < 0) {
                            _runBackAni.update(System.nanoTime());
                            if (_isShooting) {
                                _runShootingBackAni.setCurrentFrame(_runBackAni.getCurrentFrame());
                                _runShootingBackAni.draw(
                                        (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                                        g2
                                );
                            } else {
                                _runBackAni.draw(
                                        (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                        (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                                        g2
                                );
                            }

                        } else {
                            if (getDirection() == RIGHT_DIR) {
                                if (_isShooting) {
                                    _idleShootingForwardAni.update(System.nanoTime());
                                    _idleShootingForwardAni.draw(
                                            (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                            (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                                            g2
                                    );

                                } else {
                                    _idleForwardAni.update(System.nanoTime());
                                    _idleForwardAni.draw(
                                            (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                            (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                                            g2
                                    );
                                }
                            } else {
                                if (_isShooting) {
                                    _idleShootingBackAni.update(System.nanoTime());
                                    _idleShootingBackAni.draw(
                                            (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                            (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                                            g2
                                    );
                                } else {
                                    _idleBackAni.draw(
                                            (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                                            (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                                            g2
                                    );
                                }
                            }
                        }
                    }
                }
                break;
            case BEHURT:
                if (getDirection() == RIGHT_DIR) {
                    _beHurtForwardAni.draw(
                            (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                            g2
                    );
                } else {
                    _beHurtBackAni.setCurrentFrame(_beHurtForwardAni.getCurrentFrame());
                    _beHurtBackAni.draw(
                            (int) (getPosX() - getGameWorld().getCamera().getPosX()),
                            (int) (getPosY() - getGameWorld().getCamera().getPosY()),
                            g2
                    );
                }
                break;

            case FEY:
                break;
            default:
                break;
        }
        drawBoundForCollsionWithMap(g2);
    }

    @Override
    public void attack() {
        if (!_isShooting && !getIsDicking()) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Clip clip = AudioSystem.getClip();
                        clip.open(_shootingSound);
                        clip.start();
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(Megaman.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Megaman.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        }
    }

    @Override
    public void hurtingCallback() {

    }
}
