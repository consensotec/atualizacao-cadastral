package com.br.gsanac.entidades;

/**
 * Classe que monta os dados da tela de roteiro.
 * 
 * @author Arthur Carvalho
 *
 */
public class Roteiro extends EntidadeBase {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nomeCliente;
	
	private Integer matriculaImovel;
	
	private String descricaoLogradouro;
	
	private String descricaoLogradouroTipo;
	
	private String descricaoLogradouroTitulo;
	
	private String numeroImovel;
	
	private String descricaoBairro;
	
	private Integer idLocalidade;
	
	private Integer codigoSetorComercial;
	
	private Integer numeroQuadra;
	
	private Integer numeroLote;
	
	private Integer numeroSublote;
	
	private Integer codigoCep;
	
	private String descricaoMunicipio;
	
	private Integer indicadorFinalizado;
	
	private Integer posicao;

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Integer getMatriculaImovel() {
		return matriculaImovel;
	}

	public void setMatriculaImovel(Integer matriculaImovel) {
		this.matriculaImovel = matriculaImovel;
	}

	public String getDescricaoLogradouro() {
		return descricaoLogradouro;
	}

	public void setDescricaoLogradouro(String descricaoLogradouro) {
		this.descricaoLogradouro = descricaoLogradouro;
	}

	public String getDescricaoLogradouroTipo() {
		return descricaoLogradouroTipo;
	}

	public void setDescricaoLogradouroTipo(String descricaoLogradouroTipo) {
		this.descricaoLogradouroTipo = descricaoLogradouroTipo;
	}

	public String getDescricaoLogradouroTitulo() {
		return descricaoLogradouroTitulo;
	}

	public void setDescricaoLogradouroTitulo(String descricaoLogradouroTitulo) {
		this.descricaoLogradouroTitulo = descricaoLogradouroTitulo;
	}

	public String getNumeroImovel() {
		return numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel) {
		this.numeroImovel = numeroImovel;
	}

	public String getDescricaoBairro() {
		return descricaoBairro;
	}

	public void setDescricaoBairro(String descricaoBairro) {
		this.descricaoBairro = descricaoBairro;
	}

	public Integer getIdLocalidade() {
		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade) {
		this.idLocalidade = idLocalidade;
	}

	public Integer getCodigoSetorComercial() {
		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial) {
		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Integer getNumeroQuadra() {
		return numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra) {
		this.numeroQuadra = numeroQuadra;
	}

	public Integer getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(Integer numeroLote) {
		this.numeroLote = numeroLote;
	}

	public Integer getNumeroSublote() {
		return numeroSublote;
	}

	public void setNumeroSublote(Integer numeroSublote) {
		this.numeroSublote = numeroSublote;
	}

	public Integer getCodigoCep() {
		return codigoCep;
	}

	public void setCodigoCep(Integer codigoCep) {
		this.codigoCep = codigoCep;
	}

	public String getDescricaoMunicipio() {
		return descricaoMunicipio;
	}

	public void setDescricaoMunicipio(String descricaoMunicipio) {
		this.descricaoMunicipio = descricaoMunicipio;
	}

	public Integer getIndicadorFinalizado() {
		return indicadorFinalizado;
	}

	public void setIndicadorFinalizado(Integer indicadorFinalizado) {
		this.indicadorFinalizado = indicadorFinalizado;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
	
	
}
