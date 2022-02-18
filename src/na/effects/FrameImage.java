/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.effects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author hoangdp
 */
public class FrameImage {

    private String _name;
    private BufferedImage _image;

    public FrameImage() {
        _name = null;
        _image = null;
    }

    public FrameImage(String name, BufferedImage image) {
        _name = name;
        _image = image;
    }

    public FrameImage(FrameImage frame) {
        // Tao moi mot image 
        _image = new BufferedImage(
                frame.getImageWidth(),
                frame.getImageHeight(),
                frame.getImage().getType()
        );

        // Lay graphics cua image
        Graphics g = _image.getGraphics();

        // Ve lai image tren image cu
        g.drawImage(frame.getImage(), 0, 0, null);

    }

    public String getName() {
        return _name;
    }

    public BufferedImage getImage() {
        return _image;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public void setImage(BufferedImage _image) {
        this._image = _image;
    }

    public int getImageWidth() {
        return _image.getWidth();
    }

    public int getImageHeight() {
        return _image.getHeight();
    }

    public void draw(Graphics2D g2, int x, int y) {
        g2.drawImage(_image, x - _image.getHeight() / 2, y - _image.getHeight() / 2, null);
    }

}
