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
import javafxmvc.model.domain.Autor;

public class FXMLAnchorPaneCadastrosAutoresDialogController implements Initializable {

	@FXML
	private Label labelAutorCodigo;   
	@FXML
	private Label labelAutorNome;
	@FXML
	private Label labelAutorEndereco;    
	@FXML
	private Label labelAutorTelefone;
	@FXML
	private Label labelAutorStatus;
	@FXML
	private Label labelAutorTipo;
    @FXML
    private TextField textFieldAutorNome;
    @FXML
    private TextField textFieldAutorEndereco;
    @FXML
    private TextField textFieldAutorTelefone;
    @FXML
    private TextField textFieldAutorStatus;
    @FXML
    private TextField textFieldAutorTipo;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Autor autor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Autor getAutor() {
        return this.autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
        this.textFieldAutorNome.setText(autor.getNome());
        this.textFieldAutorTelefone.setText(autor.getTelefone());
        this.textFieldAutorStatus.setText(autor.getStatus());
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            autor.setNome(textFieldAutorNome.getText());
            autor.setTelefone(textFieldAutorTelefone.getText());
            autor.setStatus(textFieldAutorStatus.getText());

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

        if (textFieldAutorNome.getText() == null || textFieldAutorNome.getText().length() == 0) {
            errorMessage += "Nome inv�lido!\n";
        }
        if (textFieldAutorTelefone.getText() == null || textFieldAutorTelefone.getText().length() == 0) {
            errorMessage += "Telefone inv�lido!\n";
        }
        if (textFieldAutorStatus.getText() == null || textFieldAutorStatus.getText().length() == 0) {
            errorMessage += "Status inv�lido!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inv�lidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

}
