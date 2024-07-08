package javafxmvc.model.domain;

import java.io.Serializable;

public class LivroAutor implements Serializable {
	private Autor autor;
	private Livro livro;

	public LivroAutor(Autor autor, Livro livro) {
		super();
		this.autor = autor;
		this.livro = livro;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	@Override
	public String toString() {
		return "LivrosAutor [autor=" + autor + ", livro=" + livro + "]";
	}


}
