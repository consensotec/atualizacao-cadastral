package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad.ImovelSubCategAtlzCadColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 10/12/12
 */
public class RepositorioImovelSubCategAtlzCad extends RepositorioBase<ImovelSubCategAtlzCad> {

    private static RepositorioImovelSubCategAtlzCad instance;

    public RepositorioImovelSubCategAtlzCad() {
        super();
    }

    public static RepositorioImovelSubCategAtlzCad getInstance() {
        if (instance == null) {
            instance = new RepositorioImovelSubCategAtlzCad();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(ImovelSubCategAtlzCad entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	ImovelSubCategAtlzCad imovelSubCategAtlzCad = new ImovelSubCategAtlzCad();

        if (selection == null || selection.trim().equals("")) {
            selection = ImovelSubCategAtlzCadColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(imovelSubCategAtlzCad.getNomeTabela(),
            							 ImovelSubCategAtlzCad.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            imovelSubCategAtlzCad = imovelSubCategAtlzCad.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return imovelSubCategAtlzCad;
	}

	@Override
	public List<ImovelSubCategAtlzCad> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<ImovelSubCategAtlzCad> listaImovelSubCategAtlzCad = null;
        ImovelSubCategAtlzCad imovelSubCategAtlzCad = new ImovelSubCategAtlzCad();
        try {
        	listaImovelSubCategAtlzCad = new ArrayList<ImovelSubCategAtlzCad>();

            cursor = super.getDb().query(imovelSubCategAtlzCad.getNomeTabela(),
            							 ImovelSubCategAtlzCad.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaImovelSubCategAtlzCad = imovelSubCategAtlzCad.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaImovelSubCategAtlzCad;
	}

	@Override
	public long inserir(ImovelSubCategAtlzCad imovelSubCategAtlzCad) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = imovelSubCategAtlzCad.carregarValues();
       
        try {
        	return super.getDb().insert(imovelSubCategAtlzCad.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(ImovelSubCategAtlzCad imovelSubCategAtlzCad) throws RepositorioException {
    	ContentValues values =  imovelSubCategAtlzCad.carregarValues();

        String _id = String.valueOf(imovelSubCategAtlzCad.getId());

        String where = ImovelSubCategAtlzCadColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(imovelSubCategAtlzCad.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(ImovelSubCategAtlzCad imovelSubCategAtlzCad) throws RepositorioException {
        String _id = String.valueOf(imovelSubCategAtlzCad.getId());

        String where = ImovelSubCategAtlzCadColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(imovelSubCategAtlzCad.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
	
	public void remover(String idImovelAtlzCadastral) throws RepositorioException {
		
		ImovelSubCategAtlzCad imovelSubCategAtlzCad = new ImovelSubCategAtlzCad();
        String where = ImovelSubCategAtlzCadColunas.IMOVELATLZCAD_ID + "=?";

        String[] whereArgs = new String[] {
        		idImovelAtlzCadastral
        };

        try {
            super.getDb().delete(imovelSubCategAtlzCad.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
		
	}
}
