/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.startup;

import javax.swing.JFrame;
import na.handle.CacheDataLoader;
import na.views.GameFrame;

/**
 *
 * @author hoangdp
 */
public class startUp extends JFrame implements Runnable {

    @Override
    public void run() {

    }

    public startUp() {
        CacheDataLoader.getInstance().loadData();
        new GameFrame();    
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new startUp();
    }

}
