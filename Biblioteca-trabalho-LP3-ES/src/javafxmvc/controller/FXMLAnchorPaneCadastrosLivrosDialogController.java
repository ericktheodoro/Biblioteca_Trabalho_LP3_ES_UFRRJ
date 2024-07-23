package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxmvc.model.dao.AutorDAO;
import javafxmvc.model.dao.LivroDAO;
import javafxmvc.model.dao.PalavraChaveDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Autor;
import javafxmvc.model.domain.Livro;
import javafxmvc.model.domain.PalavraChave;

public class FXMLAnchorPaneCadastrosLivrosDialogController implements Initializable {

    @FXML
    private Label labelNome;
    @FXML
    private Label labelDataPublicacao;
    @FXML
    private Label labelPrefacio;
    @FXML
    private TextField textFieldCodigo;
    @FXML
    private TextField textFieldNome;
    @FXML
    private DatePicker DatePickerDataPublicacao;
    @FXML
    private TextField textFieldPrefacio;
    @FXML
    private TableView<Autor> tableViewAutores;
    @FXML
    private TableColumn<Autor, String> tableColumnAutorNome;
    @FXML
    private TableView<PalavraChave> tableViewPalavrasChave;
    @FXML
    private TableColumn<PalavraChave, String> tableColumnPalavraChaveNome;
    @FXML
    private Button buttonAdicionarAutor;
    @FXML
    private Button buttonRemoverAutor;
    @FXML
    private Button buttonAdicionarPalavraChave;
    @FXML
    private Button buttonRemoverPalavraChave;
    @FXML
    private Button buttonSalvar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private boolean buttonSalvarClicked = false;

    private Stage dialogStage;
    private Livro livro;
    private ObservableList<Autor> autores;
    private ObservableList<PalavraChave> palavrasChave;
    
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final LivroDAO livroDAO = new LivroDAO(connection);
    private final PalavraChaveDAO palavraChaveDAO = new PalavraChaveDAO(connection);
    private final AutorDAO autorDAO = new AutorDAO(connection);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if (connection == null) {
                throw new RuntimeException("Failed to connect to database");
            }
            livroDAO.setConnection(connection);
            palavraChaveDAO.setConnection(connection);
            autorDAO.setConnection(connection);

            tableColumnAutorNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            tableColumnPalavraChaveNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

            autores = FXCollections.observableArrayList();
            palavrasChave = FXCollections.observableArrayList();
            
            livro = new Livro();
            carregarAutores();
            carregarPalavrasChave();
        } catch (Exception e) {
            showAlert("Erro na Inicialização", "Não foi possível inicializar o controlador.", e);
        }
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
        textFieldNome.setText(livro.getNome());
        DatePickerDataPublicacao.setValue(livro.getDataPublicacao());
        textFieldPrefacio.setText(livro.getPrefacio());

        if (livro.getAutores() != null) {
            autores = FXCollections.observableArrayList(livro.getAutores());
            tableViewAutores.setItems(autores);
        }
        
        if (livro.getPalavrasChave() != null) {
            palavrasChave = FXCollections.observableArrayList(livro.getPalavrasChave());
            tableViewPalavrasChave.setItems(palavrasChave);
        }
    }

    @FXML
    private void handleButtonAdicionarAutor() {
        Autor autorSelecionado = tableViewAutores.getSelectionModel().getSelectedItem();
        if (autorSelecionado != null) {
            try {
                if (livroDAO.adicionarAutorAoLivro(autorSelecionado.getIdAutor())) {
                    carregarAutores(); // Atualiza a lista de autores
                } else {
                    showAlert("Erro", "Não foi possível adicionar o autor.", null);
                }
            } catch (Exception e) {
                showAlert("Erro", "Não foi possível adicionar o autor.", e);
            }
        } else {
            showAlert("Aviso", "Nenhum autor selecionado.", null);
        }
    }

    @FXML
    private void handleButtonRemoverAutor() {
        Autor autorSelecionado = tableViewAutores.getSelectionModel().getSelectedItem();
        if (autorSelecionado != null) {
            try {
                if (livroDAO.removerAutorDoLivro(autorSelecionado.getIdAutor())) {
                    showAlert("Sucesso", "Autor removido com sucesso!", null);
                    carregarAutores(); // Atualiza a lista de autores
                } else {
                    showAlert("Erro", "Não foi possível remover o autor.", null);
                }
            } catch (Exception e) {
                showAlert("Erro", "Não foi possível remover o autor.", e);
            }
        } else {
            showAlert("Aviso", "Nenhum autor selecionado.", null);
        }
    }

    @FXML
    private void handleButtonAdicionarPalavraChave() {
        PalavraChave palavraChaveSelecionada = tableViewPalavrasChave.getSelectionModel().getSelectedItem();
        if (palavraChaveSelecionada != null) {
            try {
                if (livroDAO.adicionarPalavraChaveAoLivro(palavraChaveSelecionada.getIdPalavraChave())) {
                    carregarPalavrasChave(); // Atualiza a lista de palavras-chave
                } else {
                    showAlert("Erro", "Não foi possível adicionar a palavra-chave.", null);
                }
            } catch (Exception e) {
                showAlert("Erro", "Não foi possível adicionar a palavra-chave.", e);
            }
        } else {
            showAlert("Aviso", "Nenhuma palavra-chave selecionada.", null);
        }
    }

    @FXML
    private void handleButtonRemoverPalavraChave() {
        PalavraChave palavraChaveSelecionada = tableViewPalavrasChave.getSelectionModel().getSelectedItem();
        if (palavraChaveSelecionada != null) {
            try {
                if (livroDAO.removerPalavraChaveDoLivro(palavraChaveSelecionada.getIdPalavraChave())) {
                    showAlert("Sucesso", "Palavra-chave removida com sucesso!", null);
                    carregarPalavrasChave(); // Atualiza a lista de palavras-chave
                } else {
                    showAlert("Erro", "Não foi possível remover a palavra-chave.", null);
                }
            } catch (Exception e) {
                showAlert("Erro", "Não foi possível remover a palavra-chave.", e);
            }
        } else {
            showAlert("Aviso", "Nenhuma palavra-chave selecionada.", null);
        }
    }

    @FXML
    private void handleButtonSalvar() {
        if (validarEntradaDeDados()) {
            livro.setNome(textFieldNome.getText());
            livro.setDataPublicacao(DatePickerDataPublicacao.getValue());
            livro.setPrefacio(textFieldPrefacio.getText());

            buttonSalvarClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }
    
    private void carregarAutores() {
        try {
            ObservableList<Autor> autores = FXCollections.observableArrayList(autorDAO.listar());
            tableViewAutores.setItems(autores);
        } catch (Exception e) {
            showAlert("Erro ao Carregar Autores", "Não foi possível carregar a lista de autores.", e);
        }
    }
    
    private void carregarPalavrasChave() {
        try {
            ObservableList<PalavraChave> palavrasChave = FXCollections.observableArrayList(palavraChaveDAO.listar());
            tableViewPalavrasChave.setItems(palavrasChave); 
        } catch (Exception e) {
            showAlert("Erro ao Carregar Palavras-Chave", "Não foi possível carregar a lista de palavras-chave.", e);
        }
    }
    
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        if (textFieldNome.getText() == null || textFieldNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (DatePickerDataPublicacao.getValue() == null) {
            errorMessage += "Data de Publicação inválida!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            showAlert("Campos Inválidos", "Por favor, corrija os campos inválidos:\n" + errorMessage, null);
            return false;
        }
    }
    
    private void showAlert(String title, String message, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        if (e != null) {
            alert.setContentText(message + "\n" + e.getMessage());
        }
        alert.showAndWait();
    }
    
	public boolean isButtonSalvarClicked() {
		return buttonSalvarClicked;
	}
}
