package com.br.gsanac.pojos;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.gsanac.utilitarios.Utilitarios;

/**
 * @author Jonathan Marcos
 * @since 04/09/2014
 */
public class Foto extends EntidadeBasica implements Cloneable{

	private static final long serialVersionUID = 1L;

	// Atributos
    private ImovelAtlzCadastral imovelAtlzCadastral;
    private Integer fotoTipo;
    private String fotoPath;
    private Integer indicadorTransmitido;
    private Date ultimaAlteracao;
    
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class FotoColuna implements BaseColumns{
    	public static final String ID = "FOTO_ID";
    	public static final String IMOVELATLZCAD_ID = "IMAC_ID";
    	public static final String FOTOTIPO = "FOTO_TIPO";
    	public static final String FOTOPATH = "FOTO_PATH";
    	public static final String INDICADORTRANSMITIDO = "FOTO_ICTRANSMITIDO";
    	public static final String FOTOULTIMAALTERACAO = "FOTO_TMULTIMAALTERACAO";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	FotoColuna.ID,
    	FotoColuna.IMOVELATLZCAD_ID,
    	FotoColuna.FOTOTIPO,
    	FotoColuna.FOTOPATH,
    	FotoColuna.FOTOULTIMAALTERACAO,
    	FotoColuna.INDICADORTRANSMITIDO
    };
	
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class FotoColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String IMOVELATLZCAD_ID = " INTEGER NOT NULL";
		private final String FOTOTIPO = " INTEGER NOT NULL";
		private final String FOTOPATH = " VARCHAR NOT NULL";
		private final String FOTOULTIMAALTERACAO  = " DATETIME NOT NULL";
		private final String INDICADORTRANSMITIDO = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {
				ID,
				IMOVELATLZCAD_ID,
				FOTOTIPO,
				FOTOPATH,
				INDICADORTRANSMITIDO,
				FOTOULTIMAALTERACAO
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
    
	// Método retorna ContentValues
    public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		values.put(FotoColuna.ID, getId());
		values.put(FotoColuna.IMOVELATLZCAD_ID, getImovelAtlzCadastral().getId());
		values.put(FotoColuna.FOTOTIPO, getFotoTipo());
		values.put(FotoColuna.FOTOPATH, getFotoPath());
		values.put(FotoColuna.INDICADORTRANSMITIDO, getIndicadorTransmitido());
		values.put(FotoColuna.FOTOULTIMAALTERACAO, Utilitarios.converterDataParaString(getUltimaAlteracao()));
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<Foto> carregarListaEntidade(Cursor cursor) {		
		ArrayList<Foto> listaFoto = new ArrayList<Foto>();
		
		if ( cursor.moveToFirst() ) {
			do{
				Foto foto = new Foto();
				int _id = cursor.getColumnIndex(FotoColuna.ID);
				int _imovelAtlzCadId = cursor.getColumnIndex(FotoColuna.IMOVELATLZCAD_ID);
				int _fotoTipo = cursor.getColumnIndex(FotoColuna.FOTOTIPO);
				int _fotoPath = cursor.getColumnIndex(FotoColuna.FOTOPATH);
				int _indicadorTransmitido = cursor.getColumnIndex(FotoColuna.INDICADORTRANSMITIDO);
				int _ultimaAlteracao = cursor.getColumnIndex(FotoColuna.FOTOULTIMAALTERACAO);
				
				foto.setId(cursor.getInt(_id));
				
		    	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
		    	imovelAtlzCadastral.setId(_imovelAtlzCadId);
		    	foto.setImovelAtlzCadastral(imovelAtlzCadastral);				

				foto.setFotoTipo(cursor.getInt(_fotoTipo));
				foto.setFotoPath(cursor.getString(_fotoPath));
				
				foto.setIndicadorTransmitido(cursor.getInt(_indicadorTransmitido));
				
				Long ultimaAlteracaoMilleS = cursor.getLong(_ultimaAlteracao);
				foto.setUltimaAlteracao(new Date(ultimaAlteracaoMilleS));
				
				listaFoto.add(foto);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaFoto;
	}
	
	// Método converte um cursor em  objeto
	public Foto carregarEntidade(Cursor cursor) {	
		
		int _id = cursor.getColumnIndex(FotoColuna.ID);
		int _imovelAtlzCadId = cursor.getColumnIndex(FotoColuna.IMOVELATLZCAD_ID);
		int _fotoTipo = cursor.getColumnIndex(FotoColuna.FOTOTIPO);
		int _fotoPath = cursor.getColumnIndex(FotoColuna.FOTOPATH);
		int _indicadorTransmitido = cursor.getColumnIndex(FotoColuna.INDICADORTRANSMITIDO);
		int _ultimaAlteracao = cursor.getColumnIndex(FotoColuna.FOTOULTIMAALTERACAO);
		
		Foto foto = new Foto();

		if ( cursor.moveToFirst() ) {
			
			foto.setId(cursor.getInt(_id));
			
	    	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
	    	imovelAtlzCadastral.setId(cursor.getInt(_imovelAtlzCadId));
	    	foto.setImovelAtlzCadastral(imovelAtlzCadastral);
			
			foto.setFotoTipo(cursor.getInt(_fotoTipo));
			foto.setFotoPath(cursor.getString(_fotoPath));
			
			foto.setIndicadorTransmitido(cursor.getInt(_indicadorTransmitido));
			
			Long ultimaAlteracaoMilleS = cursor.getLong(_ultimaAlteracao);
			foto.setUltimaAlteracao(new Date(ultimaAlteracaoMilleS));
		}

		cursor.close();
		return foto;
	}
    
	// Retorna no da tabela
    public String getNomeTabela(){
		return "FOTO";
	}
    
	public Foto clone() throws CloneNotSupportedException {
		return (Foto) super.clone();
	}
}
