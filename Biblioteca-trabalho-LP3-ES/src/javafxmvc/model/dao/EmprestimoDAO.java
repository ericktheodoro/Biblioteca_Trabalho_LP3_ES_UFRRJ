package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafxmvc.model.domain.Emprestimo;
import javafxmvc.model.domain.Usuario;

public class EmprestimoDAO {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void inserir(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimos (dt_emprestimo, id_usuario) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(LocalDate.now()));
            stmt.setInt(2, emprestimo.getUsuario().getId()); // Definir o ID do usuário
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir o empréstimo.", e);
        }
    }

    public boolean alterar(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimos SET dt_emprestimo=?, id_usuario=? WHERE id_emprestimo=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setInt(2, emprestimo.getUsuario().getId());
            stmt.setInt(3, emprestimo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean remover(Emprestimo emprestimo) {
        String sql = "DELETE FROM emprestimos WHERE id_emprestimo=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, emprestimo.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Emprestimo> listar() {
        String sql = "SELECT * FROM emprestimos";
        List<Emprestimo> resultado = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet resultadoBanco = stmt.executeQuery()) {
            while (resultadoBanco.next()) {
                Emprestimo emprestimo = new Emprestimo();
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.setConnection(connection);
                emprestimo.setUsuario(usuarioDAO.buscarPorUsuario(resultadoBanco.getInt("id_usuario")));
                emprestimo.setId(resultadoBanco.getInt("id_emprestimo"));
                emprestimo.setDataEmprestimo(resultadoBanco.getDate("dt_emprestimo").toLocalDate());
                resultado.add(emprestimo);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public List<Integer> listarExemplaresPorEmprestimo(int idEmprestimo) {
        List<Integer> exemplaresIds = new ArrayList<>();
        String sql = "SELECT id_exemplar FROM emprestimos_exemplares WHERE id_emprestimo = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEmprestimo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    exemplaresIds.add(rs.getInt("id_exemplar"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar exemplares por empréstimo.", e);
        }
        
        return exemplaresIds;
    }

    public Emprestimo buscar(int id) {
        String sql = "SELECT * FROM emprestimos WHERE id_emprestimo=?";
        Emprestimo emprestimo = new Emprestimo();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    usuarioDAO.setConnection(connection);
                    Usuario usuario = usuarioDAO.buscarPorUsuario(resultado.getInt("id_usuario"));
                    emprestimo.setId(resultado.getInt("id_emprestimo"));
                    emprestimo.setDataEmprestimo(resultado.getDate("dt_emprestimo").toLocalDate());
                    emprestimo.setUsuario(usuario);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erro ao buscar empréstimo.", ex);
        }
        return emprestimo;
    }
}

