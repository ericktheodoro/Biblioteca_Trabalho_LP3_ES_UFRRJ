package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Reserva implements Serializable {

	private int idReserva;
	private LocalDate dtReserva;
	private LocalDate dtEmprestimo;
	private Usuario usuario; 
	private List<ReservaExemplar> reservaExemplar;



	public Reserva(int idReserva, LocalDate dtReserva, LocalDate dtEmprestimo, Usuario usuario,
			List<ReservaExemplar> reservaExemplar) {
		super();
		this.idReserva = idReserva;
		this.dtReserva = dtReserva;
		this.dtEmprestimo = dtEmprestimo;
		this.usuario = usuario;
		this.reservaExemplar = reservaExemplar;
	}

	public LocalDate getDtReserva() {
		return dtReserva;
	}

	public void setDtReserva(LocalDate dtReserva) {
		this.dtReserva = dtReserva;
	}

	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
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
