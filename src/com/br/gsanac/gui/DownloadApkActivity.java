package com.br.gsanac.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Vector;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.br.gsanac.R;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.fachada.FachadaWebServer;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;



public class DownloadApkActivity extends BaseActivity {	

	//private static String VERSAO_BAIXADA = "";
	
	private static final int ERROR_ABORT_REQUESTED = 99;
	public static final int ERRO_VERSAO_INDISPONIVEL = 98;
	public static final int IGNORE_DOWNLOAD_VERSION = 90;
	private static final int ERRO_VERIFICANDO_VERSAO = 97;
	private static final int ERRO_DOWNLOAD_APK = 96;
	
	private class DownloadApkControl extends AsyncTask<ProgressBar, Integer, Integer>{

		private ProgressBar prbRoute;
		protected boolean abort = false;
		
    /**
     * Prepare activity before upload
     */
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
//	        File diretorioRaiz = new File( ConstantesSistema.SDCARD_PATH );
//			 if(!diretorioRaiz.exists()){					 
//				 diretorioRaiz.mkdirs();
//			 }
			 
//	        File caminhoPastaIsc = new File( ConstantesSistema.SDCARD_GSANAC_VERSION_PATH );
//			 if(!caminhoPastaIsc.exists()){					 
//				 caminhoPastaIsc.mkdirs();
//			 }
	    }

	    @Override
	    protected void onPostExecute(Integer sucess) {	    	
	    	if(rdc.abort == false){
		    	String errorMsg = DownloadApkActivity.this.verificaErros(sucess);
		    	if (sairSistema){
		    		finish();
		    	}
		    	else if ( errorMsg != null ){	
			
			    	new AlertDialog.Builder(DownloadApkActivity.this)
					.setTitle(getString(R.string.str_alert_download_title))
					.setMessage( errorMsg )
					.setNeutralButton(getString(android.R.string.ok), 
						new DialogInterface.OnClickListener() {
							public void onClick(
								DialogInterface dialog,
								int which) {
								Intent i = new Intent(DownloadApkActivity.this, DownloadArquivoActivity.class);
								DownloadApkActivity.this.startActivity(i);
								finish();
							}
					}).show();
				} else {							
					String appName = ConstantesSistema.SDCARD_GSANAC_VERSION_PATH + "/"+ ConstantesSistema.NOME_APK;
	                Intent intent = new Intent(Intent.ACTION_VIEW); 
	                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
	                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                intent.setDataAndType(Uri.fromFile(new File(appName)),
	                "application/vnd.android.package-archive");
	                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                DownloadApkActivity.this.startActivityForResult(intent, ConstantesSistema.INSTALL_APK);
	                finish();
	                
				}
	    	}
	    }	   
	    
	    @Override
	    protected void onProgressUpdate(Integer... values) {
	    	prbRoute.setProgress( values[0] );
	    }	
	    
	    private int baixarApk(InputStream in, int fileSize){			

			File file = new File(ConstantesSistema.SDCARD_GSANAC_VERSION_PATH+"/"+ConstantesSistema.NOME_APK);
			
			try{	    	

   			 prbRoute.setMax( fileSize );
				
				FileOutputStream fos = new FileOutputStream(file);
		       
				// Guarda o valor do primeiro byte de resposta
				// para depois verificar se o download foi feito
		        String valor = Util.getValorRespostaInputStream(in);
		        
		        int progress = 0;
		       
                byte[] buffer = new byte[in.available()];
                int len;
                if( valor.equals("*")){
                    while ((len = in.read(buffer)) != -1) {
                    	progress += len;
		    	  		publishProgress( progress );
		    	  		fos.write(buffer,0,len);
		    	  		// Por Ãºltimo, escreve o valor do byte resposta 
			           
                    }
                    fos.write((byte)ConstantesSistema.RESPOSTA_OK_CHAR);
                    fos.flush();
		            fos.close();
		            
		        }else{
		        	return ERROR_ABORT_REQUESTED;
		        }
				} catch (FileNotFoundException e) {
					Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
					e.printStackTrace();
				}
			return ConstantesSistema.OK;
		}
	
		@Override
		protected Integer doInBackground(ProgressBar... params) {
			prbRoute = params[0];	
			
			try{
	    	
				
				Vector<Object> parametros = new Vector<Object>(2);
				parametros.add( new Byte( ConstantesSistema.DOWNLOAD_APK ) );
				parametros.add( Util.getEnderecoMac( DownloadApkActivity.this) );
				
				FachadaWebServer.getInstancia().iniciarConexaoWebServer(DownloadApkActivity.this);
				InputStream in = FachadaWebServer.getInstancia().comunicar( ConstantesSistema.ACTION, parametros );
				baixarApk(in, FachadaWebServer.getInstancia().getFileLength());
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
				Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
				return ConstantesSistema.ERRO_GENERICO;
			} catch (IOException e) {
				e.printStackTrace();
				Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
				return ConstantesSistema.ERRO_CARREGANDO_ARQUIVO;
			} catch ( Exception e ){
				e.printStackTrace();
				Log.e( ConstantesSistema.LOG_TAG , e.getMessage());
				return ConstantesSistema.ERRO_CARREGANDO_ARQUIVO;
			}			
			
			return ConstantesSistema.OK;
		}		
	
	}	
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    	super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == ConstantesSistema.INSTALL_APK) {

			if (resultCode != RESULT_OK) {
				new AlertDialog.Builder(DownloadApkActivity.this)
    			.setTitle(getString(R.string.str_download_alert_file))
    			.setCancelable(false)
    			.setMessage(getString(R.string.str_error_aborted) )
    			.setNeutralButton(getString(android.R.string.ok), 
    				new DialogInterface.OnClickListener() {
    					public void onClick(
    						DialogInterface dialog,
    						int which) {
    						
    						Intent i = new Intent(DownloadApkActivity.this, DownloadApkActivity.class);
    						DownloadApkActivity.this.startActivity(i);
    						finish();
    					}
    			}).show(); 
			}else{
				Intent i = new Intent(DownloadApkActivity.this, DownloadArquivoActivity.class);
				DownloadApkActivity.this.startActivity(i);
				finish();
	            
			}
			
		}
    }
	
	private boolean sairSistema;
	private DownloadApkControl rdc;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        new AlertDialog.Builder(DownloadApkActivity.this).setTitle("Versão desatualizada")
                .setMessage("O sistema vai iniciar o processo de atualização da versão.")
                .setIcon(R.drawable.erro)
                .setCancelable(false)
                .setNeutralButton("Ok",
                                  new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(
                                              DialogInterface dialog,
                                              int which) {
                                   	
                                    	  if (execute){	
                                  	        setContentView(R.layout.download_arquivo_activity);
                                  	        Fachada.setContext( Fachada.getContext() );
                                  	        
                                  	        TextView text = (TextView) findViewById(R.id.textViewProgress);
                                  	        text.setText("Baixando nova versão. Por favor, aguarde...");
                                  	        
                                  	        ProgressBar progress = (ProgressBar) findViewById( R.id.progressPorLinha );
                                  			progress.setIndeterminate( false );
                                  			rdc = new DownloadApkControl();
                                  			rdc.execute( progress );	        		
                                        
                                          }
                                      }
                                  })
                .show();
        
        
    }
    

	private String verificaErros(Integer sucess) {
		String errorMsg = null;
		
		switch (sucess){ 
    	
			case ConstantesSistema.ERRO_GENERICO:
				errorMsg = getString(R.string.str_download_erro_problema_desconhecido);
				break;
				
			case ConstantesSistema.ERRO_DOWNLOAD_ARQUIVO:
				errorMsg = getString(R.string.str_error_download);
				break;				
				
			case ConstantesSistema.ERRO_CARREGANDO_ARQUIVO:
				errorMsg = getString(R.string.str_error_laoding);
				break;			
				
			case ConstantesSistema.ERRO_SERVIDOR_OFF_LINE:
				errorMsg = getString(R.string.str_error_off);
				break;	    			
		
			case ERRO_DOWNLOAD_APK:
				errorMsg = getString(R.string.str_alert_download_apk_erro);
				break;
				
			case ERRO_VERIFICANDO_VERSAO:
				errorMsg = getString(R.string.str_alert_verificar_versao_erro);
				break;

			case ERRO_VERSAO_INDISPONIVEL:
				errorMsg = getString(R.string.str_alert_download_download_error);
				break;
				
			case ERROR_ABORT_REQUESTED:
				errorMsg = getString(R.string.str_error_aborted);
				break;
		}
		
		if(errorMsg != null){
			return errorMsg;
		}
		
		return null;		
		
	}
	
	public void onBackPressed() {
//		Intent i = new Intent(DownloadApkActivity.this, DownloadApkActivity.class);
//		startActivity(i);
		finish();
	}
    

	@Override
	protected void onRestart() {
		super.onRestart();
		
		// Caso o usuário tenha apertado em cancelar ao baixar a versão
    	new AlertDialog.Builder(DownloadApkActivity.this)
		.setTitle(getString(R.string.str_download_alert_file))
		.setMessage( getString(R.string.str_error_aborted) )
		.setNeutralButton(getString(android.R.string.ok), 
			new DialogInterface.OnClickListener() {
				public void onClick(
					DialogInterface dialog,
					int which) {
					
					Intent i = new Intent(DownloadApkActivity.this, ApkActivity.class);
					startActivity(i);
					finish();
				
				}
		}).show(); 
	}  	
	
}