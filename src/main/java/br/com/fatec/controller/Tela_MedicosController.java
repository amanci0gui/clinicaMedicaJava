/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.dao.EspecializacaoDAO;
import br.com.fatec.dao.MedicoDAO;
import br.com.fatec.model.Especializacao;
import br.com.fatec.model.Medico;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Pichau
 */
public class Tela_MedicosController implements Initializable {

    @FXML
    private Button btnPesquisar;
    @FXML
    private TextField txtIdMed;
    @FXML
    private TableColumn<Medico, Integer> colIdMed;
    @FXML
    private TableColumn<Medico, String> colNomeMed;
    @FXML
    private TableColumn<Medico, String> colCrm;
    @FXML
    private TableColumn<Medico, LocalDate> colDataNasc;
    @FXML
    private TableColumn<Medico, String> colEspec;
    @FXML
    private TableColumn<Medico, String> colTelefone;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnSalvar;
    @FXML
    private TextField txtTelefone;
    @FXML
    private DatePicker dataNasc;
    @FXML
    private TextField txtCrm;
    @FXML
    private TextField txtNomeMed;
    @FXML
    private TableView<Medico> tviewMedicos;
    @FXML
    private ImageView logo; // logo para voltar para tela principal
    
    @FXML
    private ComboBox<Especializacao> cmbEspec;
    
    
    //variaveis auxiliares
private ObservableList<Especializacao> listaEspecializacao =  
        FXCollections.observableArrayList();
    
    private MedicoDAO medicoDAO = new MedicoDAO();
    private Medico medico;
    private boolean incluindo = true;
    private String valorSelecionado;
            
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        carregarCombo();
        fazerLigacao();
        tviewMedicos.setItems(preencherTabela());
        
        tableView_clique();
        logo.setOnMouseClicked(this::mostrarTelaPrincipal);
        
        aplicarMascaraCRM();
        aplicarMascaraTelefone();
    }    

    @FXML
    private void btnPesquisar_Click(ActionEvent event) {
        medico = new Medico(null);
        medico.setIdMedico(Integer.parseInt(txtIdMed.getText()));
        
        try{
            medico = medicoDAO.buscaID(medico);
            if(medico != null){
                carregar_View(medico);
                incluindo = false;
                
            } else {
                mensagem("Médico não encontrado");
                txtNomeMed.requestFocus();
            }
        } catch (SQLException ex) {
            mensagem("Erro na procura do Médico: " + 
                    ex.getMessage());
        }
    }

    @FXML
    private void btnExcluir_Click(ActionEvent event) {
        medico = new Medico(null);
        medico.setIdMedico(Integer.parseInt(txtIdMed.getText()));
        
        try {
            if(medicoDAO.remove(medico)) {
                mensagem("Médico excluído com Sucesso !!!");
                limparDados();
                tviewMedicos.setItems(preencherTabela());
            } 
            else {
                mensagem("Ocorreu algum erro para exclusão");
            }
        } catch (SQLException ex) {
            mensagem("Erro de Exclusão\n" + ex.getMessage());
        }
    }

    @FXML
    private void btnSalvar_Click(ActionEvent event) {
        // Verifica se os dados estão preenchidos
        if (!validarDados()) {
            return; // Se os dados não forem válidos, não continua a operação
        }

        // Carrega os dados do modelo a partir da interface gráfica
        medico = carregar_Model();

        try {
            if (incluindo) { // Se a operação é de inclusão
                if (medicoDAO.insere(medico)) {
                    mensagem("Médico incluído com sucesso!"); // Ajustado para "Médico"
                    limparDados(); // Limpa os campos após inclusão
                    tviewMedicos.setItems(preencherTabela()); // Atualiza a tabela
                } else {
                    mensagem("Erro na Inclusão do Médico");
                }
            } else { // Se a operação é de alteração
                if (medicoDAO.altera(medico)) {
                    mensagem("Médico alterado com sucesso!"); // Ajustado para "Médico"
                    limparDados(); // Limpa os campos após alteração
                    ObservableList<Medico> listaAtualizada = preencherTabela(); // Obtenha os dados atualizados
                    tviewMedicos.getItems().clear(); // Limpa os itens antigos
                    tviewMedicos.setItems(listaAtualizada); // Seta a nova lista
                } else {
                    mensagem("Erro na Alteração do Médico");
                }
            }
        } catch (SQLException ex) {
            // Exibe a mensagem de erro caso ocorra alguma exceção durante a gravação no banco
            mensagem("Erro na Gravação: " + ex.getMessage());
            ex.printStackTrace(); // Adiciona um log da exceção
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
    
    private void carregarCombo() {
        EspecializacaoDAO especDao = new EspecializacaoDAO();
        try{
            Collection<Especializacao> listaEspec = especDao.lista("");
            listaEspecializacao.addAll(listaEspec);
            cmbEspec.setItems(listaEspecializacao);
        }catch (SQLException ex){
            mensagem(ex.getMessage());
        }
            
    }
    
    private void mensagem(String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensagem");
        alerta.setHeaderText(msg);
        alerta.setContentText("");

        alerta.showAndWait(); //exibe a mensage
    }
    
    private void carregar_View(Medico model){
        txtIdMed.setText(String.valueOf(model.getIdMedico()));
        txtNomeMed.setText(model.getNomeMedico());
        txtCrm.setText(model.getCrm());
        txtTelefone.setText(model.getTelefone());
        dataNasc.setValue(model.getDataNasc());
        cmbEspec.setValue(model.getEspecializacao());
    }
    
    private Medico carregar_Model() {
        // Se o campo 'txtIdMed' estiver vazio, a id do Médico é 0 (indica que é um novo médico)
        int idMedico = (txtIdMed.getText().isEmpty()) ? 0 : Integer.parseInt(txtIdMed.getText());

        Medico model = new Medico(cmbEspec.getValue());
        model.setIdMedico(idMedico);  // Atribui a idMedico (novo ou existente)
        model.setNomeMedico(txtNomeMed.getText());
        model.setCrm(txtCrm.getText());
        model.setDataNasc(dataNasc.getValue());
        model.setTelefone(txtTelefone.getText());

        return model;
    }
    
        
    private boolean validarDados() {
        if (txtNomeMed.getText().length() == 0 ||
                txtCrm.getText().length() == 0 ||
                txtTelefone.getText().length() == 0 ||
                dataNasc.getValue() == null ||
                cmbEspec.getValue() == null) {
            mensagem("Preencha todos os campos, por favor!");
            return false;
        }
        
        LocalDate dataSelecionada = dataNasc.getValue();
        LocalDate dataHoje = LocalDate.now();
        
        if(dataSelecionada.isAfter(dataHoje)) {
            mensagem("A data de nascimento não pode ser posterior a de hoje!");
            return false;
        }
        return true;
    }
    
    private void limparDados() {
        txtNomeMed.setText("");
        txtCrm.setText("");
        txtIdMed.setText("");
        txtTelefone.setText("");
        cmbEspec.getSelectionModel().clearSelection();
        dataNasc.setValue(null);
        incluindo = true;
    }
    
    private void fazerLigacao(){
        colNomeMed.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));
        colIdMed.setCellValueFactory(new PropertyValueFactory<>("idMedico"));
        colCrm.setCellValueFactory(new PropertyValueFactory<>("crm"));
        colDataNasc.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));
        colEspec.setCellValueFactory(new PropertyValueFactory<>("Especializacao"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEspec.setCellValueFactory(cellData -> {
        // Retornando um SimpleStringProperty com o nome da especialização
        return new SimpleStringProperty(cellData.getValue().getEspecializacao().getNomeEspec());
    });
    }
    
    private ObservableList<Medico> preencherTabela() {
        MedicoDAO dao = new MedicoDAO();
        ObservableList<Medico> medicoLista = FXCollections.observableArrayList();

        try {
            // Preenche a tabela com todos os médicos
            medicoLista.addAll(dao.lista(""));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenchendo Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }

        return medicoLista;
    }
    
    private void tableView_clique(){
        tviewMedicos.setRowFactory(tv -> {
        TableRow<Medico> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (!row.isEmpty()) {
                Medico medicoSelecionado = row.getItem();
                carregar_View(medicoSelecionado); // Preenche os campos com o cliente selecionado
                incluindo = false; // Define que não está incluindo, mas editando
            }
        });
        return row;
    });
    }
    
    private void aplicarMascaraCRM() {
        txtCrm.textProperty().addListener((observable, oldValue, newValue) -> {
            // Remove caracteres não permitidos
            String valorSemMascara = newValue.replaceAll("[^\\dA-Za-z]", "");

            // Aplica a máscara (6 dígitos + "/" + 2 letras)
            StringBuilder valorComMascara = new StringBuilder(valorSemMascara);
            if (valorComMascara.length() > 6) {
                valorComMascara.insert(6, "/");
            }

            if (valorComMascara.length() > 9) {
                valorComMascara.setLength(9); // Limita ao tamanho máximo
            }

            // Atualiza o campo com o valor formatado
            txtCrm.setText(valorComMascara.toString());
        });
    }
    
    private void aplicarMascaraTelefone() {
        txtTelefone.textProperty().addListener((observable, oldValue, newValue) -> {
            String valorSemMascara = newValue.replaceAll("[^\\d]", ""); // Apenas números

            // (XX) XXXXX-XXXX
            StringBuilder valorComMascara = new StringBuilder(valorSemMascara);
            if (valorComMascara.length() > 2) {
                valorComMascara.insert(0, "(");
            }
            if (valorComMascara.length() > 6) {
                valorComMascara.insert(3, ") ");
            }
            if (valorComMascara.length() > 11) {
                valorComMascara.insert(10, "-");
            }

            if (valorComMascara.length() > 15) {
                valorComMascara.setLength(15); // tamanho máximo
            }

            // Atualiza o campo 
            txtTelefone.setText(valorComMascara.toString());
        });
    }        
}
