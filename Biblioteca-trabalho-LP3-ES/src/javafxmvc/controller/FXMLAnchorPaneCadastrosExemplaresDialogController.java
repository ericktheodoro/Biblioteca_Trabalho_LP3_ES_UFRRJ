package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafxmvc.model.dao.EditoraDAO;
import javafxmvc.model.dao.ExemplarDAO;
import javafxmvc.model.dao.LivroDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Editora;
import javafxmvc.model.domain.Exemplar;
import javafxmvc.model.domain.Livro;

public class FXMLAnchorPaneCadastrosExemplaresDialogController implements Initializable {

    @FXML
    private Label labelExemplarIdExemplar;
    @FXML
    private Label labelExemplarNomeLivro;
    @FXML
    private Label labelExemplarReservado;
    @FXML
    private Label labelExemplarNomeEditora;
    @FXML
    private ComboBox<Livro> comboBoxLivroNome;
    @FXML
    private ComboBox<Editora> comboBoxEditoraNome;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Exemplar exemplar;

    private ObservableList<Livro> livros;
    private ObservableList<Editora> editoras;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ExemplarDAO exemplarDAO = new ExemplarDAO();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	exemplarDAO.setConnection (connection);
        carregarLivros();
        carregarEditoras();
    }

    private void carregarLivros() {
        LivroDAO livroDAO = new LivroDAO(connection);
        List<Livro> listaLivros = livroDAO.listarNomesEIds();
        if (listaLivros == null || listaLivros.isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
        } else {
            System.out.println("Livros carregados: " + listaLivros.size());
        }
        livros = FXCollections.observableArrayList(listaLivros);
        comboBoxLivroNome.setItems(livros);

        // Selecionar o livro atual, se disponível
        if (exemplar != null && exemplar.getLivro() != null) {
            comboBoxLivroNome.getSelectionModel().select(exemplar.getLivro());
        }
    }

    private void carregarEditoras() {
        EditoraDAO editoraDAO = new EditoraDAO(connection);
        List<Editora> listaEditoras = editoraDAO.listar();
        if (listaEditoras == null || listaEditoras.isEmpty()) {
            System.out.println("Nenhuma editora encontrada.");
        } else {
            System.out.println("Editoras carregadas: " + listaEditoras.size());
        }
        editoras = FXCollections.observableArrayList(listaEditoras);
        comboBoxEditoraNome.setItems(editoras);

        // Selecionar a editora atual, se disponível
        if (exemplar != null && exemplar.getEditora() != null) {
            comboBoxEditoraNome.getSelectionModel().select(exemplar.getEditora());
        }
    }
    public Stage getDialogStage() {
        return this.dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Exemplar getExemplar() {
        return this.exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        if (exemplar != null && exemplar.getLivro() != null && exemplar.getEditora() != null) {
            this.exemplar = exemplar;
            Livro livro = exemplar.getLivro();
            Editora editora = exemplar.getEditora();
            this.comboBoxLivroNome.setValue(livro);
            this.comboBoxEditoraNome.setValue(editora);
        } else {
            this.exemplar = new Exemplar(); // Inicializa um novo exemplar se o passado for nulo
            // Limpar campos ou tratar de outra forma caso exemplar ou livro sejam nulos
            comboBoxLivroNome.setValue(null);
            comboBoxEditoraNome.setValue(null);
        }
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            Livro livro = comboBoxLivroNome.getValue();
            Editora editora = comboBoxEditoraNome.getValue();

            exemplar = new Exemplar(); // Inicializa o exemplar se não estiver inicializado
            exemplar.setLivro(livro);
            exemplar.setEditora(editora);

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

        if (comboBoxLivroNome.getValue() == null) {
            errorMessage += "Selecione um Livro!\n";
        }
        if (comboBoxEditoraNome.getValue() == null) {
            errorMessage += "Selecione uma Editora!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrar mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }
    
    
}
