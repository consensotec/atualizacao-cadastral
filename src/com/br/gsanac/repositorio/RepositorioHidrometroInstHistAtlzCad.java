package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad.HidrometroInstHistAtlzCadColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 12/12/12
 */
public class RepositorioHidrometroInstHistAtlzCad extends RepositorioBase<HidrometroInstHistAtlzCad> {

    private static RepositorioHidrometroInstHistAtlzCad instance;

    public RepositorioHidrometroInstHistAtlzCad() {
        super();
    }

    public static RepositorioHidrometroInstHistAtlzCad getInstance() {
        if (instance == null) {
            instance = new RepositorioHidrometroInstHistAtlzCad();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(HidrometroInstHistAtlzCad entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();

        if (selection == null || selection.trim().equals("")) {
            selection = HidrometroInstHistAtlzCadColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(hidrometroInstHistAtlzCad.getNomeTabela(),
            							 HidrometroInstHistAtlzCad.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            hidrometroInstHistAtlzCad = hidrometroInstHistAtlzCad.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return hidrometroInstHistAtlzCad;
	}

	@Override
	public List<HidrometroInstHistAtlzCad> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<HidrometroInstHistAtlzCad> listaHidrometroInstHistAtlzCad = null;
        HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();
        try {
        	listaHidrometroInstHistAtlzCad = new ArrayList<HidrometroInstHistAtlzCad>();

            cursor = super.getDb().query(hidrometroInstHistAtlzCad.getNomeTabela(),
            							 HidrometroInstHistAtlzCad.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaHidrometroInstHistAtlzCad = hidrometroInstHistAtlzCad.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaHidrometroInstHistAtlzCad;
	}

	@Override
	public long inserir(HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = hidrometroInstHistAtlzCad.carregarValues();
       
        try {
           return super.getDb().insert(hidrometroInstHistAtlzCad.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad) throws RepositorioException {
    	ContentValues values =  hidrometroInstHistAtlzCad.carregarValues();

        String _id = String.valueOf(hidrometroInstHistAtlzCad.getId());

        String where = HidrometroInstHistAtlzCadColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(hidrometroInstHistAtlzCad.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad) throws RepositorioException {
        String _id = String.valueOf(hidrometroInstHistAtlzCad.getId());

        String where = HidrometroInstHistAtlzCadColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(hidrometroInstHistAtlzCad.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
	
	public void remover(String idImovelAtlzCadastral) throws RepositorioException {

        String where = HidrometroInstHistAtlzCadColunas.IMOVELATLZCAD_ID + "=?";

        String[] whereArgs = new String[] {
        		idImovelAtlzCadastral
        };
        
        HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();
        
        try {
            super.getDb().delete(hidrometroInstHistAtlzCad.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
		
	}
}
