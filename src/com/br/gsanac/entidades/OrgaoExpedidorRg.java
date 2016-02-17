package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto OrgaoExpedidorRg
 * </p>
 * 
 * @author Anderson Cabral
 * @since  10/12/2012
 */
public class OrgaoExpedidorRg extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int OERG_ID_INDEX         		   = 1;
    private static final int OERG_DSORGAOEXPEDIDORRG_INDEX = 2;
    private static final int OERG_DSABREVIADO_INDEX 	   = 3;

    private String descricao;
    private String descricaoAbreviada;

	public static final String[] columns = new String[]{
    	OrgaoExpedidorRgColunas.ID,
    	OrgaoExpedidorRgColunas.DESCRICAO,
    	OrgaoExpedidorRgColunas.DESCRICAOABREV
    };
    
    public static final class OrgaoExpedidorRgColunas implements BaseColumns{
    	public static final String ID 		 	  = "OERG_ID";
    	public static final String DESCRICAO 	  = "OERG_DSORGAOEXPEDIDORRG";
    	public static final String DESCRICAOABREV = "OERG_DSABREVIADO";
    }
	
    public final class OrgaoExpedidorRgColunasTipo {
		public final String ID 		  	   = " INTEGER PRIMARY KEY";
		public final String DESCRICAO 	   = " VARCHAR(50) NOT NULL";
		public final String DESCRICAOABREV = " VARCHAR(6) ";
		
		private String[] tipos = new String[] {ID, DESCRICAO, DESCRICAOABREV};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static OrgaoExpedidorRg inserirDoArquivo(List<String> c) {
    	
    	OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();

    	orgaoExpedidorRg.setId(c.get(OERG_ID_INDEX));
    	orgaoExpedidorRg.setDescricao(c.get(OERG_DSORGAOEXPEDIDORRG_INDEX));
    	orgaoExpedidorRg.setDescricaoAbreviada(c.get(OERG_DSABREVIADO_INDEX));
    	
        return orgaoExpedidorRg;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(OrgaoExpedidorRgColunas.ID, getId());
		values.put(OrgaoExpedidorRgColunas.DESCRICAO, getDescricao());
		values.put(OrgaoExpedidorRgColunas.DESCRICAOABREV, getDescricaoAbreviada());

		return values;
	}
    
	public ArrayList<OrgaoExpedidorRg> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<OrgaoExpedidorRg> listaOrgaoExpedidorRg = new ArrayList<OrgaoExpedidorRg>();
		cursor.moveToFirst();
		
		do{
			int codigo 	  		   = cursor.getColumnIndex(OrgaoExpedidorRgColunas.ID);
			int descricao 		   = cursor.getColumnIndex(OrgaoExpedidorRgColunas.DESCRICAO);
			int descricaoAbreviada = cursor.getColumnIndex(OrgaoExpedidorRgColunas.DESCRICAOABREV);
			
			OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
			
			orgaoExpedidorRg.setId(cursor.getInt(codigo));
			orgaoExpedidorRg.setDescricao(cursor.getString(descricao));
			orgaoExpedidorRg.setDescricaoAbreviada(cursor.getString(descricaoAbreviada));
			
			listaOrgaoExpedidorRg.add(orgaoExpedidorRg);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return listaOrgaoExpedidorRg;
	}
	
	public OrgaoExpedidorRg carregarEntidade(Cursor cursor) {		
		
		int codigo 	  		   = cursor.getColumnIndex(OrgaoExpedidorRgColunas.ID);
		int descricao 		   = cursor.getColumnIndex(OrgaoExpedidorRgColunas.DESCRICAO);
		int descricaoAbreviada = cursor.getColumnIndex(OrgaoExpedidorRgColunas.DESCRICAOABREV);
		
		OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();

		if ( cursor.moveToFirst() ) {
			
			orgaoExpedidorRg.setId(cursor.getInt(codigo));
			orgaoExpedidorRg.setDescricao(cursor.getString(descricao));
			orgaoExpedidorRg.setDescricaoAbreviada(cursor.getString(descricaoAbreviada));
		}

		cursor.close();
		return orgaoExpedidorRg;
	}
    
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
    public String getDescricaoAbreviada() {
		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada) {
		this.descricaoAbreviada = descricaoAbreviada;
	}
	
    public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "ORGAO_EXPEDIDOR_RG";
	}
}
