/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Vaga;
import java.sql.ResultSet;
import java.util.ArrayList;
/**
 *
 * @author f3l1p3
 */


public class VagaDao {
      public boolean inserir(Vaga vaga) {

        ConexaoBanco conexaoBanco = new ConexaoBanco();

        if (!conexaoBanco.conectar()) {
            return false;
        }

        String sql = "INSERT INTO vaga "
                + "(id_empresa, titulo, descricao, salario, modalidade, nivel, localizacao) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conexao = conexaoBanco.getConnection();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setInt(1, vaga.getIdEmpresa());
            comando.setString(2, vaga.getTitulo());
            comando.setString(3, vaga.getDescricao());
            comando.setDouble(4, vaga.getSalario());
            comando.setString(5, vaga.getModalidade());
            comando.setString(6, vaga.getNivel());
            comando.setString(7, vaga.getLocalizacao());

            comando.executeUpdate();

            return true;

        } catch (SQLException ex) {

            System.out.println("Erro ao cadastrar vaga: " + ex.getMessage());

            return false;
        }
    }
      
    public ArrayList<Vaga> listarTodas() {

        ArrayList<Vaga> lista = new ArrayList<>();

        ConexaoBanco conexaoBanco = new ConexaoBanco();

        if (conexaoBanco.conectar()) {

            Connection conexao = conexaoBanco.getConnection();

           String sql = "SELECT v.*, e.nome_fantasia "
           + "FROM vaga v "
           + "INNER JOIN empresa e ON v.id_empresa = e.id_empresa";

            try {

                PreparedStatement comando = conexao.prepareStatement(sql);

                ResultSet resultado = comando.executeQuery();

                while (resultado.next()) {
                    
                    

                    Vaga vaga = new Vaga();
                    
                    vaga.setIdEmpresa(resultado.getInt("id_empresa"));
                    vaga.setNomeEmpresa(resultado.getString("nome_fantasia"));
                    vaga.setTitulo(resultado.getString("titulo"));
                    vaga.setDescricao(resultado.getString("descricao"));
                    vaga.setSalario(resultado.getDouble("salario"));
                    vaga.setModalidade(resultado.getString("modalidade"));
                    vaga.setNivel(resultado.getString("nivel"));
                    vaga.setLocalizacao(resultado.getString("localizacao"));
                    vaga.setIdVaga(resultado.getInt("id_vaga"));

                    
                    lista.add(vaga);
                }

                resultado.close();
                comando.close();
                conexao.close();

            } catch (SQLException ex) {

                System.out.println("Erro ao listar vagas: " + ex.getMessage());
            }
        }

        return lista;
    }
    
public ArrayList<Vaga> listarPorEmpresa(int idEmpresa) {

    ArrayList<Vaga> lista = new ArrayList<>();
    
String sql = "SELECT v.*, e.nome_fantasia "
           + "FROM vaga v "
           + "INNER JOIN empresa e ON v.id_empresa = e.id_empresa "
           + "WHERE v.id_empresa = ?";
    ConexaoBanco conexaoBanco = new ConexaoBanco();

    if (conexaoBanco.conectar()) {

        Connection conexao = conexaoBanco.getConnection();
        
        try {

            PreparedStatement comando = conexao.prepareStatement(sql);

            comando.setInt(1, idEmpresa);

            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {

                Vaga vaga = new Vaga();

                vaga.setIdVaga(resultado.getInt("id_vaga"));
                vaga.setIdEmpresa(resultado.getInt("id_empresa"));
                vaga.setNomeEmpresa(resultado.getString("nome_fantasia"));
                vaga.setTitulo(resultado.getString("titulo"));
                vaga.setDescricao(resultado.getString("descricao"));
                vaga.setSalario(resultado.getDouble("salario"));
                vaga.setModalidade(resultado.getString("modalidade"));
                vaga.setNivel(resultado.getString("nivel"));
                vaga.setLocalizacao(resultado.getString("localizacao"));

                lista.add(vaga);
            }

            resultado.close();
            comando.close();
            conexao.close();

        } catch (SQLException ex) {

            System.out.println("Erro ao buscar vagas da empresa: " + ex.getMessage());
        }
    }

    return lista;
}


}
