package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Livro implements Serializable {

	private int idLivro;
	private String nome;
	private LocalDate dataPublicacao;
	private String prefacio;
	private List<LivroPalavraChave> livroPalavraChave;
	private List<LivroAutor> livroAutor;

	public Livro() {
	}

	public Livro(int idLivro, String nome, LocalDate dataPublicacao, String prefacio) {
		super();
		this.idLivro = idLivro;
		this.nome = nome;
		this.dataPublicacao = dataPublicacao;
		this.prefacio = prefacio;
	}


	public List<LivroAutor> getLivroAutor() {
		return livroAutor;
	}

	public void setLivroAutor(List<LivroAutor> livroAutor) {
		this.livroAutor = livroAutor;
	}

	public List<LivroPalavraChave> getLivroPalavraChave() {
		return livroPalavraChave;
	}

	public void setLivroPalavraChave(List<LivroPalavraChave> livroPalavraChave) {
		this.livroPalavraChave = livroPalavraChave;
	}

	public int getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(int idLivro) {
		this.idLivro = idLivro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(LocalDate dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public String getPrefacio() {
		return prefacio;
	}

	public void setPrefacio(String prefacio) {
		this.prefacio = prefacio;
	}

	@Override
	public String toString() {
		return this.prefacio;
	}
}
