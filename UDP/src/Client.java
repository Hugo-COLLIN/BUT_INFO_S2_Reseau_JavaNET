import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client
{
    public static void main(String[] args) throws SocketException, IOException, UnknownHostException
    {
        try {
            //Envoi des données
            DatagramSocket socket = new DatagramSocket();
            InetAddress adr = InetAddress.getByName(args[0]); //"127.0.0.1"
            int portDest = Integer.parseInt(args[1]);
            byte[] data = args[2].getBytes(StandardCharsets.ISO_8859_1);

            DatagramPacket packet = new DatagramPacket(data, data.length, adr, portDest);
            socket.send(packet);

            //Réception de l'accusé de réception
            byte [] response = new byte[256];
            DatagramPacket receipt = new DatagramPacket(response, response.length);
            socket.setSoTimeout(2000);

            try {
                socket.receive(receipt);
                String show = new String(receipt.getData());
                System.out.println("Accusé de réception: " + show);
            }
            catch (SocketTimeoutException sTE)
            {
                System.out.println("Echec de la réception");
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
        InetAddress adr;

        byte[] data;
        String servAdr, msg;
        int portDest;


        //Envoi
        socket = new DatagramSocket();
        adr = InetAddress.getByName("127.0.0.1");
        data = (new String("Salut")).getBytes();
        portDest = 7777;

        while (true)
        {
            //Envoi
            packet = new DatagramPacket(data, data.length, adr, portDest);
            socket.send(packet);

            //Attente d'accusé de réception
            data = new byte[256];
            packet = new DatagramPacket(data, data.length);
            socket.receive(packet);
            String affichage = new String(packet.getData(), 0, packet.getLength());
        }

         */

    /*
        while (true)
        {
            //Réception
            portDest = Integer.parseInt(args[0]);
            socket = new DatagramSocket(portDest);

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
