package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EnderecoReferencia;
import com.br.gsanac.entidades.EnderecoReferencia.EnderecoReferencias;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Arthur Carvalho
 * @date 06/12/12
 */
public class RepositorioEnderecoReferencia extends RepositorioBase<EnderecoReferencia> {

    private static RepositorioEnderecoReferencia instance;

    public RepositorioEnderecoReferencia() {
        super();
    }

    public static RepositorioEnderecoReferencia getInstance() {
        if (instance == null) {
            instance = new RepositorioEnderecoReferencia();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }


    @Override
    public EnderecoReferencia pesquisar(EnderecoReferencia entity, String selection, String[] selectionArgs) throws RepositorioException {
        
    	Cursor cursor = null;

    	EnderecoReferencia enderecoReferencia = new EnderecoReferencia();

        if (selection == null || selection.trim().equals("")) {
            selection = EnderecoReferencias.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(enderecoReferencia.getNomeTabela(),
                                         EnderecoReferencia.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            enderecoReferencia = enderecoReferencia.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return enderecoReferencia;
    }

    @Override
    public List<EnderecoReferencia> pesquisarLista(String selection, String[] selectionArgs, String orderBy) throws RepositorioException {
        Cursor cursor = null;

        List<EnderecoReferencia> listaEnderecoReferencia = null;
        EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
        try {
        	listaEnderecoReferencia = new ArrayList<EnderecoReferencia>();

            cursor = super.getDb().query(enderecoReferencia.getNomeTabela(),
                                         EnderecoReferencia.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaEnderecoReferencia = enderecoReferencia.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaEnderecoReferencia;
    }

    @Override
    public long inserir(EnderecoReferencia ps) throws RepositorioException {
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
    public void atualizar(EnderecoReferencia ps) throws RepositorioException {
        
    	ContentValues values =  ps.carregarValues();

        String _id = String.valueOf(ps.getId());

        String where = EnderecoReferencias.ID + "=?";
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
    public void remover(EnderecoReferencia ps) throws RepositorioException {
        String _id = String.valueOf(ps.getId());

        String where = EnderecoReferencias.ID + "=?";

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
