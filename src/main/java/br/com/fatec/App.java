package br.com.fatec;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.scene.image.Image;

public class App extends Application {

    private static Scene scene;
    private static final Logger LOGGER = Logger.getLogger(App.class.getName()); // utilizando log para erros encontrados
    
    // Caminhos para views
    private static final String TELA_PRINCIPAL = "br/com/fatec/view/Principal";
    private static final String TELA_MEDICOS = "br/com/fatec/view/Tela_Medicos";
    private static final String TELA_AVANCADA = "br/com/fatec/view/Tela_Avancada";
    private static final String LISTA_ESPERA = "br/com/fatec/view/ListaDeEspera";
    private static final String TELA_PACIENTES = "br/com/fatec/view/Tela_Cliente";
    private static final String TELA_CONSULTA = "br/com/fatec/view/Tela_Consultas";

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML(TELA_PRINCIPAL));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método que carrega e define a cena com base no arquivo FXML.
     *
     * @param fxml o caminho do arquivo FXML a ser carregado
     * @throws IOException se houver erro ao carregar o arquivo FXML
     */
    public static void setRoot(String fxml) throws IOException {
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao carregar a tela: " + fxml, e);
            throw new IOException("Erro ao carregar a tela", e);
        }
    }

    /**
     * Carrega o arquivo FXML especificado.
     *
     * @param fxml o caminho do arquivo FXML
     * @return o componente raiz do FXML carregado
     * @throws IOException se houver erro ao carregar o arquivo FXML
     */
    private static Parent loadFXML(String fxml) throws IOException {
        // Verifica se o arquivo FXML existe no diretório correto (recursos)
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/" + fxml + ".fxml"));
        if (fxmlLoader.getLocation() == null) {
            throw new IOException("Arquivo FXML não encontrado: " + fxml);
        }
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
