package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto ClienteTipo
 * </p>
 * 
 * @author Davi Menezes
 * @since  28/12/2012
 */
public class ClienteTipo extends EntidadeBase {
	
	public final static Integer PARTICULARES = new Integer("25");

	private static final long serialVersionUID = 1L;
	
    private static final int CLTP_ID_INDEX        = 1;
    private static final int CLTP_DSCLIENTETIPO_INDEX = 2;
    private static final int CLTP_ICPESSOAFISICAJURIDICA_INDEX = 3;

    private String descricao;
    
    private Integer indicadorPessoa;
    
    public static final String[] columns = new String[]{
    	ClienteTipoColunas.ID,
    	ClienteTipoColunas.DESCRICAO,
    	ClienteTipoColunas.INDICADOR_PESSOA
    };
    
    public static final class ClienteTipoColunas implements BaseColumns{
    	public static final String ID 		 		= "CLTP_ID";
    	public static final String DESCRICAO 		= "CLTP_DSCLIENTETIPO";
    	public static final String INDICADOR_PESSOA = "CLTP_ICPESSOAFISICAJURIDICA";
    }
	
    public final class ClienteTipoColunasTipo {
		public final String ID 		  		 = " INTEGER PRIMARY KEY";
		public final String DESCRICAO 		 = " VARCHAR(50) NOT NULL";
		public final String INDICADOR_PESSOA = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO, INDICADOR_PESSOA};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static ClienteTipo inserirDoArquivo(List<String> c) {
    	
    	ClienteTipo clienteTipo = new ClienteTipo();

    	clienteTipo.setId(c.get(CLTP_ID_INDEX));
    	clienteTipo.setDescricao(c.get(CLTP_DSCLIENTETIPO_INDEX));
    	clienteTipo.setIndicadorPessoa(Integer.valueOf(c.get(CLTP_ICPESSOAFISICAJURIDICA_INDEX)));
        
        return clienteTipo;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(ClienteTipoColunas.ID , getId());
		values.put(ClienteTipoColunas.DESCRICAO, getDescricao());
		values.put(ClienteTipoColunas.INDICADOR_PESSOA, getIndicadorPessoa());

		return values;
	}
    
	public ArrayList<ClienteTipo> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<ClienteTipo> listaClienteTipo = new ArrayList<ClienteTipo>();
		if ( cursor.moveToFirst() ) {
			do{
				int codigo 	  = cursor.getColumnIndex(ClienteTipoColunas.ID);
				int descricao = cursor.getColumnIndex(ClienteTipoColunas.DESCRICAO);
				int indicadorPessoa = cursor.getColumnIndex(ClienteTipoColunas.INDICADOR_PESSOA);
				
				ClienteTipo clienteTipo = new ClienteTipo();
	
				clienteTipo.setId(cursor.getInt(codigo));
				clienteTipo.setDescricao(cursor.getString(descricao));
				clienteTipo.setIndicadorPessoa(cursor.getInt(indicadorPessoa));
				
				listaClienteTipo.add(clienteTipo);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listaClienteTipo;
	}
	
	public ClienteTipo carregarEntidade(Cursor cursor) {		
		
		int codigo 	  = cursor.getColumnIndex(ClienteTipoColunas.ID);
		int descricao = cursor.getColumnIndex(ClienteTipoColunas.DESCRICAO);
		int indicadorPessoa = cursor.getColumnIndex(ClienteTipoColunas.INDICADOR_PESSOA);
		
		ClienteTipo clienteTipo = new ClienteTipo();

		if ( cursor.moveToFirst() ) {
			
			clienteTipo.setId(cursor.getInt(codigo));
			clienteTipo.setDescricao(cursor.getString(descricao));
			clienteTipo.setIndicadorPessoa(cursor.getInt(indicadorPessoa));
		}

		cursor.close();
		return clienteTipo;
	}
    
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Integer getIndicadorPessoa() {
		return indicadorPessoa;
	}

	public void setIndicadorPessoa(Integer indicadorPessoa) {
		this.indicadorPessoa = indicadorPessoa;
	}

	public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "CLIENTE_TIPO";
	}
}
