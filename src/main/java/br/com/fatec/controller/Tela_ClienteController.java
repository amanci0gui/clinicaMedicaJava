/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.dao.ClientesDAO;
import br.com.fatec.model.Clientes;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;



/**
 * FXML Controller class
 *
 * @author Pichau
 */
public class Tela_ClienteController implements Initializable {

    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCpf;
    @FXML
    private DatePicker dateCalendario;
    @FXML
    private RadioButton radioConvenio;
    @FXML
    private ToggleGroup tipoGrupo;
    @FXML
    private RadioButton radioParticular;
    @FXML
    private TextField txtPlano;
    @FXML
    private TextField txtTelefone;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnExcluir;
    @FXML
    private TextField txtId;
    @FXML
    private TableView<Clientes> tviewClientes;
    @FXML
    private ImageView logo; // logo para voltar para tela principal

    
    //variaveis auxiliares
    private ClientesDAO clientesDAO = new ClientesDAO();
    private Clientes clientes;
    private ObservableList<Clientes> listaClientes =
            FXCollections.observableArrayList();
    private boolean incluindo = true;
    private String valorSelecionado;
    @FXML
    private Button btnPesquisar;
    @FXML
    private TableColumn<Clientes, Integer> colIdCliente;
    @FXML
    private TableColumn<Clientes, String> colNome;
    @FXML
    private TableColumn<Clientes, String> colCpf;
    @FXML
    private TableColumn<Clientes, LocalDate> colDataNasc;
    @FXML
    private TableColumn<Clientes, String> colConvOuPart;
    @FXML
    private TableColumn<Clientes, String> colPlano;
    @FXML
    private TableColumn<Clientes, String> colTelefone;

    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fazerLigacao();
        tviewClientes.setItems(preencheTabela());
        
        tableView_clique();
        logo.setOnMouseClicked(this::mostrarTelaPrincipal);
        aplicarMascaraCPF();
        aplicarMascaraTelefone();
    }    

    @FXML
    private void btnSalvar_Click(ActionEvent event) {
        //verifica se os dados estão preenchidos
        if(validarDados() == false) {
            return;
        }
        
        clientes = carregar_Model();
        
        try{
            if(incluindo) {
                if(clientesDAO.insere(clientes)){
                    mensagem("Cliente incluído com sucesso!!!");
                    limparDados();
                    tviewClientes.setItems(preencheTabela());
                }
                else {
                    mensagem("Erro na Inclusão");
                }
            }
            else {
                if(clientesDAO.altera(clientes)) {
                    mensagem("Cliente atualizado com sucesso!!!");
                    limparDados();
                    ObservableList<Clientes> listaAtualizada = preencheTabela(); // Obtenha os dados atualizados
                    tviewClientes.getItems().clear(); // Limpa os itens antigos
                    tviewClientes.setItems(listaAtualizada); // Seta a nova lista
                }
                else {
                    mensagem("Erro na Alteração");
                }
            }
        }
        catch (SQLException ex) {
            mensagem("Erro na Gravação\n" + ex.getMessage());
        }
    }

    @FXML
    private void btnExcluir_Click(ActionEvent event) {
        clientes = new Clientes();
        clientes.setIdCliente(Integer.parseInt(txtId.getText()));
        
        try{
            if(clientesDAO.remove(clientes)){
                mensagem("Cliente excluído com Sucesso !!!");
                limparDados();
                tviewClientes.setItems(preencheTabela());
            }
            else{
                mensagem("Ocorreu algum erro para exclusão");
            }            
        }
        catch (SQLException ex) {
            mensagem("Erro de Exclusão\n" + ex.getMessage());
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
    
        /**
     * Testa se todos os campos estão preenchidos
     * @return 
     */
    private boolean validarDados() {
        if(txtNome.getText().length() == 0 ||
                tipoGrupo.getSelectedToggle()== null ||
                txtCpf.getText().length() == 0 || 
                txtTelefone.getText().length() == 0) {
            mensagem("Por favor, preencher todos os dados");
            return false;
        }
        LocalDate dataSelecionada = dateCalendario.getValue();
        LocalDate dataHoje = LocalDate.now();
        
        if(dataSelecionada.isAfter(dataHoje)) {
            mensagem("A data de nascimento não pode ser posterior a de hoje!");
            return false;
        }
        else {
            return true;
        }
    }
    
     private void mensagem(String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensagem");
        alerta.setHeaderText(msg);
        alerta.setContentText("");

        alerta.showAndWait(); //exibe a mensage
    }
     
    private Clientes carregar_Model(){
        Clientes model = new Clientes();
        model.setNomeCliente(txtNome.getText());
        model.setCpf(txtCpf.getText());
        model.setDataNasc(dateCalendario.getValue());
        if (radioConvenio.isSelected()){
            valorSelecionado = "C";
        }
        else{
            valorSelecionado = "P";
            txtPlano.setText("Não possui convênio");
        }
        model.setConvOuPart(valorSelecionado);
        model.setPlanoConv(txtPlano.getText());
        model.setTelefone(txtTelefone.getText());
        if (!txtId.getText().isEmpty()) {
            model.setIdCliente(Integer.parseInt(txtId.getText()));
        }
        
        return model;
    }
    

    public void limparDados() {
        //limpa os campos
        txtNome.setText("");
        txtCpf.setText("");
        txtId.setText("");
        txtPlano.setText("");
        txtTelefone.setText("");
        radioConvenio.setSelected(false);
        radioParticular.setSelected(false);
        
        incluindo = true;
        txtNome.requestFocus();
    }

    @FXML
    private void btnPesquisar_Click(ActionEvent event) {
        clientes = new Clientes();
        clientes.setIdCliente(Integer.parseInt(txtId.getText()));
        
        try{
            clientes = clientesDAO.buscaID(clientes);
            
            if(clientes != null){
                carregar_View(clientes);
                incluindo = false;
                
            } else {
                mensagem("Cliente não encontrado");
                txtNome.requestFocus();
            }
        } catch (SQLException ex) {
            mensagem("Erro na procura do Cliente: " + 
                    ex.getMessage());
        }
    }
            
    private void carregar_View(Clientes model) {
        txtId.setText(String.valueOf(model.getIdCliente()));
        txtNome.setText(model.getNomeCliente());
        txtCpf.setText(model.getCpf());
        txtPlano.setText(model.getPlanoConv());
        txtTelefone.setText(model.getTelefone());
        dateCalendario.setValue(model.getDataNasc());
        if (model.getConvOuPart().equals("C")) {
            radioConvenio.setSelected(true);
        } else {
            radioParticular.setSelected(true);
        }
        
    }
    
    private void fazerLigacao(){
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colDataNasc.setCellValueFactory(new PropertyValueFactory<>("dataNasc"));
        colConvOuPart.setCellValueFactory(new PropertyValueFactory<>("convOuPart"));
        colPlano.setCellValueFactory(new PropertyValueFactory<>("planoConv"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        
    }
    
    private ObservableList<Clientes> preencheTabela() {
       ClientesDAO dao = new ClientesDAO();
        ObservableList<Clientes> clientesLista
            = FXCollections.observableArrayList();
        
        try {
            //busca somente que termina com 'a'
            //proprietarios.addAll(dao.lista("nome like '%a'"));
            //busca todo mundo
            clientesLista.addAll(dao.lista(""));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenche Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }
        
        return clientesLista;
    }
    
    private void tableView_clique(){
        tviewClientes.setRowFactory(tv -> {
        TableRow<Clientes> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
            if (!row.isEmpty()) {
                Clientes clienteSelecionado = row.getItem();
                carregar_View(clienteSelecionado); // Preenche os campos com o cliente selecionado
                incluindo = false; // Define que não está incluindo, mas editando
            }
        });
        return row;
    });
    }
    
    private void aplicarMascaraCPF() {
        txtCpf.textProperty().addListener((observable, oldValue, newValue) -> {
            // Remove caracteres não permitidos
            String valorSemMascara = newValue.replaceAll("[^\\d]", ""); // Apenas números

            // Aplica a máscara (XXX.XXX.XXX-XX)
            StringBuilder valorComMascara = new StringBuilder(valorSemMascara);
            if (valorComMascara.length() > 3) {
                valorComMascara.insert(3, ".");
            }
            if (valorComMascara.length() > 7) {
                valorComMascara.insert(7, ".");
            }
            if (valorComMascara.length() > 11) {
                valorComMascara.insert(11, "-");
            }

            if (valorComMascara.length() > 14) {
                valorComMascara.setLength(14); // Limita ao tamanho máximo
            }

            // Atualiza o campo com o valor formatado
            txtCpf.setText(valorComMascara.toString());
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
