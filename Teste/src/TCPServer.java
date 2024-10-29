import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPServer implements Runnable {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;

    public TCPServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void startServer() throws IOException {
        System.out.println("Servidor aguardando conex�o...");
        clientSocket = serverSocket.accept();
        System.out.println("Cliente conectado!");

        out = new DataOutputStream(clientSocket.getOutputStream());
        in = new DataInputStream(clientSocket.getInputStream());

        new Thread(this).start(); // Inicia a thread para ouvir mensagens recebidas
    }

    public void sendMessage(String message) throws IOException {
        if (out != null) {
            out.writeUTF(message);
        }
    }

    public void close() throws IOException {
        if (clientSocket != null) clientSocket.close();
        if (serverSocket != null) serverSocket.close();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readUTF();
                if (message.equalsIgnoreCase("sair")) {
                    System.out.println("Cliente desconectou.");
                    break;
                }
                System.out.println("Cliente: " + message);
            }
        } catch (IOException e) {
            System.out.println("Erro ao receber mensagem: " + e.getMessage());
        } finally {
            try {
                close();
            } catch (IOException e) {
                System.out.println("Erro ao fechar servidor: " + e.getMessage());
            }
        }
    }
}
