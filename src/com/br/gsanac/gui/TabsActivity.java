package com.br.gsanac.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.br.gsanac.R;
import com.br.gsanac.conexao.ConexaoWebServer;
import com.br.gsanac.conexao.PhotoConnection;
import com.br.gsanac.controlador.ControladorUtil;
import com.br.gsanac.entidades.Cep;
import com.br.gsanac.entidades.Cep.Ceps;
import com.br.gsanac.entidades.ClienteAtlzCadastral;
import com.br.gsanac.entidades.ClienteAtlzCadastral.ClienteAtlzCadastralColunas;
import com.br.gsanac.entidades.ClienteFoneAtlzCad;
import com.br.gsanac.entidades.ClienteFoneAtlzCad.ClienteFoneAtlzCadColunas;
import com.br.gsanac.entidades.ClienteTipo;
import com.br.gsanac.entidades.ClienteTipo.ClienteTipoColunas;
import com.br.gsanac.entidades.EntidadeBase;
import com.br.gsanac.entidades.Foto;
import com.br.gsanac.entidades.Foto.FotoColunas;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad;
import com.br.gsanac.entidades.HidrometroInstHistAtlzCad.HidrometroInstHistAtlzCadColunas;
import com.br.gsanac.entidades.ImovelAtlzCadastral;
import com.br.gsanac.entidades.ImovelAtlzCadastral.ImovelAtlzCadastralColunas;
import com.br.gsanac.entidades.ImovelOcorrencia;
import com.br.gsanac.entidades.ImovelOcorrencia.ImovelOcorrenciaColunas;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad;
import com.br.gsanac.entidades.ImovelSubCategAtlzCad.ImovelSubCategAtlzCadColunas;
import com.br.gsanac.entidades.Logradouro;
import com.br.gsanac.entidades.Logradouro.Logradouros;
import com.br.gsanac.entidades.LogradouroCep;
import com.br.gsanac.entidades.LogradouroCep.LogradouroCeps;
import com.br.gsanac.entidades.SistemaParametros;
import com.br.gsanac.exception.ControladorException;
import com.br.gsanac.exception.FachadaException;
import com.br.gsanac.exception.RepositorioException;
import com.br.gsanac.fachada.Fachada;
import com.br.gsanac.util.ConstantesSistema;
import com.br.gsanac.util.Util;

public class TabsActivity extends TabActivity implements  OnTabChangeListener  {

	public static TabHost mTabHost;
			
	Fachada fachada = Fachada.getInstance();
	
	protected boolean execute = true;
	
	private Button btnAtualizar;
	
	private Button btnCancelar;
	
	private TextView totalImoveis;

	private TextView posicao;
	
	protected Button visualizar;
	
	public static ImovelAtlzCadastral imovel;
	
	public static ImovelAtlzCadastral imovelInicial;

	public static ClienteAtlzCadastral cliente;
	
	public static HidrometroInstHistAtlzCad hidrometroInstalacaoHist;
	
	public static HidrometroInstHistAtlzCad hidrometroInstalacaoHistInicial;
	
	public static ArrayList<ClienteFoneAtlzCad> colecaoClienteFone;
	
	public static ArrayList<ClienteFoneAtlzCad> colecaoClienteFoneIncial;
	
	public static SistemaParametros sistemaParametros;
	
	public static ArrayList<ImovelSubCategAtlzCad> colImoveisSubCategoria;
	
	public static ArrayList<ImovelSubCategAtlzCad> colImoveisSubCategoriaInicial;
	
	public static ArrayList<ImovelOcorrencia> colecaoImovelOcorrencia;
	
	public static ArrayList<ImovelOcorrencia> colecaoImovelOcorrenciaInicial;
	
	public static Foto fotoFrenteDaCasa;
	
	public static Foto fotoHidrometro;
	
	public static Foto fotoFrenteDaCasaInicial;
	
	public static Foto fotoHidrometroInicial;
	
	public static String mensagemErro;
	
	//valida se é necessario carregar um novo imóvel na tela.
	public static boolean carregarImovel = false;
	
	public static boolean concluirAtualizacao = false;
	
	public static boolean indicadorExibirMensagemErro = true;
	public static boolean indicadorAtualizacaoCancelada = true;
	
	public static boolean indicadorIntegracao = false;
	public static boolean primeiraVezAbaImovel = true;
	public static boolean primeiraVezAbaLigacao = true;
	
	//validacao data
	public static boolean dataNascimentoValida = true;
	public static boolean dataEmissaoValida = true;
	
	public static ImovelAtlzCadastral imovAtlzCad;
	
	
	private void setupTabHost() {
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
	}

	/** Called when the activity is first created. */
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs_activity);
		
		execute = true;
		
		try {
			
			if( indicadorAtualizacaoCancelada ){
				Util.removerAtributosTabsActivity();
				TabsActivity.indicadorAtualizacaoCancelada = false;
			}
			
			//Caso o usuario tenha selecionado concluir atualizacao do imóvel
			if ( concluirAtualizacao ) {
				
				concluirAtualizacao = false;
				
				ProgressDialog mProgressDialog = new ProgressDialog(TabsActivity.this) {
                    @Override
                    public void onBackPressed() {
                    }

                    @Override
                    public boolean onSearchRequested() {
                        return false;
                    }
                };
                
              ControladorUtil controladorUtil	= ControladorUtil.getInstance();
              
			try {
				imovAtlzCad = controladorUtil.concluir();
				indicadorExibirMensagemErro = true;
				
				//Valida e inseri/atualiza os dados no banco
				if ( imovAtlzCad != null ) {
				
				  mProgressDialog.setTitle("Atualizando o Imóvel");
	              mProgressDialog.setMessage("Por favor, aguarde enquanto enviamos os dados para o GSAN.");
	              mProgressDialog.setIndeterminate(false);
	              mProgressDialog.setIcon(R.drawable.ok);
	              mProgressDialog.setCancelable(false);
	              mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	              mProgressDialog.show();
	   
	              AsyncTask<Object, Object, Object> taskDownloadFile = new AsyncTask<Object, Object, Object>() {

	                  @Override
	                  protected Object doInBackground(Object... arg0) {

	                	
	      				try {
	      					
      						ConexaoWebServer web = new ConexaoWebServer(TabsActivity.this);	
      						
      						Cep cepEnvio = null;
      	    		        Logradouro logradouroEnvio = null;
      	    		        LogradouroCep logradouroCepEnvio = null;
      	    		           	 
    		                String selectionCep = Ceps.CODIGO + "=? ";
    		    	        
    		    	        String[] selectionArgsCep = new String[] {
    		    	            String.valueOf(imovAtlzCad.getCodigoCep())
    		    	        };
    		    	        
    		            	List<? extends EntidadeBase> listaCep = Fachada.getInstance().pesquisarLista(Cep.class,selectionCep, selectionArgsCep, null);
    		            	
    		            	//Valida se os ceps ja foram enviados.
    	    	            if ( listaCep != null && !listaCep.isEmpty() ) {
    	    	            	 
    	    	        		 Iterator<? extends EntidadeBase> iteratorCep = listaCep.iterator();
    	    	        		
    	    	        		 while( iteratorCep.hasNext() ) {
    	    	        			 Cep cep = (Cep) iteratorCep.next();

     		           		 		if ( cep.getCodigoUnico() == null || cep.getCodigoUnico().equals("") ) {
     		           		 			Long dateTime = new Date().getTime();
     		           		 			cep.setCodigoUnico(dateTime.toString());
     		           		 			Fachada.getInstance().update(cep);
     		           		 		}
     		           		 		cepEnvio = cep;
      	    		           	}
      	    	           }
      	    	        	
      	        	        String selectionLogradouro = Logradouros.INDICADORNOVO+ "=? AND " +Logradouros.ID + " =? ";
      	        	        
      	        	        String[] selectionArgsLogradouro = new String[] {
      	        	        		String.valueOf(ConstantesSistema.SIM),
      	            	            imovAtlzCad.getLogradouro().getId().toString()
      	        	        };
      	    	        	List<? extends EntidadeBase> listaLogradouro = Fachada.getInstance().pesquisarLista(Logradouro.class, selectionLogradouro, selectionArgsLogradouro, null);
      	    	        	
      	    	        	//Valida se os logradouros ja foram enviados.
      	    	            if (listaLogradouro != null && !listaLogradouro.isEmpty() ) {
      	    	                  
      	    	        		 Iterator<? extends EntidadeBase> iteratorLogradouro = listaLogradouro.iterator();
      	    	        		
      	    	        		 while( iteratorLogradouro.hasNext() ) {
      	    	        			 logradouroEnvio = (Logradouro) iteratorLogradouro.next();
      	    	        		 }
      	    	             }
      	    	            
      	    	             if ( cepEnvio != null && logradouroEnvio != null  ) {
	      	        	        String selectionLogradouroCep = LogradouroCeps.INDICADORNOVO+ "=? AND " +LogradouroCeps.CEP + " =? AND "
	      	        	        		+LogradouroCeps.LOGRADOURO + " =? ";
	      	        	        
	      	        	        String[] selectionArgsLogradouroCep = new String[] {
	      	        	        		String.valueOf(ConstantesSistema.SIM),
	      	            	            cepEnvio.getId().toString(),
	      	            	            logradouroEnvio.getId().toString()
	      	        	        };
	      	    	        	List<? extends EntidadeBase> listaLogradouroCep = Fachada.getInstance().pesquisarLista(LogradouroCep.class, selectionLogradouroCep, selectionArgsLogradouroCep, null);
	      	    	        	
	      	    	        	//Valida se os logradouros ja foram enviados.
	      	    	            if (listaLogradouroCep != null && !listaLogradouroCep.isEmpty() ) {
	      	    	              
	      	    	        		 Iterator<? extends EntidadeBase> iteratorLogradouroCep = listaLogradouroCep.iterator();
	      	    	        		
	      	    	        		 while( iteratorLogradouroCep.hasNext() ) {
	      	    	        			 logradouroCepEnvio = (LogradouroCep) iteratorLogradouroCep.next();
	      	    	        		 }
	      	    	             }
      	    	             }
      	    	             
      	    	            //Envia os dados do endereco do imovel.
    	            		boolean sucesso = web.enviarDadosEndereco(cepEnvio, logradouroEnvio, sistemaParametros, logradouroCepEnvio);
  	    	            	
  	    	            	if ( sucesso ) {

                            	try {
                            		
                            		if ( cepEnvio != null ) {
                            			cepEnvio.setIndicadorTransmitido(ConstantesSistema.INDICADOR_TRANSMITIDO);
                            			Fachada.getInstance().update(cepEnvio);
                            		}
                            		
                            		if ( logradouroCepEnvio != null ) {
                            			logradouroCepEnvio.setIndicadorTransmitido(ConstantesSistema.INDICADOR_TRANSMITIDO);
                            			Fachada.getInstance().update(logradouroCepEnvio);
                            		}
                            		
                            		if ( logradouroEnvio != null ) {
                            			logradouroEnvio.setIndicadorTransmitido(ConstantesSistema.INDICADOR_TRANSMITIDO);
                            			Fachada.getInstance().update(logradouroEnvio);
                            		}
                                    
                                    
                                    
	                                 //Pesquisa Cliente
	 	      	       		         String selection = ClienteAtlzCadastralColunas.IMOVELATLZCAD_ID + "=?";
	 	      	       			        
	 	      	       		         String[] selectionArgs = new String[] {
	 	      	       		        	imovAtlzCad.getId().toString()
	 	      	       		         };
	 	      	       		         
	 	      	       		         ClienteAtlzCadastral clienteAtlzCadastral = new ClienteAtlzCadastral();        
	 	      	       		         clienteAtlzCadastral =  (ClienteAtlzCadastral) Fachada.getInstance().pesquisar(clienteAtlzCadastral, selection, selectionArgs);
	 	      	       	   			
	 	      	       		         //CLIENTE FONE
	 	      	       		         List<ClienteFoneAtlzCad> listaClienteFoneAtlzCadastral = null;
	 	      	       		         if ( clienteAtlzCadastral != null && clienteAtlzCadastral.getId() != null ) {
	 	      	       		        	 
	 	      	       		        	 //Pesquisa Logradouro
	 	      	       			         String selectionFone = ClienteFoneAtlzCadColunas.CLIENTEATLZCAD_ID + "=?";
	 	      	       			        
	 	      	       			         String[] selectionArgsFone = new String[] {
	 	      	       			        		clienteAtlzCadastral.getId().toString()
	 	      	       			         };
	 	      	       		        	
	 	      	       		         	listaClienteFoneAtlzCadastral = (List<ClienteFoneAtlzCad>) Fachada.getInstance().pesquisarLista(ClienteFoneAtlzCad.class, selectionFone, selectionArgsFone, null);
	 	      	       		         }
	 	      	                    	
	 	      	       		         //Registro do tipo 08 - Hidrometro Atlz Cadastral
	 	      	    		         String selectionHidrometro = HidrometroInstHistAtlzCadColunas.IMOVELATLZCAD_ID + "=?";
	 	      	    		        
	 	      	    		         String[] selectionArgsHidrometro = new String[] {
	 	      	    		        		imovAtlzCad.getId().toString()
	 	      	    		         };
	 	      	    	        	
	 	      	    	        	 List<HidrometroInstHistAtlzCad> listaHidrometroInstHistAtlzCad = (List<HidrometroInstHistAtlzCad>) Fachada.getInstance().
	 	      	    	        			 	pesquisarLista(HidrometroInstHistAtlzCad.class, selectionHidrometro, selectionArgsHidrometro, null);
	 	      	    			     
	 	      	    	        	  //Registro do tipo 09 - Subcategoria Atlz Cadastral
	 	      	    		         String selectionSub = ImovelSubCategAtlzCadColunas.IMOVELATLZCAD_ID + "=?";
	 	      	    		        
	 	      	    		         String[] selectionArgsSub = new String[] {
	 	      	    		        		imovAtlzCad.getId().toString()
	 	      	    		         };
	 	      	    	       
	 	      	    	        	
	 	      	    	        	 List<ImovelSubCategAtlzCad> listaImovelSubCategAtlzCad = (List<ImovelSubCategAtlzCad>) Fachada.getInstance()
	 	      	    	        			 .pesquisarLista(ImovelSubCategAtlzCad.class, selectionSub, selectionArgsSub, null);

	 	      	    	        	 //Registro do tipo 10 - Imovel ocorrencia
	 	      	    			     String selectionOcorrencia = ImovelOcorrenciaColunas.IMOVELATLZCAD_ID + "=?";
	 	      	    			        
	 	      	    			     String[] selectionArgsOcorrencia = new String[] {
	 	      	    			    		imovAtlzCad.getId().toString()
	 	      	    			     };
	 	      	    		        	
	 	      	    		         List<ImovelOcorrencia> listaImovelOcorrencia = (List<ImovelOcorrencia>) Fachada.getInstance()
	 	      	    		        		 .pesquisarLista(ImovelOcorrencia.class, selectionOcorrencia, selectionArgsOcorrencia, null);
	 	      	    		         
	 	      	    		         //Verifica se o arquivo não está finalizado para transmitir os dados
	 	      	    		         Integer verificador = web.arquivoLiberadoParaTransmissao(sistemaParametros.getIdComando());
	 	      	    		      
	 	      	    		         if ( verificador.equals(Integer.valueOf(1)) ) {
		 	      	                     
	 	      	    		        	 boolean sucess = web.enviarDadosImovel(imovAtlzCad, clienteAtlzCadastral, listaClienteFoneAtlzCadastral, listaHidrometroInstHistAtlzCad,
		 	      	                        		listaImovelSubCategAtlzCad, listaImovelOcorrencia);
	
		 	      	                     if (sucess) {
		 	      	                        	 
		 	      	                    	 imovAtlzCad.setIndicadorTransmitido(ConstantesSistema.INDICADOR_TRANSMITIDO);
		 	      	                        	 
	 	      	                             try {
	 	      	                                Fachada.getInstance().update(imovAtlzCad);
	 	      	                             } catch (FachadaException fe) {
	 	      	                                fe.printStackTrace();
	 	      	                             }
	 	      	                            
		 	      	                         List<? extends EntidadeBase> listaFoto = null;
		 	      	          			
					      	                 String selectionPhoto = FotoColunas.INDICADORTRANSMITIDO + "=? AND " + FotoColunas.IMOVELATLZCAD_ID  +"=?";
					
					      	                 String[] selectionPhotoArgs = new String[] {
					      	                          String.valueOf(ConstantesSistema.INDICADOR_NAO_TRANSMITIDO),
					      	                          String.valueOf(imovAtlzCad.getId())
					      	                 };
					
					      	                 listaFoto = Fachada.getInstance().pesquisarLista(Foto.class, selectionPhoto, selectionPhotoArgs, FotoColunas.ID);
					
					      	                 Iterator<? extends EntidadeBase> iteratorListaFoto = listaFoto.iterator();
					      	                 while (iteratorListaFoto.hasNext()) {
					
					      	                      Foto foto = (Foto) iteratorListaFoto.next();
					
					      	                      File imageFile = new File(foto.getFotoPath());
					      	                      String nomeFoto = imageFile.getName();
					      	                      ArrayList<String> dados = Util.split_(nomeFoto);
					
					      	                      PhotoConnection connection = new PhotoConnection();
					
					      	                      connection.execute(imageFile, dados.get(0), foto.getFotoTipo(), foto);
					      	                 }
		 	      	                     }
	 	      	    		         } else {
	 	      	    		        	if ( verificador.equals(Integer.valueOf(2)) ){ 
	 	      	    		        		Util.exibirMensagemErro(TabsActivity.this, "O Arquivo já foi finalizado");
	 	      	    		        	}
	 	      	    		         }
	 	      	    		         
                                    
                                } catch (FachadaException fe) {
                                    fe.printStackTrace();
                                }
  	    	            	}
      	                  } catch (Exception e) {
    	                        Log.e(ConstantesSistema.LOG_TAG, e.getMessage());
    	                  }
      						
      					  //Verifica se o app GEO chamou a funcionalidade de atualizacao do imóvel - necessario para enviar o retorno para o app do GEO.
      					  if ( indicadorIntegracao ) {
      							
      							//Chama a aplicacao do GEO.
      							Intent retornoIntegracaoCompesa = new Intent("exibirmapa");
      							
      							//Monta o retorno que vai ser enviado para app GEO.
      							if ( imovAtlzCad.getImovelId() != null ) {
      								//Caso seja imovel ja cadastrado na base do gsan envia o retorno = 1, indicando que a operacao foi efetuada com sucesso.
      								retornoIntegracaoCompesa.putExtra(ConstantesSistema.INTEGRACAO_OPERACAO_INTEGRACAO, String.valueOf(ConstantesSistema.SIM));
      								retornoIntegracaoCompesa.putExtra(ConstantesSistema.INTEGRACAO_ID_UNICO, imovAtlzCad.getIntegracaoID());
      							} else {
      								//caso o imovel nao seja cadastrado no gsan, retorna um identificador (inscricao + timestamp).
      								retornoIntegracaoCompesa.putExtra(ConstantesSistema.INTEGRACAO_OPERACAO_INTEGRACAO, String.valueOf(ConstantesSistema.SIM));
      								retornoIntegracaoCompesa.putExtra(ConstantesSistema.INTEGRACAO_ID_UNICO, imovAtlzCad.getIntegracaoID());
      							}
      							//Faz com que nao carregue os dados das abas.
      							execute = false;
      							//apaga os atributos.
//      							Util.removerAtributosTabsActivity();
      							
      							startActivity(retornoIntegracaoCompesa);							
      							finish();
      					  } else {

      							Intent itRoteiro = new Intent(TabsActivity.this, RoteiroActivity.class);
      							startActivity(itRoteiro);
      							finish();	
      					  }
      						
      				
		      			  concluirAtualizacao = false;
		      			  indicadorExibirMensagemErro = true;
		      			  TabsActivity.indicadorAtualizacaoCancelada = true;
		      			  
	                      return true;
	                      }
	                  };

	                  taskDownloadFile.execute();
				
				
	              } else {
						Util.exibirMensagemErro(TabsActivity.this, mensagemErro);
	              }
				
				} catch (ControladorException e) {
					Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
				} catch (RepositorioException e) {
					e.printStackTrace();
				}
              
             
				
				
			} else {
				
				String matricula = null;
				
				if(getIntent().getSerializableExtra(ConstantesSistema.INTEGRACAO_MATRICULA) != null){
					Util.removerAtributosTabsActivity();
				}
			
				if ( imovel == null ) {
					
					//Caso o sistema seja integrado com o GEO
					if ( getIntent().getSerializableExtra(ConstantesSistema.INTEGRACAO_MATRICULA) != null ) {
						matricula = (String) getIntent().getSerializableExtra(ConstantesSistema.INTEGRACAO_MATRICULA);
						
						getIntent().removeExtra(ConstantesSistema.INTEGRACAO_MATRICULA);
						
						if ( matricula != null && !matricula.equals("") ) {
							
							Util.removerAtributosTabsActivity();
							indicadorIntegracao= true;
							
							String selection = ImovelAtlzCadastralColunas.IMOVEL_ID + "=?";
					        String[] selectionArgs = new String[] {
					            matricula
					        };
					        
					        imovel = new ImovelAtlzCadastral();
					        imovel = (ImovelAtlzCadastral) Fachada.getInstance().pesquisar(imovel,selection, selectionArgs );
					        
					        if ( imovel != null && imovel.getIndicadorTransmitido() != null && 
					        		imovel.getIndicadorTransmitido().toString().equals(String.valueOf(ConstantesSistema.INDICADOR_TRANSMITIDO)) ) {
					        	
					        	  new AlertDialog.Builder(TabsActivity.this).setTitle("Imóvel Atualizado")
			                      .setMessage("O imóvel selecionado já foi transmitido.")
			                      .setIcon(R.drawable.ok)
			                      .setCancelable(false)
			                      .setNeutralButton("Ok",
			                                        new DialogInterface.OnClickListener() {
			                                            @Override
			                                            public void onClick(
			                                                    DialogInterface dialog,
			                                                    int which) {
			                                            	
			                                            	//Chama a aplicacao do GEO.
			                    							Intent retornoIntegracaoCompesa = new Intent("exibirmapa");
			                                            	//Chama a aplicacao do GEO. INFORMANDO FALHA NA ATUALIZACAO.
			                    							retornoIntegracaoCompesa.putExtra(ConstantesSistema.INTEGRACAO_OPERACAO_INTEGRACAO, String.valueOf(ConstantesSistema.NAO));
			                    							startActivity(retornoIntegracaoCompesa);
			                    							finish();
			                                            }
			                                        })
			                      .show();
					        	  TabsActivity.indicadorAtualizacaoCancelada = true;
					        }
						}
					//Caso seja imóvel novo integrado com o GEO.
					} else if ( getIntent().getSerializableExtra(ConstantesSistema.INTEGRACAO_ID_UNICO) != null ) {
						indicadorIntegracao= true;					
						String matriculaNovoImovel = (String) getIntent().getSerializableExtra(ConstantesSistema.INTEGRACAO_ID_UNICO);
						if ( matriculaNovoImovel != null && !matriculaNovoImovel.equals("") ) {
							Util.removerAtributosTabsActivity();
							
							String selection = ImovelAtlzCadastralColunas.INTEGRACAO + "=?";
					        String[] selectionArgs = new String[] {
					        		matriculaNovoImovel
					        };
					       
					        imovel = new ImovelAtlzCadastral();
					        imovel = (ImovelAtlzCadastral) Fachada.getInstance().pesquisar(imovel,selection, selectionArgs );
					        
					        if ( imovel != null && imovel.getIndicadorTransmitido() != null && 
					        		imovel.getIndicadorTransmitido().toString().equals(String.valueOf(ConstantesSistema.INDICADOR_TRANSMITIDO)) ) {
					        	
					        	  new AlertDialog.Builder(TabsActivity.this).setTitle("Imóvel Atualizado")
			                      .setMessage("O imóvel selecionado já foi transmitido.")
			                      .setIcon(R.drawable.ok)
			                      .setCancelable(false)
			                      .setNeutralButton("Ok",
			                                        new DialogInterface.OnClickListener() {
			                                            @Override
			                                            public void onClick(
			                                                    DialogInterface dialog,
			                                                    int which) {
			                                            	
			                                            	//Chama a aplicacao do GEO.
			                    							Intent retornoIntegracaoCompesa = new Intent("exibirmapa");
			                                            	//Chama a aplicacao do GEO. INFORMANDO FALHA NA ATUALIZACAO.
			                    							retornoIntegracaoCompesa.putExtra(ConstantesSistema.INTEGRACAO_OPERACAO_INTEGRACAO, String.valueOf(ConstantesSistema.NAO));
			                    							startActivity(retornoIntegracaoCompesa);
			                    							finish();
			                                            }
			                                        })
	
			                      .show();
					        	  TabsActivity.indicadorAtualizacaoCancelada = true;					        	
					        }
						}
						//Caso seja selecionado a partir da tela de roteiro
					} else if ( getIntent().getSerializableExtra(ConstantesSistema.IMOVEL) != null ) {
						Util.removerAtributosTabsActivity();
						imovel = (ImovelAtlzCadastral) getIntent().getSerializableExtra(ConstantesSistema.IMOVEL);
					}else if(getIntent().getSerializableExtra("indicadorNovo") != null){
						//Caso seja imovel novo chamado da tela de roteiro
						Util.removerAtributosTabsActivity();
					} else {
						//Caso seja imovel novo integrado com o GEO
						Util.removerAtributosTabsActivity();
						indicadorIntegracao= true;
						
					}
					
					sistemaParametros = new SistemaParametros();
					sistemaParametros = (SistemaParametros) fachada.pesquisar(sistemaParametros, null, null);
					
					if ( imovel != null && imovel.getId() != null ) {
						//Pesquisa Cliente
				        String selection = ClienteAtlzCadastralColunas.IMOVELATLZCAD_ID + "=?";
				        String[] selectionArgs = new String[] {
				            String.valueOf(imovel.getId())
				        };
				        
				        cliente = new ClienteAtlzCadastral(); 
				        cliente = (ClienteAtlzCadastral) Fachada.getInstance().pesquisar(cliente,selection, selectionArgs );
				        
				        //Pesquisa Hidrometro
			            selection = HidrometroInstHistAtlzCadColunas.IMOVELATLZCAD_ID + "=?";
			            selection = selection + " AND " + HidrometroInstHistAtlzCadColunas.MEDICAOTIPO_ID + "=?";
			        	
			            selectionArgs = new String[] {
			                String.valueOf(imovel.getId()),
			                "1" //Agua
			            };
			
			           //verifica se ja existe hidrometro na tabela HidrometroInstHistAtlzCad
			           hidrometroInstalacaoHist = new HidrometroInstHistAtlzCad();
			           hidrometroInstalacaoHist = (HidrometroInstHistAtlzCad) fachada.pesquisar(hidrometroInstalacaoHist, selection, selectionArgs);
			           hidrometroInstalacaoHistInicial = hidrometroInstalacaoHist.clone();
					} else {
						//novo imóvel
						imovel = new ImovelAtlzCadastral();
						hidrometroInstalacaoHist = new HidrometroInstHistAtlzCad();
					}
	           
					if(imovel != null && imovel.getImovelId() != null && !imovel.getImovelId().equals("")){
						//Pesquisa Cliente
				        String selection = ClienteAtlzCadastralColunas.IMOVELATLZCAD_ID + "=?";
				        String[] selectionArgs = new String[] {
				            String.valueOf(imovel.getId())
				        };
				        
				        cliente = new ClienteAtlzCadastral(); 
				        cliente = (ClienteAtlzCadastral) Fachada.getInstance().pesquisar(cliente,selection, selectionArgs );
	        		
					   if(cliente != null && cliente.getId() != null  && !cliente.getId().equals("")){
					        //Pesquisa Cliente Tipo
					   		selection = ClienteTipoColunas.ID + "=?";
					   		selectionArgs = new String[] { String.valueOf(cliente.getClienteTipo().getId()) };
				            
				            ClienteTipo clienteTipo = new ClienteTipo();
				            clienteTipo = (ClienteTipo) Fachada.getInstance().pesquisar(clienteTipo, selection, selectionArgs);
				            
				            cliente.setClienteTipo(clienteTipo);
				            
				            //Pesquisa Cliente Fone
				            selection = ClienteFoneAtlzCadColunas.CLIENTEATLZCAD_ID + "=?";
				        	
				            selectionArgs = new String[] {
				                String.valueOf(cliente.getId()),
				            };
				            
				            colecaoClienteFone = (ArrayList<ClienteFoneAtlzCad>) fachada.pesquisarLista(ClienteFoneAtlzCad.class, selection, selectionArgs, null);
				            
				            colecaoClienteFoneIncial = (ArrayList<ClienteFoneAtlzCad>) colecaoClienteFone.clone();
					   }
				   
				        //Pesquisa Hidrometro
			           selection = HidrometroInstHistAtlzCadColunas.IMOVELATLZCAD_ID + "=?";
			           selection = selection + " AND " + HidrometroInstHistAtlzCadColunas.MEDICAOTIPO_ID + "=?";
			       	
			           selectionArgs = new String[] {
			               String.valueOf(imovel.getId()),
			               "1" //Agua
			           };
			
			           //verifica se ja existe hidrometro na tabela HidrometroInstHistAtlzCad
			           hidrometroInstalacaoHist = new HidrometroInstHistAtlzCad();
			           hidrometroInstalacaoHist = (HidrometroInstHistAtlzCad) fachada.pesquisar(hidrometroInstalacaoHist, selection, selectionArgs);
			           
			           if(hidrometroInstalacaoHist.getId() == null){
			        	   hidrometroInstalacaoHist  = null;
			           }else{
			        	   hidrometroInstalacaoHistInicial = hidrometroInstalacaoHist.clone();
			           }	
			           
			           //Pesquisar Foto Frente da Casa
			           fotoFrenteDaCasa = Util.pesquisarFotoBanco(imovel.getId(), ConstantesSistema.FOTO_TIPO_FRENTE_DE_CASA);
			           if(fotoFrenteDaCasa != null){
			        	   fotoFrenteDaCasaInicial = fotoFrenteDaCasa.clone();
			           }
			           
			           //Pesquisar Foto Hidrometro
			           fotoHidrometro = Util.pesquisarFotoBanco(imovel.getId(), ConstantesSistema.FOTO_TIPO_HIDROMETRO);			           
			           if(fotoHidrometro != null){
			        	   fotoHidrometroInicial = fotoHidrometro.clone();
			           }
			           
						//Pesquisar ImovelOcorrencia Atualização Cadastral
			            selection = ImovelOcorrenciaColunas.IMOVELATLZCAD_ID + "=?";
			            selectionArgs = new String[] {
			                String.valueOf(imovel.getId())
			            };
		
			            colecaoImovelOcorrencia = (ArrayList<ImovelOcorrencia>) fachada.pesquisarLista(ImovelOcorrencia.class,selection, selectionArgs, null);
					
			            colecaoImovelOcorrenciaInicial = (ArrayList<ImovelOcorrencia>) colecaoImovelOcorrencia.clone();
			           
						//Pesquisar SubCategoria Atualização Cadastral
			            selection = ImovelSubCategAtlzCadColunas.IMOVELATLZCAD_ID + "=?";
			            selectionArgs = new String[] {
			                String.valueOf(imovel.getId())
			            };
		
			            colImoveisSubCategoria = (ArrayList<ImovelSubCategAtlzCad>) fachada.pesquisarLista(ImovelSubCategAtlzCad.class,selection, selectionArgs, null);
			            
			            colImoveisSubCategoriaInicial = (ArrayList<ImovelSubCategAtlzCad>) colImoveisSubCategoria.clone();
			            
					}else{
						//Caso seja uma 'matricula nova' inserida manualmente
						if(matricula != null && !matricula.equals("")){
							String digito = matricula.substring(matricula.length() - 1, matricula.length());
							String numero = matricula.substring(0, matricula.length() - 1);
							if(Util.obterDigitoVerificadorModulo11(numero).equals(Integer.parseInt(digito)) 
									|| Util.obterDigitoVerificadorAntigo(numero).equals(Integer.parseInt(digito))){
								imovel.setImovelId(Integer.parseInt(matricula));				
								imovel.setIndicadorImovelNovoComMatricula(ConstantesSistema.SIM);
							}else{
					        	  new AlertDialog.Builder(TabsActivity.this).setTitle("Matrícula Inválida")
			                      .setMessage("A matrícula informada está inválida.")
			                      .setIcon(R.drawable.ok)
			                      .setCancelable(false)
			                      .setNeutralButton("Ok",
			                                        new DialogInterface.OnClickListener() {
			                                            @Override
			                                            public void onClick(
			                                                    DialogInterface dialog,
			                                                    int which) {
			                                            	
			                                            	//Chama a aplicacao do GEO.
			                    							Intent retornoIntegracaoCompesa = new Intent("exibirmapa");
			                                            	//Chama a aplicacao do GEO. INFORMANDO FALHA NA ATUALIZACAO.
			                    							retornoIntegracaoCompesa.putExtra(ConstantesSistema.INTEGRACAO_OPERACAO_INTEGRACAO, String.valueOf(ConstantesSistema.NAO));
			                    							startActivity(retornoIntegracaoCompesa);
			                    							finish();
			                                            }
			                                        })
	
			                      .show();					        	  
					        	  TabsActivity.indicadorAtualizacaoCancelada = true;
							}
						}
					}
				}					
				
				imovelInicial = imovel.clone();
			}
		} catch (FachadaException fe) {
			Log.e(ConstantesSistema.LOG_TAG, fe.getMessage() + " - " + fe.getCause());
		} catch (CloneNotSupportedException e) {
			Log.e(ConstantesSistema.LOG_TAG, e.getMessage() + " - " + e.getCause());
		}
		
		if (execute){	
			// construct the tabhost
			setContentView(R.layout.tabs_activity);
			Fachada.setContext(this);
			
			// Para o teclado não aparecer ao entrar na tela
		    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	
	        setupTabHost();
			
	        exibirAbas();
			
			setUpWidgets();
			
			atualizarPosicoes();
	        
		}
	}
	
	
	private void atualizarPosicoes() {
		totalImoveis = (TextView) findViewById(R.id.total);
	    posicao = (TextView) findViewById(R.id.posicao);
	    
	    if(imovel!=null && imovel.getPosicao() != null ){	
	    	
			posicao.setText(imovel.getPosicao()+"/");
			
			totalImoveis.setText( sistemaParametros.getQuantidadeImovel() );
			
//			anterior.setVisibility(View.VISIBLE);
//			proximo.setVisibility(View.VISIBLE);
			
		}	else {
			posicao.setText("NOVO IMÓVEL");
//			anterior.setVisibility(View.INVISIBLE);
//			proximo.setVisibility(View.INVISIBLE);
		}
	}

	private void exibirAbas() {
		   
			Intent itLocalidade = new Intent(this, LocalidadeAbaActivity.class);
			novaAba(new TextView(this), getString(R.string.aba_localidade), itLocalidade);				
	    	
	    	Intent itEndereco = new Intent(this, EnderecoAbaActivity.class);
	    	novaAba(new TextView(this), getString(R.string.title_activity_endereco_aba), itEndereco);	    	

	    	Intent itCliente = new Intent(this, ClienteAbaActivity.class);
	    	novaAba(new TextView(this), getString(R.string.title_activity_endereco_cliente), itCliente);
	    	
	    	Intent itImovel = new Intent(this, ImovelAbaActivity.class);
	    	novaAba(new TextView(this), getString(R.string.title_activity_imovel) , itImovel);
	    	
	    	Intent itLigacao = new Intent(this, LigacaoAbaActivity.class);
	    	novaAba(new TextView(this), getString(R.string.title_activity_ligacao_aba), itLigacao);
	    	
	    	Intent itFotos = new Intent(this, FotosAbaActivity.class);
	    	novaAba(new TextView(this), getString(R.string.title_activity_fotos_aba), itFotos);

			mTabHost.setCurrentTabByTag(getString(R.string.aba_localidade));
			
			
			mTabHost.setOnTabChangedListener(this);
			
	}
	
     private void novaAba(final View view, final String tag, Intent it) {
    	
    	View tabview = createTabView(mTabHost.getContext(), tag);
		TabSpec setContent = mTabHost.newTabSpec(tag).setIndicator(tabview).setContent(it); {} ;
		mTabHost.addTab(setContent);
     }
	
	private void setUpWidgets() {
		totalImoveis = (TextView) findViewById(R.id.total);
        posicao = (TextView) findViewById(R.id.posicao);	
        
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bgproximo);
        @SuppressWarnings("deprecation")
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bmp);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);        
        LinearLayout layout = (LinearLayout) findViewById(R.id.barra);
        layout.setBackgroundDrawable(bitmapDrawable);
        
		
		btnAtualizar = (Button) findViewById(R.id.btnAtualizar);
		
		btnAtualizar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				indicadorExibirMensagemErro = false;
				 concluirAtualizacao = true;
					Intent i = getIntent();
					startActivity(i);
					finish();
			}
		});	
		
		btnCancelar = (Button) findViewById(R.id.btnCancelar);
		
		btnCancelar.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				if ( indicadorIntegracao ) {
					
					//Chama a aplicacao do GEO. INFORMANDO FALHA NA ATUALIZACAO.
					Intent retornoIntegracaoCompesa = new Intent("exibirmapa");
					retornoIntegracaoCompesa.putExtra(ConstantesSistema.INTEGRACAO_OPERACAO_INTEGRACAO, String.valueOf(ConstantesSistema.NAO));
					startActivity(retornoIntegracaoCompesa);
					finish();
//					Util.removerAtributosTabsActivity();
					
				} else {
					//Retorna para a tela de roteiro
					Intent intent = new Intent(TabsActivity.this, RoteiroActivity.class);
					startActivity(intent);
					finish();
				}
				
				TabsActivity.indicadorAtualizacaoCancelada = true;				
				TabsActivity.indicadorExibirMensagemErro = false;
			}
		});	
    }    
	
	public void chamaProximo(Integer posicao,boolean proximo, String ultimoRegistro){
		iniciarProximaIntent(posicao, proximo, ultimoRegistro);
	}
	
	
	
	public void iniciarProximaIntent(Integer posicao, boolean proximo, String ultimoRegistro){
		
		
		ImovelAtlzCadastral imovelProximo = null;
		
		boolean aux = true;
		
		//Pecorre a lista de imóvel para buscar o anterior/proximo imovel
		while ( aux ) {
			
			
			
			//Caso nao seja imovel novo.
			if ( posicao != null ) {
			
				//Botao proximo
				if( proximo ) {
					
					//Caso seja ultimo da uma volta
					if ( posicao.toString().equals(ultimoRegistro) ) {
						posicao = 1;
					} else {
						posicao = posicao + 1;
						
					}
					
				} else {
					//botao anterior
					if ( posicao == 1 ) {
						//caso seja o primeiro chama o ultimo
						posicao = Integer.valueOf(ultimoRegistro);
					} else {
						posicao = posicao - 1;
					}
				}
			
			}
			
			
			
			try {
				imovelProximo = Fachada.getInstance().buscarImovelPosicao(posicao);
			} catch (FachadaException e) {
				Log.e(ConstantesSistema.LOG_TAG,e.getMessage());
				e.printStackTrace();
			}
			
			if ( imovelProximo.getIndicadorFinalizado() != ConstantesSistema.FINALIZADO ) {
				aux = false;
				break;
			}
			
		}

		indicadorExibirMensagemErro = false;
		carregarImovel = true;
		Intent i = getIntent();
		i.putExtra(ConstantesSistema.IMOVEL, imovelProximo);
		startActivity(i);
		finish();
		
		if(proximo){
			overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);  
		}else{
			overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);  
		}
	}
   
    	
	private static View createTabView(final Context context, final String text) {
		View view = LayoutInflater.from(context).inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(text);
		return view;
	}

	//Verifica se a matricula inserida e valida
	private boolean validarMatriculaImovel(String matricula){
		boolean resultado = false; 
		String digito = matricula.substring(matricula.length() - 1, matricula.length());
		String numero = matricula.substring(0, matricula.length() - 1);
		
		if(Integer.parseInt(matricula) > Integer.parseInt("75999925")){
			resultado = Util.obterDigitoVerificadorModulo11(numero).equals(Integer.parseInt(digito));
		}else{
			resultado =  Util.obterDigitoVerificadorAntigo(numero).equals(Integer.parseInt(digito));
		}
		
		return resultado;
	}
	
	@Override
    public void onBackPressed() { 
	}
	
	public void onTabChanged(String tabId) {
		
	}
	
	public View createTabContent(String tag) {
		
		return null;
	}
	
}