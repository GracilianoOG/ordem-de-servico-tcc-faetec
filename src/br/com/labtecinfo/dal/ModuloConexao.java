package br.com.labtecinfo.dal;

import java.sql.*;

public class ModuloConexao {

    // Método responsável por estabelecer a conexão com o banco de dados
    public static Connection conector() {
        Connection conexao = null;

        // Chamando o driver
        String driver = "com.mysql.jdbc.Driver";

        // Armazenando informações referentes ao banco
        String url = "jdbc:mysql://localhost:3306/dblabtecinfo"; // ?characterEncoding=utf-8
        String user = "root";
        String password = "";

        // Estabelecendo a conexão com o banco
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            return null;
        }
    }
}
