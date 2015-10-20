package com.br.gsanac.pojos;

import java.io.Serializable;

import com.br.gsanac.utilitarios.Utilitarios;

/**
 * @author Jonathan Marcos
 * @since 05/09/2014
 */
public class EntidadeBasica implements Serializable {

    private static final long serialVersionUID = -6654289119975726850L;

    private Integer id;

    public EntidadeBasica() {
    	super();
    }
    
    public EntidadeBasica(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = Utilitarios.converterStringParaInteger(id);
    }

}