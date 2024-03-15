import org.example.Servidor.consultes.ConsultaLogin;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginFallitOpcionsInsuficients {
    private static final String url = "jdbc:postgresql://localhost:5432/FitHub";
    static String bbddUsuari = "postgres";
    static String bbddPass = "ioc";
    static Connection con;

    @Test
    public void main() {
        try {
            String loginTestFallit = "login,client@fithub.es";
            Class.forName("org.postgresql.Driver");

            con = DriverManager.getConnection(url, bbddUsuari, bbddPass);
            System.out.println(new ConsultaLogin(con, loginTestFallit).consultaLoginSQL());

            assertEquals("-1", new ConsultaLogin(con, loginTestFallit).consultaLoginSQL());
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
