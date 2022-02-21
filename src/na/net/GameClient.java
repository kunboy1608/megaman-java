/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.net;

/**
 *
 * @author hoangdp
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import na.startup.startUp;

public class GameClient extends Thread {

    private InetAddress _ipAddress;
    private DatagramSocket _socket;
    private startUp _game;

    public GameClient(startUp game, String ipAddress) {
        try {
            _game = game;
            _socket = new DatagramSocket();
            _ipAddress = InetAddress.getByName(ipAddress);
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void run() {
        while (true) {
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                _socket.receive(packet);
            } catch (IOException ex) {
                Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Sever :" + new String(packet.getData()));
        }
    }

    public void sendData(byte[] data) {
        DatagramPacket packet = new DatagramPacket(data, data.length, _ipAddress, 10100);
        
        try {
            System.out.println("Gui du lieu len sever");
            _socket.send(packet);
            
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
