package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.ClienteAtlzCadastral;
import com.br.gsanac.entidades.ClienteAtlzCadastral.ClienteAtlzCadastralColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 12/12/12
 */
public class RepositorioClienteAtlzCadastral extends RepositorioBase<ClienteAtlzCadastral> {

    private static RepositorioClienteAtlzCadastral instance;

    public RepositorioClienteAtlzCadastral() {
        super();
    }

    public static RepositorioClienteAtlzCadastral getInstance() {
        if (instance == null) {
            instance = new RepositorioClienteAtlzCadastral();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(ClienteAtlzCadastral entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();

//        if (selection == null || selection.trim().equals("")) {
//            selection = ClienteAtlzCadastralColunas.ID + "=?";
//        }
//
//        if (selectionArgs == null) {
//            selectionArgs = new String[] {
//                String.valueOf(entity.getId())
//            };
//        }

        try {
            cursor = super.getDb().query(clienteAtlzCadastral.getNomeTabela(),
            							 ClienteAtlzCadastral.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            clienteAtlzCadastral = clienteAtlzCadastral.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return clienteAtlzCadastral;
	}

	@Override
	public List<ClienteAtlzCadastral> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<ClienteAtlzCadastral> listaClienteAtlzCadastral = null;
        ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();
        try {
        	listaClienteAtlzCadastral = new ArrayList<ClienteAtlzCadastral>();

            cursor = super.getDb().query(clienteAtlzCadastral.getNomeTabela(),
            							 ClienteAtlzCadastral.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaClienteAtlzCadastral = clienteAtlzCadastral.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaClienteAtlzCadastral;
	}

	@Override
	public long inserir(ClienteAtlzCadastral clienteAtlzCadastral) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = clienteAtlzCadastral.carregarValues();
       
        try {
            return super.getDb().insert(clienteAtlzCadastral.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
		
	}

	@Override
	public void atualizar(ClienteAtlzCadastral clienteAtlzCadastral) throws RepositorioException {
    	ContentValues values =  clienteAtlzCadastral.carregarValues();

        String _id = String.valueOf(clienteAtlzCadastral.getId());

        String where = ClienteAtlzCadastralColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(clienteAtlzCadastral.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
		
	}

	@Override
	public void remover(ClienteAtlzCadastral clienteAtlzCadastral) throws RepositorioException {
        String _id = String.valueOf(clienteAtlzCadastral.getId());

        String where = ClienteAtlzCadastralColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(clienteAtlzCadastral.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }		
	}
	
	
	public void remover(String idImovelAtlzCadastral) throws RepositorioException {

        String where = ClienteAtlzCadastralColunas.IMOVELATLZCAD_ID + "=?";

        String[] whereArgs = new String[] {
        		idImovelAtlzCadastral
        };
        
        ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();

        try {
            super.getDb().delete(clienteAtlzCadastral.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
		
	}
}
