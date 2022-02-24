/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.effects;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author hoangdp
 */
public class Animation {

    private String _name;
    private boolean _isRepeated;
    private int _currentFrame;
    private long _beginTime;
    private boolean _drawRectFrame;

    private ArrayList<FrameImage> _frameImages;
    private ArrayList<Boolean> _ignoreFrames;
    private ArrayList<Double> _delayFrames;

    public Animation() {
        _beginTime = 0;
        _currentFrame = 0;
        _drawRectFrame = false;
        _isRepeated = true;

        _delayFrames = new ArrayList<>();
        _ignoreFrames = new ArrayList<>();
        _frameImages = new ArrayList<>();

    }

    public Animation(Animation ani) {
        _beginTime = ani._beginTime;
        _currentFrame = ani._currentFrame;
        _drawRectFrame = ani._drawRectFrame;
        _isRepeated = ani._isRepeated;

        _delayFrames = new ArrayList<>();
        ani._delayFrames.forEach(f -> {
            _delayFrames.add(f);
        });

        _ignoreFrames = new ArrayList<>();
        ani._ignoreFrames.forEach(f -> {
            _ignoreFrames.add(f);
        });

        _frameImages = new ArrayList<>();
        ani._frameImages.forEach(f -> {
            _frameImages.add(new FrameImage(f));
        });
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public boolean getIsRepeated() {
        return _isRepeated;
    }

    public void setIsRepeated(boolean _isRepeated) {
        this._isRepeated = _isRepeated;
    }

    public int getCurrentFrame() {
        return _currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        // Kiem tra dieu kien dau vao
        if (currentFrame < 0) {
            return;
        }

        // Neu qua so luong fram thi lap lai
        this._currentFrame = currentFrame % _frameImages.size();
    }

    public long getBeginTime() {
        return _beginTime;
    }

    public void setBeginTime(long _beginTime) {
        this._beginTime = _beginTime;
    }

    public boolean getDrawRectFrame() {
        return _drawRectFrame;
    }

    public void setDrawRectFrame(boolean _drawRectFrame) {
        this._drawRectFrame = _drawRectFrame;
    }

    public ArrayList<FrameImage> getFrameImages() {
        return _frameImages;
    }

    public void setFrameImages(ArrayList<FrameImage> _frameImages) {
        this._frameImages = _frameImages;
    }

    public ArrayList<Boolean> getIgnoreFrames() {
        return _ignoreFrames;
    }

    public void setIgnoreFrames(ArrayList<Boolean> _ignoreFrames) {
        this._ignoreFrames = _ignoreFrames;
    }

    public ArrayList<Double> getDelayFrames() {
        return _delayFrames;
    }

    public void setDelayFrames(ArrayList<Double> _delayFrames) {
        this._delayFrames = _delayFrames;
    }

    public boolean isIgnoreFrame(int index) {
        return _ignoreFrames.get(index);
    }

    public void setIgnoreFrames(int index) {
        if (index < 0 || index >= _ignoreFrames.size()) {
            return;
        }
        _ignoreFrames.set(index, true);
    }

    public void unIgnoreFrames(int index) {
        if (index < 0 || index >= _ignoreFrames.size()) {
            return;
        }
        _ignoreFrames.set(index, false);
    }

    public void reset() {
        _currentFrame = 0;
        _beginTime = 0;

        for (int i = 0; i < _ignoreFrames.size(); ++i) {
            _ignoreFrames.set(i, false);
        }
    }

    public void add(FrameImage frame, double timeToNextFrame) {
        _ignoreFrames.add(false);
        _frameImages.add(frame);
        _delayFrames.add(timeToNextFrame);
    }

    public BufferedImage getCurrentImage() {
        return _frameImages.get(_currentFrame).getImage();
    }

    //Them so trueyn vao la nanotime
    public void update(long currentTime) {
        if (_beginTime == 0) {
            _beginTime = currentTime;
        } else if (currentTime - _beginTime >= _delayFrames.get(_currentFrame)) {
            nextFrame();
            _beginTime = currentTime;
        }

    }

    private void nextFrame() {
        if (_currentFrame >= _frameImages.size() - 1) {

            // Neu da het frame xem co lap lai khong
            if (_isRepeated) {
                _currentFrame = 0;
            }
        } else {
            ++_currentFrame;
        }

        // Kiem tra frame nay co bi bo qua khong
        if (_ignoreFrames.get(_currentFrame)) {
            nextFrame();
        }
    }

    public Boolean isLastFrame() {
        return _currentFrame == _frameImages.size() - 1;
    }

    public void flipAllImage() {
        for (int i = 0; i < _frameImages.size(); ++i) {

            BufferedImage image = _frameImages.get(i).getImage();

            AffineTransform at = AffineTransform.getScaleInstance(-1, 1);
            at.translate(-image.getWidth(), 0);

            AffineTransformOp op = new AffineTransformOp(
                    at,
                    AffineTransformOp.TYPE_BILINEAR
            );

            image = op.filter(image, null);

            _frameImages.get(i).setImage(image);
        }
    }

    public void draw(int x, int y, Graphics2D g2) {
        BufferedImage image = getCurrentImage();

        g2.drawImage(
                image,
                x - image.getWidth() / 2,
                y - image.getHeight() / 2,
                null
        );
        if (_drawRectFrame) {
            g2.drawRect(
                    x - image.getWidth() / 2,
                    y - image.getHeight(),
                    image.getWidth(),
                    image.getHeight()
            );
        }
    }
}
