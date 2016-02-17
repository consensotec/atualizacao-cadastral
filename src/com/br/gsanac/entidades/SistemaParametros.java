package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;


/**
 * <p>
 * Classe responsável pelo objeto Sistema Parametro
 * </p>
 * 
 * @author Arthur Carvalho
 * @since 06/12/2012
 */
public class SistemaParametros extends EntidadeBase {

    private static final long    serialVersionUID     = 4327285475536305741L;

	private static final int PARM_NMLOGIN_INDEX = 1;
	private static final int PARM_SENHA_INDEX = 2;
	private static final int PARM_NNIMEI_INDEX = 3;
	private static final int PARM_NNVERSAO_INDEX = 4;
	private static final int LOCA_ID_INDEX = 5;
	private static final int LOCA_DSLOCALIDADE_INDEX = 6;
	private static final int STCM_CDSETORCOMERCIAL_INDEX = 7;
	private static final int STCM_DSSETORCOMERCIAL_INDEX = 8;
	private static final int PARM_COMANDO_INDEX = 9;
	private static final int PARM_QTDIMOVEL_INDEX = 10;
	private static final int PARM_DESCRICAOARQUIVO_INDEX = 11;
	private static final int PARM_IDEMPRESA_INDEX = 12;


    private String login;
    private String senha;
    private String imei;
    private String numeroVersao;
    private String idLocalidade;
    private String descricaoLocalidade;
    private String idCodigoSetorComercial;
    private String descricaoSetorComercial;
    private Integer idComando;
    private String quantidadeImovel;
    private String descricaoArquivo;
    private Integer indicadorRoteiroFinalizado;
    private Integer indicadorMapa;
    private Integer idEmpresa;
    private Integer indicadorArquivoCarregado;
    

    public static final String[] colunas              = new String[] {
    	SistemaParametrosColunas.ID,
    	SistemaParametrosColunas.LOGIN,
    	SistemaParametrosColunas.SENHA,
    	SistemaParametrosColunas.IMEI,
    	SistemaParametrosColunas.NUMERO_VERSAO,
    	SistemaParametrosColunas.LOCALIDADE,
    	SistemaParametrosColunas.LOCALIDADE_DESCRICAO,
    	SistemaParametrosColunas.SETOR_COMERCIAL,
    	SistemaParametrosColunas.SETOR_COMERCIAL_DESCRICAO,
    	SistemaParametrosColunas.COMANDO,
    	SistemaParametrosColunas.QUANTIDADE_IMOVEL,
    	SistemaParametrosColunas.INDICADOR_FINALIZADO,
    	SistemaParametrosColunas.INDICADOR_MAPA,
    	SistemaParametrosColunas.DESCRICAO_ARQUIVO,
    	SistemaParametrosColunas.ID_EMPRESA,
    	SistemaParametrosColunas.INDICADOR_ARQUIVO_CARREGADO
    	};

    public static final class SistemaParametrosColunas implements BaseColumns {
        public static final String ID = "PARM_ID";
        public static final String LOGIN = "PARM_NMLOGIN";
        public static final String SENHA = "PARM_SENHA";
        public static final String IMEI = "PARM_NNIMEI";
        public static final String NUMERO_VERSAO = "PARM_NNVERSAO";
        public static final String LOCALIDADE = "LOCA_ID";
        public static final String LOCALIDADE_DESCRICAO = "LOCA_DSLOCALIDADE";
        public static final String SETOR_COMERCIAL = "STCM_CDSETORCOMERCIAL";
        public static final String SETOR_COMERCIAL_DESCRICAO = "STCM_DSSETORCOMERCIAL";
        public static final String COMANDO = "PARM_COMANDO";
        public static final String QUANTIDADE_IMOVEL = "PARM_QTDIMOVEL";
        public static final String INDICADOR_FINALIZADO = "PARM_INDICADORFINALIZADO";
        public static final String INDICADOR_MAPA = "PARM_INDICADORMAPA";
        public static final String DESCRICAO_ARQUIVO = "PARM_DESCRICAOARQUIVO";
        public static final String ID_EMPRESA = "PARM_IDEMPRESA";
        public static final String INDICADOR_ARQUIVO_CARREGADO = "PARM_ICARQUIVOCARREGADO";
        
    }
    
    public String[] getColunas(){
		return colunas;
	}
    
    public String getNomeTabela(){
		return "SISTEMA_PARAMETROS";
	}
    
    public final class SistemaParametrosColunasTipo {
		public final String ID = " INTEGER PRIMARY KEY";
		public final String LOGIN = " VARCHAR(11) NOT NULL";
		public final String SENHA = " VARCHAR(40) ";
		public final String IMEI = " VARCHAR(15) NOT NULL";
		public final String NUMERO_VERSAO = " VARCHAR(10) NOT NULL";
		public final String LOCALIDADE = " INTEGER ";
		public final String LOCALIDADE_DESCRICAO = " VARCHAR(25) NOT NULL ";
		public final String SETOR_COMERCIAL = " INTEGER ";
		public final String SETOR_COMERCIAL_DESCRICAO = " VARCHAR(25) NOT NULL ";
		public final String COMANDO = " INTEGER ";
		public final String QUANTIDADE_IMOVEL = " VARCHAR(15) NOT NULL";
		public final String INDICADOR_FINALIZADO = " INTEGER ";
		public final String INDICADOR_MAPA = " INTEGER ";
		public final String DESCRICAO_ARQUIVO = " VARCHAR(30) ";
		public final String ID_EMPRESA = " INTEGER ";
		public final String INDICADOR_ARQUIVO_CARREGADO = " INTEGER ";
		
		
		private String[] tipos = new String[] {ID, LOGIN, SENHA, IMEI, NUMERO_VERSAO,LOCALIDADE, LOCALIDADE_DESCRICAO,
				SETOR_COMERCIAL, SETOR_COMERCIAL_DESCRICAO, COMANDO, QUANTIDADE_IMOVEL, INDICADOR_FINALIZADO, INDICADOR_MAPA, 
				DESCRICAO_ARQUIVO, ID_EMPRESA, INDICADOR_ARQUIVO_CARREGADO};	
		
		public String[] getTipos(){
			return tipos;
		}
	}

    public static SistemaParametros inserirDoArquivo(List<String> c, String cpfLogin) {
    	
    	SistemaParametros sistemaParametros = new SistemaParametros();

    	sistemaParametros.setId(1);
    	if ( cpfLogin != null && !cpfLogin.equals("") ) {
    		sistemaParametros.setLogin(cpfLogin);
    	} else {
    		sistemaParametros.setLogin(c.get(PARM_NMLOGIN_INDEX));
    		sistemaParametros.setSenha(c.get(PARM_SENHA_INDEX));
    	}
    	
    	sistemaParametros.setImei(c.get(PARM_NNIMEI_INDEX));
    	sistemaParametros.setNumeroVersao(c.get(PARM_NNVERSAO_INDEX));
    	sistemaParametros.setIdLocalidade(c.get(LOCA_ID_INDEX));
    	sistemaParametros.setDescricaoLocalidade(c.get(LOCA_DSLOCALIDADE_INDEX));
    	sistemaParametros.setIdCodigoSetorComercial(c.get(STCM_CDSETORCOMERCIAL_INDEX));
    	sistemaParametros.setDescricaoSetorComercial(c.get(STCM_DSSETORCOMERCIAL_INDEX));
    	sistemaParametros.setIdComando(Util.verificarNuloInt(c.get(PARM_COMANDO_INDEX)));
    	sistemaParametros.setQuantidadeImovel(c.get(PARM_QTDIMOVEL_INDEX));
    	sistemaParametros.setIndicadorRoteiroFinalizado(ConstantesSistema.PENDENTE);
    	sistemaParametros.setIndicadorMapa(ConstantesSistema.SIM);
    	sistemaParametros.setDescricaoArquivo(c.get(PARM_DESCRICAOARQUIVO_INDEX));
    	sistemaParametros.setIdEmpresa(Util.verificarNuloInt(c.get(PARM_IDEMPRESA_INDEX)));

    	//aarquivo carregado incompleto.
    	sistemaParametros.setIndicadorArquivoCarregado(Integer.valueOf(2));
    	
    	
        return sistemaParametros;
    }

  
    public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getNumeroVersao() {
		return numeroVersao;
	}

	public void setNumeroVersao(String numeroVersao) {
		this.numeroVersao = numeroVersao;
	}
	
	public String getIdLocalidade() {
		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade) {
		this.idLocalidade = idLocalidade;
	}

	public String getDescricaoLocalidade() {
		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade) {
		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getIdCodigoSetorComercial() {
		return idCodigoSetorComercial;
	}

	public void setIdCodigoSetorComercial(String idCodigoSetorComercial) {
		this.idCodigoSetorComercial = idCodigoSetorComercial;
	}

	public String getDescricaoSetorComercial() {
		return descricaoSetorComercial;
	}

	public void setDescricaoSetorComercial(String descricaoSetorComercial) {
		this.descricaoSetorComercial = descricaoSetorComercial;
	}

	public String getQuantidadeImovel() {
		return quantidadeImovel;
	}

	public void setQuantidadeImovel(String quantidadeImovel) {
		this.quantidadeImovel = quantidadeImovel;
	}

	public Integer getIdComando() {
		return idComando;
	}

	public void setIdComando(Integer idComando) {
		this.idComando = idComando;
	}
	
	public Integer getIndicadorRoteiroFinalizado() {
		return indicadorRoteiroFinalizado;
	}

	public void setIndicadorRoteiroFinalizado(Integer indicadorRoteiroFinalizado) {
		this.indicadorRoteiroFinalizado = indicadorRoteiroFinalizado;
	}

	public Integer getIndicadorMapa() {
		return indicadorMapa;
	}

	public void setIndicadorMapa(Integer indicadorMapa) {
		this.indicadorMapa = indicadorMapa;
	}

	public String getDescricaoArquivo() {
		return descricaoArquivo;
	}

	public void setDescricaoArquivo(String descricaoArquivo) {
		this.descricaoArquivo = descricaoArquivo;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	
	public Integer getIndicadorArquivoCarregado() {
		return indicadorArquivoCarregado;
	}

	public void setIndicadorArquivoCarregado(Integer indicadorArquivoCarregado) {
		this.indicadorArquivoCarregado = indicadorArquivoCarregado;
	}

	public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(SistemaParametrosColunas.ID, getId());
		values.put(SistemaParametrosColunas.LOGIN, getLogin());
		values.put(SistemaParametrosColunas.SENHA, getSenha());
		values.put(SistemaParametrosColunas.IMEI, getImei());
		values.put(SistemaParametrosColunas.NUMERO_VERSAO, getNumeroVersao());
		values.put(SistemaParametrosColunas.LOCALIDADE, getIdLocalidade());
		values.put(SistemaParametrosColunas.LOCALIDADE_DESCRICAO, getDescricaoLocalidade());
		values.put(SistemaParametrosColunas.SETOR_COMERCIAL, getIdCodigoSetorComercial());
		values.put(SistemaParametrosColunas.SETOR_COMERCIAL_DESCRICAO, getDescricaoSetorComercial());
		values.put(SistemaParametrosColunas.COMANDO, getIdComando());
		values.put(SistemaParametrosColunas.QUANTIDADE_IMOVEL, getQuantidadeImovel());
		values.put(SistemaParametrosColunas.INDICADOR_FINALIZADO, getIndicadorRoteiroFinalizado());
		values.put(SistemaParametrosColunas.INDICADOR_MAPA, getIndicadorMapa());
		values.put(SistemaParametrosColunas.DESCRICAO_ARQUIVO, getDescricaoArquivo());
		values.put(SistemaParametrosColunas.ID_EMPRESA, getIdEmpresa());
		values.put(SistemaParametrosColunas.INDICADOR_ARQUIVO_CARREGADO, getIndicadorArquivoCarregado());

		return values;
	}
	
	public ArrayList<SistemaParametros> carregarListaEntidade(Cursor cursor) {		
			
		ArrayList<SistemaParametros> listaSistemaParametros = new ArrayList<SistemaParametros>();
		cursor.moveToFirst();
		
		do{
			int codigo = cursor.getColumnIndex(SistemaParametrosColunas.ID);
			int login = cursor.getColumnIndex(SistemaParametrosColunas.LOGIN);
			int senha = cursor.getColumnIndex(SistemaParametrosColunas.SENHA);
			int imei = cursor.getColumnIndex(SistemaParametrosColunas.IMEI);
			int versao = cursor.getColumnIndex(SistemaParametrosColunas.NUMERO_VERSAO);
			int loca = cursor.getColumnIndex(SistemaParametrosColunas.LOCALIDADE);
			int dsloca = cursor.getColumnIndex(SistemaParametrosColunas.LOCALIDADE_DESCRICAO);
			int setor = cursor.getColumnIndex(SistemaParametrosColunas.SETOR_COMERCIAL);
			int dsSetor = cursor.getColumnIndex(SistemaParametrosColunas.SETOR_COMERCIAL_DESCRICAO);
			int comando = cursor.getColumnIndex(SistemaParametrosColunas.COMANDO);
			int quantidade = cursor.getColumnIndex(SistemaParametrosColunas.QUANTIDADE_IMOVEL);
			int indicadorFinalizado = cursor.getColumnIndex(SistemaParametrosColunas.INDICADOR_FINALIZADO);
			int indicadorMapa = cursor.getColumnIndex(SistemaParametrosColunas.INDICADOR_MAPA);
			int descricaoArquivo = cursor.getColumnIndex(SistemaParametrosColunas.DESCRICAO_ARQUIVO);
			int idEmpresa = cursor.getColumnIndex(SistemaParametrosColunas.ID_EMPRESA);
			int indicadorArquivoCarregado = cursor.getColumnIndex(SistemaParametrosColunas.INDICADOR_ARQUIVO_CARREGADO);
			
			SistemaParametros sistemaParametros = new SistemaParametros();
			
			sistemaParametros.setId(cursor.getInt(codigo));
			sistemaParametros.setLogin(cursor.getString(login));
			sistemaParametros.setSenha(cursor.getString(senha));
			sistemaParametros.setImei(cursor.getString(imei));
			sistemaParametros.setNumeroVersao(cursor.getString(versao));
			sistemaParametros.setIdLocalidade(cursor.getString(loca));
			sistemaParametros.setDescricaoLocalidade(cursor.getString(dsloca));
			sistemaParametros.setIdCodigoSetorComercial(cursor.getString(setor));
			sistemaParametros.setDescricaoSetorComercial(cursor.getString(dsSetor));
			sistemaParametros.setIdComando(cursor.getInt(comando));
			sistemaParametros.setQuantidadeImovel(cursor.getString(quantidade));
			sistemaParametros.setIndicadorRoteiroFinalizado(cursor.getInt(indicadorFinalizado));
			sistemaParametros.setIndicadorMapa(cursor.getInt(indicadorMapa));
			sistemaParametros.setDescricaoArquivo(cursor.getString(descricaoArquivo));
			sistemaParametros.setIdEmpresa(cursor.getInt(idEmpresa));
			sistemaParametros.setIndicadorArquivoCarregado(cursor.getInt(indicadorArquivoCarregado));
			
			listaSistemaParametros.add(sistemaParametros);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return listaSistemaParametros;
	}
	
	public SistemaParametros carregarEntidade(Cursor cursor) {		
		
		int codigo = cursor.getColumnIndex(SistemaParametrosColunas.ID);
		int login = cursor.getColumnIndex(SistemaParametrosColunas.LOGIN);
		int senha = cursor.getColumnIndex(SistemaParametrosColunas.SENHA);
		int imei = cursor.getColumnIndex(SistemaParametrosColunas.IMEI);
		int versao = cursor.getColumnIndex(SistemaParametrosColunas.NUMERO_VERSAO);
		int loca = cursor.getColumnIndex(SistemaParametrosColunas.LOCALIDADE);
		int dsloca = cursor.getColumnIndex(SistemaParametrosColunas.LOCALIDADE_DESCRICAO);
		int setor = cursor.getColumnIndex(SistemaParametrosColunas.SETOR_COMERCIAL);
		int dsSetor = cursor.getColumnIndex(SistemaParametrosColunas.SETOR_COMERCIAL_DESCRICAO);
		int quantidade = cursor.getColumnIndex(SistemaParametrosColunas.QUANTIDADE_IMOVEL);
		int comando = cursor.getColumnIndex(SistemaParametrosColunas.COMANDO);
		int indicadorFinalizado = cursor.getColumnIndex(SistemaParametrosColunas.INDICADOR_FINALIZADO);
		int indicadorMapa = cursor.getColumnIndex(SistemaParametrosColunas.INDICADOR_MAPA);
		int descricaoArquivo = cursor.getColumnIndex(SistemaParametrosColunas.DESCRICAO_ARQUIVO);
		int idEmpresa = cursor.getColumnIndex(SistemaParametrosColunas.ID_EMPRESA);
		int indicadorArquivoCarregado = cursor.getColumnIndex(SistemaParametrosColunas.INDICADOR_ARQUIVO_CARREGADO);
		
		SistemaParametros sistemaParametros = new SistemaParametros();

		if ( cursor.moveToFirst() ) {
			
			sistemaParametros.setId(cursor.getInt(codigo));
			sistemaParametros.setLogin(cursor.getString(login));
			sistemaParametros.setSenha(cursor.getString(senha));
			sistemaParametros.setImei(cursor.getString(imei));
			sistemaParametros.setNumeroVersao(cursor.getString(versao));
			sistemaParametros.setIdLocalidade(cursor.getString(loca));
			sistemaParametros.setDescricaoLocalidade(cursor.getString(dsloca));
			sistemaParametros.setIdCodigoSetorComercial(cursor.getString(setor));
			sistemaParametros.setDescricaoSetorComercial(cursor.getString(dsSetor));
			sistemaParametros.setQuantidadeImovel(cursor.getString(quantidade));
			sistemaParametros.setIdComando(cursor.getInt(comando));
			sistemaParametros.setIndicadorRoteiroFinalizado(cursor.getInt(indicadorFinalizado));
			sistemaParametros.setIndicadorMapa(cursor.getInt(indicadorMapa));
			sistemaParametros.setDescricaoArquivo(cursor.getString(descricaoArquivo));
			sistemaParametros.setIdEmpresa(cursor.getInt(idEmpresa));
			sistemaParametros.setIndicadorArquivoCarregado(cursor.getInt(indicadorArquivoCarregado));
		}

		cursor.close();
		return sistemaParametros;
	}
}