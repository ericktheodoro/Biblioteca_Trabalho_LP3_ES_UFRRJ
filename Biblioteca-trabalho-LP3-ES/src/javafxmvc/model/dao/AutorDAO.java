package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafxmvc.model.domain.Autor;

public class AutorDAO {
	
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public AutorDAO(Connection connection) {
    	this.connection = connection;
    }

    public boolean inserir(Autor autor) {
        String sql = "INSERT INTO autores(nome, telefone, status) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, autor.getNome());
            stmt.setString(2, autor.getTelefone());
            stmt.setString(3, autor.getStatus());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Autor autor) {
        String sql = "UPDATE autores SET nome=?, telefone=?, status=? WHERE id_Autor=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, autor.getNome());
            stmt.setString(2, autor.getTelefone());
            stmt.setString(3, autor.getStatus());
            stmt.setInt(4, autor.getIdAutor());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Autor autor) {
        String sql = "DELETE FROM autores WHERE id_Autor=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, autor.getIdAutor());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Autor> listar() {
        String sql = "SELECT * FROM autores";
        List<Autor> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Autor autor = new Autor();
                // nome dos campos no banco de dados
                autor.setIdAutor(resultado.getInt("id_autor"));
                autor.setNome(resultado.getString("nome"));
                autor.setTelefone(resultado.getString("telefone"));
                autor.setStatus(resultado.getString("status"));
                retorno.add(autor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Autor buscar(Autor autor) {
        String sql = "SELECT * FROM autores WHERE id_Autor=?";
        Autor retorno = new Autor();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, autor.getIdAutor());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                autor.setNome(resultado.getString("nome"));
                autor.setTelefone(resultado.getString("telefone"));
                autor.setStatus(resultado.getString("status"));
                retorno = autor;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
