package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.HidrometroLocalInst;
import com.br.gsanac.entidades.HidrometroLocalInst.HidrometroLocalInstColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 10/12/12
 */
public class RepositorioHidrometroLocalInst extends RepositorioBase<HidrometroLocalInst> {

    private static RepositorioHidrometroLocalInst instance;

    public RepositorioHidrometroLocalInst() {
        super();
    }

    public static RepositorioHidrometroLocalInst getInstance() {
        if (instance == null) {
            instance = new RepositorioHidrometroLocalInst();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }
	
	@Override
	public EntidadeBase pesquisar(HidrometroLocalInst entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	HidrometroLocalInst hidrometroLocalInst = new HidrometroLocalInst();

        if (selection == null || selection.trim().equals("")) {
            selection = HidrometroLocalInstColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(hidrometroLocalInst.getNomeTabela(),
            							 HidrometroLocalInst.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            hidrometroLocalInst = hidrometroLocalInst.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return hidrometroLocalInst;
	}

	@Override
	public List<HidrometroLocalInst> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
        
		Cursor cursor = null;

        List<HidrometroLocalInst> listaHidrometroLocalInst = null;
        HidrometroLocalInst hidrometroLocalInst = new HidrometroLocalInst();
        
        try {
        	listaHidrometroLocalInst = new ArrayList<HidrometroLocalInst>();

            cursor = super.getDb().query(hidrometroLocalInst.getNomeTabela(),
            							 HidrometroLocalInst.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaHidrometroLocalInst = hidrometroLocalInst.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaHidrometroLocalInst;
	}

	@Override
	public long inserir(HidrometroLocalInst hidrometroLocalInst) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = hidrometroLocalInst.carregarValues();
       
        try {
          return super.getDb().insert(hidrometroLocalInst.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(HidrometroLocalInst hidrometroLocalInst) throws RepositorioException {
    	
		ContentValues values =  hidrometroLocalInst.carregarValues();

        String _id = String.valueOf(hidrometroLocalInst.getId());

        String where = HidrometroLocalInstColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(hidrometroLocalInst.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(HidrometroLocalInst hidrometroLocalInst) throws RepositorioException {
        String _id = String.valueOf(hidrometroLocalInst.getId());

        String where = HidrometroLocalInstColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(hidrometroLocalInst.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
	
}
