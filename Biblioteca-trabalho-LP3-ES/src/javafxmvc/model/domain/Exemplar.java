package javafxmvc.model.domain;

import java.io.Serializable;

public class Exemplar implements Serializable {

	private int idExemplar;
	private Livro livro;
	private Editora editora;
	private String reservado;

	public Exemplar() {
	}
	
	public Exemplar(Livro livro) {
		this.livro = livro;
	}

	public Exemplar(int idExemplar, Livro livro, Editora editora, String reservado) {
		super();
		this.idExemplar = idExemplar;
		this.livro = livro;
		this.editora = editora;
		this.reservado = reservado;
	}

	public int getIdExemplar() {
		return idExemplar;
	}

	public void setIdExemplar(int idExemplar) {
		this.idExemplar = idExemplar;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public Editora getEditora() {
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public String getReservado() {
		return reservado;
	}

	public void setReservado(String reservado) {
		this.reservado = reservado;
	}
	public String getReservadoTraduzido() {
		return "S".equals(reservado) ? "Sim" : "Não";
	}
	
	@Override
	public String toString() {
		return this.livro.getNome();
	}

}