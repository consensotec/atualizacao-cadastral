package com.br.gsanac.conexao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.DBLoader;
import com.br.gsanac.util.Util;


/**
 * @author Arthur Carvalho
 */
public class ComunicacaoWebServer {

    private HttpURLConnection   conn            = null;

    private Context             context         = null;

    private StringBuilder       fileContent     = new StringBuilder("");

    private static final String REGISTER_TYPE_0 = "00";

    public ComunicacaoWebServer(Context context) {
        super();
        this.context = context;
    }

    /**
     * 
     * @author Arthur Carvalho
     * @date 31/01/2013
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     * @throws MalformedURLException
     */
    private InputStream communicate(String url, List<Object> params) throws IOException, MalformedURLException {

        InputStream is = null;

        // Mostramos no log qual a url do arquivo que estamos tentando baixar
        if (Util.hasParameter(params, ConstantesSistema.DOWNLOAD_FILE)) {
            Log.i(ConstantesSistema.LOG_TAG, "Http.downloadArquivo: " + url);
        }

        byte[] packParams = Util.packagingParameters(params);

        URL u = new URL(url);

        conn = (HttpURLConnection) u.openConnection();

        // Setamos os parametros da conexao
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/octet-stream");
        conn.setRequestProperty("User-Agent", "Profile/MIDP-2.0 Configuration/CLDC-1.1");
        conn.setRequestProperty("Content-Length", String.valueOf(packParams.length));
        conn.setRequestProperty("Accept-Encoding", "identity");
        /*
         * Tanto informamos parametros na requisicao quanto recebemos dados da mesma. Por isso,
         * ambas as propriedades estao configuradas para verdadeiro
         */
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(300);
        // Estabelecemos o canal de conexao
        conn.connect();

        // Enviamos a requisicao em si
        conn.getOutputStream().write(packParams);

        /*
         * Selecionamos o tamanho do arquivo para que possamos informar a barra de progresso, o seu
         * MAX
         */
        int fileLenght = 0;

        List<String> values = conn.getHeaderFields().get("content-Length");

        if (values != null && !values.isEmpty()) {
            String sLength = values.get(0);

            if (sLength != null) {
                fileLenght = Util.parseStringToInteger(sLength);
            }
        }

        // Logamos o tamanho do arquivo
        Log.i(ConstantesSistema.LOG_TAG, "FileSize: " + fileLenght);

        // Pegamos o retorno da requisicao
        is = conn.getInputStream();

        // Retorna a requisicao para tratamento do tipo de chamada solicitada
        return is;
    }

    /**
     * 
     * @author Arthur Carvalho
     * @date 31/01/2013
     *
     * @param operation
     * @return
     */
    public boolean fileOperation(Byte operation) {

        boolean result = false;

        InputStream is = null;

        if (this.isServerOnline()) {
            List<Object> params = new ArrayList<Object>(2);
            params.add(operation);
            params.add(new Long(Util.getEnderecoMac(this.context)));

            try {

                is = this.communicate(ConstantesSistema.ACTION, params);
                result = loadFileToDatabase(is);
                if (result) {
                    // Enviamos uma mensagem ao GSAN informando que o arquivo foi carregado com
                    // sucesso
                    if (!routeInitializationSignal(ConstantesSistema.ARQUIVO_EM_CAMPO)) {
                        result = false;
                    }
                } else {
                    result = false;
                }

            } catch (MalformedURLException mue) {
                Log.e(ConstantesSistema.LOG_TAG, mue.getMessage());
                mue.printStackTrace();
            } catch (IOException ioe) {
                Log.e(ConstantesSistema.LOG_TAG, ioe.getMessage());
                ioe.printStackTrace();
            }
        }

        return result;

    }

    /**
     * 
     * @author Arthur Carvalho
     * @date 31/01/2013
     *
     * @param operation
     * @param progressDialog
     * @return
     */
    public boolean fileOperation(Byte operation, ProgressDialog progressDialog, String login, String senha) {

        boolean result = false;

        InputStream is = null;

        //verifica se o usuario est· online
        if (this.isServerOnline()) {
            
        	List<Object> params = new ArrayList<Object>(2);
            params.add(operation);
            params.add(login);
            params.add(senha);

            try {
            	//estabelece uma comunicacao com o servidor GSAN
                is = this.communicate(ConstantesSistema.ACTION, params);
                //retorna o arquivo disponivel - carrega o arquivo retornado.
                result = loadFileToDatabase(is, progressDialog);
                
                //caso o arquivo seja carregado com sucesso
                if (result) {
                    // Enviamos uma mensagem ao GSAN informando que o arquivo foi carregado com sucesso
                    if (!routeInitializationSignal(ConstantesSistema.ARQUIVO_EM_CAMPO)) {
                    	//caso a mensagem nao chegue ao gsan o sistema remove o banco de dados.
                        result = false;
                        
                        //caso o GSAN nao tenha sido atualizado o sistema remove o indicado de arquivo carregado completo.
                        SistemaParametros sistemaParametros = new SistemaParametros();
                        try{
                        	
                        	sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
                    		if ( sistemaParametros != null && sistemaParametros.getId() != null ) {
                            	
                    			sistemaParametros.setIndicadorArquivoCarregado(Integer.valueOf(2));
                            	Fachada.getInstance().update(sistemaParametros);
                            }
                    	} catch (FachadaException e) {
                			e.printStackTrace();
                		}
                    }                        
                        
                } else {
                	//caso o arquivo nao seja carregado com sucesso o sistema remove o banco de dados.
                    result = false;
                }

            } catch (MalformedURLException mue) {
                Log.e(ConstantesSistema.LOG_TAG, mue.getMessage());
                mue.printStackTrace();
            } catch (IOException ioe) {
                Log.e(ConstantesSistema.LOG_TAG, ioe.getMessage());
                ioe.printStackTrace();
            }
        }

        return result;

    }
    
    /**
     * 
     * @author Arthur Carvalho
     * @date 31/01/2013
     *
     * @param operation
     * @param progressDialog
     * @param context
     * @return
     */
    public boolean apkOperation(Byte operation, ProgressDialog progressDialog, Context context) {

        boolean result = false;

        if (this.isServerOnline()) {
            List<Object> params = new ArrayList<Object>(2);
            params.add( operation );
            
            String strVersaoAtual = Util.getVersaoSistema(context);
			Integer intVersaoAtual = Integer.valueOf(strVersaoAtual.replace(".",""));
			

            try {

            	InputStream  in = this.communicate(ConstantesSistema.ACTION, params);
                
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    	    StringBuilder sb = new StringBuilder();
	    	    String line = null;
	    	    String strNovaVersao = null;
	    	    Integer intNovaVersao = null;
	    	   
				while ((line = reader.readLine()) != null) {
				  sb.append(line + "\n");
				}
				if(sb.length() != 0){
					strNovaVersao = sb.substring(1,sb.length()-1);
					if(strNovaVersao != ""){
						strNovaVersao = strNovaVersao.replace(".", "");
						intNovaVersao = Integer.valueOf(strNovaVersao);
					}
				}
				
				if(intNovaVersao != null){
					if(intNovaVersao > intVersaoAtual){
						result = true;			
					}
				}
                
            } catch (MalformedURLException mue) {
                Log.e(ConstantesSistema.LOG_TAG, mue.getMessage());
                mue.printStackTrace();
            } catch (IOException ioe) {
                Log.e(ConstantesSistema.LOG_TAG, ioe.getMessage());
                ioe.printStackTrace();
            }
        }

        return result;

    }

    /**
     * Verifica se o servidor do GSAN est√° online
     * 
     * @autor Bruno Barros
     * @date 01/09/2011
     * @return - Caso online, true, sen√£o false
     */
    public boolean isServerOnline() {

        int status = 0;

        try {
            HttpGet request = new HttpGet(ConstantesSistema.GSAN_HOST);

            HttpParams httpParameters = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpResponse response = httpClient.execute(request);

            status = response.getStatusLine().getStatusCode();

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
        }

        return (status == HttpStatus.SC_OK);
    }

    /**
     * 
     * @author Arthur Carvalho
     * @date 31/01/2013
     *
     * @param is
     * @return
     * @throws IOException
     */
    public boolean loadFileToDatabase(InputStream is) throws IOException {

        BufferedReader buff = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));

        String line = "";

        boolean sucess = true;
        DBLoader.CONTADOR_IMOVEL = 0;
        while ((line = buff.readLine()) != null) {
            try {
                if (line.substring(0, 1).equals("#")) {
                    sucess = false;
                    break;
                } else {
                    line = line.replace("*", "");
                    DBLoader.carregarBancoDados(line, null);
                }
            } catch (RepositorioException re) {
                Log.e(ConstantesSistema.LOG_TAG, re.getMessage() + " " + re.getCause());
                re.printStackTrace();
            }
        }

        return sucess;
    }

    /**
     * Metodo responsavel por carregar o arquivo de atualizacao cadastral no banco de dados do tablet.
     * 
     * @author Arthur Carvalho
     * @date 31/01/2013
     *
     * @param is
     * @param progressDialog
     * @return
     * @throws IOException
     */
    public boolean loadFileToDatabase(InputStream is, ProgressDialog progressDialog) throws IOException {

    	
    	// Cria um arquivo para que ele possa ser compartilhado entre outros usuarios.
    	File file = new File(ConstantesSistema.SDCARD_GSANAC_FILES_PATH + "/" + "Arquivo" + ".txt");
        FileOutputStream fileOut = null;
        fileOut = new FileOutputStream(file);
        
    	
        BufferedReader buff = new BufferedReader(new InputStreamReader(is));

        String line = "";

        boolean sucess = true;

        //Lemos a primeira linha
        DBLoader.CONTADOR_IMOVEL = 0;
        Integer numberLine = new Integer(0);
        while ((line = buff.readLine()) != null) {
            try {
                if (line.substring(0, 1).equals("#")) {
                    sucess = false;
                    break;
                } else {                              
                    numberLine++;
                    line = line.replace("*", "");
                    //armazena os registros no banco de dados do tablet
                    DBLoader.carregarBancoDados(line, null);
                    
                    fileOut.write(line.getBytes());
                    String br = "\n";
                    fileOut.write(br.getBytes());
                    
                    progressDialog.setProgress((int) (((double) numberLine / (double) 500) * 100));
                    progressDialog.setSecondaryProgress((int) (((double) numberLine / (double) 500) * 100));
                }
               
            } catch (RepositorioException re) {
                Log.e(ConstantesSistema.LOG_TAG, re.getMessage() + " " + re.getCause());
                re.printStackTrace();
            }
        }
        
        try{
        	
        	//Renomeia o arquivo com o nome correto.
        	SistemaParametros sistemaParametros = new SistemaParametros();
        	sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
        	if ( sistemaParametros != null && sistemaParametros.getDescricaoArquivo() != null ) {
	        	
        		File fileNovo = new File(ConstantesSistema.SDCARD_GSANAC_FILES_PATH + "/" + sistemaParametros.getDescricaoArquivo() );
	        	file.renameTo(fileNovo);
	        	
	        	if ( sistemaParametros.getIndicadorArquivoCarregado() != null && sistemaParametros.getIndicadorArquivoCarregado().equals(Integer.valueOf(1))) {
	        		sucess = true;
	        	} else {
	        		sucess = false;
	        	}
	        	
        	} else {
        		file.delete();
        		sucess = false;
        	}
        	
        } catch (FachadaException e) {
			e.printStackTrace();
		}

        return sucess;
    }

    /**
     * Baixa um arquivo a partir de uma url. Atualiza sempre a barra de progresso para que o usu·rio
     * saiba o que est· acontecendo no momento
     */
    public boolean uploadReturnFile(String returnData) {

        boolean success = false;

        // Verificamos se o servidor est√° online
        if (this.isServerOnline()) {
            Vector<Object> params = new Vector<Object>(3);
            params.add(new Byte(ConstantesSistema.UPLOAD_FILE));
            // params.add(new Long(Util.getIMEI(context)));

            // Adiciona a String do arquivo de retorno
            try {
                params.add(returnData.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException uee) {
                Log.e(ConstantesSistema.LOG_TAG, uee.getMessage());
            }

            InputStream in;

            try {
                in = this.communicate(ConstantesSistema.ACTION, params);
                success = this.loadFile(in);
            } catch (MalformedURLException mue) {
                Log.e(ConstantesSistema.LOG_TAG, mue.getMessage());
            } catch (IOException ioe) {
                Log.e(ConstantesSistema.LOG_TAG, ioe.getMessage());
            }
        }

        return success;
    }

    /**
     * Carrega um inputstream
     * 
     * @autor Bruno Barros
     * @date 01/09/2011
     * @param arquivo
     * @throws IOException
     */
    public boolean loadFile(InputStream arquivo) throws IOException {

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
     * Baixa um arquivo a partir de uma url. Atualiza sempre a barra de progresso para que o usu√°rio
     * saiba o que est√° acontecendo no momento
     */
//    public boolean sendOSInformation(Integer serviceOrderId, Integer inidicadorCompletion) {
//
//        boolean success = false;
//
//        // Verificamos se o servidor est√° online
//        if (this.isServerOnline()) {
//
//            try {
//                fileContent = new StringBuilder("");
//                fileContent.append(Fachada.getInstance().generateReturnFile(serviceOrderId, inidicadorCompletion));
//            } catch (FachadaException fe) {
//                fe.printStackTrace();
//            }
//
//            String returnData = fileContent.toString();
//            Vector<Object> params = new Vector<Object>(3);
//            params.add(new Byte(ConstantesSistema.UPLOAD_FILE));
//
//            // Adiciona a String do arquivo de retorno
//            try {
//                params.add(returnData.getBytes("UTF-8"));
//            } catch (UnsupportedEncodingException uee) {
//                Log.e(ConstantesSistema.LOG_TAG, uee.getMessage());
//            }
//
//            InputStream in;
//
//            try {
//                in = this.communicate(ConstantesSistema.ACTION, params);
//                success = this.loadFile(in);
//            } catch (MalformedURLException mue) {
//                Log.e(ConstantesSistema.LOG_TAG, mue.getMessage());
//            } catch (IOException ioe) {
//                Log.e(ConstantesSistema.LOG_TAG, ioe.getMessage());
//            }
//        }
//
//        return success;
//    }

    /**
     * Envia que o roteiro foi finalizado
     * 
     * @author Arthur Carvalho
     */
    public boolean sendFileFinished(Integer fileId, Integer inidicadorCompletion) {

        boolean success = false;

        // Verificamos se o servidor est√° online
        if (this.isServerOnline()) {

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
            params.add(new Byte(ConstantesSistema.FINALIZAR_ROTEIRO));
            // params.add(new Long(Util.getIMEI(context)));

            // Adiciona a String do arquivo de retorno
            try {
                params.add(returnData.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException uee) {
                Log.e(ConstantesSistema.LOG_TAG, uee.getMessage());
            }

            InputStream in;

            try {
                in = this.communicate(ConstantesSistema.ACTION, params);
                success = this.loadFile(in);
            } catch (MalformedURLException mue) {
                Log.e(ConstantesSistema.LOG_TAG, mue.getMessage());
            } catch (IOException ioe) {
                Log.e(ConstantesSistema.LOG_TAG, ioe.getMessage());
            }
        }

        return success;
    }

    /**
     * Manda um sinal para GSAN informando que a rota foi inicializada com sucesso.
     */
    public boolean routeInitializationSignal(Integer situacaoArquivo) {

        boolean sucess = false;

        // Verificamos se o servidor est· online
        if (isServerOnline()) {
            Vector<Object> parametros = new Vector<Object>(2);
            parametros.add(new Byte(ConstantesSistema.ATUALIZAR_SITUACAO_ARQUIVO));
            // Pesquisa sistema parametros
            SistemaParametros sistemaParametros = new SistemaParametros();
            try {
            	sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
            } catch (FachadaException e1) {
                e1.printStackTrace();
            }
            
            parametros.add(new Long(sistemaParametros.getIdComando()));
            parametros.add(situacaoArquivo);

            try {
                InputStream in = this.communicate(ConstantesSistema.ACTION, parametros);
                sucess = in.read() == '*';
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
                sucess = false;
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
                sucess = false;
            }
        }

        return sucess;
    }

}
