package javafxmvc.model.dao;

import javafxmvc.model.domain.Usuario;

public class Session {
    private static Session instance;
    private Usuario usuarioAtual;

    private Session() {
        // Construtor privado para garantir que não haja instâncias adicionais
    }

    public static synchronized Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }

    public void setUsuarioAtual(Usuario usuario) {
        this.usuarioAtual = usuario;
    }
}
