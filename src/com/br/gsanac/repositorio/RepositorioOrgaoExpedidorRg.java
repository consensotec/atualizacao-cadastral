package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.OrgaoExpedidorRg;
import com.br.gsanac.entidades.OrgaoExpedidorRg.OrgaoExpedidorRgColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 10/12/12
 */
public class RepositorioOrgaoExpedidorRg extends RepositorioBase<OrgaoExpedidorRg> {

    private static RepositorioOrgaoExpedidorRg instance;

    public RepositorioOrgaoExpedidorRg() {
        super();
    }

    public static RepositorioOrgaoExpedidorRg getInstance() {
        if (instance == null) {
            instance = new RepositorioOrgaoExpedidorRg();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(OrgaoExpedidorRg entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();

        if (selection == null || selection.trim().equals("")) {
            selection = OrgaoExpedidorRgColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(orgaoExpedidorRg.getNomeTabela(),
            							 OrgaoExpedidorRg.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            orgaoExpedidorRg = orgaoExpedidorRg.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return orgaoExpedidorRg;
	}

	@Override
	public List<OrgaoExpedidorRg> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<OrgaoExpedidorRg> listaOrgaoExpedidorRg = null;
        OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
        try {
        	listaOrgaoExpedidorRg = new ArrayList<OrgaoExpedidorRg>();

            cursor = super.getDb().query(orgaoExpedidorRg.getNomeTabela(),
            							 OrgaoExpedidorRg.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaOrgaoExpedidorRg = orgaoExpedidorRg.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaOrgaoExpedidorRg;
	}

	@Override
	public long inserir(OrgaoExpedidorRg orgaoExpedidorRg) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = orgaoExpedidorRg.carregarValues();
       
        try {
        	return super.getDb().insert(orgaoExpedidorRg.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(OrgaoExpedidorRg orgaoExpedidorRg) throws RepositorioException {
    	ContentValues values =  orgaoExpedidorRg.carregarValues();

        String _id = String.valueOf(orgaoExpedidorRg.getId());

        String where = OrgaoExpedidorRgColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(orgaoExpedidorRg.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(OrgaoExpedidorRg orgaoExpedidorRg) throws RepositorioException {
        String _id = String.valueOf(orgaoExpedidorRg.getId());

        String where = OrgaoExpedidorRgColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(orgaoExpedidorRg.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
