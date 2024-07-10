package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafxmvc.model.domain.Usuario;

public class UsuarioDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Usuario usuario) {
        String sql = "INSERT INTO usuarios(nome, endereco, tel, status, tipo) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEndereco());
            stmt.setString(3, usuario.getTel());
            stmt.setString(4, usuario.getStatus());
            stmt.setString(5, usuario.getTipo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome=?, endereco=?, tel=?, status=?, tipo=? WHERE id_Usuario=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEndereco());
            stmt.setString(3, usuario.getTel());
            stmt.setString(4, usuario.getStatus());
            stmt.setString(5, usuario.getTipo());
            stmt.setInt(6, usuario.getIdUsuario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Usuario usuario) {
        String sql = "DELETE FROM usuarios WHERE id_Usuario=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, usuario.getIdUsuario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Usuario> listar() {
        String sql = "SELECT * FROM usuarios";
        List<Usuario> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Usuario usuario = new Usuario();
                // nome dos campos no banco de dados
                usuario.setIdUsuario(resultado.getInt("id_usuario"));
                usuario.setNome(resultado.getString("nome"));
                usuario.setEndereco(resultado.getString("endereco"));
                usuario.setTel(resultado.getString("tel"));
                usuario.setStatus(resultado.getString("status"));
                usuario.setTipo(resultado.getString("tipo"));
                retorno.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Usuario buscar(Usuario usuario) {
        String sql = "SELECT * FROM usuarios WHERE id_Usuario=?";
        Usuario retorno = new Usuario();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, usuario.getIdUsuario());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                usuario.setNome(resultado.getString("nome"));
                usuario.setEndereco(resultado.getString("endereco"));
                usuario.setTel(resultado.getString("telefone"));
                usuario.setStatus(resultado.getString("status"));
                usuario.setTipo(resultado.getString("tipo"));
                retorno = usuario;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
