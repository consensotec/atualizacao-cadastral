package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.FonteAbastecimento;
import com.br.gsanac.entidades.FonteAbastecimento.FonteAbastecimentos;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Arthur Carvalho
 * @date 06/12/12
 */
public class RepositorioFonteAbastecimento extends RepositorioBase<FonteAbastecimento> {

    private static RepositorioFonteAbastecimento instance;

    public RepositorioFonteAbastecimento() {
        super();
    }

    public static RepositorioFonteAbastecimento getInstance() {
        if (instance == null) {
            instance = new RepositorioFonteAbastecimento();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }


    @Override
    public FonteAbastecimento pesquisar(FonteAbastecimento entity, String selection, String[] selectionArgs) throws RepositorioException {
        
    	Cursor cursor = null;

    	FonteAbastecimento fonteAbastecimento = new FonteAbastecimento();

        if (selection == null || selection.trim().equals("")) {
            selection = FonteAbastecimentos.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(fonteAbastecimento.getNomeTabela(),
                                         FonteAbastecimento.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            fonteAbastecimento = fonteAbastecimento.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return fonteAbastecimento;
    }

    @Override
    public List<FonteAbastecimento> pesquisarLista(String selection, String[] selectionArgs, String orderBy) throws RepositorioException {
        Cursor cursor = null;

        List<FonteAbastecimento> listaFonteAbastecimento = null;
        FonteAbastecimento fonteAbastecimento = new FonteAbastecimento();
        try {
        	listaFonteAbastecimento = new ArrayList<FonteAbastecimento>();

            cursor = super.getDb().query(fonteAbastecimento.getNomeTabela(),
                                         FonteAbastecimento.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaFonteAbastecimento = fonteAbastecimento.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaFonteAbastecimento;
    }

    @Override
    public long inserir(FonteAbastecimento ps) throws RepositorioException {
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
    public void atualizar(FonteAbastecimento ps) throws RepositorioException {
        
    	ContentValues values =  ps.carregarValues();

        String _id = String.valueOf(ps.getId());

        String where = FonteAbastecimentos.ID + "=?";
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
    public void remover(FonteAbastecimento ps) throws RepositorioException {
        String _id = String.valueOf(ps.getId());

        String where = FonteAbastecimentos.ID + "=?";

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
