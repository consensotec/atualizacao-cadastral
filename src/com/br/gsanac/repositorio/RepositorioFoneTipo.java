package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.FoneTipo;
import com.br.gsanac.entidades.FoneTipo.FoneTipoColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 10/12/12
 */
public class RepositorioFoneTipo extends RepositorioBase<FoneTipo> {

    private static RepositorioFoneTipo instance;

    public RepositorioFoneTipo() {
        super();
    }

    public static RepositorioFoneTipo getInstance() {
        if (instance == null) {
            instance = new RepositorioFoneTipo();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(FoneTipo entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	FoneTipo foneTipo = new FoneTipo();

        if (selection == null || selection.trim().equals("")) {
            selection = FoneTipoColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(foneTipo.getNomeTabela(),
            							 FoneTipo.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            foneTipo = foneTipo.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return foneTipo;
	}

	@Override
	public List<FoneTipo> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<FoneTipo> listaFoneTipo = null;
        FoneTipo foneTipo = new FoneTipo();
        try {
        	listaFoneTipo = new ArrayList<FoneTipo>();

            cursor = super.getDb().query(foneTipo.getNomeTabela(),
            							 FoneTipo.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaFoneTipo = foneTipo.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaFoneTipo;
	}

	@Override
	public long inserir(FoneTipo foneTipo) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = foneTipo.carregarValues();
       
        try {
           return super.getDb().insert(foneTipo.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
		
	}

	@Override
	public void atualizar(FoneTipo foneTipo) throws RepositorioException {
    	ContentValues values =  foneTipo.carregarValues();

        String _id = String.valueOf(foneTipo.getId());

        String where = FoneTipoColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(foneTipo.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(FoneTipo foneTipo) throws RepositorioException {
        String _id = String.valueOf(foneTipo.getId());

        String where = FoneTipoColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(foneTipo.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
