package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.HidrometroMarca;
import com.br.gsanac.entidades.HidrometroMarca.HidrometroMarcaColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 03/01/2013
 */
public class RepositorioHidrometroMarca extends RepositorioBase<HidrometroMarca> {

    private static RepositorioHidrometroMarca instance;

    public RepositorioHidrometroMarca() {
        super();
    }

    public static RepositorioHidrometroMarca getInstance() {
        if (instance == null) {
            instance = new RepositorioHidrometroMarca();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(HidrometroMarca entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	HidrometroMarca hidrometroMarca = new HidrometroMarca();

        if (selection == null || selection.trim().equals("")) {
            selection = HidrometroMarcaColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(hidrometroMarca.getNomeTabela(),
            							 HidrometroMarca.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            hidrometroMarca = hidrometroMarca.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return hidrometroMarca;
	}

	@Override
	public List<HidrometroMarca> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<HidrometroMarca> listaHidrometroMarca = null;
        HidrometroMarca hidrometroMarca = new HidrometroMarca();
        try {
        	listaHidrometroMarca = new ArrayList<HidrometroMarca>();

            cursor = super.getDb().query(hidrometroMarca.getNomeTabela(),
            							 HidrometroMarca.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaHidrometroMarca = hidrometroMarca.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaHidrometroMarca;
	}

	@Override
	public long inserir(HidrometroMarca hidrometroMarca) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = hidrometroMarca.carregarValues();
       
        try {
           return super.getDb().insert(hidrometroMarca.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(HidrometroMarca hidrometroMarca) throws RepositorioException {
    	ContentValues values =  hidrometroMarca.carregarValues();

        String _id = String.valueOf(hidrometroMarca.getId());

        String where = HidrometroMarcaColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(hidrometroMarca.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(HidrometroMarca hidrometroMarca) throws RepositorioException {
        String _id = String.valueOf(hidrometroMarca.getId());

        String where = HidrometroMarcaColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(hidrometroMarca.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
