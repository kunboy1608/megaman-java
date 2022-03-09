/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package na.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import na.effects.Animation;

/**
 *
 * @author hoangdp
 */
public abstract class ParticularModel extends GameModel {

    public static final int LEAGUE_TEAM = 1;
    public static final int ENEMY_TEAM = 2;

    public static final int LEFT_DIR = 0;
    public static final int RIGHT_DIR = 1;

    public static final int ALIVE = 0;
    public static final int BEHURT = 1;
    public static final int FEY = 2;
    public static final int DEATH = 3;
    public static final int NOBEHURT = 4;

    private int _state = ALIVE;

    private float _width;
    private float _height;
    private float _mass;
    private float _speedX;
    private float _speedY;
    private int _blood;

    private int _damge;

    private int _direction;

    protected Animation _beHurtForwardAni, _beHurtBackAni;

    private int _teamType;

    private long _startTimeNoBeHurt;
    private long _timeForNoBeHurt;

    public ParticularModel(float x, float y, float width, float height, float mass, int blood, GameWorld gameWorld) {
        super(x, y, gameWorld);
        _width = width;
        _height = height;
        _mass = mass;
        _blood = blood;
    }

    public void beHurt(int damgeEat) {        
        _blood -= damgeEat;
        _state = BEHURT;
        hurtingCallback();
    }

    public Rectangle getBoundForCollisionWithMap() {
        Rectangle bound = new Rectangle();
        bound.x = (int) (getPosX() - (_width / 2));
        bound.y = (int) (getPosY() - (_height / 2));
        bound.width = (int) _width;
        bound.height = (int) _height;
        return bound;
    }

    public void drawBoundForCollsionWithMap(Graphics2D g2) {
        Rectangle rect = getBoundForCollisionWithMap();
        g2.setColor(Color.BLUE);
        g2.drawRect(
                rect.x - (int) getGameWorld().getCamera().getPosX(),
                rect.y - (int) getGameWorld().getCamera().getPosY(),
                rect.width,
                rect.height
        );

    }

    public void drawBoundForCollisionWithEnemy(Graphics2D g2) {
        Rectangle rect = getBoundForCollisionWithEnemy();
        g2.setColor(Color.red);
        g2.drawRect(
                rect.x - (int) getGameWorld().getCamera().getPosX(),
                rect.y - (int) getGameWorld().getCamera().getPosY(),
                rect.width,
                rect.height
        );
    }

    public boolean isObjectOutOfCameraView() {
        return (getPosX() - getGameWorld().getCamera().getPosX() > getGameWorld().getCamera().getWidthView()
                || getPosX() - getGameWorld().getCamera().getPosX() < -50
                || getPosY() - getGameWorld().getCamera().getPosY() > getGameWorld().getCamera().getHeightView()
                || getPosY() - getGameWorld().getCamera().getPosY() < -50);
    }

    @Override
    public void update() {
        switch (_state) {
            case ALIVE:
                ParticularModel model = getGameWorld().getParticularModelManager().getCollisionWithEnemyModel(this);
                if (model != null
                        && model.getDamge() > 0) {
                    beHurt(model.getDamge());
                    System.out.println("kimochi");
                }
                break;

            case BEHURT:
                if (_beHurtBackAni == null) {
                    _state = NOBEHURT;
                    _startTimeNoBeHurt = System.nanoTime();

                    if (_blood <= 0) {
                        _state = FEY;
                    }
                } else {
                    _beHurtForwardAni.update(System.nanoTime());
                    if (_beHurtForwardAni.isLastFrame()) {
                        _beHurtForwardAni.reset();
                        _state = NOBEHURT;
                        if (_blood <= 0) {
                            _state = FEY;
                        }
                        _startTimeNoBeHurt = System.nanoTime();
                    }
                }

                break;
            case FEY:
                _state = DEATH;
                break;
            case DEATH:
                break;
            case NOBEHURT:
                if (System.nanoTime() - _startTimeNoBeHurt > _timeForNoBeHurt) {
                    _state = ALIVE;
                    System.out.println("alive");
                }
                break;
            default:
                break;
        }
    }

    public abstract Rectangle getBoundForCollisionWithEnemy();

    public abstract void draw(Graphics2D g2);

    public abstract void attack();

    public void hurtingCallback() {
    }

    public int getState() {
        return _state;
    }

    public void setState(int _state) {
        this._state = _state;
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

    public int getBlood() {
        return _blood;
    }

    public void setBlood(int _blood) {
        this._blood = _blood;
    }

    public int getDamge() {
        return _damge;
    }

    public void setDamge(int _damge) {
        this._damge = _damge;
    }

    public int getDirection() {
        return _direction;
    }

    public void setDirection(int _direction) {
        this._direction = _direction;
    }

    public int getTeamType() {
        return _teamType;
    }

    public void setTeamType(int _teamType) {
        this._teamType = _teamType;
    }

    public long getTimeForNoBeHurt() {
        return _timeForNoBeHurt;
    }

    public void setTimeForNoBeHurt(long _timeForNoBeHurt) {
        this._timeForNoBeHurt = _timeForNoBeHurt;
    }

}
