package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafxmvc.model.dao.LivroDAO;
import javafxmvc.model.domain.Autor;
import javafxmvc.model.domain.Livro;
import javafxmvc.model.domain.PalavraChave;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;

public class FXMLAnchorPaneCadastrosLivrosController implements Initializable {

    @FXML
    private TableView<Livro> tableViewLivros;
    @FXML
    private TableColumn<Livro, String> tableColumnLivroNome;
    @FXML
    private TableColumn<Livro, String> tableColumnLivroAutoresNome;
    @FXML
    private TableColumn<Livro, Date> tableColumnLivroDataPublicacao;
    @FXML
    private TableView<Autor> TableViewLivroAutores;
    @FXML
    private TableView<PalavraChave> TableViewLivroPalavrasChave;
    @FXML
    private TableColumn<PalavraChave, String> tableColumnLivroPalavrasChaveNome;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelLivroCodigo;
    @FXML
    private Label labelLivroNome;
    @FXML
    private Label labelLivroDataPublicacao;
    @FXML
    private Label labelLivroPrefacio;

    private ObservableList<Livro> observableListLivros;
    private ObservableList<Autor> observableListAutores;
    private ObservableList<PalavraChave> observableListPalavrasChave;

    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final LivroDAO livroDAO = new LivroDAO(connection);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            if (connection == null) {
                throw new RuntimeException("Failed to connect to database");
            }
            livroDAO.setConnection(connection);

            tableColumnLivroNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            tableColumnLivroDataPublicacao.setCellValueFactory(new PropertyValueFactory<>("dataPublicacao"));
            tableColumnLivroAutoresNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
            tableColumnLivroPalavrasChaveNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

            carregarTableViewLivros();
            selecionarItemTableViewLivros(null);

            tableViewLivros.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> selecionarItemTableViewLivros(newValue));
        } catch (Exception e) {
            showAlert("Erro na Inicialização", "Não foi possível inicializar o controlador.", e);
        }
    }

    public void carregarTableViewLivros() {
        try {
        	System.out.println("Carregando TableView de livros no cadastro");
            List<Livro> listLivros = livroDAO.listar();
            observableListLivros = FXCollections.observableArrayList(listLivros);
            tableViewLivros.setItems(observableListLivros);
        } catch (SQLException e) {
            showAlert("Erro ao Carregar Livros", "Não foi possível carregar a lista de livros.", e);
        }
    }

    public void selecionarItemTableViewLivros(Livro livro) {
        if (livro != null) {
            System.out.println("Selecionado livro: " + livro.getNome());
            labelLivroCodigo.setText(String.valueOf(livro.getIdLivro()));
            labelLivroNome.setText(livro.getNome());
            labelLivroDataPublicacao.setText(livro.getDataPublicacao().toString());
            labelLivroPrefacio.setText(livro.getPrefacio());

            try {
                System.out.println("Carregando TableView de autores e de palavras chave");

                // Carregar e verificar autores
                List<Autor> autores = livroDAO.listarAutoresPorLivro(livro.getIdLivro());
                if (autores != null) {
                    observableListAutores = FXCollections.observableArrayList(autores);
                    TableViewLivroAutores.setItems(observableListAutores);
                    System.out.println("Autores carregados com sucesso.");
                } else {
                    System.out.println("A lista de autores é null.");
                    TableViewLivroAutores.setItems(FXCollections.observableArrayList());
                }

                // Carregar e verificar palavras chave
                List<PalavraChave> palavrasChave = livroDAO.listarPalavrasChavePorLivro(livro.getIdLivro());
                if (palavrasChave != null) {
                    observableListPalavrasChave = FXCollections.observableArrayList(palavrasChave);
                    TableViewLivroPalavrasChave.setItems(observableListPalavrasChave);
                    System.out.println("Palavras chave carregadas com sucesso.");
                } else {
                    System.out.println("A lista de palavras chave é null.");
                    TableViewLivroPalavrasChave.setItems(FXCollections.observableArrayList());
                }
                
            } catch (SQLException e) {
                showAlert("Erro ao Carregar Autores ou Palavras Chave", "Não foi possível carregar a lista de autores ou de palavras chave.", e);
            }

        } else {
            System.out.println("Nenhum livro selecionado. Limpando detalhes.");
            labelLivroCodigo.setText("");
            labelLivroNome.setText("");
            labelLivroDataPublicacao.setText("");
            labelLivroPrefacio.setText("");

            if (TableViewLivroAutores != null) {
                TableViewLivroAutores.setItems(FXCollections.observableArrayList());
            } else {
                System.out.println("TableViewLivroAutores é null.");
            }

            if (TableViewLivroPalavrasChave != null) {
                TableViewLivroPalavrasChave.setItems(FXCollections.observableArrayList());
            } else {
                System.out.println("TableViewLivroPalavrasChave é null.");
            }
        }
    }


    @FXML
    public void handleButtonInserir() throws IOException {
        Livro livro = new Livro();
        boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosLivrosDialog(livro);
        
        if (buttonConfirmarClicked) {
            // Verifica se a lista de autores não é nula antes de inserir
            if (livro.getAutores() == null) {
                livro.setAutores(new ArrayList<>()); // Inicializa a lista se estiver nula
            }

            try {
                livroDAO.inserir(livro);
                carregarTableViewLivros();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void handleButtonAlterar() throws IOException {
        Livro livro = tableViewLivros.getSelectionModel().getSelectedItem();
        if (livro != null) {
            boolean buttonConfirmarClicked = showFXMLAnchorPaneCadastrosLivrosDialog(livro);
            if (buttonConfirmarClicked) {
                livroDAO.alterar(livro);
				carregarTableViewLivros();
            }
        } else {
            showAlert("Aviso", "Por favor, escolha um livro na tabela.");
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Livro livro = tableViewLivros.getSelectionModel().getSelectedItem();
        if (livro != null) {
            livroDAO.remover(livro);
			carregarTableViewLivros();
        } else {
            showAlert("Aviso", "Por favor, escolha um livro na tabela.");
        }
    }

    public boolean showFXMLAnchorPaneCadastrosLivrosDialog(Livro livro) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLAnchorPaneCadastrosLivrosDialogController.class.getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosLivrosDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Livros");

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        FXMLAnchorPaneCadastrosLivrosDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setLivro(livro);

        dialogStage.showAndWait();

        return controller.isButtonSalvarClicked();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
}
