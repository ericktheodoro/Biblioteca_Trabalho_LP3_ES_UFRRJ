package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxmvc.model.dao.ReservaDAO;
import javafxmvc.model.dao.ReservaExemplarDAO;
import javafxmvc.model.dao.UsuarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Exemplar;
import javafxmvc.model.domain.Reserva;
import javafxmvc.model.domain.ReservaExemplar;
import javafxmvc.model.domain.Usuario;

public class FXMLAnchorPaneProcessosReservasDialogController implements Initializable {

    @FXML
    private ComboBox<Usuario> comboBoxReservaUsuario;
    @FXML
    private DatePicker datePickerReservaData;
    @FXML
    private DatePicker datePickerReservaEmprestimo;
    @FXML
    private TableView<ReservaExemplar> tableViewReservaExemplares;
    @FXML
    private TableColumn<ReservaExemplar, LocalDate> tableColumnReservaExemplarCancelamento;
    @FXML
    private TableColumn<ReservaExemplar, Reserva> tableColumnReservaExemplarReserva;
    @FXML
    private TableColumn<ReservaExemplar, Exemplar> tableColumnReservaExemplarExemplar;
    @FXML
    private Button buttonConfirmar;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonAdicionarExemplar;

    private List<Exemplar> listExemplares;
    private ObservableList<Exemplar> observableListExemplares;
    private ObservableList<ReservaExemplar> observableListReservaExemplares;

    // Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("mysql");
    private final Connection connection = database.conectar();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final ReservaExemplarDAO reservaExemplarDAO = new ReservaExemplarDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Reserva reserva;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reservaDAO.setConnection(connection);
        reservaExemplarDAO.setConnection(connection);
        usuarioDAO.setConnection(connection);

        carregarComboBoxUsuarios();
        inicializarTabelaReservaExemplares();

        tableColumnReservaExemplarCancelamento.setCellValueFactory(new PropertyValueFactory<>("dtCancelamento"));
        tableColumnReservaExemplarReserva.setCellValueFactory(new PropertyValueFactory<>("reserva"));
        tableColumnReservaExemplarExemplar.setCellValueFactory(new PropertyValueFactory<>("exemplar"));
    }

    public void carregarComboBoxUsuarios() {
        List<Usuario> listUsuarios = usuarioDAO.listar();

        ObservableList<Usuario> observableListUsuarios = FXCollections.observableArrayList(listUsuarios);
        comboBoxReservaUsuario.setItems(observableListUsuarios);
    }

    public void inicializarTabelaReservaExemplares() {
        listExemplares = new ArrayList<>();
        observableListReservaExemplares = FXCollections.observableArrayList();
        tableViewReservaExemplares.setItems(observableListReservaExemplares);
    }

    @FXML
    public void handleButtonAdicionarExemplar() {
        // Aqui você pode implementar a lógica para adicionar exemplares à reserva
        // Exemplo: selecionar um exemplar e adicioná-lo à lista de reservaExemplares
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            reserva.setUsuario(comboBoxReservaUsuario.getValue());
            reserva.setDtReserva(datePickerReservaData.getValue());
            reserva.setDtEmprestimo(datePickerReservaEmprestimo.getValue());

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }

    private boolean validarEntradaDeDados() {
        String errorMessage = "";

        if (comboBoxReservaUsuario.getValue() == null) {
            errorMessage += "Usuário inválido!\n";
        }
        if (datePickerReservaData.getValue() == null) {
            errorMessage += "Data de Reserva inválida!\n";
        }
        if (datePickerReservaEmprestimo.getValue() == null) {
            errorMessage += "Data de Empréstimo inválida!\n";
        }
        if (observableListReservaExemplares.isEmpty()) {
            errorMessage += "Lista de Exemplares vazia!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
        datePickerReservaData.setValue(reserva.getDtReserva());
        datePickerReservaEmprestimo.setValue(reserva.getDtEmprestimo());

        // Outras inicializações conforme necessário
    }

}
