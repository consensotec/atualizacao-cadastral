package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.ClienteTipo;
import com.br.gsanac.entidades.ClienteTipo.ClienteTipoColunas;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Davi Menezes
 * @date 28/12/12
 */
public class RepositorioClienteTipo extends RepositorioBase<ClienteTipo> {

    private static RepositorioClienteTipo instance;

    public RepositorioClienteTipo() {
        super();
    }

    public static RepositorioClienteTipo getInstance() {
        if (instance == null) {
            instance = new RepositorioClienteTipo();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(ClienteTipo entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	ClienteTipo clienteTipo = new ClienteTipo();

        if (selection == null || selection.trim().equals("")) {
            selection = ClienteTipoColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(clienteTipo.getNomeTabela(),
            							 ClienteTipo.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            clienteTipo = clienteTipo.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return clienteTipo;
	}

	@Override
	public List<ClienteTipo> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<ClienteTipo> listaClienteTipo = null;
        ClienteTipo clienteTipo = new ClienteTipo();
        try {
        	listaClienteTipo = new ArrayList<ClienteTipo>();

            cursor = super.getDb().query(clienteTipo.getNomeTabela(),
            							 ClienteTipo.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaClienteTipo = clienteTipo.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaClienteTipo;
	}
	
	@Override
	public long inserir(ClienteTipo clienteTipo) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = clienteTipo.carregarValues();
       
        try {
           return super.getDb().insert(clienteTipo.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(ClienteTipo clienteTipo) throws RepositorioException {
    	ContentValues values =  clienteTipo.carregarValues();

        String _id = String.valueOf(clienteTipo.getId());

        String where = ClienteTipoColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(clienteTipo.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(ClienteTipo clienteTipo) throws RepositorioException {
        String _id = String.valueOf(clienteTipo.getId());

        String where = ClienteTipoColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(clienteTipo.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
