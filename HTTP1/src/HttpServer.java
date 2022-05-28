import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpServer
{
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
                System.out.println("Connected on port " + port + ", pending client...");

                Socket cliSocket = servSocket.accept();
                System.out.println("Client connected");
                BufferedReader fromClient = new BufferedReader(
                        new InputStreamReader(cliSocket.getInputStream())
                );

                String data;
                toClient = cliSocket.getOutputStream();
                while ((data = fromClient.readLine()) != null && data.contains("GET"))
                {
                    System.out.println(data);
                    //if (data.isEmpty()) break;

                    toClient.write("HTTP OK\r\n\r\n<b>Welcome</b>\r\n\r\n".getBytes(StandardCharsets.UTF_8));
                    toClient.flush();

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
