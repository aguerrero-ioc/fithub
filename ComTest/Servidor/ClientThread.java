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

    /**
     * Es la classe encarregada de gestionar les peticions del client
     * i distribuir-les a les diferents classes en funció del tipus de petició que es tracti.
     *
     * @param client El socket creat a partir de la petició rebuda per part del client
     * @param con La connexió amb la base de dades
     * @throws IOException
     */
    public ClientThread(Socket client, Connection con) throws IOException {
        this.client = client;
        this.in = new Scanner(client.getInputStream());
        this.out = new PrintWriter(client.getOutputStream(), true);
        this.con = con;
    }

    @Override
    public void run() {
        String msg, rsp;

        out.println("Client connectat");

        // Si el client envia un missatge, el guarda a la variable
        if (in.hasNextLine()) {
            msg = in.nextLine();
            System.out.println(msg);
            //Com coneixem el format del missatge que ens enviara el client, el dividim per agafar les parts que necessitem
            String[] msgparts = msg.split(",");
            try {
                switch (msgparts[0]) {
                    //Crida la consulta per fer login
                    case "login":
                        rsp = new ConsultaLogin(con, msg).consultaLoginSQL();
                        break;
                    default:
                        rsp = "-1";
                }
                // Envia al client la resposta obtinguda
                out.println(rsp);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Es tanca la connexió
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
