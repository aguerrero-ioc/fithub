
import org.example.Servidor.consultes.ConsultaLogin;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginCorrecteTest {

    private static final String url = "jdbc:postgresql://localhost:5432/FitHub";
    static String bbddUsuari = "postgres";
    static String bbddPass = "ioc";
    static Connection con;

    @Test
    public void main() {
        try {
            String loginTestCorrecte = "login,client@fithub.es,passClient";
            Class.forName("org.postgresql.Driver");

            con = DriverManager.getConnection(url, bbddUsuari, bbddPass);
            System.out.println(new ConsultaLogin(con, loginTestCorrecte).consultaLoginSQL());


            assertEquals("2,normal,Client", new ConsultaLogin(con, loginTestCorrecte).consultaLoginSQL());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
