package br.com.fatec.controller;

import br.com.fatec.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrincipalController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(PrincipalController.class.getName());


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void mostrarTelaMedico() {
        try {
            App.setRoot("br/com/fatec/view/Tela_Medicos");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao carregar a tela de médico", e);
        }
    }

    public void mostrarTelaPaciente() {
        try {
            App.setRoot("br/com/fatec/view/Tela_Cliente");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao carregar a tela de paciente", e);
        }
    }

    public void mostrarTelaConsulta() {
        /*try {
            App.setRoot("br/com/fatec/view/Tela_Consulta");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao carregar a tela de consulta", e);
        }*/
    }
}
