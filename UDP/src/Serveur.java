import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;

public class Serveur
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
                DatagramPacket receipt = new DatagramPacket(data, data.length, packet.getAddress(), packet.getPort());
                socket.send(receipt);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }





        /*
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
         */

    }
}
