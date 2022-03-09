/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package na.models;

import java.awt.Graphics2D;
import na.handle.CacheDataLoader;
import na.views.GameFrame;

/**
 *
 * @author hoangdp
 */
public class BackgroundMap extends GameModel {

    public int[][] map;
    private int _tileSize;

    public BackgroundMap(float x, float y, GameWorld gameWorld) {
        super(x, y, gameWorld);
        map = CacheDataLoader.getInstance().getBackgroundMap();
        _tileSize = 30;
    }

    @Override
    public void update() {
    }

    public void draw(Graphics2D g2) {
        Camera cam = getGameWorld().getCamera();
//        g2.setColor(Color.red);

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] != 0
                        && j * _tileSize - cam.getPosX() > -30
                        && j * _tileSize - cam.getPosX() < GameFrame.SCREEN_WIDTH
                        && i * _tileSize - cam.getPosY() > -30
                        && i * _tileSize - cam.getPosY() < GameFrame.SCREEN_HEIGHT) {
                    g2.drawImage(
                            CacheDataLoader.getInstance().getFrameImage("tiled" + map[i][j]).getImage(),
                            (int) getPosX() + j * _tileSize - (int) cam.getPosX(),
                            (int) getPosY() + i * _tileSize - (int) cam.getPosY(),
                            null
                    );
                }
            }
        }
    }
}
