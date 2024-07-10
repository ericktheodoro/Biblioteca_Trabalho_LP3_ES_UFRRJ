package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Reserva implements Serializable {

	private LocalDate dtEmprestimo;
	private Usuario usuario; //CRIAR USUARIO
	private List<ReservaExemplar> reservaExemplar;

	public Reserva(LocalDate dtEmprestimo, Usuario usuario) {
		super();
		this.dtEmprestimo = dtEmprestimo;
		this.usuario = usuario;
	}

	public Reserva() {
		super();
	}

	public List<ReservaExemplar> getReservaExemplar() {
		return reservaExemplar;
	}

	public void setReservaExemplar(List<ReservaExemplar> reservaExemplar) {
		this.reservaExemplar = reservaExemplar;
	}

	public LocalDate getDtEmprestimo() {
		return dtEmprestimo;
	}

	public void setDtEmprestimo(LocalDate dtEmprestimo) {
		this.dtEmprestimo = dtEmprestimo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Reserva [dtEmprestimo=" + dtEmprestimo + "]";
	}

}
