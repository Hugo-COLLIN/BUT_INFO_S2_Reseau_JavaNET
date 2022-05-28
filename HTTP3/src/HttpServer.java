import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer
{
    public static final String SITE_PATH = "site" + File.separator;
    public static final String IMG_PATH = SITE_PATH + "images" + File.separator;

    public static String cliAdr;


    public static void main(String[] args) {
        try
        {
            int port = setPort(args);

            OutputStream toClient;

            while (true) {
                //Connexion
                ServerSocket servSocket = new ServerSocket(port);
                System.out.println("\nConnected on port " + port + ", pending client request...");

                Socket cliSocket = servSocket.accept();
                cliAdr = cliSocket.getInetAddress() + ":" + cliSocket.getPort();
                System.out.println("Client " + cliAdr + " connected");
                BufferedReader fromClient = new BufferedReader(
                        new InputStreamReader(cliSocket.getInputStream())
                );

                String data;
                //FileInputStream fis;
                toClient = cliSocket.getOutputStream();

                while ((data = fromClient.readLine()) != null && data.contains("GET"))
                {
                    String[] requestSplit = data.split(" ");

                    String path = requestSplit[1].substring(File.separator.length());
                    System.out.println("Requested : " + path);

                    if (path.equals(""))
                        path = "index.html";

                    System.out.println("Try to send : " + path);

                    if (!accessFile(SITE_PATH + path, toClient))
                        accessFile(IMG_PATH + path , toClient);
                    /*
                    try {
                        fis = new FileInputStream(SITE_PATH + dir);

                        byte[] fileBytes = fis.readAllBytes();
                        toClient.write(fileBytes);
                        toClient.flush();
                    }
                    catch (FileNotFoundException e)
                    {
                        System.out.println("File not found in /");
                    }
                    finally
                    {
                        try
                        {
                            fis = new FileInputStream(IMG_PATH + dir);
                            System.out.println("File found in /images");

                            byte[] fileBytes = fis.readAllBytes();
                            toClient.write(fileBytes);
                            toClient.flush();
                        }
                        catch (FileNotFoundException e)
                        {
                            System.out.println("File not found in /images");
                        }
                    }

                     */
                }
                toClient.close();
                fromClient.close();

                cliSocket.close();
                servSocket.close();


                System.out.println("Resource loaded, connexion closed with " + cliAdr);

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static int setPort (String args[])
    {
        int port;

        if (args.length == 0)
            port = 80;
        else if (args.length == 1)
            port = Integer.parseInt(args[0]);
        else
            throw new ArrayIndexOutOfBoundsException();

        return port;
    }

    private static boolean accessFile(String path, OutputStream os)
    {
        FileInputStream fis;
        boolean res = false;
        try {
            fis = new FileInputStream(path);

            byte[] fileBytes = fis.readAllBytes();
            os.write(fileBytes);
            os.flush();
            res = true;
            System.out.println("Found in " + File.separator + path);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Not found in " + File.separator + path);
        }
        catch (IOException e)
        {
            System.out.println("In-Out error");
        }
        return res;
    }

}
