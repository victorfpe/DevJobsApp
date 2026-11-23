
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * to change this license
 */
package dao;

import modelo.Cadastro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author f3l1p3
 */
public class CadastroDao implements DaoGenerica<Cadastro> {

    private ConexaoBanco conexao;

    public CadastroDao() {
        this.conexao = new ConexaoBanco();
    }

    @Override
    public void inserir(Cadastro cadastro) {
        
    }

    @Override
    public void alterar(Cadastro cadastro) {
    }

    @Override
    public void excluir() {
    }

    public void excluirID(int id) {
    }

    @Override
    public ArrayList<Cadastro> consultar() {
        return new ArrayList<>();
    }

    public ArrayList<Cadastro> consultar(String str) {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<Cadastro> dashboard() {
        return new ArrayList<>();
    }
    
    public String buscarSenhaPorEmail(String emailAcesso) {

    ConexaoBanco conexaoBanco = new ConexaoBanco();

    if (!conexaoBanco.conectar()) {
        return null;
    }

    String sql = "SELECT senha FROM candidato WHERE email_acesso = ?";

    try {

        Connection conexao = conexaoBanco.getConnection();

        PreparedStatement comando = conexao.prepareStatement(sql);

        comando.setString(1, emailAcesso);

        ResultSet resultado = comando.executeQuery();

        if (resultado.next()) {

            String senhaHash = resultado.getString("senha");

            resultado.close();
            comando.close();
            conexao.close();

            return senhaHash;
        }

        resultado.close();
        comando.close();
        conexao.close();

        return null;

    } catch (SQLException e) {

        e.printStackTrace();
        return null;
    }
}
   
}


