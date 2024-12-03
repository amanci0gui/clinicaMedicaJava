/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.controller;


import br.com.fatec.App;
import br.com.fatec.model.Lista_de_Espera;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Pichau
 */
public class ListaDeEsperaController implements Initializable {

    @FXML
    private TableView<Lista_de_Espera> tviewEspera;
    @FXML
    private Button btnExcluir;
    @FXML
    private Button btnSalvar;
    @FXML
    private TextField txtNome;
    @FXML
    private TextField txtCpf;
    @FXML
    private RadioButton radioPrioridade;
    @FXML
    private ToggleGroup tipoPrioridade;
    @FXML
    private RadioButton radioNormal;
    @FXML
    private TextArea txtSintomas;

    @FXML
    private Button btnPesquisar;
    @FXML
    private TextField txtId;
    @FXML
    private ImageView logo; // logo para voltar para tela principal
    
    private final List<Lista_de_Espera> listaDeEspera = new ArrayList<>();
    private ObservableList<Lista_de_Espera> observableList;
    @FXML
    private TableColumn<Lista_de_Espera, Integer> colId;
    @FXML
    private TableColumn<Lista_de_Espera, String> colNome;
    @FXML
    private TableColumn<Lista_de_Espera, String> colCpf;
    @FXML
    private TableColumn<Lista_de_Espera, Boolean> colPreferencial;
            
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        fazerLigacao();
        atualizarTableView();
        logo.setOnMouseClicked(this::mostrarTelaPrincipal);
    }    

    @FXML
    private void btnExcluir_Click(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtId.getText());

            boolean removido = listaDeEspera.removeIf(paciente -> paciente.getCodLista() == id);

            if (removido) {
                mensagem("Paciente removido com sucesso!");
            } else {
                mensagem("Paciente com ID " + id + " não encontrado.");
            }
            limparDados();
            atualizarTableView();
        } catch (NumberFormatException e) {
            mensagem("Erro: ID deve ser um número inteiro válido.");
        }
    }

    @FXML
    private void btnSalvar_Click(ActionEvent event) {
        if (validarDados()) {
        // Recupera o valor do txtId e verifica se está vazio
        String idText = txtId.getText();
        int id = idText.isEmpty() ? 0 : Integer.parseInt(idText);

        // Verifica se o ID já existe na lista
        boolean idExistente = listaDeEspera.stream()
                .anyMatch(item -> item.getCodLista() == id);

        if (idExistente) {
            // Se o ID já existir, atualiza o item existente
            Lista_de_Espera itemExistente = listaDeEspera.stream()
                    .filter(item -> item.getCodLista() == id)
                    .findFirst()
                    .orElse(null); // Retorna o item encontrado ou null se não houver (o que não deveria acontecer aqui)

            if (itemExistente != null) {
                // Atualiza os dados do item existente
                itemExistente.setNome(txtNome.getText());
                itemExistente.setCpf(txtCpf.getText());
                itemExistente.setSintomas(txtSintomas.getText());
                itemExistente.setPreferencial(radioPrioridade.isSelected());
                
                tviewEspera.refresh();
                // Exibe uma mensagem de sucesso para alteração
                mensagem("Paciente alterado com sucesso!");
                limparDados();
                
            }
        } else {
            // Se o ID não existir, cria um novo item
            Lista_de_Espera novaEspera = new Lista_de_Espera(
                    id, 
                    txtNome.getText(), 
                    txtSintomas.getText(), 
                    txtCpf.getText(), 
                    radioPrioridade.isSelected()
            );

            // Adiciona o novo item à lista
            listaDeEspera.add(novaEspera);
            
            
            // Exibe uma mensagem de sucesso para adição
            mensagem("Paciente adicionado com sucesso!");
            limparDados();
            atualizarTableView();
        }

        // Atualiza a tabela após adicionar ou alterar
        atualizarTableView();
    }
    }

    @FXML
    private void btnPesquisar_Click(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtId.getText());

            Optional<Lista_de_Espera> pacienteEncontrado = listaDeEspera.stream()
                    .filter(paciente -> paciente.getCodLista() == id)
                    .findFirst();

            if (pacienteEncontrado.isPresent()) {
                Lista_de_Espera paciente = pacienteEncontrado.get();
                txtNome.setText(paciente.getNome());
                txtCpf.setText(paciente.getCpf());
                txtSintomas.setText(paciente.getSintomas());
                if (paciente.isPreferencial()) {
                    radioPrioridade.setSelected(true);
                } else {
                    radioNormal.setSelected(true);
                }
                mensagem("Paciente encontrado!");
            } else {
                mensagem("Paciente com ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException e) {
            mensagem("Erro: ID deve ser um número inteiro válido.");
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
    
    private boolean validarDados(){
        if(txtCpf.getText().length() == 0 ||
                tipoPrioridade.getSelectedToggle() == null ||
                txtNome.getText().length() == 0 ||
                txtSintomas.getText().length() == 0 ||
                txtId.getText().length() == 0) {
            mensagem("Preencha todos os campos");
            return false;
        } else{
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
    
    private void fazerLigacao(){
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colPreferencial.setCellValueFactory(new PropertyValueFactory<>("preferencial"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colId.setCellValueFactory(new PropertyValueFactory<>("codLista"));
        
        colPreferencial.setCellFactory(cell -> new TableCell<Lista_de_Espera, Boolean>() {
        @Override
        protected void updateItem(Boolean item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
            } else {
                setText(item != null && item ? "Sim" : "Não");
            }
        }
    });
    }
    
    private void atualizarTableView() {
        observableList = FXCollections.observableArrayList(listaDeEspera);
        tviewEspera.setItems(observableList);
    }
    
    private void limparDados(){
        txtCpf.setText("CPF");
        txtId.setText("ID");
        txtNome.setText("Nome");
       txtSintomas.setText("");
       radioNormal.setSelected(false);
       radioPrioridade.setSelected(false);
       
       txtId.requestFocus();
    }

    @FXML
    private void tviewEspera_Click(MouseEvent event) {
            // Obtém o item selecionado da TableView
        Lista_de_Espera itemSelecionado = tviewEspera.getSelectionModel().getSelectedItem();

        if (itemSelecionado != null) {
            // Preenche os campos com os dados do item selecionado
            txtId.setText(String.valueOf(itemSelecionado.getCodLista()));
            txtNome.setText(itemSelecionado.getNome());
            txtCpf.setText(itemSelecionado.getCpf());
            txtSintomas.setText(itemSelecionado.getSintomas());

            // Define o estado do radioButton com base no item selecionado
            if (itemSelecionado.isPreferencial()) {
                radioPrioridade.setSelected(true);
            } else {
                radioNormal.setSelected(true);
            }
        }
    }    
}
