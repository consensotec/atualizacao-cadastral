package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.CadastroOcorrencia;
import com.br.gsanac.entidades.CadastroOcorrencia.CadastroOcorrenciaColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 13/12/12
 */
public class RepositorioCadastroOcorrencia extends RepositorioBase<CadastroOcorrencia> {

    private static RepositorioCadastroOcorrencia instance;

    public RepositorioCadastroOcorrencia() {
        super();
    }

    public static RepositorioCadastroOcorrencia getInstance() {
        if (instance == null) {
            instance = new RepositorioCadastroOcorrencia();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(CadastroOcorrencia entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();

        if (selection == null || selection.trim().equals("")) {
            selection = CadastroOcorrenciaColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(cadastroOcorrencia.getNomeTabela(),
            							 CadastroOcorrencia.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            cadastroOcorrencia = cadastroOcorrencia.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }

        return cadastroOcorrencia;
	}

	@Override
	public List<CadastroOcorrencia> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<CadastroOcorrencia> listaCadastroOcorrencia = null;
        CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();
        try {
        	listaCadastroOcorrencia = new ArrayList<CadastroOcorrencia>();

            cursor = super.getDb().query(cadastroOcorrencia.getNomeTabela(),
            							 CadastroOcorrencia.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaCadastroOcorrencia = cadastroOcorrencia.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }

        }

        return listaCadastroOcorrencia;
	}

	@Override
	public long inserir(CadastroOcorrencia cadastroOcorrencia) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = cadastroOcorrencia.carregarValues();
       
        try {
           return super.getDb().insert(cadastroOcorrencia.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
		
	}

	@Override
	public void atualizar(CadastroOcorrencia cadastroOcorrencia) throws RepositorioException {
    	ContentValues values =  cadastroOcorrencia.carregarValues();

        String _id = String.valueOf(cadastroOcorrencia.getId());

        String where = CadastroOcorrenciaColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(cadastroOcorrencia.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
		
	}

	@Override
	public void remover(CadastroOcorrencia cadastroOcorrencia) throws RepositorioException {
        String _id = String.valueOf(cadastroOcorrencia.getId());

        String where = CadastroOcorrenciaColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(cadastroOcorrencia.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
		
	}
}
