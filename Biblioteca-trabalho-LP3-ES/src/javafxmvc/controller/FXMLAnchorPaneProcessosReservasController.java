package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javafxmvc.model.dao.ReservaDAO;
import javafxmvc.model.dao.ReservaExemplarDAO;
import javafxmvc.model.dao.UsuarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Exemplar;
import javafxmvc.model.domain.Livro;
import javafxmvc.model.domain.Reserva;
import javafxmvc.model.domain.ReservaExemplar;
import javafxmvc.model.domain.Usuario;


public class FXMLAnchorPaneProcessosReservasController implements Initializable {

    @FXML
    private TableView<Reserva> tableViewReservas;
    @FXML
    private TableColumn<Reserva, Integer> tableColumnReservaId;
    @FXML
    private TableColumn<Reserva, LocalDate> tableColumnReservaData;
    @FXML
    private TableColumn<Reserva, LocalDate> tableColumnReservaEmprestimo;
    @FXML
    private TableColumn<Reserva, Usuario> tableColumnReservaUsuario;
    @FXML
    private Button buttonAlterar;
    @FXML
    private Button buttonInserir;
    @FXML
    private Button buttonRemover;
    @FXML
    private Label labelReservaId;
    @FXML
    private DatePicker datePickerReservaData;
    @FXML
    private DatePicker datePickerReservaEmprestimo;
    @FXML
    private Label labelReservaUsuario;

    private List<Reserva> listReservas;
    private ObservableList<Reserva> observableListReservas;

    // Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final ReservaExemplarDAO reservaExemplarDAO = new ReservaExemplarDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reservaDAO.setConnection(connection);

        carregarTableViewReservas();

        // Limpando a exibição dos detalhes da reserva
        selecionarItemTableViewReservas(null);

        // Listen acionado diante de quaisquer alterações na seleção de itens do TableView
        tableViewReservas.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewReservas(newValue));
    }

    public void carregarTableViewReservas() {
        tableColumnReservaId.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        tableColumnReservaData.setCellValueFactory(new PropertyValueFactory<>("dtReserva"));
        tableColumnReservaEmprestimo.setCellValueFactory(new PropertyValueFactory<>("dtEmprestimo"));
        tableColumnReservaUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        listReservas = reservaDAO.listar();

        observableListReservas = FXCollections.observableArrayList(listReservas);
        tableViewReservas.setItems(observableListReservas);
    }

    public void selecionarItemTableViewReservas(Reserva reserva) {
        if (reserva != null) {
            labelReservaId.setText(String.valueOf(reserva.getIdReserva()));
            datePickerReservaData.setValue(reserva.getDtReserva());
            datePickerReservaEmprestimo.setValue(reserva.getDtEmprestimo());
            labelReservaUsuario.setText(reserva.getUsuario().toString());
        } else {
            labelReservaId.setText("");
            datePickerReservaData.setValue(null);
            datePickerReservaEmprestimo.setValue(null);
            labelReservaUsuario.setText("");
        }
    }

    @FXML
    public void handleButtonInserir() {
        Reserva reserva = new Reserva();
        reserva.setDtReserva(datePickerReservaData.getValue());
        reserva.setDtEmprestimo(datePickerReservaEmprestimo.getValue());
        reserva.setUsuario(tableViewReservas.getSelectionModel().getSelectedItem().getUsuario());

        List<Exemplar> itensParaReservar = getItensParaReservar(); // Obtém a lista de exemplares para reserva

        try {
            connection.setAutoCommit(false);
            reservaDAO.setConnection(connection);
            reservaDAO.inserir(reserva);

            reservaExemplarDAO.setConnection(connection);
            for (Exemplar exemplar : itensParaReservar) {
                ReservaExemplar reservaExemplar = new ReservaExemplar();
                reservaExemplar.setReserva(reserva);
                reservaExemplar.setExemplar(exemplar);
                reservaExemplarDAO.inserir(reservaExemplar);

                // Aqui você precisa ajustar para alterar o estado do exemplar para "reservado"
                exemplar.setReservado("S"); 
            }

            connection.commit();
            carregarTableViewReservas(); // Recarrega a tabela de reservas
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(FXMLAnchorPaneProcessosReservasController.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(FXMLAnchorPaneProcessosReservasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    // Método para obter a lista de itens para reservar (exemplo)
    private List<Exemplar> getItensParaReservar() {
        // Criar instâncias de Livro
        Livro livro1 = new Livro("Exemplar 1");
        Livro livro2 = new Livro("Exemplar 2");

        // Criar instâncias de Exemplar
        Exemplar exemplar1 = new Exemplar(livro1);
        Exemplar exemplar2 = new Exemplar(livro2);

        // Retornar a lista de exemplares
        return Arrays.asList(exemplar1, exemplar2);
    }



    @FXML
    public void handleButtonAlterar() {
        Reserva reserva = tableViewReservas.getSelectionModel().getSelectedItem();
        if (reserva != null) {
            try {
                connection.setAutoCommit(false);
                reservaDAO.setConnection(connection);
                reservaDAO.alterar(reserva);
                connection.commit();
                carregarTableViewReservas();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(FXMLAnchorPaneProcessosReservasController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLAnchorPaneProcessosReservasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione uma reserva na Tabela.");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() {
        Reserva reserva = tableViewReservas.getSelectionModel().getSelectedItem();
        if (reserva != null) {
            try {
                connection.setAutoCommit(false);
                reservaExemplarDAO.setConnection(connection);
                for (ReservaExemplar reservaExemplar : reserva.getReservaExemplar()) {
                    reservaExemplarDAO.remover(reservaExemplar);
                }
                reservaDAO.setConnection(connection);
                reservaDAO.remover(reserva);
                connection.commit();
                carregarTableViewReservas();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(FXMLAnchorPaneProcessosReservasController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLAnchorPaneProcessosReservasController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione uma reserva na Tabela.");
            alert.show();
        }
    }

    public boolean showFXMLAnchorPaneReservasDialog(Reserva reserva) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneProcessosReservasDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        // Criar o Stage de DialogStage
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Reservas");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        // Setar a reserva no controller.
        FXMLAnchorPaneProcessosReservasDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setReserva(reserva);

        // Mostrar o dialog e esperar até o usuário fechar
        dialogStage.showAndWait();

        return controller.isButtonConfirmarClicked();
    }


}
