package com.br.gsanac.persistencia.interfaces;

import java.util.ArrayList;

import org.mapsforge.core.model.LatLong;

import android.database.Cursor;

import com.br.gsanac.bean.RelatorioOcorrenciaCadastro;
import com.br.gsanac.excecoes.RepositorioException;
import com.br.gsanac.pojos.Logradouro;

public interface InterfaceRepositoriosPostgre {

	/**
	 * @author Jonatan Marcos
	 * @since 10/09/2014
	 * @param idImovelAtualizacaoCadastral
	 * @return Cursor
	 * @throws RepositorioException
	 */
	/*
	 * Método responsável por
	 * pesquisar o objeto
	 * RoteiroBean
	 */
	public Cursor pesquisarRoteiroBean(Integer idImovelAtualizacaoCadastral)
			throws RepositorioException;

	/**
	 * @author Jonathan Marcos
	 * @since 11/09/2014
	 * @param numeroCpfCnpj
	 * @return Cursor
	 * @throws RepositorioException
	 */
	/*
	 * Método responsável por
	 * pesquisar por cpf ou cnpj
	 * os imoveis
	 */
	public Cursor pesquisarImovelPeloCPFCNPJ(String numeroCpfCnpj)
			throws RepositorioException;

	/**
	 * @author Jonathan Marcos
	 * @param numeroHidrometro
	 * @return Cursor
	 * @throws RepositorioException
	 */
	/*
	 * Método responsável por
	 * pesquisar por número
	 * do hidrometro os imoveis
	 */
	public Cursor pesquisarImovelPeloHidrometro(String numeroHidrometro)
			throws RepositorioException;

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
			throws RepositorioException;

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
			throws RepositorioException;

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
	public Logradouro pesquisarLogradouroPeloCep(String cep) throws RepositorioException;
}
