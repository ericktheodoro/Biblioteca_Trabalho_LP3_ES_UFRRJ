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
import javafxmvc.model.domain.Exemplar;
import javafxmvc.model.domain.Livro;
import javafxmvc.model.dao.LivroDAO;

public class ExemplarDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    private void checkConnection() {
        if (connection == null) {
            throw new IllegalStateException("A conexão com o banco de dados não foi inicializada.");
        }
    }

    public void inserir(Exemplar exemplar) throws SQLException {
        if (exemplar.getLivro() == null) {
            throw new IllegalArgumentException("Livro cannot be null when inserting Exemplar.");
        }

        String sql = "INSERT INTO exemplares (id_livro, id_editora, reservado) VALUES (?, ?,'N')";
        PreparedStatement stmt = connection.prepareStatement(sql);
        try {
            stmt.setInt(1, exemplar.getLivro().getIdLivro());
            stmt.setInt(2, exemplar.getEditora().getIdEditora());
            stmt.execute(); 
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDAO.class.getName()).log(Level.SEVERE, "Erro ao inserir exemplar", ex); 
        }
    }
    

    public boolean alterar(Exemplar exemplar) {
        checkConnection(); // Verificar se a conexão foi inicializada
        String sql = "UPDATE exemplares SET id_livro=?, id_editora=?, reservado=? WHERE id_exemplar=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, exemplar.getLivro().getIdLivro());
            stmt.setInt(2, exemplar.getEditora().getIdEditora());
            stmt.setInt(3, exemplar.getIdExemplar());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDAO.class.getName()).log(Level.SEVERE, "Erro ao alterar exemplar", ex);
            return false;
        }
    }

    public boolean remover(Exemplar exemplar) {
        checkConnection(); // Verificar se a conexão foi inicializada
        String sql = "DELETE FROM exemplares WHERE id_exemplar=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, exemplar.getIdExemplar());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDAO.class.getName()).log(Level.SEVERE, "Erro ao remover exemplar", ex);
            return false;
        }
    }

    public List<Exemplar> listar() {
        checkConnection(); // Verificar se a conexão foi inicializada
        String sql = "SELECT * FROM exemplares";
        List<Exemplar> retorno = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet resultado = stmt.executeQuery()) {

            while (resultado.next()) {
                Exemplar exemplar = new Exemplar();
                exemplar.setIdExemplar(resultado.getInt("id_exemplar"));

                LivroDAO livroDAO = new LivroDAO(connection);
                livroDAO.setConnection(connection); // Passar a conexão
                Livro livro = livroDAO.buscarLivro(resultado.getInt("id_livro"));
                exemplar.setLivro(livro);

                EditoraDAO editoraDAO = new EditoraDAO();
                editoraDAO.setConnection(connection); // Passar a conexão
                Editora editora = editoraDAO.buscarPorId(resultado.getInt("id_editora"));
                exemplar.setEditora(editora);

                exemplar.setReservado(resultado.getString("reservado"));
                retorno.add(exemplar);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDAO.class.getName()).log(Level.SEVERE, "Erro ao listar exemplares", ex);
        }
        return retorno;
    }

    public Exemplar buscar(Exemplar exemplar) {
        checkConnection(); // Verificar se a conexão foi inicializada
        String sql = "SELECT * FROM exemplares WHERE id_exemplar=?";
        Exemplar retorno = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, exemplar.getIdExemplar());
            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    retorno = new Exemplar();
                    retorno.setIdExemplar(resultado.getInt("id_exemplar"));

                    LivroDAO livroDAO = new LivroDAO(connection);
                    livroDAO.setConnection(connection); // Passar a conexão
                    Livro livro = livroDAO.buscarLivro(resultado.getInt("id_livro"));
                    retorno.setLivro(livro);

                    EditoraDAO editoraDAO = new EditoraDAO();
                    editoraDAO.setConnection(connection); // Passar a conexão
                    Editora editora = editoraDAO.buscarPorId(resultado.getInt("id_editora"));
                    retorno.setEditora(editora);

                    retorno.setReservado(resultado.getString("reservado"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExemplarDAO.class.getName()).log(Level.SEVERE, "Erro ao buscar exemplar", ex);
        }
        return retorno;
    }
}
