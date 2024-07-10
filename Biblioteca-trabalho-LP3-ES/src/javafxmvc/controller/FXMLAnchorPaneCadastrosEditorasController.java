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
import javafxmvc.model.dao.EditoraDAO;
import javafxmvc.model.domain.Editora;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;

public class FXMLAnchorPaneCadastrosEditorasController implements Initializable{

    @FXML
    private TableView<Editora> tableViewEditoras;
    @FXML
    private TableColumn<Editora, String> tableColumnEditoraNome;
    @FXML
    private TableColumn<Editora, String> tableColumnEditoraStatus;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
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


    private List<Editora> listEditoras;
    private ObservableList<Editora> observableListEditoras;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final EditoraDAO editoraDAO = new EditoraDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editoraDAO.setConnection (connection);
        
        carregarTableViewEditoras();

        // Limpando a exibi��o dos detalhes do usu�rio
        selecionarItemTableViewEditoras(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewEditoras.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewEditoras(newValue));
        
    }

    public void carregarTableViewEditoras() {
        tableColumnEditoraNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnEditoraStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        listEditoras = editoraDAO.listar();

        observableListEditoras = FXCollections.observableArrayList(listEditoras);
        tableViewEditoras.setItems(observableListEditoras);
    }

    public void selecionarItemTableViewEditoras(Editora editora) {
        if (editora != null) {
            labelEditoraCodigo.setText(String.valueOf(editora.getIdEditora()));
            labelEditoraNome.setText(editora.getNome());
            labelEditoraEndereco.setText(editora.getEndereco());
            labelEditoraTelefone.setText(editora.getTelefone());
            labelEditoraContato.setText(editora.getContato());
            labelEditoraStatus.setText(editora.getStatus());
        } else {
            labelEditoraCodigo.setText("");
            labelEditoraNome.setText("");
            labelEditoraEndereco.setText("");
            labelEditoraTelefone.setText("");
            labelEditoraContato.setText("");
            labelEditoraStatus.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        Editora editora = new Editora();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosEditorasDialog(editora);
        if (buttonConfirmarClicked) {
           editoraDAO.inserir(editora);
            carregarTableViewEditoras();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Editora editora = tableViewEditoras.getSelectionModel().getSelectedItem();//Obtendo editora selecionado
        if (editora != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosEditorasDialog(editora);
            if (buttonConfirmarClicked) {
            	editoraDAO.alterar(editora);
                carregarTableViewEditoras();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma editora na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Editora editora = tableViewEditoras.getSelectionModel().getSelectedItem();
        if (editora != null) {
        	editoraDAO.remover(editora);
            carregarTableViewEditoras();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma editora na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneCadastrosEditorasDialog(Editora editora) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosEditorasDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosEditorasDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de usu�rios");
        //Especifica a modalidade para esta fase . Isso deve ser feito antes de fazer o estágio visível. 
        // A modalidade pode ser: Modality.NONE , Modality.WINDOW_MODAL , ou Modality.APPLICATION_MODAL 
        //dialogStage.initModality(Modality.WINDOW_MODAL);//WINDOW_MODAL (possibilita minimizar)
        
        //Especifica a janela do proprietário para esta página, ou null para um nível superior.
        //dialogStage.initOwner(null); //null deixa a Tela Principal livre para ser movida
        //dialogStage.initOwner(this.tableViewEditoras.getScene().getWindow()); //deixa a tela de Preenchimento dos dados como prioritária
        
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o usu�rio no Controller.
        FXMLAnchorPaneCadastrosEditorasDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setEditora(editora);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();

    }

}
