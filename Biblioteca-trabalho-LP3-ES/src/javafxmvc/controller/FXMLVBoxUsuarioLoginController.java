package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafxmvc.model.dao.UsuarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Usuario;

public class FXMLVBoxUsuarioLoginController implements Initializable {

    @FXML
    private VBox vboxLogin;
    @FXML
    private TextField textFieldLogin;
    @FXML
    private PasswordField passwordFieldSenha;
    @FXML
    private Button buttonEntrar;
    @FXML
    private Button buttonCancelar;

    // Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO.setConnection(connection);
    }

    @FXML
    public void handleButtonEntrar() {
        System.out.println("handleButtonEntrar called");
        String login = textFieldLogin.getText();
        String senha = passwordFieldSenha.getText();
        System.out.println("Login: " + login + ", Senha: " + senha);

        Usuario usuario = usuarioDAO.buscarPorLoginSenha(login, senha);
        if (usuario != null) {
            System.out.println("Usuário encontrado: " + usuario.getNome());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login bem-sucedido");
            alert.setHeaderText(null);
            alert.setContentText(usuario.getTipoDescricao() + " " + usuario.getNome() + " logado com sucesso!");
            alert.showAndWait();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafxmvc/view/FXMLVBoxMain.fxml"));
                VBox root = loader.load();

                // Pegue o controlador da nova tela para inicializar dados, se necessário
                FXMLVBoxMainController mainController = loader.getController();
                // mainController.initData(usuario);

                System.out.println("Setting new root to vboxLogin");
                
                // Check if vboxLogin is not null
                if (vboxLogin != null) {
                    vboxLogin.getChildren().setAll(root);
                } else {
                    System.out.println("vboxLogin is null");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Usuário ou senha incorretos.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Login");
            alert.setHeaderText(null);
            alert.setContentText("Usuário ou senha incorretos.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleButtonCancelar() {
        textFieldLogin.clear();
        passwordFieldSenha.clear();
    }
}
