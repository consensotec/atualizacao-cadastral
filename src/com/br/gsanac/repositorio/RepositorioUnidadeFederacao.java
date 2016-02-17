package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.UnidadeFederacao;
import com.br.gsanac.entidades.UnidadeFederacao.UnidadeFederacaoColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Davi Menezes
 * @date 27/12/12
 */
public class RepositorioUnidadeFederacao extends RepositorioBase<UnidadeFederacao> {

    private static RepositorioUnidadeFederacao instance;

    public RepositorioUnidadeFederacao() {
        super();
    }

    public static RepositorioUnidadeFederacao getInstance() {
        if (instance == null) {
            instance = new RepositorioUnidadeFederacao();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(UnidadeFederacao entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	UnidadeFederacao unidadeFederacao = new UnidadeFederacao();

        if (selection == null || selection.trim().equals("")) {
            selection = UnidadeFederacaoColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(unidadeFederacao.getNomeTabela(),
            							 UnidadeFederacao.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            unidadeFederacao = unidadeFederacao.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return unidadeFederacao;
	}

	@Override
	public List<UnidadeFederacao> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<UnidadeFederacao> listaUnidadeFederacao = null;
        UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
        try {
        	listaUnidadeFederacao = new ArrayList<UnidadeFederacao>();

            cursor = super.getDb().query(unidadeFederacao.getNomeTabela(),
            							 UnidadeFederacao.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaUnidadeFederacao = unidadeFederacao.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaUnidadeFederacao;
	}

	@Override
	public long inserir(UnidadeFederacao unidadeFederacao) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = unidadeFederacao.carregarValues();
       
        try {
           return super.getDb().insert(unidadeFederacao.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(UnidadeFederacao unidadeFederacao) throws RepositorioException {
    	ContentValues values =  unidadeFederacao.carregarValues();

        String _id = String.valueOf(unidadeFederacao.getId());

        String where = UnidadeFederacaoColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(unidadeFederacao.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(UnidadeFederacao unidadeFederacao) throws RepositorioException {
        String _id = String.valueOf(unidadeFederacao.getId());

        String where = UnidadeFederacaoColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(unidadeFederacao.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
