package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.EditoraDAO;
import javafxmvc.model.dao.ExemplarDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Editora;
import javafxmvc.model.domain.Exemplar;
import javafxmvc.model.domain.Livro;

public class FXMLAnchorPaneCadastrosExemplaresController implements Initializable {

    @FXML
    private TableView<Exemplar> tableViewExemplares;
    @FXML
    private TableColumn<Exemplar, String> tableColumnExemplarLivro;
    @FXML
    private TableColumn<Exemplar, String> tableColumnExemplarReservado;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelExemplarIdExemplar;
    @FXML
    private Label labelExemplarNomeLivro;
    @FXML
    private Label labelExemplarNomeEditora;    
    @FXML
    private Label labelExemplarReservado;
    @FXML
    private TextField textFieldExemplarNomeLivro;
    @FXML
    private TextField textFieldExemplarNomeEditora;
    @FXML
    private TextField textFieldExemplarStatus;
    @FXML
    private ComboBox<Editora> comboBoxEditoraNome;  // Assumindo que existe uma ComboBox para Editora

    private List<Exemplar> listExemplares;
    private ObservableList<Exemplar> observableListExemplares;

    // Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ExemplarDAO exemplarDAO = new ExemplarDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        exemplarDAO.setConnection(connection);
        
        carregarTableViewExemplares();

        // Limpando a exibição dos detalhes do exemplar
        selecionarItemTableViewExemplares(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewExemplares.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewExemplares(newValue));

        EditoraDAO editoraDAO = new EditoraDAO();
        editoraDAO.setConnection(connection); // Configura a conexão no DAO
    }
    
    @FXML
    private void handleButtonInserir(ActionEvent event) {
        try {
            // Assumindo que o livro está sendo obtido de alguma forma, ajuste conforme necessário
            Livro livro = new Livro(); // Obtenha o livro de acordo com sua lógica

            Editora editora = comboBoxEditoraNome.getSelectionModel().getSelectedItem();

            if (livro == null) {
                throw new IllegalArgumentException("Livro cannot be null when inserting Exemplar.");
            }
            
            if (editora == null) {
                throw new IllegalArgumentException("Editora cannot be null when inserting Exemplar.");
            }

            Exemplar exemplar = new Exemplar();
            exemplar.setLivro(livro);
            exemplar.setEditora(editora);
            exemplarDAO.inserir(exemplar);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Exemplar inserted successfully!");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error inserting Exemplar: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void carregarTableViewExemplares() {
        tableColumnExemplarLivro.setCellValueFactory(cellData -> {
            Exemplar exemplar = cellData.getValue();
            return new SimpleStringProperty(exemplar.getLivro().getNome());
        });
        tableColumnExemplarReservado.setCellValueFactory(new PropertyValueFactory<>("reservado"));

        listExemplares = exemplarDAO.listar();

        observableListExemplares = FXCollections.observableArrayList(listExemplares);
        tableViewExemplares.setItems(observableListExemplares);
    }

    public void selecionarItemTableViewExemplares(Exemplar exemplar) {
        if (exemplar != null) {
            labelExemplarIdExemplar.setText(String.valueOf(exemplar.getIdExemplar()));
            
            Livro livro = exemplar.getLivro();
            if (livro != null) {
                labelExemplarNomeLivro.setText(livro.getNome());
            } else {
                labelExemplarNomeLivro.setText("Livro não especificado");
            }

            Editora editora = exemplar.getEditora();
            if (editora != null) {
                labelExemplarNomeEditora.setText(editora.getNome());
            } else {
                labelExemplarNomeEditora.setText("Editora não especificada");
            }

            labelExemplarReservado.setText(exemplar.getReservado());

        } else {
            labelExemplarIdExemplar.setText("");
            labelExemplarNomeLivro.setText("");
            labelExemplarNomeEditora.setText("");
            labelExemplarReservado.setText("");
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Exemplar exemplar = tableViewExemplares.getSelectionModel().getSelectedItem(); // Obtendo exemplar selecionado
        if (exemplar != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosExemplaresDialog(exemplar);
            if (buttonConfirmarClicked) {
                exemplarDAO.alterar(exemplar);
                carregarTableViewExemplares();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um exemplar na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Exemplar exemplar = tableViewExemplares.getSelectionModel().getSelectedItem();
        if (exemplar != null) {
            exemplarDAO.remover(exemplar);
            carregarTableViewExemplares();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um exemplar na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneCadastrosExemplaresDialog(Exemplar exemplar) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosExemplaresDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosExemplaresDialog.fxml"));
        AnchorPane page = loader.load();

        // Criando um Estágio de Diálogo (Stage Dialog)
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de exemplares");
        // Especifica a modalidade para esta fase . Isso deve ser feito antes de fazer o estágio visível. 
        // A modalidade pode ser: Modality.NONE , Modality.WINDOW_MODAL , ou Modality.APPLICATION_MODAL 
        // dialogStage.initModality(Modality.WINDOW_MODAL);//WINDOW_MODAL (possibilita minimizar)
        
        // Especifica a janela do proprietário para esta página, ou null para um nível superior.
        // dialogStage.initOwner(null); // null deixa a Tela Principal livre para ser movida
        // dialogStage.initOwner(this.tableViewExemplares.getScene().getWindow()); //deixa a tela de Preenchimento dos dados como prioritária
        
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setando o exemplar no Controller.
        FXMLAnchorPaneCadastrosExemplaresDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setExemplar(exemplar);

        // Mostra o Dialog e espera até que o usuário o feche
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }
}
