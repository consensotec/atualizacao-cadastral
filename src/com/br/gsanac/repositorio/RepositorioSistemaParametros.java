package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.entidades.SistemaParametros.SistemaParametrosColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Arthur Carvalho
 */
public class RepositorioSistemaParametros extends RepositorioBase<SistemaParametros> {

    private static RepositorioSistemaParametros instance;

    public RepositorioSistemaParametros() {
        super();
    }

    public static RepositorioSistemaParametros getInstance() {
        if (instance == null) {
            instance = new RepositorioSistemaParametros();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

    
    @Override
    public SistemaParametros pesquisar(SistemaParametros entity, String selection, String[] selectionArgs) throws RepositorioException {
        
    	Cursor cursor = null;

    	SistemaParametros sistemaParametros = new SistemaParametros();

//        if (selection == null || selection.trim().equals("")) {
//            selection = SistemaParametrosColunas.ID + "=?";
//        }
//
//        if (selectionArgs == null) {
//            selectionArgs = new String[] {
//                String.valueOf(1)
//            };
//        }

        try {
            cursor = super.getDb().query(sistemaParametros.getNomeTabela(),
                                         SistemaParametros.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            sistemaParametros = sistemaParametros.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return sistemaParametros;
    }

    @Override
    public List<SistemaParametros> pesquisarLista(String selection, String[] selectionArgs, String orderBy) throws RepositorioException {
        Cursor cursor = null;

        List<SistemaParametros> listaSistemaParametros = null;
        SistemaParametros sistemaParametros = new SistemaParametros();
        try {
        	listaSistemaParametros = new ArrayList<SistemaParametros>();

            cursor = super.getDb().query(sistemaParametros.getNomeTabela(),
            							 SistemaParametros.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaSistemaParametros = sistemaParametros.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaSistemaParametros;
    }

    @Override
    public long inserir(SistemaParametros ps) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = ps.carregarValues();
       
        try {
        	return super.getDb().insert(ps.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
    }

    @Override
    public void atualizar(SistemaParametros ps) throws RepositorioException {
        
    	ContentValues values =  ps.carregarValues();

        String _id = String.valueOf(ps.getId());

        String where = SistemaParametrosColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(ps.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
    }

    @Override
    public void remover(SistemaParametros ps) throws RepositorioException {
        String _id = String.valueOf(ps.getId());

        String where = SistemaParametrosColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(ps.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
    }

   /**
    * 
    * @author Arthur Carvalho
    * @date 06/12/2012
    *
    * @param login
    * @param password
    * @return
    * @throws RepositorioException
    */
	public SistemaParametros validarLogin(String login, String password) throws RepositorioException {
       
    	Cursor cursor = null;

        SistemaParametros sistemaParametros = new SistemaParametros();

        try {
            cursor = super.getDb().query(sistemaParametros.getNomeTabela(),
            							 SistemaParametros.colunas,
            							 SistemaParametrosColunas.LOGIN + "='" + login + "' AND " + SistemaParametrosColunas.SENHA + "='" + password + "'",
                                         null,
                                         null,
                                         null,
                                         null,
                                         null);

            sistemaParametros = sistemaParametros.carregarEntidade(cursor);
           
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        
        return sistemaParametros;
    }
	
	 /**
	    * 
	    * @author Arthur Carvalho
	    * @date 06/12/2012
	    *
	    * @param login
	    * @param password
	    * @return
	    * @throws RepositorioException
	    */
		public SistemaParametros validarLoginCpf(String login) throws RepositorioException {
	       
	    	Cursor cursor = null;

	        SistemaParametros sistemaParametros = new SistemaParametros();

	        try {
	            cursor = super.getDb().query(sistemaParametros.getNomeTabela(),
	            							 SistemaParametros.colunas,
	            							 SistemaParametrosColunas.LOGIN + "='" + login + "' AND " + SistemaParametrosColunas.SENHA + " is null ",
	                                         null,
	                                         null,
	                                         null,
	                                         null,
	                                         null);

	            sistemaParametros = sistemaParametros.carregarEntidade(cursor);
	           
	        } catch (SQLException sqe) {
	            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
	            sqe.printStackTrace();
	            throw new RepositorioException(context.getResources().getString(R.string.db_error));
	        } finally {
	            if (cursor != null) {
	                cursor.close();
	            }
	        }
	        
	        return sistemaParametros;
	    }
}