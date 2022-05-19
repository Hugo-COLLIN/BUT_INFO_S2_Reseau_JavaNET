
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

/**
 * @author Hugo COLLIN 20220520
 */
public class UdpServer
{
    public static void main(String[] args)
    {
        try {
            DatagramSocket socket = new DatagramSocket(Integer.parseInt(args[0]));
            byte [] data = new byte[256];
            DatagramPacket packet = new DatagramPacket(data, data.length);

            while (true)
            {
                //Réception des données
                socket.receive(packet);
                String show = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Reçu: " + show);

                //Envoi d'un accusé de réception
                String s = new String(data, StandardCharsets.UTF_8);
                data = ("recu " + s).getBytes();
                DatagramPacket receipt = new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
                socket.send(receipt);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
