package javafxmvc.model.domain;

import java.io.Serializable;

public class Usuario implements Serializable {

	private int idUsuario;
    private String nome;
    private String endereco;
    private String tel;
    private String status;
	private String tipo;
  
    public Usuario(){
    }

    public Usuario(int idUsuario, String nome, String endereco, String tel, String status, String tipo) {
		super();
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.endereco = endereco;
		this.tel = tel;
		this.status = status;
		this.tipo = tipo;
	}


    public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
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

    public String getTel() {
        return tel;
    }

    public void setTel(String telefone) {
        this.tel = telefone;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

    @Override
    public String toString() {
        return this.nome;
    }

}
