/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.models;

import java.awt.Graphics2D;

/**
 *
 * @author hoangdp
 */
public class GameWorld {

    private Megaman _megaman;
    private PhysicalMap _physicalMap;

    public GameWorld() {
        _megaman = new Megaman(400, 300, 100, 100, 5f, this);
        _physicalMap = new PhysicalMap(0, 0, this);
    }

    public void update() {
        _megaman.update();
    }

    public void render(Graphics2D g2) {
        _physicalMap.draw(g2);
        _megaman.draw(g2);
    }

    public Megaman getMegaman() {
        return _megaman;
    }

    public PhysicalMap getPhysicalMap() {
        return _physicalMap;
    }
}
