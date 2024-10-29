/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPServer {
    public static void main(String[] args) {
        try {
            final DatagramSocket socket = new DatagramSocket(4872); // Porta do servidor
            byte[] buffer = new byte[1000];
            System.out.println("Servidor iniciado. Aguardando mensagens...");

            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                        socket.receive(request); // Recebe mensagens do cliente
                        String message = new String(request.getData(), 0, request.getLength());
                        System.out.println("\n" + message);

                        // Envia resposta de confirmação ao cliente
//                        String replyMessage = "Mensagem recebida!";
//                        DatagramPacket reply = new DatagramPacket(replyMessage.getBytes(), replyMessage.length(),
//                                request.getAddress(), request.getPort());
//                        socket.send(reply);
                    }
                } catch (IOException e) {
                    System.out.println("Erro de E/S: " + e.getMessage());
                }
            });

            // Inicializa a thread para receber mensagens
            receiveThread.start();

            // Permite que o servidor também envie mensagens ao cliente
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\n");
                String message = scanner.nextLine();
                InetAddress clientAddress = InetAddress.getByName("10.0.0.200"); // Altere para o IP do cliente se necessário
                int clientPort = 6360; // Porta do cliente

                DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), clientAddress, clientPort);
                socket.send(packet);
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro de E/S: " + e.getMessage());
        }
    }
}
