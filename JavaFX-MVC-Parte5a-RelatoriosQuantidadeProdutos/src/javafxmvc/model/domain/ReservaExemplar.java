package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class ReservaExemplar implements Serializable{
	private LocalDate dataDevolucao;
	private Exemplar exemplar;
	private Reserva reserva;

	public ReservaExemplar(LocalDate dataDevolucao, Exemplar exemplar, Reserva reserva) {
		super();
		this.dataDevolucao = dataDevolucao;
		this.exemplar = exemplar;
		this.reserva = reserva;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public Exemplar getExemplar() {
		return exemplar;
	}

	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	@Override
	public String toString() {
		return "ReservaExemplar [dataDevolucao=" + dataDevolucao + ", exemplar=" + exemplar + ", reserva=" + reserva
				+ "]";
	}


}
