package org.example.Servidor.consultes;

import java.sql.*;

public class ConsultaLogin {
    Connection con;
    String msg;

    public ConsultaLogin(Connection con, String msg) {
        this.con = con;
        this.msg = msg;
    }

    public String consultaLoginSQL() throws SQLException {
        String[] valorsPeticio = this.msg.split(",");
        //Comprovem que la peticio de login te el format correcte
        if (valorsPeticio.length == 3) {
            String consultaNomLogin = "SELECT \"Usuaris\".\"id\", \"Usuaris\".\"nomUsuari\", \"Usuaris\".\"passUsuari\",\"tipusUsuari\".\"tipus\",\"Usuaris\".\"correuUsuari\" " +
                    "FROM \"fithubSchema\".\"Usuaris\" INNER JOIN \"fithubSchema\".\"tipusUsuari\" ON \"Usuaris\".\"tipusUsuari\" = \"tipusUsuari\".\"id\" " +
                    "WHERE \"Usuaris\".\"correuUsuari\" = '" + valorsPeticio[1] + "'";

            Statement statement = con.createStatement();
            ResultSet resultat = statement.executeQuery(consultaNomLogin);

            if (resultat.next()) {
                if (resultat.getString(3).equals(valorsPeticio[2])) {
                    return resultat.getString(1) + "," + resultat.getString(4) + "," + resultat.getString(2);
                }
            }
        }
        return "-1";
    }
}
