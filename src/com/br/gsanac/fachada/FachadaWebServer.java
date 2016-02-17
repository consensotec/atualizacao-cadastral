/*
 * Copyright (C) 2007-2009 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */
/*
 * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Clêudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flêvio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Roberto Souza
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 * Fernanda Almeida
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implêcita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330com.br.gsanac.conexao1307, USA.
 */
package com.br.gsanac.fachada;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Vector;

import android.content.Context;

import com.br.gsanac.conexao.ConexaoWebServer;

/**
 * A classe FachadaWebServer agrupa serviços que precisam da rede. Ela conecta quando
 * necessário, envia os dados para o servidor, recebe a resposta do servidor e
 * repassa para o listener.
 * 
 * @author Rafael Palermo de Araújo
 */
public class FachadaWebServer {

	public static boolean indcConfirmacaRecebimento = false;

	/**
	 * Identificador da requisição Cliente->Servidor de confirmar recebimento do
	 * roteiro.
	 */
	public static final byte CS_CONFIRMAR_RECEBIMENTO = 3;
	
	private boolean requestOK = false;

	private ConexaoWebServer conexaoWebServer;
	
	/**
	 * Com base no padrão de Projeto chamado Singleton, FachadaWebServer tem apenas
	 * uma única instância em todo o contexto da aplicação.
	 */
	private static FachadaWebServer instance;


	/**
	 * Retorna a instância da fachada de rede.
	 * 
	 * @return A instância da fachada de rede.
	 */
	public static FachadaWebServer getInstancia() {
		if (FachadaWebServer.instance == null) {
			FachadaWebServer.instance = new FachadaWebServer();
		}
		return FachadaWebServer.instance;
	}

	/**
	 * Repassa as requisições ao servidor.
	 * 
	 * @param parametros
	 *            Vetor de parâmetros da operação.
	 * @param recebeResposta
	 *            Boolean que diz se recebe ou não um InputStream do servidor
	 */
//	public boolean iniciarServicoRede(ArrayList<Object> parametros, boolean enviarIMEI,Context context) {
//		return ConexaoWebServer.getInstancia().iniciarServicoRede(parametros, enviarIMEI, context);		
//	}

//	/**
//	 * Envia o arquivo gerado do imovel para o servidor
//	 * 
//	 * @param Array
//	 *            de bytes com o arquivo
//	 * @return 
//	 */
//	public boolean enviarImovel(byte[] imovel,Context context) throws IOException {
//		return ConexaoWebServer.getInstancia().enviarImovel(imovel, context);
//	}
//
//	/**
//	 * Envia o arquivo gerado do imovel para o servidor
//	 * 
//	 * @param Array
//	 *            de bytes com o arquivo
//	 */
//	public void finalizarLeitura(byte[] arquivoRetorno,Context context) throws IOException {
//		requestOK = ConexaoWebServer.getInstancia().finalizarLeitura(arquivoRetorno, context);
//	}

	public boolean isRequestOK() {
		return requestOK;
	}

	public void iniciarConexaoWebServer(Context ctx){
		conexaoWebServer = new ConexaoWebServer(ctx);
	}
	
	public InputStream comunicar( String url, Vector<Object> parametros ) throws IOException, MalformedURLException{
		return this.conexaoWebServer.comunicar(url, parametros);
	}
	
	public int getFileLength() {
		return this.conexaoWebServer.getFileLength();
	}
	
	public boolean serverOnline(){
		return this.conexaoWebServer.serverOnline();
	}
	
//	/**
//	 * 
//	 * Manda um sinal para GSAN informando que
//	 * a rota foi inicializada com sucessoo.
//	 * 
//	 */	
//	public boolean routeInitializationSignal(){
//		return this.conexaoWebServer.routeInitializationSignal();
//	}
	
	// Só podemos pegar a mensagem de error uma vez
//	public static String getMensagemError() {
//		return ConexaoWebServer.getMensagemError();
//	}
	
//	public void setContextComunicacaoWebServer(Context ctx){
//		ComunicacaoWebServer.getInstancia().setContext(ctx);
//	}
	
//	 public Object[] comunicacao(short tipoFinalizacao, ArquivoRetorno arquivoRetorno, int posicao, boolean continua){	
//		 return ComunicacaoWebServer.getInstancia()
//				 .comunicacao(tipoFinalizacao, arquivoRetorno, posicao, continua);
//	 }
	 
//	 public int enviarDados(String nomeArquivo, short tipoFinalizacao,Context contexto, StringBuilder montaArquivo) {
//		 return ComunicacaoWebServer.getInstancia()
//				 .enviarDados(nomeArquivo, tipoFinalizacao, contexto, montaArquivo);
//	 }
}