package javafxmvc.model.domain;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;

public class Emprestimo {
    private int idEmprestimo;
    private LocalDate dtEmprestimo;
    private Usuario usuario; // Mudar para o tipo Usuario
    private Exemplar exemplar;

    public int getIdEmprestimo() {
		return idEmprestimo;
	}

	public void setIdEmprestimo(int idEmprestimo) {
		this.idEmprestimo = idEmprestimo;
	}

	public LocalDate getDtEmprestimo() {
		return dtEmprestimo;
	}

	public void setDtEmprestimo(LocalDate dtEmprestimo) {
		this.dtEmprestimo = dtEmprestimo;
	}

	public Exemplar getExemplar() {
		return exemplar;
	}

	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

	// Construtor
    public Emprestimo(int idEmprestimo, LocalDate dtEmprestimo, Usuario usuario) {
        this.idEmprestimo = idEmprestimo;
        this.dtEmprestimo = dtEmprestimo;
        this.usuario = usuario; // Mudar para o tipo Usuario
    }
    
    public Emprestimo() {
    	this.usuario = new Usuario();
    }

    // Getter para 'idEmprestimo'
    public int getId() {
        return idEmprestimo;
    }

    // Setter para 'idEmprestimo'
    public void setId(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    // Getter para 'dtEmprestimo'
    public LocalDate getDataEmprestimo() {
        return dtEmprestimo;
    }

    // Setter para 'dtEmprestimo'
    public void setDataEmprestimo(LocalDate dtEmprestimo) {
        this.dtEmprestimo = dtEmprestimo;
    }

    // Getter para 'usuario'
    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getId_Usuario() {
        return usuario.getId_Usuario();
    }
    
    public void setId_Usuario(int id_Usuario) {
        if (this.usuario == null) {
            this.usuario = new Usuario();
        }
        this.usuario.setId_Usuario(id_Usuario);
    }

    public IntegerProperty id_UsuarioProperty() {
        return usuario.id_UsuarioProperty();
    }
}
