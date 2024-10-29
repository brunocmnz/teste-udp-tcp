/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.*;
import java.io.*;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        try {
            final DatagramSocket socketSrv = new DatagramSocket(6360); // Porta do servidor
            byte[] bufferSrv = new byte[1000];
            System.out.println("Servidor iniciado. Aguardando mensagens...");

            Thread receiveThreadSrv = new Thread(() -> {
                try {
                    while (true) {
                        DatagramPacket request = new DatagramPacket(bufferSrv, bufferSrv.length);
                        socketSrv.receive(request); // Recebe mensagens do cliente
                        String message = new String(request.getData(), 0, request.getLength());
                        System.out.println("\n" + message);

                        // Envia resposta de confirma??o ao cliente
                        //String replyMessage = "Mensagem recebida!";
                        //DatagramPacket reply = new DatagramPacket(replyMessage.getBytes(), replyMessage.length(),
                        //      request.getAddress(), request.getPort());
                        //socketSrv.send(reply);
                    }
                } catch (IOException e) {
                    System.out.println("Erro de E/S: " + e.getMessage());
                }
            });

            // Inicializa a thread para receber mensagens
            receiveThreadSrv.start();
            
            final DatagramSocket socket = new DatagramSocket(); // Cria o socket para enviar e receber mensagens
            InetAddress serverAddress = InetAddress.getByName("10.0.0.156"); // Substitua pelo IP do servidor se necess?rio
            int serverPort = 4872; // Porta do servidor

            // Thread para receber mensagens do servidor
            Thread receiveThread = new Thread(() -> {
                try {
                    byte[] buffer = new byte[1000];
                    while (true) {
                        DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                        socket.receive(reply); // Recebe a mensagem do servidor
                        String message = new String(reply.getData(), 0, reply.getLength());
                        System.out.println("\n" + message);
                    }
                } catch (IOException e) {
                    System.out.println("Erro de E/S: " + e.getMessage());
                }
            });

            // Inicia a thread de recep??o
            receiveThread.start();

            // Permite que o cliente tamb?m envie mensagens ao servidor
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\n");
                String message = scanner.nextLine();
                DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), serverAddress, serverPort);
                socket.send(packet);
            }
        } catch (SocketException e) {
            System.out.println("Erro no Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro de E/S: " + e.getMessage());
        }
    }
}