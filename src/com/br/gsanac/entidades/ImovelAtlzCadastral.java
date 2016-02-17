package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

/**
 * <p>
 * Classe responsavel pelo objeto ImovelAtlzCadastral
 * </p>
 * 
 * @author Anderson Cabral
 * @since  12/12/2012
 */
public class ImovelAtlzCadastral extends EntidadeBase implements Cloneable {

	private static final long serialVersionUID = 1L;
	
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
	private static final int STCM_DSSETORCOMERCIAL_INDEX = 28;
	
	
	private static final int AD_IMAC_ID_INDEX = 1;
	private static final int AD_IMOV_ID_INDEX = 2;
	private static final int AD_MUNI_ID_INDEX = 3;
	private static final int AD_LOCA_ID_INDEX = 4;
	private static final int AD_STCM_CDSETORCOMERCIAL_INDEX = 5;
	private static final int AD_QDRA_NNQUADRA_INDEX = 6;
	private static final int AD_IMAC_NNLOTE_INDEX = 7;
	private static final int AD_IMAC_NNSUBLOTE_INDEX = 8;
	private static final int AD_LOGRADOURO_LOGR_ID_INDEX = 9;
	private static final int AD_EDRF_ID_INDEX = 10;
	private static final int AD_IMAC_NNIMOVEL_INDEX = 11;
	private static final int AD_IMAC_DSCOMPLEMENTOENDERECO_INDEX = 12;
	private static final int AD_LGBR_ID_INDEX = 13;
	private static final int AD_LGCP_ID_INDEX = 14;
	private static final int AD_IPER_ID_INDEX = 15;
	private static final int AD_IMAC_NNMEDIDORENERGIA_INDEX = 16;
	private static final int AD_IMAC_NNMORADOR_INDEX = 17;
	private static final int AD_PRUA_ID_INDEX = 18;
	private static final int AD_PCAL_ID_INDEX = 19;
	private static final int AD_FTAB_ID_INDEX = 20;
	private static final int AD_LAST_ID_INDEX = 21;
	private static final int AD_LEST_ID_INDEX = 22;
	private static final int AD_IMAC_ICTARIFASOCIAL_INDEX = 23;
	private static final int AD_IMAC_DATA_VISITA = 24;
	private static final int AD_IMAC_INTEGRACAO_ID = 25;
	private static final int AD_IMAC_IDBAIRRO_INDEX = 27;
	private static final int AD_IMAC_CDCEP_INDEX = 28;
	private static final int AD_IMAC_OBSERVACAO = 29;
	private static final int AD_IMAC_LOGIN = 31;
	private static final int AD_IMAC_NOMEMUNICIPIO = 32;
	
	
	

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
    private String descricaoSetorComercial;
    
    
    public static final String[] columns = new String[]{
    	ImovelAtlzCadastralColunas.ID,
    	ImovelAtlzCadastralColunas.LOGRADOURO_ID,
    	ImovelAtlzCadastralColunas.LIGAGUASITUACAO_ID,
    	ImovelAtlzCadastralColunas.LIGESGOTOSITUACAO_ID,
    	ImovelAtlzCadastralColunas.FONTEABASTECIMENTO_ID,
    	ImovelAtlzCadastralColunas.PAVIMENTOCALCADA_ID,
    	ImovelAtlzCadastralColunas.ENDERECOREFERENCIA_ID,
    	ImovelAtlzCadastralColunas.PAVIMENTORUA_ID,
    	ImovelAtlzCadastralColunas.IMOVEL_ID,
    	ImovelAtlzCadastralColunas.MUNICIPIO_ID,
    	ImovelAtlzCadastralColunas.NOMEMUNICIPIO,
    	ImovelAtlzCadastralColunas.LOCALIDADE_ID,
    	ImovelAtlzCadastralColunas.NOMELOCALIDADE,
    	ImovelAtlzCadastralColunas.CODIGOSETORCOMERCIAL,
    	ImovelAtlzCadastralColunas.NUMQUADRA,
    	ImovelAtlzCadastralColunas.NUMLOTE,
    	ImovelAtlzCadastralColunas.NUMSUBLOTE,
    	ImovelAtlzCadastralColunas.NUMIMOVEL,
    	ImovelAtlzCadastralColunas.COMPLENDERECO,
    	ImovelAtlzCadastralColunas.LOGRADOUROBAIRRO_ID,
    	ImovelAtlzCadastralColunas.LOGRADOUROCEP_ID,
    	ImovelAtlzCadastralColunas.IMOVELPERFIL_ID,
    	ImovelAtlzCadastralColunas.NUMMEDIDORENERGIA,
    	ImovelAtlzCadastralColunas.NUMMORADOR,
    	ImovelAtlzCadastralColunas.INDICADOR_FINALIZADO,
    	ImovelAtlzCadastralColunas.OBSERVACAO,
    	ImovelAtlzCadastralColunas.INDICADOR_TARIFA_SOCIAL,
    	ImovelAtlzCadastralColunas.POSICAO,
    	ImovelAtlzCadastralColunas.INDICADOR_TRANSMITIDO,
    	ImovelAtlzCadastralColunas.INTEGRACAO,
    	ImovelAtlzCadastralColunas.DATA_VISITA,
    	ImovelAtlzCadastralColunas.ID_BAIRRO,
    	ImovelAtlzCadastralColunas.CODIGO_CEP,
    	ImovelAtlzCadastralColunas.CODIGO_LOGRADOURO,
    	ImovelAtlzCadastralColunas.LOGIN,
    	ImovelAtlzCadastralColunas.DESCRICAOSETORCOMERCIAL
    };
    
    public static final class ImovelAtlzCadastralColunas implements BaseColumns{
    
    	public static final String ID 			  		 	= "IMAC_ID";        	  
    	public static final String LOGRADOURO_ID  		 	= "LOGR_ID";
    	public static final String LIGAGUASITUACAO_ID 	 	= "LAST_ID";
    	public static final String LIGESGOTOSITUACAO_ID  	= "LEST_ID";
    	public static final String FONTEABASTECIMENTO_ID 	= "FTAB_ID";
    	public static final String PAVIMENTOCALCADA_ID	 	= "PCAL_ID";
    	public static final String ENDERECOREFERENCIA_ID 	= "EDRF_ID";
    	public static final String PAVIMENTORUA_ID		 	= "PRUA_ID";
    	public static final String IMOVEL_ID			 	= "IMOV_ID";
    	public static final String MUNICIPIO_ID			 	= "MUNI_ID";
    	public static final String NOMEMUNICIPIO		 	= "MUNI_NMMUNICIPIO";
    	public static final String LOCALIDADE_ID		 	= "LOCA_ID";
        public static final String NOMELOCALIDADE		 	= "LOCA_NMLOCALIDADE";
        public static final String CODIGOSETORCOMERCIAL	 	= "STCM_CDSETORCOMERCIAL";
        public static final String NUMQUADRA			 	= "QDRA_NNQUADRA";
        public static final String NUMLOTE				 	= "IMAC_NNLOTE";
        public static final String NUMSUBLOTE			 	= "IMAC_NNSUBLOTE";
        public static final String NUMIMOVEL			 	= "IMAC_NNIMOVEL";
        public static final String COMPLENDERECO		 	= "IMAC_DSCOMPLEMENTOENDERECO";
        public static final String LOGRADOUROBAIRRO_ID		= "LGBR_ID";
        public static final String LOGRADOUROCEP_ID		 	= "LGCP_ID";
        public static final String IMOVELPERFIL_ID		 	= "IPER_ID";
        public static final String NUMMEDIDORENERGIA	 	= "IMAC_NNMEDIDORENERGIA";
        public static final String NUMMORADOR			 	= "IMAC_NNMORADOR";
        public static final String INDICADOR_FINALIZADO  	= "IMAC_ICFINALIZADO";
        public static final String OBSERVACAO			 	= "IMAC_DSOBSERVACAO";
        public static final String INDICADOR_TARIFA_SOCIAL 	= "IMAC_ICTARIFASOCIAL";
        public static final String POSICAO			     	= "IMAC_POSICAO";
        public static final String INDICADOR_TRANSMITIDO  	= "IMAC_ICTRANSMITIDO";
        public static final String INTEGRACAO       	    = "IMAC_INTEGRACAOID";
        public static final String DATA_VISITA       	    = "IMAC_DTVISITA";
        public static final String ID_BAIRRO       	    	= "BAIR_ID";
        public static final String CODIGO_CEP       	    = "IMAC_CDCEP";
        public static final String CODIGO_LOGRADOURO        = "IMAC_CDUNICOLOG";
        public static final String LOGIN       				= "IMAC_LOGIN";
        public static final String DESCRICAOSETORCOMERCIAL  = "STCM_DSSETORCOMERCIAL";
    
    }
	
    public final class ImovelAtlzCadastralColunasTipo {
		
    	public final String ID 			  		  	= " INTEGER PRIMARY KEY";        	  
    	public final String LOGRADOURO_ID  		  	= " INTEGER";
    	public final String LIGACAOAGUA_ID 		  	= " INTEGER NOT NULL";
    	public final String LIGACAOESGOTO_ID 	  	= " INTEGER NOT NULL";
    	public final String FONTEABASTECIMENTO_ID 	= " INTEGER";
    	public final String PAVIMENTOCALCADA_ID	  	= " INTEGER";
    	public final String ENDERECOREFERENCIA_ID 	= " INTEGER";
    	public final String PAVIMENTORUA_ID		  	= " INTEGER";
    	public final String IMOVEL_ID			  	= " INTEGER";
    	public final String MUNICIPIO_ID		  	= " INTEGER";
    	public final String NOMEMUNICIPIO		  	= " VARCHAR(30)";
    	public final String LOCALIDADE_ID		  	= " INTEGER NOT NULL";
        public final String NOMELOCALIDADE		  	= " VARCHAR(30)";
        public final String CODIGOSETORCOMERCIAL  	= " INTEGER";
        public final String NUMQUADRA			  	= " INTEGER NOT NULL";
        public final String NUMLOTE				  	= " INTEGER NOT NULL";
        public final String NUMSUBLOTE			  	= " INTEGER NOT NULL";
        public final String NUMIMOVEL			  	= " VARCHAR(5) NOT NULL";
        public final String COMPLENDERECO		  	= " VARCHAR(25)";
        public final String LOGRADOUROBAIRRO_ID		= " INTEGER";
        public final String LOGRADOUROCEP_ID	  	= " INTEGER";
        public final String IMOVELPERFIL_ID		  	= " INTEGER NOT NULL";
        public final String NUMMEDIDORENERGIA	  	= " VARCHAR(10)";
        public final String NUMMORADOR			  	= " INTEGER";
        public final String INDICADOR_FINALIZADO  	= " INTEGER NOT NULL";
        public final String OBSERVACAO			  	= " VARCHAR(400)";
        public final String INDICADOR_TARIFA_SOCIAL = " INTEGER NOT NULL";
        public final String POSICAO  			  	= " INTEGER";
        public final String INDICADOR_TRANSMITIDO  	= " INTEGER NOT NULL";
        public final String INTEGRACAO 				= " VARCHAR(50)";
        public final String DATA_VISITA				= " DATE";
        public final String BAIRRO					= " INTEGER";
        public final String CEP						= " INTEGER";
        public final String CDLOG					= " VARCHAR(40)";
        public final String LOGIN					= " VARCHAR(30)";
        public final String DESCRICAOSETORCOMERCIAL	= " VARCHAR(50)";
		
		private String[] tipos = new String[] {ID, LOGRADOURO_ID, LIGACAOAGUA_ID, LIGACAOESGOTO_ID, 
				FONTEABASTECIMENTO_ID, PAVIMENTOCALCADA_ID, ENDERECOREFERENCIA_ID, PAVIMENTORUA_ID, 
				IMOVEL_ID, MUNICIPIO_ID, NOMEMUNICIPIO, LOCALIDADE_ID, NOMELOCALIDADE, CODIGOSETORCOMERCIAL, 
				NUMQUADRA, NUMLOTE, NUMSUBLOTE, NUMIMOVEL, COMPLENDERECO, LOGRADOUROBAIRRO_ID, LOGRADOUROCEP_ID, 
				IMOVELPERFIL_ID, NUMMEDIDORENERGIA, NUMMORADOR, INDICADOR_FINALIZADO, OBSERVACAO, INDICADOR_TARIFA_SOCIAL, POSICAO,
				INDICADOR_TRANSMITIDO,INTEGRACAO,DATA_VISITA, BAIRRO, CEP, CDLOG, LOGIN, DESCRICAOSETORCOMERCIAL};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static ImovelAtlzCadastral inserirDoArquivo(List<String> c, Integer posicao ) {
    	
    	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
    	
    	imovelAtlzCadastral.setPosicao(posicao);
    	
    	imovelAtlzCadastral.setId(c.get(IMAC_ID_INDEX));
    	
        Logradouro _logradouro = new Logradouro();
        _logradouro.setId(c.get(LOGRADOURO_LOGR_ID_INDEX));
        imovelAtlzCadastral.setLogradouro(_logradouro);
        
        LigacaoAguaSituacao _ligAguaSituacao = new LigacaoAguaSituacao();
        _ligAguaSituacao.setId(c.get(LAST_ID_INDEX));
        imovelAtlzCadastral.setLigAguaSituacao(_ligAguaSituacao);

        LigacaoEsgotoSituacao _ligEsgotoSituacao = new LigacaoEsgotoSituacao();
        _ligEsgotoSituacao.setId(c.get(LEST_ID_INDEX));
        imovelAtlzCadastral.setLigEsgotoSituacao(_ligEsgotoSituacao);
        
        FonteAbastecimento _fonteAbastecimento = new FonteAbastecimento();
        _fonteAbastecimento.setId(c.get(FTAB_ID_INDEX));
        imovelAtlzCadastral.setFonteAbastecimento(_fonteAbastecimento);
        
        PavimentoCalcada _pavimentoCalcada = new PavimentoCalcada();
        _pavimentoCalcada.setId(c.get(PCAL_ID_INDEX));
        imovelAtlzCadastral.setPavimentoCalcada(_pavimentoCalcada);
        
        EnderecoReferencia _enderecoReferencia = new EnderecoReferencia();
        _enderecoReferencia.setId(Util.verificarNuloInt(c.get(EDRF_ID_INDEX)));
        imovelAtlzCadastral.setEnderecoReferencia(_enderecoReferencia);
        
        PavimentoRua _pavimentoRua = new PavimentoRua();
        _pavimentoRua.setId(Util.verificarNuloInt(c.get(PRUA_ID_INDEX)));
        imovelAtlzCadastral.setPavimentoRua(_pavimentoRua);
        
        ImovelPerfil _imovelPerfil = new ImovelPerfil();
        _imovelPerfil.setId(Util.verificarNuloInt(c.get(IPER_ID_INDEX)));
        imovelAtlzCadastral.setImovelPerfil(_imovelPerfil);
        
        imovelAtlzCadastral.setImovelId(Util.verificarNuloInt(c.get(IMOV_ID_INDEX)));
        imovelAtlzCadastral.setMunicipioId(Util.verificarNuloInt(c.get(MUNI_ID_INDEX)));
        imovelAtlzCadastral.setNomeMunicipio(c.get(MUNI_NMMUNICIPIO_INDEX));
        imovelAtlzCadastral.setLocalidadeId(Util.verificarNuloInt(c.get(LOCA_ID_INDEX)));
        imovelAtlzCadastral.setNomeLocalidade(c.get(LOCA_NMLOCALIDADE_INDEX));
        imovelAtlzCadastral.setCodigoSetorComercial(Util.verificarNuloInt(c.get(STCM_CDSETORCOMERCIAL_INDEX)));
        imovelAtlzCadastral.setNumeroQuadra(Util.verificarNuloInt(c.get(QDRA_NNQUADRA_INDEX)));
        imovelAtlzCadastral.setNumeroLote(Util.verificarNuloInt(c.get(IMAC_NNLOTE_INDEX)));
        imovelAtlzCadastral.setNumeroSubLote(Util.verificarNuloInt(c.get(IMAC_NNSUBLOTE_INDEX)));
        
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
        
        
        
        imovelAtlzCadastral.setLogradouroBairroId(Util.verificarNuloInt(c.get(LGBR_ID_INDEX)));
        imovelAtlzCadastral.setLogradouroCEPId(Util.verificarNuloInt(c.get(LGCP_ID_INDEX)));
        imovelAtlzCadastral.setNumeroMedidorEnergia(c.get(IMAC_NNMEDIDORENERGIA_INDEX));
        imovelAtlzCadastral.setNumeroMorador(Util.verificarNuloInt(c.get(IMAC_NNMORADOR_INDEX)));
        imovelAtlzCadastral.setIndicadorTarifaSocial(Util.verificarNuloInt(c.get(IMAC_ICTARIFASOCIAL_INDEX)));
        imovelAtlzCadastral.setIndicadorFinalizado(ConstantesSistema.PENDENTE);
        imovelAtlzCadastral.setIndicadorTransmitido(ConstantesSistema.NAO);       
        imovelAtlzCadastral.setIdBairro(Util.verificarNuloInt(c.get(IMAC_IDBAIRRO_INDEX)));
        imovelAtlzCadastral.setCodigoCep(Util.verificarNuloInt(c.get(IMAC_CDCEP_INDEX))); 
        imovelAtlzCadastral.setDescricaoSetorComercial(c.get(STCM_DSSETORCOMERCIAL_INDEX));
     
        return imovelAtlzCadastral;
    }
    
    public static ImovelAtlzCadastral inserirAtualizarDoArquivoDividido(List<String> c, Integer posicao, ImovelAtlzCadastral imovelAtlzCadastral , Logradouro logradouro) {
    	
    	//caso seja imovel novo atualiza a posicao do imovel
    	if ( posicao != null ) {
    		imovelAtlzCadastral.setPosicao(posicao);
    	} else {
    		//caso o imovel ja seja cadastrado  no tablet, informa o id para atualizacao.
    		imovelAtlzCadastral.setId(c.get(AD_IMAC_ID_INDEX));
    	}
    	
    	
    	
        if ( logradouro != null ) {
        	imovelAtlzCadastral.setLogradouro(logradouro);
        	imovelAtlzCadastral.setCodigoUnicoLogradouro(logradouro.getCodigoUnico());
        } else {
        	Logradouro _logradouro = new Logradouro();
            _logradouro.setId(c.get(AD_LOGRADOURO_LOGR_ID_INDEX));        	
        }
        
        LigacaoAguaSituacao _ligAguaSituacao = new LigacaoAguaSituacao();
        _ligAguaSituacao.setId(c.get(AD_LAST_ID_INDEX));
        imovelAtlzCadastral.setLigAguaSituacao(_ligAguaSituacao);

        LigacaoEsgotoSituacao _ligEsgotoSituacao = new LigacaoEsgotoSituacao();
        _ligEsgotoSituacao.setId(c.get(AD_LEST_ID_INDEX));
        imovelAtlzCadastral.setLigEsgotoSituacao(_ligEsgotoSituacao);
        
        FonteAbastecimento _fonteAbastecimento = new FonteAbastecimento();
        _fonteAbastecimento.setId(c.get(AD_FTAB_ID_INDEX));
        imovelAtlzCadastral.setFonteAbastecimento(_fonteAbastecimento);
        
        PavimentoCalcada _pavimentoCalcada = new PavimentoCalcada();
        _pavimentoCalcada.setId(c.get(AD_PCAL_ID_INDEX));
        imovelAtlzCadastral.setPavimentoCalcada(_pavimentoCalcada);
        
        EnderecoReferencia _enderecoReferencia = new EnderecoReferencia();
        _enderecoReferencia.setId(Util.verificarNuloInt(c.get(AD_EDRF_ID_INDEX)));
        imovelAtlzCadastral.setEnderecoReferencia(_enderecoReferencia);
        
        PavimentoRua _pavimentoRua = new PavimentoRua();
        _pavimentoRua.setId(Util.verificarNuloInt(c.get(AD_PRUA_ID_INDEX)));
        imovelAtlzCadastral.setPavimentoRua(_pavimentoRua);
        
        ImovelPerfil _imovelPerfil = new ImovelPerfil();
        _imovelPerfil.setId(Util.verificarNuloInt(c.get(AD_IPER_ID_INDEX)));
        imovelAtlzCadastral.setImovelPerfil(_imovelPerfil);
        
        imovelAtlzCadastral.setImovelId(Util.verificarNuloInt(c.get(AD_IMOV_ID_INDEX)));
        imovelAtlzCadastral.setMunicipioId(Util.verificarNuloInt(c.get(AD_MUNI_ID_INDEX)));
        imovelAtlzCadastral.setLocalidadeId(Util.verificarNuloInt(c.get(AD_LOCA_ID_INDEX)));
        imovelAtlzCadastral.setCodigoSetorComercial(Util.verificarNuloInt(c.get(AD_STCM_CDSETORCOMERCIAL_INDEX)));
        imovelAtlzCadastral.setNumeroQuadra(Util.verificarNuloInt(c.get(AD_QDRA_NNQUADRA_INDEX)));
        imovelAtlzCadastral.setNumeroLote(Util.verificarNuloInt(c.get(AD_IMAC_NNLOTE_INDEX)));
        imovelAtlzCadastral.setNumeroSubLote(Util.verificarNuloInt(c.get(AD_IMAC_NNSUBLOTE_INDEX)));
        imovelAtlzCadastral.setNumeroImovel(c.get(AD_IMAC_NNIMOVEL_INDEX));
        imovelAtlzCadastral.setComplementoEndereco(c.get(AD_IMAC_DSCOMPLEMENTOENDERECO_INDEX));
        imovelAtlzCadastral.setLogradouroBairroId(Util.verificarNuloInt(c.get(AD_LGBR_ID_INDEX)));
        imovelAtlzCadastral.setLogradouroCEPId(Util.verificarNuloInt(c.get(AD_LGCP_ID_INDEX)));
        imovelAtlzCadastral.setNumeroMedidorEnergia(c.get(AD_IMAC_NNMEDIDORENERGIA_INDEX));
        imovelAtlzCadastral.setNumeroMorador(Util.verificarNuloInt(c.get(AD_IMAC_NNMORADOR_INDEX)));
        imovelAtlzCadastral.setIndicadorTarifaSocial(Util.verificarNuloInt(c.get(AD_IMAC_ICTARIFASOCIAL_INDEX)));
        imovelAtlzCadastral.setIndicadorFinalizado(ConstantesSistema.SIM);
        imovelAtlzCadastral.setIndicadorTransmitido(ConstantesSistema.NAO);
        imovelAtlzCadastral.setIdBairro(Util.verificarNuloInt(c.get(AD_IMAC_IDBAIRRO_INDEX)));
        imovelAtlzCadastral.setCodigoCep(Util.verificarNuloInt(c.get(AD_IMAC_CDCEP_INDEX)));
        imovelAtlzCadastral.setObservacao(c.get(AD_IMAC_OBSERVACAO));
        imovelAtlzCadastral.setDataVisita(Util.converteStringParaDate(c.get(AD_IMAC_DATA_VISITA)));
        imovelAtlzCadastral.setIntegracaoID(c.get(AD_IMAC_INTEGRACAO_ID));
        imovelAtlzCadastral.setLogin(c.get(AD_IMAC_LOGIN));
        imovelAtlzCadastral.setNomeMunicipio(c.get(AD_IMAC_NOMEMUNICIPIO));
        
        return imovelAtlzCadastral;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(ImovelAtlzCadastralColunas.ID, getId());
		values.put(ImovelAtlzCadastralColunas.LOGRADOURO_ID, getLogradouro().getId());
		values.put(ImovelAtlzCadastralColunas.LIGAGUASITUACAO_ID, getLigAguaSituacao().getId());
		values.put(ImovelAtlzCadastralColunas.LIGESGOTOSITUACAO_ID, getLigEsgotoSituacao().getId());
		values.put(ImovelAtlzCadastralColunas.FONTEABASTECIMENTO_ID, getFonteAbastecimento().getId());
		values.put(ImovelAtlzCadastralColunas.PAVIMENTOCALCADA_ID, getPavimentoCalcada().getId());
		
		if ( getEnderecoReferencia() != null && getEnderecoReferencia().getId() != null) {
			values.put(ImovelAtlzCadastralColunas.ENDERECOREFERENCIA_ID, getEnderecoReferencia().getId());
		}
		
		values.put(ImovelAtlzCadastralColunas.PAVIMENTORUA_ID, getPavimentoRua().getId());
		values.put(ImovelAtlzCadastralColunas.IMOVEL_ID, getImovelId());
		values.put(ImovelAtlzCadastralColunas.MUNICIPIO_ID, getMunicipioId());
		values.put(ImovelAtlzCadastralColunas.NOMEMUNICIPIO, getNomeMunicipio());
		values.put(ImovelAtlzCadastralColunas.LOCALIDADE_ID, getLocalidadeId());
		values.put(ImovelAtlzCadastralColunas.NOMELOCALIDADE, getNomeLocalidade());
		values.put(ImovelAtlzCadastralColunas.CODIGOSETORCOMERCIAL, getCodigoSetorComercial());
		values.put(ImovelAtlzCadastralColunas.NUMQUADRA, getNumeroQuadra());
		values.put(ImovelAtlzCadastralColunas.NUMLOTE, getNumeroLote());
		values.put(ImovelAtlzCadastralColunas.NUMSUBLOTE, getNumeroSubLote());
		values.put(ImovelAtlzCadastralColunas.NUMIMOVEL, getNumeroImovel());
		
		if ( getComplementoEndereco() != null && !getComplementoEndereco().equals("") ) {
			values.put(ImovelAtlzCadastralColunas.COMPLENDERECO, getComplementoEndereco());
		}else{
			values.putNull(ImovelAtlzCadastralColunas.COMPLENDERECO);
		}
		
		values.put(ImovelAtlzCadastralColunas.LOGRADOUROBAIRRO_ID, getLogradouroBairroId());
		values.put(ImovelAtlzCadastralColunas.LOGRADOUROCEP_ID, getLogradouroCEPId());
		values.put(ImovelAtlzCadastralColunas.IMOVELPERFIL_ID, getImovelPerfil().getId());
		
		if ( getNumeroMedidorEnergia() != null && !getNumeroMedidorEnergia().equals("") ) {
			values.put(ImovelAtlzCadastralColunas.NUMMEDIDORENERGIA, getNumeroMedidorEnergia());
		}else{
			values.putNull(ImovelAtlzCadastralColunas.NUMMEDIDORENERGIA);
		}
		
		values.put(ImovelAtlzCadastralColunas.NUMMORADOR, getNumeroMorador());
		values.put(ImovelAtlzCadastralColunas.INDICADOR_FINALIZADO, getIndicadorFinalizado());
		values.put(ImovelAtlzCadastralColunas.OBSERVACAO, getObservacao());
		values.put(ImovelAtlzCadastralColunas.INDICADOR_TARIFA_SOCIAL, getIndicadorTarifaSocial());
		values.put(ImovelAtlzCadastralColunas.POSICAO, getPosicao());
		values.put(ImovelAtlzCadastralColunas.INDICADOR_TRANSMITIDO, getIndicadorTransmitido());
		
		values.put(ImovelAtlzCadastralColunas.INTEGRACAO, getIntegracaoID());
		
		if(getDataVisita() != null){
			values.put(ImovelAtlzCadastralColunas.DATA_VISITA, Util.dateFormatDB.format(getDataVisita()));
		}else{
			values.putNull(ImovelAtlzCadastralColunas.DATA_VISITA);
		}
		
		
		values.put(ImovelAtlzCadastralColunas.ID_BAIRRO, getIdBairro());
		values.put(ImovelAtlzCadastralColunas.CODIGO_CEP, getCodigoCep());
		values.put(ImovelAtlzCadastralColunas.CODIGO_LOGRADOURO, getCodigoUnicoLogradouro());
		values.put(ImovelAtlzCadastralColunas.LOGIN, getLogin());
		
		values.put(ImovelAtlzCadastralColunas.DESCRICAOSETORCOMERCIAL, getDescricaoSetorComercial());
		

		return values;
	}
    
	public ArrayList<ImovelAtlzCadastral> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<ImovelAtlzCadastral> listaImovelAtlzCadastral = new ArrayList<ImovelAtlzCadastral>();
		
		if ( cursor.moveToFirst() ) {
		
			do{
				int _codigo 	  	 	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.ID);
				int _logradouroId 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LOGRADOURO_ID);
				int _ligAguaSituacaoId 	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LIGAGUASITUACAO_ID);
				int _ligEsgotoSituacaoId  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LIGESGOTOSITUACAO_ID);
				int _fonteAbastecimentoId = cursor.getColumnIndex(ImovelAtlzCadastralColunas.FONTEABASTECIMENTO_ID);
				int _pavimentoCalcadaId   = cursor.getColumnIndex(ImovelAtlzCadastralColunas.PAVIMENTOCALCADA_ID);
				int _enderecoReferenciaId = cursor.getColumnIndex(ImovelAtlzCadastralColunas.ENDERECOREFERENCIA_ID);
				int _pavimentoRuaId 	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.PAVIMENTORUA_ID);
				int _imovelId 			  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.IMOVEL_ID);
				int _municipioId 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.MUNICIPIO_ID);
				int _nomeMunicipio 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NOMEMUNICIPIO);
				int _localidadeId 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LOCALIDADE_ID);
				int _nomeLocalidade 	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NOMELOCALIDADE);
				int _codigoSetorComercial = cursor.getColumnIndex(ImovelAtlzCadastralColunas.CODIGOSETORCOMERCIAL);
				int _numeroQuadra 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NUMQUADRA);
				int _numeroLote 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NUMLOTE);
				int _numeroSubLote 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NUMSUBLOTE);
				int _numeroImovel 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NUMIMOVEL);
				int _complementoEndereco  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.COMPLENDERECO);
				int _logradouroBairroId   = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LOGRADOUROBAIRRO_ID);
				int _logradouroCEPId 	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LOGRADOUROCEP_ID);
				int _imovelPerfilId 	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.IMOVELPERFIL_ID);
				int _numeroMedidorEnergia = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NUMMEDIDORENERGIA);
				int _numeroMorador 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NUMMORADOR);
				int _observacao 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.OBSERVACAO);
				int _icTarifaSocial		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.INDICADOR_TARIFA_SOCIAL);
				int _posicao 		      = cursor.getColumnIndex(ImovelAtlzCadastralColunas.POSICAO);
				int _indicadorFinalizado  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.INDICADOR_FINALIZADO);
				int _indicadorTransmitido = cursor.getColumnIndex(ImovelAtlzCadastralColunas.INDICADOR_TRANSMITIDO);
				int _integracao           = cursor.getColumnIndex(ImovelAtlzCadastralColunas.INTEGRACAO);
				int _dataVisita           = cursor.getColumnIndex(ImovelAtlzCadastralColunas.DATA_VISITA);
				int _bairro           	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.ID_BAIRRO);
				int cep          		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.CODIGO_CEP);
				int cdLogradouroUnico     = cursor.getColumnIndex(ImovelAtlzCadastralColunas.CODIGO_LOGRADOURO);
				int login     			  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LOGIN);
				int descricaoSetorComercial = cursor.getColumnIndex(ImovelAtlzCadastralColunas.DESCRICAOSETORCOMERCIAL);
				
				ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
					
				imovelAtlzCadastral.setId(cursor.getInt(_codigo));
				
		        Logradouro _logradouro = new Logradouro();
		        _logradouro.setId(cursor.getInt(_logradouroId));
		        imovelAtlzCadastral.setLogradouro(_logradouro);
		        
		        LigacaoAguaSituacao _ligAguaSituacao = new LigacaoAguaSituacao();
		        _ligAguaSituacao.setId(cursor.getInt(_ligAguaSituacaoId));
		        imovelAtlzCadastral.setLigAguaSituacao(_ligAguaSituacao);
	
		        LigacaoEsgotoSituacao _ligEsgotoSituacao = new LigacaoEsgotoSituacao();
		        _ligEsgotoSituacao.setId(cursor.getInt(_ligEsgotoSituacaoId));
		        imovelAtlzCadastral.setLigEsgotoSituacao(_ligEsgotoSituacao);
		        
		        FonteAbastecimento _fonteAbastecimento = new FonteAbastecimento();
		        _fonteAbastecimento.setId(cursor.getInt(_fonteAbastecimentoId));
		        imovelAtlzCadastral.setFonteAbastecimento(_fonteAbastecimento);
		        
		        PavimentoCalcada _pavimentoCalcada = new PavimentoCalcada();
		        _pavimentoCalcada.setId(cursor.getInt(_pavimentoCalcadaId));
		        imovelAtlzCadastral.setPavimentoCalcada(_pavimentoCalcada);
		        
		        EnderecoReferencia _enderecoReferencia = new EnderecoReferencia();
		        _enderecoReferencia.setId(cursor.getInt(_enderecoReferenciaId));
		        imovelAtlzCadastral.setEnderecoReferencia(_enderecoReferencia);
		        
		        PavimentoRua _pavimentoRua = new PavimentoRua();
		        _pavimentoRua.setId(cursor.getInt(_pavimentoRuaId));
		        imovelAtlzCadastral.setPavimentoRua(_pavimentoRua);
		        
		        ImovelPerfil _imovelPerfil = new ImovelPerfil();
		        _imovelPerfil.setId(cursor.getInt(_imovelPerfilId));
		        imovelAtlzCadastral.setImovelPerfil(_imovelPerfil);
				
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
				imovelAtlzCadastral.setCodigoCep(cursor.getInt(cep));
				imovelAtlzCadastral.setCodigoUnicoLogradouro(cursor.getString(cdLogradouroUnico));
				imovelAtlzCadastral.setLogin(cursor.getString(login));
				
				if(cursor.getString(_dataVisita) != null && !cursor.getString(_dataVisita).equals("")){
					imovelAtlzCadastral.setDataVisita(Util.convertStringToDate(cursor.getString(_dataVisita)));
		    	}
				
				imovelAtlzCadastral.setDescricaoSetorComercial(cursor.getString(descricaoSetorComercial));
				
				listaImovelAtlzCadastral.add(imovelAtlzCadastral);
			
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return listaImovelAtlzCadastral;
	}
	
	public ImovelAtlzCadastral carregarEntidade(Cursor cursor) {		
		
		int _codigo 	  	 	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.ID);
		int _logradouroId 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LOGRADOURO_ID);
		int _ligAguaSituacaoId 	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LIGAGUASITUACAO_ID);
		int _ligEsgotoSituacaoId  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LIGESGOTOSITUACAO_ID);
		int _fonteAbastecimentoId = cursor.getColumnIndex(ImovelAtlzCadastralColunas.FONTEABASTECIMENTO_ID);
		int _pavimentoCalcadaId   = cursor.getColumnIndex(ImovelAtlzCadastralColunas.PAVIMENTOCALCADA_ID);
		int _enderecoReferenciaId = cursor.getColumnIndex(ImovelAtlzCadastralColunas.ENDERECOREFERENCIA_ID);
		int _pavimentoRuaId 	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.PAVIMENTORUA_ID);
		int _imovelId 			  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.IMOVEL_ID);
		int _municipioId 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.MUNICIPIO_ID);
		int _nomeMunicipio 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NOMEMUNICIPIO);
		int _localidadeId 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LOCALIDADE_ID);
		int _nomeLocalidade 	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NOMELOCALIDADE);
		int _codigoSetorComercial = cursor.getColumnIndex(ImovelAtlzCadastralColunas.CODIGOSETORCOMERCIAL);
		int _numeroQuadra 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NUMQUADRA);
		int _numeroLote 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NUMLOTE);
		int _numeroSubLote 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NUMSUBLOTE);
		int _numeroImovel 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NUMIMOVEL);
		int _complementoEndereco  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.COMPLENDERECO);
		int _logradouroBairroId   = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LOGRADOUROBAIRRO_ID);
		int _logradouroCEPId 	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LOGRADOUROCEP_ID);
		int _imovelPerfilId 	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.IMOVELPERFIL_ID);
		int _numeroMedidorEnergia = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NUMMEDIDORENERGIA);
		int _numeroMorador 		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.NUMMORADOR);
		int _indicadorFinalizado  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.INDICADOR_FINALIZADO);
		int _observacao			  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.OBSERVACAO);
		int _icTarifaSocial		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.INDICADOR_TARIFA_SOCIAL);
		int _posicao			  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.POSICAO);
		int _indicadorTransmitido = cursor.getColumnIndex(ImovelAtlzCadastralColunas.INDICADOR_TRANSMITIDO);
		int _integracao           = cursor.getColumnIndex(ImovelAtlzCadastralColunas.INTEGRACAO);
		int _dataVisita           = cursor.getColumnIndex(ImovelAtlzCadastralColunas.DATA_VISITA);
		int _bairro           	  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.ID_BAIRRO);
		int cep           		  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.CODIGO_CEP);
		int cdLogradouroUnico     = cursor.getColumnIndex(ImovelAtlzCadastralColunas.CODIGO_LOGRADOURO);
		int login     			  = cursor.getColumnIndex(ImovelAtlzCadastralColunas.LOGIN);
		int descricaoSetorComercial = cursor.getColumnIndex(ImovelAtlzCadastralColunas.DESCRICAOSETORCOMERCIAL);
		
		ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();

		if ( cursor.moveToFirst() ) {
			
			imovelAtlzCadastral.setId(cursor.getInt(_codigo));
			
	        Logradouro _logradouro = new Logradouro();
	        _logradouro.setId(cursor.getInt(_logradouroId));
	        imovelAtlzCadastral.setLogradouro(_logradouro);
	        
	        LigacaoAguaSituacao _ligAguaSituacao = new LigacaoAguaSituacao();
	        _ligAguaSituacao.setId(cursor.getInt(_ligAguaSituacaoId));
	        imovelAtlzCadastral.setLigAguaSituacao(_ligAguaSituacao);

	        LigacaoEsgotoSituacao _ligEsgotoSituacao = new LigacaoEsgotoSituacao();
	        _ligEsgotoSituacao.setId(cursor.getInt(_ligEsgotoSituacaoId));
	        imovelAtlzCadastral.setLigEsgotoSituacao(_ligEsgotoSituacao);
	        
	        FonteAbastecimento _fonteAbastecimento = new FonteAbastecimento();
	        _fonteAbastecimento.setId(cursor.getInt(_fonteAbastecimentoId));
	        imovelAtlzCadastral.setFonteAbastecimento(_fonteAbastecimento);
	        
	        PavimentoCalcada _pavimentoCalcada = new PavimentoCalcada();
	        _pavimentoCalcada.setId(cursor.getInt(_pavimentoCalcadaId));
	        imovelAtlzCadastral.setPavimentoCalcada(_pavimentoCalcada);
	        
	        EnderecoReferencia _enderecoReferencia = new EnderecoReferencia();
	        _enderecoReferencia.setId(cursor.getInt(_enderecoReferenciaId));
	        imovelAtlzCadastral.setEnderecoReferencia(_enderecoReferencia);
	        
	        PavimentoRua _pavimentoRua = new PavimentoRua();
	        _pavimentoRua.setId(cursor.getInt(_pavimentoRuaId));
	        imovelAtlzCadastral.setPavimentoRua(_pavimentoRua);
	        
	        ImovelPerfil _imovelPerfil = new ImovelPerfil();
	        _imovelPerfil.setId(cursor.getInt(_imovelPerfilId));
	        imovelAtlzCadastral.setImovelPerfil(_imovelPerfil);
			
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
			imovelAtlzCadastral.setCodigoCep(cursor.getInt(cep));
			imovelAtlzCadastral.setCodigoUnicoLogradouro(cursor.getString(cdLogradouroUnico));
			imovelAtlzCadastral.setLogin(cursor.getString(login));
			
			if ( cursor.getString(_dataVisita) != null && !cursor.getString(_dataVisita).equals("") ) {
				imovelAtlzCadastral.setDataVisita(Util.convertStringToDate(cursor.getString(_dataVisita)));
	    	}
			
			imovelAtlzCadastral.setDescricaoSetorComercial(cursor.getString(descricaoSetorComercial));
		}

		cursor.close();
		return imovelAtlzCadastral;
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

	public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "IMOVEL_ATLZ_CADASTRAL";
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

	public String getDescricaoSetorComercial() {
		return descricaoSetorComercial;
	}

	public void setDescricaoSetorComercial(String descricaoSetorComercial) {
		this.descricaoSetorComercial = descricaoSetorComercial;
	}
	
	public ImovelAtlzCadastral clone() throws CloneNotSupportedException {
		return (ImovelAtlzCadastral) super.clone();
	}

}
