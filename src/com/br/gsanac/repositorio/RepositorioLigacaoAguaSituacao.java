package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.LigacaoAguaSituacao;
import com.br.gsanac.entidades.LigacaoAguaSituacao.LigacaoAguaSituacaos;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Arthur Carvalho
 * @date 06/12/12
 */
public class RepositorioLigacaoAguaSituacao extends RepositorioBase<LigacaoAguaSituacao> {

    private static RepositorioLigacaoAguaSituacao instance;

    public RepositorioLigacaoAguaSituacao() {
        super();
    }

    public static RepositorioLigacaoAguaSituacao getInstance() {
        if (instance == null) {
            instance = new RepositorioLigacaoAguaSituacao();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }


    @Override
    public LigacaoAguaSituacao pesquisar(LigacaoAguaSituacao entity, String selection, String[] selectionArgs) throws RepositorioException {
        
    	Cursor cursor = null;

    	LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();

        if (selection == null || selection.trim().equals("")) {
            selection = LigacaoAguaSituacaos.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(ligacaoAguaSituacao.getNomeTabela(),
                                         LigacaoAguaSituacao.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            ligacaoAguaSituacao = ligacaoAguaSituacao.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return ligacaoAguaSituacao;
    }

    @Override
    public List<LigacaoAguaSituacao> pesquisarLista(String selection, String[] selectionArgs, String orderBy) throws RepositorioException {
        Cursor cursor = null;

        List<LigacaoAguaSituacao> listaLigacaoAguaSituacao = null;
        LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
        try {
        	listaLigacaoAguaSituacao = new ArrayList<LigacaoAguaSituacao>();

            cursor = super.getDb().query(ligacaoAguaSituacao.getNomeTabela(),
                                         LigacaoAguaSituacao.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaLigacaoAguaSituacao = ligacaoAguaSituacao.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaLigacaoAguaSituacao;
    }

    @Override
    public long inserir(LigacaoAguaSituacao ps) throws RepositorioException {
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
    public void atualizar(LigacaoAguaSituacao ps) throws RepositorioException {
        
    	ContentValues values =  ps.carregarValues();

        String _id = String.valueOf(ps.getId());

        String where = LigacaoAguaSituacaos.ID + "=?";
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
    public void remover(LigacaoAguaSituacao ps) throws RepositorioException {
        String _id = String.valueOf(ps.getId());

        String where = LigacaoAguaSituacaos.ID + "=?";

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
