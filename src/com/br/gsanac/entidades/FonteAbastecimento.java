package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;


/**
 * <p>
 * Classe responsavel pelo objeto FonteAbastecimento
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class FonteAbastecimento extends EntidadeBase {

    private static final long    serialVersionUID     = 4327285475536305741L;

    private static final int     FTAB_ID_INDEX        = 1;
    private static final int     FTAB_DESCRICAO_INDEX        = 2;
    
    //Constantes
 	public static final int COMPESA = 1;
 	public static final int POCO 	= 2;
 	public static final int VIZINHO = 3;
 	public static final int CACIMBA = 4;
 	public static final int CHAFARIZ 		  = 5;
 	public static final int CARRO_PIPA 		  = 6;
 	public static final int SEM_ABASTECIMENTO = 7;

    private String descricao;

    public static final String[] colunas              = new String[] {
    	FonteAbastecimentos.ID,
    	FonteAbastecimentos.DESCRICAO
    	};

    public static final class FonteAbastecimentos implements BaseColumns {
        public static final String ID = "FTAB_ID";
        public static final String DESCRICAO = "FTAB_DSFONTEABASTECIMENTO";
        
    }
    
    public String[] getColunas(){
		return colunas;
	}
    
    public String getNomeTabela(){
		return "FONTE_ABASTECIMENTO";
	}
    
    public final class FonteAbastecimentoTipos {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(25) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

    public static FonteAbastecimento inserirDoArquivo(List<String> c) {
    	
    	FonteAbastecimento fonteAbastecimento = new FonteAbastecimento();

    	fonteAbastecimento.setId(c.get(FTAB_ID_INDEX));
    	fonteAbastecimento.setDescricao(c.get(FTAB_DESCRICAO_INDEX));
        
        return fonteAbastecimento;
    }

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    
  
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(FonteAbastecimentos.ID, getId());
		values.put(FonteAbastecimentos.DESCRICAO, getDescricao());

		return values;
	}
	
	public ArrayList<FonteAbastecimento> carregarListaEntidade(Cursor cursor) {		
			
		ArrayList<FonteAbastecimento> fonteAbastecimentos = new ArrayList<FonteAbastecimento>();
		
		do{
			FonteAbastecimento fonteAbastecimento = new FonteAbastecimento();
			
			int codigo = cursor.getColumnIndex(FonteAbastecimentos.ID);
			int descricao = cursor.getColumnIndex(FonteAbastecimentos.DESCRICAO);
			
			fonteAbastecimento.setId(cursor.getInt(codigo));
			fonteAbastecimento.setDescricao(cursor.getString(descricao));
			
			fonteAbastecimentos.add(fonteAbastecimento);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return fonteAbastecimentos;
	}
	
	public FonteAbastecimento carregarEntidade(Cursor cursor) {		
		
		int codigo = cursor.getColumnIndex(FonteAbastecimentos.ID);
		int descricao = cursor.getColumnIndex(FonteAbastecimentos.DESCRICAO);
		
		FonteAbastecimento fonteAbastecimento = new FonteAbastecimento();

		if ( cursor.moveToFirst() ) {
			
			fonteAbastecimento.setId(cursor.getInt(codigo));
			fonteAbastecimento.setDescricao(cursor.getString(descricao));
		}

		cursor.close();
		return fonteAbastecimento;
	}
}