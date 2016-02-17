package com.br.gsanac.gui;

import java.io.File;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.br.gsanac.R;
import com.br.gsanac.conexao.ComunicacaoWebServer;
import com.br.gsanac.conexao.DBConnection;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

public class DownloadArquivoActivity extends Activity implements OnClickListener {

    ProgressDialog mProgressDialog;
    final KeyEvent event = new KeyEvent(KeyEvent.KEYCODE_BACK,
                                        KeyEvent.KEYCODE_BACK);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fachada.setContext(this);

        //verifica se existe banco de dados cadastrado no sistema e nao existe nenhum registro de sistema parametro
        //caso isso aconteca, remove o banco de dados
        if (DBConnection.checkDatabase() && !existeSistemaParametros() ) {
    		
        	//remove banco de dados do sistema, preparando o sistema para carregar um novo arquivo.
        	DBConnection.getInstance(DownloadArquivoActivity.this).deleteDatabase();
    	} 

        //Caso nao exista banco de dados no sistema
        if (!DBConnection.checkDatabase()) {

            mProgressDialog = new ProgressDialog(DownloadArquivoActivity.this) {
            	//desabilita os botoes
                @Override
                public void onBackPressed() {
                }

                @Override
                public boolean onSearchRequested() {
                    return false;
                }
            };
            
            //monta a barra de progresso para ser exibida enquanto o arquivo é carregado.
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setTitle(getString(R.string.titulo_carregando_arquivo));
            mProgressDialog.setMessage(getString(R.string.carregando));
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setMax(100);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setIcon(R.drawable.transfer);
            mProgressDialog.show();
            
            AsyncTask<Object, Object, Object> taskDownloadFile = new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object... params) {

                	//verifica se o servidor esta online
                    ComunicacaoWebServer downloadFile = new ComunicacaoWebServer(DownloadArquivoActivity.this);
                    if ( downloadFile.isServerOnline() ) {

                    	//recebe o login como parametro - para pesquisar o arquivo para o login informado
                    	String login = (String) getIntent().getSerializableExtra(ConstantesSistema.LOGIN);
                    	String senha = (String) getIntent().getSerializableExtra(ConstantesSistema.SENHA);
                    	
                    	//carrega o arquivo, caso seja carregado com sucesso
	                    if (downloadFile.fileOperation(ConstantesSistema.DOWNLOAD_FILE, mProgressDialog, login, senha)) {
	                    	
	                    	//recupera a versao do sistema - tablet
	                        String strVersaoAtual = Util.getVersaoSistema(DownloadArquivoActivity.this);
                            
                            
	                        //recupera a versao do sistema - arquivo carregado
                            SistemaParametros sistemaParametros = new SistemaParametros();
                            try{
                        		sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
                        	} catch (FachadaException e) {
                    			e.printStackTrace();
                    		}
                            
                        	Integer intVersaoAtual = Integer.valueOf(strVersaoAtual.replace(".", ""));
                        	Integer intNovaVersao = Integer.valueOf(sistemaParametros.getNumeroVersao().replace(".", ""));
                        	
                        	//verifica se a versao do arquivo carregado é maior que a do tablet 
                        	//caso seja remove o arquivo carregado e solicita o usario atualizar a versao
                        	if(intNovaVersao > intVersaoAtual){
                        		mProgressDialog.dismiss();
                        		  //remove o arquivo carregado.
                          	    DBConnection.getInstance(Fachada.getContext()).deleteDatabase();
                                Util.removeInstanceRepository();
                                
                                //caso o arquivo tenha sido carregado - envia o usuario para a tela de login
    	                        Intent i = new Intent(DownloadArquivoActivity.this, VersaoArquivoErradaActivity.class);
    	                        startActivity(i);
    	                        finish();
                    		} else {
	                        
		                        mProgressDialog.dismiss();
	
		                        //caso o arquivo tenha sido carregado - envia o usuario para a tela de login
		                        Intent i = new Intent(DownloadArquivoActivity.this, LoginActivity.class);
		                        startActivity(i);
		                        finish();
                    		}
	                        
	                    } else {
	                    	//Falha no carregamento do arquivo.
	                    	try{
	                    		//Caso tenha criado um arquivo incompleto
	                    		//Remove o arquivo.
	                        	SistemaParametros sistemaParametros = new SistemaParametros();
	                        	sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
	                        	if ( sistemaParametros != null && sistemaParametros.getDescricaoArquivo() != null ) {
	                	        	
	                        		File fileInconsistence = new File(ConstantesSistema.SDCARD_GSANAC_FILES_PATH + "/" + sistemaParametros.getDescricaoArquivo() );
	                        		fileInconsistence.delete();
	                        	}
	                        } catch (FachadaException e) {
	                			e.printStackTrace();
	                		}
	
	                    	//Erro no carregamento do arquivo.
	                        DBConnection.getInstance(DownloadArquivoActivity.this).deleteDatabase();
	                        Util.removeInstanceRepository();
	                        mProgressDialog.dismiss();
	                    }
                    }
                    return true;
                }

                @Override
                protected void onPostExecute(Object result) {
                    mProgressDialog.dismiss();

                    // Informamos ao usuário que ocorreu erro na transmissao do arquivo.
                    new AlertDialog.Builder(DownloadArquivoActivity.this)
                    							.setTitle(getString(R.string.error_ao_carregar_arquivo))
                                                .setMessage(R.string.conexao_falhou)
                                                .setIcon(R.drawable.erro)
                                                .setCancelable(false)
                                                .setNeutralButton(getString(R.string.sim),
                                                                  new DialogInterface.OnClickListener() {
                                                                      @Override
                                                                      public void onClick(
                                                                              DialogInterface dialog,
                                                                              int which) {
                                                                    	  
                                                                    	  if (DBConnection.checkDatabase()) {
	                                                                    	  //Erro no carregamento do arquivo.
	                                                                          DBConnection.getInstance(DownloadArquivoActivity.this).deleteDatabase();
	                                                                          Util.removeInstanceRepository();
                                                                    	  }
                                                                          
                                                                    	  //enviamos o usuario para tela de carregar o arquivo offline
                                                                    	  Intent i = new Intent(DownloadArquivoActivity.this,SelecionarArquivoActivity.class);
                                                                          DownloadArquivoActivity.this.startActivityForResult(i,1);
                                                                          finish();
                                                                      }
                                                                  })
                                                .show();

                }
            };

            taskDownloadFile.execute();

        } else {
        	
        	//usuario sem conexao - enviado para tela de carregar arquivo offline
    		Intent intent = new Intent(this,  SelecionarArquivoActivity.class);
    		startActivity(intent);
    		finish();
            
        }

    }

    @Override
    public void onClick(View view) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    @Override
    public boolean onSearchRequested() {
        finish();
        return false;
    }

    /**
     * Verifica se existe o registro de sistema parametros - primeiro registro a ser criado no banco de dados - no carregamento do arquivo.
     * 
     * @author Arthur Carvalho
     * @date 03/10/2013
     *
     * @return
     */
    private boolean existeSistemaParametros(){
    	boolean existe = false;
    	try {
			   SistemaParametros sistemaParametros = new SistemaParametros();
			   sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
			 
			   if ( sistemaParametros != null && sistemaParametros.getId() != null ) {
				   existe = true;
			   }
			   
			} catch (FachadaException fe) {
				Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
			}
    	return existe;
    }
}
