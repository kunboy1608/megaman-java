/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package na.models;

/**
 *
 * @author hoangdp
 */
public class BulletManager extends ParticularModelManager {

    public BulletManager(GameWorld game) {
        super(game);
    }

    @Override
    public void update() {
        super.update();
        synchronized (_particularModels) {
            for (int id = 0; id < _particularModels.size(); id++) {
                ParticularModel model = _particularModels.get(id);

                if (model.isObjectOutOfCameraView()
                        || model.getState() == ParticularModel.DEATH) {
                    _particularModels.remove(id);
                }
            }

        }
    }
}
