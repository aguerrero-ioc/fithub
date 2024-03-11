package org.example.Servidor.consultes;

import java.sql.Connection;

public class ConsultaLogin {

    String consultaLoginst = "SELECT \"Usuaris\".\"id\", \"Usuaris\".\"nomUsuari\", \"Usuaris\".\"passUsuari\",\"tipusUsuari\".\"tipus\""+
    "FROM \"fithubSchema\".\"Usuaris\""+
    "INNER JOIN \"fithubSchema\".\"tipusUsuari\" ON \"Usuaris\".\"tipusUsuari\" = \"tipusUsuari\".id;\"";

    Connection con;
    public ConsultaLogin(Connection con) {
        this.con = con;
    }
}
