package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Emprestimo implements Serializable {

	private LocalDate dtEmprestimo;
	private Usuario usuario; //CRIAR USUARIO
	private List<EmprestimoExemplar> emprestimoExemplar;

	public Emprestimo(LocalDate dtEmprestimo, Usuario usuario) {
		super();
		this.dtEmprestimo = dtEmprestimo;
		this.usuario = usuario;
	}

	public Emprestimo() {
		super();
	}


	public List<EmprestimoExemplar> getEmprestimoExemplar() {
		return emprestimoExemplar;
	}

	public void setEmprestimoExemplar(List<EmprestimoExemplar> emprestimoExemplar) {
		this.emprestimoExemplar = emprestimoExemplar;
	}

	public LocalDate getDtEmprestimo() {
		return dtEmprestimo;
	}

	public void setDtEmprestimo(LocalDate dtEmprestimo) {
		this.dtEmprestimo = dtEmprestimo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Emprestimo [dtEmprestimo=" + dtEmprestimo + "]";
	}

}
