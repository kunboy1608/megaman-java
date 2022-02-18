/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.startup.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author hoangdp
 */
public class LoadLanguage {

    private LoadLanguage _instance = new LoadLanguage();
    private Properties _lang;

    public LoadLanguage getInstance() {
        loadFile();
        return _instance;
    }

    private void loadFile() {
        _lang = new Properties();
        String url = "/src/na/startup/config/lang.properties";

        try ( FileInputStream fis = new FileInputStream(url)) {
            _lang.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public String getValue(String s) {
        return _lang.getProperty(s);
    }
}
