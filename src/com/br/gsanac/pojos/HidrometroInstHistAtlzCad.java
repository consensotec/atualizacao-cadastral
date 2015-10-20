package com.br.gsanac.pojos;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * @author Jonathan Marcos
 * @since 05/09/2014
 */
public class HidrometroInstHistAtlzCad extends EntidadeBasica implements Cloneable {

	private static final long serialVersionUID = 1L;
	
	// Atributos
    private ImovelAtlzCadastral imovelAtlzCadastral;
    private MedicaoTipo medicaoTipo;
    private String numeroHidrometro;
    private HidrometroLocalInst hidrometroLocalInst;
    private HidrometroProtecao  hidrometroProtecao;
    private Integer indicadorCavalete;   
    private Integer numeroInstHidrometro;
	
	// Index de acesso
    private static final int HIAC_ID_INDEX = 1;
    private static final int IMAC_ID_INDEX = 2;
    private static final int MEDT_ID_INDEX = 3;
    private static final int HIAC_NNHIDROMETRO_INDEX = 4;
    private static final int HILI_ID_INDEX = 5;
    private static final int HIPR_ID_INDEX = 6;
    private static final int IMAC_ICCAVALETE_INDEX = 7;
    private static final int HIAC_NNINSTALACAOHIDMT_INDEX = 8;
    
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class HidrometroInstHistAtlzCadColuna implements BaseColumns{
    	public static final String ID = "HIAC_ID";
    	public static final String IMOVELATLZCAD_ID = "IMAC_ID";
    	public static final String MEDICAOTIPO_ID = "MEDT_ID";
    	public static final String NUMEROHIDROMETRO = "HIAC_NNHIDROMETRO";
    	public static final String HIDROMETROLOCALINST_ID = "HILI_ID";
    	public static final String HIDROMETROPROTECAO_ID = "HIPR_ID";    	
    	public static final String INDICADORCAVALETE = "IMAC_ICCAVALETE";
    	public static final String NUMEROINSTALACAOHIDMT = "HIAC_NNINSTALACAOHIDMT";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	HidrometroInstHistAtlzCadColuna.ID,
    	HidrometroInstHistAtlzCadColuna.IMOVELATLZCAD_ID,
    	HidrometroInstHistAtlzCadColuna.MEDICAOTIPO_ID,
    	HidrometroInstHistAtlzCadColuna.NUMEROHIDROMETRO,
    	HidrometroInstHistAtlzCadColuna.HIDROMETROLOCALINST_ID,
    	HidrometroInstHistAtlzCadColuna.HIDROMETROPROTECAO_ID,
    	HidrometroInstHistAtlzCadColuna.INDICADORCAVALETE,
    	HidrometroInstHistAtlzCadColuna.NUMEROINSTALACAOHIDMT
    };
   
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
    
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class HidrometroInstHistAtlzCadColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
		private final String IMOVELATLZCAD_ID = " INTEGER NOT NULL";
		private final String MEDICAOTIPO_ID = " INTEGER";
		private final String NUMEROHIDROMETRO = " VARCHAR(20)";
		private final String HIDROMETROLOCALINST_ID = " INTEGER";
		private final String HIDROMETROPROTECAO_ID  = " INTEGER";
		private final String INDICADORCAVALETE = " INTEGER";
		private final String NUMEROINSTALACAOHIDMT  = " INTEGER";
		
		private String[] tipos = new String[] {
				ID,
				IMOVELATLZCAD_ID,
				MEDICAOTIPO_ID,
				NUMEROHIDROMETRO,
				HIDROMETROLOCALINST_ID,
				HIDROMETROPROTECAO_ID,
				INDICADORCAVALETE,
				NUMEROINSTALACAOHIDMT
		};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
    
    public MedicaoTipo getMedicaoTipo() {
		return medicaoTipo;
	}

	public void setMedicaoTipo(MedicaoTipo medicaoTipo) {
		this.medicaoTipo = medicaoTipo;
	}

	public ImovelAtlzCadastral getImovelAtlzCadastral() {
		return imovelAtlzCadastral;
	}

	public void setImovelAtlzCadastral(ImovelAtlzCadastral imovelAtlzCadastral) {
		this.imovelAtlzCadastral = imovelAtlzCadastral;
	}

	public HidrometroProtecao getHidrometroProtecao() {
		return hidrometroProtecao;
	}

	public void setHidrometroProtecao(HidrometroProtecao hidrometroProtecao) {
		this.hidrometroProtecao = hidrometroProtecao;
	}

	public HidrometroLocalInst getHidrometroLocalInst() {
		return hidrometroLocalInst;
	}

	public void setHidrometroLocalInst(HidrometroLocalInst hidrometroLocalInst) {
		this.hidrometroLocalInst = hidrometroLocalInst;
	}

	public String getNumeroHidrometro() {
		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro) {
		this.numeroHidrometro = numeroHidrometro;
	}

	public Integer getIndicadorCavalete() {
		return indicadorCavalete;
	}

	public void setIndicadorCavalete(Integer indicadorCavalete) {
		this.indicadorCavalete = indicadorCavalete;
	}

	public Integer getNumeroInstHidrometro() {
		return numeroInstHidrometro;
	}

	public void setNumeroInstHidrometro(Integer numeroInstHidrometro) {
		this.numeroInstHidrometro = numeroInstHidrometro;
	}
    
	/*
     * Método responsável por retornar objeto
     * a partir de uma lista
     */
    public static HidrometroInstHistAtlzCad converteLinhaArquivoEmObjeto(List<String> c) {
    	
    	HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();

    	hidrometroInstHistAtlzCad.setId(c.get(HIAC_ID_INDEX));
    	
    	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
    	imovelAtlzCadastral.setId(c.get(IMAC_ID_INDEX));
    	hidrometroInstHistAtlzCad.setImovelAtlzCadastral(imovelAtlzCadastral);
    	
    	MedicaoTipo medicaoTipo = new MedicaoTipo();
    	medicaoTipo.setId(c.get(MEDT_ID_INDEX));
    	hidrometroInstHistAtlzCad.setMedicaoTipo(medicaoTipo);
    	
    	hidrometroInstHistAtlzCad.setNumeroHidrometro(c.get(HIAC_NNHIDROMETRO_INDEX));
    	
        HidrometroLocalInst hidrometroLocalInst = new HidrometroLocalInst();
        hidrometroLocalInst.setId(c.get(HILI_ID_INDEX));
        hidrometroInstHistAtlzCad.setHidrometroLocalInst(hidrometroLocalInst);
    	
        HidrometroProtecao  _hidrometroProtecao  = new HidrometroProtecao();
        _hidrometroProtecao.setId(c.get(HIPR_ID_INDEX));
        hidrometroInstHistAtlzCad.setHidrometroProtecao(_hidrometroProtecao);
        
        if(c.get(IMAC_ICCAVALETE_INDEX) != null && !c.get(IMAC_ICCAVALETE_INDEX).equals("")){
        	hidrometroInstHistAtlzCad.setIndicadorCavalete(Integer.valueOf(c.get(IMAC_ICCAVALETE_INDEX)));
        }
        
        if(c.get(HIAC_NNINSTALACAOHIDMT_INDEX) != null && !c.get(HIAC_NNINSTALACAOHIDMT_INDEX).equals("")){
            hidrometroInstHistAtlzCad.setNumeroInstHidrometro(Integer.valueOf(c.get(HIAC_NNINSTALACAOHIDMT_INDEX)));
        }
        
        return hidrometroInstHistAtlzCad;
    }
    
    // Método retorna ContentValues
    public ContentValues carregarValues() {
    	ContentValues values = new ContentValues();
		values.put(HidrometroInstHistAtlzCadColuna.ID, getId());
		values.put(HidrometroInstHistAtlzCadColuna.IMOVELATLZCAD_ID, getImovelAtlzCadastral() == null ? null : getImovelAtlzCadastral().getId());
		values.put(HidrometroInstHistAtlzCadColuna.MEDICAOTIPO_ID, getMedicaoTipo() == null ? null : getMedicaoTipo().getId());
		values.put(HidrometroInstHistAtlzCadColuna.NUMEROHIDROMETRO, getNumeroHidrometro());
		values.put(HidrometroInstHistAtlzCadColuna.HIDROMETROLOCALINST_ID, getHidrometroLocalInst() == null ? null : getHidrometroLocalInst().getId());
		values.put(HidrometroInstHistAtlzCadColuna.HIDROMETROPROTECAO_ID, getHidrometroProtecao() == null ? null : getHidrometroProtecao().getId());
		values.put(HidrometroInstHistAtlzCadColuna.INDICADORCAVALETE, getIndicadorCavalete());
		values.put(HidrometroInstHistAtlzCadColuna.NUMEROINSTALACAOHIDMT, getNumeroInstHidrometro());
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<HidrometroInstHistAtlzCad> carregarListaEntidade(Cursor cursor) {		
		ArrayList<HidrometroInstHistAtlzCad> listaHidrometroInstHistAtlzCad = new ArrayList<HidrometroInstHistAtlzCad>();
		
		if ( cursor.moveToFirst() ) {
			
			do{
				HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();
				
				int _id = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.ID);
				int _imovelAtlzCadastralId = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.IMOVELATLZCAD_ID);
				int _medicaoTipoId = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.MEDICAOTIPO_ID);
				int _numeroHidrometro = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.NUMEROHIDROMETRO);
				int _hidrometroLocalInstId = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.HIDROMETROLOCALINST_ID);
				int _hidrometroProtecaoId  = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.HIDROMETROPROTECAO_ID);
				int _indicadorCavalete = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.INDICADORCAVALETE);
				int _numeroInstHidrometro = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.NUMEROINSTALACAOHIDMT);
				
				hidrometroInstHistAtlzCad.setId(cursor.getInt(_id));
				
		    	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
		    	imovelAtlzCadastral.setId(cursor.getInt(_imovelAtlzCadastralId));
		    	hidrometroInstHistAtlzCad.setImovelAtlzCadastral(imovelAtlzCadastral);
				
		    	MedicaoTipo medicaoTipo = new MedicaoTipo();
		    	medicaoTipo.setId(cursor.getInt(_medicaoTipoId));
		    	hidrometroInstHistAtlzCad.setMedicaoTipo(medicaoTipo);
		    	
		        hidrometroInstHistAtlzCad.setNumeroHidrometro(cursor.getString(_numeroHidrometro));
		        
		        HidrometroLocalInst hidrometroLocalInst = new HidrometroLocalInst();
		        hidrometroLocalInst.setId(cursor.getInt(_hidrometroLocalInstId));
		        hidrometroInstHistAtlzCad.setHidrometroLocalInst(hidrometroLocalInst);
		    	
		        HidrometroProtecao hidrometroProtecao  = new HidrometroProtecao();
		        hidrometroProtecao.setId(cursor.getInt(_hidrometroProtecaoId));
		        hidrometroInstHistAtlzCad.setHidrometroProtecao(hidrometroProtecao);
		        
		        hidrometroInstHistAtlzCad.setIndicadorCavalete(cursor.getInt(_indicadorCavalete));
		        hidrometroInstHistAtlzCad.setNumeroInstHidrometro(cursor.getInt(_numeroInstHidrometro));
		        
				listaHidrometroInstHistAtlzCad.add(hidrometroInstHistAtlzCad);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaHidrometroInstHistAtlzCad;
	}
	
	// Método converte um cursor em  objeto
	public HidrometroInstHistAtlzCad carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.ID);
		int _imovelAtlzCadastralId = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.IMOVELATLZCAD_ID);
		int _medicaoTipoId = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.MEDICAOTIPO_ID);
		int _numeroHidrometro = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.NUMEROHIDROMETRO);
		int _hidrometroLocalInstId = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.HIDROMETROLOCALINST_ID);
		int _hidrometroProtecaoId  = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.HIDROMETROPROTECAO_ID);
		int _indicadorCavalete = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.INDICADORCAVALETE);
		int _numeroInstHidrometro = cursor.getColumnIndex(HidrometroInstHistAtlzCadColuna.NUMEROINSTALACAOHIDMT);
		
		HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();

		if ( cursor.moveToFirst() ) {
			
			hidrometroInstHistAtlzCad.setId(cursor.getInt(_id));
	    	
			ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
	    	imovelAtlzCadastral.setId(cursor.getInt(_imovelAtlzCadastralId));
	    	hidrometroInstHistAtlzCad.setImovelAtlzCadastral(imovelAtlzCadastral);
			
	    	MedicaoTipo medicaoTipo = new MedicaoTipo();
	    	medicaoTipo.setId(cursor.getInt(_medicaoTipoId));
	    	hidrometroInstHistAtlzCad.setMedicaoTipo(medicaoTipo);
	    	
	        hidrometroInstHistAtlzCad.setNumeroHidrometro(cursor.getString(_numeroHidrometro));
	        
	        HidrometroLocalInst hidrometroLocalInst = new HidrometroLocalInst();
	        hidrometroLocalInst.setId(cursor.getInt(_hidrometroLocalInstId));
	        hidrometroInstHistAtlzCad.setHidrometroLocalInst(hidrometroLocalInst);
	    	
	        HidrometroProtecao hidrometroProtecao  = new HidrometroProtecao();
	        hidrometroProtecao.setId(cursor.getInt(_hidrometroProtecaoId));
	        hidrometroInstHistAtlzCad.setHidrometroProtecao(hidrometroProtecao);
	        
	        hidrometroInstHistAtlzCad.setIndicadorCavalete(cursor.getInt(_indicadorCavalete));
	        hidrometroInstHistAtlzCad.setNumeroInstHidrometro(cursor.getInt(_numeroInstHidrometro));			
		}

		cursor.close();
		return hidrometroInstHistAtlzCad;
	}
	
	// Retorna no da tabela
    public String getNomeTabela(){
		return "HIDROM_INST_HIST_ATL_CAD";
	}

	public HidrometroInstHistAtlzCad clone() throws CloneNotSupportedException {
		return (HidrometroInstHistAtlzCad) super.clone();
	}
    
}
