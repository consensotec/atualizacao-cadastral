package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.LigacaoEsgotoSituacao;
import com.br.gsanac.entidades.LigacaoEsgotoSituacao.LigacaoEsgotoSituacaos;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Arthur Carvalho
 * @date 06/12/12
 */
public class RepositorioLigacaoEsgotoSituacao extends RepositorioBase<LigacaoEsgotoSituacao> {

    private static RepositorioLigacaoEsgotoSituacao instance;

    public RepositorioLigacaoEsgotoSituacao() {
        super();
    }

    public static RepositorioLigacaoEsgotoSituacao getInstance() {
        if (instance == null) {
            instance = new RepositorioLigacaoEsgotoSituacao();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }


    @Override
    public LigacaoEsgotoSituacao pesquisar(LigacaoEsgotoSituacao entity, String selection, String[] selectionArgs) throws RepositorioException {
        
    	Cursor cursor = null;

    	LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();

        if (selection == null || selection.trim().equals("")) {
            selection = LigacaoEsgotoSituacaos.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(ligacaoEsgotoSituacao.getNomeTabela(),
                                         LigacaoEsgotoSituacao.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            ligacaoEsgotoSituacao = ligacaoEsgotoSituacao.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return ligacaoEsgotoSituacao;
    }

    @Override
    public List<LigacaoEsgotoSituacao> pesquisarLista(String selection, String[] selectionArgs, String orderBy) throws RepositorioException {
        Cursor cursor = null;

        List<LigacaoEsgotoSituacao> listaLigacaoEsgotoSituacao = null;
        LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
        try {
        	listaLigacaoEsgotoSituacao = new ArrayList<LigacaoEsgotoSituacao>();

            cursor = super.getDb().query(ligacaoEsgotoSituacao.getNomeTabela(),
                                         LigacaoEsgotoSituacao.colunas,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaLigacaoEsgotoSituacao = ligacaoEsgotoSituacao.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaLigacaoEsgotoSituacao;
    }

    @Override
    public long inserir(LigacaoEsgotoSituacao ps) throws RepositorioException {
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
    public void atualizar(LigacaoEsgotoSituacao ps) throws RepositorioException {
        
    	ContentValues values =  ps.carregarValues();

        String _id = String.valueOf(ps.getId());

        String where = LigacaoEsgotoSituacaos.ID + "=?";
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
    public void remover(LigacaoEsgotoSituacao ps) throws RepositorioException {
        String _id = String.valueOf(ps.getId());

        String where = LigacaoEsgotoSituacaos.ID + "=?";

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
