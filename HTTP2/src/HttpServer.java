import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer
{
    public static final String SITE_PATH = "site" + File.separator;
    public static final String IMG_PATH = SITE_PATH + "images" + File.separator;


    public static void main(String[] args) {
        try
        {
            //int port = 8081;
            int port;
            if (args.length == 0)
                port = 80;
            else if (args.length == 1)
                port = Integer.parseInt(args[0]);
            else
                throw new ArrayIndexOutOfBoundsException();


            OutputStream toClient;


            while (true) {
                //Connexion
                ServerSocket servSocket = new ServerSocket(port);
                System.err.println("\nConnected on port " + port + ", pending client request...");

                Socket cliSocket = servSocket.accept();
                System.out.println("Client connected");
                BufferedReader fromClient = new BufferedReader(
                        new InputStreamReader(cliSocket.getInputStream())
                );

                String data;
                FileInputStream fis;
                toClient = cliSocket.getOutputStream();
                while ((data = fromClient.readLine()) != null && data.contains("GET"))
                {
                    String[] requestSplit = data.split(" ");

                    String dir = requestSplit[1];
                    dir = dir.substring("/".length());

                    System.out.println("Requested : " + dir);

                    for (String s : requestSplit)
                        System.out.print(s + "\n");

                    try {
                        fis = new FileInputStream(SITE_PATH + dir);

                        byte[] fileBytes = fis.readAllBytes();
                        toClient.write(fileBytes);
                        toClient.flush();
                    }
                    catch (FileNotFoundException e)
                    {
                        System.out.println("Fichier introuvable dans /");
                    }
                    finally
                    {
                        try
                        {
                            fis = new FileInputStream(IMG_PATH + dir);

                            byte[] fileBytes = fis.readAllBytes();
                            toClient.write(fileBytes);
                            toClient.flush();
                        }
                        catch (FileNotFoundException e)
                        {
                            System.out.println("Fichier introuvable dans /images");
                        }

                    }

                    /*
                    if (dir.contains("html")) {

                    } else {
                        fis = new FileInputStream(IMG_PATH + dir);
                    }*/



                    /*
                    System.out.println(data);
                    //if (data.isEmpty()) break;

                    toClient.write("HTTP OK\r\n\r\n<b>Welcome</b>\r\n\r\n".getBytes(StandardCharsets.UTF_8));
                    toClient.flush();
                                         */

                }
                toClient.close();
                fromClient.close();

                cliSocket.close();
                servSocket.close();


                System.out.println("Client connexion closed");

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
