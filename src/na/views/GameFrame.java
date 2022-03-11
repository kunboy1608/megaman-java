/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.views;

import javax.swing.JFrame;

/**
 *
 * @author hoangdp
 */
public class GameFrame extends JFrame {

    public static final int SCREEN_WIDTH = 1080;
    public static final int SCREEN_HEIGHT = 680;

    public GameFrame() {
        initComponents();
    }

    private void initComponents() {

        //Properties Frame
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Megaman - JDK17");
        setResizable(false);

        // Can Frame giua man hinh        
        setLocationRelativeTo(null);

        // Add Components
        GamePanel panel = new GamePanel();
        add(panel);
        setVisible(true);
    }
}
