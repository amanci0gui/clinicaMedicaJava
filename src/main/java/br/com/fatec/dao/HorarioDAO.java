/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.dao;

import br.com.fatec.model.Horario;
import br.com.fatec.persistencia.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Pichau
 */
public class HorarioDAO implements DAO<Horario> {
    
    private Horario horario;
    private PreparedStatement pst; //pacote java.sql
    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql

    @Override
    public boolean insere(Horario model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean remove(Horario model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean altera(Horario model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Horario buscaID(Horario model) throws SQLException {
        horario = null;
        
        //Comando SELECT
        String sql = "SELECT * FROM horarios WHERE idHorario = ?;";
        
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //troca a ?
        pst.setInt(1, model.getIdHorario());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //cria o objeto proprietario
            horario = new Horario();
            //move os dados do resultSet para o objeto proprietario
            horario.setIdHorario(rs.getInt("idHorario"));
            horario.setHorario(rs.getTime("horario").toLocalTime());
        }
        
        Banco.desconectar();
        
        return horario;
    }

    @Override
    public Collection<Horario> lista(String criterio) throws SQLException {
        Collection<Horario> listagem = new ArrayList<>();
        
        horario = null;
        //Comando SELECT
        String sql = "SELECT * FROM horarios ";
        //colocar filtro ou nao
        if(criterio.length() != 0) {
            sql += "WHERE " + criterio;
        }
        
        //conecta ao banco
        Banco.conectar();
        
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        while(rs.next()) { //achou 1 registro
            //cria o objeto veiculo
            horario = new Horario();
            //move os dados do resultSet para o objeto veiculo
            horario.setIdHorario(rs.getInt("idHorario"));
            horario.setHorario(rs.getTime("horario").toLocalTime());
            
            //adicionar na coleção
            listagem.add(horario);
        }
        
        Banco.desconectar();
        
        return listagem;
    }
    
}
