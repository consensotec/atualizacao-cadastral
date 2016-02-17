package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto ImovelSubCategAtlzCad
 * </p>
 * 
 * @author Anderson Cabral
 * @since  10/12/2012
 */
public class ImovelSubCategAtlzCad extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
//    private static final int ISAC_ID_INDEX         = 1;
    private static final int IMAC_ID_INDEX         = 1;
    private static final int IMOV_ID_INDEX         = 2;
    private static final int CATG_ID_INDEX         = 3;
    private static final int SCAT_ID_INDEX         = 4;
    private static final int ISAC_QTECONOMIA_INDEX = 5;
    
    private static final int AD_IMAC_ID_INDEX         = 1;
    private static final int AD_CATG_ID_INDEX         = 2;
    private static final int AD_SCAT_ID_INDEX         = 3;
    private static final int AD_ISAC_QTECONOMIA_INDEX = 4;

    private ImovelAtlzCadastral imovelAtlzCadastral;
	private Categoria categoria;
    private SubCategoria subCategoria;
    private Integer imovelId;
    private Integer quantidadeEconomia;

	public static final String[] columns = new String[]{
    	ImovelSubCategAtlzCadColunas.ID,
    	ImovelSubCategAtlzCadColunas.IMOVELATLZCAD_ID,
    	ImovelSubCategAtlzCadColunas.CATEGORIA_ID,
    	ImovelSubCategAtlzCadColunas.SUBCATEGORIA_ID,
    	ImovelSubCategAtlzCadColunas.IMOVEL_ID,
    	ImovelSubCategAtlzCadColunas.QTECONOMIA
    };
    
    public static final class ImovelSubCategAtlzCadColunas implements BaseColumns{
    	public static final String ID 		 		= "ISAC_ID";
    	public static final String IMOVELATLZCAD_ID = "IMAC_ID";
    	public static final String CATEGORIA_ID 	= "CATG_ID";
    	public static final String SUBCATEGORIA_ID 	= "SCAT_ID";
    	public static final String IMOVEL_ID 		= "IMOV_ID";
    	public static final String QTECONOMIA		= "ISAC_QTECONOMIA";
    }
	
    public final class ImovelSubCategAtlzCadColunasTipo {
		public final String ID 		  		 = " INTEGER PRIMARY KEY AUTOINCREMENT";
    	public final String IMOVELATLZCAD_ID = " INTEGER NOT NULL";
    	public final String CATEGORIA_ID 	 = " INTEGER";
    	public final String SUBCATEGORIA_ID  = " INTEGER NOT NULL";
    	public final String IMOVEL_ID 		 = " INTEGER";
    	public final String QTECONOMIA		 = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, IMOVELATLZCAD_ID, CATEGORIA_ID, 
												SUBCATEGORIA_ID, IMOVEL_ID, QTECONOMIA};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static ImovelSubCategAtlzCad inserirDoArquivo(List<String> c) {
    	
    	ImovelSubCategAtlzCad imovelSubCategAtlzCad = new ImovelSubCategAtlzCad();
//    	imovelSubCategAtlzCad.setId(c.get(ISAC_ID_INDEX));
    	
    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
    	_imovelAtlzCadastral.setId(c.get(IMAC_ID_INDEX));
    	imovelSubCategAtlzCad.setImovelAtlzCadastral(_imovelAtlzCadastral);
    	
    	Categoria _categoria = new Categoria();
    	_categoria.setId(c.get(CATG_ID_INDEX));
    	imovelSubCategAtlzCad.setCategoria(_categoria);
    	
    	SubCategoria _subCategoria = new SubCategoria();
    	_subCategoria.setId(c.get(SCAT_ID_INDEX));
    	imovelSubCategAtlzCad.setSubCategoria(_subCategoria);
    	
    	imovelSubCategAtlzCad.setImovelId(Integer.valueOf(c.get(IMOV_ID_INDEX)));
    	
    	imovelSubCategAtlzCad.setQuantidadeEconomia(Integer.valueOf(c.get(ISAC_QTECONOMIA_INDEX)));
        
        return imovelSubCategAtlzCad;
    }
    
  public static ImovelSubCategAtlzCad inserirAtualizarDoArquivoDividido(List<String> c, Long idImovelAtlzCad) {
    	
    	ImovelSubCategAtlzCad imovelSubCategAtlzCad = new ImovelSubCategAtlzCad();
    	
    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
    	_imovelAtlzCadastral.setId(idImovelAtlzCad.intValue());
    	imovelSubCategAtlzCad.setImovelAtlzCadastral(_imovelAtlzCadastral);
    	
    	Categoria _categoria = new Categoria();
    	_categoria.setId(c.get(AD_CATG_ID_INDEX));
    	imovelSubCategAtlzCad.setCategoria(_categoria);
    	
    	SubCategoria _subCategoria = new SubCategoria();
    	_subCategoria.setId(c.get(AD_SCAT_ID_INDEX));
    	imovelSubCategAtlzCad.setSubCategoria(_subCategoria);
    	
    	imovelSubCategAtlzCad.setQuantidadeEconomia(Integer.valueOf(c.get(AD_ISAC_QTECONOMIA_INDEX)));
        
        return imovelSubCategAtlzCad;
    }
    
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(ImovelSubCategAtlzCadColunas.ID, getId());
		values.put(ImovelSubCategAtlzCadColunas.IMOVELATLZCAD_ID, getImovelAtlzCadastral().getId());
		values.put(ImovelSubCategAtlzCadColunas.CATEGORIA_ID, getCategoria().getId());
		values.put(ImovelSubCategAtlzCadColunas.SUBCATEGORIA_ID, getSubCategoria().getId());
		values.put(ImovelSubCategAtlzCadColunas.IMOVEL_ID, getImovelId());
		values.put(ImovelSubCategAtlzCadColunas.QTECONOMIA, getQuantidadeEconomia());

		return values;
	}
    
	public ArrayList<ImovelSubCategAtlzCad> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<ImovelSubCategAtlzCad> listaImovelSubCategAtlzCad = new ArrayList<ImovelSubCategAtlzCad>();
		
		cursor.moveToFirst();
		
		do{			
			int codigo 	  	   	= cursor.getColumnIndex(ImovelSubCategAtlzCadColunas.ID);
			int imovelAtlzCadastralId = cursor.getColumnIndex(ImovelSubCategAtlzCadColunas.IMOVELATLZCAD_ID);
			int categoriaId    	= cursor.getColumnIndex(ImovelSubCategAtlzCadColunas.CATEGORIA_ID);
			int subCategoriaId 	= cursor.getColumnIndex(ImovelSubCategAtlzCadColunas.SUBCATEGORIA_ID);
			int imovelId	   	= cursor.getColumnIndex(ImovelSubCategAtlzCadColunas.IMOVEL_ID);
			int qtEconomia	   	= cursor.getColumnIndex(ImovelSubCategAtlzCadColunas.QTECONOMIA);
			
			ImovelSubCategAtlzCad imovelSubCategAtlzCad = new ImovelSubCategAtlzCad();
			
			imovelSubCategAtlzCad.setId(cursor.getInt(codigo));
			
			ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
			_imovelAtlzCadastral.setId(cursor.getInt(imovelAtlzCadastralId));			
			imovelSubCategAtlzCad.setImovelAtlzCadastral(_imovelAtlzCadastral);
			
			Categoria _categoria = new Categoria();
			_categoria.setId(cursor.getInt(categoriaId));			
			imovelSubCategAtlzCad.setCategoria(_categoria);
			
			SubCategoria _subCategoria = new SubCategoria();
			_subCategoria.setId(cursor.getInt(subCategoriaId));			
			imovelSubCategAtlzCad.setSubCategoria(_subCategoria);
			
			imovelSubCategAtlzCad.setImovelId(cursor.getInt(imovelId));
			
			imovelSubCategAtlzCad.setQuantidadeEconomia(cursor.getInt(qtEconomia));
			
			listaImovelSubCategAtlzCad.add(imovelSubCategAtlzCad);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return listaImovelSubCategAtlzCad;
	}
	
	public ImovelSubCategAtlzCad carregarEntidade(Cursor cursor) {		
		
		int codigo 	  	   	= cursor.getColumnIndex(ImovelSubCategAtlzCadColunas.ID);
		int imovelAtlzCadastralId = cursor.getColumnIndex(ImovelSubCategAtlzCadColunas.IMOVELATLZCAD_ID);
		int categoriaId    	= cursor.getColumnIndex(ImovelSubCategAtlzCadColunas.CATEGORIA_ID);
		int subCategoriaId 	= cursor.getColumnIndex(ImovelSubCategAtlzCadColunas.SUBCATEGORIA_ID);
		int imovelId	   	= cursor.getColumnIndex(ImovelSubCategAtlzCadColunas.IMOVEL_ID);
		int qtEconomia	   	= cursor.getColumnIndex(ImovelSubCategAtlzCadColunas.QTECONOMIA);

		ImovelSubCategAtlzCad imovelSubCategAtlzCad = new ImovelSubCategAtlzCad();
		
		if ( cursor.moveToFirst() ) {
						
			imovelSubCategAtlzCad.setId(cursor.getInt(codigo));
			
			ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
			_imovelAtlzCadastral.setId(cursor.getInt(imovelAtlzCadastralId));			
			imovelSubCategAtlzCad.setImovelAtlzCadastral(_imovelAtlzCadastral);
			
			Categoria _categoria = new Categoria();
			_categoria.setId(cursor.getInt(categoriaId));			
			imovelSubCategAtlzCad.setCategoria(_categoria);
			
			SubCategoria _subCategoria = new SubCategoria();
			_subCategoria.setId(cursor.getInt(subCategoriaId));			
			imovelSubCategAtlzCad.setSubCategoria(_subCategoria);
			
			imovelSubCategAtlzCad.setImovelId(cursor.getInt(imovelId));
			
			imovelSubCategAtlzCad.setQuantidadeEconomia(cursor.getInt(qtEconomia));

		}

		cursor.close();
		return imovelSubCategAtlzCad;
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
	
	public Integer getImovelId() {
		return imovelId;
	}

	public void setImovelId(Integer imovelId) {
		this.imovelId = imovelId;
	}
	
    public String[] getColunas(){
		return columns;
	}
    
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
