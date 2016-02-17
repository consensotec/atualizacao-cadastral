package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

/**
 * <p>
 * Classe responsavel pelo objeto Foto
 * </p>
 * 
 * @author Anderson Cabral
 * @since  04/01/2013
 */
public class Foto extends EntidadeBase implements Cloneable{

	private static final long serialVersionUID = 1L;
	
	private static final int FOTO_PATH_INDEX = 1;
    private static final int FOTP_ID_INDEX 	 = 2;

    private ImovelAtlzCadastral imovelAtlzCadastral;
    private Integer fotoTipo;
    private String fotoPath;
    private Date ultimaAlteracao;
    private Integer indicadorTransmitido;
    
    public static final String[] columns = new String[]{
    	FotoColunas.ID,
    	FotoColunas.FOTOTIPO,
    	FotoColunas.IMOVELATLZCAD_ID,
    	FotoColunas.FOTOPATH,
    	FotoColunas.FOTOULTIMAALTERACAO,
    	FotoColunas.INDICADORTRANSMITIDO
    };
    
    public static final class FotoColunas implements BaseColumns{
    	public static final String ID 		   		= "FOTO_ID";
    	public static final String IMOVELATLZCAD_ID = "IMAC_ID";
    	public static final String FOTOTIPO			= "FOTO_TIPO";
    	public static final String FOTOPATH   		= "FOTO_PATH";
    	public static final String FOTOULTIMAALTERACAO  = "FOTO_TMULTIMAALTERACAO";
    	public static final String INDICADORTRANSMITIDO = "FOTO_ICTRANSMITIDO";
    }
	
    public final class FotoColunasTipo {
		public final String ID 		  			 = " INTEGER PRIMARY KEY";
		public final String IMOVELATLZCAD_ID 	 = " INTEGER NOT NULL";
		public final String FOTOTIPO 			 = " INTEGER NOT NULL";
		public final String FOTOPATH 			 = " VARCHAR NOT NULL";
		public final String FOTOULTIMAALTERACAO  = " DATETIME NOT NULL";
		public final String INDICADORTRANSMITIDO = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, IMOVELATLZCAD_ID, FOTOTIPO, FOTOPATH,
											   FOTOULTIMAALTERACAO, INDICADORTRANSMITIDO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static Foto inserirAtualizarDoArquivoDividido(List<String> c, ImovelAtlzCadastral imovelAtlzCadastral) {
    	
    	Foto foto = new Foto();

    	foto.setFotoTipo(Integer.valueOf(c.get(FOTP_ID_INDEX)));
    	
    	foto.setImovelAtlzCadastral(imovelAtlzCadastral);
    	
    	foto.setFotoPath(c.get(FOTO_PATH_INDEX));
    	foto.setUltimaAlteracao(new Date());
    	foto.setIndicadorTransmitido(Integer.valueOf(String.valueOf(ConstantesSistema.NAO)));
        
        return foto;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(FotoColunas.ID, getId());
		values.put(FotoColunas.IMOVELATLZCAD_ID, getImovelAtlzCadastral().getId());
		values.put(FotoColunas.FOTOTIPO, getFotoTipo());
		values.put(FotoColunas.FOTOPATH, getFotoPath());
		values.put(FotoColunas.FOTOULTIMAALTERACAO, Util.convertDateToString(getUltimaAlteracao()));
		values.put(FotoColunas.INDICADORTRANSMITIDO, getIndicadorTransmitido());

		return values;
	}
    
	public ArrayList<Foto> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<Foto> listaFoto = new ArrayList<Foto>();
		if ( cursor.moveToFirst() ) {
			do{
				Foto foto = new Foto();
				int id 	  	  			 = cursor.getColumnIndex(FotoColunas.ID);
				int imovelAtlzCadId 	 = cursor.getColumnIndex(FotoColunas.IMOVELATLZCAD_ID);
				int fotoTipo 			 = cursor.getColumnIndex(FotoColunas.FOTOTIPO);
				int fotoPath 			 = cursor.getColumnIndex(FotoColunas.FOTOPATH);
				int ultimaAlteracao 	 = cursor.getColumnIndex(FotoColunas.FOTOULTIMAALTERACAO);
				int indicadorTransmitido = cursor.getColumnIndex(FotoColunas.INDICADORTRANSMITIDO);
				
				foto.setId(cursor.getInt(id));
				
		    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
		    	_imovelAtlzCadastral.setId(imovelAtlzCadId);
		    	foto.setImovelAtlzCadastral(_imovelAtlzCadastral);				

				foto.setFotoTipo(cursor.getInt(fotoTipo));
				foto.setFotoPath(cursor.getString(fotoPath));
				
				//testar
				Long ultimaAlteracaoMilleS = cursor.getLong(ultimaAlteracao);
				foto.setUltimaAlteracao(new Date(ultimaAlteracaoMilleS));
				// -- //
				
				foto.setIndicadorTransmitido(cursor.getInt(indicadorTransmitido));
				
				listaFoto.add(foto);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaFoto;
	}
	
	public Foto carregarEntidade(Cursor cursor) {	
		
		int id 	  	  			 = cursor.getColumnIndex(FotoColunas.ID);
		int imovelAtlzCadId 	 = cursor.getColumnIndex(FotoColunas.IMOVELATLZCAD_ID);
		int fotoTipo 			 = cursor.getColumnIndex(FotoColunas.FOTOTIPO);
		int fotoPath 			 = cursor.getColumnIndex(FotoColunas.FOTOPATH);
		int ultimaAlteracao 	 = cursor.getColumnIndex(FotoColunas.FOTOULTIMAALTERACAO);
		int indicadorTransmitido = cursor.getColumnIndex(FotoColunas.INDICADORTRANSMITIDO);
		
		Foto foto = new Foto();

		if ( cursor.moveToFirst() ) {
			
			foto.setId(cursor.getInt(id));
			
	    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
	    	_imovelAtlzCadastral.setId(cursor.getInt(imovelAtlzCadId));
	    	foto.setImovelAtlzCadastral(_imovelAtlzCadastral);
			
			foto.setFotoTipo(cursor.getInt(fotoTipo));
			foto.setFotoPath(cursor.getString(fotoPath));
			
			//testar
			Long ultimaAlteracaoMilleS = cursor.getLong(ultimaAlteracao);
			foto.setUltimaAlteracao(new Date(ultimaAlteracaoMilleS));
			// -- //
			
			foto.setIndicadorTransmitido(cursor.getInt(indicadorTransmitido));
		}

		cursor.close();
		return foto;
	}

	public ImovelAtlzCadastral getImovelAtlzCadastral() {
		return imovelAtlzCadastral;
	}

	public void setImovelAtlzCadastral(ImovelAtlzCadastral imovelAtlzCadastral) {
		this.imovelAtlzCadastral = imovelAtlzCadastral;
	}
	
	public Integer getFotoTipo() {
		return fotoTipo;
	}

	public void setFotoTipo(Integer fotoTipo) {
		this.fotoTipo = fotoTipo;
	}

	public String getFotoPath() {
		return fotoPath;
	}

	public void setFotoPath(String fotoPath) {
		this.fotoPath = fotoPath;
	}

	public Date getUltimaAlteracao() {
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao) {
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getIndicadorTransmitido() {
		return indicadorTransmitido;
	}

	public void setIndicadorTransmitido(Integer indicadorTransmitido) {
		this.indicadorTransmitido = indicadorTransmitido;
	}
	
    public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "FOTO";
	}
    
	public Foto clone() throws CloneNotSupportedException {
		return (Foto) super.clone();
	}
}
