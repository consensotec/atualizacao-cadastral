package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto ImovelOcorrencia
 * </p>
 * 
 * @author Anderson Cabral
 * @since  08/01/2013
 */
public class ImovelOcorrencia extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
	//arquivo dividido
	private static final int AD_COCR_ID_INDEX         = 1;
    private static final int AD_IMAC_ID_INDEX         = 2;

    private ImovelAtlzCadastral imovelAtlzCadastral;
    private CadastroOcorrencia 	cadastroOcorrencia;
    
    public static final String[] columns = new String[]{
    	ImovelOcorrenciaColunas.ID,
    	ImovelOcorrenciaColunas.IMOVELATLZCAD_ID,
    	ImovelOcorrenciaColunas.CADOCORRENCIA_ID
    };
    
    public static final class ImovelOcorrenciaColunas implements BaseColumns{
    	public static final String ID 		 		= "IMOC_ID";
    	public static final String IMOVELATLZCAD_ID = "IMAC_ID";
    	public static final String CADOCORRENCIA_ID = "COCR_ID";
    }
	
    public final class ImovelOcorrenciaColunasTipo {
		public final String ID 		  		 = " INTEGER PRIMARY KEY";
		public final String IMOVELATLZCAD_ID = " INTEGER NOT NULL";
		public final String CADOCORRENCIA_ID = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, IMOVELATLZCAD_ID, CADOCORRENCIA_ID};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
    
    public static ImovelOcorrencia inserirAtualizarDoArquivoDividido(List<String> c, ImovelAtlzCadastral imovelAtlzCadastral) {
    	
    	ImovelOcorrencia imovelOcorrencia = new ImovelOcorrencia();
    	
    	CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();
    	cadastroOcorrencia.setId(c.get(AD_COCR_ID_INDEX));
    	imovelOcorrencia.setCadastroOcorrencia(cadastroOcorrencia);
    	
//    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
//    	_imovelAtlzCadastral.setId(idImovelAtlzCad.intValue());
    	imovelOcorrencia.setImovelAtlzCadastral(imovelAtlzCadastral);	
    	
        return imovelOcorrencia;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(ImovelOcorrenciaColunas.ID, getId());
		values.put(ImovelOcorrenciaColunas.IMOVELATLZCAD_ID, getImovelAtlzCadastral().getId());
		values.put(ImovelOcorrenciaColunas.CADOCORRENCIA_ID, getCadastroOcorrencia().getId());

		return values;
	}
    
	public ArrayList<ImovelOcorrencia> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<ImovelOcorrencia> listaImovelOcorrencia = new ArrayList<ImovelOcorrencia>();
		
		if ( cursor.moveToFirst() ) {
			do{
				ImovelOcorrencia imovelOcorrencia = new ImovelOcorrencia();
				
				int id 	  			 	 = cursor.getColumnIndex(ImovelOcorrenciaColunas.ID);
				int imovelAtlzCadId 	 = cursor.getColumnIndex(ImovelOcorrenciaColunas.IMOVELATLZCAD_ID);
				int cadastroOcorrenciaId = cursor.getColumnIndex(ImovelOcorrenciaColunas.CADOCORRENCIA_ID);
				
				imovelOcorrencia.setId(cursor.getInt(id));
				
		    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
		    	_imovelAtlzCadastral.setId(cursor.getInt(imovelAtlzCadId));   	
		    	imovelOcorrencia.setImovelAtlzCadastral(_imovelAtlzCadastral);
				
		    	CadastroOcorrencia _cadastroOcorrencia = new CadastroOcorrencia();
		    	_cadastroOcorrencia.setId(cursor.getInt(cadastroOcorrenciaId));
		    	imovelOcorrencia.setCadastroOcorrencia(_cadastroOcorrencia);
		    	
				listaImovelOcorrencia.add(imovelOcorrencia);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaImovelOcorrencia;
	}
	
	public ImovelOcorrencia carregarEntidade(Cursor cursor) {		
		
		int id 	  			 	 = cursor.getColumnIndex(ImovelOcorrenciaColunas.ID);
		int imovelAtlzCadId 	 = cursor.getColumnIndex(ImovelOcorrenciaColunas.IMOVELATLZCAD_ID);
		int cadastroOcorrenciaId = cursor.getColumnIndex(ImovelOcorrenciaColunas.CADOCORRENCIA_ID);
		
		ImovelOcorrencia imovelOcorrencia = new ImovelOcorrencia();

		if ( cursor.moveToFirst() ) {
			
			imovelOcorrencia.setId(cursor.getInt(id));
			
	    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
	    	_imovelAtlzCadastral.setId(cursor.getInt(imovelAtlzCadId));   	
	    	imovelOcorrencia.setImovelAtlzCadastral(_imovelAtlzCadastral);
			
	    	CadastroOcorrencia _cadastroOcorrencia = new CadastroOcorrencia();
	    	_cadastroOcorrencia.setId(cursor.getInt(cadastroOcorrenciaId));
	    	imovelOcorrencia.setCadastroOcorrencia(_cadastroOcorrencia);
		}

		cursor.close();
		return imovelOcorrencia;
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

	public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "IMOVEL_OCORRENCIA";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((cadastroOcorrencia == null) ? 0 : cadastroOcorrencia
						.hashCode());
		result = prime
				* result
				+ ((imovelAtlzCadastral == null) ? 0 : imovelAtlzCadastral
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImovelOcorrencia other = (ImovelOcorrencia) obj;
		if (cadastroOcorrencia == null) {
			if (other.cadastroOcorrencia != null)
				return false;
		} else if (!cadastroOcorrencia.getId().equals(other.cadastroOcorrencia.getId()))
			return false;
		if (imovelAtlzCadastral == null) {
			if (other.imovelAtlzCadastral != null)
				return false;
		}else if (imovelAtlzCadastral.getId() == null) {
			if (other.imovelAtlzCadastral.getId() != null)
				return false;
		} else if (!imovelAtlzCadastral.getId().equals(other.imovelAtlzCadastral.getId()))
			return false;
		return true;
	}
    
    

    
}
