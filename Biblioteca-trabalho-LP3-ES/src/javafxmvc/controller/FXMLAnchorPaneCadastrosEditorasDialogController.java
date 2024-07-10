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
import javafxmvc.model.domain.Editora;

public class FXMLAnchorPaneCadastrosEditorasDialogController implements Initializable {

	@FXML
	private Label labelEditoraCodigo;
	@FXML
	private Label labelEditoraNome;
	@FXML
	private Label labelEditoraEndereco;
	@FXML
	private Label labelEditoraTelefone;
	@FXML
	private Label labelEditoraContato;
	@FXML
	private Label labelEditoraStatus;
	@FXML
	private TextField textFieldEditoraNome;
	@FXML
	private TextField textFieldEditoraEndereco;
	@FXML
	private TextField textFieldEditoraTelefone;
	@FXML
	private TextField textFieldEditoraContato;
	@FXML
	private TextField textFieldEditoraStatus;
	@FXML
	private Button buttonConfirmar;
	@FXML
	private Button buttonCancelar;

	private Stage dialogStage;
	private boolean buttonConfirmarClicked = false;
	private Editora editora;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}

	public Stage getDialogStage() {
		return dialogStage;
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public Editora getEditora() {
		return this.editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
		this.textFieldEditoraNome.setText(editora.getNome());
		this.textFieldEditoraEndereco.setText(editora.getEndereco());
		this.textFieldEditoraTelefone.setText(editora.getTelefone());
		this.textFieldEditoraContato.setText(editora.getContato());
		this.textFieldEditoraStatus.setText(editora.getStatus());
	}

	public boolean isButtonConfirmarClicked() {
		return buttonConfirmarClicked;
	}

	@FXML
	public void handleButtonConfirmar() {
		if (validarEntradaDeDados()) {
			editora.setNome(textFieldEditoraNome.getText());
			editora.setEndereco(textFieldEditoraEndereco.getText());
			editora.setTelefone(textFieldEditoraTelefone.getText());
			editora.setContato(textFieldEditoraContato.getText());
			editora.setStatus(textFieldEditoraStatus.getText());

			buttonConfirmarClicked = true;
			dialogStage.close();
		}
	}

	@FXML
	public void handleButtonCancelar() {
		getDialogStage().close();
	}

	// Validar entrada de dados para o cadastro
	private boolean validarEntradaDeDados() {
		String errorMessage = "";

		if (textFieldEditoraNome.getText() == null || textFieldEditoraNome.getText().length() == 0) {
			errorMessage += "Nome inv�lido!\n";
		}
		if (textFieldEditoraEndereco.getText() == null || textFieldEditoraEndereco.getText().length() == 0) {
			errorMessage += "Endere�o inv�lido!\n";
		}
		if (textFieldEditoraTelefone.getText() == null || textFieldEditoraTelefone.getText().length() == 0) {
			errorMessage += "Telefone inv�lido!\n";
		}
		if (textFieldEditoraContato.getText() == null || textFieldEditoraContato.getText().length() == 0) {
			errorMessage += "Contato inv�lido!\n";
		}
		if (textFieldEditoraStatus.getText() == null || textFieldEditoraStatus.getText().length() == 0) {
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
