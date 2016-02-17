package com.br.gsanac.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.br.gsanac.util.Util;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * <p>
 * Classe responsavel pelo objeto ClienteAtlzCadastralastral
 * </p>
 * 
 * @author Anderson Cabral
 * @since  11/12/2012
 */
public class ClienteAtlzCadastral extends EntidadeBase {

	private static final long serialVersionUID = 1L;
	
    private static final int CLAC_ID_INDEX = 1;
    private static final int IMAC_ID_INDEX = 2;
    private static final int CLTP_ID_INDEX = 3;
    private static final int CLAC_NNCPFCNPPJ_INDEX 		= 4;
    private static final int CLAC_NMCLIENTE_INDEX 		= 5;
    private static final int CLAC_NNRG_INDEX 			= 6;
    private static final int OERG_ID_INDEX = 7;
    private static final int UNFE_ID_INDEX 	= 8;
    private static final int PSEX_ID_INDEX = 9;
    private static final int CLAC_DTRGEMISSAO_INDEX		= 10;
    private static final int CLAC_DTNASCIMENTO_INDEX	= 11;
    private static final int CLAC_ID_CLIENTE	= 12;
    
    
    //Carregar arquivo dividido.
    private static final int AD_CLTP_ID_INDEX = 2;
    private static final int AD_CLAC_NNCPFCNPPJ_INDEX 		= 3;
    private static final int AD_CLAC_NMCLIENTE_INDEX 		= 4;
    private static final int AD_CLAC_NNRG_INDEX 			= 5;
    private static final int AD_OERG_ID_INDEX = 6;
    private static final int AD_UNFE_ID_INDEX 	= 7;
    private static final int AD_CLAC_DTRGEMISSAO_INDEX		= 8;
    private static final int AD_PSEX_ID_INDEX = 9;
    private static final int AD_CLAC_DTNASCIMENTO_INDEX	= 10;
    private static final int AD_CLAC_ID_CLIENTE	= 11;
    
    
    
    
    private ImovelAtlzCadastral imovelAtlzCadastral;
    private PessoaSexo 	  pessoaSexo;
    private ClienteTipo clienteTipo;
	private String numeroCPFCNPPJ;
    private String nomeCliente;
    private String numeroRG;
    private OrgaoExpedidorRg orgaoExpedidorRg;
    private UnidadeFederacao unidadeFederacao;
    private Date dataEmissaoRg;
    private Date dataNascimento;
    private Integer idCliente;
	
    public static final String[] columns = new String[]{
    	ClienteAtlzCadastralColunas.ID,
    	ClienteAtlzCadastralColunas.IMOVELATLZCAD_ID,
    	ClienteAtlzCadastralColunas.PESSOASEXO_ID,
    	ClienteAtlzCadastralColunas.CLIENTETIPO_ID,
    	ClienteAtlzCadastralColunas.NUMCPFCNPJ,
    	ClienteAtlzCadastralColunas.NOMECLIENTE,
    	ClienteAtlzCadastralColunas.NUMERORG,
    	ClienteAtlzCadastralColunas.ORGAOEXPEDIDOR_ID,
    	ClienteAtlzCadastralColunas.UNIDADEFEDERACAO_ID,
    	ClienteAtlzCadastralColunas.DATAEMISSAORG,
    	ClienteAtlzCadastralColunas.DATANASCIMENTO,
    	ClienteAtlzCadastralColunas.ID_CLIENTE
    };
    
    public static final class ClienteAtlzCadastralColunas implements BaseColumns{
    	public static final String ID 		 		   = "CLAC_ID";
    	public static final String IMOVELATLZCAD_ID    = "IMAC_ID";
    	public static final String PESSOASEXO_ID 	   = "PSEX_ID";
    	public static final String CLIENTETIPO_ID 	   = "CLTP_ID";
    	public static final String NUMCPFCNPJ 		   = "CLAC_NNCPFCNPPJ";
    	public static final String NOMECLIENTE 		   = "CLAC_NMCLIENTE";
    	public static final String NUMERORG 		   = "CLAC_NNRG";
    	public static final String ORGAOEXPEDIDOR_ID   = "OERG_ID";
    	public static final String UNIDADEFEDERACAO_ID = "UNFE_ID";
    	public static final String DATAEMISSAORG	   = "CLAC_DTRGEMISSAO";
    	public static final String DATANASCIMENTO	   = "CLAC_DTNASCIMENTO";
    	public static final String ID_CLIENTE	   = "CLAC_IDCLIENTE";
    }
	
    public final class ClienteAtlzCadastralColunasTipo {
		public final String ID 		  		    = " INTEGER PRIMARY KEY";
    	public final String IMOVELATLZCAD_ID    = " INTEGER";
    	public final String PESSOASEXO_ID 	    = " INTEGER";
    	public final String CLIENTETIPO_ID 		= " INTEGER NOT NULL";
    	public final String NUMCPFCNPJ 	 	    = " VARCHAR(14)";
    	public final String NOMECLIENTE 	    = " VARCHAR(50)";
    	public final String NUMERORG 	 	    = " VARCHAR(13)";
    	public final String ORGAOEXPEDIDOR_ID   = " INTEGER";
    	public final String UNIDADEFEDERACAO_ID = " INTEGER";
    	public final String DATAEMISSAORG		= " DATE";
    	public final String DATANASCIMENTO		= " DATE";
    	public final String ID_CLIENTE		= " INTEGER";
		
		private String[] tipos = new String[] {ID, IMOVELATLZCAD_ID, PESSOASEXO_ID,  
											CLIENTETIPO_ID, NUMCPFCNPJ, NOMECLIENTE, NUMERORG, 
											ORGAOEXPEDIDOR_ID, UNIDADEFEDERACAO_ID, 
											DATAEMISSAORG, DATANASCIMENTO, ID_CLIENTE};	
		
		public String[] getTipos(){
			return tipos;
		}
	}
      
    public static ClienteAtlzCadastral inserirDoArquivo(List<String> c) {
    	
    	ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();

    	clienteAtlzCadastral.setId(c.get(CLAC_ID_INDEX));
    	
    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
    	_imovelAtlzCadastral.setId(c.get(IMAC_ID_INDEX));
    	clienteAtlzCadastral.setImovelAtlzCadastral(_imovelAtlzCadastral);
    	
    	if(c.get(PSEX_ID_INDEX) != null && !c.get(PSEX_ID_INDEX).toString().equals("")){
	    	PessoaSexo _pessoaSexo = new PessoaSexo();
	    	_pessoaSexo.setId(c.get(PSEX_ID_INDEX));
	    	clienteAtlzCadastral.setPessoaSexo(_pessoaSexo);
    	}
    	
    	ClienteTipo _clienteTipo = new ClienteTipo();
    	_clienteTipo.setId(c.get(CLTP_ID_INDEX));
    	clienteAtlzCadastral.setClienteTipo(_clienteTipo);
    	
    	clienteAtlzCadastral.setNumeroCPFCNPPJ(c.get(CLAC_NNCPFCNPPJ_INDEX));
    	clienteAtlzCadastral.setNomeCliente(c.get(CLAC_NMCLIENTE_INDEX));
    	clienteAtlzCadastral.setNumeroRG(c.get(CLAC_NNRG_INDEX));
    	
    	if(c.get(CLAC_DTRGEMISSAO_INDEX) != null && !c.get(CLAC_DTRGEMISSAO_INDEX).toString().equals("")){
    		clienteAtlzCadastral.setDataEmissaoRg(Util.converteStringParaDate(c.get(CLAC_DTRGEMISSAO_INDEX)));
    	}
    	
    	if(c.get(CLAC_DTNASCIMENTO_INDEX) != null && !c.get(CLAC_DTNASCIMENTO_INDEX).toString().equals("")){
    		clienteAtlzCadastral.setDataNascimento(Util.converteStringParaDate(c.get(CLAC_DTNASCIMENTO_INDEX)));
    	}
    	
    	if(c.get(OERG_ID_INDEX) != null && !c.get(OERG_ID_INDEX).toString().equals("")){
	    	OrgaoExpedidorRg _orgaoExpedidorRg = new OrgaoExpedidorRg();
	    	_orgaoExpedidorRg.setId(c.get(OERG_ID_INDEX));
	    	clienteAtlzCadastral.setOrgaoExpedidorRg(_orgaoExpedidorRg);
    	}
    	
    	if(c.get(UNFE_ID_INDEX) != null && !c.get(UNFE_ID_INDEX).toString().equals("")){
	        UnidadeFederacao _unidadeFederacao = new UnidadeFederacao();
	    	_unidadeFederacao.setId(c.get(UNFE_ID_INDEX));
	    	clienteAtlzCadastral.setUnidadeFederacao(_unidadeFederacao);
    	}
    	
    	if ( c.get(CLAC_ID_CLIENTE) != null ) {
    		clienteAtlzCadastral.setIdCliente(Integer.valueOf(c.get(CLAC_ID_CLIENTE)));
    	}
    	
        return clienteAtlzCadastral;
    }
    
    
    public static ClienteAtlzCadastral inserirAtualizarDoArquivoDividido(List<String> c, Long idImovelAtlzCad) {
    	
    	ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();

    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
    	_imovelAtlzCadastral.setId(idImovelAtlzCad.intValue());
    	clienteAtlzCadastral.setImovelAtlzCadastral(_imovelAtlzCadastral);
    	
    	if(c.get(AD_PSEX_ID_INDEX) != null && !c.get(AD_PSEX_ID_INDEX).toString().equals("")){
	    	PessoaSexo _pessoaSexo = new PessoaSexo();
	    	_pessoaSexo.setId(c.get(AD_PSEX_ID_INDEX));
	    	clienteAtlzCadastral.setPessoaSexo(_pessoaSexo);
    	}
    	
    	ClienteTipo _clienteTipo = new ClienteTipo();
    	_clienteTipo.setId(c.get(AD_CLTP_ID_INDEX));
    	clienteAtlzCadastral.setClienteTipo(_clienteTipo);
    	
    	clienteAtlzCadastral.setNumeroCPFCNPPJ(c.get(AD_CLAC_NNCPFCNPPJ_INDEX));
    	clienteAtlzCadastral.setNomeCliente(c.get(AD_CLAC_NMCLIENTE_INDEX));
    	clienteAtlzCadastral.setNumeroRG(c.get(AD_CLAC_NNRG_INDEX));
    	
    	if(c.get(AD_CLAC_DTRGEMISSAO_INDEX) != null && !c.get(AD_CLAC_DTRGEMISSAO_INDEX).toString().equals("")){
    		clienteAtlzCadastral.setDataEmissaoRg(Util.converteStringParaDate(c.get(AD_CLAC_DTRGEMISSAO_INDEX)));
    	}
    	
    	if(c.get(AD_CLAC_DTNASCIMENTO_INDEX) != null && !c.get(AD_CLAC_DTNASCIMENTO_INDEX).toString().equals("")){
    		clienteAtlzCadastral.setDataNascimento(Util.converteStringParaDate(c.get(AD_CLAC_DTNASCIMENTO_INDEX)));
    	}
    	
    	if(c.get(AD_OERG_ID_INDEX) != null && !c.get(AD_OERG_ID_INDEX).toString().equals("")){
	    	OrgaoExpedidorRg _orgaoExpedidorRg = new OrgaoExpedidorRg();
	    	_orgaoExpedidorRg.setId(c.get(AD_OERG_ID_INDEX));
	    	clienteAtlzCadastral.setOrgaoExpedidorRg(_orgaoExpedidorRg);
    	}
    	
    	if(c.get(AD_UNFE_ID_INDEX) != null && !c.get(AD_UNFE_ID_INDEX).toString().equals("")){
	        UnidadeFederacao _unidadeFederacao = new UnidadeFederacao();
	    	_unidadeFederacao.setId(c.get(AD_UNFE_ID_INDEX));
	    	clienteAtlzCadastral.setUnidadeFederacao(_unidadeFederacao);
    	}
    	
    	if ( c.get(AD_CLAC_ID_CLIENTE) != null ) {
    		clienteAtlzCadastral.setIdCliente(Integer.valueOf(c.get(AD_CLAC_ID_CLIENTE)));
    	}
    	
        return clienteAtlzCadastral;
    }
    
    public ContentValues carregarValues() {
		
    	ContentValues values = new ContentValues();
		
		values.put(ClienteAtlzCadastralColunas.ID, getId());
		values.put(ClienteAtlzCadastralColunas.IMOVELATLZCAD_ID, getImovelAtlzCadastral().getId());
	
		if ( getClienteTipo() != null ) {
			values.put(ClienteAtlzCadastralColunas.CLIENTETIPO_ID, getClienteTipo().getId());
		}
		
		values.put(ClienteAtlzCadastralColunas.NUMCPFCNPJ, getNumeroCPFCNPPJ());
		values.put(ClienteAtlzCadastralColunas.NOMECLIENTE, getNomeCliente());
		
		if(getDataNascimento() != null){
			values.put(ClienteAtlzCadastralColunas.DATANASCIMENTO, Util.dateFormatDB.format(getDataNascimento()));
		}else{
			values.putNull(ClienteAtlzCadastralColunas.DATANASCIMENTO);
		}
		
		if(getPessoaSexo() != null){
			values.put(ClienteAtlzCadastralColunas.PESSOASEXO_ID, getPessoaSexo().getId());
		}else{
			values.putNull(ClienteAtlzCadastralColunas.PESSOASEXO_ID);
		}
		
		values.put(ClienteAtlzCadastralColunas.NUMERORG, getNumeroRG());
		
		if(getOrgaoExpedidorRg() != null){
			values.put(ClienteAtlzCadastralColunas.ORGAOEXPEDIDOR_ID, getOrgaoExpedidorRg().getId());
		}else{
			values.putNull(ClienteAtlzCadastralColunas.ORGAOEXPEDIDOR_ID);
		}
		
		if(getUnidadeFederacao() != null){
			values.put(ClienteAtlzCadastralColunas.UNIDADEFEDERACAO_ID, getUnidadeFederacao().getId());
		}else{
			values.putNull(ClienteAtlzCadastralColunas.UNIDADEFEDERACAO_ID);
		}
		
		if(getDataEmissaoRg() != null){
			values.put(ClienteAtlzCadastralColunas.DATAEMISSAORG, Util.dateFormatDB.format(getDataEmissaoRg()));
		}else{
			values.putNull(ClienteAtlzCadastralColunas.DATAEMISSAORG);
		}
		
		values.put(ClienteAtlzCadastralColunas.ID_CLIENTE, getIdCliente());

		return values;
	}
    
	public ArrayList<ClienteAtlzCadastral> carregarListaEntidade(Cursor cursor) {		
		
		ArrayList<ClienteAtlzCadastral> listaClienteAtlzCadastral = new ArrayList<ClienteAtlzCadastral>();
		
		do{
			ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();
			
			int _codigo 	  	   	= cursor.getColumnIndex(ClienteAtlzCadastralColunas.ID);
			int _imovelAtlzCadastralId 	= cursor.getColumnIndex(ClienteAtlzCadastralColunas.IMOVELATLZCAD_ID);
			int _pessoaSexoId		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.PESSOASEXO_ID);
			int _clienteTipoId		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.CLIENTETIPO_ID);
			int _numeroCPFCNPPJ		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.NUMCPFCNPJ);
			int _nomeCliente		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.NOMECLIENTE);
			int _numeroRG			= cursor.getColumnIndex(ClienteAtlzCadastralColunas.NUMERORG);
			int _orgaoExpedidorId 	= cursor.getColumnIndex(ClienteAtlzCadastralColunas.ORGAOEXPEDIDOR_ID);
			int _unidadeFederacaoId = cursor.getColumnIndex(ClienteAtlzCadastralColunas.UNIDADEFEDERACAO_ID);
			int _dataEmissaoRg		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.DATAEMISSAORG);
			int _dataNascimento		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.DATANASCIMENTO);
			int _idCliente		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.ID_CLIENTE);
			clienteAtlzCadastral.setId(cursor.getInt(_codigo));
	    	
	    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
	    	_imovelAtlzCadastral.setId(cursor.getInt(_imovelAtlzCadastralId));
	    	clienteAtlzCadastral.setImovelAtlzCadastral(_imovelAtlzCadastral);
	    	
	    	PessoaSexo _pessoaSexo = new PessoaSexo();
	    	_pessoaSexo.setId(cursor.getInt(_pessoaSexoId));
	    	clienteAtlzCadastral.setPessoaSexo(_pessoaSexo);
	    	
	    	ClienteTipo _clienteTipo = new ClienteTipo();
	    	_clienteTipo.setId(cursor.getInt(_clienteTipoId));
	    	clienteAtlzCadastral.setClienteTipo(_clienteTipo);
	    	
	    	clienteAtlzCadastral.setNumeroCPFCNPPJ(cursor.getString(_numeroCPFCNPPJ));
	    	clienteAtlzCadastral.setNomeCliente(cursor.getString(_nomeCliente));
	    	clienteAtlzCadastral.setNumeroRG(cursor.getString(_numeroRG));
	    	
	    	if(cursor.getString(_dataEmissaoRg) != null && !cursor.getString(_dataEmissaoRg).equals("")){
	    		clienteAtlzCadastral.setDataEmissaoRg(Util.convertStringToDate(cursor.getString(_dataEmissaoRg)));
	    	}
	    	
	    	if(cursor.getString(_dataNascimento) != null && !cursor.getString(_dataNascimento).equals("")){
	    		clienteAtlzCadastral.setDataNascimento(Util.convertStringToDate(cursor.getString(_dataNascimento)));
	    	}
	    	
	    	OrgaoExpedidorRg _orgaoExpedidor = new OrgaoExpedidorRg();
	    	_orgaoExpedidor.setId(cursor.getInt(_orgaoExpedidorId));
	    	clienteAtlzCadastral.setOrgaoExpedidorRg(_orgaoExpedidor);
	    	
			UnidadeFederacao _unidadeFederacao = new UnidadeFederacao();
			_unidadeFederacao.setId(cursor.getInt(_unidadeFederacaoId));
			clienteAtlzCadastral.setUnidadeFederacao(_unidadeFederacao);
			clienteAtlzCadastral.setIdCliente(cursor.getInt(_idCliente));
			listaClienteAtlzCadastral.add(clienteAtlzCadastral);
			
		} while (cursor.moveToNext());
		
		cursor.close();
		return listaClienteAtlzCadastral;
	}
	
	public ClienteAtlzCadastral carregarEntidade(Cursor cursor) {		
		
		int _codigo 	  	   	= cursor.getColumnIndex(ClienteAtlzCadastralColunas.ID);
		int _imovelAtlzCadastralId 	= cursor.getColumnIndex(ClienteAtlzCadastralColunas.IMOVELATLZCAD_ID);
		int _pessoaSexoId		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.PESSOASEXO_ID);
		int _clienteTipoId		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.CLIENTETIPO_ID);
		int _numeroCPFCNPPJ		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.NUMCPFCNPJ);
		int _nomeCliente		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.NOMECLIENTE);
		int _numeroRG			= cursor.getColumnIndex(ClienteAtlzCadastralColunas.NUMERORG);
		int _orgaoExpedidorId 	= cursor.getColumnIndex(ClienteAtlzCadastralColunas.ORGAOEXPEDIDOR_ID);
		int _unidadeFederacaoId = cursor.getColumnIndex(ClienteAtlzCadastralColunas.UNIDADEFEDERACAO_ID);
		int _dataEmissaoRg		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.DATAEMISSAORG);
		int _dataNascimento		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.DATANASCIMENTO);
		int _idCliente		= cursor.getColumnIndex(ClienteAtlzCadastralColunas.ID_CLIENTE);
		
		ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();

		if ( cursor.moveToFirst() ) {
			
			clienteAtlzCadastral.setId(cursor.getInt(_codigo));
	    	
	    	ImovelAtlzCadastral _imovelAtlzCadastral = new ImovelAtlzCadastral();
	    	_imovelAtlzCadastral.setId(cursor.getInt(_imovelAtlzCadastralId));
	    	clienteAtlzCadastral.setImovelAtlzCadastral(_imovelAtlzCadastral);
	    	
	    	PessoaSexo _pessoaSexo = new PessoaSexo();
	    	_pessoaSexo.setId(cursor.getInt(_pessoaSexoId));
	    	clienteAtlzCadastral.setPessoaSexo(_pessoaSexo);
	    	
	    	ClienteTipo _clienteTipo = new ClienteTipo();
	    	_clienteTipo.setId(cursor.getInt(_clienteTipoId));
	    	clienteAtlzCadastral.setClienteTipo(_clienteTipo);
	    	
	    	clienteAtlzCadastral.setNumeroCPFCNPPJ(cursor.getString(_numeroCPFCNPPJ));
	    	clienteAtlzCadastral.setNomeCliente(cursor.getString(_nomeCliente));
	    	clienteAtlzCadastral.setNumeroRG(cursor.getString(_numeroRG));
	    	
	    	if(cursor.getString(_dataEmissaoRg) != null && !cursor.getString(_dataEmissaoRg).equals("")){
	    		clienteAtlzCadastral.setDataEmissaoRg(Util.convertStringToDate(cursor.getString(_dataEmissaoRg)));
	    	}
	    	
	    	if(cursor.getString(_dataNascimento) != null && !cursor.getString(_dataNascimento).equals("")){
	    		clienteAtlzCadastral.setDataNascimento(Util.convertStringToDate(cursor.getString(_dataNascimento)));
	    	}
	    	
	    	OrgaoExpedidorRg _orgaoExpedidorRg = new OrgaoExpedidorRg();
	    	_orgaoExpedidorRg.setId(cursor.getInt(_orgaoExpedidorId));
	    	clienteAtlzCadastral.setOrgaoExpedidorRg(_orgaoExpedidorRg);
	    	
	    	UnidadeFederacao _unidadeFederacao = new UnidadeFederacao();
	    	_unidadeFederacao.setId(cursor.getInt(_unidadeFederacaoId));
	    	clienteAtlzCadastral.setUnidadeFederacao(_unidadeFederacao);
	    	clienteAtlzCadastral.setIdCliente(cursor.getInt(_idCliente));
		}

		cursor.close();
		return clienteAtlzCadastral;
	}
    
    public ImovelAtlzCadastral getImovelAtlzCadastral() {
		return imovelAtlzCadastral;
	}

	public void setImovelAtlzCadastral(ImovelAtlzCadastral imovelAtlzCadastral) {
		this.imovelAtlzCadastral = imovelAtlzCadastral;
	}

	public PessoaSexo getPessoaSexo() {
		return pessoaSexo;
	}

	public void setPessoaSexo(PessoaSexo pessoaSexo) {
		this.pessoaSexo = pessoaSexo;
	}
	
    public Date getDataEmissaoRg() {
		return dataEmissaoRg;
	}

	public void setDataEmissaoRg(Date dataEmissaoRg) {
		this.dataEmissaoRg = dataEmissaoRg;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public ClienteTipo getClienteTipo() {
		return clienteTipo;
	}

	public void setClienteTipo(ClienteTipo clienteTipo) {
		this.clienteTipo = clienteTipo;
	}

	public String getNumeroCPFCNPPJ() {
		return numeroCPFCNPPJ;
	}

	public void setNumeroCPFCNPPJ(String numeroCPFCNPPJ) {
		this.numeroCPFCNPPJ = numeroCPFCNPPJ;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getNumeroRG() {
		return numeroRG;
	}

	public void setNumeroRG(String numeroRG) {
		this.numeroRG = numeroRG;
	}

	public OrgaoExpedidorRg getOrgaoExpedidorRg() {
		return orgaoExpedidorRg;
	}

	public void setOrgaoExpedidorRg(OrgaoExpedidorRg orgaoExpedidorRg) {
		this.orgaoExpedidorRg = orgaoExpedidorRg;
	}

	public UnidadeFederacao getUnidadeFederacao() {
		return unidadeFederacao;
	}

	public void setUnidadeFederacao(UnidadeFederacao unidadeFederacao) {
		this.unidadeFederacao = unidadeFederacao;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String[] getColunas(){
		return columns;
	}
    
    public String getNomeTabela(){
		return "CLIENTE_ATLZ_CADASTRAL";
	}
}
