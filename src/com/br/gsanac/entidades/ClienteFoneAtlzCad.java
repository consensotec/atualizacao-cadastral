package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto ClienteFoneAtlzCad
 * </p>
 * 
 * @author Anderson Cabral
 * @since  11/12/2012
 */
public class ClienteFoneAtlzCad extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
	private static final int CLAC_ID_INDEX    = 1;
	private static final int CLIE_ID_INDEX	   = 2;
	private static final int FNET_ID_INDEX    = 3;
    private static final int CFAC_CDDDD_INDEX  = 4;
    private static final int CFAC_NNFONE_INDEX = 5;
    private static final int CFAC_FONEPADRAO_INDEX = 6;

    private static final int AD_FNET_ID_INDEX    = 2;
    private static final int AD_CFAC_CDDDD_INDEX  = 3;
    private static final int AD_CFAC_NNFONE_INDEX = 4;
    private static final int AD_CFAC_FONEPADRAO_INDEX = 5;
    private static final int AD_CLAC_ID_INDEX    = 6;
    private static final int AD_CLIE_ID_INDEX    = 7;
    
	
    private FoneTipo foneTipo;
    private ClienteAtlzCadastral clienteAtlzCadastral;
    private Integer clienteId;
    private Integer codigoDDD;
    private Integer numeroFone;
    private Integer indicadorFonePadrao;

	public static final String[] columns = new String[]{
    	ClienteFoneAtlzCadColunas.ID,
    	ClienteFoneAtlzCadColunas.FONETIPO_ID,
    	ClienteFoneAtlzCadColunas.CLIENTEATLZCAD_ID,
    	ClienteFoneAtlzCadColunas.CLIENTE_ID,
    	ClienteFoneAtlzCadColunas.CODIGODDD,
    	ClienteFoneAtlzCadColunas.NUMEROFONE,
    	ClienteFoneAtlzCadColunas.FONE_PADRAO
    };
    
    public static final class ClienteFoneAtlzCadColunas implements BaseColumns{
    	public static final String ID 		   		 = "CFAC_ID";
    	public static final String FONETIPO_ID 		 = "FNET_ID";
    	public static final String CLIENTEATLZCAD_ID = "CLAC_ID";
    	public static final String CLIENTE_ID 		 = "CLIE_ID";
    	public static final String CODIGODDD 		 = "CFAC_CDDDD";
    	public static final String NUMEROFONE 		 = "CFAC_NNFONE";
    	public static final String FONE_PADRAO 		 = "CFAC_ICFONEPADRAO";
    }
	
    public final class ClienteFoneAtlzCadColunasTipo {
		public final String ID 		  		  = " INTEGER PRIMARY KEY AUTOINCREMENT";
		public final String FONETIPO_ID 	  = " INTEGER NOT NULL";
		public final String CLIENTEATLZCAD_ID = " INTEGER NOT NULL";
		public final String CLIENTE_ID 		  = " INTEGER ";
		public final String CODIGODDD 		  = " INTEGER ";
		public final String NUMEROFONE 		  = " INTEGER ";
		public final String FONE_PADRAO 	  = " INTEGER ";
		
		
		private String[] tipos = new String[] {ID, FONETIPO_ID, CLIENTEATLZCAD_ID,  
											CLIENTE_ID, CODIGODDD, NUMEROFONE, FONE_PADRAO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static ClienteFoneAtlzCad inserirDoArquivo(List<String> c) {
    	
    	ClienteFoneAtlzCad clienteFoneAtlzCad = new ClienteFoneAtlzCad();

        FoneTipo _foneTipo = new FoneTipo();
        _foneTipo.setId(c.get(FNET_ID_INDEX));
        clienteFoneAtlzCad.setFoneTipo(_foneTipo);
        
        ClienteAtlzCadastral _clienteAtlzCadastral = new ClienteAtlzCadastral();
        _clienteAtlzCadastral.setId(c.get(CLAC_ID_INDEX));
        clienteFoneAtlzCad.setClienteAtlzCadastral(_clienteAtlzCadastral);
        
        clienteFoneAtlzCad.setClienteId(Integer.valueOf(c.get(CLIE_ID_INDEX)));
        clienteFoneAtlzCad.setCodigoDDD(Integer.valueOf(c.get(CFAC_CDDDD_INDEX)));
        clienteFoneAtlzCad.setNumeroFone(Integer.valueOf(c.get(CFAC_NNFONE_INDEX)));
       
        if(c.get(CFAC_FONEPADRAO_INDEX) != null && !c.get(CFAC_FONEPADRAO_INDEX).equals("")){
        	clienteFoneAtlzCad.setIndicadorFonePadrao(Integer.valueOf(c.get(CFAC_FONEPADRAO_INDEX)));
        }else{
        	clienteFoneAtlzCad.setIndicadorFonePadrao(Integer.valueOf("2"));
        }
        
        return clienteFoneAtlzCad;
    }
    
  public static ClienteFoneAtlzCad inserirAtualizarDoArquivoDividido(List<String> c) {
    	
    	ClienteFoneAtlzCad clienteFoneAtlzCad = new ClienteFoneAtlzCad();

        FoneTipo _foneTipo = new FoneTipo();
        _foneTipo.setId(c.get(AD_FNET_ID_INDEX));
        clienteFoneAtlzCad.setFoneTipo(_foneTipo);
        
        ClienteAtlzCadastral _clienteAtlzCadastral = new ClienteAtlzCadastral();
        _clienteAtlzCadastral.setId(c.get(AD_CLAC_ID_INDEX));
        clienteFoneAtlzCad.setClienteAtlzCadastral(_clienteAtlzCadastral);
        
        clienteFoneAtlzCad.setClienteId(Integer.valueOf(c.get(AD_CLIE_ID_INDEX)));
        clienteFoneAtlzCad.setCodigoDDD(Integer.valueOf(c.get(AD_CFAC_CDDDD_INDEX)));
        clienteFoneAtlzCad.setNumeroFone(Integer.valueOf(c.get(AD_CFAC_NNFONE_INDEX)));
        
        if(c.get(AD_CFAC_FONEPADRAO_INDEX) != null && !c.get(AD_CFAC_FONEPADRAO_INDEX).equals("")){
        	clienteFoneAtlzCad.setIndicadorFonePadrao(Integer.valueOf(c.get(AD_CFAC_FONEPADRAO_INDEX)));
        }else{
        	clienteFoneAtlzCad.setIndicadorFonePadrao(Integer.valueOf("2"));
        }
        
        return clienteFoneAtlzCad;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(ClienteFoneAtlzCadColunas.ID, getId());
		values.put(ClienteFoneAtlzCadColunas.FONETIPO_ID, getFoneTipo().getId());
		values.put(ClienteFoneAtlzCadColunas.CLIENTEATLZCAD_ID, getClienteAtlzCadastral().getId());
		values.put(ClienteFoneAtlzCadColunas.CLIENTE_ID, getClienteId());
		values.put(ClienteFoneAtlzCadColunas.CODIGODDD, getCodigoDDD());
		values.put(ClienteFoneAtlzCadColunas.NUMEROFONE, getNumeroFone());
		values.put(ClienteFoneAtlzCadColunas.FONE_PADRAO, getIndicadorFonePadrao());

		return values;
	}
    
	public ArrayList<ClienteFoneAtlzCad> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<ClienteFoneAtlzCad> listaClienteFoneAtlzCad = new ArrayList<ClienteFoneAtlzCad>();
		
		if ( cursor.moveToFirst() ) {
		
			do{
				ClienteFoneAtlzCad clienteFoneAtlzCad = new ClienteFoneAtlzCad();
				
				int codigo 	  		 = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.ID);
				int foneTipoId 		 = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.FONETIPO_ID);
				int clienteAtlzCadastralId = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.CLIENTEATLZCAD_ID);
				int clienteId 		 = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.CLIENTE_ID);
				int codigoDDD 		 = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.CODIGODDD);
				int numeroFone 		 = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.NUMEROFONE);
				int fonePadrao 		 = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.FONE_PADRAO);
				
				clienteFoneAtlzCad.setId(cursor.getInt(codigo));
				
		        FoneTipo _foneTipo = new FoneTipo();
		        _foneTipo.setId(cursor.getInt(foneTipoId));
		        clienteFoneAtlzCad.setFoneTipo(_foneTipo);
		        
		        ClienteAtlzCadastral _clienteAtlzCadastral = new ClienteAtlzCadastral();
		        _clienteAtlzCadastral.setId(cursor.getInt(clienteAtlzCadastralId));
		        clienteFoneAtlzCad.setClienteAtlzCadastral(_clienteAtlzCadastral);
				
		        clienteFoneAtlzCad.setClienteId(cursor.getInt(clienteId));
		        clienteFoneAtlzCad.setCodigoDDD(cursor.getInt(codigoDDD));
		        clienteFoneAtlzCad.setNumeroFone(cursor.getInt(numeroFone));
		        clienteFoneAtlzCad.setIndicadorFonePadrao(cursor.getInt(fonePadrao));
		        
				listaClienteFoneAtlzCad.add(clienteFoneAtlzCad);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listaClienteFoneAtlzCad;
	}
	
	public ClienteFoneAtlzCad carregarEntidade(Cursor cursor) {	
		
		int codigo 	  		 = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.ID);
		int foneTipoId 		 = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.FONETIPO_ID);
		int clienteAtlzCadastralId = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.CLIENTEATLZCAD_ID);
		int clienteId 		 = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.CLIENTE_ID);
		int codigoDDD 		 = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.CODIGODDD);
		int numeroFone 		 = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.NUMEROFONE);
		int fonePadrao 		 = cursor.getColumnIndex(ClienteFoneAtlzCadColunas.FONE_PADRAO);
		
		ClienteFoneAtlzCad clienteFoneAtlzCad = new ClienteFoneAtlzCad();

		if ( cursor.moveToFirst() ) {
			
			clienteFoneAtlzCad.setId(cursor.getInt(codigo));
			
	        FoneTipo _foneTipo = new FoneTipo();
	        _foneTipo.setId(cursor.getInt(foneTipoId));
	        clienteFoneAtlzCad.setFoneTipo(_foneTipo);
	        
	        ClienteAtlzCadastral _clienteAtlzCadastral = new ClienteAtlzCadastral();
	        _clienteAtlzCadastral.setId(cursor.getInt(clienteAtlzCadastralId));
	        clienteFoneAtlzCad.setClienteAtlzCadastral(_clienteAtlzCadastral);
			
	        clienteFoneAtlzCad.setClienteId(cursor.getInt(clienteId));
	        clienteFoneAtlzCad.setCodigoDDD(cursor.getInt(codigoDDD));
	        clienteFoneAtlzCad.setNumeroFone(cursor.getInt(numeroFone));
	        clienteFoneAtlzCad.setIndicadorFonePadrao(cursor.getInt(fonePadrao));
		}

		cursor.close();
		return clienteFoneAtlzCad;
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
	
    public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "CLIENTE_FONE_ATLZ_CAD";
	}

	public Integer getIndicadorFonePadrao() {
		return indicadorFonePadrao;
	}

	public void setIndicadorFonePadrao(Integer indicadorFonePadrao) {
		this.indicadorFonePadrao = indicadorFonePadrao;
	}
    
}
