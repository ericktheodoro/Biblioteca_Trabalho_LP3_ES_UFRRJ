package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.PalavraChave;

public class PalavraChaveDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public PalavraChaveDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(PalavraChave palavraChave) {
        String sql = "INSERT INTO palavrachave(nome, status) VALUES(?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, palavraChave.getNome());
            stmt.setString(2, palavraChave.getStatus());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PalavraChaveDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(PalavraChave palavraChave) {
        String sql = "UPDATE palavrachave SET nome=?, status=? WHERE id_palavrachave=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, palavraChave.getNome());
            stmt.setString(2, palavraChave.getStatus());
            stmt.setInt(3, palavraChave.getIdPalavraChave());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PalavraChaveDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean desabilitar(PalavraChave palavraChave) {
        String sql = "UPDATE palavrachave SET status='D' WHERE id_palavrachave=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, palavraChave.getIdPalavraChave());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PalavraChaveDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean habilitar(PalavraChave palavraChave) {
        String sql = "UPDATE palavrachave SET status='H' WHERE id_palavrachave=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, palavraChave.getIdPalavraChave());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(PalavraChaveDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<PalavraChave> listar() {
        String sql = "SELECT * FROM palavrachave";
        List<PalavraChave> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                PalavraChave palavraChave = new PalavraChave();
                palavraChave.setIdPalavraChave(resultado.getInt("id_palavrachave"));
                palavraChave.setNome(resultado.getString("nome"));
                palavraChave.setStatus(resultado.getString("status"));
                retorno.add(palavraChave);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PalavraChaveDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public PalavraChave buscar(PalavraChave palavraChave) {
        String sql = "SELECT * FROM palavrachave WHERE id_palavrachave=?";
        PalavraChave retorno = new PalavraChave();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, palavraChave.getIdPalavraChave());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                palavraChave.setNome(resultado.getString("nome"));
                palavraChave.setStatus(resultado.getString("status"));
                retorno = palavraChave;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PalavraChaveDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public PalavraChave buscarPorId(int idPalavraChave) {
        String sql = "SELECT * FROM palavrachave WHERE id_palavrachave = ?";
        PalavraChave palavraChave = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPalavraChave);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                palavraChave = new PalavraChave();
                palavraChave.setIdPalavraChave(resultado.getInt("id_palavrachave"));
                palavraChave.setNome(resultado.getString("nome"));
                palavraChave.setStatus(resultado.getString("status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PalavraChaveDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return palavraChave;
    }
}
