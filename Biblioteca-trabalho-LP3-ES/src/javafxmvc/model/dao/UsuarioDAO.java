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
        String sql = "INSERT INTO usuarios(nome, endereco, tel, usuario, senha, status, tipo) VALUES(?,?,?,?,?,'H',?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEndereco());
            stmt.setString(3, usuario.getTel());
            stmt.setString(4, usuario.getUsuario());
            stmt.setString(5, usuario.getSenha());
            stmt.setString(6, usuario.getTipo());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome=?, endereco=?, tel=?, usuario=?, senha=?, status='H', tipo=? WHERE id_usuario=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEndereco());
            stmt.setString(3, usuario.getTel());
            stmt.setString(4, usuario.getUsuario());
            stmt.setString(5, usuario.getSenha());
            stmt.setString(6, usuario.getTipo());
            stmt.setInt(7, usuario.getId_Usuario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Usuario usuario) {
        String sql = "DELETE FROM usuarios WHERE id_usuario=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, usuario.getId_Usuario());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }       
    }
    
    public void desabilitar(Usuario usuario) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE usuarios SET status = 'D' WHERE id_usuario = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, usuario.getId_Usuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao desabilitar usuário: " + e.getMessage(), e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Erro ao fechar statement: " + e.getMessage(), e);
            }
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
                usuario.setId_Usuario(resultado.getInt("id_usuario"));
                usuario.setNome(resultado.getString("nome"));
                usuario.setEndereco(resultado.getString("endereco"));
                usuario.setTel(resultado.getString("tel"));
                usuario.setUsuario(resultado.getString("usuario"));
                usuario.setSenha(resultado.getString("senha"));
                usuario.setStatus(resultado.getString("status"));
                usuario.setTipo(resultado.getString("tipo"));
                retorno.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Usuario buscarPorLoginSenha(String usuario, String senha) {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ? AND status = 'H'";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario user = new Usuario();
                user.setId_Usuario(rs.getInt("id_usuario"));
                user.setNome(rs.getString("nome"));
                user.setEndereco(rs.getString("endereco"));
                user.setTel(rs.getString("tel"));
                user.setUsuario(rs.getString("usuario"));
                user.setSenha(rs.getString("senha"));
                user.setStatus(rs.getString("status"));
                user.setTipo(rs.getString("tipo"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario buscarPorUsuario(String usuario) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, usuario);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Usuario user = new Usuario();
                user.setId_Usuario(resultado.getInt("id_usuario"));
                user.setNome(resultado.getString("nome"));
                user.setEndereco(resultado.getString("endereco"));
                user.setTel(resultado.getString("tel"));
                user.setUsuario(resultado.getString("usuario"));
                user.setSenha(resultado.getString("senha"));
                user.setStatus(resultado.getString("status"));
                user.setTipo(resultado.getString("tipo"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Usuario buscarPorUsuario(int idUsuario) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Usuario user = new Usuario();
                user.setId_Usuario(resultado.getInt("id_usuario"));
                user.setNome(resultado.getString("nome"));
                user.setEndereco(resultado.getString("endereco"));
                user.setTel(resultado.getString("tel"));
                user.setUsuario(resultado.getString("usuario"));
                user.setSenha(resultado.getString("senha"));
                user.setStatus(resultado.getString("status"));
                user.setTipo(resultado.getString("tipo"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Usuario buscar(Usuario usuario) {
        String sql = "SELECT * FROM usuarios WHERE id_usuario=?";
        Usuario retorno = new Usuario();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, usuario.getId_Usuario());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                usuario.setNome(resultado.getString("nome"));
                usuario.setEndereco(resultado.getString("endereco"));
                usuario.setTel(resultado.getString("tel"));
                usuario.setUsuario(resultado.getString("usuario"));
                usuario.setSenha(resultado.getString("senha"));
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
