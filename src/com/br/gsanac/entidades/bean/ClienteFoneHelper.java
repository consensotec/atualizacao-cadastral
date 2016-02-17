package com.br.gsanac.entidades.bean;

/**
 * 
 * @author Davi Menezes
 * @date 03/01/2012
 * 
 */
public class ClienteFoneHelper {

	private int idFoneTipo;
	
	private String descricaoFoneTipo;
	
	private String codigoDDD;
	
	private String numeroTelefone;

	public int getIdFoneTipo() {
		return idFoneTipo;
	}

	public void setIdFoneTipo(int idFoneTipo) {
		this.idFoneTipo = idFoneTipo;
	}

	public String getDescricaoFoneTipo() {
		return descricaoFoneTipo;
	}

	public void setDescricaoFoneTipo(String descricaoFoneTipo) {
		this.descricaoFoneTipo = descricaoFoneTipo;
	}

	public String getCodigoDDD() {
		return codigoDDD;
	}

	public void setCodigoDDD(String codigoDDD) {
		this.codigoDDD = codigoDDD;
	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codigoDDD == null) ? 0 : codigoDDD.hashCode());
		result = prime * result
				+ ((numeroTelefone == null) ? 0 : numeroTelefone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteFoneHelper other = (ClienteFoneHelper) obj;
		if (codigoDDD == null) {
			if (other.codigoDDD != null)
				return false;
		} else if (!codigoDDD.equals(other.codigoDDD))
			return false;
		if (numeroTelefone == null) {
			if (other.numeroTelefone != null)
				return false;
		} else if (!numeroTelefone.equals(other.numeroTelefone))
			return false;
		return true;
	}

}
