package com.br.gsanac.gui;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.br.gsanac.R;
import com.br.gsanac.conexao.DBConnection;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelAtlzCadastral.ImovelAtlzCadastralColunas;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Cryptograph;
import com.br.gsanac.util.Util;

public class LoginActivity extends Activity {
	
	private TextView tvLogin;
	private TextView tvPassword;
	private TextView tvCPF;
	private Button btnLogin;

	Intent intent ;
	LocationManager manager = null;
	
	private TextView numero;
	
	/**
	 * @author Flávio Ferreira
	 * @since 29/08/2013
	 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_sobre, menu);
            return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sobre:
            	menuSobre();
        }
		return false;
    }
    
    public void menuSobre(){
    	SistemaParametros sistemaParametros = new SistemaParametros();
		try {
			sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
			
			
		} catch (FachadaException e) {
			e.printStackTrace();
		}
		
		String versao =  Util.getVersaoSistema(LoginActivity.this);
		if(versao == null){
			versao = "";
		}
		String login = sistemaParametros.getLogin();
		if(login == null){
			login = "";
		}
		
		String desLocalidade = sistemaParametros.getDescricaoLocalidade();
		if(desLocalidade == null){
			desLocalidade = "";
		}
		
		String idLocalidade = sistemaParametros.getIdLocalidade();
		if(idLocalidade == null){
			idLocalidade = "";
		}
		
		String qtdImoveis = sistemaParametros.getQuantidadeImovel();
		if(qtdImoveis == null){
			qtdImoveis = "";
		}
		
		String selection = ImovelAtlzCadastralColunas.INDICADOR_FINALIZADO + "=?";
		
		String[] selectionArgs = new String[]{
				String.valueOf(ConstantesSistema.SIM)
		};
		
		List<ImovelAtlzCadastral> listaImovAtualizado = null;
		
		try {
			listaImovAtualizado = (List<ImovelAtlzCadastral>) Fachada.getInstance().pesquisarLista(ImovelAtlzCadastral.class, selection, selectionArgs, null);
		} catch (FachadaException e) {
			e.printStackTrace();
		}
		
		int qtdImovelAtualizados = 0;
		int qtdImovelNovo = 0;
		for(ImovelAtlzCadastral imovel : listaImovAtualizado){
			
			if(imovel.getImovelId() != 0){
				qtdImovelAtualizados++;
				
			}else{ 
				qtdImovelNovo++;
				
			}
		}
		
		
		new AlertDialog.Builder(LoginActivity.this).setTitle("Sobre")
		.setMessage("Versão: " +  versao + "\n" + "Login: " + login + "\n" + "Localidade: " + idLocalidade + " - " 	+
		desLocalidade + "\n" + "Imoveis Novos: " + qtdImovelNovo + "\n" + "Imoveis Atualizados: " + 
		qtdImovelAtualizados +"\n" +  "Total de Imoveis:  " + qtdImoveis)
		.setIcon(R.drawable.icon_info)
		.setCancelable(false)
		.setNeutralButton("Fechar",
		new DialogInterface.OnClickListener() {
			@Override
			public void onClick(
			      DialogInterface dialog,
			      int which) {
			}
		}).show();
    }
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        Fachada.setContext(this);
        setContentView(R.layout.tela_login);
        
        //Atualiza o numero da versao.
        String strVersaoAtual = Util.getVersaoSistema(LoginActivity.this);
        numero = (TextView) findViewById(R.id.numero);
        numero.setText("Versão " +strVersaoAtual);
        
        tvLogin = (TextView) findViewById(R.id.edtLogin);
        tvPassword = (TextView) findViewById(R.id.edtPassword);
        tvCPF = (TextView) findViewById(R.id.edtCPF);
        btnLogin = (Button) findViewById(R.id.btLogin);
        
        btnLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
            	//remove o teclado da tela
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		        
                //valida se o campo login foi informado  
                //Caso o login efetuado seja pelo cadastrador.
		        if ( tvLogin != null && !tvLogin.getText().toString().trim().equals("") ) {
		        	
		        	//valida se o campo senha foi informado
		    		if ( tvPassword != null && !tvPassword.getText().toString().trim().equals("") ) {
		
		    			//verfica se existe banco de dados - se o arquivo ja foi carregado
		    			if (!DBConnection.checkDatabase() ) {
		    				
		    				//verifica se o indicador arquivo carregado completo esta atualizado
		    				//caso contrario remove a base e solicita o usuario carregar o arquivo novamente.
		    				Intent intent = new Intent(LoginActivity.this, ApkActivity.class);
			
			                intent.putExtra(ConstantesSistema.LOGIN, tvLogin.getText().toString());
			                intent.putExtra(ConstantesSistema.SENHA, tvPassword.getText().toString());
			
			                startActivity(intent);
			                finish();
								
		    			} else {
		    				
		    				try {

		    					SistemaParametros sistemaParametros = Fachada.getInstance().validateLogin(tvLogin.getText().toString(),
									Cryptograph.encode(tvPassword.getText().toString()));
	                	        
		    					//senha de administrador do sistema
		    					if ( tvLogin.getText().toString().equals("gsan") && tvPassword.getText().toString().equals("gsan") ){
	
	                	        	try{
		                	    		sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
		                	    	} catch (FachadaException e) {
		                				e.printStackTrace();
		                			}
	                	        } else {
	                			
		                			sistemaParametros = Fachada.getInstance().validateLogin(tvLogin.getText().toString(),
											Cryptograph.encode(tvPassword.getText().toString()));
	                	        }
		    					
	                			//Valida se o login existe
								if ( sistemaParametros != null && sistemaParametros.getIdComando() != null ) {
									
									//verifica se o arquivo foi carregado completo
									if ( validaArquivoCarregadoCompleto() ) {
									
										String data = Util.convertDateToString(new Date());
					        			AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
					        			alert.setTitle("Atualização Cadastral");
					        			alert.setMessage("A data atual é " + data.substring(0,10) + ". Confirma? ");
					        			
					        			alert.setPositiveButton(R.string.str_sim, new DialogInterface.OnClickListener(){
		
					        				
					        				public void onClick(DialogInterface arg0, int arg1) {
		
												intent = new Intent(LoginActivity.this, RoteiroActivity.class);
												startActivity(intent);
												finish();	
			    								
					        				}
					            			
					        			});
					        			
					        			//Caso nao confirme!
					        			alert.setNegativeButton(R.string.str_nao, new DialogInterface.OnClickListener() {
					        				public void onClick(DialogInterface dialog, int which) {
					        					Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
					        					startActivity(intent);
					        				}
					        			});
					        			
					        			alert.show();
									} else {
										//informar que o arquivo foi carregado incompleto
										//remover e carregar novamente
										
										AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
					        			alert.setTitle("Erro inesperado no carregamento do arquivo");
					        			alert.setMessage("Solicitamos que o arquivo seja carregado novamente, caso o erro persista entrar em contato com o administrador do sistema.");
					        			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
		
					        				
					        				public void onClick(DialogInterface arg0, int arg1) {
		
					        					 //Erro no carregamento do arquivo.
                                                DBConnection.getInstance(LoginActivity.this).deleteDatabase();
                                                Util.removeInstanceRepository();
			    								
					        				}
					            			
					        			});
					        								        			
					        			alert.show();
									}
									
								} else {
									//usuario com login invalido
									new AlertDialog.Builder(LoginActivity.this).setTitle("Erro")
										.setMessage("Login inválido")
										.setIcon(R.drawable.erro)
										.setCancelable(false)
										.setNeutralButton("Ok",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
											      DialogInterface dialog,
											      int which) {
											}
										}).show();
								}
								
			    			} catch (FachadaException e) {
								e.printStackTrace();
							}
		    			}
		
		    		} else {
		    			//usuario nao informou a senha.
		    			new AlertDialog.Builder(LoginActivity.this).setTitle("Erro")
						.setMessage("Informe a senha")
						.setIcon(R.drawable.erro)
						.setCancelable(false)
						.setNeutralButton("Ok",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
							      DialogInterface dialog,
							      int which) {
							}
						}).show();
		    		}
		    	
		    	//valida se o CPF foi informado
		        } else if ( tvCPF != null && !tvCPF.getText().toString().trim().equals("")  ) {
		        	
		        	//valida o cpf informado.
		        	if ( Util.validateCPF(tvCPF.getText().toString()) ) {
		        		
		        		//verifica se o arquivo nao ta carregado
		        		if (!DBConnection.checkDatabase() ) {
			        		Intent intent = new Intent(LoginActivity.this, ApkActivity.class);
			                intent.putExtra(ConstantesSistema.CPF_LOGIN, tvCPF.getText().toString());
			
			                startActivity(intent);
			                finish();
			                
		        		} else {
		        			
		        			try {

		        				
		        				SistemaParametros parametros = new SistemaParametros();
		        				parametros = (SistemaParametros) Fachada.getInstance().pesquisar(parametros, null, null);
		        				
		        				//valida se o arquivo carregado ta para o cpf informado no login
		    					SistemaParametros sistemaParametros = Fachada.getInstance().validarLoginCpf(tvCPF.getText().toString());

		    					//Valida se o login existe
								if ( sistemaParametros != null && sistemaParametros.getIdComando() != null ) {
									
									//valida se o arquivo foi carregado completo - sem erros inesperados no arquivo.
									if ( validaArquivoCarregadoCompleto() ) {
									
					    				Intent intent = new Intent(LoginActivity.this, RoteiroActivity.class);
						                startActivity(intent);
						                finish();
									} else {
										//informar que o arquivo foi carregado incompleto
										//remover e carregar novamente
										AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
					        			alert.setTitle("Erro inesperado no carregamento do arquivo");
					        			alert.setMessage("Solicitamos que o arquivo seja carregado novamente, caso o erro persista entrar em contato com o administrador do sistema.");
					        			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
		
					        				public void onClick(DialogInterface arg0, int arg1) {
		
					        					 //Erro no carregamento do arquivo.
                                                DBConnection.getInstance(LoginActivity.this).deleteDatabase();
                                                Util.removeInstanceRepository();
					        				}
					        			});
					        								        			
					        			alert.show();
									}
				                
								} else {
									new AlertDialog.Builder(LoginActivity.this).setTitle("Erro")
									.setMessage("Login inválido")
									.setIcon(R.drawable.erro)
									.setCancelable(false)
									.setNeutralButton("Ok",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
										      DialogInterface dialog,
										      int which) {
										}
									}).show();
								}
			    			} catch (FachadaException e) {
								e.printStackTrace();
							}
		        		}
		                
		        	} else {
		        		new AlertDialog.Builder(LoginActivity.this).setTitle("Erro")
							.setMessage("CPF inválido")
							.setIcon(R.drawable.erro)
							.setCancelable(false)
							.setNeutralButton("Ok",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
								      DialogInterface dialog,
								      int which) {
								}
							}).show();
		        	}
		        	
		        } else {
		        	new AlertDialog.Builder(LoginActivity.this).setTitle("Erro")
						.setMessage("Login inválido")
						.setIcon(R.drawable.erro)
						.setCancelable(false)
						.setNeutralButton("Ok",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(
							      DialogInterface dialog,
							      int which) {
							}
						}).show();
		        }
            }
        });
        
        
        Util.createSystemDirs();
       
    }
	
	/**
	 * Metodo responsavel por verificar se o arquivo foi carregado completo.
	 * 
	 * @author Arthur Carvalho
	 * @date 03/10/2013
	 *
	 * @return
	 */
	public boolean validaArquivoCarregadoCompleto(){
		boolean retorno = false;
		SistemaParametros sistemaParametros = new SistemaParametros();
	        

    	try{
    		sistemaParametros = (SistemaParametros) Fachada.getInstance().pesquisar(sistemaParametros, null, null);
    	} catch (FachadaException e) {
			e.printStackTrace();
		}
    	
    	//valida se o arquivo foi carregado completo
    	if ( sistemaParametros != null && sistemaParametros.getIndicadorArquivoCarregado() != null && sistemaParametros.getIndicadorArquivoCarregado().equals(Integer.valueOf(1)) ) {
    		retorno = true;
    	}
    	
    	return retorno;
	}
    
    
 
}