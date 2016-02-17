package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.HidrometroCapacidade;
import com.br.gsanac.entidades.HidrometroCapacidade.HidrometroCapacidadeColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 03/01/2013
 */
public class RepositorioHidrometroCapacidade extends RepositorioBase<HidrometroCapacidade> {

    private static RepositorioHidrometroCapacidade instance;

    public RepositorioHidrometroCapacidade() {
        super();
    }

    public static RepositorioHidrometroCapacidade getInstance() {
        if (instance == null) {
            instance = new RepositorioHidrometroCapacidade();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(HidrometroCapacidade entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();

        if (selection == null || selection.trim().equals("")) {
            selection = HidrometroCapacidadeColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(hidrometroCapacidade.getNomeTabela(),
            							 HidrometroCapacidade.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            hidrometroCapacidade = hidrometroCapacidade.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return hidrometroCapacidade;
	}

	@Override
	public List<HidrometroCapacidade> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<HidrometroCapacidade> listaHidrometroCapacidade = null;
        HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();
        try {
        	listaHidrometroCapacidade = new ArrayList<HidrometroCapacidade>();

            cursor = super.getDb().query(hidrometroCapacidade.getNomeTabela(),
            							 HidrometroCapacidade.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaHidrometroCapacidade = hidrometroCapacidade.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaHidrometroCapacidade;
	}

	@Override
	public long inserir(HidrometroCapacidade hidrometroCapacidade) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = hidrometroCapacidade.carregarValues();
       
        try {
           return super.getDb().insert(hidrometroCapacidade.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(HidrometroCapacidade hidrometroCapacidade) throws RepositorioException {
    	ContentValues values =  hidrometroCapacidade.carregarValues();

        String _id = String.valueOf(hidrometroCapacidade.getId());

        String where = HidrometroCapacidadeColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(hidrometroCapacidade.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(HidrometroCapacidade hidrometroCapacidade) throws RepositorioException {
        String _id = String.valueOf(hidrometroCapacidade.getId());

        String where = HidrometroCapacidadeColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(hidrometroCapacidade.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
