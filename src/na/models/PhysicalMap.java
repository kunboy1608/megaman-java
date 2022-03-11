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
public class PhysicalMap extends GameModel {

    public int[][] phys_map;
    private int _tileSize;

    public PhysicalMap(float x, float y, GameWorld gameWorld) {
        super(x, y, gameWorld);
        this._tileSize = 30;
        phys_map = CacheDataLoader.getInstance().getPhys_map();
    }

    public Rectangle haveCollisionWithTop(Rectangle rect) {

        int posX1 = rect.x / _tileSize;
        posX1 -= 2;
        int posX2 = (rect.x + rect.width) / _tileSize;
        posX2 += 2;

        //int posY = (rect.y + rect.height)/_tileSize;
        int posY = rect.y / _tileSize;

        if (posX1 < 0) {
            posX1 = 0;
        }

        if (posX2 >= phys_map[0].length) {
            posX2 = phys_map[0].length - 1;
        }

        if (posY >= phys_map.length) {
            posY = phys_map.length - 1;
        }
        for (int y = posY; y >= 0; y--) {
            for (int x = posX1; x <= posX2; x++) {

                if (phys_map[y][x] == 1) {
                    Rectangle r = new Rectangle(
                            (int) getPosX() + x * _tileSize,
                            (int) getPosY() + y * _tileSize,
                            _tileSize,
                            _tileSize);
                    if (rect.intersects(r)) {
                        return r;
                    }
                }
            }
        }
        return null;
    }

    public Rectangle haveCollisionWithLand(Rectangle rect) {

        int posX1 = rect.x / _tileSize;
        posX1 -= 2;
        int posX2 = (rect.x + rect.width) / _tileSize;
        posX2 += 2;

        int posY = (rect.y + rect.height) / _tileSize;

        if (posX1 < 0) {
            posX1 = 0;
        }

        if (posX2 >= phys_map[0].length) {
            posX2 = phys_map[0].length - 1;
        }
        for (int y = posY; y < phys_map.length; y++) {
            for (int x = posX1; x <= posX2; x++) {

                if (phys_map[y][x] == 1) {
                    Rectangle r = new Rectangle(
                            (int) getPosX() + x * _tileSize,
                            (int) getPosY() + y * _tileSize,
                            _tileSize,
                            _tileSize);
                    if (rect.intersects(r)) {
                        return r;
                    }
                }
            }
        }
        return null;
    }

    public Rectangle haveCollisionWithRightWall(Rectangle rect) {

        int posY1 = rect.y / _tileSize;
        posY1 -= 2;
        int posY2 = (rect.y + rect.height) / _tileSize;
        posY2 += 2;

        int posX1 = (rect.x + rect.width) / _tileSize;
        int posX2 = posX1 + 3;
        if (posX2 >= phys_map[0].length) {
            posX2 = phys_map[0].length - 1;
        }

        if (posY1 < 0) {
            posY1 = 0;
        }
        if (posY2 >= phys_map.length) {
            posY2 = phys_map.length - 1;
        }

        for (int x = posX1; x <= posX2; x++) {
            for (int y = posY1; y <= posY2; y++) {
                if (phys_map[y][x] == 1) {
                    Rectangle r = new Rectangle(
                            (int) getPosX() + x * _tileSize,
                            (int) getPosY() + y * _tileSize,
                            _tileSize,
                            _tileSize);
                    if (r.y < rect.y + rect.height - 1 && rect.intersects(r)) {
                        return r;
                    }
                }
            }
        }
        return null;

    }

    public Rectangle haveCollisionWithLeftWall(Rectangle rect) {

        int posY1 = rect.y / _tileSize;
        posY1 -= 2;
        int posY2 = (rect.y + rect.height) / _tileSize;
        posY2 += 2;

        int posX1 = (rect.x + rect.width) / _tileSize;
        int posX2 = posX1 - 3;
        if (posX2 < 0) {
            posX2 = 0;
        }

        if (posY1 < 0) {
            posY1 = 0;
        }
        if (posY2 >= phys_map.length) {
            posY2 = phys_map.length - 1;
        }

        for (int x = posX1; x >= posX2; x--) {
            for (int y = posY1; y <= posY2; y++) {
                if (phys_map[y][x] == 1) {
                    Rectangle r = new Rectangle(
                            (int) getPosX() + x * _tileSize,
                            (int) getPosY() + y * _tileSize,
                            _tileSize,
                            _tileSize);
                    if (r.y < rect.y + rect.height - 1 && rect.intersects(r)) {
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
                            (int) getPosX() + j * _tileSize - (int) getGameWorld().getCamera().getPosX(),
                            (int) getPosY() + i * _tileSize - (int) getGameWorld().getCamera().getPosY(),
                            _tileSize,
                            _tileSize
                    );
                }
            }
        }
    }

    public int getTileSize() {
        return _tileSize;
    }

    @Override
    public void update() {
    }

}
