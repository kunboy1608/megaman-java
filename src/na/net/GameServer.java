/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package na.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import na.startup.startUp;

/**
 *
 * @author hoangdp
 */
public class GameServer extends Thread {

    private DatagramSocket _socket;
    private startUp _game;

    public GameServer(startUp game) {
        _game = game;
        try {
            _socket = new DatagramSocket(10100);
        } catch (SocketException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
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

            //Xu li thong tin gui den
            String message = new String(packet.getData());
            if (message.trim().equalsIgnoreCase("ping")) {
                System.out.println("Client [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "]:" + new String(packet.getData()));
                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
            }
        }
    }

    public void sendData(byte[] data, InetAddress ipAddress, int port) {
        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
        try {
            _socket.send(packet);
        } catch (IOException ex) {
            Logger.getLogger(GameClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
