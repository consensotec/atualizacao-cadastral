package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import com.br.gsanac.entidades.Quadra.QuadraColunas;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto SetorComercial
 * </p>
 * 
 * @author Anderson Cabral
 * @since  25/06/2013
 */
public class SetorComercial extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int STCM_ID_INDEX       		 = 1;
    private static final int STCM_CDSETORCOMERCIAL_INDEX = 2;
    private static final int STCM_DSSETORCOMERCIAL_INDEX = 3;

    private Integer id;
    private Integer codigo;
    private String descricao;
    private Integer ordenacao;
    
    public static final String[] columns = new String[]{
    	SetorComercialColunas.ID,
    	SetorComercialColunas.CODIGO,
    	SetorComercialColunas.DESCRICAO,
    	SetorComercialColunas.ORDENACAO
    };
    
    public static final class SetorComercialColunas implements BaseColumns{
    	public static final String ID 		 = "STCM_ID";
    	public static final String CODIGO 	 = "STCM_CDSETORCOMERCIAL";
    	public static final String DESCRICAO = "STCM_DSSETORCOMERCIAL";
    	public static final String ORDENACAO = "STCM_ORDENACAO";
    }
	
    public final class SetorColunasTipo {
		public final String ID 		  = " INTEGER PRIMARY KEY";
		public final String CODIGO 	  = " INTEGER NOT NULL";
		public final String DESCRICAO = " VARCHAR(50)";
		public final String ORDENACAO = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, CODIGO, DESCRICAO, ORDENACAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
    
    public static SetorComercial inserirDoArquivo(List<String> c) {
    	
    	SetorComercial setorComercial = new SetorComercial();

    	setorComercial.setId(c.get(STCM_ID_INDEX));
    	setorComercial.setCodigo(Integer.valueOf(c.get(STCM_CDSETORCOMERCIAL_INDEX)));
    	setorComercial.setDescricao(String.valueOf(c.get(STCM_DSSETORCOMERCIAL_INDEX)));
    	setorComercial.setOrdenacao(setorComercial.getCodigo());
    	
        return setorComercial;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(SetorComercialColunas.ID, getId());
		values.put(SetorComercialColunas.CODIGO, getCodigo());
		values.put(SetorComercialColunas.DESCRICAO, getDescricao());
		values.put(SetorComercialColunas.ORDENACAO, getOrdenacao());

		return values;
	}
    
	public ArrayList<SetorComercial> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<SetorComercial> listaSetorComercial = new ArrayList<SetorComercial>();
		cursor.moveToFirst();
		
		do{
			int id 	   = cursor.getColumnIndex(SetorComercialColunas.ID);
			int codigo = cursor.getColumnIndex(SetorComercialColunas.CODIGO);
			int descricao = cursor.getColumnIndex(SetorComercialColunas.DESCRICAO);
			int ordenacao = cursor.getColumnIndex(SetorComercialColunas.ORDENACAO);
			
			SetorComercial setorComercial = new SetorComercial();
			
			setorComercial.setId(cursor.getInt(id));
			setorComercial.setCodigo(cursor.getInt(codigo));
			setorComercial.setDescricao(cursor.getString(descricao));
			setorComercial.setOrdenacao(cursor.getInt(ordenacao));
			
			listaSetorComercial.add(setorComercial);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return listaSetorComercial;
	}
	
	public SetorComercial carregarEntidade(Cursor cursor) {	
		
		int id 	   = cursor.getColumnIndex(SetorComercialColunas.ID);
		int codigo = cursor.getColumnIndex(SetorComercialColunas.CODIGO);
		int descricao = cursor.getColumnIndex(SetorComercialColunas.DESCRICAO);
		int ordenacao = cursor.getColumnIndex(SetorComercialColunas.ORDENACAO);
		
		SetorComercial setorComercial = new SetorComercial();

		if ( cursor.moveToFirst() ) {
			
			setorComercial.setId(cursor.getInt(id));
			setorComercial.setCodigo(cursor.getInt(codigo));
			setorComercial.setDescricao(cursor.getString(descricao));
			setorComercial.setOrdenacao(cursor.getInt(ordenacao));
		}

		cursor.close();
		return setorComercial;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getOrdenacao() {
		return ordenacao;
	}

	public void setOrdenacao(Integer ordenacao) {
		this.ordenacao = ordenacao;
	}

	public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "SETOR_COMERCIAL";
	}
}
