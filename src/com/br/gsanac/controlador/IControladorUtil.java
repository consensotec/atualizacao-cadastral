package com.br.gsanac.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.br.gsanac.entidades.Cep;
import com.br.gsanac.entidades.ClienteAtlzCadastral;
import com.br.gsanac.entidades.ClienteFoneAtlzCad;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelOcorrencia;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.LogradouroCep;
import com.br.gsanac.entidades.Roteiro;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.ControladorException;
import com.br.gsanac.exception.RepositorioException;

/**
 * @author Arthur Carvalho
 */
public interface IControladorUtil {

	public String validarAbaLocalidade(ImovelAtlzCadastral imovelAtlzCadastral) throws ControladorException;
	
    /**
     * Metodo responsavel por validar os campos da aba endereco
     * 
     * @author Anderson Cabral
     * @date 26/12/2012
     *
     * @param imovelAtlzCadastral
     * @return Mensagem de Erro
     * @throws ControladorException
     */
	public String validarAbaEndereco(ImovelAtlzCadastral imovelAtlzCadastral) throws ControladorException;
	
	/**
	 * Metodo responsavel por validar os campos da aba cliente
	 * 
	 * @author Davi Menezes
	 * @date 28/12/2012
	 * 
	 * @param clienteAtlzCadastral
	 * @return Mensagem de Erro
	 * @throws ControladorException
	 */
	public String validarAbaCliente(ClienteAtlzCadastral clienteAtlzCadastral, Integer idLigacaoAguaSituacao) throws ControladorException;
	
    /**
     * Metodo responsavel por validar os campos da aba ligacao
     * 
     * @author Anderson Cabral
     * @date 03/01/2013
     *
     * @param imovelAtlzCadastral
     * @param hidrometroInstHistAtlzCad
     * @param contemHidrometro
     * @return Mensagem de Erro
     * @throws ControladorException
     */
    public String validarAbaLigacao(ImovelAtlzCadastral imovelAtlzCadastral, HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad) throws ControladorException;
    
    /**
     * Metodo que pesquisa o imovel de acordo com a posicao dele no roteiro.
     * 
     * @author Arthur Carvalho
     * @date 08/01/2013
     *
     * @param posicao
     * @return
     * @throws ControladorException
     */
    public ImovelAtlzCadastral buscarImovelPosicao(Integer posicao) throws ControladorException;

    /**
     * Metodo responsavel por validar os campos da aba Fotos
     * 
     * @author Anderson Cabral
     * @date 08/01/2013
     *
     * @param idImovel
     * @return Mensagem de Erro
     * @throws ControladorException
     */
    public String validarAbaFotos(Integer idImovel)throws ControladorException;
    
	/****
	 * Retorna o maior id da tabela Imovel
	 * 
	 *@author Anderson Cabral
	 *@since 11/01/2013
	 ****/
	public Integer pesquisarMaiorIdImovel() throws ControladorException;
	
	/**
	 * 
	 * @author Arthur Carvalho
	 * @date 14/01/2013
	 *
	 * @throws ControladorException
	 */
	public String gerarArquivoRetornoImovel(ImovelAtlzCadastral imovelAtlzCadastral) throws ControladorException;
	
	/**
     * 
     * @author Arthur Carvalho
     * @date 15/01/2013
     *
     * @return
     * @throws ControladorException
     */
    public String gerarArquivoRetornoLogradouroBairro(Logradouro logradouro, SistemaParametros sistemaParametros) throws ControladorException ;
    
    
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 15/01/2013
     *
     * @return
     * @throws ControladorException
     */
    public String gerarArquivoRetornoLogradouroCep(LogradouroCep logradouroCep) throws ControladorException ;
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 15/01/2013
     *
     * @return
     * @throws ControladorException
     */
    public String gerarArquivoRetornoCep(Cep cep) throws ControladorException ;
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 18/01/2013
     *
     * @return
     * @throws ControladorException
     */
    public Roteiro pesquisarRoteiro(Integer idImovelAtlzCadastral) throws ControladorException;
    
    /**
     * Metodo responsavel por pesquisar todos os imóveis cadastrados no gsan.
     * 
     * @author Arthur Carvalho
     * @return FileReturn
     * @param idServiceOrder
     * @throws RepositoryException
     */
    public ArrayList<Integer> pesquisarMatriculas() throws ControladorException; 
    
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 15/01/2013
     *
     * @return
     * @throws ControladorException
     */
    public String gerarArquivoRetornoCliente(ClienteAtlzCadastral clienteAtlzCadastral, List<ClienteFoneAtlzCad> listaClienteFoneAtlzCadastral, String codigoImovelAtlzCadastral)
    		throws ControladorException;
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 15/01/2013
     *
     * @return
     * @throws ControladorException
     */
    public String gerarArquivoRetornoHidrometro( List<HidrometroInstHistAtlzCad> listaHidrometroInstHistAtlzCad, String codigoImovelAtlzCadastral, Integer idImovel) throws ControladorException;
    
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 15/01/2013
     *
     * @return
     * @throws ControladorException
     */
    public String gerarArquivoRetornoSubcategoria( List<ImovelSubCategAtlzCad> listaImovelSubCategAtlzCad, String codigoImovelAtlzCadastral) throws ControladorException;
    
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 15/01/2013
     *
     * @return
     * @throws ControladorException
     */
    public String gerarArquivoRetornoOcorrencia( List<ImovelOcorrencia> listaImovelOcorrencia, String codigoImovelAtlzCadastral) throws ControladorException ;
   
    /**
     * 
     * @author Arthur Carvalho
     * @date 18/02/2013
     *
     * @param login
     * @param password
     * @return
     * @throws ControladorException
     */
    public SistemaParametros validarLogin(String login, String password) throws ControladorException;
    
    /**
     * @author Erivan Sousa
     * @since 06/09/2011
     * @param login
     * @param password
     * @return
     */
    public SistemaParametros validarLoginCpf(String login) throws ControladorException ;
    
    /**
	    * 
	    * @author Arthur Carvalho
	    * @date 26/06/2013
	    *
	    * @return
	    * @throws ControladorException
	    */
    public Integer pesquisarSetorComercialPrincipal() throws ControladorException ;
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 18/01/2013
     *
     * @return
     * @throws ControladorException
     */
    public Date pesquisarArquivoDivididoCarregado(String nomeArquivo) throws ControladorException; 
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 18/01/2013
     *
     * @return
     * @throws ControladorException
     */
    public void inserirArquivoDividido(String nomeArquivo) throws ControladorException; 
    
    /**
     * 
     * @author Flávio Ferreira
     * @date 11/10/2013
     *
     * @return
     * @throws ControladorException
     */
    public Integer obterQuantidadeImoveisIncluidosComPorOcorrencia(Integer numeroOcorrencia) throws ControladorException;
    
    /**
     * 
     * @author Flávio Ferreira
     * @date 11/10/2013
     *
     * @return
     * @throws ControladorException
     */
    
    public Integer obterQuantidadeImoveisAtualizadosPorOcorrencia(Integer numeroOcorrencia) throws ControladorException;
    
    /**
     * 
     * @author Flávio Ferreira
     * @date 11/10/2013
     *
     * @return
     * @throws ControladorException
     */
    
    public Integer obterQuantidadeImoveis() throws ControladorException;
    
    
    /**
     * @author Flavio Ferreira
     * @date 10/10/2013
     * @param numeroOcorrencia
     * @return
     * @throws RepositorioException
     */
    public String buscarDescricaoOcorrencias(Integer idCadastroOcorrencia)throws ControladorException;
    
    /**
     * @author Flavio Ferreira
     * @date 14/10/2013
     * @param numeroOcorrencia
     * @return
     * @throws RepositorioException
     */
  
	
	public Integer obterTotalImoveisAtualizados(String login) throws ControladorException;
	
	 /**
     * @author Flavio Ferreira
     * @date 14/10/2013
     * @param numeroOcorrencia
     * @return
     * @throws RepositorioException
     */

	public Integer obterTotalImoveisIncluidos(String login) throws ControladorException;
	
	 /**
     * @author Flavio Ferreira
     * @date 15/10/2013
     * @return
     * @throws RepositorioException
     */
	
	public List<String> pesquisarListaLogin() throws ControladorException;
}
