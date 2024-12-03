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
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Pichau
 */
public class Tela_ConsultasController implements Initializable {

    @FXML
    private TextArea txtSintomas;
    @FXML
    private ComboBox<Medico> cmbMed;
    @FXML
    private DatePicker dataConsulta;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnExcluir;
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
    private TextField txtIdCons;
    @FXML
    private Button btnPesquisar;
    @FXML
    private ComboBox<Clientes> cmbPaciente;
    @FXML
    private ComboBox<Horario> cmbHorario;
    @FXML
    private ImageView logo; // logo para voltar para tela principal
    
    private Consulta consulta;
    private boolean incluindo = true;
    
    private ObservableList<Medico> listaMedicos =  
            FXCollections.observableArrayList();
    
    private ObservableList<Clientes> listaClientes =  
            FXCollections.observableArrayList();

    private ObservableList<Horario> listaHorarios =  
            FXCollections.observableArrayList();
    
    //VARIAVEIS AUXILIARES
    private ConsultaDAO consultaDAO = new ConsultaDAO();
    @FXML
    private TableView<Consulta> tviewConsultas;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        carregarComboCliente();
        carregarComboHorario();
        carregarComboMedico();
        
        fazerLigacao();
        tviewConsultas.setItems(preencherTabela());
        tableView_clique();
        logo.setOnMouseClicked(this::mostrarTelaPrincipal);
    }    

    @FXML
    private void btnSalvar_Click(ActionEvent event) {
        // Verifica se os dados estão preenchidos
        if (!validarDados()) {
            return; // Se os dados não forem válidos, não continua a operação
        }

        // Carrega os dados do modelo a partir da interface gráfica
        consulta = carregar_Model();

        try {
            if (incluindo) { // Se a operação é de inclusão
                if (consultaDAO.insere(consulta)) {
                    mensagem("Consulta incluída com sucesso!"); // Ajustado para "Médico"
                    limparDados(); // Limpa os campos após inclusão
                    tviewConsultas.setItems(preencherTabela());
                } else {
                    mensagem("Já existe uma consulta neste horário com esse médico!");
                }
            } else { // Se a operação é de alteração
                if (consultaDAO.altera(consulta)) {
                    mensagem("Consulta alterada com sucesso!"); // Ajustado para "Médico"
                    limparDados(); // Limpa os campos após alteração
                    ObservableList<Consulta> listaAtualizada = preencherTabela(); // Obtenha os dados atualizados
                    tviewConsultas.getItems().clear(); // Limpa os itens antigos
                    tviewConsultas.setItems(listaAtualizada); // Seta a nova lista
                } else {
                    mensagem("Já existe uma consulta neste horário com esse médico!");
                }
            }
        } catch (SQLException ex) {
            // Exibe a mensagem de erro caso ocorra alguma exceção durante a gravação no banco
            mensagem("Erro na Gravação: " + ex.getMessage());
            ex.printStackTrace(); // Adiciona um log da exceção
        }
    }

    @FXML
    private void btnExcluir_Click(ActionEvent event) {
        consulta = new Consulta(null, null, null);
        consulta.setIdConsulta(Integer.parseInt(txtIdCons.getText()));
        
        try {
            if(consultaDAO.remove(consulta)) {
                mensagem("Consulta excluída com Sucesso !!!");
                limparDados();
                tviewConsultas.setItems(preencherTabela());
            } 
            else {
                mensagem("Ocorreu algum erro para exclusão");
            }
        } catch (SQLException ex) {
            mensagem("Erro de Exclusão\n" + ex.getMessage());
        }
    }

    @FXML
    private void btnPesquisar_Click(ActionEvent event) {
        consulta = new Consulta(null, null, null);
        consulta.setIdConsulta(Integer.parseInt(txtIdCons.getText()));
        
        try{
            consulta = consultaDAO.buscaID(consulta);
            if(consulta!=null){
                carregar_View(consulta);
                incluindo = false;
            } else{
                mensagem("Consulta não encontrada");
            }
        } catch (SQLException ex) {
            mensagem("Erro na procura da Consulta: " + 
                    ex.getMessage());
        }
    }
    
    public void mostrarTelaPrincipal(MouseEvent event) {
        try {
            // Troca a tela chamando o método setRoot da classe App
            App.setRoot("br/com/fatec/view/Principal");
        } catch (Exception ex) {
            mensagem("Erro ao tentar voltar para tela principal: " + ex.getMessage());
        }
    }
    
    private void mensagem(String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensagem");
        alerta.setHeaderText(msg);
        alerta.setContentText("");

        alerta.showAndWait(); //exibe a mensage
    }
    
    private void carregarComboMedico(){
        MedicoDAO medDao = new MedicoDAO();
        try{
            Collection<Medico> listaMed = medDao.lista("");
            listaMedicos.addAll(listaMed);
            cmbMed.setItems(listaMedicos);
        } catch (SQLException ex){
            mensagem(ex.getMessage());
        }
    }
    
    private void carregarComboCliente(){
        ClientesDAO clienteDao = new ClientesDAO();
        try{
            Collection<Clientes> listaCli = clienteDao.lista("");
            listaClientes.addAll(listaCli);
            cmbPaciente.setItems(listaClientes);
        } catch (SQLException ex){
            mensagem(ex.getMessage());
        }
    }
    
    private void carregarComboHorario(){
        HorarioDAO horarioDao = new HorarioDAO();
        try{
            Collection<Horario> listaHora = horarioDao.lista("");
            listaHorarios.addAll(listaHora);
            cmbHorario.setItems(listaHorarios);
        } catch (SQLException ex){
            mensagem(ex.getMessage());
        }
    }
    
    private void carregar_View(Consulta model){
        txtIdCons.setText(String.valueOf(model.getIdConsulta()));
        txtSintomas.setText(model.getSintomas());
        dataConsulta.setValue(model.getDataConsul());
        cmbHorario.setValue(model.getHorario());
        cmbMed.setValue(model.getMedico());
        cmbPaciente.setValue(model.getCliente());
    }
    
    private Consulta carregar_Model(){
        int idCons = (txtIdCons.getText().isEmpty()) ? 0 : Integer.parseInt(txtIdCons.getText());
        
        Consulta model = new Consulta(cmbPaciente.getValue(), cmbMed.getValue(), cmbHorario.getValue());
        model.setIdConsulta(idCons);
        model.setDataConsul(dataConsulta.getValue());
        model.setSintomas(txtSintomas.getText());
        
        return model;
    }
    
    private boolean validarDados() {
        if (txtSintomas.getText().length() == 0 ||
                cmbHorario.getValue() == null ||
                cmbPaciente.getValue() == null ||
                cmbMed.getValue() == null) {
            mensagem("Preencha todos os campos, por favor!");
            return false;
        }
        
        LocalDate dataSelecionada = dataConsulta.getValue();
        LocalDate dataHoje = LocalDate.now();
        
        if(dataSelecionada.isBefore(dataHoje)) {
            mensagem("A data da consulta não pode ser anterior a de hoje!");
            return false;
        }
        return true;
    }
    
    private void limparDados() {
        txtIdCons.setText("");
        txtSintomas.setText("");
        cmbHorario.getSelectionModel().clearSelection();
        cmbMed.getSelectionModel().clearSelection();
        cmbPaciente.getSelectionModel().clearSelection();
        dataConsulta.setValue(null);
        incluindo = true;
    }
    
    private ObservableList<Consulta> preencherTabela() {
        ConsultaDAO dao = new ConsultaDAO();
        ObservableList<Consulta> consultaLista = FXCollections.observableArrayList();

        try {
            // Preenche a tabela com todos os médicos
            consultaLista.addAll(dao.lista(""));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenchendo Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }

        return consultaLista;
    }
    
    private void tableView_clique(){
        tviewConsultas.setRowFactory(tv -> {
        TableRow<Consulta> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (!row.isEmpty()) {
                Consulta consultaSelecionada = row.getItem();
                carregar_View(consultaSelecionada); // Preenche os campos com o cliente selecionado
                incluindo = false; // Define que não está incluindo, mas editando
            }
        });
        return row;
    });
    }
    
    private void fazerLigacao(){
        colHorario.setCellValueFactory(new PropertyValueFactory<>("Horario"));
        colIdConsul.setCellValueFactory(new PropertyValueFactory<>("idConsulta"));
        colMed.setCellValueFactory(new PropertyValueFactory<>("Medico"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataConsul"));
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