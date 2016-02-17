/*
* Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
* Cláudio de Andrade Lira
* Denys Guimarães Guenes Tavares
* Eduardo Breckenfeld da Rosa Borges
* Fabíola Gomes de Araújo
* Fernanda Vieira de Barros Almeida
* Flávio Leonardo Cavalcanti Cordeiro
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
* Rodrigo Avellar Silveira
* Rosana Carvalho Barbosa
* Sávio Luiz de Andrade Cavalcante
* Tai Mu Shih
* Thiago Augusto Souza do Nascimento
* Thúlio dos Santos Lins de Araújo
* Tiago Moreno Rodrigues
* Vivianne Barbosa Sousa
*
* Este programa é software livre; você pode redistribuí-lo e/ou
* modificá-lo sob os termos de Licença Pública Geral GNU, conforme
* publicada pela Free Software Foundation; versão 2 da
* Licença.
* Este programa é distribuído na expectativa de ser útil, mas SEM
* QUALQUER GARANTIA; sem mesmo a garantia implícita de
* COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
* PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
* detalhes.
* Você deve ter recebido uma cópia da Licença Pública Geral GNU
* junto com este programa; se não, escreva para Free Software
* Foundation, Inc., 59 Temple Place, Suitecom.br.gsanac.conexao1-1307, USA.
*/  

package com.br.gsanac.conexao;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

import com.br.gsanac.entidades.Cep;
import com.br.gsanac.entidades.ClienteAtlzCadastral;
import com.br.gsanac.entidades.ClienteFoneAtlzCad;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelOcorrencia;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.LogradouroCep;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

/**
 * Classe responsa�vel pela conexao do android
 * com o GSAN. Todas as requisições devem passar
 * por aqui
 * 
 * Essa classe deve sempre ser utilizada
 * a partir de uma thread, para que nao
 * utilize a thread principal;
 * 
 * @author Fernanda Almeida
 * @date 01/04/2012
 *
 */
public class ConexaoWebServer {

	public static ConexaoWebServer instancia;
	private InputStream respostaOnline;
	
	private char tipoArquivo;
	private static String mensagemError = null;	
		
		
	// Contexto, para que, ao finalizarmos qualquer operação, possamos
	// mudar de tela
	@SuppressWarnings("unused")
	private Context context = null;
	private HttpURLConnection conn = null;

	StringBuffer buffer = new StringBuffer();
	StringBuffer bufferValorParametro = new StringBuffer();
	
	private StringBuilder       fileContent     = new StringBuilder("");

    private static final String REGISTER_TYPE_0 = "00";

	
	private int fileLength;
			

	private static String respostaServidor = ConstantesSistema.RESPOSTA_OK;
	
	public static ConexaoWebServer getInstancia() {
		if (instancia == null) {
			instancia = new ConexaoWebServer();
		}

		return instancia;
	}

	public static String getRespostaServidor() {
		return respostaServidor;
	}

		
	public void setContext(Context context){
		this.context=context;
	}
	/**
	 * Contrutor padrão.
	 * 
	 * @param context
	 */
	public ConexaoWebServer( Context context ){
		super();
		this.context = context;
	}
	
	public ConexaoWebServer() {
		// TODO Auto-generated constructor stub
	}

	
	public InputStream comunicar( String url, Vector<Object> parametros ) throws IOException, MalformedURLException{
		InputStream in = null;
		// Mostramos no log qual a url do arquivo que estamos tentando baixar

		if (!parametros.isEmpty() && parametros.contains(ConstantesSistema.DOWNLOAD_APK) && 
				parametros.firstElement().equals(ConstantesSistema.DOWNLOAD_APK)){
			Log.i( ConstantesSistema.LOG_TAG, "Http.downloadApk: " + url );
		}
		
		byte[] pacoteParametros = empacotarParametros( parametros );		
		
		URL u = new URL( url );
	
//		if(ConstantesSistema.HOST.equals(ConstantesSistema.IP_CAERN_PRODUCAO)){
//			conn = ( HttpURLConnection ) u.openConnection(ConstantesSistema.PROXY_CAERN);
//		}else{
			conn = ( HttpURLConnection ) u.openConnection();
//		}

		// Setamos os parametros da conexao
		conn.setRequestMethod( "POST" );
	    conn.setRequestProperty("Content-Type", "application/octet-stream");
	    conn.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
	    conn.setRequestProperty("Content-Length", pacoteParametros.length+"");
	    conn.setRequestProperty("Accept-Encoding", "identity");
	    
	    if ( parametros.contains(ConstantesSistema.PING)){
	    	conn.setConnectTimeout( 3000 );
	    } else {
	    	conn.setConnectTimeout(300);
	    }
	    
		// Tanto informamos parametros na requisicao quanto
	    // recebemos dados da mesma. Por isso, ambas as
	    // propriedades estao configuradas para verdadeiro
		conn.setDoInput( true );
		conn.setDoOutput( true );
		// Estabelecemos o canal de conexao
		conn.connect();
		
		// Enviamos a requisicao em si
		conn.getOutputStream().write( pacoteParametros );			
		
		// Selecionamos o tamanho do arquivo para que possamos
		// informar a barra de progresso, o seu MAX
		if(conn != null){
			
			fileLength = conn.getContentLength();
			// Logamos o tamanho do arquivo
			Log.i( ConstantesSistema.LOG_TAG, "FileSize: " + fileLength );			
			
			// Pegamos o retorno da requisicao
			in = conn.getInputStream();
		}
			
		// Retorna a requisicao para tratamento do tipo de chamada solicitada
		return in;		
	}
			
	/**
	 * 
	 * Verifica se o servidor do GSAN estao
	 * online
	 * 
	 * @autor Bruno Barros
	 * @date 01/09/2011
	 * 
	 * @return - Caso online, true, senao false
	 */
	public boolean serverOnline(){
				boolean sucesso = true;
		
		try {
//			 Verificamos se o servidor está online
			Vector<Object> parametros = new Vector<Object>(3);
			parametros.add( new Byte(ConstantesSistema.PING) );
//			parametros.add( new Long( Util.getIMEI( context ) ) );

			InputStream in = this.comunicar( ConstantesSistema.ACTION, parametros );
			
			if(in != null){
				sucesso = in.read() == '*';
				return sucesso;
			}else{
				return false;
			}
					
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(ConstantesSistema.LOG_TAG , e.getMessage());
			sucesso = false;			
		}
		
		return sucesso;		
	}
	
	
	/**
	 * 
	 * @author Arthur Carvalho
	 * @date 20/08/2013
	 *
	 * @param idComando
	 * @return
	 */
	public Integer arquivoLiberadoParaTransmissao(Integer idComando){
		
		Integer retorno = null;
		boolean sucesso = true;
		
		try {
			if ( serverOnline() ) {
			//Verificamos se o arquivo ja foi finalizado		
			Vector<Object> parametros = new Vector<Object>(3);
			parametros.add( new Byte(ConstantesSistema.CONSULTAR_ARQUIVO_FINALIZADO) );
			parametros.add( new Long( idComando ) );

			InputStream in = this.comunicar( ConstantesSistema.ACTION, parametros );
			
				if(in != null){
					sucesso = in.read() == '*';
					if ( sucesso ) {
						retorno = 1;
					} else {
						retorno = 2;
					}
					return retorno;
				}else{
					return retorno = 3;
				}
				
			} else {
				retorno = 3;
			}
					
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(ConstantesSistema.LOG_TAG , e.getMessage());
			sucesso = false;			
		}
		
		return retorno;		
	}
	
	
	public byte[] empacotarParametros(Vector<Object> parametros) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		byte[] resposta = null;

		parametros.trimToSize();

		// escreve os dados no OutputStream
		if (parametros != null) {
			int tamanho = parametros.size();
			for (int i = 0; i < tamanho; i++) {
				Object param = parametros.elementAt(i);
				if (param instanceof Byte) {
					dos.writeByte(((Byte) param).byteValue());
				} else if (param instanceof Integer) {
					dos.writeInt(((Integer) param).intValue());
				} else if (param instanceof Short) {
					dos.writeShort(((Short) param).shortValue());					
				} else if (param instanceof Long) {
					dos.writeLong(((Long) param).longValue());
				} else if (param instanceof String) {
					dos.writeUTF((String) param);
				} else if (param instanceof byte[]) {
					dos.write((byte[]) param);
				}
			}
		}

		// pega os dados enpacotados
		resposta = baos.toByteArray();

		if (dos != null) {
			dos.close();
			dos = null;
		}
		if (baos != null) {
			baos.close();
			baos = null;
		}

		// retorna o array de bytes
		return resposta;
	}	
	public InputStream getRespostaOnline() {
		return this.respostaOnline;
	}

	public void setRespostaOnline(InputStream respostaOnline) {
		this.respostaOnline = respostaOnline;
	}

	public void setFileLength(int fileLength) {
		this.fileLength = fileLength;
	}

	public int getFileLength() {
		return fileLength;
	}

	public void setTipoArquivo(char tipoArquivo) {
		this.tipoArquivo = tipoArquivo;
	}

	public char getTipoArquivo() {
		return tipoArquivo;
	}

	// Só podemos pegar a mensagem de error uma vez
	public static String getMensagemError() {
		String temp = mensagemError;
		mensagemError = null;
		return temp;
	}
	
	 /**
     * Baixa um arquivo a partir de uma url. Atualiza sempre a barra de progresso para que o usu�rio
     * saiba o que est� acontecendo no momento
     */
    public boolean enviarDadosImovel(ImovelAtlzCadastral imovelAtlzCadastral, ClienteAtlzCadastral clienteAtlzCadastral, List<ClienteFoneAtlzCad> listaClienteFoneAtlzCadastral,
    		 List<HidrometroInstHistAtlzCad> listaHidrometroInstHistAtlzCad, List<ImovelSubCategAtlzCad> listaImovelSubCategAtlzCad, List<ImovelOcorrencia> listaImovelOcorrencia ) {

        boolean success = false;

        // Verificamos se o servidor est� online
        if (this.serverOnline()) {

            try {
                
            	fileContent = new StringBuilder("");
                fileContent.append(Fachada.getInstance().gerarArquivoRetornoImovel(imovelAtlzCadastral));
                
	           	//CLIENTE | CLIENTE FONE
		        fileContent.append(Fachada.getInstance().gerarArquivoRetornoCliente(clienteAtlzCadastral, listaClienteFoneAtlzCadastral, imovelAtlzCadastral.getIntegracaoID()));
		        
	        	fileContent.append(Fachada.getInstance().gerarArquivoRetornoHidrometro(listaHidrometroInstHistAtlzCad, imovelAtlzCadastral.getIntegracaoID(), imovelAtlzCadastral.getImovelId()));
					
	        	fileContent.append(Fachada.getInstance().gerarArquivoRetornoSubategoria(listaImovelSubCategAtlzCad, imovelAtlzCadastral.getIntegracaoID()));
		         
		        fileContent.append(Fachada.getInstance().gerarArquivoRetornoOcorrencia(listaImovelOcorrencia, imovelAtlzCadastral.getIntegracaoID()));
                
            } catch (FachadaException fe) {
                fe.printStackTrace();
            }

            String returnData = fileContent.toString();
            Vector<Object> params = new Vector<Object>(3);
            params.add(new Byte(ConstantesSistema.FINALIZAR_ROTEIRO));

            // Adiciona a String do arquivo de retorno
            try {
                params.add(returnData.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException uee) {
                Log.e(ConstantesSistema.LOG_TAG, uee.getMessage());
            }

            InputStream in;

            try {
                in = this.comunicar(ConstantesSistema.ACTION, params);
                success = this.carregarArquivo(in);
            } catch (MalformedURLException mue) {
                Log.e(ConstantesSistema.LOG_TAG, mue.getMessage());
            } catch (IOException ioe) {
                Log.e(ConstantesSistema.LOG_TAG, ioe.getMessage());
            }
        }

        return success;
    }
    
    /**
     * Envia que o roteiro foi finalizado
     * 
     * @author Arthur Carvalho
     */
    public boolean enviarArquivoFinalizado(Integer fileId, Integer inidicadorCompletion) {

        boolean success = false;

        // Verificamos se o servidor est� online
        if (this.serverOnline()) {

            StringBuilder sb = new StringBuilder("");
            // Registro tipo 00
            sb.append(Util.stringPipe(REGISTER_TYPE_0));
            // Id do Arquivo Texto para Visita de Campo
            sb.append(Util.stringPipe(fileId));
            // Indicador de Finalizacao do Lote
            sb.append(Util.stringPipe(inidicadorCompletion));
            fileContent = new StringBuilder("");
            fileContent.append(sb);

            String returnData = fileContent.toString();
            Vector<Object> params = new Vector<Object>(3);
            params.add(new Byte(ConstantesSistema.FINALIZAR_IMOVEL));
            // params.add(new Long(Util.getIMEI(context)));

            // Adiciona a String do arquivo de retorno
            try {
                params.add(returnData.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException uee) {
                Log.e(ConstantesSistema.LOG_TAG, uee.getMessage());
            }

            InputStream in;

            try {
                in = this.comunicar(ConstantesSistema.ACTION, params);
                success = this.carregarArquivo(in);
            } catch (MalformedURLException mue) {
                Log.e(ConstantesSistema.LOG_TAG, mue.getMessage());
            } catch (IOException ioe) {
                Log.e(ConstantesSistema.LOG_TAG, ioe.getMessage());
            }
        }

        return success;
    }
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 14/01/2013
     *
     * @param arquivo
     * @return
     * @throws IOException
     */
    public boolean carregarArquivo(InputStream arquivo) throws IOException {

        boolean sucess = false;

        if (arquivo != null) {
            BufferedReader buff = new BufferedReader(new InputStreamReader(arquivo));

            String line = buff.readLine();

            if (line.substring(0, 1).equals("*"))
                sucess = true;
        }

        return sucess;
    }
    
    
    /**
     * Metodo responsavel por enviar os dados do endereco do imovel
     * @author Arthur Carvalho
     * @date 28/03/2013
     *
     * @param cep
     * @param logradouro
     * @param sistemaParametros
     * @param logradouroCep
     * @return
     */
    public boolean enviarDadosEndereco(Cep cep , Logradouro logradouro, SistemaParametros sistemaParametros, LogradouroCep logradouroCep) {
    	
    	 boolean success = false;

         // Verificamos se o servidor est� online
         if (this.serverOnline()) {

             try {
                 fileContent = new StringBuilder("");
                 
                 if ( cep != null ) {
                	 fileContent.append(Fachada.getInstance().gerarArquivoRetornoCep(cep));
                 }
                 
                 if ( logradouro != null ) {
                	 fileContent.append(Fachada.getInstance().gerarArquivoRetornoLogradouro(logradouro, sistemaParametros));
                 }
                 
                 if ( logradouroCep != null ) {
                	 fileContent.append(Fachada.getInstance().gerarArquivoRetornoLogradouroCep(logradouroCep));
                 }
                 
                 
             } catch (FachadaException fe) {
                 fe.printStackTrace();
             }

             String returnData = fileContent.toString();
             Vector<Object> params = new Vector<Object>(3);
             params.add(new Byte(ConstantesSistema.FINALIZAR_ROTEIRO));

             // Adiciona a String do arquivo de retorno
             try {
                 params.add(returnData.getBytes("UTF-8"));
             } catch (UnsupportedEncodingException uee) {
                 Log.e(ConstantesSistema.LOG_TAG, uee.getMessage());
             }

             InputStream in;

             try {
                 in = this.comunicar(ConstantesSistema.ACTION, params);
                 success = this.carregarArquivo(in);
             } catch (MalformedURLException mue) {
                 Log.e(ConstantesSistema.LOG_TAG, mue.getMessage());
             } catch (IOException ioe) {
                 Log.e(ConstantesSistema.LOG_TAG, ioe.getMessage());
             }
         }

         return success;
    	
    	
    }
    
    
    
    /**
     * Baixa um arquivo a partir de uma url. Atualiza sempre a barra de progresso para que o usu�rio
     * saiba o que est� acontecendo no momento
     */
    public boolean enviarDadosCep(Cep cep) {

        boolean success = false;

        // Verificamos se o servidor est� online
        if (this.serverOnline()) {

            try {
                fileContent = new StringBuilder("");
                fileContent.append(Fachada.getInstance().gerarArquivoRetornoCep(cep));
            } catch (FachadaException fe) {
                fe.printStackTrace();
            }

            String returnData = fileContent.toString();
            Vector<Object> params = new Vector<Object>(3);
            params.add(new Byte(ConstantesSistema.FINALIZAR_ROTEIRO));

            // Adiciona a String do arquivo de retorno
            try {
                params.add(returnData.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException uee) {
                Log.e(ConstantesSistema.LOG_TAG, uee.getMessage());
            }

            InputStream in;

            try {
                in = this.comunicar(ConstantesSistema.ACTION, params);
                success = this.carregarArquivo(in);
            } catch (MalformedURLException mue) {
                Log.e(ConstantesSistema.LOG_TAG, mue.getMessage());
            } catch (IOException ioe) {
                Log.e(ConstantesSistema.LOG_TAG, ioe.getMessage());
            }
        }

        return success;
    }
    
    /**
     * Baixa um arquivo a partir de uma url. Atualiza sempre a barra de progresso para que o usu�rio
     * saiba o que est� acontecendo no momento
     */
    public boolean enviarDadosLogradouro(Logradouro logradouro, SistemaParametros sistemaParametros) {

        boolean success = false;

        // Verificamos se o servidor est� online
        if (this.serverOnline()) {

            try {
                fileContent = new StringBuilder("");
                fileContent.append(Fachada.getInstance().gerarArquivoRetornoLogradouro(logradouro, sistemaParametros));
            } catch (FachadaException fe) {
                fe.printStackTrace();
            }

            String returnData = fileContent.toString();
            Vector<Object> params = new Vector<Object>(3);
            params.add(new Byte(ConstantesSistema.FINALIZAR_ROTEIRO));

            // Adiciona a String do arquivo de retorno
            try {
                params.add(returnData.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException uee) {
                Log.e(ConstantesSistema.LOG_TAG, uee.getMessage());
            }

            InputStream in;

            try {
                in = this.comunicar(ConstantesSistema.ACTION, params);
                success = this.carregarArquivo(in);
            } catch (MalformedURLException mue) {
                Log.e(ConstantesSistema.LOG_TAG, mue.getMessage());
            } catch (IOException ioe) {
                Log.e(ConstantesSistema.LOG_TAG, ioe.getMessage());
            }
        }

        return success;
    }
    
    /**
     * Baixa um arquivo a partir de uma url. Atualiza sempre a barra de progresso para que o usu�rio
     * saiba o que est� acontecendo no momento
     */
    public boolean enviarDadosLogradouroCep(LogradouroCep logradouroCep) {

        boolean success = false;

        // Verificamos se o servidor est� online
        if (this.serverOnline()) {

            try {
                fileContent = new StringBuilder("");
                fileContent.append(Fachada.getInstance().gerarArquivoRetornoLogradouroCep(logradouroCep));
            } catch (FachadaException fe) {
                fe.printStackTrace();
            }

            String returnData = fileContent.toString();
            Vector<Object> params = new Vector<Object>(3);
            params.add(new Byte(ConstantesSistema.FINALIZAR_ROTEIRO));

            // Adiciona a String do arquivo de retorno
            try {
                params.add(returnData.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException uee) {
                Log.e(ConstantesSistema.LOG_TAG, uee.getMessage());
            }

            InputStream in;

            try {
                in = this.comunicar(ConstantesSistema.ACTION, params);
                success = this.carregarArquivo(in);
            } catch (MalformedURLException mue) {
                Log.e(ConstantesSistema.LOG_TAG, mue.getMessage());
            } catch (IOException ioe) {
                Log.e(ConstantesSistema.LOG_TAG, ioe.getMessage());
            }
        }

        return success;
    }
    
    
    
    
}