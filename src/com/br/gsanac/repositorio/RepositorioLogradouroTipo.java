package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.LogradouroTipo;
import com.br.gsanac.entidades.LogradouroTipo.LogradouroTipoColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 12/12/12
 */
public class RepositorioLogradouroTipo extends RepositorioBase<LogradouroTipo> {

    private static RepositorioLogradouroTipo instance;

    public RepositorioLogradouroTipo() {
        super();
    }

    public static RepositorioLogradouroTipo getInstance() {
        if (instance == null) {
            instance = new RepositorioLogradouroTipo();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(LogradouroTipo entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	LogradouroTipo logradouroTipo = new LogradouroTipo();

//        if (selection == null || selection.trim().equals("")) {
//            selection = LogradouroTipoColunas.ID + "=?";
//        }
//
//        if (selectionArgs == null) {
//            selectionArgs = new String[] {
//                String.valueOf(entity.getId())
//            };
//        }

        try {
            cursor = super.getDb().query(logradouroTipo.getNomeTabela(),
            							 LogradouroTipo.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            logradouroTipo = logradouroTipo.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return logradouroTipo;
	}

	@Override
	public List<LogradouroTipo> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<LogradouroTipo> listaLogradouroTipo = null;
        LogradouroTipo logradouroTipo = new LogradouroTipo();
        try {
        	listaLogradouroTipo = new ArrayList<LogradouroTipo>();

            cursor = super.getDb().query(logradouroTipo.getNomeTabela(),
            							 LogradouroTipo.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaLogradouroTipo = logradouroTipo.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaLogradouroTipo;
	}

	@Override
	public long inserir(LogradouroTipo logradouroTipo) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = logradouroTipo.carregarValues();
       
        try {
        	return super.getDb().insert(logradouroTipo.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(LogradouroTipo logradouroTipo) throws RepositorioException {
    	ContentValues values =  logradouroTipo.carregarValues();

        String _id = String.valueOf(logradouroTipo.getId());

        String where = LogradouroTipoColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(logradouroTipo.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(LogradouroTipo logradouroTipo) throws RepositorioException {
        String _id = String.valueOf(logradouroTipo.getId());

        String where = LogradouroTipoColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(logradouroTipo.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
