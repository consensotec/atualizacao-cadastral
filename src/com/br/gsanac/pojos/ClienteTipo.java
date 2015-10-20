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
public class ClienteTipo extends EntidadeBasica {
	
	private static final long serialVersionUID = 1L;
	
	// Atributos
	private String descricao;
    private Integer indicadorPessoaJuridica;
	
    // Index de acesso
    private static final int CLTP_ID_INDEX = 1;
    private static final int CLTP_DSCLIENTETIPO_INDEX = 2;
    private static final int CLTP_ICPESSOAFISICAJURIDICA_INDEX = 3;
    
    // Constantes
    public final static Integer PARTICULARES = Integer.parseInt("25");
    
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class ClienteTipoColuna implements BaseColumns{
    	public static final String ID = "CLTP_ID";
    	public static final String DESCRICAO = "CLTP_DSCLIENTETIPO";
    	public static final String INDICADOR_PESSOA = "CLTP_ICPESSOAFISICAJURIDICA";
    }
    
    /*
     *  Atributo respons�vel por obter as 
     *  colunas usadas na cria��o
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	ClienteTipoColuna.ID,
    	ClienteTipoColuna.DESCRICAO,
    	ClienteTipoColuna.INDICADOR_PESSOA
    };
   
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class ClienteTipoColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String DESCRICAO = " VARCHAR(50) NOT NULL";
		private final String INDICADOR_PESSOA = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO, INDICADOR_PESSOA};	
		
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
	
	public Integer getIndicadorPessoa() {
		return indicadorPessoaJuridica;
	}

	public void setIndicadorPessoaJuridica(Integer indicadorPessoaJuridica) {
		this.indicadorPessoaJuridica = indicadorPessoaJuridica;
	}
    
	/*
     * Método responsável por retornar objeto
     * a partir de uma lista
     */
    public static ClienteTipo converteLinhaArquivoEmObjeto(List<String> c) {
    	ClienteTipo clienteTipo = new ClienteTipo();
    	clienteTipo.setId(c.get(CLTP_ID_INDEX));
    	clienteTipo.setDescricao(c.get(CLTP_DSCLIENTETIPO_INDEX));
    	clienteTipo.setIndicadorPessoaJuridica(Integer.valueOf(c.get(CLTP_ICPESSOAFISICAJURIDICA_INDEX)));
        return clienteTipo;
    }
    
    // Método retorna ContentValues
    public ContentValues carregarValues() {
    	ContentValues values = new ContentValues();
		values.put(ClienteTipoColuna.ID , getId());
		values.put(ClienteTipoColuna.DESCRICAO, getDescricao());
		values.put(ClienteTipoColuna.INDICADOR_PESSOA, getIndicadorPessoa());
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<ClienteTipo> carregarListaEntidade(Cursor cursor) {		
		ArrayList<ClienteTipo> listaClienteTipo = new ArrayList<ClienteTipo>();
		if ( cursor.moveToFirst() ) {
			do{
				int _id = cursor.getColumnIndex(ClienteTipoColuna.ID);
				int _descricao = cursor.getColumnIndex(ClienteTipoColuna.DESCRICAO);
				int _indicadorPessoa = cursor.getColumnIndex(ClienteTipoColuna.INDICADOR_PESSOA);
				
				ClienteTipo clienteTipo = new ClienteTipo();
	
				clienteTipo.setId(cursor.getInt(_id));
				clienteTipo.setDescricao(cursor.getString(_descricao));
				clienteTipo.setIndicadorPessoaJuridica(cursor.getInt(_indicadorPessoa));
				
				listaClienteTipo.add(clienteTipo);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listaClienteTipo;
	}
	
	// Método converte um cursor em  objeto
	public ClienteTipo carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(ClienteTipoColuna.ID);
		int _descricao = cursor.getColumnIndex(ClienteTipoColuna.DESCRICAO);
		int _indicadorPessoa = cursor.getColumnIndex(ClienteTipoColuna.INDICADOR_PESSOA);
		
		ClienteTipo clienteTipo = new ClienteTipo();

		if ( cursor.moveToFirst() ) {
			
			clienteTipo.setId(cursor.getInt(_id));
			clienteTipo.setDescricao(cursor.getString(_descricao));
			clienteTipo.setIndicadorPessoaJuridica(cursor.getInt(_indicadorPessoa));
		}

		cursor.close();
		return clienteTipo;
	}
	
	// Retorna no da tabela
    public String getNomeTabela(){
		return "CLIENTE_TIPO";
	}
    
    @Override
    public String toString(){
    	return this.descricao;
    }
}
