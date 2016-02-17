package com.br.gsanac.repositorio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.br.gsanac.conexao.DBConnection;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.Logradouro.Logradouros;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.util.ConstantesSistema;

/**
 * 
 * @author Arthur Carvalho
 * @date 06/12/2012
 * @param <T>
 */
public abstract class RepositorioBase<T extends EntidadeBase> implements IRepositorioBase<T> {

    protected static Context context;

    private SQLiteDatabase   db;

    public RepositorioBase() {
//    	criarConexao();
    }
    
    public void criarConexao(){
        DBConnection dbConnection = new DBConnection(context);
        this.db = dbConnection.getDb();
    }

    public static void setContext(Context c) {
        context = c;
    }
    
    public static Context getContext() {
        return context;
    }

    public SQLiteDatabase getDb() {
    	if(db == null || !db.isOpen()){
    		criarConexao();
    	}
    	
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public Cursor getCursor(String idField, String descriptionField, String tablename) throws RepositorioException {

        String sql = "SELECT " + idField + " AS _id, " + descriptionField + " AS " + ConstantesSistema.COLUMN_DESCRIPTION_ALIAS
                + " FROM " + tablename + " ORDER BY " + descriptionField;

        return getDb().rawQuery(sql, null);
    }
    
    @Override
    public Cursor getCursor(String idField, String descriptionField, String tablename, String where) throws RepositorioException {

        String sql = "SELECT " + idField + " AS _id, " + descriptionField + " AS " + ConstantesSistema.COLUMN_DESCRIPTION_ALIAS
                + " FROM " + tablename;
        
        if(where != null){
        	sql += " WHERE " + where + " ORDER BY " + descriptionField;
        }else{
        	sql += " ORDER BY " + descriptionField;
        }

        return getDb().rawQuery(sql, null);
    }
    
    @Override
    public Cursor getCursorOrderBy(String idField, String descriptionField, String tablename, String orderBy) throws RepositorioException {

        String sql = "SELECT " + idField + " AS _id, " + descriptionField + " AS " + ConstantesSistema.COLUMN_DESCRIPTION_ALIAS
                + " FROM " + tablename + " ORDER BY " + orderBy;

        return getDb().rawQuery(sql, null);
    }
    
    @Override
    public Cursor getCursorOrderBy(String idField, String descriptionField, String tablename, String where, String orderBy) throws RepositorioException {

        String sql = "SELECT " + idField + " AS _id, " + descriptionField + " AS " + ConstantesSistema.COLUMN_DESCRIPTION_ALIAS
                + " FROM " + tablename;
        
        if(where != null){
        	sql += " WHERE " + where;
        }

        if(orderBy != null){
        	sql += " ORDER BY " + orderBy;
        }

        return getDb().rawQuery(sql, null);
    }
    
    @Override
    public Cursor getCursorLogradouro(String where) throws RepositorioException {

    	String sql = "SELECT LOGR_ID AS _id, LGTP_DSLOGRADOUROTIPO AS "+ ConstantesSistema.COLUMN_TIPO_ALIAS + ", " + "LGTT_DSLOGRADOUROTITULO AS "+ ConstantesSistema.COLUMN_TITULO_ALIAS + ", " +
    			" LOGR_NMLOGRADOURO AS "+ ConstantesSistema.COLUMN_LOGRADOURO_ALIAS + ", " +
    			" LGTP_DSLOGRADOUROTIPO || ' ' || CASE WHEN (LGTT_DSLOGRADOUROTITULO is not null) THEN LGTT_DSLOGRADOUROTITULO || ' ' ELSE '' END || LOGR_NMLOGRADOURO  as  " + ConstantesSistema.COLUMN_DESC_FORMATADA_ALIAS + 
    			" FROM  LOGRADOURO logra " + 
    			" INNER join LOGRADOURO_TIPO tipo on logra.LGTP_ID = tipo.LGTP_ID " +
    			" LEFT join LOGRADOURO_TITULO titulo on titulo.LGTT_ID = logra.LGTT_ID " ;
    			
				if ( where != null && !where.equals("") ) {
				sql += where;	
				}
    			
				sql += " ORDER BY  LGTP_DSLOGRADOUROTIPO , LGTT_DSLOGRADOUROTITULO , LOGR_NMLOGRADOURO" ;

        return getDb().rawQuery(sql, null);
    }
    
    /**
     * @author Flavio Ferreira
     * @since 27/12/2013
     */
    public Cursor getCursorListaLogradouro() throws RepositorioException{
    
    	String sql = " select * from logradouro logra "
    				+" inner join imovel_atlz_cadastral imov on imov.logr_id = logra.logr_id"
    				+" where logra.logr_icnovo = "      + ConstantesSistema.SIM 
    				+" and logra.logr_ictransmitido = " + ConstantesSistema.NAO;
    	
    	return getDb().rawQuery(sql, null);
    				
    }
    
    /**
     * @author Flavio Ferreira
     * @since 27/12/2013
     */
    
    public Cursor getCursorListaLogradouroCep() throws RepositorioException{
    	
    	String sql = " select * from logradouro_cep logracep"
    				+" inner join imovel_atlz_cadastral imov on imov.lgcp_id = logracep.lgcp_id "
    				+" where logracep.lgcp_icnovo = "     + ConstantesSistema.SIM
    				+" and logracep.lgcp_ictransmitido = " + ConstantesSistema.NAO;
    	
    	return getDb().rawQuery(sql, null);
    }

    /**
     * @author Arthur Carvalho
     * @since 06/12/2012
     */
    public void closeDb() {
        if (this.db != null) {
            this.db.close();
        }
    }
}