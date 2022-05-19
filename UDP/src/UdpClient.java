import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Hugo COLLIN 20220520
 */
public class UdpClient
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
            socket.setSoTimeout(2000); //Lance une exception après 2 secondes si l'accusé de réception n'est pas arrivé

            try {
                socket.receive(receipt);
                String show = new String(receipt.getData());
                System.out.println("Accusé de réception: " + show);
            }
            catch (SocketTimeoutException sTE) //Si l'accusé de réception n'arrive pas avant la timeout de la socket
            {
                System.out.println("Echec de la réception");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
