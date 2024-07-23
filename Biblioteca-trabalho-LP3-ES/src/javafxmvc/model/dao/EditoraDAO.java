package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.Editora;

public class EditoraDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public EditoraDAO(Connection connection) {
        this.connection = connection;
    }
    

    public EditoraDAO() {
        // Construtor padrão, se você usar uma conexão padrão ou inicializar aqui
    }

    public boolean inserir(Editora editora) {
        String sql = "INSERT INTO editoras(nome, endereco, telefone, contato, status) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, editora.getNome());
            stmt.setString(2, editora.getEndereco());
            stmt.setString(3, editora.getTelefone());
            stmt.setString(4, editora.getContato());
            stmt.setString(5, editora.getStatus());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EditoraDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Editora editora) {
        String sql = "UPDATE editoras SET nome=?, endereco=?, telefone=?, contato=?, status=? WHERE id_editora=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, editora.getNome());
            stmt.setString(2, editora.getEndereco());
            stmt.setString(3, editora.getTelefone());
            stmt.setString(4, editora.getContato());
            stmt.setString(5, editora.getStatus());
            stmt.setInt(6, editora.getIdEditora());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EditoraDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Editora editora) {
        String sql = "DELETE FROM editoras WHERE id_editora=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, editora.getIdEditora());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EditoraDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Editora> listar() {
        String sql = "SELECT * FROM editoras";
        List<Editora> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Editora editora = new Editora();
                editora.setIdEditora(resultado.getInt("id_editora"));
                editora.setNome(resultado.getString("nome"));
                editora.setEndereco(resultado.getString("endereco"));
                editora.setTelefone(resultado.getString("telefone"));
                editora.setContato(resultado.getString("contato"));
                editora.setStatus(resultado.getString("status"));
                retorno.add(editora);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditoraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Editora buscar(Editora editora) {
        String sql = "SELECT * FROM editoras WHERE id_editora=?";
        Editora retorno = null;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, editora.getIdEditora());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno = new Editora();
                retorno.setIdEditora(resultado.getInt("id_editora"));
                retorno.setNome(resultado.getString("nome"));
                retorno.setEndereco(resultado.getString("endereco"));
                retorno.setTelefone(resultado.getString("telefone"));
                retorno.setContato(resultado.getString("contato"));
                retorno.setStatus(resultado.getString("status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditoraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Editora buscarPorId(int idEditora) {
        String sql = "SELECT * FROM editoras WHERE id_editora = ?";
        Editora editora = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEditora);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                editora = new Editora();
                editora.setIdEditora(resultado.getInt("id_editora"));
                editora.setNome(resultado.getString("nome"));
                editora.setEndereco(resultado.getString("endereco"));
                editora.setTelefone(resultado.getString("telefone"));
                editora.setContato(resultado.getString("contato"));
                editora.setStatus(resultado.getString("status"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EditoraDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return editora;
    }
}
