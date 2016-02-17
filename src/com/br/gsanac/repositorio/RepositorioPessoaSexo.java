package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.PessoaSexo;
import com.br.gsanac.entidades.PessoaSexo.PessoaSexoColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Arthur Carvalho
 * @date 06/12/12
 */
public class RepositorioPessoaSexo extends RepositorioBase<PessoaSexo> {

    private static RepositorioPessoaSexo instance;

    public RepositorioPessoaSexo() {
        super();
    }

    public static RepositorioPessoaSexo getInstance() {
        if (instance == null) {
            instance = new RepositorioPessoaSexo();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }


    @Override
    public PessoaSexo pesquisar(PessoaSexo entity, String selection, String[] selectionArgs) throws RepositorioException {
        
    	Cursor cursor = null;

    	PessoaSexo pessoaSexo = new PessoaSexo();

        if (selection == null || selection.trim().equals("")) {
            selection = PessoaSexoColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(pessoaSexo.getNomeTabela(),
                                         PessoaSexo.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            pessoaSexo = pessoaSexo.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return pessoaSexo;
    }

    @Override
    public List<PessoaSexo> pesquisarLista(String selection, String[] selectionArgs, String orderBy) throws RepositorioException {
        Cursor cursor = null;

        List<PessoaSexo> listaPessoaSexo = null;
        PessoaSexo pessoaSexo = new PessoaSexo();
        try {
        	listaPessoaSexo = new ArrayList<PessoaSexo>();

            cursor = super.getDb().query(pessoaSexo.getNomeTabela(),
                                         PessoaSexo.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaPessoaSexo = pessoaSexo.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaPessoaSexo;
    }

    @Override
    public long inserir(PessoaSexo ps) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = ps.carregarValues();
       
        try {
        	return super.getDb().insert(ps.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
    }

    @Override
    public void atualizar(PessoaSexo ps) throws RepositorioException {
        
    	ContentValues values =  ps.carregarValues();

        String _id = String.valueOf(ps.getId());

        String where = PessoaSexoColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(ps.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
    }

    @Override
    public void remover(PessoaSexo ps) throws RepositorioException {
        String _id = String.valueOf(ps.getId());

        String where = PessoaSexoColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(ps.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
    }

}
