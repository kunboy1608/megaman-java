/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package na.models;

import java.awt.Rectangle;

/**
 *
 * @author hoangdp
 */
public abstract class Human extends ParticularModel {

    private boolean _isJumping;
    private boolean _isDicking;

    private boolean _isLanding;

    public Human(float x, float y, float width, float height, float mass, int blood, GameWorld gameWorld) {
        super(x, y, width, height, mass, blood, gameWorld);
        setState(ALIVE);
    }

    @Override
    public void update() {
        super.update();
        if (getState() == ALIVE || getState() == NOBEHURT) {
            if (!_isLanding) {
                setPosX(getPosX() + getSpeedX());

                // Đoạn này cso thể tối ưu bằng cách xét 1 loại va chạm với map
                if (getDirection() == LEFT_DIR) {
                    Rectangle rectLeftWall = getGameWorld().getPhysicalMap().haveCollisionWithLeftWall(getBoundForCollisionWithMap());
                    if (rectLeftWall != null) {
                        setPosX(rectLeftWall.x + rectLeftWall.width + getWidth() / 2);
                    }
                    
                }

                if (getDirection() == RIGHT_DIR) {
                    Rectangle rectRightWall = getGameWorld().getPhysicalMap().haveCollisionWithRightWall(getBoundForCollisionWithMap());
                    if (rectRightWall != null) {
                        setPosX(rectRightWall.x - getWidth() / 2);
                    }
              
                }

                Rectangle boundForCollisionWithMapFuture = getBoundForCollisionWithMap();

                //Nguyên mẫu của video 
//                boundForCollisionWitideohMapFuture.y += (getSpeedX() != 0 ? getSpeedY() : 2);
                boundForCollisionWithMapFuture.y += getSpeedY() + getMass();
                Rectangle rectLand = getGameWorld().getPhysicalMap().haveCollisionWithLand(boundForCollisionWithMapFuture);
                Rectangle rectTop = getGameWorld().getPhysicalMap().haveCollisionWithTop(boundForCollisionWithMapFuture);
                if (rectTop != null) {
                    
                    setSpeedY(0);
                    setPosY(rectTop.y + getGameWorld().getPhysicalMap().getTileSize() + getHeight() / 2);
                } else if (rectLand != null) {
                    
                    setIsJumping(false);
                    if (getSpeedY() > 0) {
                        setIsLanding(true);
                    }
                    setSpeedY(0);
                    setPosY(rectLand.y - getHeight() / 2 + 1);
                } else {
                    
                    setIsJumping(true);
                    setSpeedY(getSpeedY() + getMass());
                    setPosY(getPosY() + getSpeedY());
                }
            }
        }
    }

    public abstract void run();

    public abstract void jump();

    public abstract void dick();

    public abstract void standUp();

    public abstract void stopRun();

    public boolean getIsJumping() {
        return _isJumping;
    }

    public void setIsJumping(boolean _isJumping) {
        this._isJumping = _isJumping;
    }

    public boolean getIsDicking() {
        return _isDicking;
    }

    public void setIsDicking(boolean _isDicking) {
        this._isDicking = _isDicking;
    }

    public boolean getIsLanding() {
        return _isLanding;
    }

    public void setIsLanding(boolean _isLanding) {
        this._isLanding = _isLanding;
    }

}
