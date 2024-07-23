package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafxmvc.model.domain.Autor;
import javafxmvc.model.domain.Livro;
import javafxmvc.model.domain.PalavraChave;

public class LivroDAO {

    private Connection connection;
    
    private List<String> pendingSqlCommands;
    private List<Object[]> pendingParams;
    
    public LivroDAO(Connection connection) {
    	this.pendingSqlCommands = new ArrayList<>();
    	this.pendingParams = new ArrayList<>();
    	this.connection = connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Livro livro) {
        String sqlLivro = "INSERT INTO livros (nome, data_publicacao, prefacio) VALUES (?, ?, ?)";
        try {
            connection.setAutoCommit(false); // Inicia a transação

            // Insere o livro
            System.out.println("Inserindo livro: " + livro.getNome());
            PreparedStatement stmt = connection.prepareStatement(sqlLivro, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, livro.getNome());
            stmt.setDate(2, java.sql.Date.valueOf(livro.getDataPublicacao()));
            stmt.setString(3, livro.getPrefacio());
            int affectedRows = stmt.executeUpdate();
            System.out.println("Livro inserido, linhas afetadas: " + affectedRows);

            // Recupera o ID do livro inserido
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int idLivro = rs.getInt(1);
                System.out.println("ID do livro inserido: " + idLivro);

                // Adiciona autores ao livro
                for (Autor autor : livro.getAutores()) {
                    System.out.println("Adicionando autor com ID: " + autor.getIdAutor() + " ao livro com ID: " + idLivro);
                    adicionarAutorAoLivro(autor.getIdAutor()); // Adiciona o comando SQL e o parâmetro
                }

                // Adiciona palavras-chave ao livro
                if (livro.getPalavrasChave() != null) {
                    for (PalavraChave palavraChave : livro.getPalavrasChave()) {
                        System.out.println("Adicionando palavra-chave com ID: " + palavraChave.getIdPalavraChave() + " ao livro com ID: " + idLivro);
                        adicionarPalavraChaveAoLivro(palavraChave.getIdPalavraChave()); // Adiciona o comando SQL e o parâmetro
                    }
                }

                // Verificação de tamanho das listas
                System.out.println("Tamanho da lista de comandos pendentes antes do loop: " + pendingSqlCommands.size());
                System.out.println("Tamanho da lista de parâmetros pendentes antes do loop: " + pendingParams.size());

                // Executa comandos SQL pendentes
                for (int i = 0; i < pendingSqlCommands.size(); i++) {
                    String pendingSql = pendingSqlCommands.get(i);
                    Object[] params = pendingParams.get(i);

                    System.out.println("Executando comando SQL: " + pendingSql);
                    try (PreparedStatement pendingStmt = connection.prepareStatement(pendingSql)) {
                        pendingStmt.setInt(1, idLivro); // Define o ID do livro para o comando pendente
                        for (int j = 0; j < params.length; j++) {
                            pendingStmt.setObject(j + 2, params[j]); // Define os parâmetros restantes
                        }
                        pendingStmt.executeUpdate();
                        System.out.println("Comando SQL executado com sucesso.");
                    } catch (SQLException e) {
                        System.err.println("Erro ao executar comando SQL: " + pendingSql);
                        e.printStackTrace();
                        connection.rollback(); // Reverte a transação em caso de erro
                        return false;
                    }
                }
                pendingSqlCommands.clear();
                pendingParams.clear();

                connection.commit(); // Confirma a transação
                System.out.println("Transação confirmada com sucesso.");
                connection.setAutoCommit(true);
                return true;
            } else {
                System.out.println("Nenhum ID gerado para o livro inserido.");
                connection.rollback(); // Reverte a transação em caso de falha
                connection.setAutoCommit(true);
                return false;
            }
        } catch (SQLException ex) {
            try {
                connection.rollback(); // Reverte a transação em caso de exceção
            } catch (SQLException e) {
                Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, null, e);
            }
            System.err.println("Erro SQL: " + ex.getMessage());
            Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public Livro buscarLivro(int idLivro) {
        String sql = "SELECT * FROM livros WHERE id_livro=?";
        Livro livro = null;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idLivro);
            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    livro = new Livro();
                    livro.setIdLivro(resultado.getInt("id_livro"));
                    livro.setNome(resultado.getString("nome"));
                    livro.setPrefacio(resultado.getString("prefacio"));
                    // Defina outros atributos do livro conforme necessário
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, "Erro ao buscar livro", ex);
        }
        return livro;
    }

    
    public Livro buscarPorId(int idLivro) {
        String sqlLivro = "SELECT * FROM Livros WHERE Id_Livro = ?";
        String sqlPalavrasChave = "SELECT Id_PalavraChave FROM livros_palavrachave WHERE id_livro = ?";
        String sqlAutores = "SELECT Id_Autor FROM livros_autores WHERE id_livro = ?";
        
        try {
            // Buscar Livro
            PreparedStatement stmtLivro = connection.prepareStatement(sqlLivro);
            stmtLivro.setInt(1, idLivro);
            ResultSet rsLivro = stmtLivro.executeQuery();
            
            if (rsLivro.next()) {
                Livro livro = new Livro();
                livro.setIdLivro(rsLivro.getInt("Id_Livro"));
                livro.setNome(rsLivro.getString("Nome"));
                livro.setDataPublicacao(rsLivro.getDate("data_Publicacao").toLocalDate());
                livro.setPrefacio(rsLivro.getString("Prefacio"));

                // Buscar e adicionar palavras-chave associadas
                PreparedStatement stmtPalavrasChave = connection.prepareStatement(sqlPalavrasChave);
                stmtPalavrasChave.setInt(1, idLivro);
                ResultSet rsPalavrasChave = stmtPalavrasChave.executeQuery();
                List<PalavraChave> palavrasChave = new ArrayList<>();
                while (rsPalavrasChave.next()) {
                    PalavraChave palavraChave = new PalavraChave();
                    palavraChave.setIdPalavraChave(rsPalavrasChave.getInt("Id_PalavraChave"));

                    // Busque o nome da palavra-chave usando o id
                    String sqlNomePalavraChave = "SELECT Nome FROM PalavraChave WHERE Id_PalavraChave = ?";
                    try (PreparedStatement stmtNomePalavraChave = connection.prepareStatement(sqlNomePalavraChave)) {
                        stmtNomePalavraChave.setInt(1, palavraChave.getIdPalavraChave());
                        ResultSet rsNomePalavraChave = stmtNomePalavraChave.executeQuery();
                        if (rsNomePalavraChave.next()) {
                            palavraChave.setNome(rsNomePalavraChave.getString("Nome"));
                        }
                    }
                    
                    palavrasChave.add(palavraChave);
                }
                livro.setPalavrasChave(palavrasChave);

                // Buscar e adicionar autores associados
                PreparedStatement stmtAutores = connection.prepareStatement(sqlAutores);
                stmtAutores.setInt(1, idLivro);
                ResultSet rsAutores = stmtAutores.executeQuery();
                List<Autor> autores = new ArrayList<>();
                while (rsAutores.next()) {
                    Autor autor = new Autor();
                    autor.setIdAutor(rsAutores.getInt("Id_Autor"));

                    // Busque o nome do autor usando o id
                    String sqlNomeAutor = "SELECT Nome FROM Autor WHERE Id_Autor = ?";
                    try (PreparedStatement stmtNomeAutor = connection.prepareStatement(sqlNomeAutor)) {
                        stmtNomeAutor.setInt(1, autor.getIdAutor());
                        ResultSet rsNomeAutor = stmtNomeAutor.executeQuery();
                        if (rsNomeAutor.next()) {
                            autor.setNome(rsNomeAutor.getString("Nome"));
                        }
                    }

                    autores.add(autor);
                }
                livro.setAutores(autores);

                return livro;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

 // Método para listar palavras-chave associadas a um livro
    public List<PalavraChave> listarPalavrasChavePorLivro(int livroId) {
        String sql = "SELECT * FROM PalavraChave pc INNER JOIN livros_palavrachave lpc ON pc.id_palavrachave = lpc.id_palavrachave\r\n"
        		+ " WHERE lpc.id_livro = ?";
        List<PalavraChave> retorno = new ArrayList<>();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, livroId);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                PalavraChave palavraChave = new PalavraChave();
                palavraChave.setIdPalavraChave(resultado.getInt("id_palavrachave"));
                palavraChave.setNome(resultado.getString("nome"));
                retorno.add(palavraChave);
            }
            resultado.close();
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return retorno;
    }

    public List<Autor> listarAutoresPorLivro(Livro livro) {
        String sql = "SELECT * FROM Autores a INNER JOIN livros_autores la ON a.id_autor = la.id_autor WHERE la.id_livro = ?";
        List<Autor> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, livro.getIdLivro());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Autor autor = new Autor();
                autor.setIdAutor(resultado.getInt("id"));
                autor.setNome(resultado.getString("nome"));
                retorno.add(autor);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return retorno;
    }
    
    public List<Autor> listarAutoresPorLivro(int livroId) throws SQLException {
        List<Autor> listaAutores = new ArrayList<>();
        String sql = "SELECT a.* FROM autores a INNER JOIN livros_autores la ON la.id_autor = a.id_autor WHERE la.id_livro = ?";
        try {
        	PreparedStatement stmt = connection.prepareStatement(sql);
        	stmt.setInt(1, livroId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Autor autor = new Autor();
                autor.setIdAutor(rs.getInt("id_autor"));
                autor.setNome(rs.getString("nome"));
                listaAutores.add(autor);
            }
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listaAutores;
    }

    public boolean alterar(Livro livro) {
        String sqlLivro = "UPDATE Livros SET Nome=?, data_Publicacao=?, Prefacio=? WHERE Id_Livro=?";
        String sqlDeletePalavrasChave = "DELETE FROM Livros_PalavraChave WHERE Id_Livro=?";
        String sqlInsertPalavrasChave = "INSERT INTO Livros_PalavraChave (Id_Livro, Id_PalavraChave) VALUES (?, ?)";
        String sqlDeleteAutores = "DELETE FROM Livros_Autores WHERE Id_Livro=?";
        String sqlInsertAutores = "INSERT INTO Livros_Autores (Id_Livro, Id_Autor) VALUES (?, ?)";

        try {
            connection.setAutoCommit(false); // Iniciar transação
            
            // Atualizar Livro
            PreparedStatement stmtLivro = connection.prepareStatement(sqlLivro);
            stmtLivro.setString(1, livro.getNome());
            stmtLivro.setDate(2, Date.valueOf(livro.getDataPublicacao()));
            stmtLivro.setString(3, livro.getPrefacio());
            stmtLivro.setInt(4, livro.getIdLivro());
            stmtLivro.executeUpdate();

            // Atualizar palavras-chave associadas
            PreparedStatement stmtDeletePalavrasChave = connection.prepareStatement(sqlDeletePalavrasChave);
            stmtDeletePalavrasChave.setInt(1, livro.getIdLivro());
            stmtDeletePalavrasChave.executeUpdate();

            PreparedStatement stmtInsertPalavrasChave = connection.prepareStatement(sqlInsertPalavrasChave);
            for (PalavraChave palavraChave : livro.getPalavrasChave()) {
                stmtInsertPalavrasChave.setInt(1, livro.getIdLivro());
                stmtInsertPalavrasChave.setInt(2, palavraChave.getIdPalavraChave());
                stmtInsertPalavrasChave.addBatch();
            }
            stmtInsertPalavrasChave.executeBatch();

            // Atualizar autores associados
            PreparedStatement stmtDeleteAutores = connection.prepareStatement(sqlDeleteAutores);
            stmtDeleteAutores.setInt(1, livro.getIdLivro());
            stmtDeleteAutores.executeUpdate();

            PreparedStatement stmtInsertAutores = connection.prepareStatement(sqlInsertAutores);
            for (Autor autor : livro.getAutores()) {
                stmtInsertAutores.setInt(1, livro.getIdLivro());
                stmtInsertAutores.setInt(2, autor.getIdAutor());
                stmtInsertAutores.addBatch();
            }
            stmtInsertAutores.executeBatch();

            connection.commit(); // Confirmar transação
            return true;
        } catch (SQLException ex) {
            try {
                connection.rollback(); // Reverter transação em caso de erro
            } catch (SQLException e) {
                Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, null, e);
            }
            Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true); // Restaurar comportamento padrão de commit automático
            } catch (SQLException ex) {
                Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }



    public boolean remover(Livro Livro) {
        String sql = "DELETE FROM Livros WHERE id_Livro=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, Livro.getIdLivro());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }       
    }
    
    public void desabilitar(Livro Livro) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE Livros SET status = 'D' WHERE id_Livro = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, Livro.getIdLivro());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao desabilitar livro: " + e.getMessage(), e);
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

 // Método atualizado para listar livros com suas palavras-chave
    public List<Livro> listar() throws SQLException {
        List<Livro> livros = new ArrayList<>();
        String sqlLivros = "SELECT * from Livros";

        try (PreparedStatement stmtLivros = connection.prepareStatement(sqlLivros);
             ResultSet rsLivros = stmtLivros.executeQuery()) {

            while (rsLivros.next()) {
                Livro livro = new Livro();
                livro.setIdLivro(rsLivros.getInt("id_Livro"));
                livro.setNome(rsLivros.getString("Nome"));
                livro.setDataPublicacao(rsLivros.getDate("data_Publicacao").toLocalDate());
                livro.setPrefacio(rsLivros.getString("Prefacio"));

                // Buscar e adicionar palavras-chave associadas
                String sqlPalavrasChave = "SELECT p.Id_PalavraChave, p.Nome " +
                                          "FROM PalavraChave p " +
                                          "JOIN Livros_PalavraChave lp ON p.Id_PalavraChave = lp.Id_PalavraChave " +
                                          "WHERE lp.Id_Livro = ?";
                try (PreparedStatement stmtPalavrasChave = connection.prepareStatement(sqlPalavrasChave)) {
                    stmtPalavrasChave.setInt(1, livro.getIdLivro());
                    try (ResultSet rsPalavrasChave = stmtPalavrasChave.executeQuery()) {
                        List<PalavraChave> palavrasChave = new ArrayList<>();
                        while (rsPalavrasChave.next()) {
                            PalavraChave palavraChave = new PalavraChave();
                            palavraChave.setIdPalavraChave(rsPalavrasChave.getInt("Id_PalavraChave"));
                            palavraChave.setNome(rsPalavrasChave.getString("Nome"));
                            palavrasChave.add(palavraChave);
                        }
                        livro.setPalavrasChave(palavrasChave);
                    }
                }

                // Buscar e adicionar autores associados
                String sqlAutores = "SELECT a.Id_Autor, a.Nome " +
                                    "FROM Autores a " +
                                    "JOIN Livros_Autores la ON a.Id_Autor = la.Id_Autor " +
                                    "WHERE la.Id_Livro = ?";
                try (PreparedStatement stmtAutores = connection.prepareStatement(sqlAutores)) {
                    stmtAutores.setInt(1, livro.getIdLivro());
                    try (ResultSet rsAutores = stmtAutores.executeQuery()) {
                        List<Autor> autores = new ArrayList<>();
                        while (rsAutores.next()) {
                            Autor autor = new Autor();
                            autor.setIdAutor(rsAutores.getInt("Id_Autor"));
                            autor.setNome(rsAutores.getString("Nome"));
                            autores.add(autor);
                        }
                        livro.setAutores(autores);
                    }
                }

                livros.add(livro);
            }
        }
        return livros;
    }


    public Livro buscarPorLoginSenha(String Livro, String senha) {
        String sql = "SELECT * FROM Livros WHERE Livro = ? AND senha = ? AND status = 'H'";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, Livro);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Livro user = new Livro();
                user.setIdLivro(rs.getInt("Id_Livro"));
                user.setNome(rs.getString("Nome"));
                user.setDataPublicacao(rs.getDate("dataPublicacao").toLocalDate());
                user.setPrefacio(rs.getString("Prefacio"));        
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Livro buscarPorLivro(String Livro) {
        String sql = "SELECT * FROM Livros WHERE Livro = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, Livro);
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Livro user = new Livro();
                user.setIdLivro(resultado.getInt("Id_Livro"));
                user.setNome(resultado.getString("Nome"));
                user.setDataPublicacao(resultado.getDate("dataPublicacao").toLocalDate());
                user.setPrefacio(resultado.getString("tel"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Livro buscar(Livro livro) {
        String sqlLivro = "SELECT * FROM Livros WHERE Id_Livro = ?";
        Livro retorno = new Livro();
        
        try (PreparedStatement stmtLivro = connection.prepareStatement(sqlLivro)) {
            stmtLivro.setInt(1, livro.getIdLivro());
            try (ResultSet rsLivro = stmtLivro.executeQuery()) {
                if (rsLivro.next()) {
                    retorno.setIdLivro(rsLivro.getInt("Id_Livro"));
                    retorno.setNome(rsLivro.getString("Nome"));
                    retorno.setDataPublicacao(rsLivro.getDate("data_Publicacao").toLocalDate());
                    retorno.setPrefacio(rsLivro.getString("Prefacio"));

                    // Buscar e adicionar palavras-chave associadas
                    String sqlPalavrasChave = "SELECT p.Id_PalavraChave, p.Nome " +
                                              "FROM PalavrasChave p " +
                                              "JOIN Livros_PalavraChave lp ON p.Id_PalavraChave = lp.Id_PalavraChave " +
                                              "WHERE lp.Id_Livro = ?";
                    try (PreparedStatement stmtPalavrasChave = connection.prepareStatement(sqlPalavrasChave)) {
                        stmtPalavrasChave.setInt(1, retorno.getIdLivro());
                        try (ResultSet rsPalavrasChave = stmtPalavrasChave.executeQuery()) {
                            List<PalavraChave> palavrasChave = new ArrayList<>();
                            while (rsPalavrasChave.next()) {
                                PalavraChave palavraChave = new PalavraChave();
                                palavraChave.setIdPalavraChave(rsPalavrasChave.getInt("Id_PalavraChave"));
                                palavraChave.setNome(rsPalavrasChave.getString("Nome"));
                                palavrasChave.add(palavraChave);
                            }
                            retorno.setPalavrasChave(palavrasChave);
                        }
                    }

                    // Buscar e adicionar autores associados
                    String sqlAutores = "SELECT a.Id_Autor, a.Nome " +
                                        "FROM Autores a " +
                                        "JOIN Livros_Autores la ON a.Id_Autor = la.Id_Autor " +
                                        "WHERE la.Id_Livro = ?";
                    try (PreparedStatement stmtAutores = connection.prepareStatement(sqlAutores)) {
                        stmtAutores.setInt(1, retorno.getIdLivro());
                        try (ResultSet rsAutores = stmtAutores.executeQuery()) {
                            List<Autor> autores = new ArrayList<>();
                            while (rsAutores.next()) {
                                Autor autor = new Autor();
                                autor.setIdAutor(rsAutores.getInt("Id_Autor"));
                                autor.setNome(rsAutores.getString("Nome"));
                                autores.add(autor);
                            }
                            retorno.setAutores(autores);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LivroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return retorno;
    }

    public boolean adicionarAutorAoLivro(int idAutor) {
        String sql = "INSERT INTO livros_autores(id_Livro, id_Autor) VALUES(?, ?)";
        pendingSqlCommands.add(sql);
        pendingParams.add(new Object[] { idAutor });
        System.out.println("Autor adicionado a lista com o id:" + idAutor);
        return true;
    }
    
    public boolean removerAutorDoLivro(int autorId) {
        String sql = "DELETE FROM livros_autores WHERE id_livro = ? AND id_autor = ?";
        pendingSqlCommands.remove(sql);
        pendingParams.remove(new Object[] { autorId });
        System.out.println("Autor removido da lista com o id:" + autorId);
        return true;
    }
    
    public boolean adicionarPalavraChaveAoLivro(int idPalavraChave) {
        String sql = "INSERT INTO livros_palavrachave(id_Livro, id_PalavraChave) VALUES(?, ?)";
        pendingSqlCommands.add(sql);
        pendingParams.add(new Object[] { idPalavraChave });
        System.out.println("Palavra Chave adicionada a lista com o id:" + idPalavraChave);
        System.out.println("Comando sql adicionado a lista:" + pendingSqlCommands);
        return true;
    }
    
    public boolean removerPalavraChaveDoLivro(int palavraChaveId) {
        String sql = "DELETE FROM livros_palavrachave WHERE id_livro = ? AND id_palavrachave = ?";
        pendingSqlCommands.add(sql);
        pendingParams.add(new Object[] { palavraChaveId });
        System.out.println("Palavra Chave removida da lista com o id:" + palavraChaveId);
        return true;
    }
    
    public List<Livro> listarNomesEIds() {
        checkConnection(); // Verificar se a conexão foi inicializada
        String sql = "SELECT * FROM livros";
        List<Livro> livros = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Livro livro = new Livro();
                livro.setIdLivro(rs.getInt("id_livro"));
                livro.setNome(rs.getString("nome"));
                livros.add(livro);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Tratar o erro de forma apropriada
        }
        return livros;
    }
    
    private void checkConnection() {
        if (connection == null) {
            throw new IllegalStateException("A conexão com o banco de dados não foi inicializada.");
        }
    }
    
}
