package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.Cep;
import com.br.gsanac.entidades.Cep.Ceps;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Arthur Carvalho
 * @date 06/12/12
 */
public class RepositorioCep extends RepositorioBase<Cep> {

    private static RepositorioCep instance;

    public RepositorioCep() {
        super();
    }

    public static RepositorioCep getInstance() {
        if (instance == null) {
            instance = new RepositorioCep();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }


    @Override
    public Cep pesquisar(Cep entity, String selection, String[] selectionArgs) throws RepositorioException {
        
    	Cursor cursor = null;

    	Cep cep = new Cep();

//        if (selection == null || selection.trim().equals("")) {
//            selection = Ceps.ID + "=?";
//        }
//
//        if (selectionArgs == null) {
//            selectionArgs = new String[] {
//                String.valueOf(entity.getId())
//            };
//        }

        try {
            cursor = super.getDb().query(cep.getNomeTabela(),
                                         Cep.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            cep = cep.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return cep;
    }

    @Override
    public List<Cep> pesquisarLista(String selection, String[] selectionArgs, String orderBy) throws RepositorioException {
        Cursor cursor = null;

        List<Cep> listaCep = null;
        Cep cep = new Cep();
        try {
        	listaCep = new ArrayList<Cep>();

            cursor = super.getDb().query(cep.getNomeTabela(),
                                         Cep.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaCep = cep.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaCep;
    }

    @Override
    public long inserir(Cep ps) throws RepositorioException {
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
    public void atualizar(Cep ps) throws RepositorioException {
        
    	ContentValues values =  ps.carregarValues();

        String _id = String.valueOf(ps.getId());

        String where = Ceps.ID + "=?";
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
    public void remover(Cep ps) throws RepositorioException {
        String _id = String.valueOf(ps.getId());

        String where = Ceps.ID + "=?";

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
