package com.br.gsanac.pojos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * @author Jonathan Marcos
 * @since 03/09/2014
 */
public class CadastroOcorrencia extends EntidadeBasica {

	private static final long serialVersionUID = 1L;
	
	// Atributos
	private String descricao;
    private Short indicadorCampoObrigatorio;
	
    // Index de acesso
    private static final int COCR_ID_INDEX = 1;
    private static final int COCR_DSCADASTROOCORRENCIA_INDEX = 2;
    private static final int COCR_ICCAMPOSOBRIGTABLET_INDEX = 3;
    
    // Constantes
  	public final static int SEM_OCORRENCIAS = 1; 	
  	public final static int CLIENTE_NAO_PERMITIU_ACESSO = 2;
  	public final static int CLIENTE_NAO_PODE_RESPONDER = 3;
  	public final static int IMOVEL_NAO_VISITADO = 8;
  	public final static int IMOVEL_FECHADO = 10;
  	public final static int ANIMAL_BRAVO = 11;
  	public final static int IMOVEL_NAO_LOCALIZADO = 16;
  	public final static int IMOVEL_DEMOLIDO = 26;
  	public final static int IMOVEL_DESOCUPADO = 27;

  	/*
     * SubClasse referente aos 
     * campos da tabela
     */
  	public static final class CadastroOcorrenciaColuna implements BaseColumns{
    	public static final String ID = "COCR_ID";
    	public static final String DESCRICAO = "COCR_DSCADASTROOCORRENCIA";
    	public static final String INDICADOR_CAMPO_OBRIGATORIO = "COCR_ICCAMPOSOBRIGTABLET";
    }
  	
  	/*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	CadastroOcorrenciaColuna.ID,
    	CadastroOcorrenciaColuna.DESCRICAO,
    	CadastroOcorrenciaColuna.INDICADOR_CAMPO_OBRIGATORIO
    };
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}

    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class CadastroOcorrenciaColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String DESCRICAO = " VARCHAR(25) NOT NULL";
		private final String INDICADOR_CAMPO_OBRIGATORIO = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO, INDICADOR_CAMPO_OBRIGATORIO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
   
    public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Short getIndicadorCampoObrigatorio() {
		return indicadorCampoObrigatorio;
	}

	public void setIndicadorCampoObrigatorio(Short indicadorCampoObrigatorio) {
		this.indicadorCampoObrigatorio = indicadorCampoObrigatorio;
	}
    
    /*
     * Método responsável por retornar objeto
     * a partir de uma lista
     */
    public static CadastroOcorrencia converteLinhaArquivoEmObjeto(List<String> c) {
    	CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();
    	cadastroOcorrencia.setId(c.get(COCR_ID_INDEX));
    	cadastroOcorrencia.setDescricao(c.get(COCR_DSCADASTROOCORRENCIA_INDEX));
    	cadastroOcorrencia.setIndicadorCampoObrigatorio(Short.valueOf(c.get(COCR_ICCAMPOSOBRIGTABLET_INDEX)));
        return cadastroOcorrencia;
    }
    
	// M�todo retorna ContentValues
    public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		values.put(CadastroOcorrenciaColuna.ID, getId());
		values.put(CadastroOcorrenciaColuna.DESCRICAO, getDescricao());
		values.put(CadastroOcorrenciaColuna.INDICADOR_CAMPO_OBRIGATORIO, getIndicadorCampoObrigatorio());
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<CadastroOcorrencia> carregarListaEntidade(Cursor cursor) {		
		ArrayList<CadastroOcorrencia> listaCadastroOcorrencia = new ArrayList<CadastroOcorrencia>();
		if ( cursor.moveToFirst() ) {
			do{
				CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();
				
				int _id = cursor.getColumnIndex(CadastroOcorrenciaColuna.ID);
				int _descricao = cursor.getColumnIndex(CadastroOcorrenciaColuna.DESCRICAO);
				int _indicadorCampoObrigatorio = cursor.getColumnIndex(CadastroOcorrenciaColuna.INDICADOR_CAMPO_OBRIGATORIO);
				
				cadastroOcorrencia.setId(cursor.getInt(_id));
				cadastroOcorrencia.setDescricao(cursor.getString(_descricao));
				cadastroOcorrencia.setIndicadorCampoObrigatorio(cursor.getShort(_indicadorCampoObrigatorio));
				listaCadastroOcorrencia.add(cadastroOcorrencia);
			
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listaCadastroOcorrencia;
	}
	
	// Método converte um cursor em  objeto
	public CadastroOcorrencia carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(CadastroOcorrenciaColuna.ID);
		int _descricao = cursor.getColumnIndex(CadastroOcorrenciaColuna.DESCRICAO);
		int _indicadorCampoObrigatorio = cursor.getColumnIndex(CadastroOcorrenciaColuna.INDICADOR_CAMPO_OBRIGATORIO);
		
		CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();

		if ( cursor.moveToFirst() ) {
			
			cadastroOcorrencia.setId(cursor.getInt(_id));
			cadastroOcorrencia.setDescricao(cursor.getString(_descricao));
			cadastroOcorrencia.setIndicadorCampoObrigatorio(cursor.getShort(_indicadorCampoObrigatorio));
		}
		
		cursor.close();
		return cadastroOcorrencia;
	}
	
	// Retorna nome da tabela
	public String getNomeTabela(){
		return "CADASTRO_OCORRENCIA";
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getDescricao();
	}
}
