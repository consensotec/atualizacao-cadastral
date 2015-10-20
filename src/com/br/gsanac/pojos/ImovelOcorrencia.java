package com.br.gsanac.pojos;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * @author Jonathan Marcos
 * @since 05/09/2014
 */
public class ImovelOcorrencia extends EntidadeBasica {

	private static final long serialVersionUID = 1L;
	
	// Atributos
    private ImovelAtlzCadastral imovelAtlzCadastral;
    private CadastroOcorrencia 	cadastroOcorrencia;
    
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class ImovelOcorrenciaColuna implements BaseColumns{
    	public static final String ID = "IMOC_ID";
    	public static final String IMOVELATLZCAD_ID = "IMAC_ID";
    	public static final String CADOCORRENCIA_ID = "COCR_ID";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	ImovelOcorrenciaColuna.ID,
    	ImovelOcorrenciaColuna.IMOVELATLZCAD_ID,
    	ImovelOcorrenciaColuna.CADOCORRENCIA_ID
    };
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
	
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class ImovelOcorrenciaColunaTipo {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String IMOVELATLZCAD_ID = " INTEGER NOT NULL";
		public final String CADOCORRENCIA_ID = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, IMOVELATLZCAD_ID, CADOCORRENCIA_ID};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
    
    public ImovelAtlzCadastral getImovelAtlzCadastral() {
		return imovelAtlzCadastral;
	}

	public void setImovelAtlzCadastral(ImovelAtlzCadastral imovelAtlzCadastral) {
		this.imovelAtlzCadastral = imovelAtlzCadastral;
	}

	public CadastroOcorrencia getCadastroOcorrencia() {
		return cadastroOcorrencia;
	}

	public void setCadastroOcorrencia(CadastroOcorrencia cadastroOcorrencia) {
		this.cadastroOcorrencia = cadastroOcorrencia;
	}
    
	// Método retorna ContentValues
    public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		values.put(ImovelOcorrenciaColuna.ID, getId());
		values.put(ImovelOcorrenciaColuna.IMOVELATLZCAD_ID, getImovelAtlzCadastral().getId());
		values.put(ImovelOcorrenciaColuna.CADOCORRENCIA_ID, getCadastroOcorrencia().getId());
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<ImovelOcorrencia> carregarListaEntidade(Cursor cursor) {		
		ArrayList<ImovelOcorrencia> listaImovelOcorrencia = new ArrayList<ImovelOcorrencia>();
		
		if ( cursor.moveToFirst() ) {
			do{
				ImovelOcorrencia imovelOcorrencia = new ImovelOcorrencia();
				
				int _id = cursor.getColumnIndex(ImovelOcorrenciaColuna.ID);
				int _imovelAtlzCadId = cursor.getColumnIndex(ImovelOcorrenciaColuna.IMOVELATLZCAD_ID);
				int _cadastroOcorrenciaId = cursor.getColumnIndex(ImovelOcorrenciaColuna.CADOCORRENCIA_ID);
				
				imovelOcorrencia.setId(cursor.getInt(_id));
				
		    	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
		    	imovelAtlzCadastral.setId(cursor.getInt(_imovelAtlzCadId));   	
		    	imovelOcorrencia.setImovelAtlzCadastral(imovelAtlzCadastral);
				
		    	CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();
		    	cadastroOcorrencia.setId(cursor.getInt(_cadastroOcorrenciaId));
		    	imovelOcorrencia.setCadastroOcorrencia(cadastroOcorrencia);
		    	
				listaImovelOcorrencia.add(imovelOcorrencia);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaImovelOcorrencia;
	}
	
	// Método converte um cursor em  objeto
	public ImovelOcorrencia carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(ImovelOcorrenciaColuna.ID);
		int _imovelAtlzCadId = cursor.getColumnIndex(ImovelOcorrenciaColuna.IMOVELATLZCAD_ID);
		int _cadastroOcorrenciaId = cursor.getColumnIndex(ImovelOcorrenciaColuna.CADOCORRENCIA_ID);
		
		ImovelOcorrencia imovelOcorrencia = new ImovelOcorrencia();

		if ( cursor.moveToFirst() ) {
			
			imovelOcorrencia.setId(cursor.getInt(_id));
			
	    	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
	    	imovelAtlzCadastral.setId(cursor.getInt(_imovelAtlzCadId));   	
	    	imovelOcorrencia.setImovelAtlzCadastral(imovelAtlzCadastral);
			
	    	CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();
	    	cadastroOcorrencia.setId(cursor.getInt(_cadastroOcorrenciaId));
	    	imovelOcorrencia.setCadastroOcorrencia(cadastroOcorrencia);
		}

		cursor.close();
		return imovelOcorrencia;
	}
    
	// Retorna no da tabela
    public String getNomeTabela(){
		return "IMOVEL_OCORRENCIA";
	}
}
