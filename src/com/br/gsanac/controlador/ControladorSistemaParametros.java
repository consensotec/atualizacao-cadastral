package com.br.gsanac.controlador;

import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.ControladorException;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.repositorio.RepositorioSistemaParametros;

/**
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class ControladorSistemaParametros extends ControladorBase<SistemaParametros> implements IControladorSistemaParametros {

    private static ControladorSistemaParametros instance;

    private ControladorSistemaParametros() {
        super();
    }

    /**
     * @author Arthur Carvalho
     * @since 06/12/2012
     * @return controllerUser instance
     */
    public static ControladorSistemaParametros getInstance() {
        if (instance == null) {
            instance = new ControladorSistemaParametros();
        }
        return instance;
    }

    /**
     * @author Arthur Carvalho
     * @since 06/12/2012
     */
    @Override
    public SistemaParametros validarLogin(String login, String password) throws ControladorException {
        SistemaParametros sistemaParametros = null;
        try {
        	sistemaParametros = RepositorioSistemaParametros.getInstance().validarLogin(login, password);
        	
        } catch (RepositorioException e) {
            e.printStackTrace();
        }
        return sistemaParametros;
    }
}