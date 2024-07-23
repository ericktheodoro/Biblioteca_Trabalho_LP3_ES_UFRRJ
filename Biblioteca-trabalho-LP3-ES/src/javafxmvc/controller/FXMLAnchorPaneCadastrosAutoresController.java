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
import javafxmvc.model.dao.AutorDAO;
import javafxmvc.model.domain.Autor;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;

public class FXMLAnchorPaneCadastrosAutoresController implements Initializable {

    @FXML
    private TableView<Autor> tableViewAutores;
    @FXML
    private TableColumn<Autor, String> tableColumnAutorNome;
    @FXML
    private TableColumn<Autor, String> tableColumnAutorStatus;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
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


    private List<Autor> listAutores;
    private ObservableList<Autor> observableListAutores;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final AutorDAO autorDAO = new AutorDAO(connection);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        carregarTableViewAutores();

        // Limpando a exibi��o dos detalhes do autor
        selecionarItemTableViewAutores(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewAutores.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewAutores(newValue));
        
    }

    public void carregarTableViewAutores() {
        tableColumnAutorNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnAutorStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        listAutores = autorDAO.listar();

        observableListAutores = FXCollections.observableArrayList(listAutores);
        tableViewAutores.setItems(observableListAutores);
    }

    public void selecionarItemTableViewAutores(Autor autor) {
        if (autor != null) {
            labelAutorCodigo.setText(String.valueOf(autor.getIdAutor()));
            labelAutorNome.setText(autor.getNome());
            labelAutorTelefone.setText(autor.getTelefone());
            labelAutorStatus.setText(autor.getStatus());
        } else {
            labelAutorCodigo.setText("");
            labelAutorNome.setText("");
            labelAutorTelefone.setText("");
            labelAutorStatus.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Autor autor = new Autor();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosAutoresDialog(autor);
        if (buttonConfirmarClicked) {
           autorDAO.inserir(autor);
            carregarTableViewAutores();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Autor autor = tableViewAutores.getSelectionModel().getSelectedItem();//Obtendo autor selecionado
        if (autor != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosAutoresDialog(autor);
            if (buttonConfirmarClicked) {
            	autorDAO.alterar(autor);
                carregarTableViewAutores();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um autor na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Autor autor = tableViewAutores.getSelectionModel().getSelectedItem();
        if (autor != null) {
        	autorDAO.remover(autor);
            carregarTableViewAutores();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um autor na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneCadastrosAutoresDialog(Autor autor) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosAutoresDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosAutoresDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de autores");
        //Especifica a modalidade para esta fase . Isso deve ser feito antes de fazer o estágio visível. 
        // A modalidade pode ser: Modality.NONE , Modality.WINDOW_MODAL , ou Modality.APPLICATION_MODAL 
        //dialogStage.initModality(Modality.WINDOW_MODAL);//WINDOW_MODAL (possibilita minimizar)
        
        //Especifica a janela do proprietário para esta página, ou null para um nível superior.
        //dialogStage.initOwner(null); //null deixa a Tela Principal livre para ser movida
        //dialogStage.initOwner(this.tableViewAutores.getScene().getWindow()); //deixa a tela de Preenchimento dos dados como prioritária
        
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o autor no Controller.
        FXMLAnchorPaneCadastrosAutoresDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setAutor(autor);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }

}
