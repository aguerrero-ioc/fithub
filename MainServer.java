/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MainServer {

    static int puerto = 8080;
    static String ip = "127.0.0.1";
    public static void main(String[] args) {
        try {
            InetAddress direccionIP = InetAddress.getByName(ip); // Cambia a la IP que deseas usar
            InetSocketAddress direccion = new InetSocketAddress(direccionIP, puerto);
            // Crea el socket del servidor vinculándolo a la dirección y puerto especificados
            ServerSocket server = new ServerSocket();
            server.bind(direccion);
            int i = 0;

            while (true) {

                System.out.println("Esperant client...");
                //Accepta conexio del client
                Socket socket = server.accept();
                System.out.println("Client connectat " + i);
                i++;
                new ThreadClient(socket).start();  // Inicia un fil per a la conexio del client
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class ThreadClient extends Thread {
    private final Socket client;
    private final Scanner in;
    private final PrintWriter out;

    public ThreadClient(Socket client) throws IOException {
        this.client = client;
        this.in = new Scanner(client.getInputStream());
        this.out = new PrintWriter(client.getOutputStream(), true);
    }

    @Override
    public void run() {
        //Missatge d'intercanvi amb el client
        String msg;
        String rsp = "BAD";

        // Envia missatge de conectat al client
        out.println("Client connectat");

        // Llegeix missatge enviat pel client
        if (in.hasNextLine()) {
            msg = in.nextLine();
            if (msg.equals("a")) {
                rsp = "ok";
            }
            // Retorna resposta
            out.println(rsp);
        }

        // Tanca la conexio
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}