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
import javafxmvc.model.domain.Usuario;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;

public class FXMLAnchorPaneCadastrosUsuariosController implements Initializable {

    @FXML
    private TableView<Usuario> tableViewUsuarios;
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
    @FXML
    private Label labelUsuarioTipo;


    private List<Usuario> listUsuarios;
    private ObservableList<Usuario> observableListUsuarios;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO.setConnection (connection);
        
        carregarTableViewUsuarios();

        // Limpando a exibi��o dos detalhes do usu�rio
        selecionarItemTableViewUsuarios(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewUsuarios.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewUsuarios(newValue));
        
    }

    public void carregarTableViewUsuarios() {
        tableColumnUsuarioNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnUsuarioStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        listUsuarios = usuarioDAO.listar();

        observableListUsuarios = FXCollections.observableArrayList(listUsuarios);
        tableViewUsuarios.setItems(observableListUsuarios);
    }

    public void selecionarItemTableViewUsuarios(Usuario usuario) {
        if (usuario != null) {
            labelUsuarioCodigo.setText(String.valueOf(usuario.getIdUsuario()));
            labelUsuarioNome.setText(usuario.getNome());
            labelUsuarioEndereco.setText(usuario.getEndereco());
            labelUsuarioTelefone.setText(usuario.getTel());
            labelUsuarioStatus.setText(usuario.getStatus());
            labelUsuarioTipo.setText(usuario.getTipo());
        } else {
            labelUsuarioCodigo.setText("");
            labelUsuarioNome.setText("");
            labelUsuarioEndereco.setText("");
            labelUsuarioTelefone.setText("");
            labelUsuarioStatus.setText("");
            labelUsuarioTipo.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Usuario usuario = new Usuario();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosUsuariosDialog(usuario);
        if (buttonConfirmarClicked) {
           usuarioDAO.inserir(usuario);
            carregarTableViewUsuarios();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Usuario usuario = tableViewUsuarios.getSelectionModel().getSelectedItem();//Obtendo usuario selecionado
        if (usuario != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosUsuariosDialog(usuario);
            if (buttonConfirmarClicked) {
            	usuarioDAO.alterar(usuario);
                carregarTableViewUsuarios();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um usu�rio na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Usuario usuario = tableViewUsuarios.getSelectionModel().getSelectedItem();
        if (usuario != null) {
        	usuarioDAO.remover(usuario);
            carregarTableViewUsuarios();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um usu�rio na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneCadastrosUsuariosDialog(Usuario usuario) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosUsuariosDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosUsuariosDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de usu�rios");
        //Especifica a modalidade para esta fase . Isso deve ser feito antes de fazer o estágio visível. 
        // A modalidade pode ser: Modality.NONE , Modality.WINDOW_MODAL , ou Modality.APPLICATION_MODAL 
        //dialogStage.initModality(Modality.WINDOW_MODAL);//WINDOW_MODAL (possibilita minimizar)
        
        //Especifica a janela do proprietário para esta página, ou null para um nível superior.
        //dialogStage.initOwner(null); //null deixa a Tela Principal livre para ser movida
        //dialogStage.initOwner(this.tableViewUsuarios.getScene().getWindow()); //deixa a tela de Preenchimento dos dados como prioritária
        
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o usu�rio no Controller.
        FXMLAnchorPaneCadastrosUsuariosDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setUsuario(usuario);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }

}
