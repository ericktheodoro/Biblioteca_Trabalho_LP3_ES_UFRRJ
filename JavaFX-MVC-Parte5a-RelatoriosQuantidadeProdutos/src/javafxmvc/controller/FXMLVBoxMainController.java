package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class FXMLVBoxMainController implements Initializable {

    @FXML
    private MenuItem menuItemCadastrosUsuario;

    @FXML
    private MenuItem menuItemProcessosVendas;

    @FXML
    private MenuItem menuItemGraficosVendasPorMes;

    @FXML
    private MenuItem menuItemRelatoriosQuantidadeProdutos;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void handleMenuItemCadastrosUsuario() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneCadastrosUsuario.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void handleMenuItemProcessosVendas() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneProcessosVendas.fxml"));
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

}
