package org.example.Servidor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

class ClientThread extends Thread {
    private final Socket client;
    private final Scanner in;
    private final PrintWriter out;

    public ClientThread(Socket client) throws IOException {
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
