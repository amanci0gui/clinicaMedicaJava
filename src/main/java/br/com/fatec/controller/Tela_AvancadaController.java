/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.dao.ClientesDAO;
import br.com.fatec.dao.ConsultaDAO;
import br.com.fatec.dao.HorarioDAO;
import br.com.fatec.dao.MedicoDAO;
import br.com.fatec.model.Clientes;
import br.com.fatec.model.Consulta;
import br.com.fatec.model.Horario;
import br.com.fatec.model.Medico;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Pichau
 */
public class Tela_AvancadaController implements Initializable {

    @FXML
    private ComboBox<Horario> cmbHorario;
    @FXML
    private ComboBox<Clientes> cmbPaciente;
    @FXML
    private DatePicker dataConsulta;
    @FXML
    private ComboBox<Medico> cmbMed;
    @FXML
    private TableView<Consulta> tviewConsultas;
    @FXML
    private TableColumn<Consulta, Integer> colIdConsul;
    @FXML
    private TableColumn<Consulta, String> colPaciente;
    @FXML
    private TableColumn<Consulta, String> colMed;
    @FXML
    private TableColumn<Consulta, LocalDate> colData;
    @FXML
    private TableColumn<Consulta, LocalTime> colHorario;
    @FXML
    private Button btnLimparPaciente;
    @FXML
    private Button btnLimparMedico;
    @FXML
    private Button btnLimparHorario;
    @FXML
    private Button btnLimparData;
    @FXML
    private ImageView logo; // logo para voltar para tela principal
    
    private ConsultaDAO consultaDAO;
    private MedicoDAO medDAO;
    private ClientesDAO cliDAO;
    private HorarioDAO horDAO;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        consultaDAO = new ConsultaDAO();
        medDAO = new MedicoDAO();
        cliDAO = new ClientesDAO();
        horDAO = new HorarioDAO();
        logo.setOnMouseClicked(this::mostrarTelaPrincipal);
        fazerLigacao();
        // Carregar dados nas ComboBoxes
        carregarComboBoxes();
    tviewConsultas.setItems(preencherTabela());
        // Atualizar tabela quando algum valor for alterado
        cmbPaciente.valueProperty().addListener((observable, oldValue, newValue) -> tviewConsultas.setItems(preencherTabela()));
        cmbMed.valueProperty().addListener((observable, oldValue, newValue) -> tviewConsultas.setItems(preencherTabela()));
        cmbHorario.valueProperty().addListener((observable, oldValue, newValue) -> tviewConsultas.setItems(preencherTabela()));
        dataConsulta.valueProperty().addListener((observable, oldValue, newValue) -> tviewConsultas.setItems(preencherTabela()));
        
    }
    
    public void mostrarTelaPrincipal(MouseEvent event) {
        try {
            // Troca a tela chamando o método setRoot da classe App
            App.setRoot("br/com/fatec/view/Principal");
        } catch (Exception ex) {
            mensagem("Erro ao tentar voltar para tela principal: " + ex.getMessage());
        }
    }
    
    private void carregarComboBoxes() {
        // Carregar os itens das ComboBoxes
        // Exemplo de carregamento de ComboBox de Médico
        try{
            ObservableList<Medico> medicos = FXCollections.observableArrayList();
            medicos.addAll(medDAO.lista(""));
            cmbMed.setItems(medicos);
        } catch (SQLException ex){
            mensagem(ex.getMessage());
        }
       
        
        try{
        // Exemplo de carregamento de ComboBox de Horários
        ObservableList<Horario> horarios = FXCollections.observableArrayList();
        horarios.addAll(horDAO.lista(""));
        cmbHorario.setItems(horarios);
        } catch (SQLException ex){
            mensagem(ex.getMessage());
        }
        
        try{
        // Exemplo de carregamento de ComboBox de Clientes
        ObservableList<Clientes> clientes = FXCollections.observableArrayList();
        clientes.addAll(cliDAO.lista(""));
        cmbPaciente.setItems(clientes);
        } catch(SQLException ex){
            mensagem(ex.getMessage());
        }
    }

    private ObservableList<Consulta> preencherTabela() {
        // Criar a string de filtro
        StringBuilder criterio = new StringBuilder();

        if (cmbPaciente.getValue() != null) {
            criterio.append("c.idCliente = ").append(cmbPaciente.getValue().getIdCliente()).append(" AND ");
        }
        if (cmbMed.getValue() != null) {
            criterio.append("m.idMedico = ").append(cmbMed.getValue().getIdMedico()).append(" AND ");
        }
        if (cmbHorario.getValue() != null) {
            criterio.append("h.idHorario = ").append(cmbHorario.getValue().getIdHorario()).append(" AND ");
        }
        if (dataConsulta.getValue() != null) {
            criterio.append("co.dataConsulta = '").append(dataConsulta.getValue()).append("' AND ");
        }

        // Remover o "AND" final
        if (criterio.length() > 0) {
            criterio.setLength(criterio.length() - 4);
        }

        ConsultaDAO dao = new ConsultaDAO();
        ObservableList<Consulta> consultaLista = FXCollections.observableArrayList();

        try {
            // Preenche a tabela com todos os médicos
            consultaLista.addAll(dao.lista(criterio.toString()));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenchendo Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }

        return consultaLista;
    }

    @FXML
    private void btnLimparPaciente_Click(ActionEvent event) {
        cmbPaciente.setValue(null);
        tviewConsultas.setItems(preencherTabela());
    }

    @FXML
    private void btnLimparMedico_Click(ActionEvent event) {
        cmbMed.setValue(null);
        tviewConsultas.setItems(preencherTabela());
    }

    @FXML
    private void btnLimparHorario_Click(ActionEvent event) {
        cmbHorario.setValue(null);
        tviewConsultas.setItems(preencherTabela());
    }

    @FXML
    private void btnLimparData_Click(ActionEvent event) {
        dataConsulta.setValue(null);
        tviewConsultas.setItems(preencherTabela());
    }
    
    private void mensagem(String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensagem");
        alerta.setHeaderText(msg);
        alerta.setContentText("");

        alerta.showAndWait(); //exibe a mensage
    }
    
    
    private void fazerLigacao(){
        colData.setCellValueFactory(new PropertyValueFactory<>("dataConsul"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("Horario"));
        colIdConsul.setCellValueFactory(new PropertyValueFactory<>("idConsul"));
        colMed.setCellValueFactory(new PropertyValueFactory<>("Medico"));
        colPaciente.setCellValueFactory(new PropertyValueFactory<>("Clientes"));
        
        colHorario.setCellValueFactory(cellData -> {
        // Retornando um SimpleStringProperty com o nome da especialização
        return new SimpleObjectProperty(cellData.getValue().getHorario().getHorario());
    });
        colMed.setCellValueFactory(cellData -> {
        // Retornando um SimpleStringProperty com o nome da especialização
        return new SimpleStringProperty(cellData.getValue().getMedico().getNomeMedico());
    });
        colPaciente.setCellValueFactory(cellData -> {
        // Retornando um SimpleStringProperty com o nome da especialização
        return new SimpleStringProperty(cellData.getValue().getCliente().getNomeCliente());
    });
    }
    
}