/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.models;

import java.awt.Graphics2D;
import na.views.GameFrame;
import na.views.GamePanel;

/**
 *
 * @author hoangdp
 */
public class GameWorld {

    private Megaman _megaman;
    private PhysicalMap _physicalMap;
    private Camera _camera;

    public GameWorld() {
        _megaman = new Megaman(300, 300, this);
        _physicalMap = new PhysicalMap(0, 0, this);
        _camera = new Camera(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this);
    }

    public void update() {
        _megaman.update();
        _camera.update();
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

    public Camera getCamera() {
        return _camera;
    }

    public void setCamera(Camera _camera) {
        this._camera = _camera;
    }
    
}
