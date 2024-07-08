package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Exemplar implements Serializable {

	private int idExemplar;
	private LocalDate dataAquisicao;
	private Livro livro;
	private Editora editora;

	public Exemplar() {
	}

	public Exemplar(int idExemplar, LocalDate dataAquisicao) {
		super();
		this.idExemplar = idExemplar;
		this.dataAquisicao = dataAquisicao;
	}

	public int getIdExemplar() {
		return idExemplar;
	}

	public void setIdExemplar(int idExemplar) {
		this.idExemplar = idExemplar;
	}

	public LocalDate getDataAquisicao() {
		return dataAquisicao;
	}

	public void setDataAquisicao(LocalDate dataAquisicao) {
		this.dataAquisicao = dataAquisicao;
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

	@Override
	public String toString() {
		return this.livro.getNome();
	}
}
