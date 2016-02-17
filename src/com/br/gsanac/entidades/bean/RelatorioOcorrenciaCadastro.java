package com.br.gsanac.entidades.bean;

import java.io.Serializable;

public class RelatorioOcorrenciaCadastro implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer totalImoveis;
	private Integer totalImoveisAtualizados;
	private Integer totalImoveisIncluidos;
	private Integer totalImoveisVisitados;
	private String descricao;
	
	public RelatorioOcorrenciaCadastro(Integer totalImoveis, Integer totalImoveisAtualizados, Integer totalImoveisIncluidos, Integer totalImoveisVisitados, String descricao){
		super();
		this.totalImoveis = totalImoveis;
		this.totalImoveisAtualizados = totalImoveisAtualizados;
		this.totalImoveisIncluidos = totalImoveisIncluidos;
		this.totalImoveisVisitados = totalImoveisVisitados;
		this.descricao = descricao;
	}
	


	public Integer getTotalImoveis() {
		return totalImoveis;
	}


	public void setTotalImoveis(Integer totalImoveis) {
		this.totalImoveis = totalImoveis;
	}


	public Integer getTotalImoveisAtualizados() {
		return totalImoveisAtualizados;
	}

	public void setTotalImoveisAtualizados(Integer totalImoveisAtualizados) {
		this.totalImoveisAtualizados = totalImoveisAtualizados;
	}

	public Integer getTotalImoveisIncluidos() {
		return totalImoveisIncluidos;
	}

	public void setTotalImoveisIncluidos(Integer totalImoveisIncluidos) {
		this.totalImoveisIncluidos = totalImoveisIncluidos;
	}

	public Integer getTotalImoveisVisitados() {
		return totalImoveisVisitados;
	}

	public void setTotalImoveisVisitados(Integer totalImoveisVisitados) {
		this.totalImoveisVisitados = totalImoveisVisitados;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
