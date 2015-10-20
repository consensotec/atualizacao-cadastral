package com.br.gsanac.pojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.br.gsanac.utilitarios.Utilitarios;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * @author Jonathan Marcos
 * @since 03/09/2014
 */
public class ClienteAtlzCadastral extends EntidadeBasica {

	private static final long serialVersionUID = 1L;
	
	// Atributos
	private ImovelAtlzCadastral imovelAtlzCadastral;
	private PessoaSexo pessoaSexo;
	private ClienteTipo clienteTipo;
	private String numeroCPFCNPPJ;
	private String nomeCliente;
	private String numeroRG;
	private OrgaoExpedidorRg orgaoExpedidorRg;
	private UnidadeFederacao unidadeFederacao;
	private Date dataEmissaoRg;
	private Date dataNascimento;
	private Integer idCliente;
	private String nomeMae;
	private Integer indicadorProprietario;
	private Integer indicadorResponsavel;
	private Integer indicadorDocumento;
	
	// Index de acesso
    private static final int CLAC_ID_INDEX = 1;
    private static final int IMAC_ID_INDEX = 2;
    private static final int CLTP_ID_INDEX = 3;
    private static final int CLAC_NNCPFCNPPJ_INDEX = 4;
    private static final int CLAC_NMCLIENTE_INDEX = 5;
    private static final int CLAC_NNRG_INDEX = 6;
    private static final int OERG_ID_INDEX = 7;
    private static final int UNFE_ID_INDEX 	= 8;
    private static final int PSEX_ID_INDEX = 9;
    private static final int CLAC_DTRGEMISSAO_INDEX = 10;
    private static final int CLAC_DTNASCIMENTO_INDEX = 11;
    private static final int CLAC_ID_CLIENTE = 12;
    private static final int CLAC_NMMAE_INDEX = 13;
   
    /*
     * SubClasse referente aos 
     * campos da tabela
     */
    public static final class ClienteAtlzCadastralColuna implements BaseColumns{
    	public static final String ID = "CLAC_ID";
    	public static final String IMOVELATLZCAD_ID = "IMAC_ID";
    	public static final String CLIENTETIPO_ID = "CLTP_ID";
    	public static final String NUMCPFCNPJ = "CLAC_NNCPFCNPPJ";
    	public static final String NOMECLIENTE = "CLAC_NMCLIENTE";
    	public static final String NUMERORG = "CLAC_NNRG";
    	public static final String ORGAOEXPEDIDOR_ID = "OERG_ID";
    	public static final String UNIDADEFEDERACAO_ID = "UNFE_ID";
    	public static final String PESSOASEXO_ID = "PSEX_ID";
    	public static final String DATAEMISSAORG = "CLAC_DTRGEMISSAO";
    	public static final String DATANASCIMENTO = "CLAC_DTNASCIMENTO";
    	public static final String ID_CLIENTE = "CLAC_IDCLIENTE";
    	public static final String NOME_MAE = "CLAC_NMMAE";
    	public static final String INDICADOR_PROPRIETARIO = "CLAC_ICPROPRIETARIO";
    	public static final String INDICADOR_RESPONSAVEL = "CLAC_ICRESPONSAVEL";
    	public static final String INDICADOR_DOCUMENTO = "CLAC_ICDOCUMENTACAO";
    }
    
    /*
     *  Atributo responsável por obter as 
     *  colunas usadas na criação
     *  do banco
     */
    public static final String[] colunas = new String[]{
    	ClienteAtlzCadastralColuna.ID,
    	ClienteAtlzCadastralColuna.IMOVELATLZCAD_ID,
    	ClienteAtlzCadastralColuna.CLIENTETIPO_ID,
    	ClienteAtlzCadastralColuna.NUMCPFCNPJ,
    	ClienteAtlzCadastralColuna.NOMECLIENTE,
    	ClienteAtlzCadastralColuna.NUMERORG,
    	ClienteAtlzCadastralColuna.ORGAOEXPEDIDOR_ID,
    	ClienteAtlzCadastralColuna.UNIDADEFEDERACAO_ID,
    	ClienteAtlzCadastralColuna.PESSOASEXO_ID,
    	ClienteAtlzCadastralColuna.DATAEMISSAORG,
    	ClienteAtlzCadastralColuna.DATANASCIMENTO,
    	ClienteAtlzCadastralColuna.ID_CLIENTE,
    	ClienteAtlzCadastralColuna.NOME_MAE,
    	ClienteAtlzCadastralColuna.INDICADOR_PROPRIETARIO,
    	ClienteAtlzCadastralColuna.INDICADOR_RESPONSAVEL,
    	ClienteAtlzCadastralColuna.INDICADOR_DOCUMENTO
    };
    
    // Método retorna as colunas da tabela
    public String[] getColunas(){
		return colunas;
	}
   
    /*
     * SubClasse referente aos
     * tipos das colunas
     */
    public final class ClienteAtlzCadastralColunaTipo {
		private final String ID = " INTEGER PRIMARY KEY";
    	private final String IMOVELATLZCAD_ID = " INTEGER";
    	private final String CLIENTETIPO_ID = " INTEGER NOT NULL";
    	private final String NUMCPFCNPJ = " VARCHAR(14)";
    	private final String NOMECLIENTE = " VARCHAR(50)";
    	private final String NUMERORG = " VARCHAR(13)";
    	private final String ORGAOEXPEDIDOR_ID = " INTEGER";
    	private final String UNIDADEFEDERACAO_ID = " INTEGER";
    	private final String PESSOASEXO_ID = " INTEGER";
    	private final String DATAEMISSAORG = " DATE";
    	private final String DATANASCIMENTO = " DATE";
    	private final String ID_CLIENTE = " INTEGER";
    	private final String NOME_MAE = " VARCHAR(50)";
    	private final String INDICADOR_PROPRIETARIO = " INTEGER";
    	private final String INDICADOR_RESPONSAVEL = " INTEGER";
    	private final String INDICADOR_DOCUMENTO = " INTEGER";
		
		private String[] tipos = new String[] {
				ID,
				IMOVELATLZCAD_ID,
				CLIENTETIPO_ID,
				NUMCPFCNPJ,
				NOMECLIENTE,
				NUMERORG,
				ORGAOEXPEDIDOR_ID,
				UNIDADEFEDERACAO_ID,
				PESSOASEXO_ID,
				DATAEMISSAORG,
				DATANASCIMENTO,
				ID_CLIENTE,NOME_MAE,
				INDICADOR_PROPRIETARIO,
				INDICADOR_RESPONSAVEL,
				INDICADOR_DOCUMENTO
			};	
		
		public String[] getTipos(){
			return tipos;
		}
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
    
	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public Integer getIndicadorProprietario() {
		return indicadorProprietario;
	}

	public void setIndicadorProprietario(Integer indicadorProprietario) {
		this.indicadorProprietario = indicadorProprietario;
	}

	public Integer getIndicadorResponsavel() {
		return indicadorResponsavel;
	}

	public void setIndicadorResponsavel(Integer indicadorResponsavel) {
		this.indicadorResponsavel = indicadorResponsavel;
	}

	public Integer getIndicadorDocumento() {
		return indicadorDocumento;
	}

	public void setIndicadorDocumento(Integer indicadorDocumento) {
		this.indicadorDocumento = indicadorDocumento;
	}

	/*
     * Método responsável por retornar objeto
     * a partir de uma lista
     */
    public static ClienteAtlzCadastral converteLinhaArquivoEmObjeto(List<String> c) {
    	ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();

    	clienteAtlzCadastral.setId(c.get(CLAC_ID_INDEX));
    	
    	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
    	imovelAtlzCadastral.setId(c.get(IMAC_ID_INDEX));
    	clienteAtlzCadastral.setImovelAtlzCadastral(imovelAtlzCadastral);
    	
    	ClienteTipo clienteTipo = new ClienteTipo();
    	clienteTipo.setId(c.get(CLTP_ID_INDEX));
    	clienteAtlzCadastral.setClienteTipo(clienteTipo);
    	
    	clienteAtlzCadastral.setNumeroCPFCNPPJ(c.get(CLAC_NNCPFCNPPJ_INDEX));
    	clienteAtlzCadastral.setNomeCliente(c.get(CLAC_NMCLIENTE_INDEX));
    	clienteAtlzCadastral.setNumeroRG(c.get(CLAC_NNRG_INDEX));
    	
    	if(c.get(CLAC_DTRGEMISSAO_INDEX) != null && !c.get(CLAC_DTRGEMISSAO_INDEX).toString().equals("")){
    		clienteAtlzCadastral.setDataEmissaoRg(Utilitarios.converteStringParaDate(c.get(CLAC_DTRGEMISSAO_INDEX)));
    	}
    	
    	if(c.get(CLAC_DTNASCIMENTO_INDEX) != null && !c.get(CLAC_DTNASCIMENTO_INDEX).toString().equals("")){
    		clienteAtlzCadastral.setDataNascimento(Utilitarios.converteStringParaDate(c.get(CLAC_DTNASCIMENTO_INDEX)));
    	}
    	
    	if(c.get(OERG_ID_INDEX) != null && !c.get(OERG_ID_INDEX).toString().equals("")){
	    	OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
	    	orgaoExpedidorRg.setId(c.get(OERG_ID_INDEX));
	    	clienteAtlzCadastral.setOrgaoExpedidorRg(orgaoExpedidorRg);
    	}
    	
    	if(c.get(UNFE_ID_INDEX) != null && !c.get(UNFE_ID_INDEX).toString().equals("")){
	        UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
	    	unidadeFederacao.setId(c.get(UNFE_ID_INDEX));
	    	clienteAtlzCadastral.setUnidadeFederacao(unidadeFederacao);
    	}
    	
    	if(c.get(PSEX_ID_INDEX) != null && !c.get(PSEX_ID_INDEX).toString().equals("")){
	    	PessoaSexo pessoaSexo = new PessoaSexo();
	    	pessoaSexo.setId(c.get(PSEX_ID_INDEX));
	    	clienteAtlzCadastral.setPessoaSexo(pessoaSexo);
    	}
    	
    	if ( c.get(CLAC_ID_CLIENTE) != null ) {
    		clienteAtlzCadastral.setIdCliente(Integer.valueOf(c.get(CLAC_ID_CLIENTE)));
    	}
    	
    	if(c.get(CLAC_NMMAE_INDEX)!=null && c.get(CLAC_NMMAE_INDEX).toString().compareTo("")!=0){
    		clienteAtlzCadastral.setNomeMae(c.get(CLAC_NMMAE_INDEX));
    	}
    	
    	
        return clienteAtlzCadastral;
    }
    
    // Método retorna ContentValues
    public ContentValues carregarValues() {
		ContentValues values = new ContentValues();
		
		values.put(ClienteAtlzCadastralColuna.ID, getId());
		values.put(ClienteAtlzCadastralColuna.IMOVELATLZCAD_ID, getImovelAtlzCadastral().getId());
	
		if ( getClienteTipo() != null ) {
			values.put(ClienteAtlzCadastralColuna.CLIENTETIPO_ID, getClienteTipo().getId());
		}
		
		values.put(ClienteAtlzCadastralColuna.NUMCPFCNPJ, getNumeroCPFCNPPJ());
		values.put(ClienteAtlzCadastralColuna.NOMECLIENTE, getNomeCliente());
		values.put(ClienteAtlzCadastralColuna.NUMERORG, getNumeroRG());
		
		if(getOrgaoExpedidorRg() != null){
			values.put(ClienteAtlzCadastralColuna.ORGAOEXPEDIDOR_ID, getOrgaoExpedidorRg().getId());
		}else{
			values.putNull(ClienteAtlzCadastralColuna.ORGAOEXPEDIDOR_ID);
		}
		
		if(getUnidadeFederacao() != null){
			values.put(ClienteAtlzCadastralColuna.UNIDADEFEDERACAO_ID, getUnidadeFederacao().getId());
		}else{
			values.putNull(ClienteAtlzCadastralColuna.UNIDADEFEDERACAO_ID);
		}
		
		if(getPessoaSexo() != null){
			values.put(ClienteAtlzCadastralColuna.PESSOASEXO_ID, getPessoaSexo().getId());
		}else{
			values.putNull(ClienteAtlzCadastralColuna.PESSOASEXO_ID);
		}
		
		if(getDataEmissaoRg() != null){
			values.put(ClienteAtlzCadastralColuna.DATAEMISSAORG, 
					Utilitarios.simpleDateFormatFormatoBancoDeDados.format(getDataEmissaoRg()));
		}else{
			values.putNull(ClienteAtlzCadastralColuna.DATAEMISSAORG);
		}
		
		if(getDataNascimento() != null){
			values.put(ClienteAtlzCadastralColuna.DATANASCIMENTO, 
					Utilitarios.simpleDateFormatFormatoBancoDeDados.format(getDataNascimento()));
		}else{
			values.putNull(ClienteAtlzCadastralColuna.DATANASCIMENTO);
		}
		
		values.put(ClienteAtlzCadastralColuna.ID_CLIENTE, getIdCliente());
		
		if(getNomeMae()!=null){
			values.put(ClienteAtlzCadastralColuna.NOME_MAE, getNomeMae());
		}else{
			values.putNull(ClienteAtlzCadastralColuna.NOME_MAE);
		}
		
		if(getIndicadorProprietario()!=null){
			values.put(ClienteAtlzCadastralColuna.INDICADOR_PROPRIETARIO, getIndicadorProprietario());
		}else{
			values.putNull(ClienteAtlzCadastralColuna.INDICADOR_PROPRIETARIO);
		}
		
		if(getIndicadorResponsavel()!=null){
			values.put(ClienteAtlzCadastralColuna.INDICADOR_RESPONSAVEL, getIndicadorResponsavel());
		}else{
			values.putNull(ClienteAtlzCadastralColuna.INDICADOR_RESPONSAVEL);
		}
		
		if(getIndicadorDocumento()!=null){
			values.put(ClienteAtlzCadastralColuna.INDICADOR_DOCUMENTO, getIndicadorDocumento());
		}else{
			values.putNull(ClienteAtlzCadastralColuna.INDICADOR_DOCUMENTO);
		}
		
		return values;
	}
    
    /*
	 *  Método converte um cursor em 
	 *  uma lista do objeto
	 */
	public ArrayList<ClienteAtlzCadastral> carregarListaEntidade(Cursor cursor) {		
		ArrayList<ClienteAtlzCadastral> listaClienteAtlzCadastral = new ArrayList<ClienteAtlzCadastral>();
		if(cursor.moveToFirst()){
			do{
				ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();
				
				int _id = cursor.getColumnIndex(ClienteAtlzCadastralColuna.ID);
				int _imovelAtlzCadastralId 	= cursor.getColumnIndex(ClienteAtlzCadastralColuna.IMOVELATLZCAD_ID);
				int _clienteTipoId = cursor.getColumnIndex(ClienteAtlzCadastralColuna.CLIENTETIPO_ID);
				int _numeroCPFCNPPJ = cursor.getColumnIndex(ClienteAtlzCadastralColuna.NUMCPFCNPJ);
				int _nomeCliente = cursor.getColumnIndex(ClienteAtlzCadastralColuna.NOMECLIENTE);
				int _numeroRG = cursor.getColumnIndex(ClienteAtlzCadastralColuna.NUMERORG);
				int _orgaoExpedidorId = cursor.getColumnIndex(ClienteAtlzCadastralColuna.ORGAOEXPEDIDOR_ID);
				int _unidadeFederacaoId = cursor.getColumnIndex(ClienteAtlzCadastralColuna.UNIDADEFEDERACAO_ID);
				int _pessoaSexoId = cursor.getColumnIndex(ClienteAtlzCadastralColuna.PESSOASEXO_ID);
				int _dataEmissaoRg = cursor.getColumnIndex(ClienteAtlzCadastralColuna.DATAEMISSAORG);
				int _dataNascimento = cursor.getColumnIndex(ClienteAtlzCadastralColuna.DATANASCIMENTO);
				int _idCliente = cursor.getColumnIndex(ClienteAtlzCadastralColuna.ID_CLIENTE);
				int _nomeMae = cursor.getColumnIndex(ClienteAtlzCadastralColuna.NOME_MAE);
				int _indicadorProprietario = cursor.getColumnIndex(ClienteAtlzCadastralColuna.INDICADOR_PROPRIETARIO);
				int _indicadorResponsavel = cursor.getColumnIndex(ClienteAtlzCadastralColuna.INDICADOR_RESPONSAVEL);
				int _indicadorDocumento = cursor.getColumnIndex(ClienteAtlzCadastralColuna.INDICADOR_DOCUMENTO);
				
				clienteAtlzCadastral.setId(cursor.getInt(_id));
		    	
		    	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
		    	imovelAtlzCadastral.setId(cursor.getInt(_imovelAtlzCadastralId));
		    	clienteAtlzCadastral.setImovelAtlzCadastral(imovelAtlzCadastral);
		    	
		    	ClienteTipo clienteTipo = new ClienteTipo();
		    	clienteTipo.setId(cursor.getInt(_clienteTipoId));
		    	clienteAtlzCadastral.setClienteTipo(clienteTipo);
		    	
		    	clienteAtlzCadastral.setNumeroCPFCNPPJ(cursor.getString(_numeroCPFCNPPJ));
		    	clienteAtlzCadastral.setNomeCliente(cursor.getString(_nomeCliente));
		    	clienteAtlzCadastral.setNumeroRG(cursor.getString(_numeroRG));
		    	
		    	OrgaoExpedidorRg orgaoExpedidor = new OrgaoExpedidorRg();
		    	orgaoExpedidor.setId(cursor.getInt(_orgaoExpedidorId));
		    	clienteAtlzCadastral.setOrgaoExpedidorRg(orgaoExpedidor);
		    	
				UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
				unidadeFederacao.setId(cursor.getInt(_unidadeFederacaoId));
				clienteAtlzCadastral.setUnidadeFederacao(unidadeFederacao);
				
				PessoaSexo pessoaSexo = new PessoaSexo();
		    	pessoaSexo.setId(cursor.getInt(_pessoaSexoId));
		    	clienteAtlzCadastral.setPessoaSexo(pessoaSexo);
				
		    	if(cursor.getString(_dataEmissaoRg) != null && !cursor.getString(_dataEmissaoRg).equals("")){
		    		clienteAtlzCadastral.setDataEmissaoRg(Utilitarios.converterStringParaDataddMMyyyy(cursor.getString(_dataEmissaoRg)));
		    	}
		    	
		    	if(cursor.getString(_dataNascimento) != null && !cursor.getString(_dataNascimento).equals("")){
		    		clienteAtlzCadastral.setDataNascimento(Utilitarios.converterStringParaDataddMMyyyy(cursor.getString(_dataNascimento)));
		    	}
		    	
				clienteAtlzCadastral.setIdCliente(cursor.getInt(_idCliente));
				
				if(cursor.getString(_nomeMae)!=null && cursor.getString(_nomeMae).compareTo("")!=0){
					clienteAtlzCadastral.setNomeMae(cursor.getString(_nomeMae));
				}
				
				clienteAtlzCadastral.setIndicadorProprietario(cursor.getInt(_indicadorProprietario));
				clienteAtlzCadastral.setIndicadorResponsavel(cursor.getInt(_indicadorResponsavel));
				clienteAtlzCadastral.setIndicadorDocumento(cursor.getInt(_indicadorDocumento));
				
				listaClienteAtlzCadastral.add(clienteAtlzCadastral);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		return listaClienteAtlzCadastral;
	}
	
	// Método converte um cursor em  objeto
	public ClienteAtlzCadastral carregarEntidade(Cursor cursor) {		
		
		int _id = cursor.getColumnIndex(ClienteAtlzCadastralColuna.ID);
		int _imovelAtlzCadastralId 	= cursor.getColumnIndex(ClienteAtlzCadastralColuna.IMOVELATLZCAD_ID);
		int _clienteTipoId = cursor.getColumnIndex(ClienteAtlzCadastralColuna.CLIENTETIPO_ID);
		int _numeroCPFCNPPJ = cursor.getColumnIndex(ClienteAtlzCadastralColuna.NUMCPFCNPJ);
		int _nomeCliente = cursor.getColumnIndex(ClienteAtlzCadastralColuna.NOMECLIENTE);
		int _numeroRG = cursor.getColumnIndex(ClienteAtlzCadastralColuna.NUMERORG);
		int _orgaoExpedidorId = cursor.getColumnIndex(ClienteAtlzCadastralColuna.ORGAOEXPEDIDOR_ID);
		int _unidadeFederacaoId = cursor.getColumnIndex(ClienteAtlzCadastralColuna.UNIDADEFEDERACAO_ID);
		int _pessoaSexoId = cursor.getColumnIndex(ClienteAtlzCadastralColuna.PESSOASEXO_ID);
		int _dataEmissaoRg = cursor.getColumnIndex(ClienteAtlzCadastralColuna.DATAEMISSAORG);
		int _dataNascimento = cursor.getColumnIndex(ClienteAtlzCadastralColuna.DATANASCIMENTO);
		int _idCliente = cursor.getColumnIndex(ClienteAtlzCadastralColuna.ID_CLIENTE);
		int _nomeMae = cursor.getColumnIndex(ClienteAtlzCadastralColuna.NOME_MAE);
		int _indicadorProprietario = cursor.getColumnIndex(ClienteAtlzCadastralColuna.INDICADOR_PROPRIETARIO);
		int _indicadorResponsavel = cursor.getColumnIndex(ClienteAtlzCadastralColuna.INDICADOR_RESPONSAVEL);
		int _indicadorDocumento = cursor.getColumnIndex(ClienteAtlzCadastralColuna.INDICADOR_DOCUMENTO);
		
		ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();

		if ( cursor.moveToFirst() ) {
			
			clienteAtlzCadastral.setId(cursor.getInt(_id));
	    	
	    	ImovelAtlzCadastral imovelAtlzCadastral = new ImovelAtlzCadastral();
	    	imovelAtlzCadastral.setId(cursor.getInt(_imovelAtlzCadastralId));
	    	clienteAtlzCadastral.setImovelAtlzCadastral(imovelAtlzCadastral);
	    	
	    	ClienteTipo clienteTipo = new ClienteTipo();
	    	clienteTipo.setId(cursor.getInt(_clienteTipoId));
	    	clienteAtlzCadastral.setClienteTipo(clienteTipo);
	    	
	    	clienteAtlzCadastral.setNumeroCPFCNPPJ(cursor.getString(_numeroCPFCNPPJ));
	    	clienteAtlzCadastral.setNomeCliente(cursor.getString(_nomeCliente));
	    	clienteAtlzCadastral.setNumeroRG(cursor.getString(_numeroRG));
	    	
	    	OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
	    	orgaoExpedidorRg.setId(cursor.getInt(_orgaoExpedidorId));
	    	clienteAtlzCadastral.setOrgaoExpedidorRg(orgaoExpedidorRg);
	    	
	    	UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
	    	unidadeFederacao.setId(cursor.getInt(_unidadeFederacaoId));
	    	clienteAtlzCadastral.setUnidadeFederacao(unidadeFederacao);
	    	
	    	PessoaSexo pessoaSexo = new PessoaSexo();
	    	pessoaSexo.setId(cursor.getInt(_pessoaSexoId));
	    	clienteAtlzCadastral.setPessoaSexo(pessoaSexo);
	    	
	    	if(cursor.getString(_dataEmissaoRg) != null && !cursor.getString(_dataEmissaoRg).equals("")){
	    		clienteAtlzCadastral.setDataEmissaoRg(Utilitarios.converterStringParaDataddMMyyyy(cursor.getString(_dataEmissaoRg)));
	    	}
	    	
	    	if(cursor.getString(_dataNascimento) != null && !cursor.getString(_dataNascimento).equals("")){
	    		clienteAtlzCadastral.setDataNascimento(Utilitarios.converterStringParaDataddMMyyyy(cursor.getString(_dataNascimento)));
	    	}
	    	
	    	if(cursor.getString(_nomeMae)!=null && cursor.getString(_nomeMae).compareTo("")!=0){
				clienteAtlzCadastral.setNomeMae(cursor.getString(_nomeMae));
			}
	    	
	    	clienteAtlzCadastral.setIdCliente(cursor.getInt(_idCliente));
	    	
	    	clienteAtlzCadastral.setIndicadorProprietario(cursor.getInt(_indicadorProprietario));
			clienteAtlzCadastral.setIndicadorResponsavel(cursor.getInt(_indicadorResponsavel));
			clienteAtlzCadastral.setIndicadorDocumento(cursor.getInt(_indicadorDocumento));
	    	
		}

		cursor.close();
		return clienteAtlzCadastral;
	}
    
	// Retorna no da tabela
    public String getNomeTabela(){
		return "CLIENTE_ATLZ_CADASTRAL";
	}
}
