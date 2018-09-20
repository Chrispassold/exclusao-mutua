import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static Client instance;

    /**
     * Referencia para enviar comandos para o servidor
     */
    final PrintStream sender;

    /**
     * Referencia para receber comandos do servidor
     */
    final Scanner receiver;

    private Client() throws IOException {
        Socket cliente = new Socket("127.0.0.1", 9999);
        sender = new PrintStream(cliente.getOutputStream());
        receiver = new Scanner(cliente.getInputStream());
    }

    public static void start() throws IOException {
        if (instance == null) instance = new Client();
    }

    public static void main(String[] args) throws IOException {
        Client.start();
    }

}
