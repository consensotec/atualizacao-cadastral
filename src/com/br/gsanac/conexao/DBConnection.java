package com.br.gsanac.conexao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.br.gsanac.R;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.DBScript;
import com.br.gsanac.util.SQLiteHelper;

/**
 * <p>
 * Classe responsável pelo gerenciamento da conexão com o banco de dados
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/12
 */
public class DBConnection {

    private static final int    DATABASE_VERSION = 1;

    protected SQLiteDatabase    db;

    private SQLiteHelper        dbHelper;

    protected Context           context;

    private static DBConnection instance;

    /**
     * @param context
     */
    public DBConnection(Context context) {
        this.context = context;
        if (db == null || (db != null && !db.isOpen())) {
        	this.openDatabase();
		}
        
    }

    public static DBConnection getInstance(Context context) {
        if (instance == null) {
            instance = new DBConnection(context);
        }
        return instance;
    }

    /**
     * @author Arthur Carvalho
     * @since 06/12/12
     */
    private void openDatabase() {
        try {
            
        	this.closeDatabase();
        	
//        	if (db != null) {
//				if (!db.isOpen()) {
		            DBScript bDScript = new DBScript();
		            dbHelper = new SQLiteHelper(this.context,
		                                        ConstantesSistema.DATABASE_NAME,
		                                        DATABASE_VERSION,
		                                        bDScript.obterScriptBanco(),
		                                        null);
		
		            synchronized (dbHelper){
						this.db = dbHelper.getWritableDatabase();
					}
//				}
//        	}
        } catch (RepositorioException re) {
            Log.e(ConstantesSistema.LOG_TAG, re.getMessage());
        }
    }
    

    /**
     * @author Arthur Carvalho
     * @since 06/12/12
     * @throws RepositorioException
     */
    public void closeDatabase() throws RepositorioException {
        if (this.db != null && !this.db.isOpen()) {
            try {
                this.db.close();
            } catch (SQLException sqle) {
                Log.e(ConstantesSistema.LOG_TAG, sqle.getMessage());
                throw new RepositorioException(context.getResources().getString(R.string.db_error));
            }
        }
        
        if (this.dbHelper != null) {
            try {
            	dbHelper.close();
            } catch (SQLException sqle) {
                Log.e(ConstantesSistema.LOG_TAG, sqle.getMessage());
                throw new RepositorioException(context.getResources().getString(R.string.db_error));
            }        	
        }
    }

    /**
     * @author Arthur Carvalho
     * @since 06/12/12
     * @return SQLiteDatabase
     */
    public static boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(ConstantesSistema.DATABASE_PATH + ConstantesSistema.DATABASE_NAME,
                                                  null,
                                                  SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException sle) {
            Log.e(ConstantesSistema.LOG_TAG, sle.getMessage() + " | " + sle.getCause());
        }
        return (checkDB != null);
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * <p>
     * Remove o banco de dados.
     * </p>
     * 
     * @author Arthur Carvalho
     * @since 06/12/12
     */
    public void deleteDatabase() {

        dbHelper.close();

        db.close();
        if (context.deleteDatabase(ConstantesSistema.DATABASE_NAME)) {
            Log.d(ConstantesSistema.LOG_TAG, "deleteDatabase(): database deleted.");
        } else {
            Log.d(ConstantesSistema.LOG_TAG, "deleteDatabase(): database NOT deleted.");
        }
    }
    
}
