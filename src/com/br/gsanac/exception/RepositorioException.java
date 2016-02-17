package com.br.gsanac.exception;

import android.util.Log;

import com.br.gsanac.util.ConstantesSistema;

public class RepositorioException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6282470126821833043L;

    public RepositorioException() {
        super();
    }

    public RepositorioException(String message) {
        super(message);
        Log.e(ConstantesSistema.LOG_TAG, message);
    }
}
