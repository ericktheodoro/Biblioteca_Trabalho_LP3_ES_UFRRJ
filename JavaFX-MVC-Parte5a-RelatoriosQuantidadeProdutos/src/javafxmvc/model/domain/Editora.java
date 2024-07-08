package javafxmvc.model.domain;

import java.io.Serializable;

public class Editora implements Serializable {

    private int idEditora;
    private String nome;
    private String endereco;
    private String telefone;
    private String contato;
    private String status;

	public Editora() {
	}

	public Editora(int idEditora, String nome, String endereco, String telefone, String contato, String status) {
		super();
		this.idEditora = idEditora;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.contato = contato;
		this.status = status;
	}

	public int getIdEditora() {
		return idEditora;
	}

	public void setIdEditora(int idEditora) {
		this.idEditora = idEditora;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
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
