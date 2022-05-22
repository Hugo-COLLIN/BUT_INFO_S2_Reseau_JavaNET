import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TcpServer
{
    public static void main(String[] args)
    {
        try {
            //Vérifie que le nombre d'arguments est correct
            if (args.length != 1)
                throw new ArrayIndexOutOfBoundsException();

            String strExchange = "";
            Scanner sc = new Scanner(System.in);

            //Ouverture d'une connexion passive
            ServerSocket serverSocket = new ServerSocket(Integer.parseInt(args[0]));
            System.out.println("Serveur lancé, en attente...");

            //Connexion
            Socket socket = serverSocket.accept();
            System.out.println("Connecté au client " + socket.getInetAddress() + ":" + socket.getPort());

            BufferedReader fromClient = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter toClient = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            while (!strExchange.equals("stop"))
            {
                //Envoi de données
                System.out.println("\nEntrer un message : ");
                strExchange = sc.nextLine();
                toClient.println(strExchange);
                toClient.flush();

                //Réception des données
                if (!strExchange.equals("stop"))
                {
                    System.out.println("\nEn attente du client...");
                    strExchange = fromClient.readLine();
                    System.out.println("Reçu : " + strExchange);
                }
            }
            toClient.close();
            fromClient.close();
            socket.close();
            serverSocket.close();
            System.out.println("\nConnexion stoppée");
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("<!> Nombre d'arguments incorrect. Syntaxe : TcpServer [portNumber]");
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
