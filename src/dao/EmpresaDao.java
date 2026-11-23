/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.Empresa;

/**
 *
 * @author f3l1p3
 */
public class EmpresaDao {
    
     public boolean inserir(Empresa empresa) {

        ConexaoBanco conexaoBanco = new ConexaoBanco();

        if (!conexaoBanco.conectar()) {
            return false;
        }

        Connection conexao = conexaoBanco.getConnection();

        String sql = "INSERT INTO empresa ("
                + "razao_social, nome_fantasia, cnpj, area_atuacao, "
                + "cep, logradouro, numero, complemento, bairro, cidade, uf, "
                + "email, telefone, linkedin, github, descricao, "
                + "email_acesso, senha"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, empresa.getRazaoSocial());
            stmt.setString(2, empresa.getNomeFantasia());
            stmt.setString(3, empresa.getCnpj());
            stmt.setString(4, empresa.getAreaAtuacao());

            stmt.setString(5, empresa.getCep());
            stmt.setString(6, empresa.getLogradouro());
            stmt.setString(7, empresa.getNumero());
            stmt.setString(8, empresa.getComplemento());
            stmt.setString(9, empresa.getBairro());
            stmt.setString(10, empresa.getCidade());
            stmt.setString(11, empresa.getUf());

            stmt.setString(12, empresa.getEmail());
            stmt.setString(13, empresa.getTelefone());

            stmt.setString(14, empresa.getLinkedin());
            stmt.setString(15, empresa.getGithub());
            stmt.setString(16, empresa.getDescricao());

            stmt.setString(17, empresa.getEmailAcesso());
            stmt.setString(18, empresa.getSenha());

            stmt.executeUpdate();

            stmt.close();
            conexao.close();

            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao cadastrar empresa: " + ex.getMessage()
            );

            return false;
        }
    }

    public Empresa buscarPorEmailAcesso(String emailAcesso) {

        ConexaoBanco conexaoBanco = new ConexaoBanco();

        if (!conexaoBanco.conectar()) {
            return null;
        }

        Connection conexao = conexaoBanco.getConnection();

        String sql = "SELECT * FROM empresa WHERE email_acesso = ?";

        try {
            PreparedStatement stmt = conexao.prepareStatement(sql);

            stmt.setString(1, emailAcesso);

            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {

                Empresa empresa = new Empresa();

                empresa.setRazaoSocial(resultado.getString("razao_social"));
                empresa.setNomeFantasia(resultado.getString("nome_fantasia"));
                empresa.setCnpj(resultado.getString("cnpj"));
                empresa.setAreaAtuacao(resultado.getString("area_atuacao"));

                empresa.setCep(resultado.getString("cep"));
                empresa.setLogradouro(resultado.getString("logradouro"));
                empresa.setNumero(resultado.getString("numero"));
                empresa.setComplemento(resultado.getString("complemento"));
                empresa.setBairro(resultado.getString("bairro"));
                empresa.setCidade(resultado.getString("cidade"));
                empresa.setUf(resultado.getString("uf"));

                empresa.setEmail(resultado.getString("email"));
                empresa.setTelefone(resultado.getString("telefone"));

                empresa.setLinkedin(resultado.getString("linkedin"));
                empresa.setGithub(resultado.getString("github"));
                empresa.setDescricao(resultado.getString("descricao"));

                empresa.setEmailAcesso(resultado.getString("email_acesso"));
                empresa.setSenha(resultado.getString("senha"));

                resultado.close();
                stmt.close();
                conexao.close();

                return empresa;
            }

            resultado.close();
            stmt.close();
            conexao.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao buscar empresa: " + ex.getMessage()
            );
        }

        return null;
    }
    
}
