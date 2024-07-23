package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.Reserva;
import javafxmvc.model.domain.ReservaExemplar;
import javafxmvc.model.domain.Usuario;

public class ReservaDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Reserva reserva) {
        String sql = "INSERT INTO reservas(id_reserva, dt_reserva, dt_reserva_emprestimo, id_usuario) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reserva.getIdReserva());
            if (reserva.getDtReserva() == null) {
                Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, "DtReserva é null");
                return false;
            }
            stmt.setDate(2, Date.valueOf(reserva.getDtReserva()));
            
            if (reserva.getDtEmprestimo() != null) {
                stmt.setDate(3, Date.valueOf(reserva.getDtEmprestimo()));
            } else {
                stmt.setNull(3, java.sql.Types.DATE);
            }
            stmt.setInt(4, reserva.getUsuario().getId_Usuario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Reserva reserva) {
        String sql = "UPDATE reservas SET dt_reserva=?, dt_reserva_emprestimo=?, id_usuario=? WHERE id_reserva=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDate(1, Date.valueOf(reserva.getDtReserva()));
            stmt.setDate(2, reserva.getDtEmprestimo() != null ? Date.valueOf(reserva.getDtEmprestimo()) : null);
            stmt.setInt(3, reserva.getUsuario().getId_Usuario());
            stmt.setInt(4, reserva.getIdReserva());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Reserva reserva) {
        String sql = "DELETE FROM reservas_exemplares WHERE id_reserva=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reserva.getIdReserva());
            stmt.execute();	
            
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        sql = "DELETE FROM reservas WHERE id_reserva=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reserva.getIdReserva());
            stmt.execute();	
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Reserva> listar() {
        String sql = "SELECT * FROM reservas";
        List<Reserva> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Reserva reserva = new Reserva();
                Usuario usuario = new Usuario();

                reserva.setIdReserva(resultado.getInt("id_reserva"));
                reserva.setDtReserva(resultado.getDate("dt_reserva").toLocalDate());
                reserva.setDtEmprestimo(resultado.getDate("dt_reserva_emprestimo") != null ? resultado.getDate("dt_reserva_emprestimo").toLocalDate() : null);
                usuario.setId_Usuario(resultado.getInt("id_usuario"));

                // Obtendo os dados completos do Usuario associado à Reserva
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.setConnection(connection);
                usuario = usuarioDAO.buscar(usuario);

                // Obtendo os dados completos dos Itens de Reserva associados à Reserva
                ReservaExemplarDAO reservaExemplarDAO = new ReservaExemplarDAO();
                reservaExemplarDAO.setConnection(connection);
                List<ReservaExemplar> reservaExemplares = reservaExemplarDAO.listarPorReserva(reserva);

                reserva.setUsuario(usuario);
                reserva.setReservaExemplar(reservaExemplares);
                retorno.add(reserva);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Reserva buscar(Reserva reserva) {
        String sql = "SELECT * FROM reservas WHERE id_reserva=?";
        Reserva retorno = null;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, reserva.getIdReserva());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Usuario usuario = new Usuario();

                reserva.setIdReserva(resultado.getInt("id_reserva"));
                reserva.setDtReserva(resultado.getDate("dt_reserva").toLocalDate());
                reserva.setDtEmprestimo(resultado.getDate("dt_reserva_emprestimo") != null ? resultado.getDate("dt_reserva_emprestimo").toLocalDate() : null);
                usuario.setId_Usuario(resultado.getInt("id_usuario"));

                // Obtendo os dados completos do Usuario associado à Reserva
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.setConnection(connection);
                usuario = usuarioDAO.buscar(usuario);

                // Obtendo os dados completos dos Itens de Reserva associados à Reserva
                ReservaExemplarDAO reservaExemplarDAO = new ReservaExemplarDAO();
                reservaExemplarDAO.setConnection(connection);
                List<ReservaExemplar> reservaExemplares = reservaExemplarDAO.listarPorReserva(reserva);

                reserva.setUsuario(usuario);
                reserva.setReservaExemplar(reservaExemplares);
                retorno = reserva;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Reserva buscarUltimaReserva() {
        String sql = "SELECT MAX(id_reserva) AS max_id FROM reservas";
        Reserva retorno = null;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno = new Reserva();
                retorno.setIdReserva(resultado.getInt("max_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Map<Integer, ArrayList> listarQuantidadeReservasPorMes() {
        String sql = "SELECT COUNT(id_reserva) AS count, EXTRACT(YEAR FROM dt_reserva) AS ano, EXTRACT(MONTH FROM dt_reserva) AS mes FROM reservas GROUP BY ano, mes ORDER BY ano, mes";
        Map<Integer, ArrayList> retorno = new HashMap<>();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                ArrayList<Integer> linha = new ArrayList<>();
                int ano = resultado.getInt("ano");
                linha.add(resultado.getInt("mes"));
                linha.add(resultado.getInt("count"));

                if (!retorno.containsKey(ano)) {
                    retorno.put(ano, linha);
                } else {
                    ArrayList<Integer> linhaNova = retorno.get(ano);
                    linhaNova.add(resultado.getInt("mes"));
                    linhaNova.add(resultado.getInt("count"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReservaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
