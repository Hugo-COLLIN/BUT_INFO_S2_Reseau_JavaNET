import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TcpClient
{
    public static void main(String[] args) {
        try
        {
            //Envoi des données
            Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
            String strExchange = "";
            Scanner sc = new Scanner(System.in);

            PrintWriter toServer = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            BufferedReader fromServer = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            //boolean quit = strExchange.equals("stop".toLowerCase());
            //quit = "stop".toLowerCase().equals(strExchange);
            while (!strExchange.equals("stop"))
            {
                //Réception des données
                strExchange = fromServer.readLine();
                System.out.println("Reçu du serveur : " + strExchange);

                //Envoi de données
                if (!strExchange.equals("stop"))
                {
                    System.out.println("Entrer un message : ");
                    strExchange = sc.nextLine();
                    toServer.println(strExchange);
                    toServer.flush();
                }

            }
            toServer.close();
            fromServer.close();
            socket.close();

        }
        catch (SocketException e)
        {
            System.out.println("Erreur de socket");
        }
        catch (UnknownHostException e)
        {
            System.out.println("Hôte inconnu");
        }
        catch (IOException e)
        {
            System.out.println("Erreur d'entrée-sortie");
            System.exit(1);
        }

    }
}
