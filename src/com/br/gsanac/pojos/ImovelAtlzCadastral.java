package com.br.gsanac.pojos;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.gsanac.utilitarios.ConstantesSistema;
import com.br.gsanac.utilitarios.Utilitarios;

/**
 * @author Jonathan Marcos
 * @since 05/09/2014
 */
public class ImovelAtlzCadastral extends EntidadeBasica implements Cloneable {

	private static final long serialVersionUID = 1L;
	
	// Atributos
	private Logradouro logradouro;
    private LigacaoAguaSituacao ligAguaSituacao;
    private LigacaoEsgotoSituacao ligEsgotoSituacao;
    private FonteAbastecimento fonteAbastecimento;
    private PavimentoCalcada pavimentoCalcada;
    private EnderecoReferencia enderecoReferencia;
    private PavimentoRua pavimentoRua;
    private ImovelPerfil imovelPerfil;
    private Integer imovelId;
    private Integer municipioId;
    private String nomeMunicipio;
    private Integer localidadeId;
    private String nomeLocalidade;
    private Integer codigoSetorComercial;
    private Integer numeroQuadra;
    private Integer numeroLote;
    private Integer numeroSubLote;
    private String numeroImovel;
    private String complementoEndereco;
    private Integer logradouroBairroId;
    private Integer logradouroCEPId;
    private String numeroMedidorEnergia;
    private Integer numeroMorador;
    private Integer indicadorFinalizado;
    private String observacao;
    private Integer indicadorTarifaSocial;
    private Integer posicao;
    private Integer indicadorTransmitido;
    private String integracaoID;
    private Date dataVisita;
    private Integer idBairro;
    private Integer codigoCep;
    private String codigoUnicoLogradouro;
    private String login;
    private Integer indicadorImovelNovoComMatricula;
	private LeituraAnormalidade anormalidadeLeitura;
	private String coordenadaX;
	private String coordenadaY;
	
    // Index de acesso
	private static final int IMAC_ID_INDEX = 1;
	private static final int IMOV_ID_INDEX = 2;
	private static final int MUNI_ID_INDEX = 3;
	private static final int MUNI_NMMUNICIPIO_INDEX = 4;
	private static final int LOCA_ID_INDEX = 5;
	private static final int LOCA_NMLOCALIDADE_INDEX = 6;
	private static final int STCM_CDSETORCOMERCIAL_INDEX = 7;
	private static final int QDRA_NNQUADRA_INDEX = 8;
	private static final int IMAC_NNLOTE_INDEX = 9;
	private static final int IMAC_NNSUBLOTE_INDEX = 10;
	private static final int LOGRADOURO_LOGR_ID_INDEX = 11;
	private static final int EDRF_ID_INDEX = 12;
	private static final int IMAC_NNIMOVEL_INDEX = 13;
	private static final int IMAC_DSCOMPLEMENTOENDERECO_INDEX = 14;
	private static final int LGBR_ID_INDEX = 15;
	private static final int LGCP_ID_INDEX = 16;
	private static final int IPER_ID_INDEX = 17;
	private static final int IMAC_NNMEDIDORENERGIA_INDEX = 18;
	private static final int IMAC_NNMORADOR_INDEX = 19;
	private static final int PRUA_ID_INDEX = 20;
	private static final int PCAL_ID_INDEX = 21;
	private static final int FTAB_ID_INDEX = 22;
	private static final int LAST_ID_INDEX = 23;
	private static final int LEST_ID_INDEX = 24;
	private static final int IMAC_ICTARIFASOCIAL_INDEX = 25;
	private static final int IMAC_IDBAIRRO_INDEX = 26;
	private static final int IMAC_CDCEP_INDEX = 27;
	private static final int IMAC_COORDENADAX_INDEX  = 28;
	private static final int IMAC_COORDENATAY_INDEX = 29;
    
	/*
     * SubClasse referente aos 
     * campos da tabela
     */
	public static final class ImovelAtlzCadastralColuna implements BaseColumns{
    	public static final String ID = "IMAC_ID";        	  
    	public static final String LOGRADOURO_ID = "LOGR_ID";
    	public static final String LIGAGUASITUACAO_ID = "LAST_ID";
    	public static final String LIGESGOTOSITUACAO_ID = "LEST_ID";
    	public static final String FONTEABASTECIMENTO_ID = "FTAB_ID";
    	public static final String PAVIMENTOCALCADA_ID = "PCAL_ID";
    	public static final String ENDERECOREFERENCIA_ID = "EDRF_ID";
    	public static final String PAVIMENTORUA_ID = "PRUA_ID";
    	public static final String IMOVEL_ID = "IMOV_ID";
    	public static final String MUNICIPIO_ID = "MUNI_ID";
    	public static final String NOMEMUNICIPIO = "MUNI_NMMUNICIPIO";
    	public static final String LOCALIDADE_ID = "LOCA_ID";
        public static final String NOMELOCALIDADE = "LOCA_NMLOCALIDADE";
        public static final String CODIGOSETORCOMERCIAL = "STCM_CDSETORCOMERCIAL";
        public static final String NUMQUADRA = "QDRA_NNQUADRA";
        public static final String NUMLOTE = "IMAC_NNLOTE";
        public static final String NUMSUBLOTE = "IMAC_NNSUBLOTE";
        public static final String NUMIMOVEL = "IMAC_NNIMOVEL";
        public static final String COMPLENDERECO = "IMAC_DSCOMPLEMENTOENDERECO";
        public static final String LOGRADOUROBAIRRO_ID = "LGBR_ID";
        public static final String LOGRADOUROCEP_ID = "LGCP_ID";
        public static final String IMOVELPERFIL_ID = "IPER_ID";
        public static final String NUMMEDIDORENERGIA = "IMAC_NNMEDIDORENERGIA";
        public static final String NUMMORADOR = "IMAC_NNMORADOR";
        public static final String INDICADOR_FINALIZADO = "IMAC_ICFINALIZADO";
        public static final String OBSERVACAO = "IMAC_DSOBSERVACAO";
        public static final String INDICADOR_TARIFA_SOCIAL 	= "IMAC_ICALERTATARIFASOCIAL";
        public static final String POSICAO = "IMAC_POSICAO";
        public static final String INDICADOR_TRANSMITIDO = "IMAC_ICTRANSMITIDO";
        public static final String INTEGRACAO = "IMAC_INTEGRACAOID";
        public static final String DATA_VISITA = "IMAC_DTVISITA";
        public static final String ID_BAIRRO = "BAIR_ID";
        public static final String CODIGO_CEP = "IMAC_CDCEP";
        public static final String CODIGO_LOGRADOURO = "IMAC_CDUNICOLOG";
        public static final String LOGIN = "IMAC_LOGIN";
        public static final String ANORMALIDADE_LEITURA_ID = "LTAN_ID";
        public static final String COORDENADAX = "IMAC_NNCOORDENADAX";
        public static final String COORDENADAY = "IMAC_NNCOORDENADAY";
    }
	
	/*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	ImovelAtlzCadastralColuna.ID,
    	ImovelAtlzCadastralColuna.LOGRADOURO_ID,
    	ImovelAtlzCadastralColuna.LIGAGUASITUACAO_ID,
    	ImovelAtlzCadastralColuna.LIGESGOTOSITUACAO_ID,
    	ImovelAtlzCadastralColuna.FONTEABASTECIMENTO_ID,
    	ImovelAtlzCadastralColuna.PAVIMENTOCALCADA_ID,
    	ImovelAtlzCadastralColuna.ENDERECOREFERENCIA_ID,
    	ImovelAtlzCadastralColuna.PAVIMENTORUA_ID,
    	ImovelAtlzCadastralColuna.IMOVEL_ID,
    	ImovelAtlzCadastralColuna.MUNICIPIO_ID,
    	ImovelAtlzCadastralColuna.NOMEMUNICIPIO,
    	ImovelAtlzCadastralColuna.LOCALIDADE_ID,
    	ImovelAtlzCadastralColuna.NOMELOCALIDADE,
    	ImovelAtlzCadastralColuna.CODIGOSETORCOMERCIAL,
    	ImovelAtlzCadastralColuna.NUMQUADRA,
    	ImovelAtlzCadastralColuna.NUMLOTE,
    	ImovelAtlzCadastralColuna.NUMSUBLOTE,
    	ImovelAtlzCadastralColuna.NUMIMOVEL,
    	ImovelAtlzCadastralColuna.COMPLENDERECO,
    	ImovelAtlzCadastralColuna.LOGRADOUROBAIRRO_ID,
    	ImovelAtlzCadastralColuna.LOGRADOUROCEP_ID,
    	ImovelAtlzCadastralColuna.IMOVELPERFIL_ID,
    	ImovelAtlzCadastralColuna.NUMMEDIDORENERGIA,
    	ImovelAtlzCadastralColuna.NUMMORADOR,
    	ImovelAtlzCadastralColuna.INDICADOR_FINALIZADO,
    	ImovelAtlzCadastralColuna.OBSERVACAO,
    	ImovelAtlzCadastralColuna.INDICADOR_TARIFA_SOCIAL,
    	ImovelAtlzCadastralColuna.POSICAO,
    	ImovelAtlzCadastralColuna.INDICADOR_TRANSMITIDO,
    	ImovelAtlzCadastralColuna.INTEGRACAO,
    	ImovelAtlzCadastralColuna.DATA_VISITA,
    	ImovelAtlzCadastralColuna.ID_BAIRRO,
    	ImovelAtlzCadastralColuna.CODIGO_CEP,
    	ImovelAtlzCadastralColuna.CODIGO_LOGRADOURO,
    	ImovelAtlzCadastralColuna.LOGIN,
    	ImovelAtlzCadastralColuna.ANORMALIDADE_LEITURA_ID,
    	ImovelAtlzCadastralColuna.COORDENADAX,
    	ImovelAtlzCadastralColuna.COORDENADAY
    };
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
	
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class ImovelAtlzCadastralColunaTipo {
    	private final String ID = " INTEGER PRIMARY KEY";        	  
    	private final String LOGRADOURO_ID = " INTEGER";
    	private final String LIGACAOAGUA_ID = " INTEGER NOT NULL";
    	private final String LIGACAOESGOTO_ID = " INTEGER NOT NULL";
    	private final String FONTEABASTECIMENTO_ID = " INTEGER";
    	private final String PAVIMENTOCALCADA_ID = " INTEGER";
    	private final String ENDERECOREFERENCIA_ID = " INTEGER";
    	private final String PAVIMENTORUA_ID = " INTEGER";
    	private final String IMOVEL_ID = " INTEGER";
    	private final String MUNICIPIO_ID = " INTEGER";
    	private final String NOMEMUNICIPIO = " VARCHAR(30)";
    	private final String LOCALIDADE_ID = " INTEGER NOT NULL";
    	private final String NOMELOCALIDADE = " VARCHAR(30)";
    	private final String CODIGOSETORCOMERCIAL = " INTEGER";
    	private final String NUMQUADRA = " INTEGER NOT NULL";
    	private final String NUMLOTE = " INTEGER NOT NULL";
    	private final String NUMSUBLOTE = " INTEGER NOT NULL";
    	private final String NUMIMOVEL = " VARCHAR(5) NOT NULL";
    	private final String COMPLENDERECO = " VARCHAR(25)";
    	private final String LOGRADOUROBAIRRO_ID = " INTEGER";
    	private final String LOGRADOUROCEP_ID = " INTEGER";
    	private final String IMOVELPERFIL_ID = " INTEGER";
    	private final String NUMMEDIDORENERGIA = " VARCHAR(10)";
    	private final String NUMMORADOR = " INTEGER";
    	private final String INDICADOR_FINALIZADO = " INTEGER NOT NULL";
    	private final String OBSERVACAO = " VARCHAR(400)";
    	private final String INDICADOR_TARIFA_SOCIAL = " INTEGER";
    	private final String POSICAO	= " INTEGER";
    	private final String INDICADOR_TRANSMITIDO = " INTEGER NOT NULL";
    	private final String INTEGRACAO = " VARCHAR(50)";
    	private final String DATA_VISITA = " DATE";
    	private final String BAIRRO = " INTEGER";
    	private final String CEP = " INTEGER";
    	private final String CDLOG = " VARCHAR(40)";
    	private final String LOGIN = " VARCHAR(30)";
    	private final String ANORMALIDADE_LEITURA_ID = " INTEGER";
    	private final String COORDENADAX = " VARCHAR(40)";
    	private final String COORDENADAY = " VARCHAR(40)";
  	
		private String[] tipos = new String[] {
				ID,
				LOGRADOURO_ID,
				LIGACAOAGUA_ID, 
				LIGACAOESGOTO_ID, 
				FONTEABASTECIMENTO_ID,
				PAVIMENTOCALCADA_ID,
				ENDERECOREFERENCIA_ID,
				PAVIMENTORUA_ID, 
				IMOVEL_ID,
				MUNICIPIO_ID,
				NOMEMUNICIPIO,
				LOCALIDADE_ID,
				NOMELOCALIDADE, 
				CODIGOSETORCOMERCIAL, 
				NUMQUADRA,
				NUMLOTE,
				NUMSUBLOTE,
				NUMIMOVEL,
				COMPLENDERECO, 
				LOGRADOUROBAIRRO_ID,
				LOGRADOUROCEP_ID, 
				IMOVELPERFIL_ID,
				NUMMEDIDORENERGIA,
				NUMMORADOR, 
				INDICADOR_FINALIZADO,
				OBSERVACAO,
				INDICADOR_TARIFA_SOCIAL,
				POSICAO,
				INDICADOR_TRANSMITIDO,
				INTEGRACAO,
				DATA_VISITA, 
				BAIRRO, 
				CEP,
				CDLOG,
				LOGIN,
				ANORMALIDADE_LEITURA_ID,
				COORDENADAX,
				COORDENADAY
			};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
    
    public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public LigacaoAguaSituacao getLigAguaSituacao() {
		return ligAguaSituacao;
	}

	public void setLigAguaSituacao(LigacaoAguaSituacao ligAguaSituacao) {
		this.ligAguaSituacao = ligAguaSituacao;
	}

	public LigacaoEsgotoSituacao getLigEsgotoSituacao() {
		return ligEsgotoSituacao;
	}

	public void setLigEsgotoSituacao(LigacaoEsgotoSituacao ligEsgotoSituacao) {
		this.ligEsgotoSituacao = ligEsgotoSituacao;
	}

	public FonteAbastecimento getFonteAbastecimento() {
		return fonteAbastecimento;
	}

	public void setFonteAbastecimento(FonteAbastecimento fonteAbastecimento) {
		this.fonteAbastecimento = fonteAbastecimento;
	}

	public PavimentoCalcada getPavimentoCalcada() {
		return pavimentoCalcada;
	}

	public void setPavimentoCalcada(PavimentoCalcada pavimentoCalcada) {
		this.pavimentoCalcada = pavimentoCalcada;
	}

	public EnderecoReferencia getEnderecoReferencia() {
		return enderecoReferencia;
	}

	public void setEnderecoReferencia(EnderecoReferencia enderecoReferencia) {
		this.enderecoReferencia = enderecoReferencia;
	}

	public PavimentoRua getPavimentoRua() {
		return pavimentoRua;
	}

	public void setPavimentoRua(PavimentoRua pavimentoRua) {
		this.pavimentoRua = pavimentoRua;
	}

	public Integer getImovelId() {
		return imovelId;
	}

	public void setImovelId(Integer imovelId) {
		this.imovelId = imovelId;
	}

	public Integer getMunicipioId() {
		return municipioId;
	}

	public void setMunicipioId(Integer municipioId) {
		this.municipioId = municipioId;
	}

	public String getNomeMunicipio() {
		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio) {
		this.nomeMunicipio = nomeMunicipio;
	}

	public Integer getLocalidadeId() {
		return localidadeId;
	}

	public void setLocalidadeId(Integer localidadeId) {
		this.localidadeId = localidadeId;
	}

	public String getNomeLocalidade() {
		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade) {
		this.nomeLocalidade = nomeLocalidade;
	}

	public Integer getCodigoSetorComercial() {
		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial) {
		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Integer getNumeroQuadra() {
		return numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra) {
		this.numeroQuadra = numeroQuadra;
	}

	public Integer getNumeroLote() {
		return numeroLote;
	}

	public void setNumeroLote(Integer numeroLote) {
		this.numeroLote = numeroLote;
	}

	public Integer getNumeroSubLote() {
		return numeroSubLote;
	}

	public void setNumeroSubLote(Integer numeroSubLote) {
		this.numeroSubLote = numeroSubLote;
	}

	public String getNumeroImovel() {
		return numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel) {
		this.numeroImovel = numeroImovel;
	}

	public String getComplementoEndereco() {
		return complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco) {
		this.complementoEndereco = complementoEndereco;
	}

	public Integer getLogradouroBairroId() {
		return logradouroBairroId;
	}

	public void setLogradouroBairroId(Integer logradouroBairroId) {
		this.logradouroBairroId = logradouroBairroId;
	}

	public Integer getLogradouroCEPId() {
		return logradouroCEPId;
	}

	public void setLogradouroCEPId(Integer logradouroCEPId) {
		this.logradouroCEPId = logradouroCEPId;
	}

	public ImovelPerfil getImovelPerfil() {
		return imovelPerfil;
	}

	public void setImovelPerfil(ImovelPerfil imovelPerfil) {
		this.imovelPerfil = imovelPerfil;
	}

	public String getNumeroMedidorEnergia() {
		return numeroMedidorEnergia;
	}

	public void setNumeroMedidorEnergia(String numeroMedidorEnergia) {
		this.numeroMedidorEnergia = numeroMedidorEnergia;
	}

	public Integer getNumeroMorador() {
		return numeroMorador;
	}

	public void setNumeroMorador(Integer numeroMorador) {
		this.numeroMorador = numeroMorador;
	}

	public Integer getIndicadorFinalizado() {
		return indicadorFinalizado;
	}

	public void setIndicadorFinalizado(Integer indicadorFinalizado) {
		this.indicadorFinalizado = indicadorFinalizado;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Integer getIndicadorTarifaSocial() {
		return indicadorTarifaSocial;
	}

	public void setIndicadorTarifaSocial(Integer indicadorTarifaSocial) {
		this.indicadorTarifaSocial = indicadorTarifaSocial;
	}

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}
	
	public Integer getIndicadorTransmitido() {
		return indicadorTransmitido;
	}

	public void setIndicadorTransmitido(Integer indicadorTransmitido) {
		this.indicadorTransmitido = indicadorTransmitido;
	}
	
	public String getIntegracaoID() {
		return integracaoID;
	}

	public void setIntegracaoID(String integracaoID) {
		this.integracaoID = integracaoID;
	}
	
	public Date getDataVisita() {
		return dataVisita;
	}

	public void setDataVisita(Date dataVisita) {
		this.dataVisita = dataVisita;
	}

	public Integer getIdBairro() {
		return idBairro;
	}

	public void setIdBairro(Integer idBairro) {
		this.idBairro = idBairro;
	}

	public Integer getCodigoCep() {
		return codigoCep;
	}

	public void setCodigoCep(Integer codigoCep) {
		this.codigoCep = codigoCep;
	}

	public String getCodigoUnicoLogradouro() {
		return codigoUnicoLogradouro;
	}

	public void setCodigoUnicoLogradouro(String codigoUnicoLogradouro) {
		this.codigoUnicoLogradouro = codigoUnicoLogradouro;
	}
   
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Integer getIndicadorImovelNovoComMatricula() {
		return indicadorImovelNovoComMatricula;
	}

	public void setIndicadorImovelNovoComMatricula(
			Integer indicadorImovelNovoComMatricula) {
		this.indicadorImovelNovoComMatricula = indicadorImovelNovoComMatricula;
	}
    
	 public LeituraAnormalidade getAnormalidadeLeitura() {
		return anormalidadeLeitura;
	}

	public void setAnormalidadeLeitura(LeituraAnormalidade anormalidadeLeitura) {
		this.anormalidadeLeitura = anormalidadeLeitura;
	}

	public String getCoordenadaX() {
		return coordenadaX;
	}

	public void setCoordenadaX(String coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public String getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(String coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	/*
     * Método responsável por retornar objeto
     * a partir de uma lista
     */
    public static ImovelAtlzCadastral converteLinhaArquivoEmObjeto(List<String> c, Integer posicao ) {
    	
    	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
    	
    	imovelAtlzCadastral.setPosicao(posicao);
    	
    	imovelAtlzCadastral.setId(c.get(IMAC_ID_INDEX));
    	
        Logradouro logradouro = new Logradouro();
        logradouro.setId(c.get(LOGRADOURO_LOGR_ID_INDEX));
        imovelAtlzCadastral.setLogradouro(logradouro);
        
        LigacaoAguaSituacao ligAguaSituacao = new LigacaoAguaSituacao();
        ligAguaSituacao.setId(c.get(LAST_ID_INDEX));
        imovelAtlzCadastral.setLigAguaSituacao(ligAguaSituacao);

        LigacaoEsgotoSituacao ligEsgotoSituacao = new LigacaoEsgotoSituacao();
        ligEsgotoSituacao.setId(c.get(LEST_ID_INDEX));
        imovelAtlzCadastral.setLigEsgotoSituacao(ligEsgotoSituacao);
        
        FonteAbastecimento fonteAbastecimento = new FonteAbastecimento();
        fonteAbastecimento.setId(c.get(FTAB_ID_INDEX));
        imovelAtlzCadastral.setFonteAbastecimento(fonteAbastecimento);
        
        PavimentoCalcada pavimentoCalcada = new PavimentoCalcada();
        pavimentoCalcada.setId(c.get(PCAL_ID_INDEX));
        imovelAtlzCadastral.setPavimentoCalcada(pavimentoCalcada);
        
        EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
        enderecoReferencia.setId(Utilitarios.verificarNuloInt(c.get(EDRF_ID_INDEX)));
        imovelAtlzCadastral.setEnderecoReferencia(enderecoReferencia);
        
        PavimentoRua pavimentoRua = new PavimentoRua();
        pavimentoRua.setId(Utilitarios.verificarNuloInt(c.get(PRUA_ID_INDEX)));
        imovelAtlzCadastral.setPavimentoRua(pavimentoRua);
        
        ImovelPerfil imovelPerfil = new ImovelPerfil();
        imovelPerfil.setId(Utilitarios.verificarNuloInt(c.get(IPER_ID_INDEX)));
        imovelAtlzCadastral.setImovelPerfil(imovelPerfil);
        
        imovelAtlzCadastral.setImovelId(Utilitarios.verificarNuloInt(c.get(IMOV_ID_INDEX)));
        imovelAtlzCadastral.setMunicipioId(Utilitarios.verificarNuloInt(c.get(MUNI_ID_INDEX)));
        imovelAtlzCadastral.setNomeMunicipio(c.get(MUNI_NMMUNICIPIO_INDEX));
        imovelAtlzCadastral.setLocalidadeId(Utilitarios.verificarNuloInt(c.get(LOCA_ID_INDEX)));
        imovelAtlzCadastral.setNomeLocalidade(c.get(LOCA_NMLOCALIDADE_INDEX));
        imovelAtlzCadastral.setCodigoSetorComercial(Utilitarios.verificarNuloInt(c.get(STCM_CDSETORCOMERCIAL_INDEX)));
        imovelAtlzCadastral.setNumeroQuadra(Utilitarios.verificarNuloInt(c.get(QDRA_NNQUADRA_INDEX)));
        imovelAtlzCadastral.setNumeroLote(Utilitarios.verificarNuloInt(c.get(IMAC_NNLOTE_INDEX)));
        imovelAtlzCadastral.setNumeroSubLote(Utilitarios.verificarNuloInt(c.get(IMAC_NNSUBLOTE_INDEX)));
        
        //retira qualquer caracter especial do numero do imovel.
        String numeroImovel = c.get(IMAC_NNIMOVEL_INDEX);
        if ( numeroImovel != null && !numeroImovel.equals("") ) {
        	
        	numeroImovel = numeroImovel.replaceAll("[^a-zA-Z0-9/\\- ]", " ");
        	imovelAtlzCadastral.setNumeroImovel(numeroImovel);
        }
        
        String complementoEndereco = c.get(IMAC_DSCOMPLEMENTOENDERECO_INDEX);
        if ( complementoEndereco != null && !complementoEndereco.equals("") ) {
        	
        	complementoEndereco = complementoEndereco.replaceAll("[^a-zA-Z0-9/\\- ]", " ");  
        	imovelAtlzCadastral.setComplementoEndereco(complementoEndereco);
        }
        
        imovelAtlzCadastral.setLogradouroBairroId(Utilitarios.verificarNuloInt(c.get(LGBR_ID_INDEX)));
        imovelAtlzCadastral.setLogradouroCEPId(Utilitarios.verificarNuloInt(c.get(LGCP_ID_INDEX)));
        imovelAtlzCadastral.setNumeroMedidorEnergia(c.get(IMAC_NNMEDIDORENERGIA_INDEX));
        imovelAtlzCadastral.setNumeroMorador(Utilitarios.verificarNuloInt(c.get(IMAC_NNMORADOR_INDEX)));
        imovelAtlzCadastral.setIndicadorTarifaSocial(Utilitarios.verificarNuloInt(c.get(IMAC_ICTARIFASOCIAL_INDEX)));
        imovelAtlzCadastral.setIndicadorFinalizado(ConstantesSistema.PENDENTE);
        imovelAtlzCadastral.setIndicadorTransmitido(ConstantesSistema.NAO);       
        imovelAtlzCadastral.setIdBairro(Utilitarios.verificarNuloInt(c.get(IMAC_IDBAIRRO_INDEX)));
        imovelAtlzCadastral.setCodigoCep(Utilitarios.verificarNuloInt(c.get(IMAC_CDCEP_INDEX)));
        
        imovelAtlzCadastral.setCoordenadaX(Utilitarios.verificarNuloString(c.get(IMAC_COORDENADAX_INDEX)));
        imovelAtlzCadastral.setCoordenadaY(Utilitarios.verificarNuloString(c.get(IMAC_COORDENATAY_INDEX)));
      
        return imovelAtlzCadastral;
    }
    
    // Método converte um cursor em  objeto
    public ContentValues carregarValues() throws ParseException {
		
    	ContentValues values = new ContentValues();
		
		values.put(ImovelAtlzCadastralColuna.ID, getId());
		values.put(ImovelAtlzCadastralColuna.LOGRADOURO_ID, getLogradouro().getId());
		values.put(ImovelAtlzCadastralColuna.LIGAGUASITUACAO_ID, getLigAguaSituacao().getId());
		values.put(ImovelAtlzCadastralColuna.LIGESGOTOSITUACAO_ID, getLigEsgotoSituacao().getId());
		values.put(ImovelAtlzCadastralColuna.FONTEABASTECIMENTO_ID, getFonteAbastecimento().getId());
		values.put(ImovelAtlzCadastralColuna.PAVIMENTOCALCADA_ID, getPavimentoCalcada().getId());
		
		if ( getEnderecoReferencia() != null && getEnderecoReferencia().getId() != null) {
			values.put(ImovelAtlzCadastralColuna.ENDERECOREFERENCIA_ID, getEnderecoReferencia().getId());
		}
		
		values.put(ImovelAtlzCadastralColuna.PAVIMENTORUA_ID, getPavimentoRua().getId());
		values.put(ImovelAtlzCadastralColuna.IMOVEL_ID, getImovelId());
		values.put(ImovelAtlzCadastralColuna.MUNICIPIO_ID, getMunicipioId());
		values.put(ImovelAtlzCadastralColuna.NOMEMUNICIPIO, getNomeMunicipio());
		values.put(ImovelAtlzCadastralColuna.LOCALIDADE_ID, getLocalidadeId());
		values.put(ImovelAtlzCadastralColuna.NOMELOCALIDADE, getNomeLocalidade());
		values.put(ImovelAtlzCadastralColuna.CODIGOSETORCOMERCIAL, getCodigoSetorComercial());
		values.put(ImovelAtlzCadastralColuna.NUMQUADRA, getNumeroQuadra());
		values.put(ImovelAtlzCadastralColuna.NUMLOTE, getNumeroLote());
		values.put(ImovelAtlzCadastralColuna.NUMSUBLOTE, getNumeroSubLote());
		values.put(ImovelAtlzCadastralColuna.NUMIMOVEL, getNumeroImovel());
		
		if ( getComplementoEndereco() != null && !getComplementoEndereco().equals("") ) {
			values.put(ImovelAtlzCadastralColuna.COMPLENDERECO, getComplementoEndereco());
		}else{
			values.putNull(ImovelAtlzCadastralColuna.COMPLENDERECO);
		}
		
		values.put(ImovelAtlzCadastralColuna.LOGRADOUROBAIRRO_ID, getLogradouroBairroId());
		values.put(ImovelAtlzCadastralColuna.LOGRADOUROCEP_ID, getLogradouroCEPId());
		
		if(getImovelPerfil()!=null){
			values.put(ImovelAtlzCadastralColuna.IMOVELPERFIL_ID, getImovelPerfil().getId());
		}else{
			values.putNull(ImovelAtlzCadastralColuna.IMOVELPERFIL_ID);
		}
		
		
		if ( getNumeroMedidorEnergia() != null && !getNumeroMedidorEnergia().equals("") ) {
			values.put(ImovelAtlzCadastralColuna.NUMMEDIDORENERGIA, getNumeroMedidorEnergia());
		}else{
			values.putNull(ImovelAtlzCadastralColuna.NUMMEDIDORENERGIA);
		}
		
		values.put(ImovelAtlzCadastralColuna.NUMMORADOR, getNumeroMorador());
		values.put(ImovelAtlzCadastralColuna.INDICADOR_FINALIZADO, getIndicadorFinalizado());
		values.put(ImovelAtlzCadastralColuna.OBSERVACAO, getObservacao());
		values.put(ImovelAtlzCadastralColuna.INDICADOR_TARIFA_SOCIAL, getIndicadorTarifaSocial());
		values.put(ImovelAtlzCadastralColuna.POSICAO, getPosicao());
		values.put(ImovelAtlzCadastralColuna.INDICADOR_TRANSMITIDO, getIndicadorTransmitido());
		
		values.put(ImovelAtlzCadastralColuna.INTEGRACAO, getIntegracaoID());
		
		if(getDataVisita() != null){
			values.put(ImovelAtlzCadastralColuna.DATA_VISITA,Utilitarios.simpleDateFormatFormatoBancoDeDados.
					format(getDataVisita()));
		}else{
			values.putNull(ImovelAtlzCadastralColuna.DATA_VISITA);
		}
		
		
		values.put(ImovelAtlzCadastralColuna.ID_BAIRRO, getIdBairro());
		values.put(ImovelAtlzCadastralColuna.CODIGO_CEP, getCodigoCep());
		values.put(ImovelAtlzCadastralColuna.CODIGO_LOGRADOURO, getCodigoUnicoLogradouro());
		values.put(ImovelAtlzCadastralColuna.LOGIN, getLogin());

		if(getAnormalidadeLeitura()!=null){			
			values.put(ImovelAtlzCadastralColuna.ANORMALIDADE_LEITURA_ID, getAnormalidadeLeitura().getId());
		}else{
			values.putNull(ImovelAtlzCadastralColuna.ANORMALIDADE_LEITURA_ID);
		}
		
		if(getCoordenadaX()!=null){
			values.put(ImovelAtlzCadastralColuna.COORDENADAX, getCoordenadaX());
		}
		
		if(getCoordenadaY()!=null){
			values.put(ImovelAtlzCadastralColuna.COORDENADAY, getCoordenadaY());
		}
	
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<ImovelAtlzCadastral> carregarListaEntidade(Cursor cursor) throws ParseException {		
		ArrayList<ImovelAtlzCadastral> listaImovelAtlzCadastral = new ArrayList<ImovelAtlzCadastral>();
		
		if ( cursor.moveToFirst() ) {
			do{
				int _id = cursor.getColumnIndex(ImovelAtlzCadastralColuna.ID);
				int _logradouroId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LOGRADOURO_ID);
				int _ligAguaSituacaoId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LIGAGUASITUACAO_ID);
				int _ligEsgotoSituacaoId  = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LIGESGOTOSITUACAO_ID);
				int _fonteAbastecimentoId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.FONTEABASTECIMENTO_ID);
				int _pavimentoCalcadaId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.PAVIMENTOCALCADA_ID);
				int _enderecoReferenciaId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.ENDERECOREFERENCIA_ID);
				int _pavimentoRuaId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.PAVIMENTORUA_ID);
				int _imovelId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.IMOVEL_ID);
				int _municipioId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.MUNICIPIO_ID);
				int _nomeMunicipio = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NOMEMUNICIPIO);
				int _localidadeId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LOCALIDADE_ID);
				int _nomeLocalidade = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NOMELOCALIDADE);
				int _codigoSetorComercial = cursor.getColumnIndex(ImovelAtlzCadastralColuna.CODIGOSETORCOMERCIAL);
				int _numeroQuadra = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NUMQUADRA);
				int _numeroLote = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NUMLOTE);
				int _numeroSubLote = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NUMSUBLOTE);
				int _numeroImovel = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NUMIMOVEL);
				int _complementoEndereco = cursor.getColumnIndex(ImovelAtlzCadastralColuna.COMPLENDERECO);
				int _logradouroBairroId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LOGRADOUROBAIRRO_ID);
				int _logradouroCEPId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LOGRADOUROCEP_ID);
				int _imovelPerfilId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.IMOVELPERFIL_ID);
				int _numeroMedidorEnergia = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NUMMEDIDORENERGIA);
				int _numeroMorador = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NUMMORADOR);
				int _observacao = cursor.getColumnIndex(ImovelAtlzCadastralColuna.OBSERVACAO);
				int _icTarifaSocial = cursor.getColumnIndex(ImovelAtlzCadastralColuna.INDICADOR_TARIFA_SOCIAL);
				int _posicao = cursor.getColumnIndex(ImovelAtlzCadastralColuna.POSICAO);
				int _indicadorFinalizado  = cursor.getColumnIndex(ImovelAtlzCadastralColuna.INDICADOR_FINALIZADO);
				int _indicadorTransmitido = cursor.getColumnIndex(ImovelAtlzCadastralColuna.INDICADOR_TRANSMITIDO);
				int _integracao = cursor.getColumnIndex(ImovelAtlzCadastralColuna.INTEGRACAO);
				int _dataVisita = cursor.getColumnIndex(ImovelAtlzCadastralColuna.DATA_VISITA);
				int _bairro = cursor.getColumnIndex(ImovelAtlzCadastralColuna.ID_BAIRRO);
				int _cep = cursor.getColumnIndex(ImovelAtlzCadastralColuna.CODIGO_CEP);
				int _cdLogradouroUnico = cursor.getColumnIndex(ImovelAtlzCadastralColuna.CODIGO_LOGRADOURO);
				int _login = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LOGIN);
				int _anormalidadeLeitura = cursor.getColumnIndex(ImovelAtlzCadastralColuna.ANORMALIDADE_LEITURA_ID);
				int _coordenadax = cursor.getColumnIndex(ImovelAtlzCadastralColuna.COORDENADAX);
				int _coordenaday = cursor.getColumnIndex(ImovelAtlzCadastralColuna.COORDENADAY);
				
				ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
					
				imovelAtlzCadastral.setId(cursor.getInt(_id));
				
		        Logradouro logradouro = new Logradouro();
		        logradouro.setId(cursor.getInt(_logradouroId));
		        imovelAtlzCadastral.setLogradouro(logradouro);
		        
		        LigacaoAguaSituacao ligAguaSituacao = new LigacaoAguaSituacao();
		        ligAguaSituacao.setId(cursor.getInt(_ligAguaSituacaoId));
		        imovelAtlzCadastral.setLigAguaSituacao(ligAguaSituacao);
	
		        LigacaoEsgotoSituacao ligEsgotoSituacao = new LigacaoEsgotoSituacao();
		        ligEsgotoSituacao.setId(cursor.getInt(_ligEsgotoSituacaoId));
		        imovelAtlzCadastral.setLigEsgotoSituacao(ligEsgotoSituacao);
		        
		        FonteAbastecimento fonteAbastecimento = new FonteAbastecimento();
		        fonteAbastecimento.setId(cursor.getInt(_fonteAbastecimentoId));
		        imovelAtlzCadastral.setFonteAbastecimento(fonteAbastecimento);
		        
		        PavimentoCalcada pavimentoCalcada = new PavimentoCalcada();
		        pavimentoCalcada.setId(cursor.getInt(_pavimentoCalcadaId));
		        imovelAtlzCadastral.setPavimentoCalcada(pavimentoCalcada);
		        
		        EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
		        enderecoReferencia.setId(cursor.getInt(_enderecoReferenciaId));
		        imovelAtlzCadastral.setEnderecoReferencia(enderecoReferencia);
		        
		        PavimentoRua pavimentoRua = new PavimentoRua();
		        pavimentoRua.setId(cursor.getInt(_pavimentoRuaId));
		        imovelAtlzCadastral.setPavimentoRua(pavimentoRua);
		        
		        ImovelPerfil imovelPerfil = new ImovelPerfil();
		        imovelPerfil.setId(cursor.getInt(_imovelPerfilId));
		        imovelAtlzCadastral.setImovelPerfil(imovelPerfil);
				
				imovelAtlzCadastral.setImovelId(cursor.getInt(_imovelId));
				imovelAtlzCadastral.setMunicipioId(cursor.getInt(_municipioId));
				imovelAtlzCadastral.setNomeMunicipio(cursor.getString(_nomeMunicipio));
				imovelAtlzCadastral.setLocalidadeId(cursor.getInt(_localidadeId));
				imovelAtlzCadastral.setNomeLocalidade(cursor.getString(_nomeLocalidade));
				imovelAtlzCadastral.setCodigoSetorComercial(cursor.getInt(_codigoSetorComercial));
				imovelAtlzCadastral.setNumeroQuadra(cursor.getInt(_numeroQuadra));
				imovelAtlzCadastral.setNumeroLote(cursor.getInt(_numeroLote));
				imovelAtlzCadastral.setNumeroSubLote(cursor.getInt(_numeroSubLote));
				imovelAtlzCadastral.setNumeroImovel(cursor.getString(_numeroImovel));
				imovelAtlzCadastral.setComplementoEndereco(cursor.getString(_complementoEndereco));
				imovelAtlzCadastral.setLogradouroBairroId(cursor.getInt(_logradouroBairroId));
				imovelAtlzCadastral.setLogradouroCEPId(cursor.getInt(_logradouroCEPId));			
				imovelAtlzCadastral.setNumeroMedidorEnergia(cursor.getString(_numeroMedidorEnergia));
				imovelAtlzCadastral.setNumeroMorador(cursor.getInt(_numeroMorador));
				imovelAtlzCadastral.setObservacao(cursor.getString(_observacao));
				imovelAtlzCadastral.setIndicadorTarifaSocial(cursor.getInt(_icTarifaSocial));
				imovelAtlzCadastral.setPosicao(cursor.getInt(_posicao));
				imovelAtlzCadastral.setIndicadorFinalizado(cursor.getInt(_indicadorFinalizado));
				imovelAtlzCadastral.setIndicadorTransmitido(cursor.getInt(_indicadorTransmitido));
				imovelAtlzCadastral.setIntegracaoID(cursor.getString(_integracao));
				imovelAtlzCadastral.setIdBairro(cursor.getInt(_bairro));
				imovelAtlzCadastral.setCodigoCep(cursor.getInt(_cep));
				imovelAtlzCadastral.setCodigoUnicoLogradouro(cursor.getString(_cdLogradouroUnico));
				imovelAtlzCadastral.setLogin(cursor.getString(_login));
				
				LeituraAnormalidade tmpAnormalidadeLeitura = new LeituraAnormalidade();
				tmpAnormalidadeLeitura.setId(cursor.getInt(_anormalidadeLeitura));
				imovelAtlzCadastral.setAnormalidadeLeitura(tmpAnormalidadeLeitura);
				
				if(cursor.getString(_dataVisita) != null && !cursor.getString(_dataVisita).equals("")){
					imovelAtlzCadastral.setDataVisita(Utilitarios.simpleDateFormatFormatoBancoDeDados.
							parse(cursor.getString(_dataVisita)));
		    	}
				
				if(cursor.getString(_coordenadax)!=null && !cursor.getString(_coordenadax).toString().equals("")){
					imovelAtlzCadastral.setCoordenadaX(cursor.getString(_coordenadax));
				}
				
				if(cursor.getString(_coordenaday)!=null && !cursor.getString(_coordenaday).toString().equals("")){
					imovelAtlzCadastral.setCoordenadaY(cursor.getString(_coordenaday));
				}
				
				listaImovelAtlzCadastral.add(imovelAtlzCadastral);
			
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaImovelAtlzCadastral;
	}
	
	// Método converte um cursor em  objeto
	public ImovelAtlzCadastral carregarEntidade(Cursor cursor) throws ParseException {		
		
		int _id = cursor.getColumnIndex(ImovelAtlzCadastralColuna.ID);
		int _logradouroId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LOGRADOURO_ID);
		int _ligAguaSituacaoId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LIGAGUASITUACAO_ID);
		int _ligEsgotoSituacaoId  = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LIGESGOTOSITUACAO_ID);
		int _fonteAbastecimentoId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.FONTEABASTECIMENTO_ID);
		int _pavimentoCalcadaId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.PAVIMENTOCALCADA_ID);
		int _enderecoReferenciaId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.ENDERECOREFERENCIA_ID);
		int _pavimentoRuaId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.PAVIMENTORUA_ID);
		int _imovelId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.IMOVEL_ID);
		int _municipioId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.MUNICIPIO_ID);
		int _nomeMunicipio = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NOMEMUNICIPIO);
		int _localidadeId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LOCALIDADE_ID);
		int _nomeLocalidade = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NOMELOCALIDADE);
		int _codigoSetorComercial = cursor.getColumnIndex(ImovelAtlzCadastralColuna.CODIGOSETORCOMERCIAL);
		int _numeroQuadra = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NUMQUADRA);
		int _numeroLote = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NUMLOTE);
		int _numeroSubLote = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NUMSUBLOTE);
		int _numeroImovel = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NUMIMOVEL);
		int _complementoEndereco = cursor.getColumnIndex(ImovelAtlzCadastralColuna.COMPLENDERECO);
		int _logradouroBairroId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LOGRADOUROBAIRRO_ID);
		int _logradouroCEPId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LOGRADOUROCEP_ID);
		int _imovelPerfilId = cursor.getColumnIndex(ImovelAtlzCadastralColuna.IMOVELPERFIL_ID);
		int _numeroMedidorEnergia = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NUMMEDIDORENERGIA);
		int _numeroMorador = cursor.getColumnIndex(ImovelAtlzCadastralColuna.NUMMORADOR);
		int _observacao = cursor.getColumnIndex(ImovelAtlzCadastralColuna.OBSERVACAO);
		int _icTarifaSocial = cursor.getColumnIndex(ImovelAtlzCadastralColuna.INDICADOR_TARIFA_SOCIAL);
		int _posicao = cursor.getColumnIndex(ImovelAtlzCadastralColuna.POSICAO);
		int _indicadorFinalizado  = cursor.getColumnIndex(ImovelAtlzCadastralColuna.INDICADOR_FINALIZADO);
		int _indicadorTransmitido = cursor.getColumnIndex(ImovelAtlzCadastralColuna.INDICADOR_TRANSMITIDO);
		int _integracao = cursor.getColumnIndex(ImovelAtlzCadastralColuna.INTEGRACAO);
		int _dataVisita = cursor.getColumnIndex(ImovelAtlzCadastralColuna.DATA_VISITA);
		int _bairro = cursor.getColumnIndex(ImovelAtlzCadastralColuna.ID_BAIRRO);
		int _cep = cursor.getColumnIndex(ImovelAtlzCadastralColuna.CODIGO_CEP);
		int _cdLogradouroUnico = cursor.getColumnIndex(ImovelAtlzCadastralColuna.CODIGO_LOGRADOURO);
		int _login = cursor.getColumnIndex(ImovelAtlzCadastralColuna.LOGIN);
		int _anormalidadeLeitura = cursor.getColumnIndex(ImovelAtlzCadastralColuna.ANORMALIDADE_LEITURA_ID);
		int _coordenadax = cursor.getColumnIndex(ImovelAtlzCadastralColuna.COORDENADAX);
		int _coordenaday = cursor.getColumnIndex(ImovelAtlzCadastralColuna.COORDENADAY);

		ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();

		if (cursor.moveToFirst() ) {
			
			imovelAtlzCadastral.setId(cursor.getInt(_id));
			
			Logradouro logradouro = new Logradouro();
	        logradouro.setId(cursor.getInt(_logradouroId));
	        imovelAtlzCadastral.setLogradouro(logradouro);
	        
	        LigacaoAguaSituacao ligAguaSituacao = new LigacaoAguaSituacao();
	        ligAguaSituacao.setId(cursor.getInt(_ligAguaSituacaoId));
	        imovelAtlzCadastral.setLigAguaSituacao(ligAguaSituacao);

	        LigacaoEsgotoSituacao ligEsgotoSituacao = new LigacaoEsgotoSituacao();
	        ligEsgotoSituacao.setId(cursor.getInt(_ligEsgotoSituacaoId));
	        imovelAtlzCadastral.setLigEsgotoSituacao(ligEsgotoSituacao);
	        
	        FonteAbastecimento fonteAbastecimento = new FonteAbastecimento();
	        fonteAbastecimento.setId(cursor.getInt(_fonteAbastecimentoId));
	        imovelAtlzCadastral.setFonteAbastecimento(fonteAbastecimento);
	        
	        PavimentoCalcada pavimentoCalcada = new PavimentoCalcada();
	        pavimentoCalcada.setId(cursor.getInt(_pavimentoCalcadaId));
	        imovelAtlzCadastral.setPavimentoCalcada(pavimentoCalcada);
	        
	        EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
	        enderecoReferencia.setId(cursor.getInt(_enderecoReferenciaId));
	        imovelAtlzCadastral.setEnderecoReferencia(enderecoReferencia);
	        
	        PavimentoRua pavimentoRua = new PavimentoRua();
	        pavimentoRua.setId(cursor.getInt(_pavimentoRuaId));
	        imovelAtlzCadastral.setPavimentoRua(pavimentoRua);
	        
	        ImovelPerfil imovelPerfil = new ImovelPerfil();
	        imovelPerfil.setId(cursor.getInt(_imovelPerfilId));
	        imovelAtlzCadastral.setImovelPerfil(imovelPerfil);
			
			imovelAtlzCadastral.setImovelId(cursor.getInt(_imovelId));
			imovelAtlzCadastral.setMunicipioId(cursor.getInt(_municipioId));
			imovelAtlzCadastral.setNomeMunicipio(cursor.getString(_nomeMunicipio));
			imovelAtlzCadastral.setLocalidadeId(cursor.getInt(_localidadeId));
			imovelAtlzCadastral.setNomeLocalidade(cursor.getString(_nomeLocalidade));
			imovelAtlzCadastral.setCodigoSetorComercial(cursor.getInt(_codigoSetorComercial));
			imovelAtlzCadastral.setNumeroQuadra(cursor.getInt(_numeroQuadra));
			imovelAtlzCadastral.setNumeroLote(cursor.getInt(_numeroLote));
			imovelAtlzCadastral.setNumeroSubLote(cursor.getInt(_numeroSubLote));
			imovelAtlzCadastral.setNumeroImovel(cursor.getString(_numeroImovel));
			imovelAtlzCadastral.setComplementoEndereco(cursor.getString(_complementoEndereco));
			imovelAtlzCadastral.setLogradouroBairroId(cursor.getInt(_logradouroBairroId));
			imovelAtlzCadastral.setLogradouroCEPId(cursor.getInt(_logradouroCEPId));			
			imovelAtlzCadastral.setNumeroMedidorEnergia(cursor.getString(_numeroMedidorEnergia));
			imovelAtlzCadastral.setNumeroMorador(cursor.getInt(_numeroMorador));
			imovelAtlzCadastral.setIndicadorFinalizado(cursor.getInt(_indicadorFinalizado));
			imovelAtlzCadastral.setObservacao(cursor.getString(_observacao));
			imovelAtlzCadastral.setIndicadorTarifaSocial(cursor.getInt(_icTarifaSocial));
			imovelAtlzCadastral.setPosicao(cursor.getInt(_posicao));
			imovelAtlzCadastral.setIndicadorTransmitido(cursor.getInt(_indicadorTransmitido));
			imovelAtlzCadastral.setIntegracaoID(cursor.getString(_integracao));
			imovelAtlzCadastral.setIdBairro(cursor.getInt(_bairro));
			imovelAtlzCadastral.setCodigoCep(cursor.getInt(_cep));
			imovelAtlzCadastral.setCodigoUnicoLogradouro(cursor.getString(_cdLogradouroUnico));
			imovelAtlzCadastral.setLogin(cursor.getString(_login));
			
			LeituraAnormalidade tmpAnormalidadeLeitura = new LeituraAnormalidade();
			tmpAnormalidadeLeitura.setId(cursor.getInt(_anormalidadeLeitura));
			imovelAtlzCadastral.setAnormalidadeLeitura(tmpAnormalidadeLeitura);

			
			if ( cursor.getString(_dataVisita) != null && !cursor.getString(_dataVisita).equals("") ) {
				imovelAtlzCadastral.setDataVisita(Utilitarios.simpleDateFormatFormatoBancoDeDados.
						parse(cursor.getString(_dataVisita)));
	    	}
			
			if(cursor.getString(_coordenadax)!=null && !cursor.getString(_coordenadax).toString().equals("")){
				imovelAtlzCadastral.setCoordenadaX(cursor.getString(_coordenadax));
			}
			
			if(cursor.getString(_coordenaday)!=null && !cursor.getString(_coordenaday).toString().equals("")){
				imovelAtlzCadastral.setCoordenadaY(cursor.getString(_coordenaday));
			}
		}

		cursor.close();
		return imovelAtlzCadastral;
	}
	
	// Retorna no da tabela
	public String getNomeTabela(){
		return "IMOVEL_ATLZ_CADASTRAL";
	}
	
	public ImovelAtlzCadastral clone() throws CloneNotSupportedException {
		return (ImovelAtlzCadastral) super.clone();
	}
}