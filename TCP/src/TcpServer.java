import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TcpServer
{
    public static void main(String[] args) {

        try {
            //Ouverture connexion passive
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
            String strExchange = "";
            Scanner sc = new Scanner(System.in);

            while (!strExchange.equals("stop"))
            {
                System.out.println(strExchange);
                System.out.println(strExchange.equals("stop".toLowerCase()));
                //Connexion
                Socket socket = serverSocket.accept();
                BufferedReader fromClient = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                PrintWriter toClient = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream())
                );

                //Envoi de données
                System.out.println("Entrer un message : ");
                strExchange = sc.nextLine();
                toClient.println(strExchange);
                toClient.flush();

                //Réception des données
                if (!strExchange.equals("stop"))
                {
                    strExchange = fromClient.readLine();
                    System.out.println("Reçu du client : " + strExchange);
                }

                toClient.close();
                fromClient.close();
                socket.close();
            }
            serverSocket.close();
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
