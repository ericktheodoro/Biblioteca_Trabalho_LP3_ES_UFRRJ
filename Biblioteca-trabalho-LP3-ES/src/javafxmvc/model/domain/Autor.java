package javafxmvc.model.domain;

import java.io.Serializable;

public class Autor implements Serializable {

	private int idAutor;
	private String nome;
	private String telefone;
	private String satatus;

	public Autor() {
	}

	public Autor(int idAutor, String nome, String telefone, String satatus) {
		super();
		this.idAutor = idAutor;
		this.nome = nome;
		this.telefone = telefone;
		this.satatus = satatus;
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

	public String getSatatus() {
		return satatus;
	}

	public void setSatatus(String satatus) {
		this.satatus = satatus;
	}

	@Override
	public String toString() {
		return this.nome;
	}

}
