import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * @author Hugo COLLIN, 20220522
 */
public class HttpServer
{
    public static void main(String[] args)
    {
        try {
            //Vérifie que le nombre d'arguments est correct et définit le port si correct
            int port;
            if (args.length == 0)
                port = 80;
            else if (args.length == 1)
                port = Integer.parseInt(args[0]);
            else
                throw new ArrayIndexOutOfBoundsException();

            String strExchange = "";
            Scanner sc = new Scanner(System.in);

            //Ouverture d'une connexion passive
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started, pending...");

            //Connexion
            Socket socket = serverSocket.accept();
            System.out.println("Connected to client " + socket.getInetAddress() + ":" + socket.getPort());

            BufferedReader fromClient = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            PrintWriter toClient = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            while (!strExchange.equals("stop"))
            {
                //Envoi de données
                System.out.println("\nType a message : ");
                strExchange = sc.nextLine();
                toClient.println(strExchange);
                toClient.flush();

                //Réception des données
                if (!strExchange.equals("stop"))
                {
                    System.out.println("\nPending client...");
                    strExchange = fromClient.readLine();
                    System.out.println("Received : " + strExchange);
                }
            }
            toClient.close();
            fromClient.close();
            socket.close();
            serverSocket.close();
            System.out.println("\nConnexion stopped");
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
