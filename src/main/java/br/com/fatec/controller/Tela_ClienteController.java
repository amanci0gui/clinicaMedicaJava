package br.com.fatec.controller;

import br.com.fatec.App;
import br.com.fatec.dao.ClientesDAO;
import br.com.fatec.model.Clientes;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    @FXML
    private ImageView logo; // logo para voltar para tela principal
    
    // Variáveis auxiliares
    private ClientesDAO clientesDAO = new ClientesDAO();
    private Clientes clientes;
    private ObservableList<Clientes> listaClientes = FXCollections.observableArrayList();
    private boolean incluindo = true;
    private String valorSelecionado;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fazerLigacao();
        tviewClientes.setItems(preencheTabela());
        tableView_clique();
        logo.setOnMouseClicked(this::mostrarTelaPrincipal);
    }

    @FXML
    private void btnSalvar_Click(ActionEvent event) {
        if (!validarDados()) {
            return;
        }

        clientes = carregar_Model();

        try {
            if (incluindo) {
                if (clientesDAO.insere(clientes)) {
                    mensagem("Cliente incluído com sucesso!!!");
                    limparDados();
                    tviewClientes.setItems(preencheTabela());
                } else {
                    mensagem("Erro na Inclusão");
                }
            } else {
                if (clientesDAO.altera(clientes)) {
                    mensagem("Cliente atualizado com sucesso!!!");
                    limparDados();
                    atualizarTabela(clientes);
                } else {
                    mensagem("Erro na Alteração");
                }
            }
        } catch (SQLException ex) {
            mensagem("Erro na Gravação\n" + ex.getMessage());
        }
    }

    @FXML
    private void btnExcluir_Click(ActionEvent event) {
        clientes = new Clientes();
        clientes.setIdCliente(Integer.parseInt(txtId.getText()));

        try {
            if (clientesDAO.remove(clientes)) {
                mensagem("Cliente excluído com sucesso!!!");
                limparDados();
                tviewClientes.setItems(preencheTabela());
            } else {
                mensagem("Erro na exclusão");
            }
        } catch (SQLException ex) {
            mensagem("Erro de Exclusão\n" + ex.getMessage());
        }
    }

    @FXML
    private void btnPesquisar_Click(ActionEvent event) {
        clientes = new Clientes();
        clientes.setIdCliente(Integer.parseInt(txtId.getText()));

        try {
            clientes = clientesDAO.buscaID(clientes);

            if (clientes != null) {
                carregar_View(clientes);
                incluindo = false;
            } else {
                mensagem("Cliente não encontrado");
                txtNome.requestFocus();
            }
        } catch (SQLException ex) {
            mensagem("Erro na procura do Cliente: " + ex.getMessage());
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

    private void carregar_View(Clientes model) {
        txtNome.setText(model.getNomeCliente());
        txtId.setText(String.valueOf(model.getIdCliente()));
        txtCpf.setText(model.getCpf());
        txtPlano.setText(model.getPlanoConv());
        txtTelefone.setText(model.getTelefone());
        dateCalendario.setValue(model.getDataNasc());
    }

    private Clientes carregar_Model() {
        Clientes model = new Clientes();
        model.setNomeCliente(txtNome.getText());
        model.setCpf(txtCpf.getText());
        model.setDataNasc(dateCalendario.getValue());
        if (radioConvenio.isSelected()) {
            valorSelecionado = "C";
        } else {
            valorSelecionado = "P";
            txtPlano.setText("Não possui convênio");
        }
        model.setConvOuPart(valorSelecionado);
        model.setPlanoConv(txtPlano.getText());
        model.setTelefone(txtTelefone.getText());
        model.setIdCliente(txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText()));
        return model;
    }

    private boolean validarDados() {
        if (txtNome.getText().isEmpty() ||
                tipoGrupo.getSelectedToggle() == null ||
                txtCpf.getText().isEmpty() ||
                txtTelefone.getText().isEmpty()) {
            mensagem("Por favor, preencher todos os dados");
            return false;
        }
        return true;
    }

    private void limparDados() {
        txtNome.setText("");
        txtCpf.setText("");
        txtId.setText("");
        txtPlano.setText("");
        txtTelefone.setText("");
        radioConvenio.setSelected(false);
        radioParticular.setSelected(false);
        txtNome.requestFocus();
    }

    private void mensagem(String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensagem");
        alerta.setHeaderText(msg);
        alerta.setContentText("");
        alerta.showAndWait();
    }

    private void fazerLigacao() {
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
        ObservableList<Clientes> clientesLista = FXCollections.observableArrayList();

        try {
            clientesLista.addAll(dao.lista(""));
        } catch (SQLException ex) {
            Alert alerta = new Alert(Alert.AlertType.ERROR,
                    "Erro Preenchendo Tabela: " + ex.getMessage(),
                    ButtonType.OK);
            alerta.showAndWait();
        }

        return clientesLista;
    }

    private void tableView_clique() {
        tviewClientes.setRowFactory(tv -> {
            TableRow<Clientes> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    Clientes clienteSelecionado = row.getItem();
                    carregar_View(clienteSelecionado);
                    incluindo = false;
                }
            });
            return row;
        });
    }

    // Atualiza a tabela com os dados modificados de do cliente pelo ID
    private void atualizarTabela(Clientes clienteAlterado) {
        int index = getClienteIndex(clienteAlterado.getIdCliente());
        if (index != -1) {
            tviewClientes.getItems().set(index, clienteAlterado);
        } else {
            mensagem("Cliente não encontrado na tabela para atualização.");
        }
    }

    // Método para encontrar o índice do cliente na lista (TableView)
    private int getClienteIndex(int idCliente) {
        for (int i = 0; i < tviewClientes.getItems().size(); i++) {
            if (tviewClientes.getItems().get(i).getIdCliente() == idCliente) {
                return i;  // Retorna o índice do cliente na lista
            }
        }
        return -1;
    }
}
