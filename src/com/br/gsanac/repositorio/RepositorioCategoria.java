package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.Categoria;
import com.br.gsanac.entidades.Categoria.CategoriaColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 10/12/12
 */
public class RepositorioCategoria extends RepositorioBase<Categoria> {

    private static RepositorioCategoria instance;

    public RepositorioCategoria() {
        super();
    }

    public static RepositorioCategoria getInstance() {
        if (instance == null) {
            instance = new RepositorioCategoria();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(Categoria entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	Categoria categoria = new Categoria();

        if (selection == null || selection.trim().equals("")) {
            selection = CategoriaColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(categoria.getNomeTabela(),
            							 Categoria.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            categoria = categoria.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return categoria;
	}

	@Override
	public List<Categoria> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<Categoria> listaCategoria = null;
        Categoria categoria = new Categoria();
        try {
        	listaCategoria = new ArrayList<Categoria>();

            cursor = super.getDb().query(categoria.getNomeTabela(),
            							 Categoria.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaCategoria = categoria.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaCategoria;
	}

	@Override
	public long inserir(Categoria categoria) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = categoria.carregarValues();
       
        try {
           return super.getDb().insert(categoria.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }		
	}

	@Override
	public void atualizar(Categoria categoria) throws RepositorioException {
    	ContentValues values =  categoria.carregarValues();

        String _id = String.valueOf(categoria.getId());

        String where = CategoriaColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(categoria.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }		
	}

	@Override
	public void remover(Categoria categoria) throws RepositorioException {
        String _id = String.valueOf(categoria.getId());

        String where = CategoriaColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(categoria.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }		
	}
}
