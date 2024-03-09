/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example;
/*
    Author: ScrumFit Squad 2024

 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainClient {

    static int port = 8080;
    static String ip = "127.0.0.1";

    public static void main(String[] args) {
        Socket clientSocket = null;
        Scanner in = null;
        PrintWriter out = null;
        Scanner sc = new Scanner(System.in);
        String resultat;

        try {
            // Conectar al servidor
            clientSocket = new Socket(ip, port);
            System.out.println("Client connectant al servidor...");

            in = new Scanner(clientSocket.getInputStream());
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Llegir missatge de conexio
            resultat = in.nextLine();
            System.out.println(resultat);

            // Envia missatge al servidor
            String msg = "a";
            out.println(msg);

            // Llegeix resposta del servidor
            while (in.hasNextLine()) {
                resultat = in.nextLine();
                System.out.println(resultat);
            }

        } catch (IOException ex) {
            Logger.getLogger(MainClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                // Cerrar recursos y el socket
                if (sc != null) sc.close();
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}