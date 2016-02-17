package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.ImovelOcorrencia;
import com.br.gsanac.entidades.ImovelOcorrencia.ImovelOcorrenciaColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 08/01/2013
 */
public class RepositorioImovelOcorrencia extends RepositorioBase<ImovelOcorrencia> {

    private static RepositorioImovelOcorrencia instance;

    public RepositorioImovelOcorrencia() {
        super();
    }

    public static RepositorioImovelOcorrencia getInstance() {
        if (instance == null) {
            instance = new RepositorioImovelOcorrencia();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(ImovelOcorrencia entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	ImovelOcorrencia imovelOcorrencia = new ImovelOcorrencia();

        if (selection == null || selection.trim().equals("")) {
            selection = ImovelOcorrenciaColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(imovelOcorrencia.getNomeTabela(),
            							 ImovelOcorrencia.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            imovelOcorrencia = imovelOcorrencia.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return imovelOcorrencia;
	}

	@Override
	public List<ImovelOcorrencia> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<ImovelOcorrencia> listaImovelOcorrencia = null;
        ImovelOcorrencia imovelOcorrencia = new ImovelOcorrencia();
        try {
        	listaImovelOcorrencia = new ArrayList<ImovelOcorrencia>();

            cursor = super.getDb().query(imovelOcorrencia.getNomeTabela(),
            							 ImovelOcorrencia.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaImovelOcorrencia = imovelOcorrencia.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaImovelOcorrencia;
	}

	@Override
	public long inserir(ImovelOcorrencia imovelOcorrencia) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = imovelOcorrencia.carregarValues();
       
        try {
           return super.getDb().insert(imovelOcorrencia.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(ImovelOcorrencia imovelOcorrencia) throws RepositorioException {
    	ContentValues values =  imovelOcorrencia.carregarValues();

        String _id = String.valueOf(imovelOcorrencia.getId());

        String where = ImovelOcorrenciaColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(imovelOcorrencia.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(ImovelOcorrencia imovelOcorrencia) throws RepositorioException {
        String _id = String.valueOf(imovelOcorrencia.getId());

        String where = ImovelOcorrenciaColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(imovelOcorrencia.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
