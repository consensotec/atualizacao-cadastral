package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto ImovelPerfil
 * </p>
 * 
 * @author Anderson Cabral
 * @since  12/12/2012
 */
public class ImovelPerfil extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
	public static final long NORMAL = 5;
	
    private static final int IPER_ID_INDEX         	   = 1;
    private static final int IPER_DSIMOVELPERFIL_INDEX = 2;

    private String descricao;
    
    public static final String[] columns = new String[]{
    	ImovelPerfilColunas.ID,
    	ImovelPerfilColunas.DESCRICAO
    };
    
    public static final class ImovelPerfilColunas implements BaseColumns{
    	public static final String ID 		 = "IPER_ID";
    	public static final String DESCRICAO = "IPER_DSIMOVELPERFIL";
    }
	
    public final class ImovelPerfilColunasTipo {
		public final String ID 		  = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(30) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static ImovelPerfil inserirDoArquivo(List<String> c) {
    	
    	ImovelPerfil imovelPerfil = new ImovelPerfil();

    	imovelPerfil.setId(c.get(IPER_ID_INDEX));
    	imovelPerfil.setDescricao(c.get(IPER_DSIMOVELPERFIL_INDEX));
        
        return imovelPerfil;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(ImovelPerfilColunas.ID, getId());
		values.put(ImovelPerfilColunas.DESCRICAO, getDescricao());

		return values;
	}
    
	public ArrayList<ImovelPerfil> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<ImovelPerfil> listaImovelPerfil = new ArrayList<ImovelPerfil>();
		if ( cursor.moveToFirst() ) {
		do{
			int codigo 	  = cursor.getColumnIndex(ImovelPerfilColunas.ID);
			int descricao = cursor.getColumnIndex(ImovelPerfilColunas.DESCRICAO);
			
			ImovelPerfil imovelPerfil = new ImovelPerfil();

			imovelPerfil.setId(cursor.getInt(codigo));
			imovelPerfil.setDescricao(cursor.getString(descricao));
			
			listaImovelPerfil.add(imovelPerfil);
			
		} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaImovelPerfil;
	}
	
	public ImovelPerfil carregarEntidade(Cursor cursor) {		
		
		int codigo 	  = cursor.getColumnIndex(ImovelPerfilColunas.ID);
		int descricao = cursor.getColumnIndex(ImovelPerfilColunas.DESCRICAO);
		
		ImovelPerfil imovelPerfil = new ImovelPerfil();

		if ( cursor.moveToFirst() ) {
			
			imovelPerfil.setId(cursor.getInt(codigo));
			imovelPerfil.setDescricao(cursor.getString(descricao));
		}

		cursor.close();
		return imovelPerfil;
	}
    
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
    public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "IMOVEL_PERFIL";
	}
}
