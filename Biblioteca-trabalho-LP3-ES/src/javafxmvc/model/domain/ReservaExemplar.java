package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class ReservaExemplar implements Serializable{
	
	private LocalDate dtCancelamento;
	private Reserva reserva;
	private Exemplar exemplar;
	
	public ReservaExemplar() {
		super();
	}

	public ReservaExemplar(LocalDate dtCancelamento, Reserva reserva, Exemplar exemplar) {
		super();
		this.dtCancelamento = dtCancelamento;
		this.reserva = reserva;
		this.exemplar = exemplar;
	}

	public LocalDate getDtCancelamento() {
		return dtCancelamento;
	}

	public void setDtCancelamento(LocalDate dtCancelamento) {
		this.dtCancelamento = dtCancelamento;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public Exemplar getExemplar() {
		return exemplar;
	}

	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

	@Override
	public String toString() {
		return "ReservaExemplar [dtCancelamento=" + dtCancelamento + ", reserva=" + reserva + ", exemplar=" + exemplar
				+ "]";
	}

}
