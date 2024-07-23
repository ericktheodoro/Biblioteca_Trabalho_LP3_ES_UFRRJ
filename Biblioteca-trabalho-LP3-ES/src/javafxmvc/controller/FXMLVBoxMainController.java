package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafxmvc.model.dao.LivroDAO;
import javafxmvc.model.domain.Autor;
import javafxmvc.model.domain.Livro;
import javafxmvc.model.domain.PalavraChave;

public class FXMLVBoxMainController implements Initializable {

    @FXML
    private MenuItem menuItemCadastrosClientes;
    
    @FXML
    private MenuItem menuItemCadastrosUsuarios;
    
    @FXML
    private MenuItem menuItemProcessosVendas;
    
    @FXML
    private MenuItem menuItemProcessosEmprestimos;
    
    @FXML
    private MenuItem menuItemCadastrosAutores;
    
    @FXML
    private MenuItem menuItemCadastrosExemplares;
    
    @FXML
    private MenuItem menuItemCadastrosLivros;
    
    @FXML
    private MenuItem menuItemGraficosVendasPorMes;
    
    @FXML
    private MenuItem menuItemRelatoriosQuantidadeProdutos;
    
    @FXML
    private MenuItem menuItemCadastrosPalavrasChave;

    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private MenuItem menuItemCadastrosEditoras;
    @FXML
    private ImageView imagembiblioteca;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	String imagePath = "/images/biblioteca.png";
    	Image image = new Image(getClass().getResourceAsStream(imagePath));
    	imagembiblioteca.setImage(image);
    }
    
    public void initData(Object data) {
        // Inicializar dados se necessário
    }
    @FXML
    public void handleMenuItemCadastrosClientes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosClientes.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    @FXML
    public void handleMenuItemCadastrosExemplares() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosExemplares.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    @FXML
    public void handleMenuItemCadastrosUsuarios() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosUsuarios.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    @FXML
    public void handleMenuItemCadastrosEditoras() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosEditoras.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    @FXML
    public void handleMenuItemCadastrosAutores() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosAutores.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemCadastrosLivros() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosLivros.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemProcessosVendas() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneProcessosVendas.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemProcessosEmprestimos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneProcessosEmprestimos.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemGraficosVendasPorMes() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneGraficosVendasPorMes.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemRelatoriosQuantidadeProdutos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneRelatoriosQuantidadeProdutos.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuItemCadastrosPalavrasChave() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosPalavrasChave.fxml"));
        anchorPane.getChildren().setAll(a);
    }

}
