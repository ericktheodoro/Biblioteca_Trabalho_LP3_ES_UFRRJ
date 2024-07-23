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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.PalavraChaveDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.PalavraChave;

public class FXMLAnchorPaneCadastrosPalavrasChaveController implements Initializable {

    @FXML
    private TableView<PalavraChave> tableViewPalavrasChave;
    @FXML
    private TableColumn<PalavraChave, String> tableColumnPalavraChaveNome;
    @FXML
    private TableColumn<PalavraChave, String> tableColumnPalavraChaveStatus;
    @FXML
    private Label labelPalavraChaveCodigo;
    @FXML
    private Label labelPalavraChaveNome;
    @FXML
    private Label labelPalavraChaveStatus;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonDesabilitar;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private List<PalavraChave> listPalavrasChave;
    private ObservableList<PalavraChave> observableListPalavrasChave;

    // Atributos para manipulação de banco de dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final PalavraChaveDAO palavraChaveDAO = new PalavraChaveDAO(connection);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        palavraChaveDAO.setConnection(connection);
        carregarTableViewPalavraChave();

        // Listener acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewPalavrasChave.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewPalavrasChave(newValue));
    }

    public void carregarTableViewPalavraChave() {
        tableColumnPalavraChaveNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnPalavraChaveStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        listPalavrasChave = palavraChaveDAO.listar();

        observableListPalavrasChave = FXCollections.observableArrayList(listPalavrasChave);
        tableViewPalavrasChave.setItems(observableListPalavrasChave);
    }

    public void selecionarItemTableViewPalavrasChave(PalavraChave palavraChave) {
        if (palavraChave != null) {
            labelPalavraChaveCodigo.setText(String.valueOf(palavraChave.getIdPalavraChave()));
            labelPalavraChaveNome.setText(palavraChave.getNome());
            labelPalavraChaveStatus.setText(palavraChave.getStatusDescricao());
        } else {
            labelPalavraChaveCodigo.setText("");
            labelPalavraChaveNome.setText("");
            labelPalavraChaveStatus.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() throws IOException {
        PalavraChave palavraChave = new PalavraChave();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosPalavraChaveDialog(palavraChave);
        if (buttonConfirmarClicked) {
            palavraChaveDAO.inserir(palavraChave);
            carregarTableViewPalavraChave();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        PalavraChave palavraChave = tableViewPalavrasChave.getSelectionModel().getSelectedItem();
        if (palavraChave != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosPalavraChaveDialog(palavraChave);
            if (buttonConfirmarClicked) {
                palavraChaveDAO.alterar(palavraChave);
                carregarTableViewPalavraChave();
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma palavra-chave na Tabela!");
            alert.show();
        }
    }

    @FXML
    void handleButtonDesabilitar() {
        PalavraChave palavraChave = tableViewPalavrasChave.getSelectionModel().getSelectedItem();
        if (palavraChave != null) {
            if (palavraChaveDAO.desabilitar(palavraChave)) {
            	carregarTableViewPalavraChave();
            } else {
            	 Alert alert = new Alert(AlertType.ERROR);
                 alert.setContentText("Falha ao desabilitar palavra chave!");
                 alert.show();
            }
        } else {
        	 Alert alert = new Alert(AlertType.ERROR);
             alert.setContentText("Por favor, escolha uma palavra-chave na Tabela!");
             alert.show();
        }
    }
    @FXML
    void handleButtonHabilitar() {
        PalavraChave palavraChave = tableViewPalavrasChave.getSelectionModel().getSelectedItem();
        if (palavraChave != null) {
            if (palavraChaveDAO.habilitar(palavraChave)) {
            	carregarTableViewPalavraChave();
            } else {
            	 Alert alert = new Alert(AlertType.ERROR);
                 alert.setContentText("Falha ao desabilitar palavra chave!");
                 alert.show();
            }
        } else {
        	 Alert alert = new Alert(AlertType.ERROR);
             alert.setContentText("Por favor, escolha uma palavra-chave na Tabela!");
             alert.show();
        }
    }

    public boolean showFXMLAnchorPaneCadastrosPalavraChaveDialog(PalavraChave palavraChave) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosPalavrasChaveDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosPalavrasChaveDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Palavra-Chave");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando a palavraChave no Controller.
        FXMLAnchorPaneCadastrosPalavrasChaveDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setPalavraChave(palavraChave);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }
}
