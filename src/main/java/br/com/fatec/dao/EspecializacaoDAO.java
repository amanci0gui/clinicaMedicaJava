/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.dao;

import br.com.fatec.model.Especializacao;
import br.com.fatec.persistencia.Banco;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Aluno
 */
public class EspecializacaoDAO implements DAO<Especializacao>{
    private Especializacao especializacao;
    private PreparedStatement pst; //pacote java.sql
    //para conter os dados vindos do BD
    private ResultSet rs; //pacote java.sql

    @Override
    public boolean insere(Especializacao model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean remove(Especializacao model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean altera(Especializacao model) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Especializacao buscaID(Especializacao model) throws SQLException {
        especializacao = null;
        
        //Comando SELECT
        String sql = "SELECT * FROM espec WHERE idEspec = ?;";
        
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //troca a ?
        pst.setInt(1, model.getIdEspec());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //cria o objeto proprietario
            especializacao = new Especializacao();
            //move os dados do resultSet para o objeto proprietario
            especializacao.setIdEspec(rs.getInt("idEspec"));
            especializacao.setNomeEspec(rs.getString("nomeEspec"));
        }
        
        Banco.desconectar();
        
        return especializacao;
    }

    @Override
    public Collection<Especializacao> lista(String criterio) throws SQLException {
        Collection<Especializacao> listagem = new ArrayList<>();
        
        especializacao = null;
        //Comando SELECT
        String sql = "SELECT * FROM espec ";
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
            especializacao= new Especializacao();
            //move os dados do resultSet para o objeto veiculo
            especializacao.setIdEspec(rs.getInt("idEspec"));
            especializacao.setNomeEspec(rs.getString("nomeEspec"));
            
            //adicionar na coleção
            listagem.add(especializacao);
        }
        
        Banco.desconectar();
        
        return listagem;
    }
    
}
