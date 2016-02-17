package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.SubCategoria;
import com.br.gsanac.entidades.SubCategoria.SubCategoriaColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 10/12/12
 */
public class RepositorioSubCategoria extends RepositorioBase<SubCategoria> {

    private static RepositorioSubCategoria instance;

    public RepositorioSubCategoria() {
        super();
    }

    public static RepositorioSubCategoria getInstance() {
        if (instance == null) {
            instance = new RepositorioSubCategoria();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(SubCategoria entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	SubCategoria subCategoria = new SubCategoria();

        if (selection == null || selection.trim().equals("")) {
            selection = SubCategoriaColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(subCategoria.getNomeTabela(),
            							 SubCategoria.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            subCategoria = subCategoria.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return subCategoria;
	}

	@Override
	public List<SubCategoria> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<SubCategoria> listaSubCategoria = null;
        SubCategoria subCategoria = new SubCategoria();
        try {
        	listaSubCategoria = new ArrayList<SubCategoria>();

            cursor = super.getDb().query(subCategoria.getNomeTabela(),
            							 SubCategoria.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaSubCategoria = subCategoria.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaSubCategoria;
	}

	@Override
	public long inserir(SubCategoria subCategoria) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = subCategoria.carregarValues();
       
        try {
        	return super.getDb().insert(subCategoria.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(SubCategoria subCategoria) throws RepositorioException {
    	ContentValues values =  subCategoria.carregarValues();

        String _id = String.valueOf(subCategoria.getId());

        String where = SubCategoriaColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(subCategoria.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(SubCategoria subCategoria) throws RepositorioException {
        String _id = String.valueOf(subCategoria.getId());

        String where = SubCategoriaColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(subCategoria.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
