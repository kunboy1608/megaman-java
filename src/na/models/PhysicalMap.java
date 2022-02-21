/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import na.handle.CacheDataLoader;

/**
 *
 * @author hoangdp
 */
public class PhysicalMap extends GameModel{
        
    public int[][] phys_map;
    private int _tileSize;

    public PhysicalMap(float x, float y, GameWorld gameWorld){
        super(x,y,gameWorld);
        this._tileSize = 30;
        phys_map = CacheDataLoader.getInstance().getPhys_map();
    }

    public Rectangle haveCollisionWithLand(Rectangle rect) {
        // Lay toa do X cua diem dau
        int posX1 = rect.x / _tileSize;
        posX1 -= 2;
        if (posX1 < 0) {
            posX1 = 0;
        }

        //Lay toa do X cua diem cuoi
        int posX2 = (rect.x + rect.width) / _tileSize;
        posX2 += 2;
        if (posX2 >= phys_map[0].length) {
            posX2 = phys_map[0].length - 1;
        }

        // Lay toa do Y cua dong can xet
        int posY1 = (rect.y + rect.height) / _tileSize;
        posY1 -= 2;
        if (posY1 < 0) {
            posY1 = 0;
        }        
        
        for (int y = posY1; y < phys_map.length; y++) {
            for (int x = posX1; x <= posX2; x++) {
                if (phys_map[y][x] == 1) {
                    Rectangle r = new Rectangle(
                            (int) getPosX() + x * _tileSize,
                            (int) getPosY() + y * _tileSize,
                            _tileSize,
                            _tileSize
                    );
                    if (rect.intersects(r)) {
                        return r;
                    }
                }

            }
        }
        return null;

    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.GRAY);

        for (int i = 0; i < phys_map.length; i++) {
            for (int j = 0; j < phys_map[0].length; j++) {
                if (phys_map[i][j] != 0) {
                    g2.fillRect(
                            (int) getPosX() + j * _tileSize,
                            (int) getPosY() + i * _tileSize,
                            _tileSize,
                            _tileSize
                    );
                }
            }
        }
    }

    public int getTitleSize() {
        return _tileSize;
    }

    @Override
    public void update() {        
    }

}
