package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto CadastroOcorrencia
 * </p>
 * 
 * @author Anderson Cabral
 * @since  13/12/2012
 */
public class CadastroOcorrencia extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int COCR_ID_INDEX         			 = 1;
    private static final int COCR_DSCADASTROOCORRENCIA_INDEX = 2;
    private static final int COCR_ICCAMPOSOBRIGTABLET_INDEX = 3;
    
    //Constantes
  	public final static int SEM_OCORRENCIAS = 1; 	
  	public final static int CLIENTE_NAO_PERMITIU_ACESSO = 2;
  	public final static int CLIENTE_NAO_PODE_RESPONDER = 3;
  	public final static int IMOVEL_NAO_VISITADO = 8;
  	public final static int IMOVEL_FECHADO = 10;
  	public final static int ANIMAL_BRAVO = 11;
  	public final static int IMOVEL_NAO_LOCALIZADO = 16;
  	public final static int IMOVEL_DEMOLIDO = 26;
  	public final static int IMOVEL_DESOCUPADO = 27;

    private String descricao;
    private Short indicadorCampoObrigatorio;
    
    public static final String[] columns = new String[]{
    	CadastroOcorrenciaColunas.ID,
    	CadastroOcorrenciaColunas.DESCRICAO,
    	CadastroOcorrenciaColunas.INDICADOR_CAMPO_OBRIGATORIO
    };
    
    public static final class CadastroOcorrenciaColunas implements BaseColumns{
    	public static final String ID 		 = "COCR_ID";
    	public static final String DESCRICAO = "COCR_DSCADASTROOCORRENCIA";
    	public static final String INDICADOR_CAMPO_OBRIGATORIO = "COCR_ICCAMPOSOBRIGTABLET";
    }
	
    public final class CadastroOcorrenciaColunasTipo {
		public final String ID 		  = " INTEGER PRIMARY KEY";
		public final String DESCRICAO = " VARCHAR(25) NOT NULL";
		public final String INDICADOR_CAMPO_OBRIGATORIO = " INTEGER NOT NULL";
		
		private String[] tipos = new String[] {ID, DESCRICAO, INDICADOR_CAMPO_OBRIGATORIO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static CadastroOcorrencia inserirDoArquivo(List<String> c) {
    	
    	CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();

    	cadastroOcorrencia.setId(c.get(COCR_ID_INDEX));
    	cadastroOcorrencia.setDescricao(c.get(COCR_DSCADASTROOCORRENCIA_INDEX));
    	cadastroOcorrencia.setIndicadorCampoObrigatorio(Short.valueOf(c.get(COCR_ICCAMPOSOBRIGTABLET_INDEX)));
        
        return cadastroOcorrencia;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(CadastroOcorrenciaColunas.ID, getId());
		values.put(CadastroOcorrenciaColunas.DESCRICAO, getDescricao());
		values.put(CadastroOcorrenciaColunas.INDICADOR_CAMPO_OBRIGATORIO, getIndicadorCampoObrigatorio());
		
		return values;
	}
    
	public ArrayList<CadastroOcorrencia> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<CadastroOcorrencia> listaCadastroOcorrencia = new ArrayList<CadastroOcorrencia>();
		
		if ( cursor.moveToFirst() ) {
			do{
				CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();
				int codigo 	  = cursor.getColumnIndex(CadastroOcorrenciaColunas.ID);
				int descricao = cursor.getColumnIndex(CadastroOcorrenciaColunas.DESCRICAO);
				int indicadorCampoObrigatorio = cursor.getColumnIndex(CadastroOcorrenciaColunas.INDICADOR_CAMPO_OBRIGATORIO);
				
				cadastroOcorrencia.setId(cursor.getInt(codigo));
				cadastroOcorrencia.setDescricao(cursor.getString(descricao));
				cadastroOcorrencia.setIndicadorCampoObrigatorio(cursor.getShort(indicadorCampoObrigatorio));
				
				listaCadastroOcorrencia.add(cadastroOcorrencia);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listaCadastroOcorrencia;
	}
	
	public CadastroOcorrencia carregarEntidade(Cursor cursor) {		
		
		int codigo 	  = cursor.getColumnIndex(CadastroOcorrenciaColunas.ID);
		int descricao = cursor.getColumnIndex(CadastroOcorrenciaColunas.DESCRICAO);
		int indicadorCampoObrigatorio = cursor.getColumnIndex(CadastroOcorrenciaColunas.INDICADOR_CAMPO_OBRIGATORIO);
		
		CadastroOcorrencia cadastroOcorrencia = new CadastroOcorrencia();

		if ( cursor.moveToFirst() ) {
			
			cadastroOcorrencia.setId(cursor.getInt(codigo));
			cadastroOcorrencia.setDescricao(cursor.getString(descricao));
			cadastroOcorrencia.setIndicadorCampoObrigatorio(cursor.getShort(indicadorCampoObrigatorio));
		}
		
		cursor.close();
		return cadastroOcorrencia;
	}
    
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Short getIndicadorCampoObrigatorio() {
		return indicadorCampoObrigatorio;
	}

	public void setIndicadorCampoObrigatorio(Short indicadorCampoObrigatorio) {
		this.indicadorCampoObrigatorio = indicadorCampoObrigatorio;
	}

	public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "CADASTRO_OCORRENCIA";
	}
}
