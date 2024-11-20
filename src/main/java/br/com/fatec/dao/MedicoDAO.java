/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.dao;

import br.com.fatec.model.Especializacao;
import br.com.fatec.model.Medico;
import br.com.fatec.persistencia.Banco;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Pichau
 */
public class MedicoDAO implements DAO<Medico> {

    private Medico medico;
    private PreparedStatement pst;
    private ResultSet rs;
    
    @Override
    public boolean insere(Medico model) throws SQLException {
         String sql = "INSERT INTO medico (nomeMedico, crm, dataNasc, telefone, idEspec) "
        + "VALUES (?, ?, ?, ?, ?);";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNomeMedico());
        pst.setString(2, model.getCrm());
        pst.setDate(3, Date.valueOf(model.getDataNasc()));
        pst.setString(4, model.getTelefone());
        pst.setInt(5, model.getEspecializacao().getIdEspec());


        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public boolean remove(Medico model) throws SQLException {
        String sql = "DELETE FROM medico WHERE idMedico = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getIdMedico());
        
        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public boolean altera(Medico model) throws SQLException {
        String sql = "UPDATE medico SET nomeMedico = ?, "
        + "crm = ?, "
        + "dataNasc = ?, "
        + "idEspec = ?, "
        + "telefone = ? "
        + "WHERE idMedico = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNomeMedico());
        pst.setString(2, model.getCrm());
        pst.setDate(3, Date.valueOf(model.getDataNasc()));
        pst.setInt(4, model.getEspecializacao().getIdEspec());
        pst.setString(5, model.getTelefone());
        pst.setInt(6, model.getIdMedico());
        
        //executa o comando
        if(pst.executeUpdate() >= 1) { //tudo certo
            Banco.desconectar();
            return true;
        }
        else {
            Banco.desconectar();
            return false;
        }
    }

    @Override
    public Medico buscaID(Medico model) throws SQLException {
        medico = null;
        
        //Comando SELECT
        String sql = "SELECT * FROM medico WHERE idMedico = ?;";
        
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //troca a ?
        pst.setInt(1, model.getIdMedico());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //cria o objeto proprietario
            Especializacao e = new Especializacao();
            e.setIdEspec(rs.getInt("idEspec"));
            EspecializacaoDAO dao = new EspecializacaoDAO();
            e = dao.buscaID(e);
            
            
            medico = new Medico(e);
            
            //move os dados do resultSet para o objeto proprietario
            medico.setIdMedico(rs.getInt("idMedico"));
            medico.setNomeMedico(rs.getString("nomeMedico"));
            medico.setCrm(rs.getString("crm"));
            medico.setDataNasc(rs.getDate("dataNasc").toLocalDate());
            medico.setTelefone(rs.getString("telefone"));
        }
        
        Banco.desconectar();
        
        return medico;
    }

        @Override
    public Collection<Medico> lista(String criterio) throws SQLException {
        Collection<Medico> listagem = new ArrayList<>();
        String sql = "SELECT m.idMedico, m.nomeMedico, m.crm, m.dataNasc, m.telefone, e.idEspec, e.nomeEspec "
                   + "FROM Medico m "
                   + "INNER JOIN espec e ON m.idEspec = e.idEspec";

        // Adiciona filtro se houver
        if (criterio.length() != 0) {
            sql += " WHERE " + criterio;
        }

        // Conectar ao banco
        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);

        // Executa a consulta SQL
        rs = pst.executeQuery();

        // Processa os resultados
        while (rs.next()) {
            // Cria o objeto Especializacao com base nos dados da consulta
            Especializacao e = new Especializacao();
            e.setIdEspec(rs.getInt("idEspec"));
            e.setNomeEspec(rs.getString("nomeEspec"));

            // Cria o objeto Medico e associa a especialização
            medico = new Medico(e);
            medico.setIdMedico(rs.getInt("idMedico"));
            medico.setNomeMedico(rs.getString("nomeMedico"));
            medico.setCrm(rs.getString("crm"));
            medico.setDataNasc(rs.getDate("dataNasc").toLocalDate());
            medico.setTelefone(rs.getString("telefone"));

            // Adiciona o médico à lista
            listagem.add(medico);
        }

        // Desconectar do banco
        Banco.desconectar();

        return listagem;
    }
}
