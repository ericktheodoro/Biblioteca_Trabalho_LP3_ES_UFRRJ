package javafxmvc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.domain.PalavraChave;

public class FXMLAnchorPaneCadastrosPalavrasChaveDialogController implements Initializable {

    @FXML
    private TextField textFieldPalavraChaveNome;
    @FXML
    private ChoiceBox<String> choiceBarPalavraChaveStatus;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private PalavraChave palavraChave;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> statusOptions = FXCollections.observableArrayList("Habilitado", "Desabilitado");
        choiceBarPalavraChaveStatus.setItems(statusOptions);
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    public PalavraChave getPalavraChave() {
        return palavraChave;
    }

    public String getStatusFromChoiceBox() {
        String selectedStatus = choiceBarPalavraChaveStatus.getValue();
        if ("Habilitado".equals(selectedStatus)) {
            return "H";
        } else if ("Desabilitado".equals(selectedStatus)) {
            return "D";
        }
        return null;
    }

    public void setChoiceBoxStatus(String statusFromDB) {
        if ("H".equals(statusFromDB)) {
        	choiceBarPalavraChaveStatus.setValue("Habilitado");
        } else if ("D".equals(statusFromDB)) {
        	choiceBarPalavraChaveStatus.setValue("Desabilitado");
        }
    }

    public void setPalavraChave(PalavraChave palavraChave) {
        this.palavraChave = palavraChave;
        this.textFieldPalavraChaveNome.setText(palavraChave.getNome());
        this.setChoiceBoxStatus(palavraChave.getStatus()); // Define o status na ChoiceBox
    }

    @FXML
    public void handleButtonConfirmar() {
        palavraChave.setNome(textFieldPalavraChaveNome.getText());
        palavraChave.setStatus(this.getStatusFromChoiceBox()); // Obtém o status da ChoiceBox

        buttonConfirmarClicked = true;
        dialogStage.close();
    }

    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }
}
