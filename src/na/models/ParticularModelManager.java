/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package na.models;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author hoangdp
 */
public class ParticularModelManager {

    protected List<ParticularModel> _particularModels;
    private GameWorld _gameWorld;

    public ParticularModelManager(GameWorld game) {
        _particularModels = Collections.synchronizedList(new LinkedList<ParticularModel>());
        _gameWorld = game;
    }

    public void addModel(ParticularModel model) {
        synchronized (_particularModels) {
            _particularModels.add(model);
        }
    }

    public void removeModel(ParticularModel model) {
        synchronized (_particularModels) {
//            for (int id = 0; id < _particularModels.size(); id++) {
//                if (model == _particularModels.get(id)) {
//                    _particularModels.remove(id);
//                }
//            }
            _particularModels.remove(model);
        }
    }

    public ParticularModel getCollisionWithEnemyModel(ParticularModel model) {
        synchronized (_particularModels) {
            for (int id = 0; id < _particularModels.size(); id++) {
                ParticularModel m = _particularModels.get(id);
                if (model.getTeamType() != m.getTeamType()
                        && model.getBoundForCollisionWithEnemy().intersects(m.getBoundForCollisionWithEnemy())) {
                    return m;
                }
            }
        }
        return null;
    }

    public void inRaDanhSachModel() {
        for (ParticularModel _particularModel : _particularModels) {
            System.out.println(_particularModel.getClass());
        }
        System.out.println("----------------");
    }

    public void update() {
        synchronized (_particularModels) {
            for (int id = 0; id < _particularModels.size(); id++) {
                ParticularModel model = _particularModels.get(id);
                if (!model.isObjectOutOfCameraView()) {
                    model.update();
                }
                if (model.getState() == ParticularModel.DEATH) {
                    _particularModels.remove(id);
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        synchronized (_particularModels) {
            for (ParticularModel model : _particularModels) {
                if (!model.isObjectOutOfCameraView()) {
                    model.draw(g2);
                }
            }

        }
    }

    public GameWorld getGameWorld() {
        return _gameWorld;
    }
}
