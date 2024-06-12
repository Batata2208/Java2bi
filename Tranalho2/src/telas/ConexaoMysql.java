package telas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoMysql {

    private static final String jdbcURL = "jdbc:mysql://localhost:3306/";
    private static final String dbName = "trade_db";
    private static final String username = "root";
    private static final String password = "1234";

    public ConexaoMysql() {}
    static Connection con = null;
    
    public static Connection Conectar() {
        

        try {
            // Carregar o driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Estabelecer a conexão inicial
            con = DriverManager.getConnection(jdbcURL, username, password);

            // Verificar e criar o banco de dados, se necessário
            criarDataBaseSeNaoExistir(con);

            // Estabelecer a conexão com o banco de dados específico
            con = DriverManager.getConnection(jdbcURL + dbName, username, password);
            System.out.println("Conexão estabelecida com sucesso com o banco de dados " + dbName + ".");

        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado.");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao servidor MySQL.");
            e.printStackTrace();
        }

        return con;
    }
    private static void criarDataBaseSeNaoExistir(Connection con) {
        try (Statement stmt = con.createStatement()) {
            String sql = "CREATE DATABASE IF NOT EXISTS " + dbName;
            stmt.executeUpdate(sql);
            System.out.println("Banco de dados " + dbName + " verificado/criado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro ao verificar/criar o banco de dados.");
            e.printStackTrace();
        }
    }

    public static void fecharConexao() {
        if (con != null) {
            try {
                con.close();
                System.out.println("Conexão fechada com sucesso.");
            } catch (SQLException e) {
                System.out.println("Erro ao fechar a conexão.");
                e.printStackTrace();
            }
        }
    }
}