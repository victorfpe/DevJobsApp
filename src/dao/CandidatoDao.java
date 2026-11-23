/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Candidato;
import java.sql.ResultSet;

/**
 *
 * @author f3l1p3
 */
public class CandidatoDao {
    public boolean inserir(Candidato candidato) {

        ConexaoBanco conexaoBanco = new ConexaoBanco();

        if (!conexaoBanco.conectar()) {
            return false;
        }

        String sql = "INSERT INTO candidato ("
                + "nome, cpf, nascimento, email, telefone, sexualidade, "
                + "cep, logradouro, numero, complemento, bairro, cidade, uf, "
                + "curso, instituicao, escolaridade, cargo_desejado, "
                + "area_interesse, nivel_profissional, tipo_trabalho, "
                + "resumo, linguagens, github, linkedin, "
                + "email_acesso, senha"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
                + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection conexao = conexaoBanco.getConnection();

            PreparedStatement comando = conexao.prepareStatement(sql);

            comando.setString(1, candidato.getNome());
            comando.setString(2, candidato.getCpf());
            comando.setString(3, candidato.getNascimento());
            comando.setString(4, candidato.getEmail());
            comando.setString(5, candidato.getTelefone());
            comando.setString(6, candidato.getSexualidade());

            comando.setString(7, candidato.getCep());
            comando.setString(8, candidato.getLogradouro());
            comando.setString(9, candidato.getNumero());
            comando.setString(10, candidato.getComplemento());
            comando.setString(11, candidato.getBairro());
            comando.setString(12, candidato.getCidade());
            comando.setString(13, candidato.getUf());

            comando.setString(14, candidato.getCurso());
            comando.setString(15, candidato.getInstituicao());
            comando.setString(16, candidato.getEscolaridade());

            comando.setString(17, candidato.getCargoDesejado());
            comando.setString(18, candidato.getAreaInteresse());
            comando.setString(19, candidato.getNivelProfissional());
            comando.setString(20, candidato.getTipoTrabalho());
            comando.setString(21, candidato.getResumo());
            comando.setString(22, candidato.getLinguagens());

            comando.setString(23, candidato.getGithub());
            comando.setString(24, candidato.getLinkedin());
            comando.setString(25, candidato.getEmailAcesso());
            comando.setString(26, candidato.getSenha());

            int resultado = comando.executeUpdate();

            comando.close();
            conexao.close();

            return resultado > 0;

       } catch (SQLException e) {
    e.printStackTrace();
    javax.swing.JOptionPane.showMessageDialog(
            null,
            "Erro SQL: " + e.getMessage(),
            "Detalhes do erro",
            javax.swing.JOptionPane.ERROR_MESSAGE
    );
    return false;
       }
  
    } 
    
    public Candidato buscarPorEmailAcesso(String emailAcesso) {

    ConexaoBanco conexaoBanco = new ConexaoBanco();

    if (!conexaoBanco.conectar()) {
        return null;
    }

    String sql = "SELECT * FROM candidato WHERE email_acesso = ?";

    try {
        Connection conexao = conexaoBanco.getConnection();

        PreparedStatement comando = conexao.prepareStatement(sql);
        comando.setString(1, emailAcesso);

        ResultSet resultado = comando.executeQuery();

        if (resultado.next()) {

            Candidato candidato = new Candidato();

            candidato.setNome(resultado.getString("nome"));
            candidato.setCpf(resultado.getString("cpf"));
            candidato.setNascimento(resultado.getString("nascimento"));
            candidato.setEmail(resultado.getString("email"));
            candidato.setTelefone(resultado.getString("telefone"));
            candidato.setSexualidade(resultado.getString("sexualidade"));

            candidato.setCep(resultado.getString("cep"));
            candidato.setLogradouro(resultado.getString("logradouro"));
            candidato.setNumero(resultado.getString("numero"));
            candidato.setComplemento(resultado.getString("complemento"));
            candidato.setBairro(resultado.getString("bairro"));
            candidato.setCidade(resultado.getString("cidade"));
            candidato.setUf(resultado.getString("uf"));

            candidato.setCurso(resultado.getString("curso"));
            candidato.setInstituicao(resultado.getString("instituicao"));
            candidato.setEscolaridade(resultado.getString("escolaridade"));

            candidato.setCargoDesejado(resultado.getString("cargo_desejado"));
            candidato.setAreaInteresse(resultado.getString("area_interesse"));
            candidato.setNivelProfissional(resultado.getString("nivel_profissional"));
            candidato.setTipoTrabalho(resultado.getString("tipo_trabalho"));
            candidato.setResumo(resultado.getString("resumo"));
            candidato.setLinguagens(resultado.getString("linguagens"));

            candidato.setGithub(resultado.getString("github"));
            candidato.setLinkedin(resultado.getString("linkedin"));

            candidato.setEmailAcesso(resultado.getString("email_acesso"));

            resultado.close();
            comando.close();
            conexao.close();

            return candidato;
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
