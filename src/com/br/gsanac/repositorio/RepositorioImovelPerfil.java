package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.ImovelPerfil;
import com.br.gsanac.entidades.ImovelPerfil.ImovelPerfilColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 12/12/12
 */
public class RepositorioImovelPerfil extends RepositorioBase<ImovelPerfil> {

    private static RepositorioImovelPerfil instance;

    public RepositorioImovelPerfil() {
        super();
    }

    public static RepositorioImovelPerfil getInstance() {
        if (instance == null) {
            instance = new RepositorioImovelPerfil();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(ImovelPerfil entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	ImovelPerfil imovelPerfil = new ImovelPerfil();

        if (selection == null || selection.trim().equals("")) {
            selection = ImovelPerfilColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(imovelPerfil.getNomeTabela(),
            							 ImovelPerfil.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            imovelPerfil = imovelPerfil.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return imovelPerfil;
	}

	@Override
	public List<ImovelPerfil> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<ImovelPerfil> listaImovelPerfil = null;
        ImovelPerfil imovelPerfil = new ImovelPerfil();
        try {
        	listaImovelPerfil = new ArrayList<ImovelPerfil>();

            cursor = super.getDb().query(imovelPerfil.getNomeTabela(),
            							 ImovelPerfil.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaImovelPerfil = imovelPerfil.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaImovelPerfil;
	}

	@Override
	public long inserir(ImovelPerfil imovelPerfil) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = imovelPerfil.carregarValues();
       
        try {
           return super.getDb().insert(imovelPerfil.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(ImovelPerfil imovelPerfil) throws RepositorioException {
    	ContentValues values =  imovelPerfil.carregarValues();

        String _id = String.valueOf(imovelPerfil.getId());

        String where = ImovelPerfilColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(imovelPerfil.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(ImovelPerfil imovelPerfil) throws RepositorioException {
        String _id = String.valueOf(imovelPerfil.getId());

        String where = ImovelPerfilColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(imovelPerfil.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
