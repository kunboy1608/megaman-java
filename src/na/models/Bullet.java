/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package na.models;

import java.awt.Graphics2D;

/**
 *
 * @author hoangdp
 */
public abstract class Bullet extends ParticularModel {

    public Bullet(float x, float y, float width, float height, float mass, int damge, GameWorld gameWorld) {
        super(x, y, width, height, mass, 1, gameWorld);
        setDamge(damge);
    }

    public abstract void draw(Graphics2D g2);

    public void update() {
        super.update();
        setPosX(getPosX() + getSpeedX());
        setPosY(getPosY() + getSpeedY());
        
    }
}
