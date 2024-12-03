/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.dao;

import br.com.fatec.model.Clientes;
import br.com.fatec.model.Consulta;
import br.com.fatec.model.Especializacao;
import br.com.fatec.model.Horario;
import br.com.fatec.model.Medico;
import br.com.fatec.persistencia.Banco;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Pichau
 */
public class ConsultaDAO implements DAO<Consulta>{
    
    private Consulta consulta;
    private PreparedStatement pst;
    private ResultSet rs;
    
    @Override
    public boolean insere(Consulta model) throws SQLException {
         if (verificaConsultaExistente(model.getDataConsul(), model.getMedico(), model.getHorario(), model.getCliente())) {
             return false; // Se já existe uma consulta, retorna falso
         } else {
         }
        
        String sql = "INSERT INTO consulta (idCliente, idMedico, idHorario, dataConsulta, sintomas) "
        + "VALUES (?, ?, ?, ?, ?);";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getCliente().getIdCliente());
        pst.setInt(2, model.getMedico().getIdMedico());
        pst.setInt(3, model.getHorario().getIdHorario());
        pst.setDate(4, Date.valueOf(model.getDataConsul()));
        pst.setString(5, model.getSintomas());


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
    public boolean remove(Consulta model) throws SQLException {
        String sql = "DELETE FROM consulta WHERE idConsulta = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getIdConsulta());
        
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
    public boolean altera(Consulta model) throws SQLException {
         if (verificaConsultaExistente(model.getDataConsul(), model.getMedico(), model.getHorario(), model.getCliente())) {
             return false; // Se já existe uma consulta, retorna falso
         } else {
         }
        
        String sql = "UPDATE consulta SET idCliente = ?, "
        + "idMedico = ?, "
        + "idHorario = ?, "
        + "dataConsulta = ?, "
        + "sintomas = ? "
        + "WHERE idConsulta = ?;";
        
        //Abre a conexao
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //coloca os valores dentro do comando
        //substitui as '?' por dados
        pst.setInt(1, model.getCliente().getIdCliente());
        pst.setInt(2, model.getMedico().getIdMedico());
        pst.setInt(3, model.getHorario().getIdHorario());
        pst.setDate(4, Date.valueOf(model.getDataConsul()));
        pst.setString(5, model.getSintomas());
        pst.setInt(6, model.getIdConsulta());
        
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
    public Consulta buscaID(Consulta model) throws SQLException {
        consulta = null;
        
        //Comando SELECT
        String sql = "SELECT * FROM consulta WHERE idConsulta = ?;";
        
        //conecta ao banco
        Banco.conectar();
        
        //cria o comando preparado
        pst = Banco.obterConexao().prepareStatement(sql);
        
        //troca a ?
        pst.setInt(1, model.getIdConsulta());
        
        //Executa o comando SELECT
        rs = pst.executeQuery();
        
        //le o próximo regitro
        if(rs.next()) { //achou 1 registro
            //cria o objeto Cliente
            Clientes c = new Clientes();
            c.setIdCliente(rs.getInt("idCliente"));
            ClientesDAO dao = new ClientesDAO();
            c = dao.buscaID(c);
            
            
            //cria o objeto Medico
            Medico m = new Medico(null);
            m.setIdMedico(rs.getInt("idMedico"));
            MedicoDAO dao2 = new MedicoDAO();
            m = dao2.buscaID(m);
            
            //Cria o objeto Horario
            Horario h = new Horario();
            h.setIdHorario(rs.getInt("idHorario"));
            HorarioDAO dao3 = new HorarioDAO();
            h = dao3.buscaID(h);
            
            
            consulta = new Consulta(c, m, h);
            
            //move os dados do resultSet para o objeto proprietario
            consulta.setIdConsulta(rs.getInt("idConsulta"));
            consulta.setSintomas(rs.getString("sintomas"));
            consulta.setDataConsul(rs.getDate("dataConsulta").toLocalDate());
        }
        
        Banco.desconectar();
        
        return consulta;
    }

    @Override
    public Collection<Consulta> lista(String criterio) throws SQLException {
        Collection<Consulta> listagem = new ArrayList<>();
        String sql = "SELECT co.idConsulta, co.dataConsulta, co.sintomas, "
                   + "m.idMedico, m.nomeMedico, "
                   + "c.idCliente, c.nomeCliente, "
                   + "h.idHorario, h.horario,"
                   + "e.idEspec, e.nomeEspec "
                   + "FROM consulta co "
                   + "INNER JOIN medico m ON co.idMedico = m.idMedico "
                   + "INNER JOIN clientes c ON co.idCliente = c.idCliente "
                   + "INNER JOIN horarios h ON co.idHorario = h.idHorario "
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
            // Cria o objeto Clientes com base nos dados da consulta
            Clientes c = new Clientes();
            c.setIdCliente(rs.getInt("idCliente"));
            c.setNomeCliente(rs.getString("nomeCliente"));
            
            Especializacao e = new Especializacao();
            e.setIdEspec(rs.getInt("idEspec"));
            e.setNomeEspec(rs.getString("nomeEspec")); 
            
            
            Medico m = new Medico(e);
            m.setIdMedico(rs.getInt("idMedico"));
            m.setNomeMedico(rs.getString("nomeMedico"));
            
            Horario h = new Horario();
            h.setIdHorario(rs.getInt("idHorario"));
            h.setHorario(rs.getTime("horario").toLocalTime());
            

            // Cria o objeto Medico e associa a especialização
            consulta = new Consulta(c, m, h);
            consulta.setIdConsulta(rs.getInt("idConsulta"));
            consulta.setDataConsul(rs.getDate("dataConsulta").toLocalDate());
            consulta.setSintomas(rs.getString("sintomas"));

            // Adiciona o médico à lista
            listagem.add(consulta);
        }

        // Desconectar do banco
        Banco.desconectar();

        return listagem;
    }
    
    public boolean verificaConsultaExistente(LocalDate dataConsulta, Medico medico, Horario horario, Clientes cliente) throws SQLException {
        String sql = "SELECT COUNT(*) FROM consulta WHERE dataConsulta = ? AND idMedico = ? AND idHorario = ?;";

        Banco.conectar();
        pst = Banco.obterConexao().prepareStatement(sql);

        // Corrigido o uso do LocalTime e Medico, e Horario
        pst.setDate(1, Date.valueOf(dataConsulta));  // A data da consulta
        pst.setInt(2, medico.getIdMedico());         // O id do médico 
        pst.setInt(3, horario.getIdHorario());       // O id do horário 
        pst.setInt(4, cliente.getIdCliente());       // O id do cliente

        rs = pst.executeQuery();

        // Se já existir uma consulta, o resultado será maior que 0
        rs.next();
        int count = rs.getInt(1);

        Banco.desconectar();

        return count > 0;  // Retorna true se a consulta já existir
    }
}