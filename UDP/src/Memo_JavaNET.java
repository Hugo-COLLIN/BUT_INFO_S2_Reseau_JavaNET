import java.io.*;
import java.net.*;

public class Memo_JavaNET
{
    public static void main(String[] args) throws SocketException, UnknownHostException, IOException
    {
        // --- Client ---

        //@IP Serveur
        InetAddress adr = InetAddress.getByName("127.0.0.1");

        //Données à envoyer = "Salut"
        byte[] data = (new String("Salut")).getBytes();

        //Création du packet avec les données, en précisant l'@ du serveur et port
        DatagramPacket packet = null;

        //creation d'une socket (sans la lier à un port pour le client)
        DatagramSocket socket = new DatagramSocket();

        //envoi du packet via la socket
        socket.send(packet);

        //Reception reponse du serveur
        socket.receive(packet);

        //Recup et affichage de la donnée dans le paquet
        packet.getData();


        // --- Server ---

        //Création d'une socket liée à un port
        DatagramSocket socket1 = new DatagramSocket(7777);

        //Tableau qui contiendra les données reçues
        byte[] data1 = new byte[0];

        //Creation d'un paquet en utilisant le tableau d'octets
        DatagramPacket socket2 = null;

        //Attente de la reception d'un paquet (paquet reçu est placé dans paquet et ses données dans data)
        socket1.receive(packet);

        //Recup et affichage données
        String chaine = new String(packet.getData(), 0, packet.getLength());

        //Réponse au client (mettre une nouvelle donnée dans le paquet
        packet.setData(data1);

        //on envoie le paquet au client
        socket1.send(packet);

    }
}
