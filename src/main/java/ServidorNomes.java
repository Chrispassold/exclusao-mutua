import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class ServidorNomes {
	
	public static int buscaNovoIdProcesso() {
		Random gerador = new Random();
		return gerador.nextInt(10000);
	}
	
	public static boolean isIdRepetido(HashMap<Integer, PrintStream> processos, int novoId) {
		return processos.containsKey(novoId);
	}

	public static void main(String[] args) {
		HashMap<Integer, PrintStream> processos = new HashMap<>();
//		List<PrintStream> clientes = new ArrayList<PrintStream>();

		try {
			ServerSocket servidor = new ServerSocket(9999);
			System.out.println("Servidor nomes lendo a porta 9999");

			while (true) {
				Socket socket = servidor.accept();

				new Thread() {
					public void run() {
						System.out.println("Cliente conectou: " + socket.getInetAddress().getHostName());

						try {
							PrintStream novoProcesso = new PrintStream(socket.getOutputStream());
							int novoId = buscaNovoIdProcesso();
							while (isIdRepetido(processos, novoId)) {
								novoId = buscaNovoIdProcesso();
							}
							processos.put(novoId, novoProcesso);
							novoProcesso.println(novoId);							
							Scanner leitura = new Scanner(socket.getInputStream());
							while (leitura.hasNext()) {
								String texto = leitura.nextLine();
								System.err.println(texto);
//								for (PrintStream cliente : clientes) {
//									cliente.println(texto);
//									// cliente.flush();
//								}
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
