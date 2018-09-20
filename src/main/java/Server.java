import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.TreeMap;

public class Server {

    private static Server instance;

    /**
     * Joined clients
     */
    private TreeMap<Integer, ClientInstance> clients;

    /**
     * Socket instance
     */
    private final ServerSocket serverSocket;

    private static int lastid = 0;

    private Server() throws IOException {
        serverSocket = new ServerSocket(9999);
        clients = new TreeMap<>();

        listen();
    }


    public static void start() throws IOException {
        if (instance == null) instance = new Server();
    }

    private void listen() throws IOException {
        final ClientInstance clientInstance = new ClientInstance(serverSocket.accept());
        clients.put(++lastid, clientInstance);

        new Thread(() -> {
            Scanner leitura = clientInstance.getReceiver();
            while (leitura.hasNext()) {
                String texto = leitura.nextLine();
                System.err.println(texto);
            }
        }).start();
    }

    private static class ClientInstance {
        private final Socket socket;

        /**
         * Referencia para enviar comandos para o servidor
         */
        private final PrintStream sender;

        /**
         * Referencia para receber comandos do servidor
         */
        private final Scanner receiver;

        public ClientInstance(Socket socket) throws IOException {
            this.socket = socket;
            receiver = new Scanner(socket.getInputStream());
            sender = new PrintStream(socket.getOutputStream());
        }


        public PrintStream getSender() {
            return sender;
        }

        public Scanner getReceiver() {
            return receiver;
        }
    }

    public static void main(String[] args) throws IOException {
        Server.start();
    }

}
