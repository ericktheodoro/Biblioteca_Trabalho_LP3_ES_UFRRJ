package javafxmvc.model.domain;

import java.io.Serializable;

public class PalavraChave implements Serializable {

	private int idPalavraChave;
	private String nome;
	private String status;

	public PalavraChave() {
	}

	public PalavraChave(int idPalavraChave, String nome, String status) {
		super();
		this.idPalavraChave = idPalavraChave;
		this.nome = nome;
		this.status = status;
	}

	public int getIdPalavraChave() {
		return idPalavraChave;
	}

	public void setIdPalavraChave(int idPalavraChave) {
		this.idPalavraChave = idPalavraChave;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getStatus() {
		return status;
	}
	
	public String getStatusDescricao() {
        return "H".equals(status) ? "Habilitado" : "Desabilitado";
    }

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return this.nome;
	}

}
