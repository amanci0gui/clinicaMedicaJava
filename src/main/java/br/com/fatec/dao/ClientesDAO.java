/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.dao;

import br.com.fatec.model.Clientes;
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
public class ClientesDAO implements DAO<Clientes>{
    private Clientes cliente;
    
    private PreparedStatement pst;
    private ResultSet rs;

    @Override
    public boolean insere(Clientes model) throws SQLException {
        String sql = "INSERT INTO clientes (nomeCliente, cpf, dataNasc, convOuPart, planoConv, telefone) "
        + "VALUES (?, ?, ?, ?, ?, ?);";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNomeCliente());
        pst.setString(2, model.getCpf());
        pst.setDate(3, Date.valueOf(model.getDataNasc()));
        pst.setString(4, model.getConvOuPart());
        pst.setString(5, model.getPlanoConv());
        pst.setString(6, model.getTelefone());


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
    public boolean remove(Clientes model) throws SQLException {
         String sql = "DELETE FROM clientes WHERE idCliente = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getIdCliente());
        
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
    public boolean altera(Clientes model) throws SQLException {
        String sql = "UPDATE clientes SET nomeCliente = ?, "
        + "cpf = ?, "
        + "dataNasc = ?, "
        + "convOuPart = ?, "
        + "planoConv = ?, "
        + "telefone = ? "
        + "WHERE idCliente = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setString(1, model.getNomeCliente());
        pst.setString(2, model.getCpf());
        pst.setDate(3, Date.valueOf(model.getDataNasc()));
        pst.setString(4, model.getConvOuPart());
        pst.setString(5, model.getPlanoConv());
        pst.setString(6, model.getTelefone());
        pst.setInt(7, model.getIdCliente());
        
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
    public Clientes buscaID(Clientes model) throws SQLException {
        cliente = null;
        
        //Comando SELECT
        String sql = "SELECT * FROM clientes WHERE idCliente = ?;";
        
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //troca a ?
        pst.setInt(1, model.getIdCliente());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //cria o objeto proprietario
            cliente = new Clientes();
            //move os dados do resultSet para o objeto proprietario
            cliente.setIdCliente(rs.getInt("idCliente"));
            cliente.setNomeCliente(rs.getString("nomeCliente"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setDataNasc(rs.getDate("dataNasc").toLocalDate());
            cliente.setConvOuPart(rs.getString("convOuPart"));
            cliente.setPlanoConv(rs.getString("planoConv"));
            cliente.setTelefone(rs.getString("telefone"));
        }
        
        Banco.desconectar();
        
        return cliente;
    }

    @Override
    public Collection<Clientes> lista(String criterio) throws SQLException {
        Collection<Clientes> listagem = new ArrayList<>();
        
        cliente = null;
        
        //Comando SELECT
        String sql = "SELECT * FROM clientes ";
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
            cliente = new Clientes();
            //move os dados do resultSet para o objeto cliente
            cliente.setIdCliente(rs.getInt("idCliente"));
            cliente.setNomeCliente(rs.getString("nomeCliente"));
            cliente.setCpf(rs.getString("cpf"));
            cliente.setDataNasc(rs.getDate("dataNasc").toLocalDate());
            cliente.setConvOuPart(rs.getString("convOuPart"));
            cliente.setPlanoConv(rs.getString("planoConv"));
            cliente.setTelefone(rs.getString("telefone"));
            
            //adicionar na coleção
            listagem.add(cliente);
        }
        
        Banco.desconectar();
        
        return listagem;
    }
}

    
