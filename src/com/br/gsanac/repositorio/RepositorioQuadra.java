package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.Quadra;
import com.br.gsanac.entidades.Quadra.QuadraColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 12/12/12
 */
public class RepositorioQuadra extends RepositorioBase<Quadra> {

    private static RepositorioQuadra instance;

    public RepositorioQuadra() {
        super();
    }

    public static RepositorioQuadra getInstance() {
        if (instance == null) {
            instance = new RepositorioQuadra();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(Quadra entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	Quadra quadra = new Quadra();

        if (selection == null || selection.trim().equals("")) {
            selection = QuadraColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(quadra.getNomeTabela(),
            							 Quadra.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            quadra = quadra.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return quadra;
	}

	@Override
	public List<Quadra> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<Quadra> listaQuadra = null;
        Quadra quadra = new Quadra();
        try {
        	listaQuadra = new ArrayList<Quadra>();

            cursor = super.getDb().query(quadra.getNomeTabela(),
            							 Quadra.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaQuadra = quadra.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaQuadra;
	}

	@Override
	public long inserir(Quadra quadra) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = quadra.carregarValues();
       
        try {
        	return super.getDb().insert(quadra.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(Quadra quadra) throws RepositorioException {
    	ContentValues values =  quadra.carregarValues();

        String _id = String.valueOf(quadra.getId());

        String where = QuadraColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(quadra.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(Quadra quadra) throws RepositorioException {
        String _id = String.valueOf(quadra.getId());

        String where = QuadraColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(quadra.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
