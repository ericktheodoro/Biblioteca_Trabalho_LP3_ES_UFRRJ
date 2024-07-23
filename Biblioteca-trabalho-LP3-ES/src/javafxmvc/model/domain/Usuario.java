package javafxmvc.model.domain;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Usuario implements Serializable {

    private String nome;
    private String endereco;
    private String tel;
    private String status;
	private String tipo;
	private String senha;
	private String usuario;
	
	private final IntegerProperty idUsuario;
  
    public Usuario(){
    	this.idUsuario = new SimpleIntegerProperty();
    }

    public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

    public int getId_Usuario() {
        return idUsuario.get();
    }

    public void setId_Usuario(int id_Usuario) {
        this.idUsuario.set(id_Usuario);
    }

    public IntegerProperty id_UsuarioProperty() {
        return idUsuario;
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
	
	public String getStatusDescricao() {
        return "H".equals(status) ? "Habilitado" : "Desabilitado";
    }

    public String getTipoDescricao() {
    	
    	if (tipo == null) {
            return "Tipo não definido"; // Ou outro valor padrão apropriado
        }
        switch (tipo) {
            case "1":
                return "Aluno";
            case "2":
                return "Professor";
            case "3":
                return "Funcionário";
            default:
                return "Aluno";
        }
    }
    
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

    @Override
    public String toString() {
        return this.nome;
    }

}
