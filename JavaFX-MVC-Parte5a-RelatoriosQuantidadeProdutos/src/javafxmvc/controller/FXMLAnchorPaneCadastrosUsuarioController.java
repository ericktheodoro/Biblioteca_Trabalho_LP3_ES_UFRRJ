package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.UsuarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Usuario;

public class FXMLAnchorPaneCadastrosUsuarioController implements Initializable {

    @FXML
    private TableView<Usuario> tableViewUsuario;
    @FXML
    private TableColumn<Usuario, String> tableColumnUsuarioNome;
    @FXML
    private TableColumn<Usuario, String> tableColumnUsuarioStatus;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelUsuarioCodigo;
    @FXML
    private Label labelUsuarioNome;
    @FXML
    private Label labelUsuarioEndereco;
    @FXML
    private Label labelUsuarioTelefone;
    @FXML
    private Label labelUsuarioStatus;

    private List<Usuario> listUsuario;
    private ObservableList<Usuario> observableListUsuario;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO.setConnection (connection);

        carregarTableViewUsuario();

        // Limpando a exibição dos detalhes do usuario
        selecionarItemTableViewUsuario(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewUsuario.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewUsuario(newValue));

    }

    public void carregarTableViewUsuario() {
        tableColumnUsuarioNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnUsuarioStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        listUsuario = usuarioDAO.listar();

        observableListUsuario = FXCollections.observableArrayList(listUsuario);
        tableViewUsuario.setItems(observableListUsuario);
    }

    public void selecionarItemTableViewUsuario(Usuario usuario) {
        if (usuario != null) {
            labelUsuarioCodigo.setText(String.valueOf(usuario.getidUsuario()));
            labelUsuarioNome.setText(usuario.getNome());
            labelUsuarioEndereco.setText(usuario.getEndereco());
            labelUsuarioTelefone.setText(usuario.getTelefone());
            labelUsuarioStatus.setText(usuario.getStatus());
        } else {
            labelUsuarioCodigo.setText("");
            labelUsuarioNome.setText("");
            labelUsuarioEndereco.setText("");
            labelUsuarioTelefone.setText("");
            labelUsuarioStatus.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Usuario usuario = new Usuario();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosUsuarioDialog(usuario);
        if (buttonConfirmarClicked) {
            usuarioDAO.inserir(usuario);
            carregarTableViewUsuario();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Usuario usuario = tableViewUsuario.getSelectionModel().getSelectedItem();//Obtendo usuario selecionado
        if (usuario != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosUsuarioDialog(usuario);
            if (buttonConfirmarClicked) {
                usuarioDAO.alterar(usuario);
                carregarTableViewUsuario();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um usuario na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Usuario usuario = tableViewUsuario.getSelectionModel().getSelectedItem();
        if (usuario != null) {
            usuarioDAO.remover(usuario);
            carregarTableViewUsuario();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um usuario na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneCadastrosUsuarioDialog(Usuario usuario) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosUsuarioDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosUsuarioDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Usuario");
        //Especifica a modalidade para esta fase . Isso deve ser feito antes de fazer o estágio visível. A modalidade pode ser: Modality.NONE , Modality.WINDOW_MODAL , ou Modality.APPLICATION_MODAL
        //dialogStage.initModality(Modality.WINDOW_MODAL);//WINDOW_MODAL (possibilita minimizar)

        //Especifica a janela do proprietário para esta página, ou null para um nível superior.
        //dialogStage.initOwner(null); //null deixa a Tela Principal livre para ser movida
        //dialogStage.initOwner(this.tableViewUsuario.getScene().getWindow()); //deixa a tela de Preenchimento dos dados como prioritária

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o usuario no Controller.
        FXMLAnchorPaneCadastrosUsuarioDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setUsuario(usuario);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }

}
