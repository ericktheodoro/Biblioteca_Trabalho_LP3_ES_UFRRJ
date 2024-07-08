package javafxmvc.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.domain.Usuario;

public class FXMLAnchorPaneCadastrosUsuarioDialogController implements Initializable {

    @FXML
    private Label labelUsuarioNome;
    @FXML
    private Label labelUsuarioEndereco;
    @FXML
    private Label labelUsuarioTelefone;
    @FXML
    private TextField textFieldUsuarioNome;
    @FXML
    private TextField textFieldUsuarioEndereco;
    @FXML
    private TextField textFieldUsuarioTelefone;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        this.textFieldUsuarioNome.setText(usuario.getNome());
        this.textFieldUsuarioEndereco.setText(usuario.getEndereco());
        this.textFieldUsuarioTelefone.setText(usuario.getTelefone());
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            usuario.setNome(textFieldUsuarioNome.getText());
            usuario.setEndereco(textFieldUsuarioEndereco.getText());
            usuario.setTelefone(textFieldUsuarioTelefone.getText());

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (textFieldUsuarioNome.getText() == null || textFieldUsuarioNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textFieldUsuarioEndereco.getText() == null || textFieldUsuarioEndereco.getText().length() == 0) {
            errorMessage += "Endereço inválido!\n";
        }
        if (textFieldUsuarioTelefone.getText() == null || textFieldUsuarioTelefone.getText().length() == 0) {
            errorMessage += "Telefone inválido!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

}
