import java.io.IOException;
import java.net.*;

public class Serveur
{
    public static void main(String[] args) throws SocketException, IOException, UnknownHostException
    {
        //Création d'une socket liée à un port
        DatagramSocket socket;
        DatagramPacket packet;

        //Tableau qui contiendra les données reçues
        byte[] data;
        int portLocal;



        while (true)
        {
            //Réception
            portLocal = Integer.parseInt(args[0]);
            socket = new DatagramSocket(portLocal);

            data = new byte[256];
            packet = new DatagramPacket(data, data.length);
            socket.receive(packet);

            String affichage = new String(packet.getData(), 0, packet.getLength());
            System.out.println("reçu " + affichage);

            //Accusé de réception
            packet.setData(data);
            socket.send(packet);
        }

    }
}
