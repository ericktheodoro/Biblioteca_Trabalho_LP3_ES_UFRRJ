package javafxmvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.domain.Usuario;

public class FXMLAnchorPaneCadastrosUsuariosDialogController implements Initializable {

    @FXML
    private Label labelUsuarioNome;
    @FXML
    private Label labelUsuarioEndereco;
    @FXML
    private Label labelUsuarioTelefone;
    @FXML
    private Label labelUsuarioUsuario;
    @FXML
    private Label labelUsuarioSenha;
    @FXML
    private Label labelUsuarioTipo;
    @FXML
    private TextField textFieldUsuarioNome;
    @FXML
    private TextField textFieldUsuarioEndereco;
    @FXML
    private TextField textFieldUsuarioTelefone;
    @FXML
    private TextField textFieldUsuarioUsuario;
    @FXML
    private PasswordField passwordFieldUsuarioSenha;
    @FXML
    private ChoiceBox<String> choiceBoxUsuarioTipo;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Usuario usuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        choiceBoxUsuarioTipo.getItems().addAll("Aluno", "Professor", "Funcionario");
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
        if (usuario != null) {
            textFieldUsuarioNome.setText(usuario.getNome());
            textFieldUsuarioEndereco.setText(usuario.getEndereco());
            textFieldUsuarioTelefone.setText(usuario.getTel());
            textFieldUsuarioUsuario.setText(usuario.getUsuario());
            passwordFieldUsuarioSenha.setText(usuario.getSenha());

            // Seleciona o tipo no ChoiceBox
            String tipoSelecionado = usuario.getTipoDescricao();
            choiceBoxUsuarioTipo.setValue(tipoSelecionado);
        }
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            if (usuario == null) {
                usuario = new Usuario();
            }

            usuario.setNome(textFieldUsuarioNome.getText());
            usuario.setEndereco(textFieldUsuarioEndereco.getText());
            usuario.setTel(textFieldUsuarioTelefone.getText());
            usuario.setUsuario(textFieldUsuarioUsuario.getText());
            usuario.setSenha(passwordFieldUsuarioSenha.getText());

            String tipoSelecionado = choiceBoxUsuarioTipo.getValue();
            char tipoBD;

            if (tipoSelecionado.equals("Aluno")) {
                tipoBD = '1';
            } else if (tipoSelecionado.equals("Professor")) {
                tipoBD = '2';
            } else if (tipoSelecionado.equals("Funcionario")) {
                tipoBD = '3';
            } else {
                tipoBD = '1';
            }

            usuario.setTipo(String.valueOf(tipoBD));

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }

    // Validar entrada de dados para o cadastro
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
        if (textFieldUsuarioUsuario.getText() == null || textFieldUsuarioUsuario.getText().length() == 0) {
            errorMessage += "Usuário inválido!\n";
        }
        if (passwordFieldUsuarioSenha.getText() == null || passwordFieldUsuarioSenha.getText().length() == 0) {
            errorMessage += "Senha inválida!\n";
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
