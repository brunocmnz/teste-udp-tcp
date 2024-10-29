import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Teste {
    public static void main(String[] args) {
        
        TCP();
        
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Escolha uma opção: (1) Iniciar como Servidor, (2) Iniciar como Cliente");
//        int escolha = scanner.nextInt();
//        scanner.nextLine(); // Consume the newline
//
//        if (escolha == 1) {
//            iniciarServidor();
//        } else if (escolha == 2) {
//            iniciarCliente();
//        } else {
//            System.out.println("Opção inválida. Encerrando...");
//        }
        
        
        
    }

    private static void iniciarServidor() {
        try {
            UDPServer.main(null); // Chama o servidor UDP
        } catch (Exception e) {
            System.out.println("Erro ao iniciar o servidor: " + e.getMessage());
        }
    }

    private static void iniciarCliente() {
        try {
            UDPClient.main(null); // Chama o cliente UDP
        } catch (Exception e) {
            System.out.println("Erro ao iniciar o cliente: " + e.getMessage());
        }
    }
    
    private static void TCP(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite '1' para iniciar como Servidor ou '2' para iniciar como Cliente:");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consumir nova linha

        if (choice == 1) {
            int port = 4872;

            try {
                TCPServer server = new TCPServer(port);
                server.startServer();
                
                while (true) {
                    System.out.print("Digite mensagem para o cliente (ou 'sair' para encerrar): ");
                    String message = scanner.nextLine();
                    server.sendMessage(message);
                    if (message.equalsIgnoreCase("sair")) {
                        server.close();
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Erro no servidor: " + e.getMessage());
            }

        } else if (choice == 2) {
            String serverIP = "10.0.0.156";
            int port = 4872;
            scanner.nextLine(); // Consumir nova linha
            TCPClient client = null;
            try {
                client = new TCPClient(serverIP, port);
            } catch (IOException ex) {
                Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                client.connect();
            } catch (IOException ex) {
                Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (true) {
                System.out.print("Digite mensagem para o servidor (ou 'sair' para encerrar): ");
                String message = scanner.nextLine();
                try {
                    client.sendMessage(message);
                } catch (IOException ex) {
                    Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (message.equalsIgnoreCase("sair")) {
                    try {
                        client.close();
                    } catch (IOException ex) {
                        Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                }
            }

        } else {
            System.out.println("Escolha inválida.");
        }
        
        scanner.close();
    }
        
}
