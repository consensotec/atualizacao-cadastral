package com.br.gsanac.exception;

import android.util.Log;

import com.br.gsanac.util.ConstantesSistema;

public class FachadaException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -5627298278947792277L;

    public FachadaException(String message) {
        super(message);
        Log.e(ConstantesSistema.LOG_TAG, message);
    }

    public FachadaException() {
        super();
    }
}
