package br.edu.unifor;

import java.net.*;
import java.io.*;

public class main {

    public static void main(String[] args) {

        int numero_envios = 2;

        try {
            // Define um endereço de destino (IP + porta)
            InetAddress servidor = InetAddress.getByName("all-systems.mcast.net");
            int porta = 1024;
            // Cria o socket
            DatagramSocket socket = new DatagramSocket();
            // Laço para ler linhas do teclado e enviá-las ao endereço de destino
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Escreva a mensagem que deseja enviar:");
            String linha = teclado.readLine();
            while (!linha.equals(".")) {
                // Cria um pacote com os dados da linha
                byte[] dados = linha.getBytes();
                DatagramPacket pacote = new DatagramPacket(dados, dados.length, servidor, porta);
                // Envia o pacote ao endereço de destino

                for (int i = 1; i <= numero_envios; i++){
                    socket.send(pacote);
                }

                for (int i = 1; i <= numero_envios; i++){
                    socket.receive(pacote);

                    String conteudo = new String(pacote.getData(), 0, pacote.getLength());
                    System.out.println("Pacote recebido de " + pacote.getAddress());
                    System.out.println("Conteúdo do pacote: " + conteudo);
                    // Redefine o tamanho do pacote
                    pacote.setLength(dados.length);
                }

                // Lê a próxima linha
                System.out.println("Escreva a próxima mensagem:");
                linha = teclado.readLine();
            }
        } catch(IOException e){
            System.out.println(e.getMessage());
        }

    }

}
