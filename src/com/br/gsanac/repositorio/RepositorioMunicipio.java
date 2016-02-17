package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.Municipio;
import com.br.gsanac.entidades.Municipio.MunicipioColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 12/12/12
 */
public class RepositorioMunicipio extends RepositorioBase<Municipio> {

    private static RepositorioMunicipio instance;

    public RepositorioMunicipio() {
        super();
    }

    public static RepositorioMunicipio getInstance() {
        if (instance == null) {
            instance = new RepositorioMunicipio();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(Municipio entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	Municipio municipio = new Municipio();

//        if (selection == null || selection.trim().equals("")) {
//            selection = MunicipioColunas.ID + "=?";
//        }
//
//        if (selectionArgs == null) {
//            selectionArgs = new String[] {
//                String.valueOf(entity.getId())
//            };
//        }

        try {
            cursor = super.getDb().query(municipio.getNomeTabela(),
            							 Municipio.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            municipio = municipio.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return municipio;
	}

	@Override
	public List<Municipio> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<Municipio> listaMunicipio = null;
        Municipio municipio = new Municipio();
        try {
        	listaMunicipio = new ArrayList<Municipio>();

            cursor = super.getDb().query(municipio.getNomeTabela(),
            							 Municipio.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaMunicipio = municipio.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaMunicipio;
	}

	@Override
	public long inserir(Municipio municipio) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = municipio.carregarValues();
       
        try {
        	return super.getDb().insert(municipio.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(Municipio municipio) throws RepositorioException {
    	ContentValues values =  municipio.carregarValues();

        String _id = String.valueOf(municipio.getId());

        String where = MunicipioColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(municipio.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(Municipio municipio) throws RepositorioException {
        String _id = String.valueOf(municipio.getId());

        String where = MunicipioColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(municipio.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
