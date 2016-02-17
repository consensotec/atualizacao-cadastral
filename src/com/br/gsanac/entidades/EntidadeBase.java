package com.br.gsanac.entidades;

import java.io.Serializable;

import com.br.gsanac.util.Util;

/**
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class EntidadeBase implements Serializable {

    private static final long serialVersionUID = -6654289119975726850L;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = Util.parseStringToInteger(id);
    }

}