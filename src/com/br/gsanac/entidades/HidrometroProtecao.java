package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto HidrometroProtecao
 * </p>
 * 
 * @author Anderson Cabral
 * @since  11/12/2012
 */
public class HidrometroProtecao extends EntidadeBase{

	private static final long serialVersionUID = 1L;
	private static final int HIPR_ID_INDEX         			 = 1;
    private static final int HIPR_DSHIDROMETROPROTECAO_INDEX = 2;

    private String descricao;
    
    public static final String[] columns = new String[]{
    	HidrometroProtecaoColunas.ID,
    	HidrometroProtecaoColunas.DESCRICAO
    };
    
    public static final class HidrometroProtecaoColunas implements BaseColumns{
    	public static final String ID 		 = "HIPR_ID";
    	public static final String DESCRICAO = "HIPR_DSHIDROMETROPROTECAO";
    }
    
    public final class HidrometroProtecaoColunasTipo {
		public final String ID 		  = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(50) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
    
    public static HidrometroProtecao inserirDoArquivo(List<String> c) {
    	
    	HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();

    	hidrometroProtecao.setId(c.get(HIPR_ID_INDEX));
    	hidrometroProtecao.setDescricao(c.get(HIPR_DSHIDROMETROPROTECAO_INDEX));
        
        return hidrometroProtecao;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(HidrometroProtecaoColunas.ID, getId());
		values.put(HidrometroProtecaoColunas.DESCRICAO, getDescricao());

		return values;
	}
    
	public ArrayList<HidrometroProtecao> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<HidrometroProtecao> listaHidrometroProtecao = new ArrayList<HidrometroProtecao>();
		if ( cursor.moveToFirst() ) {
			do{
				int codigo 	  = cursor.getColumnIndex(HidrometroProtecaoColunas.ID);
				int descricao = cursor.getColumnIndex(HidrometroProtecaoColunas.DESCRICAO);
				
				HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();
	
				hidrometroProtecao.setId(cursor.getInt(codigo));
				hidrometroProtecao.setDescricao(cursor.getString(descricao));
			
				listaHidrometroProtecao.add(hidrometroProtecao);
				
			} while (cursor.moveToNext());
		}		
		
		cursor.close();
		return listaHidrometroProtecao;
	}
	
	public HidrometroProtecao carregarEntidade(Cursor cursor) {		
		
		int codigo 	  = cursor.getColumnIndex(HidrometroProtecaoColunas.ID);
		int descricao = cursor.getColumnIndex(HidrometroProtecaoColunas.DESCRICAO);
		
		HidrometroProtecao hidrometroProtecao = new HidrometroProtecao();

		if ( cursor.moveToFirst() ) {
			
			hidrometroProtecao.setId(cursor.getInt(codigo));
			hidrometroProtecao.setDescricao(cursor.getString(descricao));
		}

		cursor.close();
		return hidrometroProtecao;
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
		return "HIDROMETRO_PROTECAO";
	}
}
