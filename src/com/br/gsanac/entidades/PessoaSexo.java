package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;


/**
 * <p>
 * Classe responsavel pelo objeto PessoaSexo
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class PessoaSexo extends EntidadeBase {

    private static final long    serialVersionUID     = 4327285475536305741L;

    private static final int     PSEX_ID_INDEX        = 1;
    private static final int     PSEX_DESCRICAO_INDEX        = 2;
    
    public static final int	 MASCULINO = 1;
    public static final int	 FEMININO = 2;

    private String descricao;

    public static final String[] columns              = new String[] {
    	PessoaSexoColunas.ID,
    	PessoaSexoColunas.DESCRICAO
    	};

    public static final class PessoaSexoColunas implements BaseColumns {
        public static final String ID = "PSEX_ID";
        public static final String DESCRICAO = "PSEX_DESCRICAO";
        
    }
    
    public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "PESSOA_SEXO";
	}
    
    public final class PessoaSexoColunasTipo {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(20) NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

    public static PessoaSexo inserirDoArquivo(List<String> c) {
    	
    	PessoaSexo pessoaSexo = new PessoaSexo();

    	pessoaSexo.setId(c.get(PSEX_ID_INDEX));
    	pessoaSexo.setDescricao(c.get(PSEX_DESCRICAO_INDEX));
        
        return pessoaSexo;
    }

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    
  
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(PessoaSexoColunas.ID, getId());
		values.put(PessoaSexoColunas.DESCRICAO, getDescricao());

		return values;
	}
	
	public ArrayList<PessoaSexo> carregarListaEntidade(Cursor cursor) {		
			
		ArrayList<PessoaSexo> pessoaSexos = new ArrayList<PessoaSexo>();
		cursor.moveToFirst();
		
		do{			
			int codigo = cursor.getColumnIndex(PessoaSexoColunas.ID);
			int descricao = cursor.getColumnIndex(PessoaSexoColunas.DESCRICAO);
			
			PessoaSexo pessoaSexo = new PessoaSexo();
			
			pessoaSexo.setId(cursor.getInt(codigo));
			pessoaSexo.setDescricao(cursor.getString(descricao));
			
			pessoaSexos.add(pessoaSexo);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return pessoaSexos;
	}
	
	public PessoaSexo carregarEntidade(Cursor cursor) {		
		
		int codigo = cursor.getColumnIndex(PessoaSexoColunas.ID);
		int descricao = cursor.getColumnIndex(PessoaSexoColunas.DESCRICAO);
		
		PessoaSexo pessoaSexo = new PessoaSexo();

		if ( cursor.moveToFirst() ) {
			
			pessoaSexo.setId(cursor.getInt(codigo));
			pessoaSexo.setDescricao(cursor.getString(descricao));
		}

		cursor.close();
		return pessoaSexo;
	}
}