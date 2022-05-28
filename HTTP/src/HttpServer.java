import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * @author Hugo COLLIN, 20220522
 */
public class HttpServer
{
   //static final String SITEPATH = "site" + File.separator;


    public static void main(String[] args)
    {
        try {
            //Vérifie que le nombre d'arguments est correct et définit le port si c'est le cas
            int port;
            if (args.length == 0)
                port = 80;
            else if (args.length == 1)
                port = Integer.parseInt(args[0]);
            else
                throw new ArrayIndexOutOfBoundsException();

            String msgClient = "", msgServer = "";
            Scanner sc = new Scanner(System.in);

            //Ouverture d'une connexion passive
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started, pending...");

            BufferedReader fromClient;
            OutputStream outClient;
            PrintWriter toClient;

            while (true)
            {
                //Connexion
                Socket socket = serverSocket.accept();
                System.out.println("Connected to client " + socket.getInetAddress() + ":" + socket.getPort());

                fromClient = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                outClient = socket.getOutputStream();

                toClient = new PrintWriter(
                        new OutputStreamWriter(outClient)
                );

                //Réception des données
                System.out.println("\nPending client...");
                msgClient = fromClient.readLine();
                System.out.println("Received : " + msgClient);

                if(!msgClient.contains("GET")) return;
                String [] requestSplited = msgClient.split(" ");

                String clientDirRequest = requestSplited[1];
                clientDirRequest = clientDirRequest.substring("/".length());
                System.out.println("Contenu : " + clientDirRequest);

                for (String s : requestSplited)
                    System.out.println(s);

                FileInputStream fileContent;

                //if (clientDirRequest.toLowerCase().contains("html"))
                fileContent = new FileInputStream("ressource" + File.separator);

                byte [] fileBytes = fileContent.readAllBytes();
                outClient.write(fileBytes);


                outClient.close();
                toClient.close();
                fromClient.close();
                socket.close();
                serverSocket.close();
                System.out.println("\nConnexion stopped");



                /*
                //Envoi de données
                System.out.println("\nType a message : ");
                msgClient = sc.nextLine();
                toClient.println(msgClient);
                toClient.flush();

                 */

            }
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
            e.printStackTrace();
            System.exit(6);
        }

    }
}
