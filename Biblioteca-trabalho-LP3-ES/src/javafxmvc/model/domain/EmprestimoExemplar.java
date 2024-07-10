package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class EmprestimoExemplar implements Serializable{
	private LocalDate dtDevolucao;
	private Exemplar exemplar;
	private Emprestimo emprestimo;

	public EmprestimoExemplar(LocalDate dtDevolucao, Exemplar exemplar, Emprestimo emprestimo) {
		super();
		this.dtDevolucao = dtDevolucao;
		this.exemplar = exemplar;
		this.emprestimo = emprestimo;
	}

	public LocalDate getDtDevolucao() {
		return dtDevolucao;
	}

	public void setDtDevolucao(LocalDate dtDevolucao) {
		this.dtDevolucao = dtDevolucao;
	}

	public Exemplar getExemplar() {
		return exemplar;
	}

	public void setExemplar(Exemplar exemplar) {
		this.exemplar = exemplar;
	}

	public Emprestimo getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}

	@Override
	public String toString() {
		return "EmprestimoExemplar [dtDevolucao=" + dtDevolucao + ", exemplar=" + exemplar + ", emprestimo="
				+ emprestimo + "]";
	}




}
