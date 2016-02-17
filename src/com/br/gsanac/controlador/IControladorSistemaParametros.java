package com.br.gsanac.controlador;

import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.ControladorException;

/**
 * @author Arthur Carvalho
 */
public interface IControladorSistemaParametros {
    public SistemaParametros validarLogin(String login, String password) throws ControladorException;
}
