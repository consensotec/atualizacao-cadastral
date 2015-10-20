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
public class ClienteFoneAtlzCad extends EntidadeBasica {

	private static final long serialVersionUID = 1L;
	
	// Atributos
    private ClienteAtlzCadastral clienteAtlzCadastral;
    private Integer clienteId;
    private FoneTipo foneTipo;
    private Integer codigoDDD;
    private Integer numeroFone;
    private Integer indicadorFonePadrao;
	
    // Index de acesso
	private static final int CLAC_ID_INDEX = 1;
	private static final int CLIE_ID_INDEX = 2;
	private static final int FNET_ID_INDEX = 3;
    private static final int CFAC_CDDDD_INDEX = 4;
    private static final int CFAC_NNFONE_INDEX = 5;
    private static final int CFAC_FONEPADRAO_INDEX = 6;
	
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class ClienteFoneAtlzCadColuna implements BaseColumns{
    	public static final String ID = "CFAC_ID";
    	public static final String CLIENTEATLZCAD_ID = "CLAC_ID";
    	public static final String CLIENTE_ID = "CLIE_ID";
    	public static final String FONETIPO_ID = "FNET_ID";
    	public static final String CODIGODDD = "CFAC_CDDDD";
    	public static final String NUMEROFONE = "CFAC_NNFONE";
    	public static final String FONE_PADRAO = "CFAC_ICFONEPADRAO";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
	public static final String[] colunas = new String[]{
    	ClienteFoneAtlzCadColuna.ID,
    	ClienteFoneAtlzCadColuna.CLIENTEATLZCAD_ID,
    	ClienteFoneAtlzCadColuna.CLIENTE_ID,
    	ClienteFoneAtlzCadColuna.FONETIPO_ID,
    	ClienteFoneAtlzCadColuna.CODIGODDD,
    	ClienteFoneAtlzCadColuna.NUMEROFONE,
    	ClienteFoneAtlzCadColuna.FONE_PADRAO
    };
   
	// Método retorna as colunas da tabela
	public String[] getColunas(){
		return colunas;
	}
	
	/*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class ClienteFoneAtlzCadColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY AUTOINCREMENT";
		private final String CLIENTEATLZCAD_ID = " INTEGER NOT NULL";
		private final String CLIENTE_ID = " INTEGER ";
		private final String FONETIPO_ID = " INTEGER NOT NULL";
		private final String CODIGODDD = " INTEGER ";
		private final String NUMEROFONE = " INTEGER ";
		private final String FONE_PADRAO = " INTEGER ";
		
		
		private String[] tipos = new String[] {
				ID,
				CLIENTEATLZCAD_ID,
				CLIENTE_ID,
				FONETIPO_ID,
				CODIGODDD,
				NUMEROFONE,
				FONE_PADRAO
		};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
    
    public FoneTipo getFoneTipo() {
		return foneTipo;
	}

	public void setFoneTipo(FoneTipo foneTipo) {
		this.foneTipo = foneTipo;
	}

	public ClienteAtlzCadastral getClienteAtlzCadastral() {
		return clienteAtlzCadastral;
	}

	public void setClienteAtlzCadastral(ClienteAtlzCadastral clienteAtlzCadastral) {
		this.clienteAtlzCadastral = clienteAtlzCadastral;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	public Integer getCodigoDDD() {
		return codigoDDD;
	}

	public void setCodigoDDD(Integer codigoDDD) {
		this.codigoDDD = codigoDDD;
	}

	public Integer getNumeroFone() {
		return numeroFone;
	}

	public void setNumeroFone(Integer numeroFone) {
		this.numeroFone = numeroFone;
	}
	
	public Integer getIndicadorFonePadrao() {
		return indicadorFonePadrao;
	}

	public void setIndicadorFonePadrao(Integer indicadorFonePadrao) {
		this.indicadorFonePadrao = indicadorFonePadrao;
	}
    
    /*
     * Método responsável por retornar objeto
     * a partir de uma lista
     */
    public static ClienteFoneAtlzCad converteLinhaArquivoEmObjeto(List<String> c) {
    	ClienteFoneAtlzCad clienteFoneAtlzCad = new ClienteFoneAtlzCad();
    	
    	ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();
        clienteAtlzCadastral.setId(c.get(CLAC_ID_INDEX));
        clienteFoneAtlzCad.setClienteAtlzCadastral(clienteAtlzCadastral);
    	
        clienteFoneAtlzCad.setClienteId(Integer.valueOf(c.get(CLIE_ID_INDEX)));
         
        FoneTipo foneTipo = new FoneTipo();
        foneTipo.setId(c.get(FNET_ID_INDEX));
        clienteFoneAtlzCad.setFoneTipo(foneTipo);
        
        clienteFoneAtlzCad.setCodigoDDD(Integer.valueOf(c.get(CFAC_CDDDD_INDEX)));
        clienteFoneAtlzCad.setNumeroFone(Integer.valueOf(c.get(CFAC_NNFONE_INDEX)));
       
        if(c.get(CFAC_FONEPADRAO_INDEX) != null && !c.get(CFAC_FONEPADRAO_INDEX).equals("")){
        	clienteFoneAtlzCad.setIndicadorFonePadrao(Integer.valueOf(c.get(CFAC_FONEPADRAO_INDEX)));
        }else{
        	clienteFoneAtlzCad.setIndicadorFonePadrao(Integer.valueOf("2"));
        }
        
        return clienteFoneAtlzCad;
    }
    
    // Método retorna ContentValues
    public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		values.put(ClienteFoneAtlzCadColuna.ID, getId());
		values.put(ClienteFoneAtlzCadColuna.CLIENTEATLZCAD_ID, getClienteAtlzCadastral().getId());
		values.put(ClienteFoneAtlzCadColuna.CLIENTE_ID, getClienteId());
		values.put(ClienteFoneAtlzCadColuna.FONETIPO_ID, getFoneTipo().getId());
		values.put(ClienteFoneAtlzCadColuna.CODIGODDD, getCodigoDDD());
		values.put(ClienteFoneAtlzCadColuna.NUMEROFONE, getNumeroFone());
		values.put(ClienteFoneAtlzCadColuna.FONE_PADRAO, getIndicadorFonePadrao());
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<ClienteFoneAtlzCad> carregarListaEntidade(Cursor cursor) {		
		ArrayList<ClienteFoneAtlzCad> listaClienteFoneAtlzCad = new ArrayList<ClienteFoneAtlzCad>();
		
		if ( cursor.moveToFirst() ) {
		
			do{
				ClienteFoneAtlzCad clienteFoneAtlzCad = new ClienteFoneAtlzCad();
				
				int _id = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.ID);
				int _foneTipoId = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.FONETIPO_ID);
				int _clienteAtlzCadastralId = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.CLIENTEATLZCAD_ID);
				int _clienteId = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.CLIENTE_ID);
				int _codigoDDD = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.CODIGODDD);
				int _numeroFone = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.NUMEROFONE);
				int _fonePadrao = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.FONE_PADRAO);
				
				clienteFoneAtlzCad.setId(cursor.getInt(_id));
				
				ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();
		        clienteAtlzCadastral.setId(cursor.getInt(_clienteAtlzCadastralId));
		        clienteFoneAtlzCad.setClienteAtlzCadastral(clienteAtlzCadastral);
				
		        clienteFoneAtlzCad.setClienteId(cursor.getInt(_clienteId));
		        
		        FoneTipo foneTipo = new FoneTipo();
		        foneTipo.setId(cursor.getInt(_foneTipoId));
		        clienteFoneAtlzCad.setFoneTipo(foneTipo);
		        
		        clienteFoneAtlzCad.setCodigoDDD(cursor.getInt(_codigoDDD));
		        clienteFoneAtlzCad.setNumeroFone(cursor.getInt(_numeroFone));
		        clienteFoneAtlzCad.setIndicadorFonePadrao(cursor.getInt(_fonePadrao));
		        
				listaClienteFoneAtlzCad.add(clienteFoneAtlzCad);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listaClienteFoneAtlzCad;
	}
	
	// Método converte um cursor em  objeto
	public ClienteFoneAtlzCad carregarEntidade(Cursor cursor) {	
		
		int _id = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.ID);
		int _foneTipoId = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.FONETIPO_ID);
		int _clienteAtlzCadastralId = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.CLIENTEATLZCAD_ID);
		int _clienteId = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.CLIENTE_ID);
		int _codigoDDD = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.CODIGODDD);
		int _numeroFone = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.NUMEROFONE);
		int _fonePadrao = cursor.getColumnIndex(ClienteFoneAtlzCadColuna.FONE_PADRAO);
		
		ClienteFoneAtlzCad clienteFoneAtlzCad = new ClienteFoneAtlzCad();

		if ( cursor.moveToFirst() ) {
			
			clienteFoneAtlzCad.setId(cursor.getInt(_id));
			
			ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();
	        clienteAtlzCadastral.setId(cursor.getInt(_clienteAtlzCadastralId));
	        clienteFoneAtlzCad.setClienteAtlzCadastral(clienteAtlzCadastral);
			
	        clienteFoneAtlzCad.setClienteId(cursor.getInt(_clienteId));
			
	        FoneTipo foneTipo = new FoneTipo();
	        foneTipo.setId(cursor.getInt(_foneTipoId));
	        clienteFoneAtlzCad.setFoneTipo(foneTipo);
	       
	        clienteFoneAtlzCad.setCodigoDDD(cursor.getInt(_codigoDDD));
	        clienteFoneAtlzCad.setNumeroFone(cursor.getInt(_numeroFone));
	        clienteFoneAtlzCad.setIndicadorFonePadrao(cursor.getInt(_fonePadrao));
		}

		cursor.close();
		return clienteFoneAtlzCad;
	}
	
	// Retorna no da tabela
	public String getNomeTabela(){
		return "CLIENTE_FONE_ATLZ_CAD";
	}
}
