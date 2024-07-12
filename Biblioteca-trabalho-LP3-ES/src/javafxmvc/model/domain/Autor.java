package javafxmvc.model.domain;

import java.io.Serializable;

public class Autor implements Serializable {

	private int idAutor;
	private String nome;
	private String telefone;
	private String status;

	public Autor() {
	}

	public Autor(int idAutor, String nome, String telefone, String status) {
		super();
		this.idAutor = idAutor;
		this.nome = nome;
		this.telefone = telefone;
		this.status = status;
	}

	public int getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(int idAutor) {
		this.idAutor = idAutor;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return this.nome;
	}

}
