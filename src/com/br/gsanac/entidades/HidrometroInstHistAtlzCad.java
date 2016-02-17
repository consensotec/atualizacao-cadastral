package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto HidrometroInstHistAtlzCad
 * </p>
 * 
 * @author Anderson Cabral
 * @since  12/12/2012
 */
public class HidrometroInstHistAtlzCad extends EntidadeBase implements Cloneable {

	private static final long serialVersionUID = 1L;
	
    private static final int HIAC_ID_INDEX = 1;
    private static final int IMAC_ID_INDEX = 2;
    private static final int MEDT_ID_INDEX = 3;
    private static final int HIAC_NNHIDROMETRO_INDEX = 4;
    private static final int HILI_ID_INDEX = 5;
    private static final int HIPR_ID_INDEX = 6;
    private static final int IMAC_ICCAVALETE_INDEX 		  = 7;
    private static final int HIAC_NNINSTALACAOHIDMT_INDEX = 8;
    
    private static final int AD_IMAC_ID_INDEX = 1;
    private static final int AD_HIAC_NNHIDROMETRO_INDEX = 2;
    private static final int AD_HILI_ID_INDEX = 3;
    private static final int AD_HIPR_ID_INDEX = 4;
    private static final int AD_IMAC_ICCAVALETE_INDEX 		  = 5;
    private static final int AD_HIAC_NNINSTALACAOHIDMT_INDEX = 6;
    
    private ImovelAtlzCadastral imovelAtlzCadastral;
    private MedicaoTipo medicaoTipo;
    private String numeroHidrometro;
    private HidrometroLocalInst hidrometroLocalInst;
    private HidrometroProtecao  hidrometroProtecao;
    private Integer indicadorCavalete;   
    private Integer numeroInstHidrometro;
    
    public static final String[] columns = new String[]{
    	HidrometroInstHistAtlzCadColunas.ID,
    	HidrometroInstHistAtlzCadColunas.IMOVELATLZCAD_ID,
    	HidrometroInstHistAtlzCadColunas.MEDICAOTIPO_ID,
    	HidrometroInstHistAtlzCadColunas.NUMEROHIDROMETRO,
    	HidrometroInstHistAtlzCadColunas.HIDROMETROLOCALINST_ID,
    	HidrometroInstHistAtlzCadColunas.HIDROMETROPROTECAO_ID,
    	HidrometroInstHistAtlzCadColunas.INDICADORCAVALETE,
    	HidrometroInstHistAtlzCadColunas.NUMEROINSTALACAOHIDMT
    };
    
    public static final class HidrometroInstHistAtlzCadColunas implements BaseColumns{
    	
    	public static final String ID 					  = "HIAC_ID";
    	public static final String IMOVELATLZCAD_ID 	  = "IMAC_ID";
    	public static final String MEDICAOTIPO_ID 	  	  = "MEDT_ID";
    	public static final String NUMEROHIDROMETRO		  = "HIAC_NNHIDROMETRO";
    	public static final String HIDROMETROLOCALINST_ID = "HILI_ID";
    	public static final String HIDROMETROPROTECAO_ID  = "HIPR_ID";    	
    	public static final String INDICADORCAVALETE	  = "IMAC_ICCAVALETE";
    	public static final String NUMEROINSTALACAOHIDMT  = "HIAC_NNINSTALACAOHIDMT";
    }
	
    public final class HidrometroInstHistAtlzCadColunasTipo {
		public final String ID 		  			   = " INTEGER PRIMARY KEY AUTOINCREMENT";
		public final String IMOVELATLZCAD_ID 	   = " INTEGER NOT NULL";
		public final String MEDICAOTIPO_ID 	   	   = " INTEGER";
		public final String NUMEROHIDROMETRO	   = " VARCHAR(20)";
		public final String HIDROMETROLOCALINST_ID = " INTEGER";
		public final String HIDROMETROPROTECAO_ID  = " INTEGER";
    	public final String INDICADORCAVALETE	   = " INTEGER NOT NULL";
    	public final String NUMEROINSTALACAOHIDMT  = " INTEGER";
		
		private String[] tipos = new String[] {ID, IMOVELATLZCAD_ID, MEDICAOTIPO_ID, NUMEROHIDROMETRO,
												HIDROMETROLOCALINST_ID, HIDROMETROPROTECAO_ID, 
												INDICADORCAVALETE, NUMEROINSTALACAOHIDMT};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static HidrometroInstHistAtlzCad inserirDoArquivo(List<String> c) {
    	
    	HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();

    	hidrometroInstHistAtlzCad.setId(c.get(HIAC_ID_INDEX));
    	
    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
    	_imovelAtlzCadastral.setId(c.get(IMAC_ID_INDEX));
    	hidrometroInstHistAtlzCad.setImovelAtlzCadastral(_imovelAtlzCadastral);
    	
    	MedicaoTipo _medicaoTipo = new MedicaoTipo();
    	_medicaoTipo.setId(c.get(MEDT_ID_INDEX));
    	hidrometroInstHistAtlzCad.setMedicaoTipo(_medicaoTipo);
    	
    	hidrometroInstHistAtlzCad.setNumeroHidrometro(c.get(HIAC_NNHIDROMETRO_INDEX));
    	
        HidrometroLocalInst _hidrometroLocalInst = new HidrometroLocalInst();
        _hidrometroLocalInst.setId(c.get(HILI_ID_INDEX));
        hidrometroInstHistAtlzCad.setHidrometroLocalInst(_hidrometroLocalInst);
    	
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
    
    
 public static HidrometroInstHistAtlzCad inserirAtualizarDoArquivoDividido(List<String> c, Long idImovelAtlzCad) {
    	
    	HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();

    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
    	_imovelAtlzCadastral.setId(idImovelAtlzCad.intValue());
    	hidrometroInstHistAtlzCad.setImovelAtlzCadastral(_imovelAtlzCadastral);
    	
    	MedicaoTipo _medicaoTipo = new MedicaoTipo();
    	_medicaoTipo.setId(1);
    	hidrometroInstHistAtlzCad.setMedicaoTipo(_medicaoTipo);
    	
    	hidrometroInstHistAtlzCad.setNumeroHidrometro(c.get(AD_HIAC_NNHIDROMETRO_INDEX));
    	
        HidrometroLocalInst _hidrometroLocalInst = new HidrometroLocalInst();
        _hidrometroLocalInst.setId(c.get(AD_HILI_ID_INDEX));
        hidrometroInstHistAtlzCad.setHidrometroLocalInst(_hidrometroLocalInst);
    	
        HidrometroProtecao  _hidrometroProtecao  = new HidrometroProtecao();
        _hidrometroProtecao.setId(c.get(AD_HIPR_ID_INDEX));
        hidrometroInstHistAtlzCad.setHidrometroProtecao(_hidrometroProtecao);
        
        hidrometroInstHistAtlzCad.setIndicadorCavalete(Integer.valueOf(c.get(AD_IMAC_ICCAVALETE_INDEX)));
        hidrometroInstHistAtlzCad.setNumeroInstHidrometro(Integer.valueOf(c.get(AD_HIAC_NNINSTALACAOHIDMT_INDEX)));
               
        return hidrometroInstHistAtlzCad;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(HidrometroInstHistAtlzCadColunas.ID, getId());
		values.put(HidrometroInstHistAtlzCadColunas.IMOVELATLZCAD_ID, getImovelAtlzCadastral().getId());
		values.put(HidrometroInstHistAtlzCadColunas.MEDICAOTIPO_ID, getMedicaoTipo().getId());
		values.put(HidrometroInstHistAtlzCadColunas.NUMEROHIDROMETRO, getNumeroHidrometro());
		values.put(HidrometroInstHistAtlzCadColunas.HIDROMETROLOCALINST_ID, getHidrometroLocalInst().getId());
		values.put(HidrometroInstHistAtlzCadColunas.HIDROMETROPROTECAO_ID, getHidrometroProtecao().getId());
		values.put(HidrometroInstHistAtlzCadColunas.INDICADORCAVALETE, getIndicadorCavalete());
		values.put(HidrometroInstHistAtlzCadColunas.NUMEROINSTALACAOHIDMT, getNumeroInstHidrometro());


		return values;
	}
    
	public ArrayList<HidrometroInstHistAtlzCad> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<HidrometroInstHistAtlzCad> listaHidrometroInstHistAtlzCad = new ArrayList<HidrometroInstHistAtlzCad>();
		if ( cursor.moveToFirst() ) {
			
			do{
				HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();
				
				int _codigo 	  		   = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.ID);
				int _imovelAtlzCadastralId = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.IMOVELATLZCAD_ID);
				int _medicaoTipoId 		   = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.MEDICAOTIPO_ID);
				int _numeroHidrometro  	   = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.NUMEROHIDROMETRO);
				int _hidrometroLocalInstId = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.HIDROMETROLOCALINST_ID);
				int _hidrometroProtecaoId  = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.HIDROMETROPROTECAO_ID);
				int _indicadorCavalete 	   = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.INDICADORCAVALETE);
				int _numeroInstHidrometro  = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.NUMEROINSTALACAOHIDMT);
				
				hidrometroInstHistAtlzCad.setId(cursor.getInt(_codigo));
				
		    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
		    	_imovelAtlzCadastral.setId(cursor.getInt(_imovelAtlzCadastralId));
		    	hidrometroInstHistAtlzCad.setImovelAtlzCadastral(_imovelAtlzCadastral);
				
		    	MedicaoTipo _medicaoTipo = new MedicaoTipo();
		    	_medicaoTipo.setId(cursor.getInt(_medicaoTipoId));
		    	hidrometroInstHistAtlzCad.setMedicaoTipo(_medicaoTipo);
		    	
		        hidrometroInstHistAtlzCad.setNumeroHidrometro(cursor.getString(_numeroHidrometro));
		        
		        HidrometroLocalInst _hidrometroLocalInst = new HidrometroLocalInst();
		        _hidrometroLocalInst.setId(cursor.getInt(_hidrometroLocalInstId));
		        hidrometroInstHistAtlzCad.setHidrometroLocalInst(_hidrometroLocalInst);
		    	
		        HidrometroProtecao  _hidrometroProtecao  = new HidrometroProtecao();
		        _hidrometroProtecao.setId(cursor.getInt(_hidrometroProtecaoId));
		        hidrometroInstHistAtlzCad.setHidrometroProtecao(_hidrometroProtecao);
		        
		        hidrometroInstHistAtlzCad.setIndicadorCavalete(cursor.getInt(_indicadorCavalete));
		        hidrometroInstHistAtlzCad.setNumeroInstHidrometro(cursor.getInt(_numeroInstHidrometro));
		        
				listaHidrometroInstHistAtlzCad.add(hidrometroInstHistAtlzCad);
				
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaHidrometroInstHistAtlzCad;
	}
	
	public HidrometroInstHistAtlzCad carregarEntidade(Cursor cursor) {		
		
		int _codigo 	  		   = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.ID);
		int _imovelAtlzCadastralId = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.IMOVELATLZCAD_ID);
		int _medicaoTipoId 		   = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.MEDICAOTIPO_ID);
		int _numeroHidrometro  	   = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.NUMEROHIDROMETRO);
		int _hidrometroLocalInstId = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.HIDROMETROLOCALINST_ID);
		int _hidrometroProtecaoId  = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.HIDROMETROPROTECAO_ID);
		int _indicadorCavalete 	   = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.INDICADORCAVALETE);
		int _numeroInstHidrometro  = cursor.getColumnIndex(HidrometroInstHistAtlzCadColunas.NUMEROINSTALACAOHIDMT);
		
		HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad = new HidrometroInstHistAtlzCad();

		if ( cursor.moveToFirst() ) {
			
			hidrometroInstHistAtlzCad.setId(cursor.getInt(_codigo));
	    	
	    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
	    	_imovelAtlzCadastral.setId(cursor.getInt(_imovelAtlzCadastralId));
	    	hidrometroInstHistAtlzCad.setImovelAtlzCadastral(_imovelAtlzCadastral);
	    	
	    	MedicaoTipo _medicaoTipo = new MedicaoTipo();
	    	_medicaoTipo.setId(cursor.getInt(_medicaoTipoId));
	    	hidrometroInstHistAtlzCad.setMedicaoTipo(_medicaoTipo);
			
	        hidrometroInstHistAtlzCad.setNumeroHidrometro(cursor.getString(_numeroHidrometro));
	        
	        HidrometroLocalInst _hidrometroLocalInst = new HidrometroLocalInst();
	        _hidrometroLocalInst.setId(cursor.getInt(_hidrometroLocalInstId));
	        hidrometroInstHistAtlzCad.setHidrometroLocalInst(_hidrometroLocalInst);
	    	
	        HidrometroProtecao  _hidrometroProtecao  = new HidrometroProtecao();
	        _hidrometroProtecao.setId(cursor.getInt(_hidrometroProtecaoId));
	        hidrometroInstHistAtlzCad.setHidrometroProtecao(_hidrometroProtecao);
	        
	        hidrometroInstHistAtlzCad.setIndicadorCavalete(cursor.getInt(_indicadorCavalete));
	        hidrometroInstHistAtlzCad.setNumeroInstHidrometro(cursor.getInt(_numeroInstHidrometro));			
		}

		cursor.close();
		return hidrometroInstHistAtlzCad;
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

	public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "HIDROM_INST_HIST_ATL_CAD";
	}

	public HidrometroInstHistAtlzCad clone() throws CloneNotSupportedException {
		return (HidrometroInstHistAtlzCad) super.clone();
	}
    
}
