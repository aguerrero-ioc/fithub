package org.example.Servidor;

import org.example.Servidor.consultes.ConsultaLogin;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

class ClientThread extends Thread {
    private final Socket client;
    private final Scanner in;
    private final PrintWriter out;

    Connection con;

    public ClientThread(Socket client, Connection con) throws IOException {
        this.client = client;
        this.in = new Scanner(client.getInputStream());
        this.out = new PrintWriter(client.getOutputStream(), true);
        this.con = con;
    }

    @Override
    public void run() {
        //Missatge d'intercanvi amb el client
        String msg;
        String rsp;

        // Envia missatge de conectat al client
        out.println("Client connectat");

        // Llegeix missatge enviat pel client
        if (in.hasNextLine()) {
            msg = in.nextLine();

            String[] msgparts = msg.split(",");
            try {
                switch (msgparts[0]) {
                    //Invoca la consulta per fer login
                    case "login":
                        rsp = new ConsultaLogin(con, msg).consultaLoginSQL();
                        break;
                    default:
                        rsp = "-1";
                }
                // Retorna resposta
                out.println(rsp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Tanca la conexio
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
