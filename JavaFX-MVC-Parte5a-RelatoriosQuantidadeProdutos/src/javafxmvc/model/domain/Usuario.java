package javafxmvc.model.domain;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int idUsuario;
    private String nome;
    private String endereco;
    private String telefone;
    private String status;

    public Usuario(){
    }




    public Usuario(int idUsuario, String nome, String endereco, String telefone, String status) {
		super();
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
		this.status = status;
	}




	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getidUsuario() {
        return idUsuario;
    }

    public void setidUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    @Override
    public String toString() {
        return this.nome;
    }

}
