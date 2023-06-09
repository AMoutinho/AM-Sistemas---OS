/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.amsistemas.os.dal;

import java.sql.*;

/**
 *
 * @author André
 */
public class ModuloConexao {
    // Método para estabelecer conexão

    public static Connection conector() {
        java.sql.Connection conexao = null;
        String driver = "com.mysql.jdbc.Driver"; // Chama o driver do BD
        // Armazenando Informações referentes ao BD
        String url = "jdbc:mysql://localhost:3306/bdamsisos";
        String user = "root";
        String password = "";
        // Estabelecendo a conexão com o BD
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            // System.out.println(e); // Informa o ERRO que está ocorrendo ao estabelecer uma conexão com o banco de dados
            return null;
        }
    }
}
