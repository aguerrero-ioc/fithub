/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.example.Servidor;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Scanner;

public class MainServer {

    static int port = 8080;
    static String ip = "192.168.0.47";

    private static final String url = "jdbc:postgresql://localhost:5432/FitHub";
    static String bbddUsuari = "postgres";
    static String bbddPass = "ioc";
    static Connection con;

    /**
     * Aquest es el métode principal del servidor de l'aplicació,
     * rep les peticions del client i les envia cap a la classe que gestiona el tipus de peticio rebuda
     * @param args
     */
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");

            con = DriverManager.getConnection(url,bbddUsuari,bbddPass);



            //ResultSet rs = statement.executeQuery("SELECT * FROM \"fithubSchema\".\"Usuaris\"");

            System.out.println("Connexio aconseguida.");
            /*while(rs.next()){
                System.out.println(rs.getString("passUsuari"));
                System.out.println(rs.getString(1));

            }*/

            InetAddress direccionIP = InetAddress.getByName(ip); // Cambia a la IP que deseas usar
            InetSocketAddress direccion = new InetSocketAddress(direccionIP, port);
            // Crea el socket del servidor vinculándolo a la dirección y puerto especificados
            ServerSocket server = new ServerSocket();
            server.bind(direccion);

            while (true) {
                System.out.println("Esperant client...");
                //Accepta conexio del client
                Socket socket = server.accept();
                System.out.println("Client connectat " + socket.getInetAddress().toString());

                new ClientThread(socket, con).start();  // Inicia un fil per a la conexio del client
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

