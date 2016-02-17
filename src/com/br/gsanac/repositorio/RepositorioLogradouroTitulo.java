package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.LogradouroTitulo;
import com.br.gsanac.entidades.LogradouroTitulo.LogradouroTituloColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 12/12/12
 */
public class RepositorioLogradouroTitulo extends RepositorioBase<LogradouroTitulo> {

    private static RepositorioLogradouroTitulo instance;

    public RepositorioLogradouroTitulo() {
        super();
    }

    public static RepositorioLogradouroTitulo getInstance() {
        if (instance == null) {
            instance = new RepositorioLogradouroTitulo();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(LogradouroTitulo entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	LogradouroTitulo logradouroTitulo = new LogradouroTitulo();

        if (selection == null || selection.trim().equals("")) {
            selection = LogradouroTituloColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(logradouroTitulo.getNomeTabela(),
            							 LogradouroTitulo.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            logradouroTitulo = logradouroTitulo.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return logradouroTitulo;
	}

	@Override
	public List<LogradouroTitulo> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<LogradouroTitulo> listaLogradouroTitulo = null;
        LogradouroTitulo logradouroTitulo = new LogradouroTitulo();
        try {
        	listaLogradouroTitulo = new ArrayList<LogradouroTitulo>();

            cursor = super.getDb().query(logradouroTitulo.getNomeTabela(),
            							 LogradouroTitulo.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaLogradouroTitulo = logradouroTitulo.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaLogradouroTitulo;
	}

	@Override
	public long inserir(LogradouroTitulo logradouroTitulo) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = logradouroTitulo.carregarValues();
       
        try {
        	return super.getDb().insert(logradouroTitulo.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(LogradouroTitulo logradouroTitulo) throws RepositorioException {
    	ContentValues values =  logradouroTitulo.carregarValues();

        String _id = String.valueOf(logradouroTitulo.getId());

        String where = LogradouroTituloColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(logradouroTitulo.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(LogradouroTitulo logradouroTitulo) throws RepositorioException {
        String _id = String.valueOf(logradouroTitulo.getId());

        String where = LogradouroTituloColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(logradouroTitulo.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
