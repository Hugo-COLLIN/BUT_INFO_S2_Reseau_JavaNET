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
            //Vérifie que le nombre d'arguments est correct
            if (args.length != 2)
                throw new ArrayIndexOutOfBoundsException();

            String strExchange = "";
            Scanner sc = new Scanner(System.in);

            //Demande de connexion
            Socket socket = new Socket(args[0], Integer.parseInt(args[1]));
            System.out.println("Connecté au serveur " + socket.getInetAddress() + ":" + socket.getPort());

            PrintWriter toServer = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            BufferedReader fromServer = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            while (!strExchange.equals("stop"))
            {
                //Réception des données
                System.out.println("\nEn attente du serveur...");
                strExchange = fromServer.readLine();
                System.out.println("Reçu : " + strExchange);

                //Envoi de données
                if (!strExchange.equals("stop"))
                {
                    System.out.println("\nEntrer un message : ");
                    strExchange = sc.nextLine();
                    toServer.println(strExchange);
                    toServer.flush();
                }
            }
            toServer.close();
            fromServer.close();
            socket.close();
            System.out.println("\nConnexion stoppée");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("<!> Nombre d'arguments incorrect. Syntaxe : TcpClient [IPaddress] [portNumber]");
            System.exit(1);
        }
        catch (NumberFormatException e)
        {
            System.out.println("<!> L'argument saisi n'est pas un numéro de port");
            System.exit(2);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("<!> Le numéro de port doit être compris entre 0 et 65535");
            System.exit(3);
        }
        catch (SocketException e)
        {
            System.out.println("<!> Erreur de socket");
            System.exit(4);
        }
        catch (UnknownHostException e)
        {
            System.out.println("<!> Hôte inconnu");
            System.exit(5);
        }
        catch (IOException e)
        {
            System.out.println("<!> Erreur d'entrée-sortie");
            System.exit(6);
        }

    }
}
