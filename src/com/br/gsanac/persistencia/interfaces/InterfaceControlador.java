package com.br.gsanac.persistencia.interfaces;

import java.util.ArrayList;

import org.mapsforge.core.model.LatLong;

import com.br.gsanac.bean.RelatorioOcorrenciaCadastro;
import com.br.gsanac.bean.RoteiroBean;
import com.br.gsanac.excecoes.ControladorException;
import com.br.gsanac.excecoes.RepositorioException;
import com.br.gsanac.helpers.HelperAtualizarImovel;
import com.br.gsanac.pojos.HidrometroInstHistAtlzCad;
import com.br.gsanac.pojos.ImovelAtlzCadastral;
import com.br.gsanac.pojos.Logradouro;

public interface InterfaceControlador {

	/**
	 * Método responsável por pesquisar o objeto RoteiroBean
	 * 
	 * @author Jonatan Marcos
	 * @since 10/09/2014
	 * @param idImovelAtualizacaoCadastral
	 * @return RoteiroBean
	 * @throws RepositorioException
	 */
	public RoteiroBean pesquisarRoteiroBean(Integer idImovelAtualizacaoCadastral)
			throws ControladorException;

	/**
	 * Método responsável por pesquisar por cpf ou cnpj os imoveis
	 * 
	 * @author Jonathan Marcos
	 * @since 11/09/2014
	 * @param numeroCpfCnpj
	 * @return ArrayList<ImovelAtlzCadastral>
	 * @throws RepositorioException
	 */
	public ArrayList<ImovelAtlzCadastral> pesquisarImovelPeloCPFCNPJ(String numeroCpfCnpj)
			throws ControladorException;

	/**
	 * Método responsável por pesquisar por número do hidrometro os imoveis
	 * 
	 * @author Jonathan Marcos
	 * @param numeroHidrometro
	 * @return Cursor
	 * @throws RepositorioException
	 */
	public ArrayList<ImovelAtlzCadastral> pesquisarImovelPeloHidrometro(String numeroHidrometro)
			throws ControladorException;

	/**
	 * Método responsável por validar todos os campos de um Imovel
	 *
	 * @author André Miranda
	 * @date 18/06/2015
	 *
	 * @param helper
	 * @return Mensagem de Erro
	 * @throws ControladorException
	 */
	public String validarImovel(HelperAtualizarImovel helper);

	/**
	 * Metodo responsavel por validar os campos da aba endereco
	 *
	 * @author Bruno Sá Barreto
	 * @since 11/10/2014
	 *
	 * @param imovelAtlzCadastral
	 * @throws ControladorException
	 */
	public void validarAbaEndereco(ImovelAtlzCadastral imovelAtlzCadastral) throws ControladorException;

	/**
	 * Metodo responsavel por validar os campos da aba Fotos
	 *
	 * @author Bruno Sá Barreto
	 * @since 11/10/2014
	 *
	 * @param imovelAtlzCadastral
	 * @throws ControladorException
	 */
	public String validarAbaFotos(Integer idImovel) throws ControladorException;

	/**
	 * Metodo responsavel por validar os campos da aba Ligação
	 *
	 * @author Bruno Sá Barreto
	 * @since 11/10/2014
	 *
	 * @param imovelAtlzCadastral
	 * @throws ControladorException
	 */
	public String validarAbaLigacao(ImovelAtlzCadastral imovelAtlzCadastral,
			HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad) throws ControladorException;

    /**
     * Atualizar os dados de imóvel
     *
     * [UC1423] - Concluir
     * [SB0002][SB0003][SB0004][SB0007][SB0008][SB0009] - Inserir/Atualizar dados
     *
     * @author André Miranda
     * @since 19/09/2014
     *
     * @param helper
     * @return
     * @throws ControladorException
     */
	public HelperAtualizarImovel atualizarImovel(HelperAtualizarImovel helper) throws ControladorException;

	/**
	 * Este método serve para pesquisar as
	 * ultimas coordenadas cadastradas em
	 * um imóvel na quadra
	 *
	 * @author Bruno Sá Barreto
	 * @since 28/11/2014
	 *
	 * @param nnQuadra
	 * @param cdSetorComercial
	 * @return LatLong
	 * @throws RepositorioException
	 */
	public LatLong pesquisarUltimasCoordenadasInseridasNaQuadra(String nnQuadra, String cdSetorComercial)
			throws ControladorException;

	/**
	 * Este método monta a lista com os
	 * beans usados para preencher o listView
	 * da tela de relatorios por ocorrencia de
	 * cadastro.
	 *
	 * @author Bruno Sá Barreto
	 * @since 17/12/2014
	 *
	 * @return ArrayList<RelatorioOcorrenciaCadastro>
	 * @throws RepositorioException
	 */
	public ArrayList<RelatorioOcorrenciaCadastro> pesquisarListaRelatorioOcorrenciaCadastro()
			throws ControladorException;

	/**
	 * Pesquisa um logradouro associado ao cep informado.
	 *
	 * @author André Miranda
	 * @since 29/12/2014
	 *
	 * @param cep
	 * @return Primeiro logradouro encontrado
	 * @throws RepositorioException
	 */
	public Logradouro pesquisarLogradouroPeloCep(String cep) throws ControladorException;
}
