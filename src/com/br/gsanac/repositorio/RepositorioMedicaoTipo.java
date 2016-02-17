package com.br.gsanac.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.MedicaoTipo;
import com.br.gsanac.entidades.MedicaoTipo.MedicaoTipoColunas;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * @author Anderson Cabral
 * @date 02/01/13
 */
public class RepositorioMedicaoTipo extends RepositorioBase<MedicaoTipo> {

    private static RepositorioMedicaoTipo instance;

    public RepositorioMedicaoTipo() {
        super();
    }

    public static RepositorioMedicaoTipo getInstance() {
        if (instance == null) {
            instance = new RepositorioMedicaoTipo();
        }
        return instance;
    }
    
    public static void removeInstance(){
        instance = null;
    }

	@Override
	public EntidadeBase pesquisar(MedicaoTipo entity, String selection,
			String[] selectionArgs) throws RepositorioException {

    	Cursor cursor = null;

    	MedicaoTipo medicaoTipo = new MedicaoTipo();

        if (selection == null || selection.trim().equals("")) {
            selection = MedicaoTipoColunas.ID + "=?";
        }

        if (selectionArgs == null) {
            selectionArgs = new String[] {
                String.valueOf(entity.getId())
            };
        }

        try {
            cursor = super.getDb().query(medicaoTipo.getNomeTabela(),
            							 MedicaoTipo.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         null,
                                         null);

            medicaoTipo = medicaoTipo.carregarEntidade(cursor);
            
        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return medicaoTipo;
	}

	@Override
	public List<MedicaoTipo> pesquisarLista(String selection,
			String[] selectionArgs, String orderBy) throws RepositorioException {
		
        Cursor cursor = null;

        List<MedicaoTipo> listaMedicaoTipo = null;
        MedicaoTipo medicaoTipo = new MedicaoTipo();
        try {
        	listaMedicaoTipo = new ArrayList<MedicaoTipo>();

            cursor = super.getDb().query(medicaoTipo.getNomeTabela(),
            							 MedicaoTipo.columns,
                                         selection,
                                         selectionArgs,
                                         null,
                                         null,
                                         orderBy,
                                         null);
            
            
            listaMedicaoTipo = medicaoTipo.carregarListaEntidade(cursor); 

        } catch (Exception e) {
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " " + e.getCause());
            e.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_search_record));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return listaMedicaoTipo;
	}

	@Override
	public long inserir(MedicaoTipo medicaoTipo) throws RepositorioException {
        ContentValues values = new ContentValues();

       values = medicaoTipo.carregarValues();
       
        try {
           return super.getDb().insert(medicaoTipo.getNomeTabela(), "", values);

        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error_insert_record));
        }
	}

	@Override
	public void atualizar(MedicaoTipo medicaoTipo) throws RepositorioException {
    	ContentValues values =  medicaoTipo.carregarValues();

        String _id = String.valueOf(medicaoTipo.getId());

        String where = MedicaoTipoColunas.ID + "=?";
        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().update(medicaoTipo.getNomeTabela(), values, where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}

	@Override
	public void remover(MedicaoTipo medicaoTipo) throws RepositorioException {
        String _id = String.valueOf(medicaoTipo.getId());

        String where = MedicaoTipoColunas.ID + "=?";

        String[] whereArgs = new String[] {
            _id
        };

        try {
            super.getDb().delete(medicaoTipo.getNomeTabela(), where, whereArgs);
        } catch (SQLException sqe) {
            Log.e(ConstantesSistema.LOG_TAG, sqe.getMessage() + " " + sqe.getCause());
            sqe.printStackTrace();
            throw new RepositorioException(context.getResources().getString(R.string.db_error));
        }
	}
}
