package javafxmvc.model.domain;

import java.io.Serializable;

public class LivroPalavraChave implements Serializable {
	private Livro livro;
	private PalavraChave palavraChave;

	public LivroPalavraChave(Livro livro, PalavraChave palavraChave) {
		super();
		this.livro = livro;
		this.palavraChave = palavraChave;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public PalavraChave getPalavraChave() {
		return palavraChave;
	}

	public void setPalavraChave(PalavraChave palavraChave) {
		this.palavraChave = palavraChave;
	}

	@Override
	public String toString() {
		return "LivroPalavrachave [livro=" + livro + ", palavraChave=" + palavraChave + "]";
	}


}
