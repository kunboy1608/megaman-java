/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.handle;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;

/**
 *
 * @author hoangdp
 */
public class NaLog {

    public NaLog(String messages) {
        // Dinh dang ngay thang log
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-mm-dd-HHe-mm-ss");

        //Tao moi file log
        try {
            // Viet loi  
            Formatter f = new Formatter("log-" + dtf.format(LocalDateTime.now()) + ".txt");
            f.format(messages);            
        } catch (FileNotFoundException e) {
            System.exit(-1);
        }
    }
}
