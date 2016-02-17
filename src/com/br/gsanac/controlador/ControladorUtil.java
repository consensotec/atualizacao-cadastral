package com.br.gsanac.controlador;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.util.Log;

import com.br.gsanac.conexao.DBConnection;
import com.br.gsanac.entidades.CadastroOcorrencia;
import com.br.gsanac.entidades.Cep;
import com.br.gsanac.entidades.CadastroOcorrencia.CadastroOcorrenciaColunas;
import com.br.gsanac.entidades.Cep.Ceps;
import com.br.gsanac.entidades.ClienteAtlzCadastral;
import com.br.gsanac.entidades.ClienteFoneAtlzCad;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.FonteAbastecimento;
import com.br.gsanac.entidades.Foto;
import com.br.gsanac.entidades.HidrometroCapacidade;
import com.br.gsanac.entidades.HidrometroCapacidade.HidrometroCapacidadeColunas;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad;
import com.br.gsanac.entidades.HidrometroMarca;
import com.br.gsanac.entidades.HidrometroMarca.HidrometroMarcaColunas;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelOcorrencia;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad;
import com.br.gsanac.entidades.LigacaoAguaSituacao;
import com.br.gsanac.entidades.LigacaoAguaSituacao.LigacaoAguaSituacaos;
import com.br.gsanac.entidades.LigacaoEsgotoSituacao;
import com.br.gsanac.entidades.LigacaoEsgotoSituacao.LigacaoEsgotoSituacaos;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.Logradouro.Logradouros;
import com.br.gsanac.entidades.LogradouroBairro;
import com.br.gsanac.entidades.LogradouroBairro.LogradouroBairros;
import com.br.gsanac.entidades.LogradouroCep;
import com.br.gsanac.entidades.Roteiro;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.ControladorException;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.gui.TabsActivity;
import com.br.gsanac.repositorio.RepositorioImovelAtlzCadastral;
import com.br.gsanac.repositorio.RepositorioSistemaParametros;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;


public class ControladorUtil extends ControladorBase<ImovelAtlzCadastral> implements IControladorUtil {

	   private static ControladorUtil instance;
	   //(trocar) chamar direto o controlador
	   private static Fachada fachada = Fachada.getInstance();

	   private ControladorUtil() {
	        super();
	   }

	   private static final String         REGISTER_TYPE_1 = "01";
	   private static final String         REGISTER_TYPE_2 = "02";
	   private static final String         REGISTER_TYPE_3 = "03";
	   private static final String         REGISTER_TYPE_4 = "04";
	   private static final String         REGISTER_TYPE_5 = "05";
	   private static final String         REGISTER_TYPE_6 = "06";
	   private static final String         REGISTER_TYPE_7 = "07";
	   private static final String         REGISTER_TYPE_8 = "08";
	   private static final String         REGISTER_TYPE_9 = "09";
	   private static final String         REGISTER_TYPE_10 = "10";
		
		boolean campoAbaLigacaoAlterado = false;
		boolean fonteAbastecimentoAlterada = false;
		boolean obrigatorio = true;
	   
	    /**
	     * @author Arthur Carvalho
	     * @since 06/12/2012
	     * @return ControladorUtil instance
	     */
	    public static ControladorUtil getInstance() {
	        if (instance == null) {
	            instance = new ControladorUtil();
	        }
	        return instance;
	    }
	    
	    /**
	     * [UC1423] - Concluir
	     * [SB0001][SB0005][SB0006] - Validar 
	     * 
	     * @author Anderson Cabral
	     * @since 09/01/2013
	     * **/
	    private boolean validarConcluir() throws ControladorException{
	    	String mensagemErro = "";
	    	obrigatorio = true;
	    	
	    	ImovelAtlzCadastral imovelAtlzCadastral   		   = TabsActivity.imovel;
	    	ClienteAtlzCadastral clienteAtlzCadastral 		   = TabsActivity.cliente;
	    	HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad  = TabsActivity.hidrometroInstalacaoHist;
	    	Collection<ImovelOcorrencia> colecaoImovelOcorrencia = TabsActivity.colecaoImovelOcorrencia;
	    	
	    	//Valida Campos Obrigatorios ABA Localidade
	    	if ( validarAbaLocalidade(imovelAtlzCadastral) != null && !validarAbaLocalidade(imovelAtlzCadastral).equals("") ) {
	    		mensagemErro += "ABA LOCALIDADE \n";
	    		mensagemErro += validarAbaLocalidade(imovelAtlzCadastral);
	    	}
	    	
	    	//Valida Campos Obrigatorios ABA Endereco
	    	if ( validarAbaEndereco(imovelAtlzCadastral) != null && !validarAbaEndereco(imovelAtlzCadastral).equals("") ) {
	    		mensagemErro += "ABA ENDEREÇO \n";
	    		mensagemErro += validarAbaEndereco(imovelAtlzCadastral);
	    	}
	    	
	    	//Valida Campos Obrigatorios ABA Imovel
	    	if ( validarAbaImovel(imovelAtlzCadastral) != null && !validarAbaImovel(imovelAtlzCadastral).equals("") ) {
	    		mensagemErro += "ABA IMÓVEL \n";
	    		mensagemErro += validarAbaImovel(imovelAtlzCadastral);
	    	}
	    	
	    	//Valida Campos Obrigatorios ABA Fotos
	    	if ( validarAbaFotos(imovelAtlzCadastral.getId()) != null && !validarAbaFotos(imovelAtlzCadastral.getId()).equals("") ) {
		    	mensagemErro += "ABA FOTOS \n";
		    	mensagemErro += validarAbaFotos(imovelAtlzCadastral.getId());
	    	}
	    	
	    	//[SB0001] - Validar dados aba Cliente
	    	if(imovelAtlzCadastral.getLigAguaSituacao() != null){
	    		String validarAbaCliente = validarAbaCliente(clienteAtlzCadastral, imovelAtlzCadastral.getLigAguaSituacao().getId());
		    	if (validarAbaCliente != null && !validarAbaCliente.equals("") ) {
		    		mensagemErro += "ABA CLIENTE \n";
		    		mensagemErro += validarAbaCliente;
		    	}
	    	}else{
	    		String validarAbaCliente = validarAbaCliente(clienteAtlzCadastral, null);
	    		if ( validarAbaCliente != null && !validarAbaCliente.equals("") ) {
	    			mensagemErro += "ABA CLIENTE \n";
		    		mensagemErro += validarAbaCliente;	
	    		}
	    	}
	    	
	    	//[SB0006] - Validar Situação da Ligação da Água x Hidrômetro
	    	if ( validarAbaLigacao(imovelAtlzCadastral, hidrometroInstHistAtlzCad) != null && 
	    			!validarAbaLigacao(imovelAtlzCadastral, hidrometroInstHistAtlzCad).equals("") ) {
	    		mensagemErro += "ABA LIGAÇÃO \n";
	    		mensagemErro += validarAbaLigacao(imovelAtlzCadastral, hidrometroInstHistAtlzCad);	    	
	    	}
	    	
	    	//Valida caso o indicador de campos obrigatorios no tablet de alguma ocorrencia cadastro selecionada seja igual a SIM OU 
//	    	o usuario tenha alterado algum dado da aba de ligacao ou a Fonte de Abastecimento.
	    	if(obrigatorio || campoAbaLigacaoAlterado || fonteAbastecimentoAlterada){
		    	//[SB0005] - Validar Fonte de Abastecimento x Situacao da Ligacao da Agua x Ocorrencia de Cadastro
		    	mensagemErro += validarInconsistenciaFonteAbastecimento(imovelAtlzCadastral, colecaoImovelOcorrencia);
	    	}
	    	
        	if ( mensagemErro != null && !mensagemErro.equals("") ) {
        		TabsActivity.mensagemErro = mensagemErro;
        		Util.exibirMensagemErro(getContext(), mensagemErro); 
        		return false;
        	}else{
        		return true;
        	}
	    }
	    
	    /**
	     * [UC1423] - Concluir
	     * [SB0005] - Validar Fonte de Abastecimento x Situacao da Ligacao da Agua x Ocorrencia de Cadastro
	     * 
	     * @author Anderson Cabral
	     * @since 09/01/2013
	     * 
	     * @param ImovelAtlzCadastral
	     * @param Collection<ImovelOcorrencia>
	     * @return mensagemErro
	     * **/
	    private String validarInconsistenciaFonteAbastecimento(ImovelAtlzCadastral imovelAtlzCadastral, Collection<ImovelOcorrencia> colecaoImovelOcorrencia){
	    	String mensagemErro = "";
	    	
	    	//Validar Fonte Abastecimento x Situação da Ligação de Água x Ocorrência Cadastro
	    	if(colecaoImovelOcorrencia != null){
		    	for (ImovelOcorrencia imovelOcorrencia : colecaoImovelOcorrencia) {
					if(imovelAtlzCadastral.getFonteAbastecimento() != null && !imovelAtlzCadastral.getFonteAbastecimento().getId().equals("") 
							&& imovelAtlzCadastral.getLigAguaSituacao() != null && !imovelAtlzCadastral.getLigAguaSituacao().equals("") 
							&& imovelOcorrencia != null && imovelOcorrencia.getCadastroOcorrencia() != null){
						
						String fonteAbastecimento = "";
						if(imovelAtlzCadastral.getFonteAbastecimento().getId() == FonteAbastecimento.COMPESA){
							fonteAbastecimento = "COMPESA";
							
							if(imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.POTENCIAL
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.FACTIVEL
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.CORTADO
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.SUPRIMIDO){
								
								if(imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.SEM_OCORRENCIAS
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PERMITIU_ACESSO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PODE_RESPONDER
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_VISITADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_FECHADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.ANIMAL_BRAVO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_LOCALIZADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DEMOLIDO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DESOCUPADO){
									
									mensagemErro = "Situação da Ligação de Água inválida para a sua Fonte de Abastecimento: " + fonteAbastecimento + " \n ";
								}
							}else if(imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.LIGADO){
								if(imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DEMOLIDO){
									
									mensagemErro = "Situação da Ligação de Água inválida para a sua Fonte de Abastecimento: " + fonteAbastecimento + " \n ";
								}
							}
						}else if(imovelAtlzCadastral.getFonteAbastecimento().getId() == FonteAbastecimento.POCO){
							fonteAbastecimento = "POÇO";
							
							if(imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.POTENCIAL
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.FACTIVEL
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.CORTADO
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.SUPRIMIDO){
								
								if(imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DEMOLIDO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DESOCUPADO){
									
									mensagemErro = "Situação da Ligação de Água inválida para a sua Fonte de Abastecimento: " + fonteAbastecimento + " \n ";
								}
							}else if(imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.LIGADO){
								if(imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.SEM_OCORRENCIAS
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PERMITIU_ACESSO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PODE_RESPONDER
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_VISITADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_FECHADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.ANIMAL_BRAVO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_LOCALIZADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DEMOLIDO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DESOCUPADO){
									
									mensagemErro = "Situação da Ligação de Água inválida para a sua Fonte de Abastecimento: " + fonteAbastecimento + " \n ";
								}
							}
						}else if(imovelAtlzCadastral.getFonteAbastecimento().getId() == FonteAbastecimento.VIZINHO){
							fonteAbastecimento = "VIZINHO";
							
							if(imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.POTENCIAL
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.FACTIVEL
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.CORTADO
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.SUPRIMIDO){
								
								if(imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DEMOLIDO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DESOCUPADO){
									
									mensagemErro = "Situação da Ligação de Água inválida para a sua Fonte de Abastecimento: " + fonteAbastecimento + " \n ";
								}
							}else if(imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.LIGADO){
								if(imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.SEM_OCORRENCIAS
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PERMITIU_ACESSO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PODE_RESPONDER
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_VISITADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_FECHADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.ANIMAL_BRAVO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_LOCALIZADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DEMOLIDO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DESOCUPADO){
									
									mensagemErro = "Situação da Ligação de Água inválida para a sua Fonte de Abastecimento: " + fonteAbastecimento + " \n ";
								}
							}
						}else if(imovelAtlzCadastral.getFonteAbastecimento().getId() == FonteAbastecimento.CACIMBA){
							fonteAbastecimento = "CACIMBA";
							
							if(imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.POTENCIAL
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.FACTIVEL 
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.CORTADO
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.SUPRIMIDO){
								
								if(imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DEMOLIDO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DESOCUPADO){
									
									mensagemErro = "Situação da Ligação de Água inválida para a sua Fonte de Abastecimento: " + fonteAbastecimento + " \n ";
								}
							}else if(imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.LIGADO){
								if(imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.SEM_OCORRENCIAS
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PERMITIU_ACESSO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PODE_RESPONDER
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_VISITADO 
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_FECHADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.ANIMAL_BRAVO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_LOCALIZADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DEMOLIDO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DESOCUPADO){
									
									mensagemErro = "Situação da Ligação de Água inválida para a sua Fonte de Abastecimento: " + fonteAbastecimento + " \n ";
								}
							}
						}else if(imovelAtlzCadastral.getFonteAbastecimento().getId() == FonteAbastecimento.CHAFARIZ){
							fonteAbastecimento = "CHAFARIZ";
							
							if(imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.POTENCIAL
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.FACTIVEL
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.CORTADO
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.SUPRIMIDO){
								
								if(imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DEMOLIDO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DESOCUPADO){
									
									mensagemErro = "Situação da Ligação de Água inválida para a sua Fonte de Abastecimento: " + fonteAbastecimento + " \n ";
								}
							}else if(imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.LIGADO){
								if(imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.SEM_OCORRENCIAS
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PERMITIU_ACESSO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PODE_RESPONDER
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_VISITADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_FECHADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.ANIMAL_BRAVO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_LOCALIZADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DEMOLIDO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DESOCUPADO){
									
									mensagemErro = "Situação da Ligação de Água inválida para a sua Fonte de Abastecimento: " + fonteAbastecimento + " \n ";
								}
							}
						}else if(imovelAtlzCadastral.getFonteAbastecimento().getId() == FonteAbastecimento.CARRO_PIPA){
							fonteAbastecimento = "CARRO PIPA";
							
							if(imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.POTENCIAL
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.FACTIVEL 
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.CORTADO
									|| imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.SUPRIMIDO){
								
								if(imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DEMOLIDO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DESOCUPADO){
									
									mensagemErro = "Situação da Ligação de Água inválida para a sua Fonte de Abastecimento: " + fonteAbastecimento + " \n ";
								}
							}else if(imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.LIGADO){
								if(imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.SEM_OCORRENCIAS
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PERMITIU_ACESSO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PODE_RESPONDER
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_VISITADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_FECHADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.ANIMAL_BRAVO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_LOCALIZADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DEMOLIDO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DESOCUPADO){
									
									mensagemErro = "Situação da Ligação de Água inválida para a sua Fonte de Abastecimento: " + fonteAbastecimento + " \n ";
								}
							}
						}else if(imovelAtlzCadastral.getFonteAbastecimento().getId() == FonteAbastecimento.SEM_ABASTECIMENTO){
							fonteAbastecimento = "SEM ABASTECIMENTO";
							
							if(imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.LIGADO){
								if(imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.SEM_OCORRENCIAS
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PERMITIU_ACESSO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.CLIENTE_NAO_PODE_RESPONDER
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_VISITADO 
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_FECHADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.ANIMAL_BRAVO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_NAO_LOCALIZADO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DEMOLIDO
										|| imovelOcorrencia.getCadastroOcorrencia().getId() == CadastroOcorrencia.IMOVEL_DESOCUPADO){
									
									mensagemErro = "Situação da Ligação de Água inválida para a sua Fonte de Abastecimento: " + fonteAbastecimento + " \n ";
								}
							}
						}
					}
		    	}
	    	}
	    	return mensagemErro;
	    }
	    
	    /**
	     * [UC1423] - Concluir
	     * [SB0002][SB0003][SB0004][SB0007][SB0008][SB0009] - Inserir/Atualizar dados
	     * 
	     * @author Anderson Cabral
	     * @throws RepositorioException 
	     * @since 10/01/2013
	     * **/
	    public ImovelAtlzCadastral concluir()throws ControladorException, RepositorioException{
	    	if(validarConcluir()){
			ImovelAtlzCadastral imovelAtlzCadastral = TabsActivity.imovel;
			ClienteAtlzCadastral clienteAtlzCadastral = TabsActivity.cliente;
			HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad = TabsActivity.hidrometroInstalacaoHist;
			HidrometroInstHistAtlzCad hidrometroInstHistAtlzCadExcluir = TabsActivity.hidrometroInstalacaoHistInicial;
			Collection<ClienteFoneAtlzCad> colecaoClienteFoneAtlzCad = TabsActivity.colecaoClienteFone;
			Collection<ClienteFoneAtlzCad> colecaoClienteFoneAtlzCadExcluir = TabsActivity.colecaoClienteFoneIncial;
			Collection<ImovelSubCategAtlzCad> colImoveisSubCategoria = TabsActivity.colImoveisSubCategoria;
			Collection<ImovelSubCategAtlzCad> colImoveisSubCategoriaExcluir = TabsActivity.colImoveisSubCategoriaInicial;
			Collection<ImovelOcorrencia> colecaoImovelOcorrencia = TabsActivity.colecaoImovelOcorrencia;
			Collection<ImovelOcorrencia> colecaoImovelOcorrenciaExcluir = TabsActivity.colecaoImovelOcorrenciaInicial;
			Foto fotoFrenteCasa = TabsActivity.fotoFrenteDaCasa;
			Foto fotoHidrometro = TabsActivity.fotoHidrometro;
			Foto fotoFrenteCasaInicial = TabsActivity.fotoFrenteDaCasaInicial;
			Foto fotoHidrometroInicial = TabsActivity.fotoHidrometroInicial;
	    	
		    	try {
		    		String integracao = null;
		    		
		    	   imovelAtlzCadastral.setIndicadorFinalizado(ConstantesSistema.FINALIZADO);
		 		   imovelAtlzCadastral.setIndicadorTransmitido(ConstantesSistema.INDICADOR_NAO_TRANSMITIDO);
		 		   imovelAtlzCadastral.setDataVisita(new Date());
		 		   
		 		   	SistemaParametros sistemaParametros = new SistemaParametros();
					sistemaParametros = (SistemaParametros) fachada.pesquisar(sistemaParametros, null, null);
					
					if(imovelAtlzCadastral.getEnderecoReferencia().getId() == ConstantesSistema.SEM_NUMERO){
						imovelAtlzCadastral.setNumeroImovel(ConstantesSistema.SEM_NUMERO_ONLINE_OFFLINE);
					}
					
					//Inserir/Atualiza Imovel Atualizacao Cadastral
					if(imovelAtlzCadastral.getImovelId() == null || imovelAtlzCadastral.getImovelId().equals("")
							|| (imovelAtlzCadastral.getIndicadorImovelNovoComMatricula() != null 
								&& imovelAtlzCadastral.getIndicadorImovelNovoComMatricula().intValue() == ConstantesSistema.SIM)){
						
						Integer quantidade = Integer.valueOf(sistemaParametros.getQuantidadeImovel()) + 1;
						sistemaParametros.setQuantidadeImovel(String.valueOf(quantidade));						
						
						imovelAtlzCadastral.setPosicao(quantidade);
						
						fachada.update(sistemaParametros);
						
						integracao = imovelAtlzCadastral.getLocalidadeId() +""+ imovelAtlzCadastral.getCodigoSetorComercial() +""+ imovelAtlzCadastral.getNumeroQuadra() +""+
								imovelAtlzCadastral.getNumeroLote() +""+ imovelAtlzCadastral.getNumeroSubLote() +  Util.obterAAAAMMDDHHMMSS(new Date());
						
						imovelAtlzCadastral.setIntegracaoID(integracao);
						
						long idImovel = 0;
						
						imovelAtlzCadastral.setLogin(sistemaParametros.getLogin());
					
						idImovel = fachada.inserir(imovelAtlzCadastral);
						
						imovelAtlzCadastral.setId(String.valueOf(idImovel));
					}else{
						
						integracao = imovelAtlzCadastral.getLocalidadeId() +""+ imovelAtlzCadastral.getCodigoSetorComercial() +""+ imovelAtlzCadastral.getNumeroQuadra() +""+
								imovelAtlzCadastral.getNumeroLote() +""+ imovelAtlzCadastral.getNumeroSubLote() + Util.obterAAAAMMDDHHMMSS(new Date());
						
						imovelAtlzCadastral.setIntegracaoID(integracao);
						
						if(imovelAtlzCadastral.getImovelId() != null && imovelAtlzCadastral.getImovelId().intValue() == 0){
							imovelAtlzCadastral.setImovelId(null);
						}
						imovelAtlzCadastral.setLogin(sistemaParametros.getLogin());
						fachada.update(imovelAtlzCadastral);
					}
										
					//Inserir/Atualiza Hidrometro Instalacao historico Atualizacao Cadastral
					if(hidrometroInstHistAtlzCad != null){
						if(hidrometroInstHistAtlzCad.getId() == null || hidrometroInstHistAtlzCad.getId().equals("")){
							hidrometroInstHistAtlzCad.setImovelAtlzCadastral(imovelAtlzCadastral);
							fachada.inserir(hidrometroInstHistAtlzCad);
						}else{
							hidrometroInstHistAtlzCad.setImovelAtlzCadastral(imovelAtlzCadastral);
							fachada.update(hidrometroInstHistAtlzCad);
						}       			
					}else if(hidrometroInstHistAtlzCadExcluir != null && hidrometroInstHistAtlzCadExcluir.getId() != null && !hidrometroInstHistAtlzCadExcluir.getId().equals("")){
						//Exclui hidrometroInstHistAtlzCad Inicial
						fachada.remover(hidrometroInstHistAtlzCadExcluir);	
					}
					
					//Caso o cliente não for informado:
					if((imovelAtlzCadastral.getLigAguaSituacao().getId().equals(LigacaoAguaSituacao.FACTIVEL) || 
							imovelAtlzCadastral.getLigAguaSituacao().getId().equals(LigacaoAguaSituacao.POTENCIAL)) 
							&& !this.validarAbaCliente(clienteAtlzCadastral, LigacaoAguaSituacao.LIGADO).equals("")){
						
						if ( clienteAtlzCadastral != null ) {
							//Caso cliente ja existente
							if(clienteAtlzCadastral.getId() != null && !clienteAtlzCadastral.getId().equals("")){
								
								if(colecaoClienteFoneAtlzCadExcluir != null){
									for(ClienteFoneAtlzCad clienteFoneAtlzCad : colecaoClienteFoneAtlzCadExcluir){
				    					//remove clienteFone
				    					fachada.remover(clienteFoneAtlzCad);
									}
								}
							}
							
							/*** teste ***/
//				            String selection = ClienteFoneAtlzCadColunas.CLIENTEATLZCAD_ID + "=?";
//				        	
//				            String[] selectionArgs = new String[] {
//				                String.valueOf(clienteAtlzCadastral.getId()),
//				            };
//				            
//				            ArrayList<ClienteFoneAtlzCad> colecaoClienteFone = (ArrayList<ClienteFoneAtlzCad>) fachada.pesquisarLista(ClienteFoneAtlzCad.class, selection, selectionArgs, null);				            
				            /******** ***/
				            
							//remove cliente
							fachada.remover(clienteAtlzCadastral);
							clienteAtlzCadastral = null;
							TabsActivity.cliente = null;
						} 
//						else {
//							//inseri cliente
//							clienteAtlzCadastral.setImovelAtlzCadastral(imovelAtlzCadastral);
//							long idCliente = fachada.inserir(clienteAtlzCadastral);
//		    				clienteAtlzCadastral.setId(String.valueOf(idCliente));
//		    				
//		    				//Exclui clienteFone Iniciais
//							if(colecaoClienteFoneAtlzCadExcluir != null){	
//								for(ClienteFoneAtlzCad clienteFoneAtlzCad : colecaoClienteFoneAtlzCadExcluir){
//									clienteFoneAtlzCad.setClienteAtlzCadastral(clienteAtlzCadastral);
//									fachada.remover(clienteFoneAtlzCad);
//								}
//								
//								//inseri clienteFone
//								if(colecaoClienteFoneAtlzCad != null){	
//									for(ClienteFoneAtlzCad clienteFoneAtlzCad : colecaoClienteFoneAtlzCad){       					
//										clienteFoneAtlzCad.setClienteAtlzCadastral(clienteAtlzCadastral);
//										fachada.inserir(clienteFoneAtlzCad);
//									}
//								}
//							}
//						}
					}else{
						
						if ( clienteAtlzCadastral != null ) {
							
							if(clienteAtlzCadastral.getId() != null && !clienteAtlzCadastral.getId().equals("")){
			    				//Atualiza cliente
								clienteAtlzCadastral.setImovelAtlzCadastral(imovelAtlzCadastral);
			    				fachada.update(clienteAtlzCadastral);
							}else{
			    				//inseri cliente
								clienteAtlzCadastral.setImovelAtlzCadastral(imovelAtlzCadastral);
								long idCliente = fachada.inserir(clienteAtlzCadastral);
			    				clienteAtlzCadastral.setId(String.valueOf(idCliente));
	
							}
							
							//Exclui clienteFone Iniciais
							if(colecaoClienteFoneAtlzCadExcluir != null){	
								for(ClienteFoneAtlzCad clienteFoneAtlzCad : colecaoClienteFoneAtlzCadExcluir){
									clienteFoneAtlzCad.setClienteAtlzCadastral(clienteAtlzCadastral);
									fachada.remover(clienteFoneAtlzCad);
								}
							}
							
							//inseri clienteFone
							if(colecaoClienteFoneAtlzCad != null){
								//valida se o cliente ja possui um telefone com o indicador fone padrao
								//caso contrario vai inserir um telefone com o indicador ativo
								boolean existeFonePadrao = false;
								for(ClienteFoneAtlzCad clienteFoneAtlzCad1 : colecaoClienteFoneAtlzCad){       					
									if (clienteFoneAtlzCad1.getIndicadorFonePadrao() != null && clienteFoneAtlzCad1.getIndicadorFonePadrao().toString().equals("1") ) {
										existeFonePadrao = true;
									}
								}
								
								boolean primeiraVez = true;
								for(ClienteFoneAtlzCad clienteFoneAtlzCad : colecaoClienteFoneAtlzCad){       					
									clienteFoneAtlzCad.setClienteAtlzCadastral(clienteAtlzCadastral);
									if ( primeiraVez && !existeFonePadrao ) {
										clienteFoneAtlzCad.setIndicadorFonePadrao(1);
										primeiraVez = false;
									} else {
										clienteFoneAtlzCad.setIndicadorFonePadrao(2);
									}
									fachada.inserir(clienteFoneAtlzCad);
								}
							}
						}
					}
					
					//Exclui Sub Categorias Iniciais
					if(colImoveisSubCategoriaExcluir != null){
						for(ImovelSubCategAtlzCad imovelSubCategAtlzCad : colImoveisSubCategoriaExcluir){
							imovelSubCategAtlzCad.setImovelAtlzCadastral(imovelAtlzCadastral);
								fachada.remover(imovelSubCategAtlzCad);
						}
					}
					
					//inseri Sub Categorias
					if(colImoveisSubCategoria != null){						
						//Inclui apenas as selecionadas
						for(ImovelSubCategAtlzCad imovelSubCategAtlzCad : colImoveisSubCategoria){
							imovelSubCategAtlzCad.setImovelAtlzCadastral(imovelAtlzCadastral);
							fachada.inserir(imovelSubCategAtlzCad);
						}
					}
					
					//Exclui Imovel Ocorrencia Iniciais
					if(colecaoImovelOcorrenciaExcluir != null){
						for(ImovelOcorrencia imovelOcorrencia : colecaoImovelOcorrenciaExcluir){
							imovelOcorrencia.setImovelAtlzCadastral(imovelAtlzCadastral);
							fachada.remover(imovelOcorrencia);
						}
					}
					
					//inseri Imovel Ocorrencia
					if(colecaoImovelOcorrencia != null){
						for(ImovelOcorrencia imovelOcorrencia : colecaoImovelOcorrencia){
							imovelOcorrencia.setImovelAtlzCadastral(imovelAtlzCadastral);
							fachada.inserir(imovelOcorrencia);
						}
					}
					
					
					//Inserir/Alterar Foto Frente da Casa
					if(fotoFrenteCasa != null){
						
						String caminhoFoto = fotoFrenteCasa.getFotoPath();
						File foto = new File(caminhoFoto);
						
						File fileNovo = new File(Util.getFotoFile(integracao, fotoFrenteCasa.getFotoTipo()));
			        	foto.renameTo(fileNovo);
						
						fotoFrenteCasa.setFotoPath(fileNovo.getAbsolutePath());

						if(fotoFrenteCasa.getId() != null){
							fachada.update(fotoFrenteCasa);
						}else{
							fachada.inserir(fotoFrenteCasa);
						}
					}else if(fotoFrenteCasaInicial != null){
						fachada.remover(fotoFrenteCasaInicial);
					}
					
					
					
					
					//Inserir/Alterar Foto Hidrometro
					if(fotoHidrometro != null){
						String caminhoFoto = fotoHidrometro.getFotoPath();
						File foto = new File(caminhoFoto);
						
						File fileNovo = new File(Util.getFotoFile(integracao, fotoHidrometro.getFotoTipo()));
			        	foto.renameTo(fileNovo);
						
			        	fotoHidrometro.setFotoPath(fileNovo.getAbsolutePath());
						if(fotoHidrometro.getId() != null){
							fachada.update(fotoHidrometro);
						}else{
							fachada.inserir(fotoHidrometro);
						}
					}else if(fotoHidrometroInicial != null){
						fachada.remover(fotoHidrometroInicial);
					}
		    	} catch (FachadaException e) {
					Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
				}
		    	
		    	DBConnection.getInstance(context).closeDatabase();
		    	
		    	return imovelAtlzCadastral;	
	    	}else{
	    		return null;
	    	}
	    }

	    /**
	     * @author Arthur Carvalho
	     * @since 06/12/2012
	     */
	    public String validarAbaLocalidade(ImovelAtlzCadastral imovelAtlzCadastral) throws ControladorException {
	        String mensagemErro = "";
	        
	        if(imovelAtlzCadastral.getCodigoSetorComercial() == null || imovelAtlzCadastral.getCodigoSetorComercial().equals(0)){
	        	mensagemErro +=  "Informe Setor Comercial \n";			
			}
	        
	        if(imovelAtlzCadastral.getNumeroQuadra() == null || imovelAtlzCadastral.getNumeroQuadra().equals(0)){
	        	mensagemErro +=  "Informe Quadra \n";			
			}
	        
	        if(imovelAtlzCadastral.getNumeroLote() == null ){
	        	mensagemErro +=  "Informe Lote \n";			
			}
	        
	        if ( imovelAtlzCadastral.getNumeroSubLote() == null ) {
	        	mensagemErro +=  "Informe Sublote \n";
	        }
			
	        return mensagemErro;
	    }
	    
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
	    public String validarAbaEndereco(ImovelAtlzCadastral imovelAtlzCadastral) throws ControladorException {
	    	String mensagemErro = "";
	    	
	    	if(imovelAtlzCadastral.getLogradouro() == null){
	    		mensagemErro +=  "Informe Logradouro \n";
	    	}
	    	
	    	if(imovelAtlzCadastral.getEnderecoReferencia() == null || imovelAtlzCadastral.getEnderecoReferencia().getId().equals(ConstantesSistema.ITEM_INVALIDO) || imovelAtlzCadastral.getEnderecoReferencia().getId().equals(0)){
	    		mensagemErro +=  "Informe Referência \n";
	    	}
	    	
	    	if(imovelAtlzCadastral.getNumeroImovel() == null || (imovelAtlzCadastral.getNumeroImovel().equals("")
	    			&& (imovelAtlzCadastral.getEnderecoReferencia()==null 
	    			|| imovelAtlzCadastral.getEnderecoReferencia().getId() != ConstantesSistema.SEM_NUMERO))) {

	    			mensagemErro += "Informe Número \n";
			}
	    	
	    	if(imovelAtlzCadastral.getLogradouroBairroId() == null || imovelAtlzCadastral.getLogradouroBairroId().equals(ConstantesSistema.ITEM_INVALIDO) || imovelAtlzCadastral.getLogradouroBairroId().equals(0)){
	    		mensagemErro +=  "Informe Bairro \n";
	    	}
	    	
	    	if(imovelAtlzCadastral.getLogradouroCEPId() == null || imovelAtlzCadastral.getLogradouroCEPId().equals(ConstantesSistema.ITEM_INVALIDO) || imovelAtlzCadastral.getLogradouroCEPId().equals(0)){
	    		mensagemErro +=  "Informe CEP \n";
	    	}
	    	
	    	
	    	
	    
	    	return mensagemErro;
	    }

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
		public String validarAbaCliente(ClienteAtlzCadastral clienteAtlzCadastral, Integer idLigacaoAguaSituacao) throws ControladorException {
			String mensagemErro = "";
			boolean clienteObrigatorio = false;
				
				if(idLigacaoAguaSituacao != null){
					if(!idLigacaoAguaSituacao.equals(LigacaoAguaSituacao.FACTIVEL) && !idLigacaoAguaSituacao.equals(LigacaoAguaSituacao.POTENCIAL)){
						clienteObrigatorio = true;
					}
				}else{
					clienteObrigatorio = true;
				}
				
				//verifica se foi selecionado alguma ocorrencia de cadastro com o indicador de campos obrigatorios igual a 1
				obrigatorio = this.verificarOcorrenciaCadastroSelecionada();
				boolean imovelNovo =  TabsActivity.imovelInicial == null || TabsActivity.imovelInicial.getId() == null || TabsActivity.imovelInicial.getId().equals(0);
				
				if(obrigatorio || !obrigatorio && imovelNovo){
					if(clienteAtlzCadastral != null){
						if(clienteAtlzCadastral.getClienteTipo() != null && clienteAtlzCadastral.getClienteTipo().getId() != null){
							if(clienteObrigatorio && (clienteAtlzCadastral.getClienteTipo().getId() == null || clienteAtlzCadastral.getClienteTipo().getId() == ConstantesSistema.ITEM_INVALIDO)){
								mensagemErro += "Informe Tipo de Cliente \n";
							}else{
								//Validar Dados de Pessoa Física
								if(clienteAtlzCadastral.getClienteTipo().getIndicadorPessoa().equals(ConstantesSistema.PESSOA_FISICA)){
									//Validar CPF
									if(clienteAtlzCadastral.getNumeroCPFCNPPJ() != null && clienteAtlzCadastral.getNumeroCPFCNPPJ().length() != 0 && !Util.validateCPF(clienteAtlzCadastral.getNumeroCPFCNPPJ())){
										mensagemErro += "CPF Inválido \n";
									}
									
									//Validar Nome do Cliente
									if(clienteAtlzCadastral.getNomeCliente() != null && !clienteAtlzCadastral.getNomeCliente().trim().equals("")){
										if(clienteAtlzCadastral.getNomeCliente().length() > 100){
											mensagemErro += "Nome Inválido \n";
										}
									}else if(clienteObrigatorio){
										mensagemErro += "Informe Nome \n";
									}
									
									//Validar Sexo do Cliente
									if(clienteObrigatorio && clienteAtlzCadastral.getPessoaSexo() == null){
										mensagemErro += "Informe Sexo \n";
									}
									
									//Validar Data de Nascimento do Cliente
									if(!TabsActivity.dataNascimentoValida){
										mensagemErro += "Data de Nascimento Inválida \n";
									}else if(clienteAtlzCadastral.getDataNascimento() != null && clienteAtlzCadastral.getDataNascimento().getTime() > (new Date()).getTime()){
										mensagemErro += "Data de Nascimento deve ser menor que a Data Atual \n";
									}
									
									mensagemErro = this.validarDadosRgCliente(clienteAtlzCadastral, mensagemErro);
									
								//Validar Dados de Pessoa Jurídica
								}else if(clienteAtlzCadastral.getClienteTipo().getIndicadorPessoa().equals(ConstantesSistema.PESSOA_JURIDICA)){
									//Validar CNPJ
									if(clienteAtlzCadastral.getNumeroCPFCNPPJ() != null && clienteAtlzCadastral.getNumeroCPFCNPPJ().length() != 0 && !Util.validateCNPJ(clienteAtlzCadastral.getNumeroCPFCNPPJ())){
										mensagemErro += "CNPJ Inválido \n";
									}
									
									//Validar Nome do Cliente
									if(clienteAtlzCadastral.getNomeCliente() != null && !clienteAtlzCadastral.getNomeCliente().trim().equals("")){
										if(clienteAtlzCadastral.getNomeCliente().length() > 100){
											mensagemErro += "Nome de Cliente Inválido \n";
										}
									}else if(clienteObrigatorio){
										mensagemErro += "Informe Nome do Cliente \n";
									}
								}else{
									mensagemErro += "Tipo de Cliente Inválido \n";
								}
							}
						}else if(clienteObrigatorio){
							mensagemErro += "Informe Tipo de Cliente \n";
						}
					}else if(clienteObrigatorio){
						mensagemErro += "Informe Nome \n";
						mensagemErro += "Informe Sexo \n";
					}
					
				}else{
					if(clienteAtlzCadastral != null){
						
						//Cliente Tipo
						if(clienteObrigatorio){
							if(clienteAtlzCadastral.getClienteTipo() == null || clienteAtlzCadastral.getClienteTipo().getId() == null 
									|| clienteAtlzCadastral.getClienteTipo().getId() == ConstantesSistema.ITEM_INVALIDO){
								mensagemErro += "Informe Tipo de Cliente \n";
							}
						}
						
						//Validar Nome do Cliente
						if(clienteAtlzCadastral.getNomeCliente() != null && !clienteAtlzCadastral.getNomeCliente().trim().equals("")){
							if(clienteAtlzCadastral.getNomeCliente().length() > 100){
								mensagemErro += "Nome Inválido \n";
							}
						}else if(clienteObrigatorio){
							mensagemErro += "Informe Nome \n";
						}
					}else if(clienteObrigatorio){
						mensagemErro += "Informe Nome \n";
					}
				}
			
			return mensagemErro;
		}
		
		/**
		 * Método responsável por validar os dados do RG do Cliente
		 * 
		 * @author Davi Menezes
		 * @date 28/12/2012
		 */
		private String validarDadosRgCliente(ClienteAtlzCadastral clienteAtlzCadastral, String mensagemErro) throws ControladorException {
			//Validar Dados com Número do RG Informado
			if(clienteAtlzCadastral.getNumeroRG() != null && !clienteAtlzCadastral.getNumeroRG().equals("")){
				if(clienteAtlzCadastral.getOrgaoExpedidorRg() == null || clienteAtlzCadastral.getOrgaoExpedidorRg().getId() == ConstantesSistema.ITEM_INVALIDO || clienteAtlzCadastral.getOrgaoExpedidorRg().getId().equals(0)){
					mensagemErro += "Informe Órgão Expedidor \n";
				}
				if(clienteAtlzCadastral.getUnidadeFederacao() == null || clienteAtlzCadastral.getUnidadeFederacao().getId() == ConstantesSistema.ITEM_INVALIDO || clienteAtlzCadastral.getUnidadeFederacao().getId().equals(0)){
					mensagemErro += "Informe Unidade Federação \n";
				}
				if(!TabsActivity.dataEmissaoValida){
					mensagemErro += "Data de Emissão Inválida \n";
				}else if(clienteAtlzCadastral.getDataNascimento() != null &&  clienteAtlzCadastral.getDataEmissaoRg() != null && clienteAtlzCadastral.getDataEmissaoRg().getTime() < clienteAtlzCadastral.getDataNascimento().getTime()){
					mensagemErro += "Data de Emissão deve ser maior que a Data de Nascimento \n";
				}else if(clienteAtlzCadastral.getDataEmissaoRg() != null && clienteAtlzCadastral.getDataEmissaoRg().getTime() > (new Date()).getTime()){
					mensagemErro += "Data de Emissão deve ser menor ou igual a Data Atual \n";
				}
			
			//Validar Dados com Órgão Expedidor do RG Informado
			}else if(clienteAtlzCadastral.getOrgaoExpedidorRg() != null && clienteAtlzCadastral.getOrgaoExpedidorRg().getId() != ConstantesSistema.ITEM_INVALIDO && !clienteAtlzCadastral.getOrgaoExpedidorRg().getId().equals(0)){
				if(clienteAtlzCadastral.getNumeroRG() == null || clienteAtlzCadastral.getNumeroRG().equals("")){
					mensagemErro += "Informe RG \n";
				}
				if(clienteAtlzCadastral.getUnidadeFederacao() == null || clienteAtlzCadastral.getUnidadeFederacao().getId() == ConstantesSistema.ITEM_INVALIDO || clienteAtlzCadastral.getUnidadeFederacao().getId().equals(0)){
					mensagemErro += "Informe Unidade Federação \n";
				}
				if(!TabsActivity.dataEmissaoValida){
					mensagemErro += "Data de Emissão Inválida \n";
				}else if(clienteAtlzCadastral.getDataNascimento() != null &&  clienteAtlzCadastral.getDataEmissaoRg() != null && clienteAtlzCadastral.getDataEmissaoRg().getTime() < clienteAtlzCadastral.getDataNascimento().getTime()){
					mensagemErro += "Data de Emissão deve ser maior que a Data de Nascimento \n";
				}else if(clienteAtlzCadastral.getDataEmissaoRg() != null && clienteAtlzCadastral.getDataEmissaoRg().getTime() > (new Date()).getTime()){
					mensagemErro += "Data de Emissão deve ser menor ou igual a Data Atual \n";
				}
			
			//Validar Dados com Unidade Federação Informado
			}else if(clienteAtlzCadastral.getUnidadeFederacao() != null && clienteAtlzCadastral.getUnidadeFederacao().getId() != ConstantesSistema.ITEM_INVALIDO && !clienteAtlzCadastral.getUnidadeFederacao().getId().equals(0)){
				if(clienteAtlzCadastral.getNumeroRG() == null || clienteAtlzCadastral.getNumeroRG().equals("")){
					mensagemErro += "Informe RG \n";
				}
				if(clienteAtlzCadastral.getOrgaoExpedidorRg() == null  || clienteAtlzCadastral.getOrgaoExpedidorRg().getId() == ConstantesSistema.ITEM_INVALIDO || clienteAtlzCadastral.getOrgaoExpedidorRg().getId().equals(0)){
					mensagemErro += "Informe Órgão Expedidor \n";
				}
				if(!TabsActivity.dataEmissaoValida){
					mensagemErro += "Data de Emissão Inválida \n";
				}else if(clienteAtlzCadastral.getDataNascimento() != null &&  clienteAtlzCadastral.getDataEmissaoRg() != null && clienteAtlzCadastral.getDataEmissaoRg().getTime() < clienteAtlzCadastral.getDataNascimento().getTime()){
					mensagemErro += "Data de Emissão deve ser maior que a Data de Nascimento \n";
				}else if(clienteAtlzCadastral.getDataEmissaoRg() != null && clienteAtlzCadastral.getDataEmissaoRg().getTime() > (new Date()).getTime()){
					mensagemErro += "Data de Emissão deve ser menor ou igual a Data Atual \n";
				}
			
			//Validar Dados com Data de Emissão Informado
			}else if(clienteAtlzCadastral.getDataEmissaoRg() != null || !TabsActivity.dataEmissaoValida){
				if(!TabsActivity.dataEmissaoValida){
					mensagemErro += "Data de Emissão Inválida \n";
				}else if(clienteAtlzCadastral.getDataNascimento() != null &&  clienteAtlzCadastral.getDataEmissaoRg() != null && clienteAtlzCadastral.getDataEmissaoRg().getTime() < clienteAtlzCadastral.getDataNascimento().getTime()){
					mensagemErro += "Data de Emissão deve ser maior que a Data de Nascimento \n";
				}else if(clienteAtlzCadastral.getDataEmissaoRg() != null && clienteAtlzCadastral.getDataEmissaoRg().getTime() > (new Date()).getTime()){
					mensagemErro += "Data de Emissão deve ser menor ou igual a Data Atual \n";
				}
				
				if(clienteAtlzCadastral.getNumeroRG() == null || clienteAtlzCadastral.getNumeroRG().equals("")){
					mensagemErro += "Informe RG \n";
				}
				if(clienteAtlzCadastral.getOrgaoExpedidorRg() == null || clienteAtlzCadastral.getOrgaoExpedidorRg().getId() == ConstantesSistema.ITEM_INVALIDO || clienteAtlzCadastral.getOrgaoExpedidorRg().getId().equals(0)){
					mensagemErro += "Informe Órgao Expedidor \n";
				}
				if(clienteAtlzCadastral.getUnidadeFederacao() == null || clienteAtlzCadastral.getUnidadeFederacao().getId() == ConstantesSistema.ITEM_INVALIDO || clienteAtlzCadastral.getUnidadeFederacao().getId().equals(0)){
					mensagemErro += "Informe Unidade Federação \n";
				}
			}
			
			return mensagemErro;
		}
		
	    /**
	     * Metodo responsavel por validar os campos da aba ligacao
	     * 
	     * @author Anderson Cabral
	     * @date 03/01/2013
	     *
	     * @param imovelAtlzCadastral
	     * @param hidrometroInstHistAtlzCad
	     * @return Mensagem de Erro
	     * @throws ControladorException
	     * @throws FachadaException 
	     */
	    public String validarAbaLigacao(ImovelAtlzCadastral imovelAtlzCadastral, HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad) throws ControladorException {
	    	String mensagemErro = "";
	    	
			//verifica se foi selecionado alguma ocorrencia de cadastro com o indicador de campos obrigatorios igual a 1
			obrigatorio = this.verificarOcorrenciaCadastroSelecionada();
	    	campoAbaLigacaoAlterado = false;
			boolean imovelNovo =  TabsActivity.imovelInicial == null || TabsActivity.imovelInicial.getId() == null || TabsActivity.imovelInicial.getId().equals(0);
			
			if(obrigatorio || !obrigatorio && imovelNovo){
				
	    		if(imovelAtlzCadastral.getLigAguaSituacao() == null || imovelAtlzCadastral.getLigAguaSituacao().getId().equals(0)){
	    			mensagemErro += "Informe Situação Ligação Água \n";
	    		}
	    		
	    		if(imovelAtlzCadastral.getLigEsgotoSituacao() == null || imovelAtlzCadastral.getLigEsgotoSituacao().getId().equals(0)){
	    			mensagemErro += "Informe Situação Ligação Esgoto \n";
	    		}
	    		
	    		if(hidrometroInstHistAtlzCad != null){
	        		if(hidrometroInstHistAtlzCad.getHidrometroLocalInst() == null || hidrometroInstHistAtlzCad.getHidrometroLocalInst().getId().equals(0)){
	        			mensagemErro += "Informe Local de Instalação \n";
	        		}
	        		
	        		if(hidrometroInstHistAtlzCad.getHidrometroProtecao() == null){
	        			mensagemErro += "Informe Proteção Tipo \n";
	        		}
	        		
	        		if(hidrometroInstHistAtlzCad.getIndicadorCavalete() == null){
	        			mensagemErro += "Informe Cavalete \n";
	        		}
	        		
	    	    	//Validacao do Numero Hidrometro
	        		if(hidrometroInstHistAtlzCad.getNumeroHidrometro() == null ||  hidrometroInstHistAtlzCad.getNumeroHidrometro().trim().equals("")){
	        			mensagemErro += "Informe Número do Hidrômetro \n";
	        		}else{
	        			mensagemErro += this.validarNumeroHidrometro(imovelAtlzCadastral, hidrometroInstHistAtlzCad); 
	        		}
	        		
	        		//SB0006 - Validar Situacao da Ligacao da Agua x Hidrometro
	        		if(imovelAtlzCadastral.getLigAguaSituacao() != null && imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.POTENCIAL){
	        			mensagemErro += "Situação da Ligação da Água inválida para a instalação do Hidrômetro: POTENCIAL \n";
	        		}else if(imovelAtlzCadastral.getLigAguaSituacao() != null && imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.FACTIVEL){
	        			mensagemErro += "Situação da Ligação da Água inválida para a instalação do Hidrômetro: FACTIVEL \n";
	        		}else if(imovelAtlzCadastral.getLigAguaSituacao() != null && imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.SUPRIMIDO){
	        			mensagemErro += "Situação da Ligação da Água inválida para a instalação do Hidrômetro: SUPRIMIDO \n";
	        		}
	    		}
			}
			//Faz as validacoes nnos campos que sofreram alteracao
			else{
				
				try{
			    	
					//verifica se a situacao da ligacao de agua que veio no imovel existe no banco de dados do tablet 
			    	String selection = LigacaoAguaSituacaos.ID + "=?";	           
			    	String[] selectionArgs = new String[] { String.valueOf(TabsActivity.imovelInicial.getLigAguaSituacao().getId())	};
			   				
			    	LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
			    	ligacaoAguaSituacao = (LigacaoAguaSituacao) fachada.pesquisar(ligacaoAguaSituacao, selection, selectionArgs);
					
					
					//Ligacao Agua
					if((TabsActivity.imovelInicial.getLigAguaSituacao() == null && imovelAtlzCadastral.getLigAguaSituacao() != null) 
							|| (TabsActivity.imovelInicial.getLigAguaSituacao() != null && imovelAtlzCadastral.getLigAguaSituacao() == null) 
							|| (TabsActivity.imovelInicial.getLigAguaSituacao() != null && imovelAtlzCadastral.getLigAguaSituacao() != null &&
									!TabsActivity.imovelInicial.getLigAguaSituacao().getId().equals(imovelAtlzCadastral.getLigAguaSituacao().getId()))){
						
						campoAbaLigacaoAlterado = true;
						
			    		if(imovelAtlzCadastral.getLigAguaSituacao() == null || imovelAtlzCadastral.getLigAguaSituacao().getId().equals(0)){
			    			mensagemErro += "Informe Situação Ligação Água \n";
			    		}
						
					}
					else if((ligacaoAguaSituacao == null || ligacaoAguaSituacao.getId() == null) 
							&& (imovelAtlzCadastral.getLigAguaSituacao() == null 
							|| imovelAtlzCadastral.getLigAguaSituacao().getId().equals(TabsActivity.imovelInicial.getLigAguaSituacao().getId()))){
						
						campoAbaLigacaoAlterado = true;
						mensagemErro += "Informe Situação Ligação Água \n";
					}
					
					//verifica se a situacao da ligacao de esgoto que veio no imovel existe no banco de dados do tablet 
			    	String selectionEsgoto = LigacaoEsgotoSituacaos.ID + "=?";	           
			    	String[] selectionArgsEsgoto = new String[] { String.valueOf(TabsActivity.imovelInicial.getLigEsgotoSituacao().getId())	};
			   				
			    	LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
			    	ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) fachada.pesquisar(ligacaoEsgotoSituacao, selectionEsgoto, selectionArgsEsgoto);
					
					//Ligacao Esgoto
					if((ligacaoEsgotoSituacao == null || ligacaoEsgotoSituacao.getId() == null)	
							|| (TabsActivity.imovelInicial.getLigEsgotoSituacao() == null && imovelAtlzCadastral.getLigEsgotoSituacao() != null) 
							|| (TabsActivity.imovelInicial.getLigEsgotoSituacao() != null && imovelAtlzCadastral.getLigEsgotoSituacao() == null) 
							|| (TabsActivity.imovelInicial.getLigEsgotoSituacao() != null && imovelAtlzCadastral.getLigEsgotoSituacao() != null &&
									!TabsActivity.imovelInicial.getLigEsgotoSituacao().getId().equals(imovelAtlzCadastral.getLigEsgotoSituacao().getId()))){
			    		
						campoAbaLigacaoAlterado = true;
						
			    		if(imovelAtlzCadastral.getLigEsgotoSituacao() == null || imovelAtlzCadastral.getLigEsgotoSituacao().getId().equals(0)){
			    			mensagemErro += "Informe Situação Ligação Esgoto \n";
			    		}					
					}
					else if((ligacaoEsgotoSituacao == null || ligacaoEsgotoSituacao.getId() == null) 
							&& (imovelAtlzCadastral.getLigEsgotoSituacao() == null 
							|| imovelAtlzCadastral.getLigEsgotoSituacao().getId().equals(TabsActivity.imovelInicial.getLigEsgotoSituacao().getId()))){
						
						campoAbaLigacaoAlterado = true;
						mensagemErro += "Informe Situação Ligação Esgoto \n";
					}					
					
		    		if(hidrometroInstHistAtlzCad != null){
		    			
		    			//Local Instalacao
		    			if((TabsActivity.hidrometroInstalacaoHistInicial.getHidrometroLocalInst() == null && (hidrometroInstHistAtlzCad.getHidrometroLocalInst() != null && !hidrometroInstHistAtlzCad.getHidrometroLocalInst().getId().equals(0)))
		    					|| ((TabsActivity.hidrometroInstalacaoHistInicial.getHidrometroLocalInst() != null && !TabsActivity.hidrometroInstalacaoHistInicial.getHidrometroLocalInst().getId().equals(0)) && hidrometroInstHistAtlzCad.getHidrometroLocalInst() == null)
		    					|| (TabsActivity.hidrometroInstalacaoHistInicial.getHidrometroLocalInst() != null && hidrometroInstHistAtlzCad.getHidrometroLocalInst() != null &&
		    							!TabsActivity.hidrometroInstalacaoHistInicial.getHidrometroLocalInst().getId().equals(hidrometroInstHistAtlzCad.getHidrometroLocalInst().getId()))){
		    				
		    				campoAbaLigacaoAlterado = true;
		    				
			        		if(hidrometroInstHistAtlzCad.getHidrometroLocalInst() == null || hidrometroInstHistAtlzCad.getHidrometroLocalInst().getId().equals(0)){
			        			mensagemErro += "Informe Local de Instalação \n";
			        		}
		    			}
		    			
		    			//Protecao Tipo
		    			if(TabsActivity.hidrometroInstalacaoHistInicial.getHidrometroProtecao() != null && hidrometroInstHistAtlzCad.getHidrometroProtecao() == null){        		
		    				campoAbaLigacaoAlterado = true;	
		    				mensagemErro += "Informe Proteção Tipo \n";
		    			}
		    			
		    			//Cavalete
		    			if(TabsActivity.hidrometroInstalacaoHistInicial.getIndicadorCavalete() != null && hidrometroInstHistAtlzCad.getIndicadorCavalete() == null){        		
		    				campoAbaLigacaoAlterado = true;
		    				mensagemErro += "Informe Cavalete \n";
		        		}
		    			
		    			//Numero Hidrometro
		    			if((TabsActivity.hidrometroInstalacaoHistInicial.getNumeroHidrometro() == null && hidrometroInstHistAtlzCad.getNumeroHidrometro() != null)
		    					|| (TabsActivity.hidrometroInstalacaoHistInicial.getNumeroHidrometro() != null && hidrometroInstHistAtlzCad.getNumeroHidrometro() == null)
		    					|| (TabsActivity.hidrometroInstalacaoHistInicial.getNumeroHidrometro() != null &&  hidrometroInstHistAtlzCad.getNumeroHidrometro() != null &&
		    							!TabsActivity.hidrometroInstalacaoHistInicial.getNumeroHidrometro().equalsIgnoreCase(hidrometroInstHistAtlzCad.getNumeroHidrometro()))){
			    			
		    				campoAbaLigacaoAlterado = true;
		    				
			        		if(hidrometroInstHistAtlzCad.getNumeroHidrometro() == null ||  hidrometroInstHistAtlzCad.getNumeroHidrometro().trim().equals("")){
			        			mensagemErro += "Informe Número do Hidrômetro \n";
			        		}else{
			        			mensagemErro += this.validarNumeroHidrometro(imovelAtlzCadastral, hidrometroInstHistAtlzCad); 
			        		}
		    			}
		        		
		    			if(campoAbaLigacaoAlterado){
			        		//SB0006 - Validar Situacao da Ligacao da Agua x Hidrometro
			        		if(imovelAtlzCadastral.getLigAguaSituacao() != null && imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.POTENCIAL){
			        			mensagemErro += "Situação da Ligação da Água inválida para a instalação do Hidrômetro: POTENCIAL \n";
			        		}else if(imovelAtlzCadastral.getLigAguaSituacao() != null && imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.FACTIVEL){
			        			mensagemErro += "Situação da Ligação da Água inválida para a instalação do Hidrômetro: FACTIVEL \n";
			        		}else if(imovelAtlzCadastral.getLigAguaSituacao() != null && imovelAtlzCadastral.getLigAguaSituacao().getId() == LigacaoAguaSituacao.SUPRIMIDO){
			        			mensagemErro += "Situação da Ligação da Água inválida para a instalação do Hidrômetro: SUPRIMIDO \n";
			        		}
		    			}
		    		}
		    		
					
		    	} catch (FachadaException e) {
					e.printStackTrace();
				}
			}
               	    	
	    	return mensagemErro;
	    }
	    
	    /**
	     * Metodo responsavel por validar o Numero do Hidrometro
	     * 
	     * @author Anderson Cabral
	     * @date 03/01/2013
	     *
	     * @param imovelAtlzCadastral
	     * @param hidrometroInstHistAtlzCad
	     * @return Mensagem de Erro
	     * @throws ControladorException
	     */
	    private String validarNumeroHidrometro(ImovelAtlzCadastral imovelAtlzCadastral, HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad) throws ControladorException {
	    	String mensagemErro = "";
	    	try {
		    	//Validacao do Numero Hidrometro	
		    	String primeiroCaracter = hidrometroInstHistAtlzCad.getNumeroHidrometro().substring(0, 1);
		    	
		    	HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();
		    	String selectionHidroCapacidade = HidrometroCapacidadeColunas.CODIGO + "=?";
	           
		    	String[] selectionArgsHidroCapacidade = new String[] {
	              	String.valueOf(primeiroCaracter)
		    	};
		   
				hidrometroCapacidade = (HidrometroCapacidade) fachada.pesquisar(hidrometroCapacidade, selectionHidroCapacidade, selectionArgsHidroCapacidade);
				
				if(hidrometroCapacidade == null || hidrometroCapacidade.getId() == null){
					mensagemErro = "Número do Hidrômetro inconsistente \n";
				}else{
						if ( hidrometroInstHistAtlzCad.getNumeroHidrometro().length() > 4 ) {
				    	String quartoCaracter = hidrometroInstHistAtlzCad.getNumeroHidrometro().substring(3, 4);
				    	
				    	HidrometroMarca hidrometroMarca = new HidrometroMarca();
				    	String selectionHidroMarca = HidrometroMarcaColunas.CODIGO + "=?";
			           
				    	String[] selectionArgsHidroMarca = new String[] {
			              	String.valueOf(quartoCaracter)
				    	};
				   
				    	hidrometroMarca = (HidrometroMarca) fachada.pesquisar(hidrometroMarca, selectionHidroMarca, selectionArgsHidroMarca);
				    	
						if(hidrometroMarca == null || hidrometroMarca.getId() == null){
							mensagemErro = "Número do Hidrômetro inconsistente \n";
						}
					} else {
						mensagemErro = "Número do Hidrômetro inconsistente \n";
					}
				}
				
			} catch (FachadaException e) {
				Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
			}
	    	
	    	return mensagemErro;
	    }
	    
	    /**
		 * Metodo responsavel por validar os campos da aba imóvel
		 * 
		 * @author Davi Menezes
		 * @date 07/01/2012
		 * 
		 * @param imovelAtlzCadastral
		 * @return Mensagem de Erro
		 * @throws ControladorException
		 */
		public String validarAbaImovel(ImovelAtlzCadastral imovelAtlzCadastral) throws ControladorException {
			String mensagemErro = "";
			fonteAbastecimentoAlterada = false;
			
			if(imovelAtlzCadastral != null){
				if(imovelAtlzCadastral.getImovelPerfil() == null || imovelAtlzCadastral.getImovelPerfil().getId().equals(ConstantesSistema.ITEM_INVALIDO) || imovelAtlzCadastral.getImovelPerfil().getId().equals(0)){
					mensagemErro += "Informe Perfil do Imóvel \n";
				}
				
				if(imovelAtlzCadastral.getPavimentoRua() == null || imovelAtlzCadastral.getPavimentoRua().getId().equals(ConstantesSistema.ITEM_INVALIDO) || imovelAtlzCadastral.getPavimentoRua().getId().equals(0)){
					mensagemErro += "Informe Pavimento Rua \n";
				}
				
				if(imovelAtlzCadastral.getPavimentoCalcada() == null || imovelAtlzCadastral.getPavimentoCalcada().getId().equals(ConstantesSistema.ITEM_INVALIDO) || imovelAtlzCadastral.getPavimentoCalcada().getId().equals(0)){
					mensagemErro += "Informe Pavimento Calçada \n";
				}
				
				if(imovelAtlzCadastral.getFonteAbastecimento() == null || imovelAtlzCadastral.getFonteAbastecimento().getId().equals(ConstantesSistema.ITEM_INVALIDO) || imovelAtlzCadastral.getFonteAbastecimento().getId().equals(0)){
					mensagemErro += "Informe Fonte de Abastecimento \n";
				}
				
				if(TabsActivity.colImoveisSubCategoria == null || TabsActivity.colImoveisSubCategoria.size() == 0){
					mensagemErro += "Informe no mínimo uma Categoria \n";
				}
				
			}
			
			if(TabsActivity.imovelInicial == null 
					|| ((TabsActivity.imovelInicial.getFonteAbastecimento() == null || TabsActivity.imovelInicial.getFonteAbastecimento().getId().equals(0)) && (imovelAtlzCadastral.getFonteAbastecimento() != null && !imovelAtlzCadastral.getFonteAbastecimento().getId().equals(0)))
					|| ((TabsActivity.imovelInicial.getFonteAbastecimento() != null && !TabsActivity.imovelInicial.getFonteAbastecimento().getId().equals(0)) && (imovelAtlzCadastral.getFonteAbastecimento() == null || imovelAtlzCadastral.getFonteAbastecimento().getId().equals(0)))
					|| (imovelAtlzCadastral.getFonteAbastecimento() != null && TabsActivity.imovelInicial.getFonteAbastecimento() != null &&
							!TabsActivity.imovelInicial.getFonteAbastecimento().getId().equals(imovelAtlzCadastral.getFonteAbastecimento().getId()))){
			
				fonteAbastecimentoAlterada = true;
			}
			
			return mensagemErro;
		}

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
	    public ImovelAtlzCadastral buscarImovelPosicao(Integer posicao) throws ControladorException {
	        ImovelAtlzCadastral imovelAtlzCadastral = null;
	    	try {
	        	
	        	imovelAtlzCadastral = RepositorioImovelAtlzCadastral.getInstance().buscarImovelPosicao(posicao);
	        	
	        } catch (RepositorioException e) {
	            e.printStackTrace();
	        }
	    	
	    	return imovelAtlzCadastral;
	    }
	    
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
	    public String validarAbaFotos(Integer idImovel)throws ControladorException {
	    	String mensagemErro = "";
	    	
	    	if(TabsActivity.colecaoImovelOcorrencia == null || TabsActivity.colecaoImovelOcorrencia.isEmpty()){
	    		mensagemErro += "Informe no mínimo uma Ocorrência \n";
	    	}
	    	
	    	return mensagemErro;
	    }
	    

		/****
		 * Retorna o maior id da tabela Imovel
		 * 
		 *@author Anderson Cabral
		 *@since 11/01/2013
		 ****/
		public Integer pesquisarMaiorIdImovel() throws ControladorException{
	        Integer id = null;
	    	try {
	        	
	        	id = RepositorioImovelAtlzCadastral.getInstance().pesquisarMaiorIdImovel();
	        	
	        } catch (RepositorioException e) {
	            e.printStackTrace();
	        }
	    	
	    	return id;
	    }
		
		
		/**
	     * 
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    public String gerarArquivoRetornoLogradouroBairro(Logradouro logradouro, SistemaParametros sistemaParametros) throws ControladorException {

	        StringBuilder sb = new StringBuilder("");


            //Registro tipo 02 - LOGRADOURO |  03 - LOGRADOURO BAIRRO
            sb.append(gerarRegistro02(logradouro, sistemaParametros));
            
            
	        return sb.toString();
	    }
	    
	    
	    
	    /**
	     * 
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    public String gerarArquivoRetornoLogradouroCep(LogradouroCep logradouroCep) throws ControladorException {

	        StringBuilder sb = new StringBuilder("");

	        
            //Registro tipo 04 - LOGRADOURO CEP
            sb.append(gerarRegistro04(logradouroCep));
            
            
	        return sb.toString();
	    }
	    
	    /**
	     * 
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    public String gerarArquivoRetornoCep(Cep cep) throws ControladorException {

	        StringBuilder sb = new StringBuilder("");

	        //Registro tipo 01 - CEP
            sb.append(gerarRegistro01(cep));

	        return sb.toString();
	    }
		
		/**
	     * 
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    public String gerarArquivoRetornoImovel(ImovelAtlzCadastral imovelAtlzCadastral) throws ControladorException {

	        StringBuilder sb = new StringBuilder("");

            //Registro tipo 05 - IMOVEL ATLZ CADASTRAL | 06 Cliente Atlz Cadastral | 07 Cliente Fone Atlz Cadastral
            // 08 Hidrometro Atlz Cadastral | 09 Subcategoria Atlz Cadastral | 10 Imovel ocorrencia
            sb.append(gerarRegistro05(imovelAtlzCadastral));
            
	        return sb.toString();
	    }
	   
	    /**
	     * 
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    private String gerarRegistro01(Cep cep) throws ControladorException {
	        StringBuilder sb = new StringBuilder("");

			 sb.append(Util.stringPipe(REGISTER_TYPE_1));
			 sb.append(Util.stringPipe(cep.getCodigoUnico()));
			 sb.append(Util.stringPipe(cep.getCodigo()));
			 sb.append("\n");

	        return sb.toString();
	    }
	    
	    /**
	     * Registro tipo 02 - logradouro
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    private String gerarRegistro02(Logradouro logradouro, SistemaParametros sistemaParametros) throws ControladorException {
	        StringBuilder sb = new StringBuilder("");

			 sb.append(Util.stringPipe(REGISTER_TYPE_2));
			 sb.append(Util.stringPipe(logradouro.getCodigoUnico()));
			 sb.append(Util.stringPipe(logradouro.getNomeLogradouro()));
			 sb.append(Util.stringPipe(logradouro.getNomePopularLogradouro()));
			 sb.append(Util.stringPipe(logradouro.getNomeLoteamento()));
			 if ( logradouro.getMunicipio() != null ) {
				 sb.append(Util.stringPipe(logradouro.getMunicipio().getId()));
			 }else{
				 sb.append(Util.stringPipe(null));
			 }
			 
			 if ( logradouro.getLogradouroTipo() != null ) {
				 sb.append(Util.stringPipe(logradouro.getLogradouroTipo().getId()));
			 }else{
				 sb.append(Util.stringPipe(null));
			 }
			 
			 if ( logradouro.getLogradouroTitulo() != null ) {
				 sb.append(Util.stringPipe(logradouro.getLogradouroTitulo().getId()));
			 }else{
				 sb.append(Util.stringPipe(null));
			 }
			 
			 sb.append(Util.stringPipe(sistemaParametros.getIdLocalidade()));
			 
			 sb.append(Util.stringPipe(sistemaParametros.getIdEmpresa()));
			 
			 
			 sb.append("\n");
	        			 
	        
	        //Pesquisa os logradouros bairros
	        sb.append(gerarRegistro03(logradouro.getId(), logradouro.getCodigoUnico()));
	        

	        return sb.toString();
	    }
	    
	    /**
	     * Registro tipo 03 - logradouro Bairro
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    private String gerarRegistro03(Integer idLogradouro, String codigoUnico) throws ControladorException {
	        StringBuilder sb = new StringBuilder("");

	        //Pesquisa Logradouro bairro
	        String selection = LogradouroBairros.LOGRADOURO + "=?";
	        
	        String[] selectionArgs = new String[] {
	        		idLogradouro.toString()
	        };
	        
	        try {
	        	
	        	 List<? extends EntidadeBase> listaLogradouroBairro = fachada.pesquisarLista(LogradouroBairro.class, selection, selectionArgs, null);
				
	        	 if ( listaLogradouroBairro != null ) {
	        		 
	        		 Iterator<? extends EntidadeBase> iteratorLogradouroBairro = listaLogradouroBairro.iterator();
	        		 while( iteratorLogradouroBairro.hasNext() ) {

	        			 LogradouroBairro logradouroBairro = (LogradouroBairro) iteratorLogradouroBairro.next();
	        			 sb.append(Util.stringPipe(REGISTER_TYPE_3));
	        			 sb.append(Util.stringPipe(codigoUnico));
	        			 sb.append(Util.stringPipe(logradouroBairro.getBairro().getId()));
	        			 sb.append("\n");
	        		 }
	        	 }
			} catch (FachadaException e) {
				e.printStackTrace();
			}

	        return sb.toString() ;
	    }
	    
	    
	    /**
	     * Registro tipo 04 - logradouro cep
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    private String gerarRegistro04(LogradouroCep logradouroCep) throws ControladorException {
	    	StringBuilder sb = new StringBuilder("");

	        try {
	        	 Cep cep = new Cep();
	        	 
				 String selection = ""; 
				 String[] selectionArgs = null;
				 if (selection == null || selection.trim().equals("")) {
					 selection = Ceps.ID + "=?";
				 }
				
				 if (selectionArgs == null) {
					 selectionArgs = new String[] {
						String.valueOf(logradouroCep.getCep().getId())
					 };
				 }
				
				 cep = (Cep) fachada.pesquisar(cep, selection, selectionArgs);
				
				 Logradouro logradouro = new Logradouro();
				 
				 String selectionLogradouro = ""; 
				 String[] selectionArgsLogradouro = null;
				 if (selectionLogradouro == null || selectionLogradouro.trim().equals("")) {
					 selectionLogradouro = Logradouros.ID + "=?";
				 }
				
				 if (selectionArgsLogradouro == null) {
					 selectionArgsLogradouro = new String[] {
				        String.valueOf(logradouroCep.getLogradouro().getId())
					 };
				 }
				
				 logradouro = (Logradouro) fachada.pesquisar(logradouro, selectionLogradouro, selectionArgsLogradouro);
				
				 sb.append(Util.stringPipe(REGISTER_TYPE_4));
				 sb.append(Util.stringPipe(cep.getCodigoUnico()));
				 sb.append(Util.stringPipe(logradouro.getCodigoUnico()));
				 sb.append("\n");
				 
		    } catch (FachadaException e) {
				e.printStackTrace();
			}

	        return sb.toString();
	    }

	    
	    /**
	     * Registro tipo 05 - Imovel Atlz Cadastral
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    private String gerarRegistro05(ImovelAtlzCadastral imovelAtlzCadastral) throws ControladorException {
	         
	    	StringBuilder sb = new StringBuilder("");
	       
	        SistemaParametros  sistemaParametros = new SistemaParametros();
	        
 	        try {
				sistemaParametros = (SistemaParametros) fachada.pesquisar(sistemaParametros, null, null);
	        } catch (FachadaException e) {
				e.printStackTrace();
			}

			sb.append(Util.stringPipe(REGISTER_TYPE_5));
			sb.append(Util.stringPipe(imovelAtlzCadastral.getId()));//1
			sb.append(Util.stringPipe(imovelAtlzCadastral.getImovelId()));//2
			sb.append(Util.stringPipe(imovelAtlzCadastral.getMunicipioId()));//3
			sb.append(Util.stringPipe(imovelAtlzCadastral.getLocalidadeId()));//4
			sb.append(Util.stringPipe(imovelAtlzCadastral.getCodigoSetorComercial()));//5
			sb.append(Util.stringPipe(imovelAtlzCadastral.getNumeroQuadra()));//6
			sb.append(Util.stringPipe(imovelAtlzCadastral.getNumeroLote()));//7
			sb.append(Util.stringPipe(imovelAtlzCadastral.getNumeroSubLote()));//8
			
			//enviar o codigo unico do logradouro
			if ( imovelAtlzCadastral.getCodigoUnicoLogradouro() != null && !imovelAtlzCadastral.getCodigoUnicoLogradouro().equals("") ) {
				sb.append(Util.stringPipe(imovelAtlzCadastral.getCodigoUnicoLogradouro()));//9
			} else {
				sb.append(Util.stringPipe(imovelAtlzCadastral.getLogradouro().getId()));//9
			}
			
			sb.append(Util.stringPipe(imovelAtlzCadastral.getEnderecoReferencia().getId()));//10
			sb.append(Util.stringPipe(imovelAtlzCadastral.getNumeroImovel()));//11
			sb.append(Util.stringPipe(imovelAtlzCadastral.getComplementoEndereco()));//12
			sb.append(Util.stringPipe(imovelAtlzCadastral.getLogradouroBairroId()));//13
			sb.append(Util.stringPipe(imovelAtlzCadastral.getLogradouroCEPId()));//14
			sb.append(Util.stringPipe(imovelAtlzCadastral.getImovelPerfil().getId()));//15
			sb.append(Util.stringPipe(imovelAtlzCadastral.getNumeroMedidorEnergia()));//16
			sb.append(Util.stringPipe(imovelAtlzCadastral.getNumeroMorador()));//17
			sb.append(Util.stringPipe(imovelAtlzCadastral.getPavimentoRua().getId()));//18
			sb.append(Util.stringPipe(imovelAtlzCadastral.getPavimentoCalcada().getId()));//19
			sb.append(Util.stringPipe(imovelAtlzCadastral.getFonteAbastecimento().getId()));//20
			sb.append(Util.stringPipe(imovelAtlzCadastral.getLigAguaSituacao().getId()));//21
			sb.append(Util.stringPipe(imovelAtlzCadastral.getLigEsgotoSituacao().getId()));//22
			sb.append(Util.stringPipe(imovelAtlzCadastral.getIndicadorTarifaSocial()));//23
			sb.append(Util.stringPipe(Util.convertDateToStringComHora(imovelAtlzCadastral.getDataVisita())));//24
			sb.append(Util.stringPipe(imovelAtlzCadastral.getIntegracaoID()));//25
			sb.append(Util.stringPipe(sistemaParametros.getIdComando()));//26
			sb.append(Util.stringPipe(imovelAtlzCadastral.getIdBairro()));//27
			sb.append(Util.stringPipe(imovelAtlzCadastral.getCodigoCep()));//28
			sb.append(Util.stringPipe(imovelAtlzCadastral.getObservacao()));//29
			sb.append(Util.stringPipe(sistemaParametros.getIdEmpresa()));//30
			
        	if ( imovelAtlzCadastral.getLogin() != null && !imovelAtlzCadastral.getLogin().equals("")  ) {
	        	
        		sb.append(Util.stringPipe(imovelAtlzCadastral.getLogin()));//31
        	} else {
        		sb.append(Util.stringPipe(sistemaParametros.getLogin()));//31
        	}
        	
        	sb.append(Util.stringPipe(imovelAtlzCadastral.getNomeMunicipio()));//32
			
			if(imovelAtlzCadastral.getIndicadorImovelNovoComMatricula() != null 
					&& imovelAtlzCadastral.getIndicadorImovelNovoComMatricula().intValue() == ConstantesSistema.SIM){
				sb.append(Util.stringPipe(ConstantesSistema.SIM)); //33
			}else{
				sb.append(Util.stringPipe(ConstantesSistema.NAO)); //33
			}
			
			sb.append("\n");	

	        
	        return sb.toString();
	    }
	    
	    
	    
	    /**
	     * 
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    public String gerarArquivoRetornoOcorrencia( List<ImovelOcorrencia> listaImovelOcorrencia, String codigoImovelAtlzCadastral) throws ControladorException {

	        StringBuilder sb = new StringBuilder("");

	      //Registro do tipo 10 - Imovel ocorrencia
	        sb.append(gerarRegistro10(listaImovelOcorrencia, codigoImovelAtlzCadastral));
	        
	        return sb.toString();
	    }
	    
	    /**
	     * 
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    public String gerarArquivoRetornoSubcategoria( List<ImovelSubCategAtlzCad> listaImovelSubCategAtlzCad, String codigoImovelAtlzCadastral) throws ControladorException {

	        StringBuilder sb = new StringBuilder("");

	        //Registro do tipo 09 - Subcategoria Atlz Cadastral
	        sb.append(gerarRegistro09(listaImovelSubCategAtlzCad, codigoImovelAtlzCadastral));
	        
	        return sb.toString();
	    }
	    
	    
	    
	    /**
	     * 
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    public String gerarArquivoRetornoHidrometro( List<HidrometroInstHistAtlzCad> listaHidrometroInstHistAtlzCad, String codigoImovelAtlzCadastral, Integer idImovel) throws ControladorException {

	        StringBuilder sb = new StringBuilder("");

	        //Registro do tipo 08 - Hidrometro Atlz Cadastral
	        sb.append(gerarRegistro08(listaHidrometroInstHistAtlzCad, codigoImovelAtlzCadastral, idImovel));
	        
	        return sb.toString();
	    }
	    
	    
	    /**
	     * 
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    public String gerarArquivoRetornoCliente(ClienteAtlzCadastral clienteAtlzCadastral, List<ClienteFoneAtlzCad> listaClienteFoneAtlzCadastral, 
	    		String codigoImovelAtlzCadastral) throws ControladorException {

	        StringBuilder sb = new StringBuilder("");

	        //Registro do tipo 06 - Cliente Atz Cadastral | 07 Cliente Fone Atlz Cadastral
	        sb.append(gerarRegistro06(clienteAtlzCadastral, listaClienteFoneAtlzCadastral, codigoImovelAtlzCadastral));
            
	        return sb.toString();
	    }
	    
	    
	    /**
	     * Registro tipo 06 - Cliente Atlz Cadastral
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    private String gerarRegistro06(ClienteAtlzCadastral clienteAtlzCadastral, List<ClienteFoneAtlzCad> listaClienteFoneAtlzCadastral, String codigoImovelAtlzCadastral)
	    		throws ControladorException {
	    	
	        StringBuilder sb = new StringBuilder("");

	        if ( clienteAtlzCadastral != null && clienteAtlzCadastral.getId() != null ) {		 
    			 
	        	 sb.append(Util.stringPipe(REGISTER_TYPE_6));
    			 
    			 sb.append(Util.stringPipe(codigoImovelAtlzCadastral));
    			 sb.append(Util.stringPipe(clienteAtlzCadastral.getClienteTipo().getId()));
    			 sb.append(Util.stringPipe(clienteAtlzCadastral.getNumeroCPFCNPPJ()));
    			 sb.append(Util.stringPipe(clienteAtlzCadastral.getNomeCliente()));
    			 sb.append(Util.stringPipe(clienteAtlzCadastral.getNumeroRG()));
    			 
    			 String idOrgaoExpedidor = null;
    			 if ( clienteAtlzCadastral.getOrgaoExpedidorRg() != null && clienteAtlzCadastral.getOrgaoExpedidorRg().getId() != null
    					 && !clienteAtlzCadastral.getOrgaoExpedidorRg().getId().toString().equals("0") ) {
    				 idOrgaoExpedidor = new String("");
    				 idOrgaoExpedidor = clienteAtlzCadastral.getOrgaoExpedidorRg().getId().toString();
    			 }
    			 sb.append(Util.stringPipe(idOrgaoExpedidor));
    			 
    			 String idUnidadeFederacao = null;
    			 if ( clienteAtlzCadastral.getUnidadeFederacao() != null && clienteAtlzCadastral.getUnidadeFederacao().getId() != null
    					 && !clienteAtlzCadastral.getUnidadeFederacao().getId().toString().equals("0") ) {
    				 idUnidadeFederacao = new String("");
    				 idUnidadeFederacao = clienteAtlzCadastral.getUnidadeFederacao().getId().toString();
    			 }
    			 sb.append(Util.stringPipe(idUnidadeFederacao));
    			 
    			 String dataRG = null;
    			 if ( clienteAtlzCadastral.getDataEmissaoRg() != null && !clienteAtlzCadastral.getDataEmissaoRg().equals("") ) {
    				 dataRG = new String("");
    				 dataRG = Util.convertDateToString(clienteAtlzCadastral.getDataEmissaoRg());
    			 }
    			 sb.append(Util.stringPipe(dataRG));

    			 String pessoaSexo = null;
    			 if ( clienteAtlzCadastral.getPessoaSexo() != null && clienteAtlzCadastral.getPessoaSexo().getId() != null 
    					 && !clienteAtlzCadastral.getPessoaSexo().getId().toString().equals("0") ) {
    				 pessoaSexo = new String("");
    				 pessoaSexo = clienteAtlzCadastral.getPessoaSexo().getId().toString();
    			 }
    			 sb.append(Util.stringPipe(pessoaSexo));
    			
    			 String dataNascimento = null;
    			 if ( clienteAtlzCadastral.getDataNascimento() != null && !clienteAtlzCadastral.getDataNascimento().equals("") ) {
    				 dataNascimento = new String("");
    				 dataNascimento = Util.convertDateToString(clienteAtlzCadastral.getDataNascimento());
    			 }
				 sb.append(Util.stringPipe(dataNascimento));
    			 
				 sb.append(Util.stringPipe(clienteAtlzCadastral.getIdCliente()));
				 sb.append(Util.stringPipe(clienteAtlzCadastral.getId()));
    			 sb.append("\n");
    			 
    			 sb.append(gerarRegistro07(listaClienteFoneAtlzCadastral, codigoImovelAtlzCadastral, clienteAtlzCadastral.getId()));
        	}
	        	
        	
	        
	        return sb.toString();
	    }
	    
	    
	    /**
	     * Registro tipo 07 - Cliente Fone Atlz Cadastral
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    private String gerarRegistro07(List<ClienteFoneAtlzCad> listaClienteFoneAtlzCadastral , String codigoImovelAtlzCadastral, Integer idClienteAtlzCadastral) throws ControladorException {
	    	
	        StringBuilder sb = new StringBuilder("");
	       
	        	if ( listaClienteFoneAtlzCadastral != null && !listaClienteFoneAtlzCadastral.isEmpty() ) {
	        		 
	        		 Iterator<? extends EntidadeBase> iteratorClienteFoneAtlzCadastral = listaClienteFoneAtlzCadastral.iterator();
	        		 while( iteratorClienteFoneAtlzCadastral.hasNext() ) {

	        			 ClienteFoneAtlzCad clienteFoneAtlzCadastral = (ClienteFoneAtlzCad) iteratorClienteFoneAtlzCadastral.next();
	        			 sb.append(Util.stringPipe(REGISTER_TYPE_7));
	        			 sb.append(Util.stringPipe(codigoImovelAtlzCadastral));
	        			 sb.append(Util.stringPipe(clienteFoneAtlzCadastral.getFoneTipo().getId()));
	        			 sb.append(Util.stringPipe(clienteFoneAtlzCadastral.getCodigoDDD()));
	        			 sb.append(Util.stringPipe(clienteFoneAtlzCadastral.getNumeroFone()));
	        			 sb.append(Util.stringPipe(clienteFoneAtlzCadastral.getIndicadorFonePadrao()));
	        			 
	        			 //Id do cliente atualizacao cadastral para atualizar os dados do tablet "pai".
	        			 sb.append(Util.stringPipe(idClienteAtlzCadastral));
	        			 sb.append(Util.stringPipe(clienteFoneAtlzCadastral.getClienteId()));
	        			 
	        			 sb.append("\n");
	        		 }
	        	 }

	        return sb.toString();
	    }
	    
	    /**
	     * Registro tipo 08 - Hidrometro Atlz Cadastral
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    private String gerarRegistro08(List<HidrometroInstHistAtlzCad> listaHidrometroInstHistAtlzCad, String codigoImovelAtlzCadastral, Integer idImovel) throws ControladorException {
	    	
	        StringBuilder sb = new StringBuilder("");
	        
		        
        	if ( listaHidrometroInstHistAtlzCad != null ) {
        		 
        		 Iterator<? extends EntidadeBase> iteratorHidrometroInstHistAtlzCad = listaHidrometroInstHistAtlzCad.iterator();
        		 while( iteratorHidrometroInstHistAtlzCad.hasNext() ) {

        			 HidrometroInstHistAtlzCad hidrometroInstHistAtlzCad = (HidrometroInstHistAtlzCad) iteratorHidrometroInstHistAtlzCad.next();
        			 sb.append(Util.stringPipe(REGISTER_TYPE_8));
        			 sb.append(Util.stringPipe(codigoImovelAtlzCadastral));
        			 sb.append(Util.stringPipe(hidrometroInstHistAtlzCad.getNumeroHidrometro()));
        			 
        			 String hidrometroLocalInst = null;
        			 if ( hidrometroInstHistAtlzCad.getHidrometroLocalInst() != null && hidrometroInstHistAtlzCad.getHidrometroLocalInst().getId() != null ) {
        				 hidrometroLocalInst = new String("");
        				 hidrometroLocalInst = String.valueOf(hidrometroInstHistAtlzCad.getHidrometroLocalInst().getId());
        			 }
        			 sb.append(Util.stringPipe(hidrometroLocalInst)); 
        			
        			 String hidrometroProtecao = null;
        			 if ( hidrometroInstHistAtlzCad.getHidrometroProtecao() != null && hidrometroInstHistAtlzCad.getHidrometroProtecao().getId() != null ) {
        				 hidrometroProtecao =  new String("");
        				 hidrometroProtecao = String.valueOf(hidrometroInstHistAtlzCad.getHidrometroProtecao().getId());
        			 }
        			 
        			 sb.append(Util.stringPipe(hidrometroProtecao));
        			 sb.append(Util.stringPipe(hidrometroInstHistAtlzCad.getIndicadorCavalete()));
        			 sb.append(Util.stringPipe(hidrometroInstHistAtlzCad.getNumeroInstHidrometro()));
        			 sb.append(Util.stringPipe(idImovel));
        			
        			 sb.append("\n");
        		 }
        	 }

	        
	        return sb.toString();
	    }
	    
	    
	    /**
	     * Registro tipo 09 - Subcategoria Atlz Cadastral
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    private String gerarRegistro09(List<ImovelSubCategAtlzCad> listaImovelSubCategAtlzCad, String codigoImovelAtlzCadastral) throws ControladorException {
	    	
	        StringBuilder sb = new StringBuilder("");

        	if ( listaImovelSubCategAtlzCad != null ) {
        		 
        		 Iterator<? extends EntidadeBase> iteratorImovelSubCategAtlzCad = listaImovelSubCategAtlzCad.iterator();
        		 while( iteratorImovelSubCategAtlzCad.hasNext() ) {

        			 ImovelSubCategAtlzCad imovelSubCategAtlzCad = (ImovelSubCategAtlzCad) iteratorImovelSubCategAtlzCad.next();
        			 sb.append(Util.stringPipe(REGISTER_TYPE_9));
        			 sb.append(Util.stringPipe(codigoImovelAtlzCadastral));
        			 sb.append(Util.stringPipe(imovelSubCategAtlzCad.getCategoria().getId()));
        			 sb.append(Util.stringPipe(imovelSubCategAtlzCad.getSubCategoria().getId()));
        			 sb.append(Util.stringPipe(imovelSubCategAtlzCad.getQuantidadeEconomia()));
        			
        			 sb.append("\n");
        		 }
        	 }

	        
	        return sb.toString();
	    }
	    
	    /**
	     * Registro tipo 10 - Imovel ocorrencia
	     * @author Arthur Carvalho
	     * @date 15/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    private String gerarRegistro10( List<ImovelOcorrencia> listaImovelOcorrencia, String codigoImovelAtualizacaoCadastral) throws ControladorException {
	    	
	        StringBuilder sb = new StringBuilder("");
	        
		       
        	if ( listaImovelOcorrencia != null ) {
        		 
        		 Iterator<? extends EntidadeBase> iteratorImovelOcorrencia = listaImovelOcorrencia.iterator();
        		 while( iteratorImovelOcorrencia.hasNext() ) {

        			 ImovelOcorrencia imovelOcorrencia = (ImovelOcorrencia) iteratorImovelOcorrencia.next();
        			 sb.append(Util.stringPipe(REGISTER_TYPE_10));
        			 sb.append(Util.stringPipe(imovelOcorrencia.getCadastroOcorrencia().getId()));
        			 sb.append(Util.stringPipe(codigoImovelAtualizacaoCadastral));
        			 sb.append("\n");
        		 }
        	 }
	        
	        return sb.toString();
	    }
	    
	    /**
	     * 
	     * @author Arthur Carvalho
	     * @date 18/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    public Roteiro pesquisarRoteiro(Integer idImovelAtlzCadastral) throws ControladorException {
	    	Roteiro roteiro = null;
	        try {
	        	roteiro = RepositorioImovelAtlzCadastral.getInstance().pesquisarRoteiro(idImovelAtlzCadastral);
	        	
	        } catch (RepositorioException e) {
	            e.printStackTrace();
	        }
	        return roteiro;
	    }
	    
	    /**
	     * Metodo responsavel por pesquisar todos os imóveis cadastrados no gsan.
	     * 
	     * @author Arthur Carvalho
	     * @return FileReturn
	     * @param idServiceOrder
	     * @throws RepositoryException
	     */
	    public ArrayList<Integer> pesquisarMatriculas() throws ControladorException {
	    	
	    	ArrayList<Integer> lista = null; 
	    	
	    	try {
	    		lista = RepositorioImovelAtlzCadastral.getInstance().pesquisarMatriculas();
		        	
	        } catch (RepositorioException e) {
	            e.printStackTrace();
	        }
	    	return lista;
	    }
	    
	    /**
	     * @author Arthur Carvalho
	     * @since 06/12/2012
	     */
	    @Override
	    public SistemaParametros validarLogin(String login, String password) throws ControladorException {
	        SistemaParametros sistemaParametros = null;
	        try {
	        	sistemaParametros = RepositorioSistemaParametros.getInstance().validarLogin(login, password);
	        	
	        } catch (RepositorioException e) {
	            e.printStackTrace();
	        }
	        return sistemaParametros;
	    }
	    
	    /**
	     * @author Arthur Carvalho
	     * @since 06/12/2012
	     */
	    public SistemaParametros validarLoginCpf(String login) throws ControladorException {
	        SistemaParametros sistemaParametros = null;
	        try {
	        	sistemaParametros = RepositorioSistemaParametros.getInstance().validarLoginCpf(login);
	        	
	        } catch (RepositorioException e) {
	            e.printStackTrace();
	        }
	        return sistemaParametros;
	    }
	    
		/****
		 * Retorna imovel atraves do numero do hidrometro
		 * 
		 *@author Anderson Cabral
		 *@since 17/07/2013
		 ****/
	    public List<ImovelAtlzCadastral> pesquisarImovelPeloHidrometro(String numeroHidrometro) throws ControladorException {
	    	
	    	List<ImovelAtlzCadastral> listaImovelAtlzCadastral = null; 
	    	
	    	try {
	    		listaImovelAtlzCadastral = RepositorioImovelAtlzCadastral.getInstance().pesquisarImovelPeloHidrometro(numeroHidrometro);
		        	
	        } catch (RepositorioException e) {
	            e.printStackTrace();
	        }
	    	return listaImovelAtlzCadastral;
	    }

		/****
		 * Retorna colecao de imoveis pelo cpf
		 * 
		 *@author Anderson Cabral
		 *@since 17/07/2013
		 ****/
		public List<ImovelAtlzCadastral> pesquisarImovelPeloCPFCNPJ(String numeroCpfCnpj) throws ControladorException{
	    	List<ImovelAtlzCadastral> listaImovelAtlzCadastral = null; 
	    	
	    	try {
	    		listaImovelAtlzCadastral = RepositorioImovelAtlzCadastral.getInstance().pesquisarImovelPeloCPFCNPJ(numeroCpfCnpj);
		        	
	        } catch (RepositorioException e) {
	            e.printStackTrace();
	        }
	    	return listaImovelAtlzCadastral;
		}

	   /**
	    * 
	    * @author Arthur Carvalho
	    * @date 26/06/2013
	    *
	    * @return
	    * @throws ControladorException
	    */
	    public Integer pesquisarSetorComercialPrincipal() throws ControladorException {
	    	
	    	Integer codigo = null; 
	    	
	    	try {
	    		codigo = RepositorioImovelAtlzCadastral.getInstance().pesquisarSetorComercialPrincipal();
		        	
	        } catch (RepositorioException e) {
	            e.printStackTrace();
	        }
	    	return codigo;
	    }

	    /**
	     * 
	     * @author Arthur Carvalho
	     * @date 18/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    public Date pesquisarArquivoDivididoCarregado(String nomeArquivo) throws ControladorException {
	    	Date dataCarregamento = null;
	        try {
	        	dataCarregamento = RepositorioImovelAtlzCadastral.getInstance().pesquisarArquivoDivididoCarregado(nomeArquivo);
	        	
	        } catch (RepositorioException e) {
	            e.printStackTrace();
	        }
	        return dataCarregamento;
	    }
	    
	    /**
	     * 
	     * @author Arthur Carvalho
	     * @date 18/01/2013
	     *
	     * @return
	     * @throws ControladorException
	     */
	    public void inserirArquivoDividido(String nomeArquivo) throws ControladorException {
	        try {
	        	 RepositorioImovelAtlzCadastral.getInstance().inserirArquivoDividido(nomeArquivo);
	        	
	        } catch (RepositorioException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    /**
	     * @author Flavio Ferreira
	     * @date 10/10/2013
	     * @param numeroOcorrencia
	     * @return
	     * @throws RepositorioException
	     */
	    public Integer obterQuantidadeImoveis() throws ControladorException{
	    	Integer quantidade = 0;
	    	try{
	    		quantidade = RepositorioImovelAtlzCadastral.getInstance().obterQuantidadeImoveis();
	    		
	    	}catch(RepositorioException ex){
	    		ex.printStackTrace();
	    	}
			return quantidade;
	    }
	    
	    /**
	     * @author Flavio Ferreira
	     * @date 10/10/2013
	     * @param numeroOcorrencia
	     * @return
	     * @throws RepositorioException
	     */
	    public Integer obterQuantidadeImoveisAtualizadosPorOcorrencia(Integer numeroOcorrencia) throws ControladorException{
	    	Integer quantidade = 0;
	    	try{
	    		quantidade = RepositorioImovelAtlzCadastral.getInstance().obterQuantidadeImoveisAtualizadosPorOcorrencia(numeroOcorrencia);
	    		
	    	}catch(RepositorioException ex){
	    		ex.printStackTrace();
	    	}
	    	
	    	return quantidade;
	    }
	    
	    
	    /**
	     * @author Flavio Ferreira
	     * @date 10/10/2013
	     * @param numeroOcorrencia
	     * @return
	     * @throws RepositorioException
	     */
	    public Integer obterQuantidadeImoveisIncluidosComPorOcorrencia(Integer numeroOcorrencia) throws ControladorException{
	    	Integer quantidade = 0;
	    	try{
	    		quantidade = RepositorioImovelAtlzCadastral.getInstance().obterQuantidadeImoveisIncluidosComPorOcorrencia(numeroOcorrencia);
	    		
	    	}catch(RepositorioException ex){
	    		ex.printStackTrace();;
	    	}
	    	
	    	return quantidade;
	    }
	    
	    /**
	     * @author Flavio Ferreira
	     * @date 10/10/2013
	     * @return
	     * @throws RepositorioException
	     */
	    public String buscarDescricaoOcorrencias(Integer idCadastroOcorrencia) throws ControladorException{
	    	
	    	String dsOcorreicias = null;
	    	try{
	    		
	    		dsOcorreicias =  RepositorioImovelAtlzCadastral.getInstance().buscarDescricaoOcorrencias(idCadastroOcorrencia);
	    		
	    	}catch(RepositorioException ex){
	    		ex.printStackTrace();
	    	}
			return dsOcorreicias;
	    }
	    
	    
	    /**
	     * @author Flavio Ferreira
	     * @date 14/10/2013
	     * @return
	     * @throws RepositorioException
	     */
	  
	    public Integer obterTotalImoveisAtualizados(String login) throws ControladorException{
	    	Integer quantidade = 0;
	    	
	    	try{
	    		quantidade = RepositorioImovelAtlzCadastral.getInstance().obterTotalImoveisAtualizados(login);
	    		
	    	}catch(RepositorioException ex){
	    		ex.printStackTrace();
	    	}
	    	
	    	return quantidade;
	    	
	    }
	    
	    /**
	     * @author Flavio Ferreira
	     * @date 14/10/2013
	     * @return
	     * @throws RepositorioException
	     */
	    public Integer obterTotalImoveisIncluidos(String login) throws ControladorException{
	    	Integer quantidade = 0;
	    	
	    	try{
	    		quantidade = RepositorioImovelAtlzCadastral.getInstance().obterTotalImoveisIncluidos(login);
	    		
	    	}catch(RepositorioException ex){
	    		ex.printStackTrace();
	    	}
	    	
	    	return quantidade;
	    	
	    }
	    
	    
	    /**
	     * @author Flavio Ferreira
	     * @date 15/10/2013
	     * @return
	     * @throws RepositorioException
	     */
	
		public List<String> pesquisarListaLogin() throws ControladorException{
			List<String> listaLogin = null;
			
			try{
				
				listaLogin = RepositorioImovelAtlzCadastral.getInstance().pesquisarListaLogin();
			}catch(RepositorioException ex){
				ex.printStackTrace();
			}
			
			return listaLogin; 
					
		}
	    
	    /**
	     * 
	     * @author Anderson Cabral
	     * @date 08/10/2013
	     * 
	     * @return
	     * @throws ControladorException
	     */
	    public boolean verificarOcorrenciaCadastroSelecionada(){
	    	boolean retorno = false;
	    	if(TabsActivity.colecaoImovelOcorrencia != null && !TabsActivity.colecaoImovelOcorrencia.isEmpty()){
	    		for(ImovelOcorrencia imovelOcorrencia : TabsActivity.colecaoImovelOcorrencia){
	    			CadastroOcorrencia cadastroOcorrencia = pesquisarCadastroOcorrencia(imovelOcorrencia.getCadastroOcorrencia().getId());
	    			
	    			if(cadastroOcorrencia.getIndicadorCampoObrigatorio().intValue() == ConstantesSistema.SIM){
	    				retorno = true;
	    				break;
	    			}
	    		}
	    	}else{
	    		retorno = true;
	    	}
	    	
	    	return retorno;
	    }
	    
	    public CadastroOcorrencia pesquisarCadastroOcorrencia(Integer id){
			
			//Pesquisar Cadastro Ocorrencia
            String 	selection = CadastroOcorrenciaColunas.ID + "=?";
            String[] selectionArgs = new String[] {
                String.valueOf(id)
            };
            
            CadastroOcorrencia cadOcorrencia = new CadastroOcorrencia();
            
            try {
				cadOcorrencia = (CadastroOcorrencia) fachada.pesquisar(cadOcorrencia, selection, selectionArgs);
			} catch (FachadaException fe) {
				Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
			}
            
            return cadOcorrencia;
	    }
	    
}
