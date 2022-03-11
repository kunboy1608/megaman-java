/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import na.effects.FrameImage;
import na.handle.CacheDataLoader;
import na.handle.SoundsHandle;
import na.views.GameFrame;

/**
 *
 * @author hoangdp
 */
public class GameWorld {

    private BulletManager _bulletManager;
    private ParticularModelManager _particularModelManager;

    private Megaman _megaman;

    private PhysicalMap _physicalMap;
    private BackgroundMap _backgroundMap;
    private Camera _camera;

    public static final int FINAL_BOSS_X = 3600;

    public static final int PAUSE_GAME = 0;
    public static final int TUTORIAL = 1;
    public static final int PLAY_GAME = 2;
    public static final int OVER_GAME = 3;
    public static final int WIN_GAME = 4;

    public static final int INTRO_GAME = 5;
    public static final int MEET_FINAL_BOSS = 6;

    public int openIntroGameY = 0;
    public int state = PAUSE_GAME;
    public int previousState = state;
    public int tutorialState = INTRO_GAME;

    public int storyTutorial = 0;
    public String[] texts1 = new String[4];

    public String textTutorial;
    public int currentSize = 1;

    private boolean _finalBossTrigger = true;
    private ParticularModel _boss;

    private FrameImage _avatar;

    //Tam ne
    private int _numberOfLife = 3;

    public GameWorld() {
        texts1[0] = "We are heros and our mission is protecting our Home\n Earth";
        texts1[1] = "i love you";
        texts1[2] = "Boss is ga vler";
        texts1[3] = "LET'S GO BABY";
        textTutorial = texts1[0];

        _avatar = CacheDataLoader.getInstance().getFrameImage("avatar");

        _megaman = new Megaman(400, 400, this);
        _megaman.setTeamType(ParticularModel.LEAGUE_TEAM);

        _physicalMap = new PhysicalMap(0, 0, this);
        _backgroundMap = new BackgroundMap(0, 0, this);

        _camera = new Camera(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT, this);

        _bulletManager = new BulletManager(this);
        _particularModelManager = new ParticularModelManager(this);
        _particularModelManager.addModel(_megaman);

        initEnemy();

        SoundsHandle.getInstance().playBackgroundSounds();
    }

    public void update() {
        switch (state) {
            case PAUSE_GAME:

                break;
            case TUTORIAL:
                TutorialUpdate();
                break;
            case PLAY_GAME:
                Thread t1 = new Thread() {
                    @Override
                    public void run() {
                        _bulletManager.update();
                    }
                };
                Thread t2 = new Thread() {
                    @Override
                    public void run() {
                        _physicalMap.update();
                    }
                };
                Thread t3 = new Thread() {
                    @Override
                    public void run() {
                        _camera.update();
                    }
                };

                _particularModelManager.update();
                t1.start();
                t2.start();
                t3.start();

                try {
                    t1.join();
                    t2.join();
                    t3.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameWorld.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (_megaman.getPosX() > FINAL_BOSS_X && _finalBossTrigger) {
                    _finalBossTrigger = false;
                    switchState(TUTORIAL);

                    tutorialState = MEET_FINAL_BOSS;
                    storyTutorial = 0;
                    openIntroGameY = (int) (GameFrame.SCREEN_HEIGHT * 0.25);

                    _boss = new FinalBoss(FINAL_BOSS_X + 700, 460, this);
                    _boss.setTeamType(ParticularModel.ENEMY_TEAM);
                    _boss.setDirection(ParticularModel.LEFT_DIR);

                    _particularModelManager.addModel(_boss);

                }
                if (_megaman.getState() == ParticularModel.DEATH) {
                    _numberOfLife--;
                    if (_numberOfLife >= 0) {
                        _megaman.setBlood(100);
                        _megaman.setPosY(_megaman.getPosY() - 90);
                        _megaman.setState(ParticularModel.NOBEHURT);
                        _particularModelManager.addModel(_megaman);
                    } else {
                        switchState(OVER_GAME);
                        //Dung nhac
                        SoundsHandle.getInstance().stopPlayBackgroundMusic();
                    }
                }
                if (!_finalBossTrigger
                        && _boss.getState() == ParticularModel.DEATH) {
                    switchState(WIN_GAME);
                }
                break;

            case OVER_GAME:

                break;
            default:
                break;
        }
    }

    public void render(Graphics2D g2) {
        if (g2 == null) {
            return;
        }
        switch (state) {
            case PAUSE_GAME:
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                g2.setColor(Color.WHITE);
                g2.drawString(
                        "Press ENTER to continue",
                        (int) (0.4 * GameFrame.SCREEN_WIDTH),
                        (int) (0.5 * GameFrame.SCREEN_HEIGHT)
                );
                break;
            case TUTORIAL:
                _backgroundMap.draw(g2);
                if (tutorialState == MEET_FINAL_BOSS) {
                    _particularModelManager.draw(g2);
                }
                TutorialRender(g2);
                break;
            case WIN_GAME:
            case PLAY_GAME:
                _backgroundMap.draw(g2);
                _particularModelManager.draw(g2);
                _bulletManager.draw(g2);

                g2.setColor(Color.GRAY);
                g2.fillRect(19, 59, 102, 22);
                g2.setColor(Color.RED);
                g2.fillRect(20, 60, _megaman.getBlood(), 20);

                for (int i = 0; i < _numberOfLife; i++) {
                    g2.drawImage(
                            CacheDataLoader.getInstance().getFrameImage("hearth").getImage(),
                            20 + i * 40,
                            18,
                            null);
                }

                if (state == WIN_GAME) {
                    g2.drawImage(
                            CacheDataLoader.getInstance().getFrameImage("gamewin").getImage(),
                            300,
                            300,
                            null
                    );
                }
                break;
            case OVER_GAME:
                g2.setColor(Color.BLACK);
                g2.fillRect(0, 0, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT);
                g2.setColor(Color.WHITE);
                g2.drawString("NGU !! VÃI LOZNN !!", 450, 300);
                break;
            default:
                break;
        }
    }

    private void initEnemy() {
        ParticularModel redeye = new RedEyeDevil(500, 1190, this);
        redeye.setDirection(ParticularModel.RIGHT_DIR);
        redeye.setTeamType(ParticularModel.ENEMY_TEAM);
        _particularModelManager.addModel(redeye);

        redeye = new RedEyeDevil(1000, 675, this);
        redeye.setDirection(ParticularModel.LEFT_DIR);
        redeye.setTeamType(ParticularModel.ENEMY_TEAM);
        _particularModelManager.addModel(redeye);

        redeye = new RedEyeDevil(1200, 1160, this);
        redeye.setDirection(ParticularModel.LEFT_DIR);
        redeye.setTeamType(ParticularModel.ENEMY_TEAM);
        _particularModelManager.addModel(redeye);

        redeye = new RedEyeDevil(1250, 410, this);
        redeye.setDirection(ParticularModel.LEFT_DIR);
        redeye.setTeamType(ParticularModel.ENEMY_TEAM);
        _particularModelManager.addModel(redeye);

        redeye = new RedEyeDevil(1600, 470, this);
        redeye.setDirection(ParticularModel.LEFT_DIR);
        redeye.setTeamType(ParticularModel.ENEMY_TEAM);
        _particularModelManager.addModel(redeye);

        redeye = new RedEyeDevil(1600, 1220, this);
        redeye.setDirection(ParticularModel.LEFT_DIR);
        redeye.setTeamType(ParticularModel.ENEMY_TEAM);
        _particularModelManager.addModel(redeye);

        redeye = new RedEyeDevil(2000, 1220, this);
        redeye.setDirection(ParticularModel.LEFT_DIR);
        redeye.setTeamType(ParticularModel.ENEMY_TEAM);
        _particularModelManager.addModel(redeye);

        redeye = new RedEyeDevil(2000, 470, this);
        redeye.setDirection(ParticularModel.LEFT_DIR);
        redeye.setTeamType(ParticularModel.ENEMY_TEAM);
        _particularModelManager.addModel(redeye);

        redeye = new RedEyeDevil(2500, 500, this);
        redeye.setDirection(ParticularModel.LEFT_DIR);
        redeye.setTeamType(ParticularModel.ENEMY_TEAM);
        _particularModelManager.addModel(redeye);

        redeye = new RedEyeDevil(2800, 500, this);
        redeye.setDirection(ParticularModel.LEFT_DIR);
        redeye.setTeamType(ParticularModel.ENEMY_TEAM);
        _particularModelManager.addModel(redeye);

        redeye = new RedEyeDevil(3050, 500, this);
        redeye.setDirection(ParticularModel.RIGHT_DIR);
        redeye.setTeamType(ParticularModel.ENEMY_TEAM);
        _particularModelManager.addModel(redeye);

        redeye = new RedEyeDevil(3250, 500, this);
        redeye.setDirection(ParticularModel.LEFT_DIR);
        redeye.setTeamType(ParticularModel.ENEMY_TEAM);
        _particularModelManager.addModel(redeye);

        redeye = new RedEyeDevil(3450, 500, this);
        redeye.setDirection(ParticularModel.LEFT_DIR);
        redeye.setTeamType(ParticularModel.ENEMY_TEAM);
        _particularModelManager.addModel(redeye);

    }

    public void switchState(int s) {
        if (tutorialState == MEET_FINAL_BOSS
                && _camera.getPosX() < FINAL_BOSS_X) {
            return;
        }
        previousState = this.state;
        this.state = s;
    }

    private void TutorialUpdate() {
        switch (tutorialState) {
            case INTRO_GAME:
                if (storyTutorial == 0) {
                    if (openIntroGameY < 450) {
                        openIntroGameY += 5;
                    } else {
                        storyTutorial++;
                    }
                } else {
                    if (currentSize < textTutorial.length()) {
                        currentSize++;
                    }
                }
                break;
            case MEET_FINAL_BOSS:
                if (storyTutorial == 0) {
                    if (openIntroGameY > -450) {
                        openIntroGameY -= 5;
                    }
                    if (_camera.getPosX() < FINAL_BOSS_X) {
                        _camera.setPosX(_camera.getPosX() + 5);
                    }
                    if (_megaman.getPosX() < FINAL_BOSS_X + 150) {
                        _megaman.setDirection(ParticularModel.RIGHT_DIR);
                        _megaman.run();
                        _megaman.update();
                    } else {
                        _megaman.stopRun();
                    }

                    if (openIntroGameY < 450
                            && _camera.getPosX() >= FINAL_BOSS_X
                            && _megaman.getPosX() >= FINAL_BOSS_X + 150) {
                        _camera.lock();
                        storyTutorial++;
                        _megaman.stopRun();

                        _physicalMap.phys_map[14][120] = 1;
                        _physicalMap.phys_map[15][120] = 1;
                        _physicalMap.phys_map[16][120] = 1;
                        _physicalMap.phys_map[17][120] = 1;

                        _backgroundMap.map[14][120] = 17;
                        _backgroundMap.map[15][120] = 17;
                        _backgroundMap.map[16][120] = 17;
                        _backgroundMap.map[17][120] = 17;

                    } else {
                        if (currentSize < textTutorial.length()) {
                            currentSize++;
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private void drawString(Graphics2D g2, String text, int x, int y) {
        for (String string : text.split("\n")) {
            g2.drawString(string, x, y += g2.getFontMetrics().getHeight());
        }
    }

    private void TutorialRender(Graphics2D g2) {
        switch (tutorialState) {
            case INTRO_GAME:
                int yMid = GameFrame.SCREEN_HEIGHT / 2 - 15;
                int y1 = yMid - GameFrame.SCREEN_HEIGHT / 2 - openIntroGameY / 2;
                int y2 = yMid + openIntroGameY / 2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT / 2);
                g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, GameFrame.SCREEN_HEIGHT / 2);

                if (storyTutorial >= 1) {
                    g2.drawImage(_avatar.getImage(), 600, 300, null);
                    g2.setColor(Color.BLUE);
                    g2.fillRect(280, 450, 350, 80);
                    g2.setColor(Color.WHITE);
                    String text = textTutorial.substring(0, currentSize - 1);
                    drawString(g2, text, 290, 480);
                }

                break;
            case MEET_FINAL_BOSS:
//                yMid = (int) (GameFrame.SCREEN_HEIGHT / 2 - 15);
                y1 = GameFrame.SCREEN_HEIGHT / 2 - openIntroGameY / 2;
                y2 = openIntroGameY / 2;

                g2.setColor(Color.BLACK);
                g2.fillRect(0, y1, GameFrame.SCREEN_WIDTH, (int) (0.3 * GameFrame.SCREEN_HEIGHT));
                g2.fillRect(0, y2, GameFrame.SCREEN_WIDTH, (int) (0.5 * GameFrame.SCREEN_HEIGHT));
                break;
            default:
                break;
        }
    }

    public ParticularModelManager getParticularModelManager() {
        return _particularModelManager;
    }

    public BulletManager getBulletManager() {
        return _bulletManager;
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
