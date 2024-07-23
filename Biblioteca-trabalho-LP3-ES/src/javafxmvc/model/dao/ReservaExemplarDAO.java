package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafxmvc.model.domain.Exemplar;
import javafxmvc.model.domain.Livro;
import javafxmvc.model.domain.Reserva;
import javafxmvc.model.domain.ReservaExemplar;

public class ReservaExemplarDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ReservaExemplar reservaExemplar) {
        String sql = "INSERT INTO itensdereserva(id_reserva, id_livro, id_exemplar, de_cancelamento) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reservaExemplar.getReserva().getIdReserva());
            stmt.setDouble(2, reservaExemplar.getExemplar().getLivro().getIdLivro());
            stmt.setInt(3, reservaExemplar.getExemplar().getIdExemplar());
            stmt.setDate(4, java.sql.Date.valueOf(reservaExemplar.getDtCancelamento()));
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReservaExemplarDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(ReservaExemplar reservaExemplar) {
        String sql = "UPDATE reservas_exemplares SET dt_cancelamento=? WHERE id_reserva=? AND id_livro=? AND id_exemplar=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(reservaExemplar.getDtCancelamento()));
            stmt.setInt(2, reservaExemplar.getReserva().getIdReserva());
            stmt.setInt(3, reservaExemplar.getExemplar().getLivro().getIdLivro());
            stmt.setInt(4, reservaExemplar.getExemplar().getIdExemplar());

            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReservaExemplarDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(ReservaExemplar reservaExemplar) {
        String sql = "DELETE FROM reservas_exemplares WHERE id_reserva=?, id_exemplar=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reservaExemplar.getReserva().getIdReserva());
            stmt.setInt(2, reservaExemplar.getExemplar().getIdExemplar());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReservaExemplarDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<ReservaExemplar> listar() {
        String sql = "SELECT * FROM reservas_exemplares";
        List<ReservaExemplar> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ReservaExemplar reservaExemplar = new ReservaExemplar();
                Reserva reserva = new Reserva();
                Exemplar exemplar = new Exemplar();
                Livro livro = new Livro();

                reserva.setIdReserva(resultado.getInt("id_reserva"));
                livro.setIdLivro(resultado.getInt("id_livro"));
                exemplar.setIdExemplar(resultado.getInt("id_exemplar"));
                exemplar.setLivro(livro);
                reservaExemplar.setDtCancelamento(resultado.getDate("dt_cancelamento").toLocalDate());
                reservaExemplar.setReserva(reserva);
                reservaExemplar.setExemplar(exemplar);

                retorno.add(reservaExemplar);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservaExemplarDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<ReservaExemplar> listarPorReserva(Reserva reserva) {
        String sql = "SELECT * FROM reservas_exemplares WHERE id_reserva=?";
        List<ReservaExemplar> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reserva.getIdReserva());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ReservaExemplar reservaExemplar = new ReservaExemplar();
                Exemplar exemplar = new Exemplar();
                Livro livro = new Livro();

                livro.setIdLivro(resultado.getInt("id_livro"));
                exemplar.setIdExemplar(resultado.getInt("id_exemplar"));
                exemplar.setLivro(livro);
                reservaExemplar.setDtCancelamento(resultado.getDate("dt_cancelamento").toLocalDate());
                reservaExemplar.setReserva(reserva);
                reservaExemplar.setExemplar(exemplar);

                retorno.add(reservaExemplar);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservaExemplarDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public ReservaExemplar buscar(ReservaExemplar reservaExemplar) {
        String sql = "SELECT * FROM reservas_exemplares WHERE id_reserva=? AND id_livro=? AND id_exemplar=?";
        ReservaExemplar retorno = new ReservaExemplar();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reservaExemplar.getReserva().getIdReserva());
            stmt.setInt(2, reservaExemplar.getExemplar().getLivro().getIdLivro());
            stmt.setInt(3, reservaExemplar.getExemplar().getIdExemplar());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Reserva reserva = new Reserva();
                Exemplar exemplar = new Exemplar();
                Livro livro = new Livro();

                reserva.setIdReserva(resultado.getInt("id_reserva"));
                livro.setIdLivro(resultado.getInt("id_livro"));
                exemplar.setIdExemplar(resultado.getInt("id_exemplar"));
                exemplar.setLivro(livro);
                reservaExemplar.setDtCancelamento(resultado.getDate("dt_cancelamento").toLocalDate());
                reservaExemplar.setReserva(reserva);
                reservaExemplar.setExemplar(exemplar);

                retorno = reservaExemplar;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservaExemplarDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
