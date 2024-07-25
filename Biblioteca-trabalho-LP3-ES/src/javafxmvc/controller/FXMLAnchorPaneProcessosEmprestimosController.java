package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafxmvc.model.dao.EmprestimoDAO;
import javafxmvc.model.dao.ExemplarDAO;
import javafxmvc.model.dao.Session;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Emprestimo;
import javafxmvc.model.domain.Usuario;

public class FXMLAnchorPaneProcessosEmprestimosController implements Initializable {

    @FXML
    private TableView<Emprestimo> tableViewEmprestimos;
    @FXML
    private TableColumn<Emprestimo, Integer> tableColumnEmprestimoId;
    @FXML
    private TableColumn<Emprestimo, LocalDate> tableColumnEmprestimoData;
    @FXML
    private TableColumn<Emprestimo, Integer> tableColumnEmprestimoUsuario;  // Modificando tipo para Integer
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelEmprestimoId;
    @FXML
    private DatePicker datePickerEmprestimoData;
    @FXML
    private Label labelEmprestimoUsuario;
    @FXML
    private Label labelExemplarid;
    

    private List<Emprestimo> listEmprestimos;
    private ObservableList<Emprestimo> observableListEmprestimos;

    // Database connection
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final EmprestimoDAO emprestimoDAO = new EmprestimoDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        emprestimoDAO.setConnection(connection);
        carregarTableViewEmprestimos();
        
        tableColumnEmprestimoUsuario.setCellValueFactory(new PropertyValueFactory<>("id_Usuario"));

        // Listen for selection changes and show the details when changed
        tableViewEmprestimos.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selecionarItemTableViewEmprestimos(newValue));
    }

    public void carregarTableViewEmprestimos() {
        tableColumnEmprestimoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnEmprestimoData.setCellValueFactory(new PropertyValueFactory<>("dataEmprestimo"));
        tableColumnEmprestimoUsuario.setCellValueFactory(new PropertyValueFactory<>("id_Usuario")); 
        

        listEmprestimos = emprestimoDAO.listar();

        observableListEmprestimos = FXCollections.observableArrayList(listEmprestimos);
        tableViewEmprestimos.setItems(observableListEmprestimos);
    }

    public void selecionarItemTableViewEmprestimos(Emprestimo emprestimo) {
        if (emprestimo != null) {
            labelEmprestimoId.setText(String.valueOf(emprestimo.getId()));
            datePickerEmprestimoData.setValue(emprestimo.getDataEmprestimo());

            // Verifique se o usuário não é nulo antes de acessar seus métodos
            if (emprestimo.getUsuario() != null) {
                String nomeUsuario = emprestimo.getUsuario().getNome();
                labelEmprestimoUsuario.setText(nomeUsuario);
            } else {
                labelEmprestimoUsuario.setText("Usuário não disponível");
            }

            List<Integer> exemplaresIds = emprestimoDAO.listarExemplaresPorEmprestimo(emprestimo.getId());
            // Verifique se o exemplar e o livro não são nulos
            if (exemplaresIds != null && !exemplaresIds.isEmpty()) {
                // Converte a lista de IDs em uma String separada por vírgulas para exibir na label
                String exemplaresIdsString = exemplaresIds.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(", "));
                labelExemplarid.setText(exemplaresIdsString); // Atualize a label com os IDs dos exemplares
            } else {
                labelExemplarid.setText("Exemplares não disponíveis"); // Atualize a label correspondente
            }
        } else {
            labelEmprestimoId.setText("");
            datePickerEmprestimoData.setValue(null);
            labelEmprestimoUsuario.setText("");
            labelExemplarid.setText(""); // Limpe a label do livro também
        }
    }


    @FXML
    public void handleButtonInserir() throws IOException {
        // Obter o usuário atual da sessão
        Usuario usuarioAtual = Session.getInstance().getUsuarioAtual();
        
        if (usuarioAtual == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Sessão");
            alert.setHeaderText(null);
            alert.setContentText("Nenhum usuário está logado. Por favor, faça login novamente.");
            alert.showAndWait();
            return;
        }
        
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.getUsuario().setId_Usuario(usuarioAtual.getId_Usuario()); // Definir o ID do usuário no empréstimo
        
        boolean buttonConfirmarClicked = showFXMLAnchorPaneProcessosEmprestimosDialog(emprestimo);
        if (buttonConfirmarClicked) {
            emprestimoDAO.inserir(emprestimo);
            carregarTableViewEmprestimos();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Emprestimo emprestimo = tableViewEmprestimos.getSelectionModel().getSelectedItem();
        if (emprestimo != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneProcessosEmprestimosDialog(emprestimo);
            if (buttonConfirmarClicked) {
                emprestimoDAO.alterar(emprestimo);
                carregarTableViewEmprestimos();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um empréstimo na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Emprestimo emprestimo = tableViewEmprestimos.getSelectionModel().getSelectedItem();
        if (emprestimo != null) {
            emprestimoDAO.remover(emprestimo);
            carregarTableViewEmprestimos();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um empréstimo na Tabela!");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneProcessosEmprestimosDialog(Emprestimo emprestimo) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneProcessosEmprestimosDialogController.class.getResource("../view/FXMLAnchorPaneProcessosEmprestimosDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Empréstimos");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Set the emprestimo into the controller.
        FXMLAnchorPaneProcessosEmprestimosDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setEmprestimo(emprestimo);

        // Show the dialog and wait until the user closes it
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }
}

