package com.br.gsanac.pojos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * @author Jonathan Marcos
 * @since 04/09/2014
 */
public class ImovelSubCategAtlzCad extends EntidadeBasica {

	private static final long serialVersionUID = 1L;
	
	// Atributos
	private ImovelAtlzCadastral imovelAtlzCadastral;
	private Categoria categoria;
    private SubCategoria subCategoria;
    private Integer quantidadeEconomia;
	
    // Index de acesso
    private static final int IMAC_ID_INDEX = 1;
    private static final int CATG_ID_INDEX = 2;
    private static final int SCAT_ID_INDEX = 3;
    private static final int ISAC_QTECONOMIA_INDEX = 4;
   
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class ImovelSubCategAtlzCadColuna implements BaseColumns{
    	public static final String ID = "ISAC_ID";
    	public static final String IMOVELATLZCAD_ID = "IMAC_ID";
    	public static final String CATEGORIA_ID = "CATG_ID";
    	public static final String SUBCATEGORIA_ID = "SCAT_ID";
    	public static final String QTECONOMIA = "ISAC_QTECONOMIA";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
	public static final String[] colunas = new String[]{
		ImovelSubCategAtlzCadColuna.ID,
		ImovelSubCategAtlzCadColuna.IMOVELATLZCAD_ID,
		ImovelSubCategAtlzCadColuna.CATEGORIA_ID,
		ImovelSubCategAtlzCadColuna.SUBCATEGORIA_ID,
		ImovelSubCategAtlzCadColuna.QTECONOMIA
    };
	
	// Método retorna as colunas da tabela
	public String[] getColunas(){
		return colunas;
	}
   
	/*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class ImovelSubCategAtlzCadColunasTipo {
		private final String ID = " INTEGER PRIMARY KEY";
    	private final String IMOVELATLZCAD_ID = " INTEGER NOT NULL";
    	private final String CATEGORIA_ID = " INTEGER";
    	private final String SUBCATEGORIA_ID  = " INTEGER NOT NULL";
    	private final String QTECONOMIA = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {
				ID,
				IMOVELATLZCAD_ID,
				CATEGORIA_ID,
				SUBCATEGORIA_ID,
				QTECONOMIA
		};	
		
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public SubCategoria getSubCategoria() {
		return subCategoria;
	}

	public void setSubCategoria(SubCategoria subCategoria) {
		this.subCategoria = subCategoria;
	}
	
    public Integer getQuantidadeEconomia() {
		return quantidadeEconomia;
	}

	public void setQuantidadeEconomia(Integer quantidadeEconomia) {
		this.quantidadeEconomia = quantidadeEconomia;
	}
	    
	/*
     * Método responsável por retornar objeto
     * a partir de uma lista
     */
    public static ImovelSubCategAtlzCad converteLinhaArquivoEmObjeto(List<String> c) {
    	
    	ImovelSubCategAtlzCad imovelSubCategAtlzCad = new ImovelSubCategAtlzCad();
    	
    	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
    	imovelAtlzCadastral.setId(c.get(IMAC_ID_INDEX));
    	imovelSubCategAtlzCad.setImovelAtlzCadastral(imovelAtlzCadastral);
    	
    	Categoria categoria = new Categoria();
    	categoria.setId(c.get(CATG_ID_INDEX));
    	imovelSubCategAtlzCad.setCategoria(categoria);
    	
    	SubCategoria subCategoria = new SubCategoria();
    	subCategoria.setId(c.get(SCAT_ID_INDEX));
    	imovelSubCategAtlzCad.setSubCategoria(subCategoria);
    	
    	imovelSubCategAtlzCad.setQuantidadeEconomia(Integer.valueOf(c.get(ISAC_QTECONOMIA_INDEX)));
        
        return imovelSubCategAtlzCad;
    }
    
    // Método retorna ContentValues
    public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		values.put(ImovelSubCategAtlzCadColuna.ID, getId());
		values.put(ImovelSubCategAtlzCadColuna.IMOVELATLZCAD_ID, getImovelAtlzCadastral().getId());
		values.put(ImovelSubCategAtlzCadColuna.CATEGORIA_ID, getCategoria().getId());
		values.put(ImovelSubCategAtlzCadColuna.SUBCATEGORIA_ID, getSubCategoria().getId());
		values.put(ImovelSubCategAtlzCadColuna.QTECONOMIA, getQuantidadeEconomia());
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<ImovelSubCategAtlzCad> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<ImovelSubCategAtlzCad> listaImovelSubCategAtlzCad = new ArrayList<ImovelSubCategAtlzCad>();
		
		if(cursor.moveToFirst()){
			do{			
				int _id	= cursor.getColumnIndex(ImovelSubCategAtlzCadColuna.ID);
				int _imovelAtlzCadastralId = cursor.getColumnIndex(ImovelSubCategAtlzCadColuna.IMOVELATLZCAD_ID);
				int _categoriaId	= cursor.getColumnIndex(ImovelSubCategAtlzCadColuna.CATEGORIA_ID);
				int _subCategoriaId 	= cursor.getColumnIndex(ImovelSubCategAtlzCadColuna.SUBCATEGORIA_ID);
				int _qtEconomia = cursor.getColumnIndex(ImovelSubCategAtlzCadColuna.QTECONOMIA);
				
				ImovelSubCategAtlzCad imovelSubCategAtlzCad = new ImovelSubCategAtlzCad();
				
				imovelSubCategAtlzCad.setId(cursor.getInt(_id));
				
				ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
				imovelAtlzCadastral.setId(cursor.getInt(_imovelAtlzCadastralId));			
				imovelSubCategAtlzCad.setImovelAtlzCadastral(imovelAtlzCadastral);
				
				Categoria categoria = new Categoria();
				categoria.setId(cursor.getInt(_categoriaId));			
				imovelSubCategAtlzCad.setCategoria(categoria);
				
				SubCategoria subCategoria = new SubCategoria();
				subCategoria.setId(cursor.getInt(_subCategoriaId));			
				imovelSubCategAtlzCad.setSubCategoria(subCategoria);
				
				imovelSubCategAtlzCad.setQuantidadeEconomia(cursor.getInt(_qtEconomia));
				
				listaImovelSubCategAtlzCad.add(imovelSubCategAtlzCad);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listaImovelSubCategAtlzCad;
	}
	
	// Método converte um cursor em  objeto
	public ImovelSubCategAtlzCad carregarEntidade(Cursor cursor) {		
		
		int _id	= cursor.getColumnIndex(ImovelSubCategAtlzCadColuna.ID);
		int _imovelAtlzCadastralId = cursor.getColumnIndex(ImovelSubCategAtlzCadColuna.IMOVELATLZCAD_ID);
		int _categoriaId = cursor.getColumnIndex(ImovelSubCategAtlzCadColuna.CATEGORIA_ID);
		int _subCategoriaId = cursor.getColumnIndex(ImovelSubCategAtlzCadColuna.SUBCATEGORIA_ID);
		int _qtEconomia	= cursor.getColumnIndex(ImovelSubCategAtlzCadColuna.QTECONOMIA);

		ImovelSubCategAtlzCad imovelSubCategAtlzCad = new ImovelSubCategAtlzCad();
		
		if ( cursor.moveToFirst() ) {
						
			imovelSubCategAtlzCad.setId(cursor.getInt(_id));
			
			ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
			imovelAtlzCadastral.setId(cursor.getInt(_imovelAtlzCadastralId));			
			imovelSubCategAtlzCad.setImovelAtlzCadastral(imovelAtlzCadastral);
			
			Categoria categoria = new Categoria();
			categoria.setId(cursor.getInt(_categoriaId));			
			imovelSubCategAtlzCad.setCategoria(categoria);
			
			SubCategoria subCategoria = new SubCategoria();
			subCategoria.setId(cursor.getInt(_subCategoriaId));			
			imovelSubCategAtlzCad.setSubCategoria(subCategoria);
			
			imovelSubCategAtlzCad.setQuantidadeEconomia(cursor.getInt(_qtEconomia));

		}

		cursor.close();
		return imovelSubCategAtlzCad;
	}
	
	// Retorna no da tabela
    public String getNomeTabela(){
		return "IMOVEL_SUBCATG_ATLZ_CAD";
	}
    
    @Override
	public boolean equals(Object other) {
		if ((this == other)) {
            return true;
        }
		
		if (!(other instanceof ImovelSubCategAtlzCad)) {
            return false;
        }
		ImovelSubCategAtlzCad castOther = (ImovelSubCategAtlzCad) other;
		
		boolean ehIgual = 
			
			((this.getCategoria() == null && castOther.getCategoria() == null) || 
			(this.getCategoria().getId() != null && this.getCategoria().getId().equals(castOther.getCategoria().getId()))) &&
					
			((this.getSubCategoria() == null && castOther.getSubCategoria() == null) || 
			(this.getSubCategoria().getId() != null && this.getSubCategoria().getId().equals(castOther.getSubCategoria().getId()))) &&
			
			((this.getQuantidadeEconomia() == null && castOther.getQuantidadeEconomia() == null) || 
			(this.getQuantidadeEconomia() != null && this.getQuantidadeEconomia().equals(castOther.getQuantidadeEconomia()))); 
			
		return ehIgual;
	}
}
