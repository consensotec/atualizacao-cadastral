package com.br.gsanac.gui;

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

public class ApkActivity extends BaseActivity implements OnClickListener {

    ProgressDialog mProgressDialog;
    AlertDialog alertDialog ;
    final KeyEvent event = new KeyEvent(KeyEvent.KEYCODE_BACK,
                                        KeyEvent.KEYCODE_BACK);
    
	public static final int ERRO_VERSAO_INDISPONIVEL = 98;
	public static final int IGNORE_DOWNLOAD_VERSION = 90;
    
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fachada.setContext(this);
        
        
        if ( enviarTelaLogin() ) {
        
        
	
	        if (!DBConnection.checkDatabase() ) {
	
	            mProgressDialog = new ProgressDialog(ApkActivity.this) {
	
	                @Override
	                public void onBackPressed() {
	                }
	
	                @Override
	                public boolean onSearchRequested() {
	                    return false;
	                }
	
	            };
	            mProgressDialog.setIndeterminate(false);
	            mProgressDialog.setTitle(getString(R.string.conexao_servidor_gsan));
	            mProgressDialog.setMessage(getString(R.string.validando_versao));
	            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	            mProgressDialog.setMax(100);
	            mProgressDialog.setCancelable(false);
	            mProgressDialog.setIcon(R.drawable.transfer);
	            mProgressDialog.show();
	            
	            AsyncTask<Object, Object, Object> taskDownloadFile = new AsyncTask<Object, Object, Object>() {
	                @Override
	                protected Object doInBackground(Object... params) {
	
	                	 ComunicacaoWebServer downloadFile = new ComunicacaoWebServer(ApkActivity.this);
	                	 
	                     if (downloadFile.apkOperation(ConstantesSistema.VERIFICAR_VERSAO, mProgressDialog, getApplicationContext())) {
	                    	 
   	    	        		Intent i = new Intent(ApkActivity.this, DownloadApkActivity.class);
   	    	        		mProgressDialog.dismiss();
   	    	        		startActivity(i);
   	  						finish();
	                    	 
	    	             }else{
	    	            	
	    	            	 if ( getIntent().getSerializableExtra(ConstantesSistema.LOGIN) != null ) {
		                	 	// Caso contrario, encaminhamos para a tela de carregamento de arquivo online
		    					Intent i = new Intent(ApkActivity.this, DownloadArquivoActivity.class);
		    					i.putExtra(ConstantesSistema.LOGIN, getIntent().getSerializableExtra(ConstantesSistema.LOGIN));
	    		                i.putExtra(ConstantesSistema.SENHA, getIntent().getSerializableExtra(ConstantesSistema.SENHA));
		    					ApkActivity.this.startActivity(i);
		    					finish();
	    					} else {
	    						Intent i = new Intent(ApkActivity.this,SelecionarArquivoActivity.class);
	    						i.putExtra(ConstantesSistema.CPF_LOGIN, getIntent().getSerializableExtra(ConstantesSistema.CPF_LOGIN));
                                ApkActivity.this.startActivityForResult(i,1);
                                finish();
	    					}
	    	             }
	                	
	                    return true;
	                }
	
	                @Override
	                protected void onPostExecute(Object result) {
	                    mProgressDialog.dismiss();
	
	                    alertDialog = new AlertDialog.Builder(ApkActivity.this).setTitle(getString(R.string.error_ao_carregar_arquivo))
                                                                                .setMessage(R.string.conexao_falhou)
                                                                                .setIcon(R.drawable.ok)
                                                                                .setCancelable(false)
                                                                                .setNeutralButton(getString(R.string.sim),
                                                                                                  new DialogInterface.OnClickListener() {
                                                                                                      @Override
                                                                                                      public void onClick(
                                                                                                              DialogInterface dialog,
                                                                                                              int which) {
                                                                                                          Intent i = new Intent(ApkActivity.this,SelecionarArquivoActivity.class);

                                                                                                          ApkActivity.this.startActivityForResult(i,1);
                                                                                                          finish();
                                                                                                      }
                                                                                                  }).show();
	                    
	                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
	
	                        @Override
	                        public void onDismiss(DialogInterface dialog) {
	                            Intent i = new Intent(ApkActivity.this,
	                                                  SelecionarArquivoActivity.class);
	
	                            ApkActivity.this.startActivityForResult(i, 1);
	                            finish();
	                        }
	                    });
	
	                }
	            };
	
	            taskDownloadFile.execute();
	
	        } else {
	
	            Intent intent = new Intent(this, DownloadArquivoActivity.class);
	            startActivity(intent);
	            finish();
	        }
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

	public void onBackPressed() {
		return;
	}
    

	@Override
	protected void onRestart() {
		super.onRestart();
		
		// Caso o usuário tenha apertado em cancelar ao baixar a versão
    	new AlertDialog.Builder(ApkActivity.this)
		.setTitle(getString(R.string.str_download_alert_file))
		.setMessage( getString(R.string.str_error_aborted) )
		.setNeutralButton(getString(android.R.string.ok), 
			new DialogInterface.OnClickListener() {
				public void onClick(
					DialogInterface dialog,
					int which) {
					
					Intent i = new Intent(ApkActivity.this, DownloadApkActivity.class);
					startActivity(i);
					finish();
				
				}
		}).show(); 
	} 
	
	private boolean enviarTelaLogin(){
		boolean retorno = true;
		
		
		try {
			if ( DBConnection.checkDatabase() ) {
			   SistemaParametros sistemaParametros = new SistemaParametros();
			   sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
			 
			   if ( sistemaParametros != null && sistemaParametros.getId() != null ) {
				   
				   Intent i = new Intent(ApkActivity.this, LoginActivity.class);
				   startActivity(i);
				   finish();
				   retorno = false;
			   }
			}
		} catch (FachadaException fe) {
			Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
		}
		
		return retorno;
	}
}
