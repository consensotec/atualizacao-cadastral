package com.br.gsanac.entidades.bean;

import java.io.Serializable;

public class RelatorioPorCadastrador implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer totalImoveis;
	private Integer totalImoveisAtualizados;
	private Integer totalImoveisIncluidos;
	private Integer totalImoveisVisitados;
	private String login;
	
	public RelatorioPorCadastrador(Integer totalImoveis, Integer totalImoveisAtualizados, Integer totalImoveisIncluidos, Integer totalImoveisVisitados, String login){
		super();
		this.totalImoveis = totalImoveis;
		this.totalImoveisAtualizados = totalImoveisAtualizados;
		this.totalImoveisIncluidos = totalImoveisIncluidos;
		this.totalImoveisVisitados = totalImoveisVisitados;
		this.login = login;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	
	

}
