package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.PavimentoCalcada;
import com.br.gsanac.entidades.PavimentoCalcada.PavimentoCalcadas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Arthur Carvalho
 * @date 06/12/12
 */
public class RepositorioPavimentoCalcada extends RepositorioBase<PavimentoCalcada> {

    private static RepositorioPavimentoCalcada instance;

    public RepositorioPavimentoCalcada() {
        super();
    }

    public static RepositorioPavimentoCalcada getInstance() {
        if (instance == null) {
            instance = new RepositorioPavimentoCalcada();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }


    @Override
    public PavimentoCalcada pesquisar(PavimentoCalcada entity, String selection, String[] selectionArgs) throws RepositorioException {
        
    	Cursor cursor = null;

    	PavimentoCalcada pavimentoCalcada = new PavimentoCalcada();

        if (selection == null || selection.trim().equals("")) {
            selection = PavimentoCalcadas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(pavimentoCalcada.getNomeTabela(),
                                         PavimentoCalcada.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            pavimentoCalcada = pavimentoCalcada.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return pavimentoCalcada;
    }

    @Override
    public List<PavimentoCalcada> pesquisarLista(String selection, String[] selectionArgs, String orderBy) throws RepositorioException {
        Cursor cursor = null;

        List<PavimentoCalcada> listaPavimentoCalcada = null;
        PavimentoCalcada pavimentoCalcada = new PavimentoCalcada();
        try {
        	listaPavimentoCalcada = new ArrayList<PavimentoCalcada>();

            cursor = super.getDb().query(pavimentoCalcada.getNomeTabela(),
                                         PavimentoCalcada.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaPavimentoCalcada = pavimentoCalcada.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaPavimentoCalcada;
    }

    @Override
    public long inserir(PavimentoCalcada ps) throws RepositorioException {
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
    public void atualizar(PavimentoCalcada ps) throws RepositorioException {
        
    	ContentValues values =  ps.carregarValues();

        String _id = String.valueOf(ps.getId());

        String where = PavimentoCalcadas.ID + "=?";
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
    public void remover(PavimentoCalcada ps) throws RepositorioException {
        String _id = String.valueOf(ps.getId());

        String where = PavimentoCalcadas.ID + "=?";

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
