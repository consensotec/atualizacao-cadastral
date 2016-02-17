package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.HidrometroProtecao;
import com.br.gsanac.entidades.HidrometroProtecao.HidrometroProtecaoColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 10/12/12
 */
public class RepositorioHidrometroProtecao extends RepositorioBase<HidrometroProtecao> {

    private static RepositorioHidrometroProtecao instance;

    public RepositorioHidrometroProtecao() {
        super();
    }

    public static RepositorioHidrometroProtecao getInstance() {
        if (instance == null) {
            instance = new RepositorioHidrometroProtecao();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(HidrometroProtecao entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();

        if (selection == null || selection.trim().equals("")) {
            selection = HidrometroProtecaoColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(hidrometroProtecao.getNomeTabela(),
            							 HidrometroProtecao.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            hidrometroProtecao = hidrometroProtecao.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return hidrometroProtecao;
	}

	@Override
	public List<HidrometroProtecao> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<HidrometroProtecao> listaHidrometroProtecao = null;
        HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
        try {
        	listaHidrometroProtecao = new ArrayList<HidrometroProtecao>();

            cursor = super.getDb().query(hidrometroProtecao.getNomeTabela(),
            							 HidrometroProtecao.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaHidrometroProtecao = hidrometroProtecao.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaHidrometroProtecao;
	}

	@Override
	public long inserir(HidrometroProtecao hidrometroProtecao) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = hidrometroProtecao.carregarValues();
       
        try {
          return super.getDb().insert(hidrometroProtecao.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(HidrometroProtecao hidrometroProtecao) throws RepositorioException {
    	ContentValues values =  hidrometroProtecao.carregarValues();

        String _id = String.valueOf(hidrometroProtecao.getId());

        String where = HidrometroProtecaoColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(hidrometroProtecao.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(HidrometroProtecao hidrometroProtecao) throws RepositorioException {
        String _id = String.valueOf(hidrometroProtecao.getId());

        String where = HidrometroProtecaoColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(hidrometroProtecao.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
