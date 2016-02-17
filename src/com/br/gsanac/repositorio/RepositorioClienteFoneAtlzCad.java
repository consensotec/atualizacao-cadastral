package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.ClienteFoneAtlzCad;
import com.br.gsanac.entidades.ClienteFoneAtlzCad.ClienteFoneAtlzCadColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 10/12/12
 */
public class RepositorioClienteFoneAtlzCad extends RepositorioBase<ClienteFoneAtlzCad> {

    private static RepositorioClienteFoneAtlzCad instance;

    public RepositorioClienteFoneAtlzCad() {
        super();
    }

    public static RepositorioClienteFoneAtlzCad getInstance() {
        if (instance == null) {
            instance = new RepositorioClienteFoneAtlzCad();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(ClienteFoneAtlzCad entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	ClienteFoneAtlzCad clienteFoneAtlzCad = new ClienteFoneAtlzCad();

        if (selection == null || selection.trim().equals("")) {
            selection = ClienteFoneAtlzCadColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(clienteFoneAtlzCad.getNomeTabela(),
            							 ClienteFoneAtlzCad.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            clienteFoneAtlzCad = clienteFoneAtlzCad.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return clienteFoneAtlzCad;
	}

	@Override
	public List<ClienteFoneAtlzCad> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<ClienteFoneAtlzCad> listaClienteFoneAtlzCad = null;
        ClienteFoneAtlzCad clienteFoneAtlzCad = new ClienteFoneAtlzCad();
        try {
        	listaClienteFoneAtlzCad = new ArrayList<ClienteFoneAtlzCad>();

            cursor = super.getDb().query(clienteFoneAtlzCad.getNomeTabela(),
            							 ClienteFoneAtlzCad.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaClienteFoneAtlzCad = clienteFoneAtlzCad.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaClienteFoneAtlzCad;
	}

	@Override
	public long inserir(ClienteFoneAtlzCad clienteFoneAtlzCad) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = clienteFoneAtlzCad.carregarValues();
       
        try {
           return super.getDb().insert(clienteFoneAtlzCad.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }		
	}

	@Override
	public void atualizar(ClienteFoneAtlzCad clienteFoneAtlzCad) throws RepositorioException {
    	ContentValues values =  clienteFoneAtlzCad.carregarValues();

        String _id = String.valueOf(clienteFoneAtlzCad.getId());

        String where = ClienteFoneAtlzCadColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(clienteFoneAtlzCad.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }		
	}

	@Override
	public void remover(ClienteFoneAtlzCad clienteFoneAtlzCad) throws RepositorioException {
        String _id = String.valueOf(clienteFoneAtlzCad.getId());

        String where = ClienteFoneAtlzCadColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(clienteFoneAtlzCad.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }		
	}
	
	
	public void remover(String idClienteAtlzCadastral) throws RepositorioException {

		ClienteFoneAtlzCad clienteFoneAtlzCad = new ClienteFoneAtlzCad();
		
        String where = ClienteFoneAtlzCadColunas.CLIENTEATLZCAD_ID + "=?";

        String[] whereArgs = new String[] {
        	idClienteAtlzCadastral
        };

        try {
            super.getDb().delete(clienteFoneAtlzCad.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
		
	}
}
