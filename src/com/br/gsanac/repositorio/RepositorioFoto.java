package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.Foto;
import com.br.gsanac.entidades.Foto.FotoColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 04/01/2013
 */
public class RepositorioFoto extends RepositorioBase<Foto> {

    private static RepositorioFoto instance;

    public RepositorioFoto() {
        super();
    }

    public static RepositorioFoto getInstance() {
        if (instance == null) {
            instance = new RepositorioFoto();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(Foto entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	Foto foto = new Foto();

        if (selection == null || selection.trim().equals("")) {
            selection = FotoColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(foto.getNomeTabela(),
            							 Foto.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            foto = foto.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return foto;
	}

	@Override
	public List<Foto> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<Foto> listaFoto = null;
        Foto foto = new Foto();
        try {
        	listaFoto = new ArrayList<Foto>();

            cursor = super.getDb().query(foto.getNomeTabela(),
            							 Foto.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaFoto = foto.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaFoto;
	}

	@Override
	public long inserir(Foto foto) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = foto.carregarValues();
       
        try {
           return super.getDb().insert(foto.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }		
	}

	@Override
	public void atualizar(Foto foto) throws RepositorioException {
    	ContentValues values =  foto.carregarValues();

        String _id = String.valueOf(foto.getId());

        String where = FotoColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(foto.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(Foto foto) throws RepositorioException {
        String _id = String.valueOf(foto.getId());

        String where = FotoColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(foto.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
		
	}
}
