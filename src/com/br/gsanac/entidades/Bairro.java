package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;


/**
 * <p>
 * Classe responsavel pelo objeto LogradouroCep
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class Bairro extends EntidadeBase {

    private static final long    serialVersionUID     = 4327285475536305741L;

    private static final int     BAIR_ID_INDEX        = 1;
    private static final int     BAIR_CODIGO_INDEX        = 2;
    private static final int     BAIR_DESCRICAO_INDEX        = 3;


    private Integer codigo;
    
    private String descricao;

    public static final String[] colunas              = new String[] {
    	Bairros.ID,
    	Bairros.CODIGO,
    	Bairros.DESCRICAO
    	};

    public static final class Bairros implements BaseColumns {
        public static final String ID = "BAIR_ID";
        public static final String CODIGO = "BAIR_CDBAIRRO";
        public static final String DESCRICAO = "BAIR_NMBAIRRO";
    }
    
    public String[] getColunas(){
		return colunas;
	}
    
    public String getNomeTabela(){
		return "BAIRRO";
	}
    
    public final class BairroTipos {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String CODIGO = " INTEGER NOT NULL";
		public final String DESCRICAO = " VARCHAR(30) NOT NULL";
		
		private String[] tipos = new String[] {ID, CODIGO, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

    public static Bairro inserirDoArquivo(List<String> c) {
    	
    	Bairro bairro = new Bairro();

    	bairro.setId(c.get(BAIR_ID_INDEX));
    	
    	bairro.setCodigo(Integer.valueOf(c.get(BAIR_CODIGO_INDEX)));
    	
    	bairro.setDescricao(c.get(BAIR_DESCRICAO_INDEX));
        
        return bairro;
    }


	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(Bairros.ID, getId());
		values.put(Bairros.CODIGO, getCodigo());
		values.put(Bairros.DESCRICAO, getDescricao());

		return values;
	}
	
	public ArrayList<Bairro> carregarListaEntidade(Cursor cursor) {		
			
		ArrayList<Bairro> bairros = new ArrayList<Bairro>();
		

		if ( cursor.moveToFirst() ) {
			do{
				Bairro bairro = new Bairro();
				
				int id = cursor.getColumnIndex(Bairros.ID);
				int codigo = cursor.getColumnIndex(Bairros.CODIGO);
				int descricao = cursor.getColumnIndex(Bairros.DESCRICAO);
				
				bairro.setId(cursor.getInt(id));
				
				bairro.setCodigo(cursor.getInt(codigo));
				
				bairro.setDescricao(cursor.getString(descricao));
				bairros.add(bairro);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return bairros;
	}
	
	public Bairro carregarEntidade(Cursor cursor) {		
		
		int id = cursor.getColumnIndex(Bairros.ID);
		int codigo = cursor.getColumnIndex(Bairros.CODIGO);
		int descricao = cursor.getColumnIndex(Bairros.DESCRICAO);
		
		Bairro bairro = new Bairro();

		if ( cursor.moveToFirst() ) {
			
			bairro.setId(cursor.getInt(id));
			
			bairro.setCodigo(cursor.getInt(codigo));
			
			bairro.setDescricao(cursor.getString(descricao));
		}
		cursor.close();
		return bairro;
	}
}