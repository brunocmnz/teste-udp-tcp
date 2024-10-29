import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient implements Runnable {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;

    public TCPClient(String serverIP, int port) throws IOException {
        socket = new Socket(serverIP, port);
    }

    public void connect() throws IOException {
        System.out.println("Conectado ao servidor!");
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        new Thread(this).start(); // Inicia a thread para ouvir mensagens recebidas
    }

    public void sendMessage(String message) throws IOException {
        if (out != null) {
            out.writeUTF(message);
        }
    }

    public void close() throws IOException {
        if (socket != null) socket.close();
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readUTF();
                if (message.equalsIgnoreCase("sair")) {
                    System.out.println("Servidor desconectou.");
                    break;
                }
                System.out.println("Servidor: " + message);
            }
        } catch (IOException e) {
            System.out.println("Erro ao receber mensagem: " + e.getMessage());
        } finally {
            try {
                close();
            } catch (IOException e) {
                System.out.println("Erro ao fechar cliente: " + e.getMessage());
            }
        }
    }
}
