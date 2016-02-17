package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.SetorComercial;
import com.br.gsanac.entidades.SetorComercial.SetorComercialColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 25/06/2013
 */
public class RepositorioSetorComercial extends RepositorioBase<SetorComercial> {

    private static RepositorioSetorComercial instance;

    public RepositorioSetorComercial() {
        super();
    }

    public static RepositorioSetorComercial getInstance() {
        if (instance == null) {
            instance = new RepositorioSetorComercial();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(SetorComercial entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	SetorComercial setorComercial = new SetorComercial();

        if (selection == null || selection.trim().equals("")) {
            selection = SetorComercialColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(setorComercial.getNomeTabela(),
            							 SetorComercial.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            setorComercial = setorComercial.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return setorComercial;
	}

	@Override
	public List<SetorComercial> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<SetorComercial> listaSetorComercial = null;
        SetorComercial setorComercial = new SetorComercial();
        try {
        	listaSetorComercial = new ArrayList<SetorComercial>();

            cursor = super.getDb().query(setorComercial.getNomeTabela(),
            							 SetorComercial.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaSetorComercial = setorComercial.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaSetorComercial;
	}

	@Override
	public long inserir(SetorComercial setorComercial) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = setorComercial.carregarValues();
       
        try {
        	return super.getDb().insert(setorComercial.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(SetorComercial setorComercial) throws RepositorioException {
    	ContentValues values =  setorComercial.carregarValues();

        String _id = String.valueOf(setorComercial.getId());

        String where = SetorComercialColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(setorComercial.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(SetorComercial setorComercial) throws RepositorioException {
        String _id = String.valueOf(setorComercial.getId());

        String where = SetorComercialColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(setorComercial.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
